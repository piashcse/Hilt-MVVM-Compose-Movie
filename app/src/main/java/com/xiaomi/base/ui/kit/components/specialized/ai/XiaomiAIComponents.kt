package com.xiaomi.base.ui.kit.components.specialized.ai

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.xiaomi.base.ui.kit.foundation.XiaomiTheme
import kotlinx.coroutines.delay

/**
 * Xiaomi AI Components - AI-powered UI Components
 * 
 * This file contains AI-related components following Material Design 3 principles
 * with Xiaomi's design language. These components are designed for modern AI
 * interactions and chat interfaces.
 */

// Data Classes
data class XiaomiChatMessage(
    val id: String,
    val content: String,
    val isFromUser: Boolean,
    val timestamp: Long = System.currentTimeMillis(),
    val status: MessageStatus = MessageStatus.SENT,
    val attachments: List<ChatAttachment> = emptyList()
)

data class ChatAttachment(
    val id: String,
    val type: AttachmentType,
    val url: String,
    val name: String
)

enum class MessageStatus {
    SENDING, SENT, DELIVERED, READ, ERROR
}

enum class AttachmentType {
    IMAGE, FILE, VOICE, VIDEO
}

data class XiaomiAISuggestion(
    val id: String,
    val text: String,
    val icon: ImageVector? = null
)

/**
 * Xiaomi AI Chat Interface
 * A complete chat interface designed for AI interactions
 */
@Composable
fun XiaomiAIChatInterface(
    messages: List<XiaomiChatMessage>,
    onSendMessage: (String) -> Unit,
    onSuggestionClick: (XiaomiAISuggestion) -> Unit = {},
    suggestions: List<XiaomiAISuggestion> = emptyList(),
    isTyping: Boolean = false,
    modifier: Modifier = Modifier,
    placeholder: String = "Ask me anything...",
    aiName: String = "Xiaomi AI",
    aiAvatar: ImageVector = Icons.Default.SmartToy
) {
    var inputText by remember { mutableStateOf("") }
    val listState = rememberLazyListState()
    val keyboardController = LocalSoftwareKeyboardController.current
    
    // Auto scroll to bottom when new messages arrive
    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }
    
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        // Chat Messages
        LazyColumn(
            state = listState,
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(messages) { message ->
                XiaomiChatMessageBubble(
                    message = message,
                    aiName = aiName,
                    aiAvatar = aiAvatar
                )
            }
            
            // Typing indicator
            if (isTyping) {
                item {
                    XiaomiTypingIndicator(
                        aiName = aiName,
                        aiAvatar = aiAvatar
                    )
                }
            }
        }
        
        // Suggestions
        if (suggestions.isNotEmpty() && inputText.isEmpty()) {
            XiaomiAISuggestions(
                suggestions = suggestions,
                onSuggestionClick = onSuggestionClick,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
        
        // Input Area
        XiaomiChatInput(
            value = inputText,
            onValueChange = { inputText = it },
            onSend = {
                if (inputText.isNotBlank()) {
                    onSendMessage(inputText)
                    inputText = ""
                    keyboardController?.hide()
                }
            },
            placeholder = placeholder,
            modifier = Modifier.padding(16.dp)
        )
    }
}

/**
 * Xiaomi Chat Message Bubble
 * Individual message bubble with proper styling
 */
@Composable
fun XiaomiChatMessageBubble(
    message: XiaomiChatMessage,
    aiName: String = "Xiaomi AI",
    aiAvatar: ImageVector = Icons.Default.SmartToy,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = if (message.isFromUser) {
            Arrangement.End
        } else {
            Arrangement.Start
        }
    ) {
        if (!message.isFromUser) {
            // AI Avatar
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = aiAvatar,
                    contentDescription = aiName,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(20.dp)
                )
            }
            
            Spacer(modifier = Modifier.width(8.dp))
        }
        
        Column(
            modifier = Modifier.widthIn(max = 280.dp)
        ) {
            // Message Bubble
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(
                    topStart = if (message.isFromUser) 16.dp else 4.dp,
                    topEnd = if (message.isFromUser) 4.dp else 16.dp,
                    bottomStart = 16.dp,
                    bottomEnd = 16.dp
                ),
                colors = CardDefaults.cardColors(
                    containerColor = if (message.isFromUser) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.surfaceVariant
                    }
                )
            ) {
                Text(
                    text = message.content,
                    modifier = Modifier.padding(12.dp),
                    color = if (message.isFromUser) {
                        MaterialTheme.colorScheme.onPrimary
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    },
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            
            // Message Status
            if (message.isFromUser) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    XiaomiMessageStatus(status = message.status)
                }
            }
        }
        
        if (message.isFromUser) {
            Spacer(modifier = Modifier.width(8.dp))
            
            // User Avatar
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.secondary),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "User",
                    tint = MaterialTheme.colorScheme.onSecondary,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

