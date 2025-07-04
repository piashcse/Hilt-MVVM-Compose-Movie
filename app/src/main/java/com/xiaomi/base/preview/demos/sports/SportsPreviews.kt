package com.xiaomi.base.preview.demos.sports

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
import kotlin.math.roundToInt

// Data classes for Sports demos
data class WorkoutSession(
    val id: String,
    val name: String,
    val type: WorkoutType,
    val duration: Int, // in minutes
    val caloriesBurned: Int,
    val date: String,
    val exercises: List<Exercise>,
    val difficulty: WorkoutDifficulty,
    val isCompleted: Boolean = false
)

enum class WorkoutType {
    CARDIO, STRENGTH, YOGA, PILATES, HIIT, RUNNING, CYCLING, SWIMMING, BOXING, CROSSFIT
}

enum class WorkoutDifficulty {
    BEGINNER, INTERMEDIATE, ADVANCED, EXPERT
}

data class Exercise(
    val id: String,
    val name: String,
    val type: ExerciseType,
    val sets: Int? = null,
    val reps: Int? = null,
    val duration: Int? = null, // in seconds
    val weight: Double? = null, // in kg
    val distance: Double? = null, // in km
    val restTime: Int? = null, // in seconds
    val targetMuscles: List<String>,
    val instructions: String,
    val isCompleted: Boolean = false
)

enum class ExerciseType {
    STRENGTH, CARDIO, FLEXIBILITY, BALANCE, ENDURANCE
}

data class FitnessStats(
    val totalWorkouts: Int,
    val totalDuration: Int, // in minutes
    val totalCalories: Int,
    val averageWorkoutDuration: Int,
    val weeklyGoal: Int,
    val weeklyProgress: Int,
    val currentStreak: Int,
    val longestStreak: Int
)

data class BodyMetrics(
    val weight: Double,
    val height: Double,
    val bodyFat: Double,
    val muscleMass: Double,
    val bmi: Double,
    val date: String
) {
    val bmiCategory: String
        get() = when {
            bmi < 18.5 -> "Underweight"
            bmi < 25.0 -> "Normal"
            bmi < 30.0 -> "Overweight"
            else -> "Obese"
        }
}

data class SportEvent(
    val id: String,
    val name: String,
    val sport: String,
    val date: String,
    val time: String,
    val location: String,
    val participants: Int,
    val maxParticipants: Int,
    val difficulty: String,
    val price: Double,
    val description: String,
    val organizer: String,
    val isRegistered: Boolean = false
)

data class Athlete(
    val id: String,
    val name: String,
    val sport: String,
    val position: String,
    val team: String,
    val stats: Map<String, Any>,
    val photo: String? = null,
    val ranking: Int? = null
)

data class Match(
    val id: String,
    val homeTeam: String,
    val awayTeam: String,
    val homeScore: Int,
    val awayScore: Int,
    val date: String,
    val time: String,
    val status: MatchStatus,
    val sport: String,
    val venue: String
)

enum class MatchStatus {
    SCHEDULED, LIVE, FINISHED, POSTPONED, CANCELLED
}

// Fitness Tracker Preview
@Preview(name = "Fitness Tracker", showBackground = true)
@Composable
fun FitnessTrackerPreview() {
    BaseAppTheme {
        FitnessTracker()
    }
}

