package com.xiaomi.base.preview.demos.education

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

// Data classes for Education demos
data class Course(
    val id: String,
    val title: String,
    val instructor: String,
    val category: String,
    val duration: String,
    val lessons: Int,
    val rating: Float,
    val price: String,
    val thumbnail: Color,
    val progress: Float = 0f,
    val isEnrolled: Boolean = false,
    val difficulty: String = "Beginner"
)

data class Lesson(
    val id: String,
    val title: String,
    val duration: String,
    val type: LessonType,
    val isCompleted: Boolean = false,
    val isLocked: Boolean = false
)

enum class LessonType {
    VIDEO, READING, QUIZ, ASSIGNMENT
}

data class Quiz(
    val id: String,
    val title: String,
    val questions: Int,
    val timeLimit: String,
    val difficulty: String,
    val category: String,
    val bestScore: Int? = null,
    val attempts: Int = 0
)

data class Question(
    val id: String,
    val question: String,
    val options: List<String>,
    val correctAnswer: Int,
    val explanation: String,
    val selectedAnswer: Int? = null
)

data class Assignment(
    val id: String,
    val title: String,
    val description: String,
    val dueDate: String,
    val status: AssignmentStatus,
    val grade: String? = null,
    val submittedDate: String? = null
)

enum class AssignmentStatus {
    PENDING, SUBMITTED, GRADED, OVERDUE
}

data class Student(
    val id: String,
    val name: String,
    val email: String,
    val avatar: Color,
    val coursesEnrolled: Int,
    val coursesCompleted: Int,
    val totalHours: Int,
    val certificates: Int
)

data class Certificate(
    val id: String,
    val courseName: String,
    val issueDate: String,
    val instructor: String,
    val grade: String,
    val certificateColor: Color
)

// E-Learning Dashboard Preview
@Preview(name = "E-Learning Dashboard", showBackground = true)
@Composable
fun ELearningDashboardPreview() {
    BaseAppTheme {
        ELearningDashboard()
    }
}

@Composable
fun ELearningDashboard() {
    val currentUser = Student(
        id = "1",
        name = "John Doe",
        email = "john.doe@email.com",
        avatar = Color(0xFF2196F3),
        coursesEnrolled = 8,
        coursesCompleted = 3,
        totalHours = 127,
        certificates = 3
    )
    
    val enrolledCourses = listOf(
        Course("1", "Android Development", "Dr. Smith", "Programming", "40 hours", 25, 4.8f, "$99", Color(0xFF4CAF50), 0.65f, true),
        Course("2", "UI/UX Design", "Jane Wilson", "Design", "30 hours", 20, 4.6f, "$79", Color(0xFF9C27B0), 0.30f, true),
        Course("3", "Data Science", "Prof. Johnson", "Data", "50 hours", 35, 4.9f, "$129", Color(0xFF2196F3), 0.15f, true)
    )
    
    val recentAssignments = listOf(
        Assignment("1", "Mobile App Project", "Create a simple Android app", "Dec 15, 2024", AssignmentStatus.PENDING),
        Assignment("2", "Design Portfolio", "Submit your design portfolio", "Dec 10, 2024", AssignmentStatus.SUBMITTED, submittedDate = "Dec 8, 2024"),
        Assignment("3", "Data Analysis Report", "Analyze the provided dataset", "Dec 5, 2024", AssignmentStatus.GRADED, "A-", "Dec 4, 2024")
    )
    
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Welcome back, ${currentUser.name}!",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
        }
        
        item {
            LearningStatsRow(currentUser)
        }
        
        item {
            Text(
                text = "Continue Learning",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
        }
        
        items(enrolledCourses) { course ->
            EnrolledCourseCard(course)
        }
        
        item {
            Text(
                text = "Recent Assignments",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
        }
        
        items(recentAssignments) { assignment ->
            AssignmentCard(assignment)
        }
    }
}

// Course Detail Preview
@Preview(name = "Course Detail", showBackground = true)
@Composable
fun CourseDetailPreview() {
    BaseAppTheme {
        CourseDetail()
    }
}

