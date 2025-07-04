package com.xiaomi.base.components.collaboration.realtime

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*
import com.xiaomi.base.ui.theme.Orange
import com.xiaomi.base.ui.theme.Purple

// Enums
enum class CollaborationEventType {
    USER_JOINED,
    USER_LEFT,
    CURSOR_MOVED,
    TEXT_EDITED,
    SELECTION_CHANGED,
    COMMENT_ADDED,
    FILE_OPENED,
    VOICE_STARTED,
    VOICE_ENDED,
    SCREEN_SHARED,
    ANNOTATION_ADDED
}

enum class UserPresenceStatus {
    ONLINE,
    AWAY,
    BUSY,
    OFFLINE
}

enum class CollaborationMode {
    EDIT,
    REVIEW,
    PRESENTATION,
    BRAINSTORM
}

enum class PermissionLevel {
    OWNER,
    EDITOR,
    REVIEWER,
    VIEWER
}

enum class ConflictResolutionStrategy {
    LAST_WRITE_WINS,
    OPERATIONAL_TRANSFORM,
    MANUAL_RESOLUTION,
    VERSION_BRANCHING
}

// Data Classes
data class CollaborationUser(
    val id: String,
    val name: String,
    val avatar: String,
    val status: UserPresenceStatus,
    val permission: PermissionLevel,
    val cursorPosition: Offset = Offset.Zero,
    val cursorColor: Color = Color.Blue,
    val lastActivity: Long = System.currentTimeMillis(),
    val isTyping: Boolean = false,
    val currentFile: String? = null
)

data class CollaborationEvent(
    val id: String,
    val type: CollaborationEventType,
    val userId: String,
    val timestamp: Long,
    val data: Map<String, Any> = emptyMap(),
    val position: Offset? = null
)

data class CollaborationComment(
    val id: String,
    val userId: String,
    val content: String,
    val timestamp: Long,
    val position: Offset,
    val resolved: Boolean = false,
    val replies: List<CollaborationComment> = emptyList()
)

data class CollaborationConflict(
    val id: String,
    val type: String,
    val description: String,
    val affectedUsers: List<String>,
    val timestamp: Long,
    val resolved: Boolean = false,
    val resolutionStrategy: ConflictResolutionStrategy
)

data class CollaborationSession(
    val id: String,
    val name: String,
    val mode: CollaborationMode,
    val owner: String,
    val participants: List<CollaborationUser>,
    val startTime: Long,
    val isActive: Boolean = true,
    val sharedFiles: List<String> = emptyList()
)

data class RealTimeCollaborationConfig(
    val enableCursorTracking: Boolean = true,
    val enableVoiceChat: Boolean = true,
    val enableScreenShare: Boolean = true,
    val enableComments: Boolean = true,
    val maxParticipants: Int = 10,
    val autoSaveInterval: Long = 5000,
    val conflictResolution: ConflictResolutionStrategy = ConflictResolutionStrategy.OPERATIONAL_TRANSFORM
)

// Main Component
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RealTimeCollaborationComponent(
    modifier: Modifier = Modifier,
    config: RealTimeCollaborationConfig = RealTimeCollaborationConfig()
) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Session", "Users", "Activity", "Comments", "Conflicts")
    
    // Sample data
    val currentSession by remember {
        mutableStateOf(
            generateSampleSession()
        )
    }
    val events by remember { mutableStateOf(generateSampleEvents()) }
    val comments by remember { mutableStateOf(generateSampleComments()) }
    val conflicts by remember { mutableStateOf(generateSampleConflicts()) }
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Header
        CollaborationHeader(
            session = currentSession,
            onSessionAction = { /* Handle session actions */ }
        )
        
        // Tab Row
        TabRow(
            selectedTabIndex = selectedTab,
            modifier = Modifier.fillMaxWidth()
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = { Text(title) }
                )
            }
        }
        
        // Content
        when (selectedTab) {
            0 -> CollaborationSessionComponent(
                session = currentSession,
                config = config
            )
            1 -> ActiveUsersComponent(
                users = currentSession.participants,
                config = config
            )
            2 -> ActivityFeedComponent(
                events = events,
                users = currentSession.participants
            )
            3 -> CommentsComponent(
                comments = comments,
                users = currentSession.participants
            )
            4 -> ConflictResolutionComponent(
                conflicts = conflicts,
                users = currentSession.participants
            )
        }
    }
}