@Composable
fun FitnessTracker() {
    val fitnessStats = FitnessStats(
        totalWorkouts = 45,
        totalDuration = 2250, // 37.5 hours
        totalCalories = 18750,
        averageWorkoutDuration = 50,
        weeklyGoal = 5,
        weeklyProgress = 3,
        currentStreak = 7,
        longestStreak = 21
    )
    
    val todayStats = mapOf(
        "Steps" to "8,542",
        "Calories" to "420",
        "Distance" to "6.2 km",
        "Active Time" to "45 min"
    )
    
    val recentWorkouts = listOf(
        WorkoutSession(
            id = "1",
            name = "Morning HIIT",
            type = WorkoutType.HIIT,
            duration = 30,
            caloriesBurned = 350,
            date = "Today",
            exercises = emptyList(),
            difficulty = WorkoutDifficulty.INTERMEDIATE,
            isCompleted = true
        ),
        WorkoutSession(
            id = "2",
            name = "Upper Body Strength",
            type = WorkoutType.STRENGTH,
            duration = 45,
            caloriesBurned = 280,
            date = "Yesterday",
            exercises = emptyList(),
            difficulty = WorkoutDifficulty.ADVANCED,
            isCompleted = true
        ),
        WorkoutSession(
            id = "3",
            name = "Evening Yoga",
            type = WorkoutType.YOGA,
            duration = 60,
            caloriesBurned = 180,
            date = "2 days ago",
            exercises = emptyList(),
            difficulty = WorkoutDifficulty.BEGINNER,
            isCompleted = true
        )
    )
    
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            FitnessHeader()
        }
        
        item {
            TodayStatsSection(todayStats)
        }
        
        item {
            WeeklyProgressSection(fitnessStats)
        }
        
        item {
            QuickWorkoutsSection()
        }
        
        item {
            RecentWorkoutsSection(recentWorkouts)
        }
    }
}

// Workout Planner Preview
@Preview(name = "Workout Planner", showBackground = true)
@Composable
fun WorkoutPlannerPreview() {
    BaseAppTheme {
        WorkoutPlanner()
    }
}

@Composable
fun WorkoutPlanner() {
    val exercises = listOf(
        Exercise(
            id = "1",
            name = "Push-ups",
            type = ExerciseType.STRENGTH,
            sets = 3,
            reps = 15,
            restTime = 60,
            targetMuscles = listOf("Chest", "Triceps", "Shoulders"),
            instructions = "Keep your body straight and lower yourself until your chest nearly touches the floor."
        ),
        Exercise(
            id = "2",
            name = "Squats",
            type = ExerciseType.STRENGTH,
            sets = 3,
            reps = 20,
            restTime = 90,
            targetMuscles = listOf("Quadriceps", "Glutes", "Hamstrings"),
            instructions = "Keep your feet shoulder-width apart and lower your body as if sitting back into a chair."
        ),
        Exercise(
            id = "3",
            name = "Plank",
            type = ExerciseType.STRENGTH,
            duration = 60,
            targetMuscles = listOf("Core", "Shoulders", "Back"),
            instructions = "Hold your body in a straight line from head to heels, engaging your core muscles."
        ),
        Exercise(
            id = "4",
            name = "Jumping Jacks",
            type = ExerciseType.CARDIO,
            duration = 45,
            restTime = 30,
            targetMuscles = listOf("Full Body"),
            instructions = "Jump while spreading your legs and raising your arms overhead, then return to starting position."
        )
    )
    
    val workout = WorkoutSession(
        id = "current",
        name = "Full Body HIIT",
        type = WorkoutType.HIIT,
        duration = 35,
        caloriesBurned = 400,
        date = "Today",
        exercises = exercises,
        difficulty = WorkoutDifficulty.INTERMEDIATE
    )
    
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            WorkoutPlannerHeader()
        }
        
        item {
            WorkoutOverview(workout)
        }
        
        item {
            ExercisesList(exercises)
        }
        
        item {
            WorkoutActions()
        }
    }
}

// Sports Analytics Preview
@Preview(name = "Sports Analytics", showBackground = true)
@Composable
fun SportsAnalyticsPreview() {
    BaseAppTheme {
        SportsAnalytics()
    }
}