@Composable
fun CourseDetail() {
    val course = Course(
        id = "1",
        title = "Complete Android Development Course",
        instructor = "Dr. Sarah Smith",
        category = "Programming",
        duration = "40 hours",
        lessons = 25,
        rating = 4.8f,
        price = "$99",
        thumbnail = Color(0xFF4CAF50),
        progress = 0.65f,
        isEnrolled = true,
        difficulty = "Intermediate"
    )
    
    val lessons = listOf(
        Lesson("1", "Introduction to Android", "15 min", LessonType.VIDEO, true),
        Lesson("2", "Setting up Development Environment", "20 min", LessonType.VIDEO, true),
        Lesson("3", "Understanding Activities", "25 min", LessonType.VIDEO, true),
        Lesson("4", "Working with Layouts", "30 min", LessonType.VIDEO, false),
        Lesson("5", "Quiz: Basic Concepts", "10 min", LessonType.QUIZ, false),
        Lesson("6", "Building Your First App", "45 min", LessonType.ASSIGNMENT, false, true),
        Lesson("7", "Advanced Topics", "35 min", LessonType.READING, false, true)
    )
    
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            CourseHeader(course)
        }
        
        item {
            CourseProgressCard(course)
        }
        
        item {
            Text(
                text = "Course Content",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
        }
        
        items(lessons) { lesson ->
            LessonItem(lesson)
        }
    }
}

// Quiz Interface Preview
@Preview(name = "Quiz Interface", showBackground = true)
@Composable
fun QuizInterfacePreview() {
    BaseAppTheme {
        QuizInterface()
    }
}

@Composable
fun QuizInterface() {
    val quiz = Quiz(
        id = "1",
        title = "Android Fundamentals Quiz",
        questions = 10,
        timeLimit = "15 minutes",
        difficulty = "Intermediate",
        category = "Programming",
        bestScore = 85,
        attempts = 2
    )
    
    val currentQuestion = Question(
        id = "1",
        question = "What is the main component used to display a list of items in Android?",
        options = listOf(
            "ListView",
            "RecyclerView",
            "ScrollView",
            "GridView"
        ),
        correctAnswer = 1,
        explanation = "RecyclerView is the recommended component for displaying lists as it's more efficient and flexible.",
        selectedAnswer = null
    )
    
    var selectedAnswer by remember { mutableStateOf<Int?>(null) }
    var timeRemaining by remember { mutableStateOf("14:32") }
    var currentQuestionNumber by remember { mutableStateOf(3) }
    
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            QuizHeader(quiz, timeRemaining, currentQuestionNumber)
        }
        
        item {
            QuizProgressIndicator(currentQuestionNumber, quiz.questions)
        }
        
        item {
            QuestionCard(
                question = currentQuestion,
                selectedAnswer = selectedAnswer,
                onAnswerSelected = { selectedAnswer = it }
            )
        }
        
        item {
            QuizNavigationButtons(
                canGoBack = currentQuestionNumber > 1,
                canGoNext = selectedAnswer != null,
                isLastQuestion = currentQuestionNumber == quiz.questions
            )
        }
    }
}

// Student Progress Preview
@Preview(name = "Student Progress", showBackground = true)
@Composable
fun StudentProgressPreview() {
    BaseAppTheme {
        StudentProgress()
    }
}

@Composable
fun StudentProgress() {
    val student = Student(
        id = "1",
        name = "John Doe",
        email = "john.doe@email.com",
        avatar = Color(0xFF2196F3),
        coursesEnrolled = 8,
        coursesCompleted = 3,
        totalHours = 127,
        certificates = 3
    )
    
    val certificates = listOf(
        Certificate("1", "Android Development Fundamentals", "Nov 15, 2024", "Dr. Smith", "A", Color(0xFF4CAF50)),
        Certificate("2", "UI/UX Design Principles", "Oct 22, 2024", "Jane Wilson", "A-", Color(0xFF9C27B0)),
        Certificate("3", "Introduction to Programming", "Sep 10, 2024", "Prof. Johnson", "B+", Color(0xFF2196F3))
    )
    
    val skillProgress = listOf(
        "Android Development" to 0.85f,
        "UI/UX Design" to 0.70f,
        "Data Analysis" to 0.45f,
        "Project Management" to 0.60f
    )
    
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "My Progress",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
        }
        
        item {
            StudentProfileCard(student)
        }
        
        item {
            Text(
                text = "Skill Progress",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
        }
        
        items(skillProgress) { (skill, progress) ->
            SkillProgressItem(skill, progress)
        }
        
        item {
            Text(
                text = "Certificates Earned",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
        }
        
        items(certificates) { certificate ->
            CertificateCard(certificate)
        }
    }
}

// Assignment Submission Preview
@Preview(name = "Assignment Submission", showBackground = true)
@Composable
fun AssignmentSubmissionPreview() {
    BaseAppTheme {
        AssignmentSubmission()
    }
}