@Composable
fun CollaborationHeader(
    session: CollaborationSession,
    onSessionAction: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = session.name,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Mode: ${session.mode.name}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                Row {
                    IconButton(
                        onClick = { onSessionAction("share") }
                    ) {
                        Icon(Icons.Default.Share, contentDescription = "Share Session")
                    }
                    IconButton(
                        onClick = { onSessionAction("settings") }
                    ) {
                        Icon(Icons.Default.Settings, contentDescription = "Session Settings")
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Participants preview
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(session.participants.take(5)) { user ->
                    UserAvatarWithStatus(
                        user = user,
                        size = 32.dp
                    )
                }
                if (session.participants.size > 5) {
                    item {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .background(
                                    MaterialTheme.colorScheme.surfaceVariant,
                                    CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "+${session.participants.size - 5}",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CollaborationSessionComponent(
    session: CollaborationSession,
    config: RealTimeCollaborationConfig
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            SessionInfoCard(session = session)
        }
        
        item {
            RealTimeCursorTrackingComponent(
                users = session.participants,
                enabled = config.enableCursorTracking
            )
        }
        
        item {
            VoiceChatComponent(
                users = session.participants,
                enabled = config.enableVoiceChat
            )
        }
        
        item {
            ScreenShareComponent(
                users = session.participants,
                enabled = config.enableScreenShare
            )
        }
    }
}

@Composable
fun ActiveUsersComponent(
    users: List<CollaborationUser>,
    config: RealTimeCollaborationConfig
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(users) { user ->
            UserCard(
                user = user,
                onUserAction = { action -> /* Handle user actions */ }
            )
        }
    }
}

@Composable
fun ActivityFeedComponent(
    events: List<CollaborationEvent>,
    users: List<CollaborationUser>
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(events) { event ->
            ActivityEventCard(
                event = event,
                user = users.find { it.id == event.userId }
            )
        }
    }
}

@Composable
fun CommentsComponent(
    comments: List<CollaborationComment>,
    users: List<CollaborationUser>
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(comments) { comment ->
            CommentCard(
                comment = comment,
                user = users.find { it.id == comment.userId },
                onCommentAction = { action -> /* Handle comment actions */ }
            )
        }
    }
}

@Composable
fun ConflictResolutionComponent(
    conflicts: List<CollaborationConflict>,
    users: List<CollaborationUser>
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(conflicts) { conflict ->
            ConflictCard(
                conflict = conflict,
                users = users,
                onConflictAction = { action -> /* Handle conflict resolution */ }
            )
        }
    }
}

// Supporting Components
@Composable
fun UserAvatarWithStatus(
    user: CollaborationUser,
    size: androidx.compose.ui.unit.Dp = 40.dp
) {
    Box {
        Box(
            modifier = Modifier
                .size(size)
                .background(
                    user.cursorColor,
                    CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = user.name.take(2).uppercase(),
                color = Color.White,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold
            )
        }
        
        // Status indicator
        Box(
            modifier = Modifier
                .size(12.dp)
                .background(
                    when (user.status) {
                        UserPresenceStatus.ONLINE -> Color.Green
                        UserPresenceStatus.AWAY -> Color.Yellow
                        UserPresenceStatus.BUSY -> Color.Red
                        UserPresenceStatus.OFFLINE -> Color.Gray
                    },
                    CircleShape
                )
                .align(Alignment.BottomEnd)
        )
    }
}

@Composable
fun SessionInfoCard(
    session: CollaborationSession
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Session Information",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            InfoRow("Session ID", session.id)
            InfoRow("Owner", session.owner)
            InfoRow("Participants", "${session.participants.size}")
            InfoRow("Started", formatTimestamp(session.startTime))
            InfoRow("Status", if (session.isActive) "Active" else "Inactive")
        }
    }
}

@Composable
fun RealTimeCursorTrackingComponent(
    users: List<CollaborationUser>,
    enabled: Boolean
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Cursor Tracking",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Switch(
                    checked = enabled,
                    onCheckedChange = { /* Handle toggle */ }
                )
            }
            
            if (enabled) {
                Spacer(modifier = Modifier.height(16.dp))
                
                // Cursor visualization area
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(
                            MaterialTheme.colorScheme.surfaceVariant,
                            RoundedCornerShape(8.dp)
                        )
                ) {
                    Canvas(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        users.forEach { user ->
                            if (user.status == UserPresenceStatus.ONLINE) {
                                drawCursor(
                                    position = user.cursorPosition,
                                    color = user.cursorColor,
                                    userName = user.name
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun VoiceChatComponent(
    users: List<CollaborationUser>,
    enabled: Boolean
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Voice Chat",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Switch(
                    checked = enabled,
                    onCheckedChange = { /* Handle toggle */ }
                )
            }
            
            if (enabled) {
                Spacer(modifier = Modifier.height(16.dp))
                
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(users.filter { it.status == UserPresenceStatus.ONLINE }) { user ->
                        VoiceUserCard(user = user)
                    }
                }
            }
        }
    }
}