@Composable
fun SportsAnalytics() {
    val matches = listOf(
        Match(
            id = "1",
            homeTeam = "Lakers",
            awayTeam = "Warriors",
            homeScore = 108,
            awayScore = 112,
            date = "Today",
            time = "8:00 PM",
            status = MatchStatus.LIVE,
            sport = "Basketball",
            venue = "Staples Center"
        ),
        Match(
            id = "2",
            homeTeam = "Celtics",
            awayTeam = "Heat",
            homeScore = 95,
            awayScore = 88,
            date = "Yesterday",
            time = "7:30 PM",
            status = MatchStatus.FINISHED,
            sport = "Basketball",
            venue = "TD Garden"
        ),
        Match(
            id = "3",
            homeTeam = "Nets",
            awayTeam = "76ers",
            homeScore = 0,
            awayScore = 0,
            date = "Tomorrow",
            time = "8:30 PM",
            status = MatchStatus.SCHEDULED,
            sport = "Basketball",
            venue = "Barclays Center"
        )
    )
    
    val topPlayers = listOf(
        Athlete(
            id = "1",
            name = "LeBron James",
            sport = "Basketball",
            position = "Forward",
            team = "Lakers",
            stats = mapOf(
                "PPG" to 25.8,
                "RPG" to 8.2,
                "APG" to 6.8
            ),
            ranking = 1
        ),
        Athlete(
            id = "2",
            name = "Stephen Curry",
            sport = "Basketball",
            position = "Guard",
            team = "Warriors",
            stats = mapOf(
                "PPG" to 29.5,
                "RPG" to 5.2,
                "APG" to 6.1
            ),
            ranking = 2
        ),
        Athlete(
            id = "3",
            name = "Giannis Antetokounmpo",
            sport = "Basketball",
            position = "Forward",
            team = "Bucks",
            stats = mapOf(
                "PPG" to 31.1,
                "RPG" to 11.8,
                "APG" to 5.4
            ),
            ranking = 3
        )
    )
    
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            SportsAnalyticsHeader()
        }
        
        item {
            LiveMatchesSection(matches.filter { it.status == MatchStatus.LIVE })
        }
        
        item {
            UpcomingMatchesSection(matches.filter { it.status == MatchStatus.SCHEDULED })
        }
        
        item {
            TopPlayersSection(topPlayers)
        }
        
        item {
            RecentResultsSection(matches.filter { it.status == MatchStatus.FINISHED })
        }
    }
}

// Body Metrics Tracker Preview
@Preview(name = "Body Metrics Tracker", showBackground = true)
@Composable
fun BodyMetricsTrackerPreview() {
    BaseAppTheme {
        BodyMetricsTracker()
    }
}

@Composable
fun BodyMetricsTracker() {
    val currentMetrics = BodyMetrics(
        weight = 75.2,
        height = 175.0,
        bodyFat = 15.8,
        muscleMass = 32.5,
        bmi = 24.6,
        date = "Today"
    )
    
    val weightHistory = listOf(
        76.8 to "Jan",
        76.2 to "Feb",
        75.9 to "Mar",
        75.5 to "Apr",
        75.2 to "May"
    )
    
    val goals = mapOf(
        "Target Weight" to "73.0 kg",
        "Body Fat" to "12.0%",
        "Muscle Mass" to "35.0 kg"
    )
    
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            BodyMetricsHeader()
        }
        
        item {
            CurrentMetricsSection(currentMetrics)
        }
        
        item {
            WeightProgressChart(weightHistory)
        }
        
        item {
            BodyCompositionSection(currentMetrics)
        }
        
        item {
            GoalsSection(goals)
        }
    }
}

// Sports Events Preview
@Preview(name = "Sports Events", showBackground = true)
@Composable
fun SportsEventsPreview() {
    BaseAppTheme {
        SportsEvents()
    }
}