@Composable
fun AssignmentSubmission() {
    val assignment = Assignment(
        id = "1",
        title = "Mobile App Development Project",
        description = "Create a complete Android application that demonstrates the concepts learned in this course. The app should include at least 3 activities, use RecyclerView, implement navigation, and follow Material Design guidelines.",
        dueDate = "December 15, 2024",
        status = AssignmentStatus.PENDING
    )
    
    var submissionText by remember { mutableStateOf("") }
    var selectedFiles by remember { mutableStateOf(listOf<String>()) }
    
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Assignment Submission",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
        }
        
        item {
            AssignmentDetailsCard(assignment)
        }
        
        item {
            Text(
                text = "Submission",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
        }
        
        item {
            SubmissionForm(
                submissionText = submissionText,
                onTextChange = { submissionText = it },
                selectedFiles = selectedFiles,
                onFilesSelected = { selectedFiles = it }
            )
        }
        
        item {
            SubmissionActions(assignment)
        }
    }
}

// Helper Composables
@Composable
fun LearningStatsRow(student: Student) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        LearningStatCard(
            title = "Enrolled",
            value = student.coursesEnrolled.toString(),
            icon = Icons.Default.School,
            color = MaterialTheme.colorScheme.primary
        )
        
        LearningStatCard(
            title = "Completed",
            value = student.coursesCompleted.toString(),
            icon = Icons.Default.CheckCircle,
            color = Color(0xFF4CAF50)
        )
        
        LearningStatCard(
            title = "Hours",
            value = student.totalHours.toString(),
            icon = Icons.Default.Schedule,
            color = Color(0xFFFF9800)
        )
        
        LearningStatCard(
            title = "Certificates",
            value = student.certificates.toString(),
            icon = Icons.Default.EmojiEvents,
            color = Color(0xFFF44336)
        )
    }
}

@Composable
fun LearningStatCard(
    title: String,
    value: String,
    icon: ImageVector,
    color: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = color,
                modifier = Modifier.size(24.dp)
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = color
            )
            
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun EnrolledCourseCard(course: Course) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = course.thumbnail,
                modifier = Modifier.size(64.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Course Thumbnail",
                        tint = Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = course.title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                
                Text(
                    text = "by ${course.instructor}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                LinearProgressIndicator(
                    progress = course.progress,
                    modifier = Modifier.fillMaxWidth()
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = "${(course.progress * 100).toInt()}% complete",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            IconButton(
                onClick = { }
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Continue"
                )
            }
        }
    }
}

@Composable
fun AssignmentCard(assignment: Assignment) {
    Card(
        modifier = Modifier.fillMaxWidth()
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
                    text = assignment.title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.weight(1f)
                )
                
                AssignmentStatusBadge(assignment.status)
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = assignment.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Due: ${assignment.dueDate}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                if (assignment.grade != null) {
                    Text(
                        text = "Grade: ${assignment.grade}",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF4CAF50)
                    )
                }
            }
        }
    }
}

