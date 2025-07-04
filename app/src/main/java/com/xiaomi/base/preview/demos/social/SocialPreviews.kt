package com.xiaomi.base.preview.demos.social

import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.xiaomi.base.preview.catalog.PreviewCategory
import com.xiaomi.base.preview.catalog.PreviewDifficulty
import com.xiaomi.base.preview.catalog.PreviewItem
import com.xiaomi.base.preview.catalog.PreviewRegistry
import com.xiaomi.base.ui.theme.BaseAppTheme

// Data classes for Social demos
data class User(
    val id: String,
    val username: String,
    val displayName: String,
    val avatar: Color,
    val bio: String = "",
    val followers: Int = 0,
    val following: Int = 0,
    val posts: Int = 0,
    val isVerified: Boolean = false,
    val isFollowing: Boolean = false,
    val isOnline: Boolean = false
)

data class Post(
    val id: String,
    val user: User,
    val content: String,
    val timestamp: String,
    val likes: Int,
    val comments: Int,
    val shares: Int,
    val isLiked: Boolean = false,
    val isBookmarked: Boolean = false,
    val images: List<Color> = emptyList(),
    val location: String? = null
)

data class Story(
    val id: String,
    val user: User,
    val thumbnail: Color,
    val isViewed: Boolean = false,
    val isLive: Boolean = false
)

data class Message(
    val id: String,
    val senderId: String,
    val content: String,
    val timestamp: String,
    val isRead: Boolean = false,
    val messageType: MessageType = MessageType.TEXT
)

enum class MessageType {
    TEXT, IMAGE, VOICE, VIDEO, FILE
}

data class Chat(
    val id: String,
    val participants: List<User>,
    val lastMessage: Message?,
    val unreadCount: Int = 0,
    val isGroup: Boolean = false,
    val groupName: String? = null,
    val groupAvatar: Color? = null
)

data class Notification(
    val id: String,
    val type: NotificationType,
    val user: User?,
    val content: String,
    val timestamp: String,
    val isRead: Boolean = false,
    val actionData: String? = null
)

enum class NotificationType {
    LIKE, COMMENT, FOLLOW, MENTION, MESSAGE, POST
}

// Social Feed Preview
@Preview(name = "Social Feed", showBackground = true)
@Composable
fun SocialFeedPreview() {
    BaseAppTheme {
        SocialFeed()
    }
}

@Composable
fun SocialFeed() {
    val currentUser = User(
        id = "current",
        username = "johndoe",
        displayName = "John Doe",
        avatar = Color(0xFF2196F3)
    )
    
    val stories = listOf(
        Story("1", User("1", "alice", "Alice Smith", Color(0xFFE91E63)), Color(0xFFE91E63)),
        Story("2", User("2", "bob", "Bob Johnson", Color(0xFF4CAF50)), Color(0xFF4CAF50), isLive = true),
        Story("3", User("3", "carol", "Carol Wilson", Color(0xFFFF9800)), Color(0xFFFF9800), isViewed = true),
        Story("4", User("4", "david", "David Brown", Color(0xFF9C27B0)), Color(0xFF9C27B0))
    )
    
    val posts = listOf(
        Post(
            id = "1",
            user = User("1", "alice", "Alice Smith", Color(0xFFE91E63), isVerified = true),
            content = "Just finished an amazing hike in the mountains! The view was absolutely breathtaking. Nature never fails to inspire me. 🏔️ #hiking #nature #adventure",
            timestamp = "2 hours ago",
            likes = 124,
            comments = 18,
            shares = 5,
            isLiked = true,
            images = listOf(Color(0xFF4CAF50), Color(0xFF2196F3)),
            location = "Rocky Mountain National Park"
        ),
        Post(
            id = "2",
            user = User("2", "bob", "Bob Johnson", Color(0xFF4CAF50)),
            content = "Working on a new project today. Excited to share the results soon! 💻 #coding #development #tech",
            timestamp = "4 hours ago",
            likes = 89,
            comments = 12,
            shares = 3,
            isBookmarked = true
        ),
        Post(
            id = "3",
            user = User("3", "carol", "Carol Wilson", Color(0xFFFF9800), isVerified = true),
            content = "Beautiful sunset from my balcony tonight. Sometimes the best moments are right at home. 🌅",
            timestamp = "6 hours ago",
            likes = 256,
            comments = 34,
            shares = 12,
            images = listOf(Color(0xFFFF5722))
        )
    )
    
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            SocialFeedHeader(currentUser)
        }
        
        item {
            StoriesSection(stories)
        }
        
        item {
            CreatePostSection(currentUser)
        }
        
        items(posts) { post ->
            PostCard(post)
        }
    }
}