@Composable
fun SportsEvents() {
    val events = listOf(
        SportEvent(
            id = "1",
            name = "Morning Run Club",
            sport = "Running",
            date = "Tomorrow",
            time = "7:00 AM",
            location = "Central Park",
            participants = 15,
            maxParticipants = 25,
            difficulty = "Beginner",
            price = 0.0,
            description = "Join us for a relaxing morning run through Central Park. Perfect for beginners!",
            organizer = "NYC Running Club"
        ),
        SportEvent(
            id = "2",
            name = "Basketball Tournament",
            sport = "Basketball",
            date = "This Weekend",
            time = "2:00 PM",
            location = "Community Center",
            participants = 32,
            maxParticipants = 32,
            difficulty = "Intermediate",
            price = 25.0,
            description = "3v3 basketball tournament with prizes for winners. Bring your A-game!",
            organizer = "Local Sports League"
        ),
        SportEvent(
            id = "3",
            name = "Yoga in the Park",
            sport = "Yoga",
            date = "Sunday",
            time = "9:00 AM",
            location = "Riverside Park",
            participants = 8,
            maxParticipants = 20,
            difficulty = "All Levels",
            price = 15.0,
            description = "Outdoor yoga session with beautiful river views. All skill levels welcome.",
            organizer = "Zen Yoga Studio"
        ),
        SportEvent(
            id = "4",
            name = "Cycling Challenge",
            sport = "Cycling",
            date = "Next Week",
            time = "6:00 AM",
            location = "Brooklyn Bridge",
            participants = 22,
            maxParticipants = 30,
            difficulty = "Advanced",
            price = 35.0,
            description = "50km cycling challenge across the city. For experienced cyclists only.",
            organizer = "NYC Cycling Club"
        )
    )
    
    val categories = listOf("All", "Running", "Basketball", "Yoga", "Cycling", "Swimming")
    var selectedCategory by remember { mutableStateOf("All") }
    
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            SportsEventsHeader()
        }
        
        item {
            EventCategoriesSection(categories, selectedCategory) { selectedCategory = it }
        }
        
        item {
            EventsList(events.filter { selectedCategory == "All" || it.sport == selectedCategory })
        }
    }
}

// Helper Composables
@Composable
fun FitnessHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = "Good morning,",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "Alex",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
        }
        
        Row {
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Notifications"
                )
            }
            
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Settings"
                )
            }
        }
    }
}

@Composable
fun TodayStatsSection(stats: Map<String, String>) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = "Today's Activity",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(stats.entries.toList()) { (key, value) ->
                StatCard(key, value, getStatIcon(key), getStatColor(key))
            }
        }
    }
}