@Composable
fun ScreenShareComponent(
    users: List<CollaborationUser>,
    enabled: Boolean
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Screen Share",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Switch(
                    checked = enabled,
                    onCheckedChange = { /* Handle toggle */ }
                )
            }
            
            if (enabled) {
                Spacer(modifier = Modifier.height(16.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = { /* Start screen share */ },
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(Icons.Default.ScreenShare, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Share Screen")
                    }
                    
                    Button(
                        onClick = { /* View shared screen */ },
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(Icons.Default.Visibility, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("View Share")
                    }
                }
            }
        }
    }
}

@Composable
fun UserCard(
    user: CollaborationUser,
    onUserAction: (String) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            UserAvatarWithStatus(user = user)
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = user.name,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = user.permission.name,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                if (user.isTyping) {
                    Text(
                        text = "Typing...",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
            
            IconButton(
                onClick = { onUserAction("menu") }
            ) {
                Icon(Icons.Default.MoreVert, contentDescription = "User Menu")
            }
        }
    }
}

@Composable
fun ActivityEventCard(
    event: CollaborationEvent,
    user: CollaborationUser?
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = getEventIcon(event.type),
                contentDescription = null,
                tint = getEventColor(event.type)
            )
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = getEventDescription(event, user?.name ?: "Unknown"),
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = formatTimestamp(event.timestamp),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun CommentCard(
    comment: CollaborationComment,
    user: CollaborationUser?,
    onCommentAction: (String) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (user != null) {
                        UserAvatarWithStatus(user = user, size = 24.dp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = user.name,
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                
                Text(
                    text = formatTimestamp(comment.timestamp),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = comment.content,
                style = MaterialTheme.typography.bodyMedium
            )
            
            if (comment.replies.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "${comment.replies.size} replies",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clickable { onCommentAction("view_replies") }
                )
            }
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                if (!comment.resolved) {
                    TextButton(
                        onClick = { onCommentAction("resolve") }
                    ) {
                        Text("Resolve")
                    }
                }
                TextButton(
                    onClick = { onCommentAction("reply") }
                ) {
                    Text("Reply")
                }
            }
        }
    }
}

@Composable
fun ConflictCard(
    conflict: CollaborationConflict,
    users: List<CollaborationUser>,
    onConflictAction: (String) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (conflict.resolved) 
                MaterialTheme.colorScheme.surfaceVariant 
            else 
                MaterialTheme.colorScheme.errorContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = conflict.type,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                
                if (conflict.resolved) {
                    Icon(
                        Icons.Default.CheckCircle,
                        contentDescription = "Resolved",
                        tint = Color.Green
                    )
                } else {
                    Icon(
                        Icons.Default.Warning,
                        contentDescription = "Unresolved",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = conflict.description,
                style = MaterialTheme.typography.bodyMedium
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Affected users: ${conflict.affectedUsers.joinToString(", ")}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Text(
                text = "Strategy: ${conflict.resolutionStrategy.name}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            if (!conflict.resolved) {
                Spacer(modifier = Modifier.height(12.dp))
                
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = { onConflictAction("resolve") },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Resolve")
                    }
                    
                    OutlinedButton(
                        onClick = { onConflictAction("details") },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Details")
                    }
                }
            }
        }
    }
}

@Composable
fun VoiceUserCard(
    user: CollaborationUser
) {
    val isSpeaking by remember { mutableStateOf(false) }
    val animatedScale by animateFloatAsState(
        targetValue = if (isSpeaking) 1.2f else 1f,
        animationSpec = tween(300)
    )
    
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size((40 * animatedScale).dp)
                .background(
                    user.cursorColor,
                    CircleShape
                )
                .border(
                    if (isSpeaking) 2.dp else 0.dp,
                    Color.Green,
                    CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = user.name.take(2).uppercase(),
                color = Color.White,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold
            )
        }
        
        Spacer(modifier = Modifier.height(4.dp))
        
        Text(
            text = user.name,
            style = MaterialTheme.typography.bodySmall,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun InfoRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Medium
        )
    }
}

