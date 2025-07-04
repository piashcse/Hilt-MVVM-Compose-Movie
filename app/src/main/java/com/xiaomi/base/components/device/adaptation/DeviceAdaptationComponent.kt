package com.xiaomi.base.components.device.adaptation

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.math.*
import com.xiaomi.base.ui.theme.Orange

/**
 * Hardware Feature Type
 */
enum class HardwareFeatureType {
    ACCELEROMETER,
    GYROSCOPE,
    MAGNETOMETER,
    PROXIMITY,
    LIGHT,
    PRESSURE,
    TEMPERATURE,
    HUMIDITY,
    CAMERA,
    MICROPHONE,
    SPEAKER,
    VIBRATOR,
    GPS,
    NFC,
    BLUETOOTH,
    WIFI,
    CELLULAR,
    FINGERPRINT,
    FACE_UNLOCK,
    IRIS_SCANNER,
    HEART_RATE,
    STEP_COUNTER,
    AMBIENT_LIGHT,
    ROTATION_VECTOR,
    LINEAR_ACCELERATION,
    GRAVITY
}

/**
 * Device Capability Level
 */
enum class DeviceCapabilityLevel {
    NOT_AVAILABLE,
    BASIC,
    STANDARD,
    ADVANCED,
    PREMIUM
}

/**
 * Sensor Data Type
 */
enum class SensorDataType {
    ACCELERATION,
    ROTATION,
    MAGNETIC_FIELD,
    ORIENTATION,
    PROXIMITY_DISTANCE,
    LIGHT_INTENSITY,
    PRESSURE_VALUE,
    TEMPERATURE_VALUE,
    HUMIDITY_VALUE,
    HEART_RATE_BPM,
    STEP_COUNT
}

/**
 * Adaptation Strategy
 */
enum class AdaptationStrategy {
    FEATURE_DETECTION,
    GRACEFUL_DEGRADATION,
    PROGRESSIVE_ENHANCEMENT,
    FALLBACK_IMPLEMENTATION,
    ALTERNATIVE_INPUT,
    SIMULATION_MODE
}

/**
 * Hardware Feature Info
 */
data class HardwareFeatureInfo(
    val type: HardwareFeatureType,
    val name: String,
    val isAvailable: Boolean,
    val capabilityLevel: DeviceCapabilityLevel,
    val vendor: String = "",
    val version: String = "",
    val maxRange: Float = 0f,
    val resolution: Float = 0f,
    val power: Float = 0f,
    val minDelay: Int = 0,
    val maxDelay: Int = 0
)

/**
 * Sensor Reading
 */
data class SensorReading(
    val type: SensorDataType,
    val values: FloatArray,
    val accuracy: Int,
    val timestamp: Long,
    val formattedValue: String = ""
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        
        other as SensorReading
        
        if (type != other.type) return false
        if (!values.contentEquals(other.values)) return false
        if (accuracy != other.accuracy) return false
        if (timestamp != other.timestamp) return false
        
        return true
    }
    
    override fun hashCode(): Int {
        var result = type.hashCode()
        result = 31 * result + values.contentHashCode()
        result = 31 * result + accuracy
        result = 31 * result + timestamp.hashCode()
        return result
    }
}

/**
 * Device Adaptation Configuration
 */
data class DeviceAdaptationConfig(
    val enableHardwareDetection: Boolean = true,
    val enableSensorMonitoring: Boolean = true,
    val enableHapticFeedback: Boolean = true,
    val enableAdaptiveUI: Boolean = true,
    val fallbackStrategy: AdaptationStrategy = AdaptationStrategy.GRACEFUL_DEGRADATION,
    val sensorUpdateInterval: Long = 100L, // milliseconds
    val enableSimulationMode: Boolean = false
)

/**
 * Device Adaptation Component
 * Comprehensive device adaptation and hardware integration
 * 
 * @param config Device adaptation configuration
 * @param onHardwareDetected Callback when hardware is detected
 * @param onSensorReading Callback for sensor readings
 */