// User Profile Preview
@Preview(name = "User Profile", showBackground = true)
@Composable
fun UserProfilePreview() {
    BaseAppTheme {
        UserProfile()
    }
}

@Composable
fun UserProfile() {
    val user = User(
        id = "1",
        username = "alice_smith",
        displayName = "Alice Smith",
        avatar = Color(0xFFE91E63),
        bio = "📸 Photography enthusiast\n🌍 Travel lover\n☕ Coffee addict\n📍 San Francisco, CA",
        followers = 12500,
        following = 890,
        posts = 342,
        isVerified = true,
        isFollowing = false
    )
    
    val userPosts = listOf(
        Color(0xFF4CAF50), Color(0xFF2196F3), Color(0xFFFF9800),
        Color(0xFF9C27B0), Color(0xFFF44336), Color(0xFF607D8B),
        Color(0xFF795548), Color(0xFF3F51B5), Color(0xFF009688)
    )
    
    val highlights = listOf(
        "Travel" to Color(0xFF4CAF50),
        "Food" to Color(0xFFFF9800),
        "Work" to Color(0xFF2196F3),
        "Friends" to Color(0xFFE91E63)
    )
    
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            ProfileHeader(user)
        }
        
        item {
            ProfileStats(user)
        }
        
        item {
            ProfileBio(user)
        }
        
        item {
            ProfileActions(user)
        }
        
        item {
            HighlightsSection(highlights)
        }
        
        item {
            ProfileTabsSection()
        }
        
        item {
            PostsGrid(userPosts)
        }
    }
}

// Messaging Interface Preview
@Preview(name = "Messaging Interface", showBackground = true)
@Composable
fun MessagingInterfacePreview() {
    BaseAppTheme {
        MessagingInterface()
    }
}

@Composable
fun MessagingInterface() {
    val chats = listOf(
        Chat(
            id = "1",
            participants = listOf(
                User("1", "alice", "Alice Smith", Color(0xFFE91E63), isOnline = true)
            ),
            lastMessage = Message(
                id = "1",
                senderId = "1",
                content = "Hey! How are you doing?",
                timestamp = "2 min ago",
                isRead = false
            ),
            unreadCount = 2
        ),
        Chat(
            id = "2",
            participants = listOf(
                User("2", "bob", "Bob Johnson", Color(0xFF4CAF50))
            ),
            lastMessage = Message(
                id = "2",
                senderId = "current",
                content = "Thanks for the help!",
                timestamp = "1 hour ago",
                isRead = true
            )
        ),
        Chat(
            id = "3",
            participants = listOf(
                User("3", "carol", "Carol Wilson", Color(0xFFFF9800)),
                User("4", "david", "David Brown", Color(0xFF9C27B0)),
                User("5", "eve", "Eve Davis", Color(0xFF607D8B))
            ),
            lastMessage = Message(
                id = "3",
                senderId = "3",
                content = "Let's meet tomorrow at 3 PM",
                timestamp = "3 hours ago",
                isRead = true
            ),
            isGroup = true,
            groupName = "Project Team",
            groupAvatar = Color(0xFF2196F3),
            unreadCount = 5
        )
    )
    
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            MessagingHeader()
        }
        
        item {
            ActiveUsersSection()
        }
        
        items(chats) { chat ->
            ChatItem(chat)
        }
    }
}

// Chat Detail Preview
@Preview(name = "Chat Detail", showBackground = true)
@Composable
fun ChatDetailPreview() {
    BaseAppTheme {
        ChatDetail()
    }
}

