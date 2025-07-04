package com.xiaomi.base.preview.demos.ai

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import com.xiaomi.base.preview.base.BasePreviewScreen
import com.xiaomi.base.preview.base.ComponentShowcase
import com.xiaomi.base.preview.base.InteractiveDemo
import com.xiaomi.base.preview.catalog.PreviewCategory
import com.xiaomi.base.preview.catalog.PreviewDifficulty
import com.xiaomi.base.preview.catalog.PreviewItem
import com.xiaomi.base.preview.catalog.PreviewRegistry
import com.xiaomi.base.ui.theme.BaseTheme
import kotlinx.coroutines.delay

// Register AI previews
fun registerAIPreviews() {
    PreviewRegistry.registerPreview(
        PreviewItem(
            id = "ai_chatbot",
            title = "AI Chatbot Interface",
            description = "Modern chatbot UI with typing indicators, message bubbles, and smart suggestions",
            category = PreviewCategory.AI,
            icon = Icons.Default.SmartToy,
            difficulty = PreviewDifficulty.INTERMEDIATE,
            estimatedTime = "35 min",
            tags = listOf("chatbot", "conversation", "messaging", "AI"),
            content = { AIChatbotPreview() }
        )
    )
    
    PreviewRegistry.registerPreview(
        PreviewItem(
            id = "voice_assistant",
            title = "Voice Assistant",
            description = "Voice-activated AI assistant with waveform visualization and voice commands",
            category = PreviewCategory.AI,
            icon = Icons.Default.Mic,
            difficulty = PreviewDifficulty.ADVANCED,
            estimatedTime = "45 min",
            tags = listOf("voice", "assistant", "speech", "waveform"),
            content = { VoiceAssistantPreview() }
        )
    )
    
    PreviewRegistry.registerPreview(
        PreviewItem(
            id = "image_recognition",
            title = "Image Recognition",
            description = "AI-powered image analysis with object detection and confidence scores",
            category = PreviewCategory.AI,
            icon = Icons.Default.CameraAlt,
            difficulty = PreviewDifficulty.ADVANCED,
            estimatedTime = "40 min",
            tags = listOf("image", "recognition", "detection", "analysis"),
            content = { ImageRecognitionPreview() }
        )
    )
    
    PreviewRegistry.registerPreview(
        PreviewItem(
            id = "ai_writing_assistant",
            title = "AI Writing Assistant",
            description = "Smart text editor with AI suggestions, grammar check, and style improvements",
            category = PreviewCategory.AI,
            icon = Icons.Default.Edit,
            difficulty = PreviewDifficulty.INTERMEDIATE,
            estimatedTime = "30 min",
            tags = listOf("writing", "text", "suggestions", "grammar"),
            content = { AIWritingAssistantPreview() }
        )
    )
    
    PreviewRegistry.registerPreview(
        PreviewItem(
            id = "ai_recommendation",
            title = "AI Recommendation Engine",
            description = "Personalized recommendations with explanation and confidence metrics",
            category = PreviewCategory.AI,
            icon = Icons.Default.Recommend,
            difficulty = PreviewDifficulty.BEGINNER,
            estimatedTime = "25 min",
            tags = listOf("recommendations", "personalization", "suggestions"),
            content = { AIRecommendationPreview() }
        )
    )
}