@Composable
fun StatCard(title: String, value: String, icon: ImageVector, color: Color) {
    Card(
        modifier = Modifier
            .width(140.dp)
            .height(100.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = color,
                    modifier = Modifier.size(20.dp)
                )
            }
            
            Text(
                text = value,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

fun getStatIcon(stat: String): ImageVector {
    return when (stat) {
        "Steps" -> Icons.Default.DirectionsWalk
        "Calories" -> Icons.Default.LocalFireDepartment
        "Distance" -> Icons.Default.Straighten
        "Active Time" -> Icons.Default.Timer
        else -> Icons.Default.FitnessCenter
    }
}

fun getStatColor(stat: String): Color {
    return when (stat) {
        "Steps" -> Color(0xFF2196F3)
        "Calories" -> Color(0xFFFF5722)
        "Distance" -> Color(0xFF4CAF50)
        "Active Time" -> Color(0xFF9C27B0)
        else -> Color(0xFF607D8B)
    }
}

@Composable
fun WeeklyProgressSection(stats: FitnessStats) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "Weekly Progress",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "${stats.weeklyProgress}/${stats.weeklyGoal}",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Text(
                        text = "Workouts",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                Column {
                    Text(
                        text = "${stats.currentStreak}",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFF9800)
                    )
                    
                    Text(
                        text = "Day Streak",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            LinearProgressIndicator(
                progress = stats.weeklyProgress.toFloat() / stats.weeklyGoal.toFloat(),
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.primary
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "${((stats.weeklyProgress.toFloat() / stats.weeklyGoal.toFloat()) * 100).roundToInt()}% of weekly goal completed",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun QuickWorkoutsSection() {
    val quickWorkouts = listOf(
        "HIIT" to Icons.Default.FlashOn,
        "Strength" to Icons.Default.FitnessCenter,
        "Cardio" to Icons.Default.DirectionsRun,
        "Yoga" to Icons.Default.SelfImprovement
    )
    
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = "Quick Start",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            quickWorkouts.forEach { (title, icon) ->
                QuickWorkoutItem(title, icon)
            }
        }
    }
}

@Composable
fun QuickWorkoutItem(title: String, icon: ImageVector) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { }
    ) {
        Surface(
            shape = CircleShape,
            color = MaterialTheme.colorScheme.primaryContainer,
            modifier = Modifier.size(64.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.size(28.dp)
                )
            }
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = title,
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun RecentWorkoutsSection(workouts: List<WorkoutSession>) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Recent Workouts",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold
            )
            
            TextButton(onClick = { }) {
                Text("See All")
            }
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        workouts.forEach { workout ->
            WorkoutItem(workout)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun WorkoutItem(workout: WorkoutSession) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = CircleShape,
                color = getWorkoutTypeColor(workout.type).copy(alpha = 0.1f),
                modifier = Modifier.size(48.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = getWorkoutTypeIcon(workout.type),
                        contentDescription = workout.type.name,
                        tint = getWorkoutTypeColor(workout.type),
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = workout.name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold
                )
                
                Text(
                    text = "${workout.duration} min • ${workout.caloriesBurned} cal",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                Text(
                    text = workout.date,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            if (workout.isCompleted) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Completed",
                    tint = Color(0xFF4CAF50),
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

fun getWorkoutTypeIcon(type: WorkoutType): ImageVector {
    return when (type) {
        WorkoutType.CARDIO -> Icons.Default.DirectionsRun
        WorkoutType.STRENGTH -> Icons.Default.FitnessCenter
        WorkoutType.YOGA -> Icons.Default.SelfImprovement
        WorkoutType.PILATES -> Icons.Default.SelfImprovement
        WorkoutType.HIIT -> Icons.Default.FlashOn
        WorkoutType.RUNNING -> Icons.Default.DirectionsRun
        WorkoutType.CYCLING -> Icons.Default.DirectionsBike
        WorkoutType.SWIMMING -> Icons.Default.Pool
        WorkoutType.BOXING -> Icons.Default.SportsKabaddi
        WorkoutType.CROSSFIT -> Icons.Default.FitnessCenter
    }
}

fun getWorkoutTypeColor(type: WorkoutType): Color {
    return when (type) {
        WorkoutType.CARDIO -> Color(0xFFE91E63)
        WorkoutType.STRENGTH -> Color(0xFF2196F3)
        WorkoutType.YOGA -> Color(0xFF9C27B0)
        WorkoutType.PILATES -> Color(0xFF673AB7)
        WorkoutType.HIIT -> Color(0xFFFF5722)
        WorkoutType.RUNNING -> Color(0xFF4CAF50)
        WorkoutType.CYCLING -> Color(0xFFFF9800)
        WorkoutType.SWIMMING -> Color(0xFF00BCD4)
        WorkoutType.BOXING -> Color(0xFF795548)
        WorkoutType.CROSSFIT -> Color(0xFF607D8B)
    }
}

@Composable
fun WorkoutPlannerHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { }) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back"
            )
        }
        
        Text(
            text = "Workout Planner",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        
        IconButton(onClick = { }) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "More"
            )
        }
    }
}

@Composable
fun WorkoutOverview(workout: WorkoutSession) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = workout.name,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Text(
                        text = workout.type.name.lowercase().replaceFirstChar { it.uppercase() },
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = getDifficultyColor(workout.difficulty).copy(alpha = 0.1f)
                ) {
                    Text(
                        text = workout.difficulty.name.lowercase().replaceFirstChar { it.uppercase() },
                        style = MaterialTheme.typography.bodySmall,
                        color = getDifficultyColor(workout.difficulty),
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                WorkoutStatItem("Duration", "${workout.duration} min", Icons.Default.Timer)
                WorkoutStatItem("Calories", "${workout.caloriesBurned}", Icons.Default.LocalFireDepartment)
                WorkoutStatItem("Exercises", "${workout.exercises.size}", Icons.Default.FitnessCenter)
            }
        }
    }
}

@Composable
fun WorkoutStatItem(label: String, value: String, icon: ImageVector) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )
        
        Spacer(modifier = Modifier.height(4.dp))
        
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold
        )
        
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

