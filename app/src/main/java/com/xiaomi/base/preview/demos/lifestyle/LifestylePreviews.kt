package com.xiaomi.base.preview.demos.lifestyle

import androidx.compose.animation.core.*
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
import androidx.compose.material3.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
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
import com.xiaomi.base.ui.theme.BaseAppTheme
import kotlin.math.*

// Register Lifestyle previews
fun registerLifestylePreviews() {
    PreviewRegistry.registerPreview(
        PreviewItem(
            id = "weather_dashboard",
            title = "Weather Dashboard",
            description = "Comprehensive weather app with forecasts, maps, and alerts",
            category = PreviewCategory.LIFESTYLE,
            icon = Icons.Default.WbSunny,
            difficulty = PreviewDifficulty.INTERMEDIATE,
            tags = listOf("weather", "forecast", "temperature", "climate"),
            estimatedTime = "40 min",
            content = { WeatherDashboardPreview() }
        )
    )
    
    PreviewRegistry.registerPreview(
        PreviewItem(
            id = "fitness_tracker",
            title = "Fitness & Sports Tracker",
            description = "Track workouts, sports activities, and fitness goals",
            category = PreviewCategory.LIFESTYLE,
            icon = Icons.Default.FitnessCenter,
            difficulty = PreviewDifficulty.ADVANCED,
            tags = listOf("fitness", "sports", "workout", "tracking"),
            estimatedTime = "45 min",
            content = { FitnessTrackerPreview() }
        )
    )
    
    PreviewRegistry.registerPreview(
        PreviewItem(
            id = "nutrition_planner",
            title = "Nutrition & Meal Planner",
            description = "Plan meals, track nutrition, and manage dietary goals",
            category = PreviewCategory.LIFESTYLE,
            icon = Icons.Default.Restaurant,
            difficulty = PreviewDifficulty.INTERMEDIATE,
            tags = listOf("nutrition", "meal", "diet", "calories"),
            estimatedTime = "35 min",
            content = { NutritionPlannerPreview() }
        )
    )
    
    PreviewRegistry.registerPreview(
        PreviewItem(
            id = "travel_companion",
            title = "Travel Companion",
            description = "Travel planning, booking, and itinerary management",
            category = PreviewCategory.LIFESTYLE,
            icon = Icons.Default.Flight,
            difficulty = PreviewDifficulty.INTERMEDIATE,
            tags = listOf("travel", "booking", "itinerary", "vacation"),
            estimatedTime = "40 min",
            content = { TravelCompanionPreview() }
        )
    )
    
    PreviewRegistry.registerPreview(
        PreviewItem(
            id = "home_automation",
            title = "Smart Home Control",
            description = "Control smart home devices, automation, and energy monitoring",
            category = PreviewCategory.LIFESTYLE,
            icon = Icons.Default.Home,
            difficulty = PreviewDifficulty.ADVANCED,
            tags = listOf("smart home", "automation", "IoT", "control"),
            estimatedTime = "50 min",
            content = { HomeAutomationPreview() }
        )
    )
}