@Preview(showBackground = true)
@Composable
fun AIChatbotPreview() {
    BaseTheme {
        BasePreviewScreen(
            title = "AI Chatbot",
            subtitle = "Intelligent conversation interface"
        ) {
            val messages = remember {
                mutableStateListOf(
                    ChatMessage("Hello! How can I help you today?", isUser = false, timestamp = "10:30 AM"),
                    ChatMessage("I need help with my project planning", isUser = true, timestamp = "10:31 AM"),
                    ChatMessage("I'd be happy to help you with project planning! What type of project are you working on?", isUser = false, timestamp = "10:31 AM"),
                    ChatMessage("It's a mobile app development project", isUser = true, timestamp = "10:32 AM")
                )
            }
            
            var isTyping by remember { mutableStateOf(false) }
            var newMessage by remember { mutableStateOf("") }
            
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                // Chat header
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    ),
                    shape = RoundedCornerShape(0.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(40.dp)
                        ) {
                            Box(
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.SmartToy,
                                    contentDescription = "AI Assistant",
                                    tint = Color.White,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                        
                        Spacer(modifier = Modifier.width(12.dp))
                        
                        Column {
                            Text(
                                text = "AI Assistant",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                            Text(
                                text = "Online • Ready to help",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                            )
                        }
                    }
                }
                
                // Messages
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(messages) { message ->
                        ChatMessageBubble(message = message)
                    }
                    
                    if (isTyping) {
                        item {
                            TypingIndicator()
                        }
                    }
                }
                
                // Quick suggestions
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val suggestions = listOf("Project timeline", "Team management", "Budget planning", "Risk assessment")
                    items(suggestions.size) { index ->
                        val suggestion = suggestions[index]
                        Button(
                            onClick = {
                                messages.add(ChatMessage(suggestion, isUser = true, timestamp = "Now"))
                                isTyping = true
                                // Simulate AI response
                            },
                            modifier = Modifier.height(32.dp)
                        ) {
                            Text(
                                text = suggestion,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Input field
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = newMessage,
                            onValueChange = { newMessage = it },
                            placeholder = { Text("Type your message...") },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(24.dp)
                        )
                        
                        Spacer(modifier = Modifier.width(8.dp))
                        
                        FloatingActionButton(
                            onClick = {
                                if (newMessage.isNotBlank()) {
                                    messages.add(ChatMessage(newMessage, isUser = true, timestamp = "Now"))
                                    newMessage = ""
                                    isTyping = true
                                }
                            },
                            modifier = Modifier.size(48.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Send,
                                contentDescription = "Send"
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun VoiceAssistantPreview() {
    BaseTheme {
        ComponentShowcase(
            title = "Voice Assistant",
            description = "Voice-activated AI with real-time audio visualization"
        ) {
            var isListening by remember { mutableStateOf(false) }
            var isProcessing by remember { mutableStateOf(false) }
            var recognizedText by remember { mutableStateOf("") }
            var response by remember { mutableStateOf("") }
            
            InteractiveDemo(
                title = "AI Writing Assistant",
                description = "Smart writing suggestions and improvements",
                controls = {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = {
                                isListening = !isListening
                                if (!isListening) {
                                    isProcessing = true
                                    recognizedText = "What's the weather like today?"
                                }
                            }
                        ) {
                            Text(if (isListening) "Stop" else "Start Listening")
                        }
                        
                        Button(
                            onClick = {
                                isProcessing = false
                                response = "Today will be sunny with a high of 75°F and partly cloudy skies."
                            },
                            enabled = isProcessing
                        ) {
                            Text("Simulate Response")
                        }
                    }
                }
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        // Voice visualization
                        VoiceWaveform(
                            isActive = isListening,
                            modifier = Modifier.height(80.dp)
                        )
                        
                        // Microphone button
                        val micScale by animateFloatAsState(
                            targetValue = if (isListening) 1.2f else 1f,
                            animationSpec = infiniteRepeatable(
                                animation = tween(1000),
                                repeatMode = RepeatMode.Reverse
                            )
                        )
                        
                        FloatingActionButton(
                            onClick = { isListening = !isListening },
                            modifier = Modifier
                                .size(80.dp)
                                .graphicsLayer {
                                    scaleX = micScale
                                    scaleY = micScale
                                },
                            containerColor = if (isListening) Color(0xFFF44336) else MaterialTheme.colorScheme.primary
                        ) {
                            Icon(
                                imageVector = if (isListening) Icons.Default.MicOff else Icons.Default.Mic,
                                contentDescription = "Microphone",
                                modifier = Modifier.size(32.dp),
                                tint = Color.White
                            )
                        }
                        
                        // Status text
                        Text(
                            text = when {
                                isListening -> "Listening..."
                                isProcessing -> "Processing..."
                                recognizedText.isNotEmpty() -> "Tap to speak"
                                else -> "Tap to start"
                            },
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        
                        // Recognized text
                        if (recognizedText.isNotEmpty()) {
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                                )
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp)
                                ) {
                                    Text(
                                        text = "You said:",
                                        style = MaterialTheme.typography.labelMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    Text(
                                        text = recognizedText,
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                        }
                        
                        // AI response
                        if (response.isNotEmpty()) {
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.primaryContainer
                                )
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp)
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.SmartToy,
                                            contentDescription = "AI",
                                            modifier = Modifier.size(16.dp),
                                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = "AI Assistant:",
                                            style = MaterialTheme.typography.labelMedium,
                                            color = MaterialTheme.colorScheme.onPrimaryContainer
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = response,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ImageRecognitionPreview() {
    BaseTheme {
        ComponentShowcase(
            title = "Image Recognition",
            description = "AI-powered object detection and analysis"
        ) {
            var isAnalyzing by remember { mutableStateOf(false) }
            var analysisComplete by remember { mutableStateOf(false) }
            
            val detectedObjects = remember {
                listOf(
                    DetectedObject("Person", 0.95f, "Standing in the center"),
                    DetectedObject("Dog", 0.87f, "Golden Retriever, sitting"),
                    DetectedObject("Tree", 0.92f, "Large oak tree in background"),
                    DetectedObject("Car", 0.78f, "Blue sedan, partially visible")
                )
            }
            
            InteractiveDemo(
                title = "AI Image Analysis",
                description = "Real-time object detection and analysis",
                controls = {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = {
                                isAnalyzing = true
                                analysisComplete = false
                            }
                        ) {
                            Text("Analyze Image")
                        }
                        
                        Button(
                            onClick = {
                                isAnalyzing = false
                                analysisComplete = true
                            },
                            enabled = isAnalyzing
                        ) {
                            Text("Complete Analysis")
                        }
                        
                        Button(
                            onClick = {
                                isAnalyzing = false
                                analysisComplete = false
                            }
                        ) {
                            Text("Reset")
                        }
                    }
                }
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        // Image placeholder
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant
                            )
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                if (isAnalyzing) {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        CircularProgressIndicator(
                                            modifier = Modifier.size(48.dp)
                                        )
                                        Spacer(modifier = Modifier.height(16.dp))
                                        Text(
                                            text = "Analyzing image...",
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                } else {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Image,
                                            contentDescription = "Image",
                                            modifier = Modifier.size(64.dp),
                                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                        Text(
                                            text = "Sample Image",
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(20.dp))
                        
                        // Analysis results
                        if (analysisComplete) {
                            Text(
                                text = "Detection Results",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold
                            )
                            
                            Spacer(modifier = Modifier.height(12.dp))
                            
                            detectedObjects.forEach { obj ->
                                DetectedObjectItem(obj)
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                            
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            // Summary
                            Card(
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.primaryContainer
                                )
                            ) {
                                Row(
                                    modifier = Modifier.padding(16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = "Complete",
                                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                                    )
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Column {
                                        Text(
                                            text = "Analysis Complete",
                                            style = MaterialTheme.typography.titleSmall,
                                            fontWeight = FontWeight.SemiBold,
                                            color = MaterialTheme.colorScheme.onPrimaryContainer
                                        )
                                        Text(
                                            text = "${detectedObjects.size} objects detected with high confidence",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AIWritingAssistantPreview() {
    BaseTheme {
        ComponentShowcase(
            title = "AI Writing Assistant",
            description = "Smart text editor with AI-powered suggestions"
        ) {
            var text by remember { mutableStateOf("The quick brown fox jumps over the lazy dog. This sentence contains every letter of the alphabet.") }
            var showSuggestions by remember { mutableStateOf(true) }
            
            val suggestions = remember {
                listOf(
                    WritingSuggestion("Grammar", "Consider using 'leaps' instead of 'jumps' for better flow", SuggestionType.GRAMMAR),
                    WritingSuggestion("Style", "This sentence could be more engaging with active voice", SuggestionType.STYLE),
                    WritingSuggestion("Clarity", "Consider breaking this into two sentences for better readability", SuggestionType.CLARITY)
                )
            }
            
            InteractiveDemo(
                title = "Writing Assistant",
                description = "AI-powered writing suggestions and improvements",
                controls = {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = { showSuggestions = !showSuggestions }
                        ) {
                            Text(if (showSuggestions) "Hide Suggestions" else "Show Suggestions")
                        }
                        
                        Button(
                            onClick = {
                                text = "The agile brown fox leaps gracefully over the sleepy dog."
                            }
                        ) {
                            Text("Apply Suggestions")
                        }
                    }
                }
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Text editor
                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "Document Editor",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold
                            )
                            
                            Spacer(modifier = Modifier.height(12.dp))
                            
                            OutlinedTextField(
                                value = text,
                                onValueChange = { text = it },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(120.dp),
                                placeholder = { Text("Start writing...") },
                                supportingText = {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text("${text.length} characters")
                                        Text("${text.split(" ").size} words")
                                    }
                                }
                            )
                        }
                    }
                    
                    // AI suggestions
                    if (showSuggestions) {
                        Card(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.AutoAwesome,
                                        contentDescription = "AI Suggestions",
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "AI Suggestions",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                                
                                Spacer(modifier = Modifier.height(12.dp))
                                
                                suggestions.forEach { suggestion ->
                                    WritingSuggestionItem(suggestion)
                                    Spacer(modifier = Modifier.height(8.dp))
                                }
                            }
                        }
                    }
                    
                    // Writing stats
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            WritingStatItem("Readability", "Good", Color(0xFF4CAF50))
                            WritingStatItem("Tone", "Neutral", Color(0xFF2196F3))
                            WritingStatItem("Clarity", "High", Color(0xFF4CAF50))
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AIRecommendationPreview() {
    BaseTheme {
        ComponentShowcase(
            title = "AI Recommendations",
            description = "Personalized suggestions with confidence scores"
        ) {
            val recommendations = remember {
                listOf(
                    Recommendation(
                        title = "Morning Workout Routine",
                        description = "Based on your fitness goals and schedule",
                        confidence = 0.92f,
                        category = "Health & Fitness",
                        reason = "You typically exercise in the morning and prefer 30-minute sessions"
                    ),
                    Recommendation(
                        title = "Project Management Course",
                        description = "Enhance your leadership skills",
                        confidence = 0.87f,
                        category = "Education",
                        reason = "Your recent searches and career progression indicate interest in management"
                    ),
                    Recommendation(
                        title = "Italian Restaurant Nearby",
                        description = "Highly rated, matches your preferences",
                        confidence = 0.78f,
                        category = "Dining",
                        reason = "You enjoy Italian cuisine and prefer restaurants within 2 miles"
                    )
                )
            }
            
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Recommend,
                                contentDescription = "Recommendations",
                                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = "Personalized for You",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                                Text(
                                    text = "AI-powered recommendations based on your preferences",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                                )
                            }
                        }
                    }
                }
                
                items(recommendations) { recommendation ->
                    RecommendationCard(recommendation)
                }
            }
        }
    }
}

// Data classes
data class ChatMessage(
    val content: String,
    val isUser: Boolean,
    val timestamp: String
)

data class DetectedObject(
    val name: String,
    val confidence: Float,
    val description: String
)

data class WritingSuggestion(
    val title: String,
    val description: String,
    val type: SuggestionType
)

enum class SuggestionType {
    GRAMMAR, STYLE, CLARITY
}

data class Recommendation(
    val title: String,
    val description: String,
    val confidence: Float,
    val category: String,
    val reason: String
)

// Helper Composables
@Composable
fun ChatMessageBubble(message: ChatMessage) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (message.isUser) Arrangement.End else Arrangement.Start
    ) {
        Card(
            modifier = Modifier.widthIn(max = 280.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (message.isUser) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.surfaceVariant
                }
            ),
            shape = RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 16.dp,
                bottomStart = if (message.isUser) 16.dp else 4.dp,
                bottomEnd = if (message.isUser) 4.dp else 16.dp
            )
        ) {
            Column(
                modifier = Modifier.padding(12.dp)
            ) {
                Text(
                    text = message.content,
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (message.isUser) {
                        Color.White
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    }
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = message.timestamp,
                    style = MaterialTheme.typography.labelSmall,
                    color = if (message.isUser) {
                        Color.White.copy(alpha = 0.7f)
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                    }
                )
            }
        }
    }
}

@Composable
fun TypingIndicator() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            ),
            shape = RoundedCornerShape(16.dp, 16.dp, 16.dp, 4.dp)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(3) { index ->
                    val alpha by animateFloatAsState(
                        targetValue = 1f,
                        animationSpec = infiniteRepeatable(
                            animation = tween(600, delayMillis = index * 200),
                            repeatMode = RepeatMode.Reverse
                        )
                    )
                    
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(
                                MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = alpha),
                                CircleShape
                            )
                    )
                    
                    if (index < 2) {
                        Spacer(modifier = Modifier.width(4.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun VoiceWaveform(
    isActive: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(20) { index ->
            val height by animateFloatAsState(
                targetValue = if (isActive) (0.3f + Math.random().toFloat() * 0.7f) else 0.1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(300 + index * 50),
                    repeatMode = RepeatMode.Reverse
                )
            )
            
            Box(
                modifier = Modifier
                    .width(3.dp)
                    .fillMaxHeight(height)
                    .background(
                        MaterialTheme.colorScheme.primary,
                        RoundedCornerShape(1.5.dp)
                    )
            )
            
            if (index < 19) {
                Spacer(modifier = Modifier.width(2.dp))
            }
        }
    }
}

@Composable
fun DetectedObjectItem(obj: DetectedObject) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = obj.name,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = obj.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = when {
                    obj.confidence >= 0.9f -> Color(0xFF4CAF50)
                    obj.confidence >= 0.7f -> Color(0xFFFF9800)
                    else -> Color(0xFFF44336)
                }.copy(alpha = 0.1f)
            ) {
                Text(
                    text = "${(obj.confidence * 100).toInt()}%",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = when {
                        obj.confidence >= 0.9f -> Color(0xFF4CAF50)
                        obj.confidence >= 0.7f -> Color(0xFFFF9800)
                        else -> Color(0xFFF44336)
                    },
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }
    }
}

@Composable
fun WritingSuggestionItem(suggestion: WritingSuggestion) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.Top
        ) {
            val typeColor = when (suggestion.type) {
                SuggestionType.GRAMMAR -> Color(0xFFF44336)
                SuggestionType.STYLE -> Color(0xFF2196F3)
                SuggestionType.CLARITY -> Color(0xFF4CAF50)
            }
            
            Surface(
                shape = RoundedCornerShape(4.dp),
                color = typeColor.copy(alpha = 0.1f),
                modifier = Modifier.padding(top = 2.dp)
            ) {
                Text(
                    text = suggestion.type.name,
                    style = MaterialTheme.typography.labelSmall,
                    color = typeColor,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
            }
            
            Spacer(modifier = Modifier.width(8.dp))
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = suggestion.title,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = suggestion.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            IconButton(
                onClick = { /* Apply suggestion */ },
                modifier = Modifier.size(32.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Apply",
                    modifier = Modifier.size(16.dp),
                    tint = Color(0xFF4CAF50)
                )
            }
        }
    }
}

@Composable
fun WritingStatItem(
    label: String,
    value: String,
    color: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.SemiBold,
            color = color
        )
    }
}

@Composable
fun RecommendationCard(recommendation: Recommendation) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = recommendation.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = recommendation.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                ) {
                    Text(
                        text = "${(recommendation.confidence * 100).toInt()}% match",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Surface(
                shape = RoundedCornerShape(6.dp),
                color = MaterialTheme.colorScheme.surfaceVariant
            ) {
                Text(
                    text = recommendation.category,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Text(
                text = "Why this recommendation?",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = recommendation.reason,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}