@Composable
fun DeviceAdaptationComponent(
    config: DeviceAdaptationConfig = DeviceAdaptationConfig(),
    onHardwareDetected: (List<HardwareFeatureInfo>) -> Unit = {},
    onSensorReading: (SensorReading) -> Unit = {}
) {
    val context = LocalContext.current
    var selectedTab by remember { mutableStateOf(0) }
    
    val hardwareFeatures = remember {
        if (config.enableHardwareDetection) {
            detectHardwareFeatures(context)
        } else {
            emptyList()
        }
    }
    
    LaunchedEffect(hardwareFeatures) {
        onHardwareDetected(hardwareFeatures)
    }
    
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TabRow(
            selectedTabIndex = selectedTab
        ) {
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                text = { Text("Hardware") }
            )
            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                text = { Text("Sensors") }
            )
            Tab(
                selected = selectedTab == 2,
                onClick = { selectedTab = 2 },
                text = { Text("Adaptation") }
            )
            Tab(
                selected = selectedTab == 3,
                onClick = { selectedTab = 3 },
                text = { Text("Testing") }
            )
        }
        
        when (selectedTab) {
            0 -> HardwareDetectionComponent(
                hardwareFeatures = hardwareFeatures,
                config = config
            )
            1 -> SensorMonitoringComponent(
                hardwareFeatures = hardwareFeatures,
                config = config,
                onSensorReading = onSensorReading
            )
            2 -> AdaptationStrategiesComponent(
                hardwareFeatures = hardwareFeatures,
                config = config
            )
            3 -> HardwareTestingComponent(
                hardwareFeatures = hardwareFeatures,
                config = config
            )
        }
    }
}

/**
 * Hardware Detection Component
 * Detects and displays available hardware features
 * 
 * @param hardwareFeatures List of detected hardware features
 * @param config Device adaptation configuration
 */
@Composable
fun HardwareDetectionComponent(
    hardwareFeatures: List<HardwareFeatureInfo>,
    config: DeviceAdaptationConfig
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        item {
            Text(
                text = "Hardware Features",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }
        
        item {
            HardwareSummaryCard(hardwareFeatures = hardwareFeatures)
        }
        
        items(hardwareFeatures.groupBy { it.type.name.take(1) }.toList()) { (category, features) ->
            HardwareCategoryCard(
                category = category,
                features = features
            )
        }
        
        if (config.enableSimulationMode) {
            item {
                SimulationModeCard()
            }
        }
    }
}

/**
 * Sensor Monitoring Component
 * Real-time sensor data monitoring
 * 
 * @param hardwareFeatures List of available hardware features
 * @param config Device adaptation configuration
 * @param onSensorReading Callback for sensor readings
 */