// Helper Functions
fun DrawScope.drawCursor(
    position: Offset,
    color: Color,
    userName: String
) {
    // Draw cursor pointer
    val cursorPath = androidx.compose.ui.graphics.Path().apply {
        moveTo(position.x, position.y)
        lineTo(position.x + 10, position.y + 10)
        lineTo(position.x + 5, position.y + 15)
        lineTo(position.x, position.y + 12)
        close()
    }
    
    drawPath(
        path = cursorPath,
        color = color
    )
    
    // Draw user name label
    drawRect(
        color = color,
        topLeft = Offset(position.x + 15, position.y - 5),
        size = androidx.compose.ui.geometry.Size(userName.length * 8f, 20f)
    )
}

fun getEventIcon(type: CollaborationEventType): ImageVector {
    return when (type) {
        CollaborationEventType.USER_JOINED -> Icons.Default.PersonAdd
        CollaborationEventType.USER_LEFT -> Icons.Default.PersonRemove
        CollaborationEventType.CURSOR_MOVED -> Icons.Default.Mouse
        CollaborationEventType.TEXT_EDITED -> Icons.Default.Edit
        CollaborationEventType.SELECTION_CHANGED -> Icons.Default.SelectAll
        CollaborationEventType.COMMENT_ADDED -> Icons.Default.Comment
        CollaborationEventType.FILE_OPENED -> Icons.Default.FolderOpen
        CollaborationEventType.VOICE_STARTED -> Icons.Default.Mic
        CollaborationEventType.VOICE_ENDED -> Icons.Default.MicOff
        CollaborationEventType.SCREEN_SHARED -> Icons.Default.ScreenShare
        CollaborationEventType.ANNOTATION_ADDED -> Icons.Default.Note
    }
}

fun getEventColor(type: CollaborationEventType): Color {
    return when (type) {
        CollaborationEventType.USER_JOINED -> Color.Green
        CollaborationEventType.USER_LEFT -> Color.Red
        CollaborationEventType.CURSOR_MOVED -> Color.Blue
        CollaborationEventType.TEXT_EDITED -> Orange
        CollaborationEventType.SELECTION_CHANGED -> Purple
        CollaborationEventType.COMMENT_ADDED -> Color.Cyan
        CollaborationEventType.FILE_OPENED -> Color.Gray
        CollaborationEventType.VOICE_STARTED -> Color.Green
        CollaborationEventType.VOICE_ENDED -> Color.Red
        CollaborationEventType.SCREEN_SHARED -> Color.Blue
        CollaborationEventType.ANNOTATION_ADDED -> Color.Yellow
    }
}

fun getEventDescription(event: CollaborationEvent, userName: String): String {
    return when (event.type) {
        CollaborationEventType.USER_JOINED -> "$userName joined the session"
        CollaborationEventType.USER_LEFT -> "$userName left the session"
        CollaborationEventType.CURSOR_MOVED -> "$userName moved cursor"
        CollaborationEventType.TEXT_EDITED -> "$userName edited text"
        CollaborationEventType.SELECTION_CHANGED -> "$userName changed selection"
        CollaborationEventType.COMMENT_ADDED -> "$userName added a comment"
        CollaborationEventType.FILE_OPENED -> "$userName opened a file"
        CollaborationEventType.VOICE_STARTED -> "$userName started voice chat"
        CollaborationEventType.VOICE_ENDED -> "$userName ended voice chat"
        CollaborationEventType.SCREEN_SHARED -> "$userName shared screen"
        CollaborationEventType.ANNOTATION_ADDED -> "$userName added annotation"
    }
}

fun formatTimestamp(timestamp: Long): String {
    val sdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    return sdf.format(Date(timestamp))
}

// Sample Data Generators
fun generateSampleSession(): CollaborationSession {
    return CollaborationSession(
        id = "session_001",
        name = "Mobile App Design Review",
        mode = CollaborationMode.REVIEW,
        owner = "user_001",
        participants = generateSampleUsers(),
        startTime = System.currentTimeMillis() - 3600000, // 1 hour ago
        isActive = true,
        sharedFiles = listOf("MainActivity.kt", "UserProfile.kt", "design_mockup.png")
    )
}