@Composable
fun ChatDetail() {
    val otherUser = User(
        id = "1",
        username = "alice",
        displayName = "Alice Smith",
        avatar = Color(0xFFE91E63),
        isOnline = true
    )
    
    val messages = listOf(
        Message(
            id = "1",
            senderId = "1",
            content = "Hey! How are you doing?",
            timestamp = "10:30 AM",
            isRead = true
        ),
        Message(
            id = "2",
            senderId = "current",
            content = "I'm doing great! Just finished a big project at work. How about you?",
            timestamp = "10:32 AM",
            isRead = true
        ),
        Message(
            id = "3",
            senderId = "1",
            content = "That's awesome! I'm good too. Working on some new photography projects.",
            timestamp = "10:35 AM",
            isRead = true
        ),
        Message(
            id = "4",
            senderId = "1",
            content = "Would love to show you some of my recent shots sometime!",
            timestamp = "10:35 AM",
            isRead = true
        ),
        Message(
            id = "5",
            senderId = "current",
            content = "I'd love to see them! Are you free this weekend?",
            timestamp = "10:40 AM",
            isRead = false
        )
    )
    
    var messageText by remember { mutableStateOf("") }
    
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        ChatHeader(otherUser)
        
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            reverseLayout = true
        ) {
            items(messages.reversed()) { message ->
                MessageBubble(
                    message = message,
                    isFromCurrentUser = message.senderId == "current",
                    otherUser = otherUser
                )
            }
        }
        
        MessageInput(
            messageText = messageText,
            onMessageTextChange = { messageText = it },
            onSendMessage = {
                // Handle send message
                messageText = ""
            }
        )
    }
}

// Notifications Preview
@Preview(name = "Notifications", showBackground = true)
@Composable
fun NotificationsPreview() {
    BaseAppTheme {
        Notifications()
    }
}

@Composable
fun Notifications() {
    val notifications = listOf(
        Notification(
            id = "1",
            type = NotificationType.LIKE,
            user = User("1", "alice", "Alice Smith", Color(0xFFE91E63)),
            content = "liked your photo",
            timestamp = "2 min ago",
            isRead = false
        ),
        Notification(
            id = "2",
            type = NotificationType.COMMENT,
            user = User("2", "bob", "Bob Johnson", Color(0xFF4CAF50)),
            content = "commented on your post: \"Great shot!\"",
            timestamp = "15 min ago",
            isRead = false
        ),
        Notification(
            id = "3",
            type = NotificationType.FOLLOW,
            user = User("3", "carol", "Carol Wilson", Color(0xFFFF9800)),
            content = "started following you",
            timestamp = "1 hour ago",
            isRead = true
        ),
        Notification(
            id = "4",
            type = NotificationType.MENTION,
            user = User("4", "david", "David Brown", Color(0xFF9C27B0)),
            content = "mentioned you in a comment",
            timestamp = "2 hours ago",
            isRead = true
        ),
        Notification(
            id = "5",
            type = NotificationType.POST,
            user = User("5", "eve", "Eve Davis", Color(0xFF607D8B)),
            content = "shared a new post",
            timestamp = "4 hours ago",
            isRead = true
        )
    )
    
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            NotificationsHeader()
        }
        
        items(notifications) { notification ->
            NotificationItem(notification)
        }
    }
}

// Helper Composables
@Composable
fun SocialFeedHeader(currentUser: User) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Social",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search"
                )
            }
            
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Notifications"
                )
            }
            
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Default.Message,
                    contentDescription = "Messages"
                )
            }
        }
    }
}

@Composable
fun StoriesSection(stories: List<Story>) {
    LazyRow(
        modifier = Modifier.padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(stories) { story ->
            StoryItem(story)
        }
    }
}