@Composable
fun SensorMonitoringComponent(
    hardwareFeatures: List<HardwareFeatureInfo>,
    config: DeviceAdaptationConfig,
    onSensorReading: (SensorReading) -> Unit
) {
    val context = LocalContext.current
    var isMonitoring by remember { mutableStateOf(false) }
    var sensorReadings by remember { mutableStateOf<Map<SensorDataType, SensorReading>>(emptyMap()) }
    
    val sensorManager = remember {
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }
    
    LaunchedEffect(isMonitoring) {
        if (isMonitoring && config.enableSensorMonitoring) {
            // Start sensor monitoring
            val listener = object : SensorEventListener {
                override fun onSensorChanged(event: SensorEvent?) {
                    event?.let { sensorEvent ->
                        val reading = convertSensorEvent(sensorEvent)
                        sensorReadings = sensorReadings + (reading.type to reading)
                        onSensorReading(reading)
                    }
                }
                
                override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
            }
            
            // Register sensors
            val sensors = getSensorsToMonitor(sensorManager, hardwareFeatures)
            sensors.forEach { sensor ->
                sensorManager.registerListener(
                    listener,
                    sensor,
                    SensorManager.SENSOR_DELAY_UI
                )
            }
        }
    }
    
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Sensor Monitoring",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                
                Switch(
                    checked = isMonitoring,
                    onCheckedChange = { isMonitoring = it }
                )
            }
        }
        
        if (isMonitoring) {
            items(sensorReadings.values.toList()) { reading ->
                SensorReadingCard(reading = reading)
            }
        } else {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Enable monitoring to see sensor data",
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

/**
 * Adaptation Strategies Component
 * Shows adaptation strategies for different hardware scenarios
 * 
 * @param hardwareFeatures List of available hardware features
 * @param config Device adaptation configuration
 */
@Composable
fun AdaptationStrategiesComponent(
    hardwareFeatures: List<HardwareFeatureInfo>,
    config: DeviceAdaptationConfig
) {
    val adaptationStrategies = remember(hardwareFeatures) {
        generateAdaptationStrategies(hardwareFeatures)
    }
    
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        item {
            Text(
                text = "Adaptation Strategies",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }
        
        item {
            AdaptationOverviewCard(
                strategies = adaptationStrategies,
                config = config
            )
        }
        
        items(adaptationStrategies) { strategy ->
            AdaptationStrategyCard(strategy = strategy)
        }
    }
}

/**
 * Hardware Testing Component
 * Interactive testing of hardware features
 * 
 * @param hardwareFeatures List of available hardware features
 * @param config Device adaptation configuration
 */
@Composable
fun HardwareTestingComponent(
    hardwareFeatures: List<HardwareFeatureInfo>,
    config: DeviceAdaptationConfig
) {
    val context = LocalContext.current
    var testResults by remember { mutableStateOf<Map<HardwareFeatureType, Boolean>>(emptyMap()) }
    
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        item {
            Text(
                text = "Hardware Testing",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }
        
        item {
            TestResultsSummaryCard(testResults = testResults)
        }
        
        items(hardwareFeatures.filter { it.isAvailable }) { feature ->
            HardwareTestCard(
                feature = feature,
                testResult = testResults[feature.type],
                onTest = { success ->
                    testResults = testResults + (feature.type to success)
                }
            )
        }
        
        item {
            HapticFeedbackTestCard()
        }
    }
}

/**
 * Helper Components
 */

@Composable
private fun HardwareSummaryCard(hardwareFeatures: List<HardwareFeatureInfo>) {
    val availableCount = hardwareFeatures.count { it.isAvailable }
    val totalCount = hardwareFeatures.size
    val availabilityPercentage = if (totalCount > 0) (availableCount * 100) / totalCount else 0
    
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Hardware Summary",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Medium
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                SummaryItem(
                    label = "Available",
                    value = availableCount.toString(),
                    color = Color.Green
                )
                
                SummaryItem(
                    label = "Total",
                    value = totalCount.toString(),
                    color = MaterialTheme.colorScheme.primary
                )
                
                SummaryItem(
                    label = "Coverage",
                    value = "$availabilityPercentage%",
                    color = if (availabilityPercentage >= 70) Color.Green else Orange
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            LinearProgressIndicator(
                progress = availabilityPercentage / 100f,
                modifier = Modifier.fillMaxWidth(),
                color = if (availabilityPercentage >= 70) Color.Green else Orange
            )
        }
    }
}