fun getDifficultyColor(difficulty: WorkoutDifficulty): Color {
    return when (difficulty) {
        WorkoutDifficulty.BEGINNER -> Color(0xFF4CAF50)
        WorkoutDifficulty.INTERMEDIATE -> Color(0xFFFF9800)
        WorkoutDifficulty.ADVANCED -> Color(0xFFE91E63)
        WorkoutDifficulty.EXPERT -> Color(0xFFF44336)
    }
}

@Composable
fun ExercisesList(exercises: List<Exercise>) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = "Exercises (${exercises.size})",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        
        exercises.forEachIndexed { index, exercise ->
            ExerciseItem(exercise, index + 1)
            if (index < exercises.size - 1) {
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun ExerciseItem(exercise: Exercise, number: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = CircleShape,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(32.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = number.toString(),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = exercise.name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold
                )
                
                val details = buildString {
                    if (exercise.sets != null && exercise.reps != null) {
                        append("${exercise.sets} sets × ${exercise.reps} reps")
                    } else if (exercise.duration != null) {
                        append("${exercise.duration}s")
                    }
                    
                    if (exercise.weight != null) {
                        if (isNotEmpty()) append(" • ")
                        append("${exercise.weight}kg")
                    }
                    
                    if (exercise.restTime != null) {
                        if (isNotEmpty()) append(" • ")
                        append("${exercise.restTime}s rest")
                    }
                }
                
                if (details.isNotEmpty()) {
                    Text(
                        text = details,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                Text(
                    text = exercise.targetMuscles.joinToString(", "),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            if (exercise.isCompleted) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Completed",
                    tint = Color(0xFF4CAF50),
                    modifier = Modifier.size(24.dp)
                )
            } else {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Start",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Composable
fun WorkoutActions() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Button(
            onClick = { },
            modifier = Modifier.weight(1f)
        ) {
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = "Start",
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Start Workout")
        }
        
        OutlinedButton(
            onClick = { },
            modifier = Modifier.weight(1f)
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Edit",
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Customize")
        }
    }
}

@Composable
fun SportsAnalyticsHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Sports Analytics",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        
        IconButton(onClick = { }) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search"
            )
        }
    }
}