@Composable
fun StoryItem(story: Story) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box {
            Surface(
                shape = CircleShape,
                color = if (story.isViewed) {
                    MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                } else {
                    MaterialTheme.colorScheme.primary
                },
                modifier = Modifier.size(66.dp)
            ) {
                Surface(
                    shape = CircleShape,
                    color = story.thumbnail,
                    modifier = Modifier
                        .size(60.dp)
                        .padding(3.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = story.user.displayName.split(" ").map { it.first() }.joinToString(""),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
            
            if (story.isLive) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color.Red,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .offset(y = 4.dp)
                ) {
                    Text(
                        text = "LIVE",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.height(4.dp))
        
        Text(
            text = story.user.username,
            style = MaterialTheme.typography.bodySmall,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.width(70.dp),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun CreatePostSection(currentUser: User) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = CircleShape,
                color = currentUser.avatar,
                modifier = Modifier.size(40.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = currentUser.displayName.split(" ").map { it.first() }.joinToString(""),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Text(
                text = "What's on your mind?",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .weight(1f)
                    .clickable { }
            )
            
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Default.PhotoCamera,
                    contentDescription = "Add Photo",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
fun PostCard(post: Post) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Column {
            // Post Header
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = CircleShape,
                    color = post.user.avatar,
                    modifier = Modifier.size(40.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = post.user.displayName.split(" ").map { it.first() }.joinToString(""),
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
                
                Spacer(modifier = Modifier.width(12.dp))
                
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = post.user.displayName,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                        
                        if (post.user.isVerified) {
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                imageVector = Icons.Default.Verified,
                                contentDescription = "Verified",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                    
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = post.timestamp,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        
                        if (post.location != null) {
                            Text(
                                text = " • ",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = "Location",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(12.dp)
                            )
                            
                            Text(
                                text = post.location,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
                
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "More"
                    )
                }
            }
            
            // Post Content
            Text(
                text = post.content,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            
            // Post Images
            if (post.images.isNotEmpty()) {
                Spacer(modifier = Modifier.height(12.dp))
                
                when (post.images.size) {
                    1 -> {
                        Surface(
                            color = post.images[0],
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                        ) {}
                    }
                    2 -> {
                        Row(
                            modifier = Modifier.height(200.dp)
                        ) {
                            Surface(
                                color = post.images[0],
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight()
                            ) {}
                            
                            Spacer(modifier = Modifier.width(2.dp))
                            
                            Surface(
                                color = post.images[1],
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight()
                            ) {}
                        }
                    }
                }
            }
            
            // Post Actions
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    PostActionButton(
                        icon = if (post.isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        count = post.likes,
                        isActive = post.isLiked,
                        onClick = { }
                    )
                    
                    PostActionButton(
                        icon = Icons.Default.ChatBubbleOutline,
                        count = post.comments,
                        onClick = { }
                    )
                    
                    PostActionButton(
                        icon = Icons.Default.Share,
                        count = post.shares,
                        onClick = { }
                    )
                }
                
                IconButton(
                    onClick = { }
                ) {
                    Icon(
                        imageVector = if (post.isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                        contentDescription = "Bookmark",
                        tint = if (post.isBookmarked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
fun PostActionButton(
    icon: ImageVector,
    count: Int,
    isActive: Boolean = false,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable { onClick() }
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = if (isActive) Color.Red else MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(20.dp)
        )
        
        Spacer(modifier = Modifier.width(4.dp))
        
        Text(
            text = count.toString(),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun ProfileHeader(user: User) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = user.username,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            
            if (user.isVerified) {
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Default.Verified,
                    contentDescription = "Verified",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
        
        Row {
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add"
                )
            }
            
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "More"
                )
            }
        }
    }
}

@Composable
fun ProfileStats(user: User) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Surface(
            shape = CircleShape,
            color = user.avatar,
            modifier = Modifier.size(80.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = user.displayName.split(" ").map { it.first() }.joinToString(""),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
        
        ProfileStatItem("Posts", user.posts)
        ProfileStatItem("Followers", user.followers)
        ProfileStatItem("Following", user.following)
    }
}

@Composable
fun ProfileStatItem(label: String, count: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = when {
                count >= 1000000 -> "${count / 1000000}M"
                count >= 1000 -> "${count / 1000}K"
                else -> count.toString()
            },
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun ProfileBio(user: User) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = user.displayName,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold
        )
        
        if (user.bio.isNotEmpty()) {
            Spacer(modifier = Modifier.height(4.dp))
            
            Text(
                text = user.bio,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun ProfileActions(user: User) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (user.isFollowing) {
            OutlinedButton(
                onClick = { },
                modifier = Modifier.weight(1f)
            ) {
                Text("Following")
            }
        } else {
            Button(
                onClick = { },
                modifier = Modifier.weight(1f)
            ) {
                Text("Follow")
            }
        }
        
        OutlinedButton(
            onClick = { },
            modifier = Modifier.weight(1f)
        ) {
            Text("Message")
        }
        
        OutlinedButton(
            onClick = { }
        ) {
            Icon(
                imageVector = Icons.Default.PersonAdd,
                contentDescription = "Add Friend"
            )
        }
    }
}

@Composable
fun HighlightsSection(highlights: List<Pair<String, Color>>) {
    LazyRow(
        modifier = Modifier.padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(highlights) { (title, color) ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Surface(
                    shape = CircleShape,
                    color = color,
                    modifier = Modifier.size(64.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = title.first().toString(),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun ProfileTabsSection() {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Posts", "Reels", "Tagged")
    
    TabRow(
        selectedTabIndex = selectedTab,
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        tabs.forEachIndexed { index, title ->
            Tab(
                selected = selectedTab == index,
                onClick = { selectedTab = index },
                text = { Text(title) }
            )
        }
    }
}

@Composable
fun PostsGrid(posts: List<Color>) {
    val chunkedPosts = posts.chunked(3)
    
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        chunkedPosts.forEach { rowPosts ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                rowPosts.forEach { color ->
                    Surface(
                        color = color,
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                    ) {}
                }
                
                // Fill remaining space if row is not complete
                repeat(3 - rowPosts.size) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
            
            Spacer(modifier = Modifier.height(2.dp))
        }
    }
}

@Composable
fun MessagingHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Messages",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        
        Row {
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search"
                )
            }
            
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "New Message"
                )
            }
        }
    }
}

@Composable
fun ActiveUsersSection() {
    val activeUsers = listOf(
        User("1", "alice", "Alice", Color(0xFFE91E63), isOnline = true),
        User("2", "bob", "Bob", Color(0xFF4CAF50), isOnline = true),
        User("3", "carol", "Carol", Color(0xFFFF9800), isOnline = true),
        User("4", "david", "David", Color(0xFF9C27B0), isOnline = true)
    )
    
    LazyRow(
        modifier = Modifier.padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(activeUsers) { user ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box {
                    Surface(
                        shape = CircleShape,
                        color = user.avatar,
                        modifier = Modifier.size(56.dp)
                    ) {
                        Box(
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = user.displayName.split(" ").map { it.first() }.joinToString(""),
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                    
                    Surface(
                        shape = CircleShape,
                        color = Color(0xFF4CAF50),
                        modifier = Modifier
                            .size(16.dp)
                            .align(Alignment.BottomEnd)
                    ) {}
                }
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = user.displayName,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.width(60.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun ChatItem(chat: Chat) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box {
            if (chat.isGroup) {
                Surface(
                    shape = CircleShape,
                    color = chat.groupAvatar ?: Color.Gray,
                    modifier = Modifier.size(56.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Group,
                            contentDescription = "Group",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            } else {
                val user = chat.participants.first()
                Surface(
                    shape = CircleShape,
                    color = user.avatar,
                    modifier = Modifier.size(56.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = user.displayName.split(" ").map { it.first() }.joinToString(""),
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
                
                if (user.isOnline) {
                    Surface(
                        shape = CircleShape,
                        color = Color(0xFF4CAF50),
                        modifier = Modifier
                            .size(16.dp)
                            .align(Alignment.BottomEnd)
                    ) {}
                }
            }
        }
        
        Spacer(modifier = Modifier.width(12.dp))
        
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (chat.isGroup) {
                        chat.groupName ?: "Group Chat"
                    } else {
                        chat.participants.first().displayName
                    },
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.weight(1f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                
                if (chat.lastMessage != null) {
                    Text(
                        text = chat.lastMessage.timestamp,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = chat.lastMessage?.content ?: "No messages yet",
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (chat.lastMessage?.isRead == false) {
                        MaterialTheme.colorScheme.onSurface
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    },
                    fontWeight = if (chat.lastMessage?.isRead == false) {
                        FontWeight.SemiBold
                    } else {
                        FontWeight.Normal
                    },
                    modifier = Modifier.weight(1f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                
                if (chat.unreadCount > 0) {
                    Surface(
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    ) {
                        Box(
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = if (chat.unreadCount > 9) "9+" else chat.unreadCount.toString(),
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ChatHeader(user: User) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { }) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back"
            )
        }
        
        Surface(
            shape = CircleShape,
            color = user.avatar,
            modifier = Modifier.size(40.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = user.displayName.split(" ").map { it.first() }.joinToString(""),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
        
        Spacer(modifier = Modifier.width(12.dp))
        
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = user.displayName,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold
            )
            
            Text(
                text = if (user.isOnline) "Online" else "Last seen recently",
                style = MaterialTheme.typography.bodySmall,
                color = if (user.isOnline) Color(0xFF4CAF50) else MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        
        IconButton(onClick = { }) {
            Icon(
                imageVector = Icons.Default.VideoCall,
                contentDescription = "Video Call"
            )
        }
        
        IconButton(onClick = { }) {
            Icon(
                imageVector = Icons.Default.Call,
                contentDescription = "Voice Call"
            )
        }
        
        IconButton(onClick = { }) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "More"
            )
        }
    }
}

@Composable
fun MessageBubble(
    message: Message,
    isFromCurrentUser: Boolean,
    otherUser: User
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isFromCurrentUser) Arrangement.End else Arrangement.Start
    ) {
        if (!isFromCurrentUser) {
            Surface(
                shape = CircleShape,
                color = otherUser.avatar,
                modifier = Modifier.size(32.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = otherUser.displayName.split(" ").map { it.first() }.joinToString(""),
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
            
            Spacer(modifier = Modifier.width(8.dp))
        }
        
        Column(
            horizontalAlignment = if (isFromCurrentUser) Alignment.End else Alignment.Start,
            modifier = Modifier.widthIn(max = 280.dp)
        ) {
            Surface(
                shape = RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 16.dp,
                    bottomStart = if (isFromCurrentUser) 16.dp else 4.dp,
                    bottomEnd = if (isFromCurrentUser) 4.dp else 16.dp
                ),
                color = if (isFromCurrentUser) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.surfaceVariant
                }
            ) {
                Text(
                    text = message.content,
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (isFromCurrentUser) {
                        Color.White
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    },
                    modifier = Modifier.padding(12.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(2.dp))
            
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = message.timestamp,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                if (isFromCurrentUser) {
                    Spacer(modifier = Modifier.width(4.dp))
                    
                    Icon(
                        imageVector = if (message.isRead) Icons.Default.DoneAll else Icons.Default.Done,
                        contentDescription = if (message.isRead) "Read" else "Sent",
                        tint = if (message.isRead) Color(0xFF4CAF50) else MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
        
        if (isFromCurrentUser) {
            Spacer(modifier = Modifier.width(8.dp))
            
            Surface(
                shape = CircleShape,
                color = Color(0xFF2196F3),
                modifier = Modifier.size(32.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "JD",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun MessageInput(
    messageText: String,
    onMessageTextChange: (String) -> Unit,
    onSendMessage: () -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Attach"
                )
            }
            
            OutlinedTextField(
                value = messageText,
                onValueChange = onMessageTextChange,
                placeholder = { Text("Type a message...") },
                modifier = Modifier.weight(1f),
                maxLines = 4
            )
            
            Spacer(modifier = Modifier.width(8.dp))
            
            if (messageText.isNotBlank()) {
                IconButton(
                    onClick = onSendMessage
                ) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "Send",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            } else {
                Row {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Default.PhotoCamera,
                            contentDescription = "Camera"
                        )
                    }
                    
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Default.Mic,
                            contentDescription = "Voice"
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun NotificationsHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Notifications",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        
        TextButton(onClick = { }) {
            Text("Mark all as read")
        }
    }
}

@Composable
fun NotificationItem(notification: Notification) {
    val iconColor = when (notification.type) {
        NotificationType.LIKE -> Color.Red
        NotificationType.COMMENT -> Color(0xFF2196F3)
        NotificationType.FOLLOW -> Color(0xFF4CAF50)
        NotificationType.MENTION -> Color(0xFFFF9800)
        NotificationType.MESSAGE -> Color(0xFF9C27B0)
        NotificationType.POST -> Color(0xFF607D8B)
    }
    
    val icon = when (notification.type) {
        NotificationType.LIKE -> Icons.Default.Favorite
        NotificationType.COMMENT -> Icons.Default.ChatBubble
        NotificationType.FOLLOW -> Icons.Default.PersonAdd
        NotificationType.MENTION -> Icons.Default.AlternateEmail
        NotificationType.MESSAGE -> Icons.Default.Message
        NotificationType.POST -> Icons.Default.Article
    }
    
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
            .background(
                if (!notification.isRead) {
                    MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.1f)
                } else {
                    Color.Transparent
                }
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box {
            if (notification.user != null) {
                Surface(
                    shape = CircleShape,
                    color = notification.user.avatar,
                    modifier = Modifier.size(48.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = notification.user.displayName.split(" ").map { it.first() }.joinToString(""),
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
            
            Surface(
                shape = CircleShape,
                color = iconColor,
                modifier = Modifier
                    .size(20.dp)
                    .align(Alignment.BottomEnd)
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = notification.type.name,
                        tint = Color.White,
                        modifier = Modifier.size(12.dp)
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.width(12.dp))
        
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Row {
                if (notification.user != null) {
                    Text(
                        text = notification.user.displayName,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    
                    Text(
                        text = " ${notification.content}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                } else {
                    Text(
                        text = notification.content,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(2.dp))
            
            Text(
                text = notification.timestamp,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        
        if (!notification.isRead) {
            Surface(
                shape = CircleShape,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(8.dp)
            ) {}
        }
    }
}

// Register previews with PreviewRegistry
fun registerSocialPreviews() {
    PreviewRegistry.registerPreview(
        PreviewItem(
            id = "social_feed",
            title = "Social Feed",
            description = "Complete social media feed with stories, posts, and interactions",
            category = PreviewCategory.SOCIAL,
            icon = Icons.Default.Feed,
            difficulty = PreviewDifficulty.ADVANCED,
            tags = listOf("social", "feed", "posts", "stories", "interactions"),
            content = { SocialFeedPreview() }
        )
    )
    
    PreviewRegistry.registerPreview(
        PreviewItem(
            id = "user_profile",
            title = "User Profile",
            description = "Social media user profile with stats, bio, highlights, and posts grid",
            category = PreviewCategory.SOCIAL,
            icon = Icons.Default.Person,
            difficulty = PreviewDifficulty.INTERMEDIATE,
            tags = listOf("profile", "user", "stats", "bio", "posts"),
            content = { UserProfilePreview() }
        )
    )
    
    PreviewRegistry.registerPreview(
        PreviewItem(
            id = "messaging_interface",
            title = "Messaging Interface",
            description = "Chat list interface with active users, conversations, and unread indicators",
            category = PreviewCategory.SOCIAL,
            icon = Icons.Default.Chat,
            difficulty = PreviewDifficulty.INTERMEDIATE,
            tags = listOf("messaging", "chat", "conversations", "active users"),
            content = { MessagingInterfacePreview() }
        )
    )
    
    PreviewRegistry.registerPreview(
        PreviewItem(
            id = "chat_detail",
            title = "Chat Detail",
            description = "Individual chat conversation with message bubbles and input",
            category = PreviewCategory.SOCIAL,
            icon = Icons.Default.ChatBubble,
            difficulty = PreviewDifficulty.INTERMEDIATE,
            tags = listOf("chat", "messages", "conversation", "bubbles"),
            content = { ChatDetailPreview() }
        )
    )
    
    PreviewRegistry.registerPreview(
        PreviewItem(
            id = "notifications",
            title = "Notifications",
            description = "Social notifications with different types and read states",
            category = PreviewCategory.SOCIAL,
            icon = Icons.Default.Notifications,
            difficulty = PreviewDifficulty.BEGINNER,
            tags = listOf("notifications", "alerts", "social", "interactions"),
            content = { NotificationsPreview() }
        )
    )
}