@Composable
private fun SummaryItem(
    label: String,
    value: String,
    color: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = color
        )
        
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun HardwareCategoryCard(
    category: String,
    features: List<HardwareFeatureInfo>
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Category: $category",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Medium
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            features.forEach { feature ->
                HardwareFeatureRow(feature = feature)
                
                if (feature != features.last()) {
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
private fun HardwareFeatureRow(feature: HardwareFeatureInfo) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = feature.name,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
            
            Text(
                text = feature.capabilityLevel.name,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        
        Icon(
            imageVector = if (feature.isAvailable) Icons.Default.CheckCircle else Icons.Default.Cancel,
            contentDescription = if (feature.isAvailable) "Available" else "Not Available",
            tint = if (feature.isAvailable) Color.Green else Color.Red,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
private fun SensorReadingCard(reading: SensorReading) {
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
                    text = reading.type.name.replace("_", " "),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium
                )
                
                Badge {
                    Text(
                        text = "Accuracy: ${reading.accuracy}",
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            if (reading.formattedValue.isNotEmpty()) {
                Text(
                    text = reading.formattedValue,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.primary
                )
            } else {
                Text(
                    text = "Values: ${reading.values.joinToString(", ") { "%.2f".format(it) }}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Text(
                text = "Updated: ${formatTimestamp(reading.timestamp)}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun HardwareTestCard(
    feature: HardwareFeatureInfo,
    testResult: Boolean?,
    onTest: (Boolean) -> Unit
) {
    val context = LocalContext.current
    
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
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = feature.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Medium
                    )
                    
                    Text(
                        text = feature.type.name,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                testResult?.let { result ->
                    Icon(
                        imageVector = if (result) Icons.Default.CheckCircle else Icons.Default.Error,
                        contentDescription = if (result) "Test Passed" else "Test Failed",
                        tint = if (result) Color.Green else Color.Red,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Button(
                onClick = {
                    val success = testHardwareFeature(context, feature)
                    onTest(success)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Test ${feature.type.name}")
            }
        }
    }
}

@Composable
private fun HapticFeedbackTestCard() {
    val context = LocalContext.current
    
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Haptic Feedback Test",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Medium
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { triggerHapticFeedback(context, "light") },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Light")
                }
                
                Button(
                    onClick = { triggerHapticFeedback(context, "medium") },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Medium")
                }
                
                Button(
                    onClick = { triggerHapticFeedback(context, "heavy") },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Heavy")
                }
            }
        }
    }
}

@Composable
private fun SimulationModeCard() {
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
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Science,
                    contentDescription = "Simulation Mode",
                    tint = MaterialTheme.colorScheme.onSecondaryContainer
                )
                
                Spacer(modifier = Modifier.width(8.dp))
                
                Text(
                    text = "Simulation Mode Active",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Hardware features are being simulated for testing purposes. Real device capabilities may differ.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}

@Composable
private fun AdaptationOverviewCard(
    strategies: List<AdaptationStrategyInfo>,
    config: DeviceAdaptationConfig
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Adaptation Overview",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Medium
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Text(
                text = "Primary Strategy: ${config.fallbackStrategy.name}",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.primary
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "${strategies.size} adaptation strategies identified",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun AdaptationStrategyCard(strategy: AdaptationStrategyInfo) {
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
                    text = strategy.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.weight(1f)
                )
                
                Badge(
                    containerColor = when (strategy.priority) {
                        "HIGH" -> Color.Red
                        "MEDIUM" -> Orange
                        "LOW" -> Color.Green
                        else -> MaterialTheme.colorScheme.primary
                    }
                ) {
                    Text(
                        text = strategy.priority,
                        color = Color.White,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = strategy.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Implementation: ${strategy.implementation}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
private fun TestResultsSummaryCard(testResults: Map<HardwareFeatureType, Boolean>) {
    val passedTests = testResults.values.count { it }
    val totalTests = testResults.size
    
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Test Results Summary",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Medium
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            if (totalTests > 0) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    SummaryItem(
                        label = "Passed",
                        value = passedTests.toString(),
                        color = Color.Green
                    )
                    
                    SummaryItem(
                        label = "Failed",
                        value = (totalTests - passedTests).toString(),
                        color = Color.Red
                    )
                    
                    SummaryItem(
                        label = "Success Rate",
                        value = "${(passedTests * 100) / totalTests}%",
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            } else {
                Text(
                    text = "No tests run yet",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

/**
 * Data Classes and Helper Functions
 */

data class AdaptationStrategyInfo(
    val title: String,
    val description: String,
    val priority: String,
    val implementation: String,
    val affectedFeatures: List<HardwareFeatureType>
)

private fun detectHardwareFeatures(context: Context): List<HardwareFeatureInfo> {
    val features = mutableListOf<HardwareFeatureInfo>()
    val packageManager = context.packageManager
    val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    
    // Detect sensors
    val sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL)
    
    sensorList.forEach { sensor ->
        val featureType = mapSensorToFeatureType(sensor.type)
        if (featureType != null) {
            features.add(
                HardwareFeatureInfo(
                    type = featureType,
                    name = sensor.name,
                    isAvailable = true,
                    capabilityLevel = determineCapabilityLevel(sensor),
                    vendor = sensor.vendor,
                    version = sensor.version.toString(),
                    maxRange = sensor.maximumRange,
                    resolution = sensor.resolution,
                    power = sensor.power,
                    minDelay = sensor.minDelay,
                    maxDelay = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) sensor.maxDelay else 0
                )
            )
        }
    }
    
    // Detect other hardware features
    val hardwareFeatures = mapOf(
        HardwareFeatureType.CAMERA to PackageManager.FEATURE_CAMERA,
        HardwareFeatureType.NFC to PackageManager.FEATURE_NFC,
        HardwareFeatureType.BLUETOOTH to PackageManager.FEATURE_BLUETOOTH,
        HardwareFeatureType.WIFI to PackageManager.FEATURE_WIFI,
        HardwareFeatureType.GPS to PackageManager.FEATURE_LOCATION_GPS,
        HardwareFeatureType.FINGERPRINT to PackageManager.FEATURE_FINGERPRINT
    )
    
    hardwareFeatures.forEach { (featureType, featureName) ->
        val isAvailable = packageManager.hasSystemFeature(featureName)
        if (features.none { it.type == featureType }) {
            features.add(
                HardwareFeatureInfo(
                    type = featureType,
                    name = featureType.name.replace("_", " ").lowercase().replaceFirstChar { it.uppercase() },
                    isAvailable = isAvailable,
                    capabilityLevel = if (isAvailable) DeviceCapabilityLevel.STANDARD else DeviceCapabilityLevel.NOT_AVAILABLE
                )
            )
        }
    }
    
    return features.sortedBy { it.type.name }
}

private fun mapSensorToFeatureType(sensorType: Int): HardwareFeatureType? {
    return when (sensorType) {
        Sensor.TYPE_ACCELEROMETER -> HardwareFeatureType.ACCELEROMETER
        Sensor.TYPE_GYROSCOPE -> HardwareFeatureType.GYROSCOPE
        Sensor.TYPE_MAGNETIC_FIELD -> HardwareFeatureType.MAGNETOMETER
        Sensor.TYPE_PROXIMITY -> HardwareFeatureType.PROXIMITY
        Sensor.TYPE_LIGHT -> HardwareFeatureType.LIGHT
        Sensor.TYPE_PRESSURE -> HardwareFeatureType.PRESSURE
        Sensor.TYPE_AMBIENT_TEMPERATURE -> HardwareFeatureType.TEMPERATURE
        Sensor.TYPE_RELATIVE_HUMIDITY -> HardwareFeatureType.HUMIDITY
        Sensor.TYPE_HEART_RATE -> HardwareFeatureType.HEART_RATE
        Sensor.TYPE_STEP_COUNTER -> HardwareFeatureType.STEP_COUNTER
        Sensor.TYPE_ROTATION_VECTOR -> HardwareFeatureType.ROTATION_VECTOR
        Sensor.TYPE_LINEAR_ACCELERATION -> HardwareFeatureType.LINEAR_ACCELERATION
        Sensor.TYPE_GRAVITY -> HardwareFeatureType.GRAVITY
        else -> null
    }
}

private fun determineCapabilityLevel(sensor: Sensor): DeviceCapabilityLevel {
    return when {
        sensor.power < 0.1f && sensor.resolution < 0.01f -> DeviceCapabilityLevel.PREMIUM
        sensor.power < 0.5f && sensor.resolution < 0.1f -> DeviceCapabilityLevel.ADVANCED
        sensor.power < 1.0f -> DeviceCapabilityLevel.STANDARD
        else -> DeviceCapabilityLevel.BASIC
    }
}

private fun getSensorsToMonitor(
    sensorManager: SensorManager,
    hardwareFeatures: List<HardwareFeatureInfo>
): List<Sensor> {
    val sensors = mutableListOf<Sensor>()
    
    hardwareFeatures.filter { it.isAvailable }.forEach { feature ->
        val sensorType = when (feature.type) {
            HardwareFeatureType.ACCELEROMETER -> Sensor.TYPE_ACCELEROMETER
            HardwareFeatureType.GYROSCOPE -> Sensor.TYPE_GYROSCOPE
            HardwareFeatureType.MAGNETOMETER -> Sensor.TYPE_MAGNETIC_FIELD
            HardwareFeatureType.PROXIMITY -> Sensor.TYPE_PROXIMITY
            HardwareFeatureType.LIGHT -> Sensor.TYPE_LIGHT
            HardwareFeatureType.PRESSURE -> Sensor.TYPE_PRESSURE
            else -> null
        }
        
        sensorType?.let { type ->
            sensorManager.getDefaultSensor(type)?.let { sensor ->
                sensors.add(sensor)
            }
        }
    }
    
    return sensors
}

private fun convertSensorEvent(event: SensorEvent): SensorReading {
    val dataType = when (event.sensor.type) {
        Sensor.TYPE_ACCELEROMETER -> SensorDataType.ACCELERATION
        Sensor.TYPE_GYROSCOPE -> SensorDataType.ROTATION
        Sensor.TYPE_MAGNETIC_FIELD -> SensorDataType.MAGNETIC_FIELD
        Sensor.TYPE_PROXIMITY -> SensorDataType.PROXIMITY_DISTANCE
        Sensor.TYPE_LIGHT -> SensorDataType.LIGHT_INTENSITY
        Sensor.TYPE_PRESSURE -> SensorDataType.PRESSURE_VALUE
        Sensor.TYPE_AMBIENT_TEMPERATURE -> SensorDataType.TEMPERATURE_VALUE
        Sensor.TYPE_RELATIVE_HUMIDITY -> SensorDataType.HUMIDITY_VALUE
        Sensor.TYPE_HEART_RATE -> SensorDataType.HEART_RATE_BPM
        Sensor.TYPE_STEP_COUNTER -> SensorDataType.STEP_COUNT
        else -> SensorDataType.ACCELERATION
    }
    
    val formattedValue = when (dataType) {
        SensorDataType.ACCELERATION -> "${sqrt(event.values[0].pow(2) + event.values[1].pow(2) + event.values[2].pow(2)).roundToInt()} m/s²"
        SensorDataType.PROXIMITY_DISTANCE -> "${event.values[0]} cm"
        SensorDataType.LIGHT_INTENSITY -> "${event.values[0].roundToInt()} lux"
        SensorDataType.PRESSURE_VALUE -> "${event.values[0].roundToInt()} hPa"
        SensorDataType.TEMPERATURE_VALUE -> "${event.values[0].roundToInt()}°C"
        SensorDataType.HUMIDITY_VALUE -> "${event.values[0].roundToInt()}%"
        SensorDataType.HEART_RATE_BPM -> "${event.values[0].roundToInt()} BPM"
        SensorDataType.STEP_COUNT -> "${event.values[0].roundToInt()} steps"
        else -> ""
    }
    
    return SensorReading(
        type = dataType,
        values = event.values.clone(),
        accuracy = event.accuracy,
        timestamp = event.timestamp,
        formattedValue = formattedValue
    )
}

private fun generateAdaptationStrategies(hardwareFeatures: List<HardwareFeatureInfo>): List<AdaptationStrategyInfo> {
    val strategies = mutableListOf<AdaptationStrategyInfo>()
    
    val unavailableFeatures = hardwareFeatures.filter { !it.isAvailable }
    
    if (unavailableFeatures.any { it.type == HardwareFeatureType.GPS }) {
        strategies.add(
            AdaptationStrategyInfo(
                title = "GPS Fallback Strategy",
                description = "Implement network-based location as fallback for missing GPS",
                priority = "HIGH",
                implementation = "Use network location provider and manual location input",
                affectedFeatures = listOf(HardwareFeatureType.GPS)
            )
        )
    }
    
    if (unavailableFeatures.any { it.type == HardwareFeatureType.CAMERA }) {
        strategies.add(
            AdaptationStrategyInfo(
                title = "Camera Alternative",
                description = "Provide file upload option when camera is not available",
                priority = "MEDIUM",
                implementation = "Show file picker instead of camera capture",
                affectedFeatures = listOf(HardwareFeatureType.CAMERA)
            )
        )
    }
    
    if (unavailableFeatures.any { it.type == HardwareFeatureType.FINGERPRINT }) {
        strategies.add(
            AdaptationStrategyInfo(
                title = "Biometric Fallback",
                description = "Use PIN/password authentication when biometrics unavailable",
                priority = "HIGH",
                implementation = "Implement secure PIN-based authentication",
                affectedFeatures = listOf(HardwareFeatureType.FINGERPRINT, HardwareFeatureType.FACE_UNLOCK)
            )
        )
    }
    
    return strategies
}

private fun testHardwareFeature(context: Context, feature: HardwareFeatureInfo): Boolean {
    return when (feature.type) {
        HardwareFeatureType.VIBRATOR -> {
            try {
                triggerHapticFeedback(context, "light")
                true
            } catch (e: Exception) {
                false
            }
        }
        
        HardwareFeatureType.CAMERA -> {
            context.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)
        }
        
        HardwareFeatureType.NFC -> {
            context.packageManager.hasSystemFeature(PackageManager.FEATURE_NFC)
        }
        
        else -> feature.isAvailable
    }
}

private fun triggerHapticFeedback(context: Context, intensity: String) {
    try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            val vibrator = vibratorManager.defaultVibrator
            
            val effect = when (intensity) {
                "light" -> VibrationEffect.createPredefined(VibrationEffect.EFFECT_TICK)
                "medium" -> VibrationEffect.createPredefined(VibrationEffect.EFFECT_CLICK)
                "heavy" -> VibrationEffect.createPredefined(VibrationEffect.EFFECT_HEAVY_CLICK)
                else -> VibrationEffect.createPredefined(VibrationEffect.EFFECT_CLICK)
            }
            
            vibrator.vibrate(effect)
        } else {
            @Suppress("DEPRECATION")
            val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val duration = when (intensity) {
                    "light" -> 50L
                    "medium" -> 100L
                    "heavy" -> 200L
                    else -> 100L
                }
                
                vibrator.vibrate(VibrationEffect.createOneShot(duration, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                vibrator.vibrate(100)
            }
        }
    } catch (e: Exception) {
        // Handle vibration not available
    }
}

private fun formatTimestamp(timestamp: Long): String {
    val currentTime = System.currentTimeMillis()
    val diff = (currentTime - timestamp / 1000000) // Convert nanoseconds to milliseconds
    
    return when {
        diff < 1000 -> "Just now"
        diff < 60000 -> "${diff / 1000}s ago"
        diff < 3600000 -> "${diff / 60000}m ago"
        else -> "${diff / 3600000}h ago"
    }
}