/**
 * Xiaomi Typing Indicator
 * Shows when AI is typing
 */
@Composable
fun XiaomiTypingIndicator(
    aiName: String = "Xiaomi AI",
    aiAvatar: ImageVector = Icons.Default.SmartToy,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // AI Avatar
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = aiAvatar,
                contentDescription = aiName,
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.size(20.dp)
            )
        }
        
        Spacer(modifier = Modifier.width(8.dp))
        
        // Typing Animation
        Card(
            shape = RoundedCornerShape(
                topStart = 4.dp,
                topEnd = 16.dp,
                bottomStart = 16.dp,
                bottomEnd = 16.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(3) { index ->
                    var isVisible by remember { mutableStateOf(false) }
                    
                    LaunchedEffect(Unit) {
                        delay(index * 200L)
                        while (true) {
                            isVisible = true
                            delay(600)
                            isVisible = false
                            delay(600)
                        }
                    }
                    
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .clip(CircleShape)
                            .background(
                                if (isVisible) {
                                    MaterialTheme.colorScheme.onSurfaceVariant
                                } else {
                                    MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f)
                                }
                            )
                    )
                }
            }
        }
    }
}

/**
 * Xiaomi Chat Input
 * Input field for sending messages
 */
@Composable
fun XiaomiChatInput(
    value: String,
    onValueChange: (String) -> Unit,
    onSend: () -> Unit,
    placeholder: String = "Type a message...",
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.weight(1f),
            placeholder = {
                Text(
                    text = placeholder,
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            shape = RoundedCornerShape(24.dp),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Send
            ),
            keyboardActions = KeyboardActions(
                onSend = { onSend() }
            ),
            maxLines = 4
        )
        
        FloatingActionButton(
            onClick = onSend,
            modifier = Modifier.size(48.dp),
            containerColor = if (value.isNotBlank()) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.surfaceVariant
            }
        ) {
            Icon(
                imageVector = Icons.Default.Send,
                contentDescription = "Send",
                tint = if (value.isNotBlank()) {
                    MaterialTheme.colorScheme.onPrimary
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                }
            )
        }
    }
}

/**
 * Xiaomi AI Suggestions
 * Quick suggestion chips
 */
@Composable
fun XiaomiAISuggestions(
    suggestions: List<XiaomiAISuggestion>,
    onSuggestionClick: (XiaomiAISuggestion) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Suggestions",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.heightIn(max = 120.dp)
        ) {
            items(suggestions) { suggestion ->
                XiaomiSuggestionChip(
                    suggestion = suggestion,
                    onClick = { onSuggestionClick(suggestion) }
                )
            }
        }
    }
}

/**
 * Xiaomi Suggestion Chip
 * Individual suggestion chip
 */