@Preview(showBackground = true)
@Composable
fun WeatherDashboardPreview() {
    BaseAppTheme {
        BasePreviewScreen(
            title = "Weather Dashboard",
            subtitle = "Current conditions and forecasts"
        ) {
            var selectedLocation by remember { mutableStateOf("Ho Chi Minh City") }
            var temperatureUnit by remember { mutableStateOf(TemperatureUnit.CELSIUS) }
            
            val currentWeather = remember {
                WeatherData(
                    location = "Ho Chi Minh City",
                    temperature = 32,
                    condition = WeatherCondition.SUNNY,
                    humidity = 75,
                    windSpeed = 12,
                    uvIndex = 8,
                    visibility = 10
                )
            }
            
            val forecast = remember {
                listOf(
                    DailyForecast("Today", WeatherCondition.SUNNY, 32, 26),
                    DailyForecast("Tomorrow", WeatherCondition.PARTLY_CLOUDY, 30, 24),
                    DailyForecast("Wednesday", WeatherCondition.RAINY, 28, 22),
                    DailyForecast("Thursday", WeatherCondition.THUNDERSTORM, 27, 21),
                    DailyForecast("Friday", WeatherCondition.CLOUDY, 29, 23)
                )
            }
            
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    // Current weather card
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = currentWeather.condition.backgroundColor
                        )
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
                                        text = currentWeather.location,
                                        style = MaterialTheme.typography.headlineSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                    Text(
                                        text = currentWeather.condition.displayName,
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = Color.White.copy(alpha = 0.9f)
                                    )
                                }
                                
                                IconButton(
                                    onClick = {
                                        temperatureUnit = if (temperatureUnit == TemperatureUnit.CELSIUS) {
                                            TemperatureUnit.FAHRENHEIT
                                        } else {
                                            TemperatureUnit.CELSIUS
                                        }
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Thermostat,
                                        contentDescription = "Temperature Unit",
                                        tint = Color.White
                                    )
                                }
                            }
                            
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = currentWeather.condition.icon,
                                    contentDescription = currentWeather.condition.displayName,
                                    modifier = Modifier.size(64.dp),
                                    tint = Color.White
                                )
                                
                                Spacer(modifier = Modifier.width(16.dp))
                                
                                Text(
                                    text = "${currentWeather.temperature}°${temperatureUnit.symbol}",
                                    style = MaterialTheme.typography.displayLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                            
                            Spacer(modifier = Modifier.height(20.dp))
                            
                            // Weather details
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                WeatherDetailItem(
                                    icon = Icons.Default.Water,
                                    label = "Humidity",
                                    value = "${currentWeather.humidity}%"
                                )
                                
                                WeatherDetailItem(
                                    icon = Icons.Default.Air,
                                    label = "Wind",
                                    value = "${currentWeather.windSpeed} km/h"
                                )
                                
                                WeatherDetailItem(
                                    icon = Icons.Default.WbSunny,
                                    label = "UV Index",
                                    value = currentWeather.uvIndex.toString()
                                )
                                
                                WeatherDetailItem(
                                    icon = Icons.Default.Visibility,
                                    label = "Visibility",
                                    value = "${currentWeather.visibility} km"
                                )
                            }
                        }
                    }
                }
                
                item {
                    // Hourly forecast
                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "Hourly Forecast",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold
                            )
                            
                            Spacer(modifier = Modifier.height(12.dp))
                            
                            LazyRow(
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                items(24) { hour ->
                                    HourlyForecastItem(
                                        time = "${hour}:00",
                                        temperature = (28..34).random(),
                                        condition = WeatherCondition.values().random()
                                    )
                                }
                            }
                        }
                    }
                }
                
                item {
                    // 5-day forecast
                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "5-Day Forecast",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold
                            )
                            
                            Spacer(modifier = Modifier.height(12.dp))
                            
                            forecast.forEach { day ->
                                DailyForecastItem(day)
                                if (day != forecast.last()) {
                                    Divider(
                                        modifier = Modifier.padding(vertical = 8.dp),
                                        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                                    )
                                }
                            }
                        }
                    }
                }
                
                item {
                    // Weather map
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
                                    text = "Weather Map",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.SemiBold
                                )
                                
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    FilterChip(
                                        onClick = { },
                                        label = { Text("Rain") },
                                        selected = true
                                    )
                                    
                                    FilterChip(
                                        onClick = { },
                                        label = { Text("Clouds") },
                                        selected = false
                                    )
                                }
                            }
                            
                            Spacer(modifier = Modifier.height(12.dp))
                            
                            // Simulated weather map
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp)
                                    .background(
                                        Color(0xFF1976D2),
                                        RoundedCornerShape(8.dp)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Canvas(
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    // Draw simulated weather patterns
                                    val centerX = size.width / 2
                                    val centerY = size.height / 2
                                    
                                    // Rain clouds
                                    drawCircle(
                                        color = Color.White.copy(alpha = 0.3f),
                                        radius = 40.dp.toPx(),
                                        center = Offset(centerX - 60.dp.toPx(), centerY - 30.dp.toPx())
                                    )
                                    
                                    drawCircle(
                                        color = Color.White.copy(alpha = 0.4f),
                                        radius = 50.dp.toPx(),
                                        center = Offset(centerX + 40.dp.toPx(), centerY + 20.dp.toPx())
                                    )
                                    
                                    // Location marker
                                    drawCircle(
                                        color = Color.Red,
                                        radius = 8.dp.toPx(),
                                        center = Offset(centerX, centerY)
                                    )
                                    
                                    drawCircle(
                                        color = Color.White,
                                        radius = 4.dp.toPx(),
                                        center = Offset(centerX, centerY)
                                    )
                                }
                                
                                Text(
                                    text = "Interactive Weather Map",
                                    color = Color.White,
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier.align(Alignment.BottomCenter)
                                        .padding(16.dp)
                                )
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
fun FitnessTrackerPreview() {
    BaseAppTheme {
        ComponentShowcase(
            title = "Fitness Tracker",
            description = "Track workouts and sports activities"
        ) {
            var selectedTab by remember { mutableStateOf(FitnessTab.OVERVIEW) }
            var selectedWorkout by remember { mutableStateOf(WorkoutType.RUNNING) }
            
            val fitnessData = remember {
                FitnessData(
                    dailySteps = 8547,
                    caloriesBurned = 420,
                    activeMinutes = 65,
                    heartRate = 72,
                    weeklyGoalProgress = 0.75f
                )
            }
            
            val workouts = remember {
                listOf(
                    WorkoutSession("Morning Run", WorkoutType.RUNNING, 35, 280),
                    WorkoutSession("Gym Session", WorkoutType.STRENGTH, 60, 350),
                    WorkoutSession("Yoga", WorkoutType.YOGA, 45, 120),
                    WorkoutSession("Cycling", WorkoutType.CYCLING, 40, 320)
                )
            }
            
            InteractiveDemo(
                title = "Smart Home Control",
                description = "Control your smart home devices and automation",
                controls = {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(FitnessTab.values()) { tab ->
                            FilterChip(
                                onClick = { selectedTab = tab },
                                label = { Text(tab.displayName) },
                                selected = selectedTab == tab,
                                leadingIcon = {
                                    Icon(
                                        imageVector = tab.icon,
                                        contentDescription = tab.displayName,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            )
                        }
                    }
                }
            ) {
                when (selectedTab) {
                    FitnessTab.OVERVIEW -> {
                        FitnessOverview(fitnessData)
                    }
                    FitnessTab.WORKOUTS -> {
                        WorkoutsList(workouts, selectedWorkout) { selectedWorkout = it }
                    }
                    FitnessTab.PROGRESS -> {
                        ProgressTracking(fitnessData)
                    }
                    FitnessTab.CHALLENGES -> {
                        FitnessChallenges()
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NutritionPlannerPreview() {
    BaseAppTheme {
        ComponentShowcase(
            title = "Nutrition Planner",
            description = "Plan meals and track nutrition"
        ) {
            var selectedMealType by remember { mutableStateOf(MealType.BREAKFAST) }
            var calorieGoal by remember { mutableStateOf(2000) }
            
            val nutritionData = remember {
                NutritionData(
                    caloriesConsumed = 1450,
                    caloriesGoal = calorieGoal,
                    protein = 85,
                    carbs = 180,
                    fat = 65,
                    fiber = 25,
                    sugar = 45
                )
            }
            
            val mealPlan = remember {
                mapOf(
                    MealType.BREAKFAST to listOf(
                        FoodItem("Oatmeal with Berries", 320, "Carbs: 45g, Protein: 12g"),
                        FoodItem("Greek Yogurt", 150, "Protein: 15g, Fat: 8g")
                    ),
                    MealType.LUNCH to listOf(
                        FoodItem("Grilled Chicken Salad", 420, "Protein: 35g, Carbs: 15g"),
                        FoodItem("Quinoa Bowl", 380, "Carbs: 55g, Protein: 14g")
                    ),
                    MealType.DINNER to listOf(
                        FoodItem("Salmon with Vegetables", 480, "Protein: 40g, Fat: 25g"),
                        FoodItem("Brown Rice", 220, "Carbs: 45g, Fiber: 4g")
                    ),
                    MealType.SNACKS to listOf(
                        FoodItem("Mixed Nuts", 180, "Fat: 16g, Protein: 6g"),
                        FoodItem("Apple with Peanut Butter", 190, "Carbs: 25g, Fat: 8g")
                    )
                )
            }
            
            InteractiveDemo(
                title = "Nutrition Tracker",
                description = "Track your daily nutrition and meal planning",
                controls = {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Daily Calorie Goal: $calorieGoal",
                            style = MaterialTheme.typography.labelMedium
                        )
                        
                        Slider(
                            value = calorieGoal.toFloat(),
                            onValueChange = { calorieGoal = it.toInt() },
                            valueRange = 1200f..3000f,
                            modifier = Modifier.width(200.dp)
                        )
                    }
                }
            ) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        // Nutrition overview
                        NutritionOverviewCard(nutritionData)
                    }
                    
                    item {
                        // Meal type selector
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(MealType.values()) { mealType ->
                                FilterChip(
                                    onClick = { selectedMealType = mealType },
                                    label = { Text(mealType.displayName) },
                                    selected = selectedMealType == mealType,
                                    leadingIcon = {
                                        Icon(
                                            imageVector = mealType.icon,
                                            contentDescription = mealType.displayName,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                )
                            }
                        }
                    }
                    
                    item {
                        // Meal plan for selected type
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
                                        text = selectedMealType.displayName,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    
                                    IconButton(
                                        onClick = { }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Add,
                                            contentDescription = "Add Food"
                                        )
                                    }
                                }
                                
                                Spacer(modifier = Modifier.height(12.dp))
                                
                                mealPlan[selectedMealType]?.forEach { food ->
                                    FoodItemCard(food)
                                    Spacer(modifier = Modifier.height(8.dp))
                                }
                            }
                        }
                    }
                    
                    item {
                        // Nutrition goals
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
                                    text = "Daily Nutrition Goals",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                                
                                Spacer(modifier = Modifier.height(12.dp))
                                
                                val goals = listOf(
                                    "Protein: ${nutritionData.protein}g / 120g",
                                    "Carbs: ${nutritionData.carbs}g / 250g",
                                    "Fat: ${nutritionData.fat}g / 80g",
                                    "Fiber: ${nutritionData.fiber}g / 30g"
                                )
                                
                                goals.forEach { goal ->
                                    Text(
                                        text = "• $goal",
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
fun TravelCompanionPreview() {
    BaseAppTheme {
        ComponentShowcase(
            title = "Travel Companion",
            description = "Plan and manage your travels"
        ) {
            var selectedTrip by remember { mutableStateOf(0) }
            
            val trips = remember {
                listOf(
                    TravelTrip(
                        destination = "Tokyo, Japan",
                        dates = "Dec 15-22, 2024",
                        status = TripStatus.UPCOMING,
                        imageColor = Color(0xFFE91E63)
                    ),
                    TravelTrip(
                        destination = "Paris, France",
                        dates = "Jan 10-17, 2025",
                        status = TripStatus.PLANNING,
                        imageColor = Color(0xFF3F51B5)
                    ),
                    TravelTrip(
                        destination = "Bali, Indonesia",
                        dates = "Nov 5-12, 2024",
                        status = TripStatus.COMPLETED,
                        imageColor = Color(0xFF4CAF50)
                    )
                )
            }
            
            val itinerary = remember {
                listOf(
                    ItineraryItem("Flight to Tokyo", "6:00 AM", ItineraryType.FLIGHT),
                    ItineraryItem("Check-in Hotel", "2:00 PM", ItineraryType.ACCOMMODATION),
                    ItineraryItem("Senso-ji Temple", "4:00 PM", ItineraryType.ACTIVITY),
                    ItineraryItem("Dinner at Shibuya", "7:00 PM", ItineraryType.DINING)
                )
            }
            
            InteractiveDemo(
                title = "Travel Companion",
                description = "Plan and manage your travel itinerary",
                controls = {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = { }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add Trip",
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("New Trip")
                        }
                        
                        OutlinedButton(
                            onClick = { }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search",
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Explore")
                        }
                    }
                }
            ) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        // Trip selector
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(trips.size) { index ->
                                TripCard(
                                    trip = trips[index],
                                    isSelected = selectedTrip == index,
                                    onClick = { selectedTrip = index }
                                )
                            }
                        }
                    }
                    
                    item {
                        // Trip details
                        val currentTrip = trips[selectedTrip]
                        
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
                                    Column {
                                        Text(
                                            text = currentTrip.destination,
                                            style = MaterialTheme.typography.titleLarge,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = currentTrip.dates,
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                    
                                    Surface(
                                        shape = RoundedCornerShape(16.dp),
                                        color = currentTrip.status.color.copy(alpha = 0.2f)
                                    ) {
                                        Text(
                                            text = currentTrip.status.displayName,
                                            style = MaterialTheme.typography.labelMedium,
                                            color = currentTrip.status.color,
                                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                                        )
                                    }
                                }
                                
                                Spacer(modifier = Modifier.height(16.dp))
                                
                                // Quick actions
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    TravelActionButton(
                                        icon = Icons.Default.Flight,
                                        label = "Flights",
                                        onClick = { }
                                    )
                                    
                                    TravelActionButton(
                                        icon = Icons.Default.Hotel,
                                        label = "Hotels",
                                        onClick = { }
                                    )
                                    
                                    TravelActionButton(
                                        icon = Icons.Default.Map,
                                        label = "Explore",
                                        onClick = { }
                                    )
                                    
                                    TravelActionButton(
                                        icon = Icons.Default.Restaurant,
                                        label = "Dining",
                                        onClick = { }
                                    )
                                }
                            }
                        }
                    }
                    
                    item {
                        // Itinerary
                        Card(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Text(
                                    text = "Today's Itinerary",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.SemiBold
                                )
                                
                                Spacer(modifier = Modifier.height(12.dp))
                                
                                itinerary.forEach { item ->
                                    ItineraryItemCard(item)
                                    if (item != itinerary.last()) {
                                        Spacer(modifier = Modifier.height(8.dp))
                                    }
                                }
                            }
                        }
                    }
                    
                    item {
                        // Travel tips
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.tertiaryContainer
                            )
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Lightbulb,
                                        contentDescription = "Tips",
                                        tint = MaterialTheme.colorScheme.onTertiaryContainer
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Travel Tips",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.SemiBold,
                                        color = MaterialTheme.colorScheme.onTertiaryContainer
                                    )
                                }
                                
                                Spacer(modifier = Modifier.height(8.dp))
                                
                                val tips = listOf(
                                    "Download offline maps before traveling",
                                    "Keep digital copies of important documents",
                                    "Learn basic phrases in the local language",
                                    "Check visa requirements and validity"
                                )
                                
                                tips.forEach { tip ->
                                    Text(
                                        text = "• $tip",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onTertiaryContainer
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
fun HomeAutomationPreview() {
    BaseAppTheme {
        ComponentShowcase(
            title = "Smart Home Control",
            description = "Control and monitor smart devices",
            icon = Icons.Default.Home
        ) {
            var selectedRoom by remember { mutableStateOf("Living Room") }
            
            val rooms = listOf("Living Room", "Bedroom", "Kitchen", "Bathroom")
            
            val devices = remember {
                mapOf(
                    "Living Room" to listOf(
                        SmartDevice("Smart TV", DeviceType.ENTERTAINMENT, true, "Playing Netflix"),
                        SmartDevice("Air Conditioner", DeviceType.CLIMATE, true, "22°C"),
                        SmartDevice("Smart Lights", DeviceType.LIGHTING, false, "Off"),
                        SmartDevice("Sound System", DeviceType.ENTERTAINMENT, false, "Standby")
                    ),
                    "Bedroom" to listOf(
                        SmartDevice("Bedroom Lights", DeviceType.LIGHTING, true, "Dimmed 30%"),
                        SmartDevice("Smart Curtains", DeviceType.AUTOMATION, false, "Closed"),
                        SmartDevice("Air Purifier", DeviceType.CLIMATE, true, "Auto mode")
                    ),
                    "Kitchen" to listOf(
                        SmartDevice("Smart Fridge", DeviceType.APPLIANCE, true, "Normal"),
                        SmartDevice("Coffee Maker", DeviceType.APPLIANCE, false, "Ready"),
                        SmartDevice("Kitchen Lights", DeviceType.LIGHTING, true, "Bright")
                    ),
                    "Bathroom" to listOf(
                        SmartDevice("Smart Mirror", DeviceType.AUTOMATION, false, "Off"),
                        SmartDevice("Heated Floor", DeviceType.CLIMATE, true, "25°C")
                    )
                )
            }
            
            val energyData = remember {
                EnergyData(
                    currentUsage = 2.4f,
                    dailyUsage = 18.6f,
                    monthlyBudget = 150f,
                    monthlyUsed = 89.2f
                )
            }
            
            InteractiveDemo(
                title = "Smart Home Control",
                description = "Control your smart home devices and automation",
                controls = {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(rooms) { room ->
                            FilterChip(
                                onClick = { selectedRoom = room },
                                label = { Text(room) },
                                selected = selectedRoom == room
                            )
                        }
                    }
                }
            ) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        // Energy overview
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
                                    text = "Energy Usage",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                                
                                Spacer(modifier = Modifier.height(12.dp))
                                
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    EnergyMetric(
                                        label = "Current",
                                        value = "${energyData.currentUsage} kW",
                                        color = MaterialTheme.colorScheme.onPrimaryContainer
                                    )
                                    
                                    EnergyMetric(
                                        label = "Today",
                                        value = "${energyData.dailyUsage} kWh",
                                        color = MaterialTheme.colorScheme.onPrimaryContainer
                                    )
                                    
                                    EnergyMetric(
                                        label = "Monthly",
                                        value = "${energyData.monthlyUsed}/${energyData.monthlyBudget} kWh",
                                        color = MaterialTheme.colorScheme.onPrimaryContainer
                                    )
                                }
                            }
                        }
                    }
                    
                    item {
                        // Room devices
                        Card(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Text(
                                    text = "$selectedRoom Devices",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.SemiBold
                                )
                                
                                Spacer(modifier = Modifier.height(12.dp))
                                
                                devices[selectedRoom]?.forEach { device ->
                                    SmartDeviceCard(device)
                                    if (device != devices[selectedRoom]?.last()) {
                                        Spacer(modifier = Modifier.height(8.dp))
                                    }
                                }
                            }
                        }
                    }
                    
                    item {
                        // Automation scenes
                        Card(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Text(
                                    text = "Automation Scenes",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.SemiBold
                                )
                                
                                Spacer(modifier = Modifier.height(12.dp))
                                
                                val scenes = listOf(
                                    AutomationScene("Good Morning", "Turn on lights, start coffee", Icons.Default.WbSunny),
                                    AutomationScene("Movie Time", "Dim lights, turn on TV", Icons.Default.Movie),
                                    AutomationScene("Sleep Mode", "Turn off all lights, lock doors", Icons.Default.Bedtime),
                                    AutomationScene("Away Mode", "Turn off devices, activate security", Icons.Default.Security)
                                )
                                
                                scenes.forEach { scene ->
                                    AutomationSceneCard(scene)
                                    if (scene != scenes.last()) {
                                        Spacer(modifier = Modifier.height(8.dp))
                                    }
                                }
                            }
                        }
                    }
                    
                    item {
                        // Security status
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.secondaryContainer
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
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Security,
                                            contentDescription = "Security",
                                            tint = MaterialTheme.colorScheme.onSecondaryContainer
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = "Home Security",
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.SemiBold,
                                            color = MaterialTheme.colorScheme.onSecondaryContainer
                                        )
                                    }
                                    
                                    Surface(
                                        shape = RoundedCornerShape(16.dp),
                                        color = Color.Green.copy(alpha = 0.2f)
                                    ) {
                                        Text(
                                            text = "Armed",
                                            style = MaterialTheme.typography.labelMedium,
                                            color = Color.Green,
                                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                                        )
                                    }
                                }
                                
                                Spacer(modifier = Modifier.height(8.dp))
                                
                                Text(
                                    text = "All doors locked • Motion sensors active • Cameras recording",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSecondaryContainer
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

// Data classes and enums
enum class TemperatureUnit(val symbol: String) {
    CELSIUS("C"),
    FAHRENHEIT("F")
}

enum class WeatherCondition(
    val displayName: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val backgroundColor: Color
) {
    SUNNY("Sunny", Icons.Default.WbSunny, Color(0xFF2196F3)),
    PARTLY_CLOUDY("Partly Cloudy", Icons.Default.Cloud, Color(0xFF607D8B)),
    CLOUDY("Cloudy", Icons.Default.Cloud, Color(0xFF9E9E9E)),
    RAINY("Rainy", Icons.Default.Umbrella, Color(0xFF3F51B5)),
    THUNDERSTORM("Thunderstorm", Icons.Default.Thunderstorm, Color(0xFF673AB7))
}

enum class FitnessTab(
    val displayName: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
) {
    OVERVIEW("Overview", Icons.Default.Dashboard),
    WORKOUTS("Workouts", Icons.Default.FitnessCenter),
    PROGRESS("Progress", Icons.Default.TrendingUp),
    CHALLENGES("Challenges", Icons.Default.EmojiEvents)
}

enum class WorkoutType(
    val displayName: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val color: Color
) {
    RUNNING("Running", Icons.Default.DirectionsRun, Color(0xFF4CAF50)),
    CYCLING("Cycling", Icons.Default.DirectionsBike, Color(0xFF2196F3)),
    STRENGTH("Strength", Icons.Default.FitnessCenter, Color(0xFFFF5722)),
    YOGA("Yoga", Icons.Default.SelfImprovement, Color(0xFF9C27B0))
}

enum class MealType(
    val displayName: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
) {
    BREAKFAST("Breakfast", Icons.Default.WbSunny),
    LUNCH("Lunch", Icons.Default.LunchDining),
    DINNER("Dinner", Icons.Default.DinnerDining),
    SNACKS("Snacks", Icons.Default.Cookie)
}

enum class TripStatus(
    val displayName: String,
    val color: Color
) {
    PLANNING("Planning", Color(0xFF2196F3)),
    UPCOMING("Upcoming", Color(0xFFFF9800)),
    ACTIVE("Active", Color(0xFF4CAF50)),
    COMPLETED("Completed", Color(0xFF9E9E9E))
}

enum class ItineraryType(
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val color: Color
) {
    FLIGHT(Icons.Default.Flight, Color(0xFF2196F3)),
    ACCOMMODATION(Icons.Default.Hotel, Color(0xFF4CAF50)),
    ACTIVITY(Icons.Default.Place, Color(0xFFFF9800)),
    DINING(Icons.Default.Restaurant, Color(0xFFE91E63))
}

enum class DeviceType(
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val color: Color
) {
    LIGHTING(Icons.Default.Lightbulb, Color(0xFFFFEB3B)),
    CLIMATE(Icons.Default.Thermostat, Color(0xFF2196F3)),
    ENTERTAINMENT(Icons.Default.Tv, Color(0xFF9C27B0)),
    APPLIANCE(Icons.Default.Kitchen, Color(0xFF4CAF50)),
    AUTOMATION(Icons.Default.Settings, Color(0xFF607D8B))
}

data class WeatherData(
    val location: String,
    val temperature: Int,
    val condition: WeatherCondition,
    val humidity: Int,
    val windSpeed: Int,
    val uvIndex: Int,
    val visibility: Int
)

data class DailyForecast(
    val day: String,
    val condition: WeatherCondition,
    val highTemp: Int,
    val lowTemp: Int
)

data class FitnessData(
    val dailySteps: Int,
    val caloriesBurned: Int,
    val activeMinutes: Int,
    val heartRate: Int,
    val weeklyGoalProgress: Float
)

data class WorkoutSession(
    val name: String,
    val type: WorkoutType,
    val duration: Int,
    val calories: Int
)

data class NutritionData(
    val caloriesConsumed: Int,
    val caloriesGoal: Int,
    val protein: Int,
    val carbs: Int,
    val fat: Int,
    val fiber: Int,
    val sugar: Int
)

data class FoodItem(
    val name: String,
    val calories: Int,
    val nutrition: String
)

data class TravelTrip(
    val destination: String,
    val dates: String,
    val status: TripStatus,
    val imageColor: Color
)

data class ItineraryItem(
    val title: String,
    val time: String,
    val type: ItineraryType
)

data class SmartDevice(
    val name: String,
    val type: DeviceType,
    val isOn: Boolean,
    val status: String
)

data class EnergyData(
    val currentUsage: Float,
    val dailyUsage: Float,
    val monthlyBudget: Float,
    val monthlyUsed: Float
)

data class AutomationScene(
    val name: String,
    val description: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)

// Helper Composables
@Composable
fun WeatherDetailItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = Color.White,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = Color.White.copy(alpha = 0.8f)
        )
    }
}