@Composable
fun LiveMatchesSection(matches: List<Match>) {
    if (matches.isNotEmpty()) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Live Matches",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            
            matches.forEach { match ->
                LiveMatchCard(match)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun LiveMatchCard(match: Match) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { },
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFE8F5E8)
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
                Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = Color(0xFFFF1744)
                ) {
                    Text(
                        text = "LIVE",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
                
                Text(
                    text = match.venue,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = match.homeTeam,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                    
                    Text(
                        text = match.homeScore.toString(),
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                
                Text(
                    text = "VS",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.SemiBold
                )
                
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = match.awayTeam,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                    
                    Text(
                        text = match.awayScore.toString(),
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

@Composable
fun UpcomingMatchesSection(matches: List<Match>) {
    if (matches.isNotEmpty()) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Upcoming Matches",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(matches) { match ->
                    UpcomingMatchCard(match)
                }
            }
        }
    }
}

@Composable
fun UpcomingMatchCard(match: Match) {
    Card(
        modifier = Modifier
            .width(200.dp)
            .clickable { }
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = match.date,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Text(
                text = match.time,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "${match.homeTeam} vs ${match.awayTeam}",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Text(
                text = match.venue,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun TopPlayersSection(players: List<Athlete>) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Top Players",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold
            )
            
            TextButton(onClick = { }) {
                Text("View All")
            }
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        players.forEach { player ->
            PlayerItem(player)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun PlayerItem(player: Athlete) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = CircleShape,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(48.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = player.ranking?.toString() ?: "?",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = player.name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold
                )
                
                Text(
                    text = "${player.position} • ${player.team}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    player.stats.entries.take(3).forEach { (key, value) ->
                        Text(
                            text = "$key: $value",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun RecentResultsSection(matches: List<Match>) {
    if (matches.isNotEmpty()) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Recent Results",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            
            matches.forEach { match ->
                ResultItem(match)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun ResultItem(match: Match) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "${match.homeTeam} ${match.homeScore} - ${match.awayScore} ${match.awayTeam}",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold
                )
                
                Text(
                    text = "${match.date} • ${match.venue}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Surface(
                shape = RoundedCornerShape(4.dp),
                color = MaterialTheme.colorScheme.surfaceVariant
            ) {
                Text(
                    text = "FT",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }
    }
}

@Composable
fun BodyMetricsHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = "Body Metrics",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            
            Text(
                text = "Track your progress",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        
        IconButton(onClick = { }) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add measurement"
            )
        }
    }
}

@Composable
fun CurrentMetricsSection(metrics: BodyMetrics) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "Current Metrics",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                MetricItem("Weight", "${metrics.weight} kg", Icons.Default.MonitorWeight)
                MetricItem("BMI", metrics.bmi.toString(), Icons.Default.Analytics)
                MetricItem("Body Fat", "${metrics.bodyFat}%", Icons.Default.Percent)
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = getBMIColor(metrics.bmi).copy(alpha = 0.1f),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "BMI Category: ${metrics.bmiCategory}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = getBMIColor(metrics.bmi),
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(12.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun MetricItem(label: String, value: String, icon: ImageVector) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
        
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

fun getBMIColor(bmi: Double): Color {
    return when {
        bmi < 18.5 -> Color(0xFF2196F3)
        bmi < 25.0 -> Color(0xFF4CAF50)
        bmi < 30.0 -> Color(0xFFFF9800)
        else -> Color(0xFFF44336)
    }
}

@Composable
fun WeightProgressChart(weightHistory: List<Pair<Double, String>>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "Weight Progress",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            // Simple chart representation
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                weightHistory.forEach { (weight, month) ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "${weight}kg",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        
                        Spacer(modifier = Modifier.height(4.dp))
                        
                        Box(
                            modifier = Modifier
                                .width(24.dp)
                                .height((weight * 2).dp)
                                .background(
                                    MaterialTheme.colorScheme.primary,
                                    RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp)
                                )
                        )
                        
                        Spacer(modifier = Modifier.height(4.dp))
                        
                        Text(
                            text = month,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BodyCompositionSection(metrics: BodyMetrics) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "Body Composition",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                CompositionItem("Muscle Mass", "${metrics.muscleMass} kg", Color(0xFF4CAF50))
                CompositionItem("Body Fat", "${metrics.bodyFat}%", Color(0xFFFF9800))
                CompositionItem("Water", "${(100 - metrics.bodyFat - (metrics.muscleMass / metrics.weight * 100)).roundToInt()}%", Color(0xFF2196F3))
            }
        }
    }
}

@Composable
fun CompositionItem(label: String, value: String, color: Color) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(color.copy(alpha = 0.1f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium,
                color = color,
                fontWeight = FontWeight.Bold
            )
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun GoalsSection(goals: Map<String, String>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "Goals",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            goals.entries.forEach { (goal, target) ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = goal,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    
                    Text(
                        text = target,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

@Composable
fun SportsEventsHeader() {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = "Sports Events",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        
        Text(
            text = "Discover and join local sports activities",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun EventCategoriesSection(
    categories: List<String>,
    selectedCategory: String,
    onCategorySelected: (String) -> Unit
) {
    LazyRow(
        modifier = Modifier.padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(categories) { category ->
            FilterChip(
                onClick = { onCategorySelected(category) },
                label = { Text(category) },
                selected = category == selectedCategory
            )
        }
    }
}

@Composable
fun EventsList(events: List<SportEvent>) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        events.forEach { event ->
            EventCard(event)
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
fun EventCard(event: SportEvent) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
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
                        text = event.name,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                    
                    Text(
                        text = event.sport,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = getDifficultyColorForEvent(event.difficulty).copy(alpha = 0.1f)
                ) {
                    Text(
                        text = event.difficulty,
                        style = MaterialTheme.typography.bodySmall,
                        color = getDifficultyColorForEvent(event.difficulty),
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = event.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                EventDetailItem(Icons.Default.CalendarToday, "${event.date} • ${event.time}")
                EventDetailItem(Icons.Default.LocationOn, event.location)
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                EventDetailItem(Icons.Default.Group, "${event.participants}/${event.maxParticipants} joined")
                if (event.price > 0) {
                    EventDetailItem(Icons.Default.AttachMoney, "$${event.price}")
                } else {
                    EventDetailItem(Icons.Default.MoneyOff, "Free")
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "by ${event.organizer}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                if (event.isRegistered) {
                    OutlinedButton(
                        onClick = { },
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color(0xFF4CAF50)
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Registered",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Registered")
                    }
                } else if (event.participants >= event.maxParticipants) {
                    OutlinedButton(
                        onClick = { },
                        enabled = false
                    ) {
                        Text("Full")
                    }
                } else {
                    Button(
                        onClick = { }
                    ) {
                        Text("Join Event")
                    }
                }
            }
        }
    }
}

@Composable
fun EventDetailItem(icon: ImageVector, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(16.dp)
        )
        
        Spacer(modifier = Modifier.width(4.dp))
        
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

fun getDifficultyColorForEvent(difficulty: String): Color {
    return when (difficulty.lowercase()) {
        "beginner", "all levels" -> Color(0xFF4CAF50)
        "intermediate" -> Color(0xFFFF9800)
        "advanced" -> Color(0xFFE91E63)
        "expert" -> Color(0xFFF44336)
        else -> Color(0xFF607D8B)
    }
}

// Register previews with PreviewRegistry
fun registerSportsPreviews() {
    PreviewRegistry.registerPreview(
        PreviewItem(
            id = "sports_fitness_tracker",
            title = "Fitness Tracker",
            description = "Comprehensive fitness tracking dashboard with workout sessions, progress charts, and activity monitoring",
            category = PreviewCategory.SPORTS,
            icon = Icons.Default.FitnessCenter,
            difficulty = PreviewDifficulty.INTERMEDIATE,
            tags = listOf("fitness", "tracking", "dashboard", "charts", "health"),
            content = { FitnessTrackerPreview() }
        )
    )
    
    PreviewRegistry.registerPreview(
        PreviewItem(
            id = "sports_workout_planner",
            title = "Workout Planner",
            description = "Interactive workout planning interface with exercise library, custom routines, and progress tracking",
            category = PreviewCategory.SPORTS,
            icon = Icons.Default.Schedule,
            difficulty = PreviewDifficulty.INTERMEDIATE,
            tags = listOf("workout", "planning", "exercises", "routines", "fitness"),
            content = { WorkoutPlannerPreview() }
        )
    )
    
    PreviewRegistry.registerPreview(
        PreviewItem(
            id = "sports_analytics",
            title = "Sports Analytics",
            description = "Advanced sports analytics dashboard with live matches, player statistics, and performance insights",
            category = PreviewCategory.SPORTS,
            icon = Icons.Default.Analytics,
            difficulty = PreviewDifficulty.ADVANCED,
            tags = listOf("analytics", "statistics", "matches", "players", "insights"),
            content = { SportsAnalyticsPreview() }
        )
    )
    
    PreviewRegistry.registerPreview(
        PreviewItem(
            id = "sports_body_metrics",
            title = "Body Metrics Tracker",
            description = "Detailed body composition tracking with BMI calculator, weight progress, and health goals",
            category = PreviewCategory.SPORTS,
            icon = Icons.Default.MonitorWeight,
            difficulty = PreviewDifficulty.INTERMEDIATE,
            tags = listOf("body", "metrics", "BMI", "weight", "health", "goals"),
            content = { BodyMetricsTrackerPreview() }
        )
    )
    
    PreviewRegistry.registerPreview(
        PreviewItem(
            id = "sports_events",
            title = "Sports Events",
            description = "Local sports events discovery platform with registration, categories, and event management",
            category = PreviewCategory.SPORTS,
            icon = Icons.Default.Event,
            difficulty = PreviewDifficulty.INTERMEDIATE,
            tags = listOf("events", "sports", "local", "registration", "community"),
            content = { SportsEventsPreview() }
        )
    )
}