@Composable
fun XiaomiSuggestionChip(
    suggestion: XiaomiAISuggestion,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            suggestion.icon?.let { icon ->
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            
            Text(
                text = suggestion.text,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f)
            )
            
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "Send",
                modifier = Modifier.size(16.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

/**
 * Xiaomi Message Status
 * Shows message delivery status
 */
@Composable
fun XiaomiMessageStatus(
    status: MessageStatus,
    modifier: Modifier = Modifier
) {
    val (icon, color) = when (status) {
        MessageStatus.SENDING -> Icons.Default.Schedule to MaterialTheme.colorScheme.onSurfaceVariant
        MessageStatus.SENT -> Icons.Default.Done to MaterialTheme.colorScheme.onSurfaceVariant
        MessageStatus.DELIVERED -> Icons.Default.DoneAll to MaterialTheme.colorScheme.onSurfaceVariant
        MessageStatus.READ -> Icons.Default.DoneAll to MaterialTheme.colorScheme.primary
        MessageStatus.ERROR -> Icons.Default.Error to MaterialTheme.colorScheme.error
    }
    
    Icon(
        imageVector = icon,
        contentDescription = status.name,
        modifier = modifier.size(12.dp),
        tint = color
    )
}

/**
 * Xiaomi AI Assistant Card
 * Compact AI assistant interface
 */
@Composable
fun XiaomiAIAssistantCard(
    title: String = "Xiaomi AI Assistant",
    subtitle: String = "How can I help you today?",
    onStartChat: () -> Unit,
    suggestions: List<String> = emptyList(),
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.SmartToy,
                        contentDescription = title,
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(24.dp)
                    )
                }
                
                Column {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                    )
                }
            }
            
            if (suggestions.isNotEmpty()) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    suggestions.take(3).forEach { suggestion ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onStartChat() },
                            shape = RoundedCornerShape(8.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)
                            )
                        ) {
                            Text(
                                text = suggestion,
                                modifier = Modifier.padding(12.dp),
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }
            
            Button(
                onClick = onStartChat,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Chat,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Start Conversation")
            }
        }
    }
}

// Preview Functions
@Preview(name = "AI Chat Interface - Light")
@Composable
fun XiaomiAIChatInterfacePreview() {
    XiaomiTheme {
        val sampleMessages = listOf(
            XiaomiChatMessage(
                id = "1",
                content = "Hello! How can I help you today?",
                isFromUser = false
            ),
            XiaomiChatMessage(
                id = "2",
                content = "I need help with my Xiaomi device settings",
                isFromUser = true,
                status = MessageStatus.READ
            ),
            XiaomiChatMessage(
                id = "3",
                content = "I'd be happy to help you with your device settings. What specific setting would you like to adjust?",
                isFromUser = false
            )
        )
        
        val suggestions = listOf(
            XiaomiAISuggestion("1", "How to enable dark mode?", Icons.Default.DarkMode),
            XiaomiAISuggestion("2", "Battery optimization tips", Icons.Default.Battery6Bar),
            XiaomiAISuggestion("3", "Camera settings guide", Icons.Default.CameraAlt)
        )
        
        XiaomiAIChatInterface(
            messages = sampleMessages,
            onSendMessage = {},
            suggestions = suggestions,
            isTyping = true
        )
    }
}

@Preview(name = "AI Chat Interface - Dark")
@Composable
fun XiaomiAIChatInterfaceDarkPreview() {
    XiaomiTheme(darkTheme = true) {
        val sampleMessages = listOf(
            XiaomiChatMessage(
                id = "1",
                content = "Hello! How can I help you today?",
                isFromUser = false
            ),
            XiaomiChatMessage(
                id = "2",
                content = "I need help with my Xiaomi device settings",
                isFromUser = true,
                status = MessageStatus.DELIVERED
            )
        )
        
        XiaomiAIChatInterface(
            messages = sampleMessages,
            onSendMessage = {},
            isTyping = false
        )
    }
}

@Preview(name = "AI Assistant Card - Light")
@Composable
fun XiaomiAIAssistantCardPreview() {
    XiaomiTheme {
        XiaomiAIAssistantCard(
            onStartChat = {},
            suggestions = listOf(
                "How to optimize battery life?",
                "Camera tips and tricks",
                "Security settings guide"
            )
        )
    }
}

@Preview(name = "AI Assistant Card - Dark")
@Composable
fun XiaomiAIAssistantCardDarkPreview() {
    XiaomiTheme(darkTheme = true) {
        XiaomiAIAssistantCard(
            onStartChat = {},
            suggestions = listOf(
                "How to optimize battery life?",
                "Camera tips and tricks",
                "Security settings guide"
            )
        )
    }
}