fun generateSampleUsers(): List<CollaborationUser> {
    return listOf(
        CollaborationUser(
            id = "user_001",
            name = "Alice Johnson",
            avatar = "avatar_1",
            status = UserPresenceStatus.ONLINE,
            permission = PermissionLevel.OWNER,
            cursorPosition = Offset(100f, 150f),
            cursorColor = Color.Blue,
            isTyping = false,
            currentFile = "MainActivity.kt"
        ),
        CollaborationUser(
            id = "user_002",
            name = "Bob Smith",
            avatar = "avatar_2",
            status = UserPresenceStatus.ONLINE,
            permission = PermissionLevel.EDITOR,
            cursorPosition = Offset(200f, 250f),
            cursorColor = Color.Red,
            isTyping = true,
            currentFile = "UserProfile.kt"
        ),
        CollaborationUser(
            id = "user_003",
            name = "Carol Davis",
            avatar = "avatar_3",
            status = UserPresenceStatus.AWAY,
            permission = PermissionLevel.REVIEWER,
            cursorPosition = Offset(150f, 300f),
            cursorColor = Color.Green,
            isTyping = false,
            currentFile = null
        ),
        CollaborationUser(
            id = "user_004",
            name = "David Wilson",
            avatar = "avatar_4",
            status = UserPresenceStatus.BUSY,
            permission = PermissionLevel.VIEWER,
            cursorPosition = Offset(300f, 100f),
            cursorColor = Purple,
            isTyping = false,
            currentFile = "design_mockup.png"
        )
    )
}

fun generateSampleEvents(): List<CollaborationEvent> {
    val now = System.currentTimeMillis()
    return listOf(
        CollaborationEvent(
            id = "event_001",
            type = CollaborationEventType.USER_JOINED,
            userId = "user_002",
            timestamp = now - 1800000 // 30 minutes ago
        ),
        CollaborationEvent(
            id = "event_002",
            type = CollaborationEventType.FILE_OPENED,
            userId = "user_001",
            timestamp = now - 1500000 // 25 minutes ago
        ),
        CollaborationEvent(
            id = "event_003",
            type = CollaborationEventType.TEXT_EDITED,
            userId = "user_002",
            timestamp = now - 1200000 // 20 minutes ago
        ),
        CollaborationEvent(
            id = "event_004",
            type = CollaborationEventType.COMMENT_ADDED,
            userId = "user_003",
            timestamp = now - 900000 // 15 minutes ago
        ),
        CollaborationEvent(
            id = "event_005",
            type = CollaborationEventType.VOICE_STARTED,
            userId = "user_001",
            timestamp = now - 600000 // 10 minutes ago
        )
    )
}

fun generateSampleComments(): List<CollaborationComment> {
    val now = System.currentTimeMillis()
    return listOf(
        CollaborationComment(
            id = "comment_001",
            userId = "user_003",
            content = "This function could be optimized for better performance. Consider using lazy loading.",
            timestamp = now - 900000, // 15 minutes ago
            position = Offset(200f, 300f),
            resolved = false,
            replies = listOf(
                CollaborationComment(
                    id = "reply_001",
                    userId = "user_001",
                    content = "Good point! I'll implement that in the next iteration.",
                    timestamp = now - 600000, // 10 minutes ago
                    position = Offset(200f, 320f)
                )
            )
        ),
        CollaborationComment(
            id = "comment_002",
            userId = "user_004",
            content = "The UI design looks great, but we should consider accessibility guidelines.",
            timestamp = now - 1200000, // 20 minutes ago
            position = Offset(150f, 200f),
            resolved = true
        ),
        CollaborationComment(
            id = "comment_003",
            userId = "user_002",
            content = "Should we add error handling for network requests here?",
            timestamp = now - 300000, // 5 minutes ago
            position = Offset(250f, 400f),
            resolved = false
        )
    )
}

fun generateSampleConflicts(): List<CollaborationConflict> {
    val now = System.currentTimeMillis()
    return listOf(
        CollaborationConflict(
            id = "conflict_001",
            type = "Merge Conflict",
            description = "Both Alice and Bob modified the same function simultaneously. Manual resolution required.",
            affectedUsers = listOf("user_001", "user_002"),
            timestamp = now - 600000, // 10 minutes ago
            resolved = false,
            resolutionStrategy = ConflictResolutionStrategy.MANUAL_RESOLUTION
        ),
        CollaborationConflict(
            id = "conflict_002",
            type = "Permission Conflict",
            description = "User attempted to edit file without proper permissions.",
            affectedUsers = listOf("user_004"),
            timestamp = now - 1800000, // 30 minutes ago
            resolved = true,
            resolutionStrategy = ConflictResolutionStrategy.LAST_WRITE_WINS
        )
    )
}