@Composable
fun AssignmentStatusBadge(status: AssignmentStatus) {
    val (color, text) = when (status) {
        AssignmentStatus.PENDING -> Color(0xFFFF9800) to "Pending"
        AssignmentStatus.SUBMITTED -> Color(0xFF2196F3) to "Submitted"
        AssignmentStatus.GRADED -> Color(0xFF4CAF50) to "Graded"
        AssignmentStatus.OVERDUE -> Color(0xFFF44336) to "Overdue"
    }
    
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = color.copy(alpha = 0.2f)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            color = color,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

@Composable
fun CourseHeader(course: Course) {
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
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = course.thumbnail,
                    modifier = Modifier.size(80.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.School,
                            contentDescription = "Course Thumbnail",
                            tint = Color.White,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }
                
                Spacer(modifier = Modifier.width(16.dp))
                
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = course.title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    
                    Text(
                        text = "by ${course.instructor}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Rating",
                            tint = Color(0xFFFFD700),
                            modifier = Modifier.size(16.dp)
                        )
                        
                        Spacer(modifier = Modifier.width(4.dp))
                        
                        Text(
                            text = course.rating.toString(),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        
                        Spacer(modifier = Modifier.width(16.dp))
                        
                        Text(
                            text = "${course.lessons} lessons",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                        )
                        
                        Spacer(modifier = Modifier.width(16.dp))
                        
                        Text(
                            text = course.duration,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CourseProgressCard(course: Course) {
    Card(
        modifier = Modifier.fillMaxWidth()
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
                    text = "Course Progress",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                
                Text(
                    text = "${(course.progress * 100).toInt()}%",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            LinearProgressIndicator(
                progress = course.progress,
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "${(course.lessons * course.progress).toInt()} of ${course.lessons} lessons completed",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun LessonItem(lesson: Lesson) {
    val iconColor = when {
        lesson.isCompleted -> Color(0xFF4CAF50)
        lesson.isLocked -> MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
        else -> MaterialTheme.colorScheme.primary
    }
    
    val icon = when (lesson.type) {
        LessonType.VIDEO -> Icons.Default.PlayArrow
        LessonType.READING -> Icons.Default.Article
        LessonType.QUIZ -> Icons.Default.Quiz
        LessonType.ASSIGNMENT -> Icons.Default.Assignment
    }
    
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = !lesson.isLocked) { }
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            shape = CircleShape,
            color = iconColor.copy(alpha = 0.2f),
            modifier = Modifier.size(40.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                if (lesson.isCompleted) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Completed",
                        tint = iconColor,
                        modifier = Modifier.size(20.dp)
                    )
                } else if (lesson.isLocked) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Locked",
                        tint = iconColor,
                        modifier = Modifier.size(20.dp)
                    )
                } else {
                    Icon(
                        imageVector = icon,
                        contentDescription = lesson.type.name,
                        tint = iconColor,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.width(16.dp))
        
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = lesson.title,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                color = if (lesson.isLocked) {
                    MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                } else {
                    MaterialTheme.colorScheme.onSurface
                }
            )
            
            Text(
                text = "${lesson.type.name.lowercase().replaceFirstChar { it.uppercase() }} • ${lesson.duration}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        
        if (!lesson.isLocked && !lesson.isCompleted) {
            IconButton(
                onClick = { }
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Start"
                )
            }
        }
    }
}

@Composable
fun QuizHeader(quiz: Quiz, timeRemaining: String, currentQuestion: Int) {
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
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = quiz.title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xFFF44336).copy(alpha = 0.2f)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Schedule,
                            contentDescription = "Time",
                            tint = Color(0xFFF44336),
                            modifier = Modifier.size(16.dp)
                        )
                        
                        Spacer(modifier = Modifier.width(4.dp))
                        
                        Text(
                            text = timeRemaining,
                            style = MaterialTheme.typography.labelMedium,
                            color = Color(0xFFF44336),
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Question $currentQuestion of ${quiz.questions}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
            )
        }
    }
}

@Composable
fun QuizProgressIndicator(currentQuestion: Int, totalQuestions: Int) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Progress",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Text(
                text = "$currentQuestion/$totalQuestions",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        
        Spacer(modifier = Modifier.height(4.dp))
        
        LinearProgressIndicator(
            progress = currentQuestion.toFloat() / totalQuestions,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun QuestionCard(
    question: Question,
    selectedAnswer: Int?,
    onAnswerSelected: (Int) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = question.question,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            question.options.forEachIndexed { index, option ->
                AnswerOption(
                    text = option,
                    isSelected = selectedAnswer == index,
                    onClick = { onAnswerSelected(index) }
                )
                
                if (index < question.options.size - 1) {
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
fun AnswerOption(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) {
                MaterialTheme.colorScheme.primaryContainer
            } else {
                MaterialTheme.colorScheme.surfaceVariant
            }
        ),
        border = if (isSelected) {
            androidx.compose.foundation.BorderStroke(
                2.dp,
                MaterialTheme.colorScheme.primary
            )
        } else null
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = isSelected,
                onClick = onClick
            )
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun QuizNavigationButtons(
    canGoBack: Boolean,
    canGoNext: Boolean,
    isLastQuestion: Boolean
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        OutlinedButton(
            onClick = { },
            enabled = canGoBack
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Previous",
                modifier = Modifier.size(18.dp)
            )
            
            Spacer(modifier = Modifier.width(8.dp))
            
            Text("Previous")
        }
        
        Button(
            onClick = { },
            enabled = canGoNext
        ) {
            Text(if (isLastQuestion) "Submit" else "Next")
            
            if (!isLastQuestion) {
                Spacer(modifier = Modifier.width(8.dp))
                
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Next",
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

@Composable
fun StudentProfileCard(student: Student) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = CircleShape,
                color = student.avatar,
                modifier = Modifier.size(64.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = student.name.split(" ").map { it.first() }.joinToString(""),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = student.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                
                Text(
                    text = student.email,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = "${student.totalHours} hours of learning",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                )
            }
        }
    }
}

@Composable
fun SkillProgressItem(skill: String, progress: Float) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = skill,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold
            )
            
            Text(
                text = "${(progress * 100).toInt()}%",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        
        Spacer(modifier = Modifier.height(4.dp))
        
        LinearProgressIndicator(
            progress = progress,
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun CertificateCard(certificate: Certificate) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = certificate.certificateColor,
                modifier = Modifier.size(64.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.EmojiEvents,
                        contentDescription = "Certificate",
                        tint = Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = certificate.courseName,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                
                Text(
                    text = "Issued by ${certificate.instructor}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                Text(
                    text = certificate.issueDate,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFF4CAF50).copy(alpha = 0.2f)
                ) {
                    Text(
                        text = "Grade: ${certificate.grade}",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFF4CAF50),
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun AssignmentDetailsCard(assignment: Assignment) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = assignment.title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = assignment.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Due Date",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                    )
                    
                    Text(
                        text = assignment.dueDate,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
                
                AssignmentStatusBadge(assignment.status)
            }
        }
    }
}