@Composable
fun HourlyForecastItem(
    time: String,
    temperature: Int,
    condition: WeatherCondition
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(60.dp)
    ) {
        Text(
            text = time,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(8.dp))
        Icon(
            imageVector = condition.icon,
            contentDescription = condition.displayName,
            modifier = Modifier.size(24.dp),
            tint = condition.backgroundColor
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "${temperature}°",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun DailyForecastItem(forecast: DailyForecast) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = forecast.day,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f)
        )
        
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = forecast.condition.icon,
                contentDescription = forecast.condition.displayName,
                modifier = Modifier.size(20.dp),
                tint = forecast.condition.backgroundColor
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = forecast.condition.displayName,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        
        Text(
            text = "${forecast.highTemp}°/${forecast.lowTemp}°",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.weight(1f),
            textAlign = androidx.compose.ui.text.style.TextAlign.End
        )
    }
}

@Composable
fun FitnessOverview(data: FitnessData) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            // Daily stats
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                FitnessStatCard(
                    title = "Steps",
                    value = data.dailySteps.toString(),
                    icon = Icons.Default.DirectionsWalk,
                    color = Color(0xFF4CAF50),
                    modifier = Modifier.weight(1f)
                )
                
                FitnessStatCard(
                    title = "Calories",
                    value = data.caloriesBurned.toString(),
                    icon = Icons.Default.LocalFireDepartment,
                    color = Color(0xFFFF5722),
                    modifier = Modifier.weight(1f)
                )
            }
        }
        
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                FitnessStatCard(
                    title = "Active Min",
                    value = data.activeMinutes.toString(),
                    icon = Icons.Default.Timer,
                    color = Color(0xFF2196F3),
                    modifier = Modifier.weight(1f)
                )
                
                FitnessStatCard(
                    title = "Heart Rate",
                    value = "${data.heartRate} bpm",
                    icon = Icons.Default.Favorite,
                    color = Color(0xFFE91E63),
                    modifier = Modifier.weight(1f)
                )
            }
        }
        
        item {
            // Weekly progress
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Weekly Goal Progress",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    LinearProgressIndicator(
                        progress = data.weeklyGoalProgress,
                        modifier = Modifier.fillMaxWidth(),
                        color = Color(0xFF4CAF50)
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text(
                        text = "${(data.weeklyGoalProgress * 100).toInt()}% of weekly goal completed",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
fun WorkoutsList(
    workouts: List<WorkoutSession>,
    selectedWorkout: WorkoutType,
    onWorkoutSelected: (WorkoutType) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(WorkoutType.values()) { type ->
                    FilterChip(
                        onClick = { onWorkoutSelected(type) },
                        label = { Text(type.displayName) },
                        selected = selectedWorkout == type,
                        leadingIcon = {
                            Icon(
                                imageVector = type.icon,
                                contentDescription = type.displayName,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    )
                }
            }
        }
        
        items(workouts.filter { it.type == selectedWorkout }) { workout ->
            WorkoutCard(workout)
        }
    }
}

@Composable
fun ProgressTracking(data: FitnessData) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Progress Chart",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Simulated progress chart
                    Canvas(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                    ) {
                        val points = listOf(0.2f, 0.4f, 0.3f, 0.6f, 0.8f, 0.7f, 0.9f)
                        val stepX = size.width / (points.size - 1)
                        
                        for (i in 0 until points.size - 1) {
                            drawLine(
                                color = Color(0xFF4CAF50),
                                start = Offset(
                                    x = i * stepX,
                                    y = size.height * (1 - points[i])
                                ),
                                end = Offset(
                                    x = (i + 1) * stepX,
                                    y = size.height * (1 - points[i + 1])
                                ),
                                strokeWidth = 4.dp.toPx()
                            )
                        }
                        
                        // Draw points
                        points.forEachIndexed { index, point ->
                            drawCircle(
                                color = Color(0xFF4CAF50),
                                radius = 6.dp.toPx(),
                                center = Offset(
                                    x = index * stepX,
                                    y = size.height * (1 - point)
                                )
                            )
                        }
                    }
                }
            }
        }
        
        item {
            // Achievement badges
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Recent Achievements",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    val achievements = listOf(
                        "🏃 10K Steps Streak - 7 days",
                        "🔥 Calorie Goal - 5 days",
                        "💪 Workout Warrior - 3 workouts",
                        "❤️ Heart Health - Resting HR improved"
                    )
                    
                    achievements.forEach { achievement ->
                        Text(
                            text = achievement,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(vertical = 2.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FitnessChallenges() {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = "Active Challenges",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
        }
        
        val challenges = listOf(
            "30-Day Step Challenge" to 0.6f,
            "Weekly Workout Goal" to 0.8f,
            "Hydration Challenge" to 0.4f,
            "Sleep Quality Challenge" to 0.7f
        )
        
        items(challenges) { (challenge, progress) ->
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = challenge,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    LinearProgressIndicator(
                        progress = progress,
                        modifier = Modifier.fillMaxWidth()
                    )
                    
                    Spacer(modifier = Modifier.height(4.dp))
                    
                    Text(
                        text = "${(progress * 100).toInt()}% completed",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
fun FitnessStatCard(
    title: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = color.copy(alpha = 0.1f)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = color,
                modifier = Modifier.size(32.dp)
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge,
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
fun WorkoutCard(workout: WorkoutSession) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = CircleShape,
                color = workout.type.color.copy(alpha = 0.2f),
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    imageVector = workout.type.icon,
                    contentDescription = workout.type.displayName,
                    tint = workout.type.color,
                    modifier = Modifier
                        .size(24.dp)
                        .padding(12.dp)
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = workout.name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold
                )
                
                Text(
                    text = "${workout.duration} min • ${workout.calories} cal",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            IconButton(
                onClick = { }
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Start Workout"
                )
            }
        }
    }
}

@Composable
fun NutritionOverviewCard(data: NutritionData) {
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
                text = "Daily Nutrition",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Calorie progress
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Calories",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                
                Text(
                    text = "${data.caloriesConsumed} / ${data.caloriesGoal}",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            LinearProgressIndicator(
                progress = data.caloriesConsumed.toFloat() / data.caloriesGoal,
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Macronutrients
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                MacronutrientItem(
                    label = "Protein",
                    value = "${data.protein}g",
                    color = Color(0xFF4CAF50)
                )
                
                MacronutrientItem(
                    label = "Carbs",
                    value = "${data.carbs}g",
                    color = Color(0xFF2196F3)
                )
                
                MacronutrientItem(
                    label = "Fat",
                    value = "${data.fat}g",
                    color = Color(0xFFFF9800)
                )
            }
        }
    }
}

@Composable
fun MacronutrientItem(
    label: String,
    value: String,
    color: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = color
        )
        
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}