@Composable
fun SubmissionForm(
    submissionText: String,
    onTextChange: (String) -> Unit,
    selectedFiles: List<String>,
    onFilesSelected: (List<String>) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Submission Text",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            OutlinedTextField(
                value = submissionText,
                onValueChange = onTextChange,
                placeholder = { Text("Describe your submission...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                maxLines = 5
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "Attachments",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            OutlinedButton(
                onClick = { },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.AttachFile,
                    contentDescription = "Attach Files"
                )
                
                Spacer(modifier = Modifier.width(8.dp))
                
                Text("Attach Files")
            }
            
            if (selectedFiles.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                
                selectedFiles.forEach { file ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.InsertDriveFile,
                            contentDescription = "File",
                            modifier = Modifier.size(16.dp)
                        )
                        
                        Spacer(modifier = Modifier.width(8.dp))
                        
                        Text(
                            text = file,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.weight(1f)
                        )
                        
                        IconButton(
                            onClick = { },
                            modifier = Modifier.size(24.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Remove",
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SubmissionActions(assignment: Assignment) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedButton(
            onClick = { },
            modifier = Modifier.weight(1f)
        ) {
            Text("Save Draft")
        }
        
        Button(
            onClick = { },
            modifier = Modifier.weight(1f)
        ) {
            Text("Submit")
        }
    }
}

// Register previews with PreviewRegistry
fun registerEducationPreviews() {
    PreviewRegistry.registerPreview(
        PreviewItem(
            id = "elearning_dashboard",
            title = "E-Learning Dashboard",
            description = "Complete e-learning dashboard with student progress, enrolled courses, and assignments",
            category = PreviewCategory.EDUCATION,
            icon = Icons.Default.School,
            difficulty = PreviewDifficulty.INTERMEDIATE,
            estimatedTime = "35 minutes",
            tags = listOf("education", "dashboard", "learning", "progress", "courses"),
            content = { ELearningDashboardPreview() }
        )
    )
    
    PreviewRegistry.registerPreview(
        PreviewItem(
            id = "course_detail",
            title = "Course Detail",
            description = "Detailed course view with lessons, progress tracking, and content organization",
            category = PreviewCategory.EDUCATION,
            icon = Icons.Default.MenuBook,
            difficulty = PreviewDifficulty.INTERMEDIATE,
            estimatedTime = "30 minutes",
            tags = listOf("course", "lessons", "progress", "education", "learning"),
            content = { CourseDetailPreview() }
        )
    )
    
    PreviewRegistry.registerPreview(
        PreviewItem(
            id = "quiz_interface",
            title = "Quiz Interface",
            description = "Interactive quiz interface with timer, progress tracking, and question navigation",
            category = PreviewCategory.EDUCATION,
            icon = Icons.Default.Quiz,
            difficulty = PreviewDifficulty.ADVANCED,
            estimatedTime = "40 minutes",
            tags = listOf("quiz", "assessment", "timer", "questions", "interactive"),
            content = { QuizInterfacePreview() }
        )
    )
    
    PreviewRegistry.registerPreview(
        PreviewItem(
            id = "student_progress",
            title = "Student Progress",
            description = "Student progress tracking with skills, certificates, and learning statistics",
            category = PreviewCategory.EDUCATION,
            icon = Icons.Default.TrendingUp,
            difficulty = PreviewDifficulty.INTERMEDIATE,
            estimatedTime = "25 minutes",
            tags = listOf("progress", "student", "certificates", "skills", "statistics"),
            content = { StudentProgressPreview() }
        )
    )
    
    PreviewRegistry.registerPreview(
        PreviewItem(
            id = "assignment_submission",
            title = "Assignment Submission",
            description = "Assignment submission interface with file uploads and text submission",
            category = PreviewCategory.EDUCATION,
            icon = Icons.Default.Assignment,
            difficulty = PreviewDifficulty.ADVANCED,
            estimatedTime = "35 minutes",
            tags = listOf("assignment", "submission", "upload", "education", "files"),
            content = { AssignmentSubmissionPreview() }
        )
    )
}