@Composable
fun FoodItemCard(food: FoodItem) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = food.name,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold
            )
            
            Text(
                text = food.nutrition,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        
        Text(
            text = "${food.calories} cal",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun TripCard(
    trip: TravelTrip,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(280.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) {
                MaterialTheme.colorScheme.primaryContainer
            } else {
                MaterialTheme.colorScheme.surface
            }
        ),
        border = if (isSelected) {
            BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
        } else null
    ) {
        Column {
            // Trip image placeholder
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(trip.imageColor),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Place,
                    contentDescription = trip.destination,
                    tint = Color.White,
                    modifier = Modifier.size(48.dp)
                )
            }
            
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = trip.destination,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = trip.dates,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = trip.status.color.copy(alpha = 0.2f)
                ) {
                    Text(
                        text = trip.status.displayName,
                        style = MaterialTheme.typography.labelSmall,
                        color = trip.status.color,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun TravelActionButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(
            onClick = onClick,
            modifier = Modifier
                .size(48.dp)
                .background(
                    MaterialTheme.colorScheme.primaryContainer,
                    CircleShape
                )
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
        
        Spacer(modifier = Modifier.height(4.dp))
        
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun ItineraryItemCard(item: ItineraryItem) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            shape = CircleShape,
            color = item.type.color.copy(alpha = 0.2f),
            modifier = Modifier.size(40.dp)
        ) {
            Icon(
                imageVector = item.type.icon,
                contentDescription = item.title,
                tint = item.type.color,
                modifier = Modifier
                    .size(20.dp)
                    .padding(10.dp)
            )
        }
        
        Spacer(modifier = Modifier.width(12.dp))
        
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = item.title,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold
            )
            
            Text(
                text = item.time,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        
        IconButton(
            onClick = { }
        ) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "More options"
            )
        }
    }
}

@Composable
fun SmartDeviceCard(device: SmartDevice) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            Surface(
                shape = CircleShape,
                color = device.type.color.copy(alpha = 0.2f),
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = device.type.icon,
                    contentDescription = device.name,
                    tint = device.type.color,
                    modifier = Modifier
                        .size(20.dp)
                        .padding(10.dp)
                )
            }
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Column {
                Text(
                    text = device.name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold
                )
                
                Text(
                    text = device.status,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        
        Switch(
            checked = device.isOn,
            onCheckedChange = { }
        )
    }
}

@Composable
fun EnergyMetric(
    label: String,
    value: String,
    color: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = color
        )
        
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = color
        )
    }
}

@Composable
fun AutomationSceneCard(scene: AutomationScene) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = CircleShape,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    imageVector = scene.icon,
                    contentDescription = scene.name,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .size(24.dp)
                        .padding(12.dp)
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = scene.name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold
                )
                
                Text(
                    text = scene.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            IconButton(
                onClick = { }
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Activate Scene"
                )
            }
        }
    }
}