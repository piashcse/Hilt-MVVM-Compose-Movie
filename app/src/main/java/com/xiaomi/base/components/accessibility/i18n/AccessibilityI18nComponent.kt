package com.xiaomi.base.components.accessibility.i18n

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.*
import kotlinx.coroutines.delay
import kotlin.math.*
import kotlin.random.Random

// Helper colors
val Orange = Color(0xFFFF9800)

// Enums
enum class AccessibilityFeature {
    SCREEN_READER,
    HIGH_CONTRAST,
    LARGE_TEXT,
    VOICE_CONTROL,
    SWITCH_CONTROL,
    MAGNIFICATION,
    COLOR_CORRECTION,
    REDUCED_MOTION,
    AUDIO_DESCRIPTIONS,
    CLOSED_CAPTIONS
}

enum class AccessibilityLevel {
    NONE,
    BASIC,
    INTERMEDIATE,
    ADVANCED,
    ENHANCED,
    EXPERT,
    FULL
}

enum class TextDirection {
    LTR, // Left to Right
    RTL  // Right to Left
}

enum class LocaleCategory {
    LANGUAGE,
    REGION,
    CURRENCY,
    DATE_FORMAT,
    NUMBER_FORMAT,
    TIME_ZONE
}

enum class TranslationStatus {
    NOT_STARTED,
    IN_PROGRESS,
    COMPLETED,
    NEEDS_REVIEW,
    APPROVED
}

enum class AccessibilityTestType {
    SCREEN_READER_TEST,
    KEYBOARD_NAVIGATION,
    COLOR_CONTRAST,
    TOUCH_TARGET_SIZE,
    FOCUS_MANAGEMENT,
    SEMANTIC_MARKUP
}

enum class I18nTestType {
    TEXT_EXPANSION,
    RTL_LAYOUT,
    CURRENCY_FORMAT,
    DATE_FORMAT,
    NUMBER_FORMAT,
    PLURALIZATION
}

enum class TestStatus {
    PASSED,
    FAILED,
    PENDING,
    SKIPPED,
    WARNING
}

// Data Classes
data class AccessibilitySettings(
    val feature: AccessibilityFeature,
    val enabled: Boolean = false,
    val level: AccessibilityLevel = AccessibilityLevel.BASIC,
    val customSettings: Map<String, Any> = emptyMap(),
    val description: String = "",
    val shortcut: String? = null
)

data class LocaleInfo(
    val code: String, // e.g., "en-US", "zh-CN"
    val language: String, // e.g., "English", "中文"
    val region: String, // e.g., "United States", "China"
    val nativeName: String, // e.g., "English", "中文"
    val textDirection: TextDirection = TextDirection.LTR,
    val enabled: Boolean = false,
    val completeness: Double = 0.0, // 0.0 to 1.0
    val lastUpdated: Long = System.currentTimeMillis()
) {
    val displayName: String
        get() = "$language ($region)"
}

data class TranslationEntry(
    val key: String,
    val sourceText: String,
    val translatedText: String,
    val locale: String,
    val status: TranslationStatus = TranslationStatus.NOT_STARTED,
    val translator: String? = null,
    val reviewer: String? = null,
    val lastModified: Long = System.currentTimeMillis(),
    val context: String? = null,
    val notes: String? = null
)

data class AccessibilityTest(
    val id: String,
    val type: AccessibilityTestType,
    val name: String,
    val description: String,
    val status: TestStatus = TestStatus.PENDING,
    val score: Double, // 0.0 to 1.0
    val issues: List<String> = emptyList(),
    val recommendations: List<String> = emptyList(),
    val lastRun: Long = System.currentTimeMillis()
)

data class I18nTest(
    val id: String,
    val type: I18nTestType,
    val name: String,
    val description: String,
    val locale: String,
    val status: TestStatus = TestStatus.PENDING,
    val score: Double, // 0.0 to 1.0
    val issues: List<String> = emptyList(),
    val recommendations: List<String> = emptyList(),
    val lastRun: Long = System.currentTimeMillis()
)

data class AccessibilityAudit(
    val id: String,
    val name: String,
    val description: String,
    val overallScore: Double,
    val tests: List<AccessibilityTest>,
    val summary: String,
    val criticalIssues: Int,
    val warnings: Int,
    val passed: Int,
    val createdAt: Long = System.currentTimeMillis()
)

data class I18nReport(
    val id: String,
    val name: String,
    val description: String,
    val locales: List<String>,
    val totalStrings: Int,
    val translatedStrings: Int,
    val completeness: Double,
    val tests: List<I18nTest>,
    val issues: List<String>,
    val createdAt: Long = System.currentTimeMillis()
)

data class AccessibilityI18nConfig(
    val enableAccessibilityFeatures: Boolean = true,
    val enableI18nSupport: Boolean = true,
    val defaultLocale: String = "en-US",
    val supportedLocales: List<String> = listOf("en-US"),
    val enableRTLSupport: Boolean = true,
    val enableAccessibilityTesting: Boolean = true,
    val enableI18nTesting: Boolean = true,
    val autoDetectLocale: Boolean = true
)

// Helper Functions (Define BEFORE usage)
fun getAccessibilityColor(score: Double): Color {
    return when {
        score >= 0.8 -> Color.Green
        score >= 0.6 -> Orange
        else -> Color.Red
    }
}

fun getLocalizationColor(progress: Double): Color {
    return when {
        progress >= 0.8 -> Color.Green
        progress >= 0.6 -> Orange
        else -> Color.Red
    }
}

fun formatTimestamp(timestamp: Long): String {
    val formatter = SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault())
    return formatter.format(Date(timestamp))
}

fun getAccessibilityLevelColor(level: AccessibilityLevel): Color {
    return when (level) {
        AccessibilityLevel.NONE -> Color.Gray
        AccessibilityLevel.BASIC -> Color.Blue
        AccessibilityLevel.INTERMEDIATE -> Color.Green
        AccessibilityLevel.ADVANCED -> Orange
        AccessibilityLevel.ENHANCED -> Color.Magenta
        AccessibilityLevel.EXPERT -> Color.Red
        AccessibilityLevel.FULL -> Color.Cyan
    }
}

// Sample Data Generators
fun generateSampleAccessibilitySettings(): List<AccessibilitySettings> {
    return AccessibilityFeature.values().map { feature ->
        AccessibilitySettings(
            feature = feature,
            enabled = Random.nextBoolean(),
            level = AccessibilityLevel.values().random(),
            description = when (feature) {
                AccessibilityFeature.SCREEN_READER -> "Provides audio feedback for UI elements"
                AccessibilityFeature.HIGH_CONTRAST -> "Increases contrast for better visibility"
                AccessibilityFeature.LARGE_TEXT -> "Increases text size for better readability"
                AccessibilityFeature.VOICE_CONTROL -> "Enables voice commands for navigation"
                AccessibilityFeature.SWITCH_CONTROL -> "Allows navigation using external switches"
                AccessibilityFeature.MAGNIFICATION -> "Provides screen magnification capabilities"
                AccessibilityFeature.COLOR_CORRECTION -> "Adjusts colors for color blindness"
                AccessibilityFeature.REDUCED_MOTION -> "Reduces animations and motion effects"
                AccessibilityFeature.AUDIO_DESCRIPTIONS -> "Provides audio descriptions for media"
                AccessibilityFeature.CLOSED_CAPTIONS -> "Displays captions for audio content"
            },
            shortcut = when (feature) {
                AccessibilityFeature.SCREEN_READER -> "Ctrl+Alt+S"
                AccessibilityFeature.HIGH_CONTRAST -> "Ctrl+Alt+H"
                AccessibilityFeature.LARGE_TEXT -> "Ctrl+Plus"
                else -> null
            }
        )
    }
}

fun generateSampleLocales(): List<LocaleInfo> {
    return listOf(
        LocaleInfo(
            code = "en-US",
            language = "English",
            region = "United States",
            nativeName = "English",
            textDirection = TextDirection.LTR,
            enabled = true,
            completeness = 1.0
        ),
        LocaleInfo(
            code = "zh-CN",
            language = "Chinese",
            region = "China",
            nativeName = "中文",
            textDirection = TextDirection.LTR,
            enabled = true,
            completeness = 0.85
        ),
        LocaleInfo(
            code = "ar-SA",
            language = "Arabic",
            region = "Saudi Arabia",
            nativeName = "العربية",
            textDirection = TextDirection.RTL,
            enabled = false,
            completeness = 0.6
        ),
        LocaleInfo(
            code = "es-ES",
            language = "Spanish",
            region = "Spain",
            nativeName = "Español",
            textDirection = TextDirection.LTR,
            enabled = true,
            completeness = 0.9
        )
    )
}

fun generateSampleTranslations(): List<TranslationEntry> {
    return listOf(
        TranslationEntry(
            key = "welcome_message",
            sourceText = "Welcome to our app",
            translatedText = "欢迎使用我们的应用",
            locale = "zh-CN",
            status = TranslationStatus.COMPLETED,
            translator = "Li Wei",
            reviewer = "Wang Ming"
        ),
        TranslationEntry(
            key = "login_button",
            sourceText = "Login",
            translatedText = "登录",
            locale = "zh-CN",
            status = TranslationStatus.APPROVED,
            translator = "Li Wei"
        ),
        TranslationEntry(
            key = "welcome_message",
            sourceText = "Welcome to our app",
            translatedText = "Bienvenido a nuestra aplicación",
            locale = "es-ES",
            status = TranslationStatus.NEEDS_REVIEW,
            translator = "Maria Garcia"
        )
    )
}

fun generateSampleAccessibilityTests(): List<AccessibilityTest> {
    return listOf(
        AccessibilityTest(
            id = "test_001",
            type = AccessibilityTestType.SCREEN_READER_TEST,
            name = "Screen Reader Compatibility",
            description = "Tests compatibility with screen reader software",
            status = TestStatus.PASSED,
            score = 0.92,
            issues = emptyList(),
            recommendations = listOf("Add more descriptive labels for images")
        ),
        AccessibilityTest(
            id = "test_002",
            type = AccessibilityTestType.COLOR_CONTRAST,
            name = "Color Contrast Validation",
            description = "Validates color contrast ratios meet WCAG standards",
            status = TestStatus.FAILED,
            score = 0.65,
            issues = listOf("Low contrast in button text", "Warning colors too subtle"),
            recommendations = listOf("Increase contrast ratio", "Use darker text colors")
        ),
        AccessibilityTest(
            id = "test_003",
            type = AccessibilityTestType.KEYBOARD_NAVIGATION,
            name = "Keyboard Navigation",
            description = "Tests full keyboard accessibility",
            status = TestStatus.PASSED,
            score = 0.88,
            issues = listOf("Missing focus indicators on custom components"),
            recommendations = listOf("Add visible focus indicators")
        )
    )
}

fun generateSampleI18nTests(): List<I18nTest> {
    return listOf(
        I18nTest(
            id = "i18n_001",
            type = I18nTestType.TEXT_EXPANSION,
            name = "Text Expansion Test",
            description = "Tests layout with expanded German text",
            locale = "de-DE",
            status = TestStatus.PASSED,
            score = 0.85,
            issues = listOf("Minor text clipping in navigation"),
            recommendations = listOf("Increase container width")
        ),
        I18nTest(
            id = "i18n_002",
            type = I18nTestType.RTL_LAYOUT,
            name = "RTL Layout Test",
            description = "Tests right-to-left layout rendering",
            locale = "ar-SA",
            status = TestStatus.FAILED,
            score = 0.45,
            issues = listOf("Icons not mirrored", "Text alignment issues"),
            recommendations = listOf("Implement proper RTL support", "Mirror directional icons")
        )
    )
}

fun generateSampleAccessibilityAudits(): List<AccessibilityAudit> {
    return listOf(
        AccessibilityAudit(
            id = "audit_001",
            name = "Comprehensive Accessibility Audit",
            description = "Full accessibility assessment of the application",
            overallScore = 0.82,
            tests = generateSampleAccessibilityTests(),
            summary = "Good accessibility compliance with some minor issues to address",
            criticalIssues = 1,
            warnings = 2,
            passed = 12
        )
    )
}

fun generateSampleI18nReports(): List<I18nReport> {
    return listOf(
        I18nReport(
            id = "report_001",
            name = "Q4 Internationalization Report",
            description = "Quarterly review of internationalization progress",
            locales = listOf("en-US", "zh-CN", "es-ES", "ar-SA"),
            totalStrings = 1250,
            translatedStrings = 1050,
            completeness = 0.84,
            tests = generateSampleI18nTests(),
            issues = listOf("RTL support incomplete", "Some currencies not formatted correctly")
        )
    )
}

// Main Component
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccessibilityI18nComponent(
    modifier: Modifier = Modifier,
    config: AccessibilityI18nConfig = AccessibilityI18nConfig()
) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Overview", "Accessibility", "Localization", "Testing", "Reports")
    
    // Sample data - now using properly defined functions
    val accessibilitySettings by remember { mutableStateOf(generateSampleAccessibilitySettings()) }
    val locales by remember { mutableStateOf(generateSampleLocales()) }
    val translations by remember { mutableStateOf(generateSampleTranslations()) }
    val accessibilityTests by remember { mutableStateOf(generateSampleAccessibilityTests()) }
    val i18nTests by remember { mutableStateOf(generateSampleI18nTests()) }
    val accessibilityAudits by remember { mutableStateOf(generateSampleAccessibilityAudits()) }
    val i18nReports by remember { mutableStateOf(generateSampleI18nReports()) }
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Header
        AccessibilityI18nHeader(
            config = config,
            accessibilityScore = accessibilityTests.map { it.score }.average(),
            localizationProgress = locales.map { it.completeness }.average()
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
            0 -> AccessibilityI18nOverviewComponent(
                accessibilitySettings = accessibilitySettings,
                locales = locales,
                accessibilityTests = accessibilityTests,
                i18nTests = i18nTests,
                config = config
            )
            1 -> AccessibilityManagementComponent(
                settings = accessibilitySettings,
                tests = accessibilityTests,
                onSettingChange = { _: AccessibilitySettings, _: Boolean -> /* Handle setting change */ }
            )
            2 -> LocalizationManagementComponent(
                locales = locales,
                translations = translations,
                onLocaleChange = { _: LocaleInfo, _: Boolean -> /* Handle locale change */ },
                onTranslationUpdate = { _: TranslationEntry -> /* Handle translation update */ }
            )
            3 -> AccessibilityI18nTestingComponent(
                accessibilityTests = accessibilityTests,
                i18nTests = i18nTests,
                onRunTest = { _: String -> /* Handle test run */ }
            )
            4 -> AccessibilityI18nReportsComponent(
                accessibilityAudits = accessibilityAudits,
                i18nReports = i18nReports,
                onGenerateReport = { _: String -> /* Handle report generation */ }
            )
        }
    }
}

// Component implementations
@Composable
fun AccessibilityI18nHeader(
    config: AccessibilityI18nConfig,
    accessibilityScore: Double,
    localizationProgress: Double,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Accessibility & Internationalization",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ScoreCard(
                    title = "Accessibility Score",
                    score = accessibilityScore,
                    color = getAccessibilityColor(accessibilityScore)
                )
                
                ScoreCard(
                    title = "Localization Progress",
                    score = localizationProgress,
                    color = getLocalizationColor(localizationProgress)
                )
            }
        }
    }
}

@Composable
fun ScoreCard(
    title: String,
    score: Double,
    color: Color,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(60.dp)
                .background(color, CircleShape)
        ) {
            Text(
                text = "${(score * 100).toInt()}%",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

@Composable
fun AccessibilityI18nOverviewComponent(
    accessibilitySettings: List<AccessibilitySettings>,
    locales: List<LocaleInfo>,
    accessibilityTests: List<AccessibilityTest>,
    i18nTests: List<I18nTest>,
    config: AccessibilityI18nConfig,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Overview",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }
        
        item {
            QuickStatsCard(
                accessibilitySettings = accessibilitySettings,
                locales = locales,
                accessibilityTests = accessibilityTests,
                i18nTests = i18nTests
            )
        }
        
        item {
            RecentTestsCard(
                accessibilityTests = accessibilityTests.take(3),
                i18nTests = i18nTests.take(3)
            )
        }
    }
}

@Composable
fun QuickStatsCard(
    accessibilitySettings: List<AccessibilitySettings>,
    locales: List<LocaleInfo>,
    accessibilityTests: List<AccessibilityTest>,
    i18nTests: List<I18nTest>,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Quick Stats",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatItem(
                    label = "Enabled Features",
                    value = "${accessibilitySettings.count { it.enabled }}/${accessibilitySettings.size}"
                )
                
                StatItem(
                    label = "Active Locales",
                    value = "${locales.count { it.enabled }}/${locales.size}"
                )
                
                StatItem(
                    label = "Passed Tests",
                    value = "${accessibilityTests.count { it.status == TestStatus.PASSED } + i18nTests.count { it.status == TestStatus.PASSED }}"
                )
            }
        }
    }
}

@Composable
fun StatItem(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun RecentTestsCard(
    accessibilityTests: List<AccessibilityTest>,
    i18nTests: List<I18nTest>,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Recent Tests",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            accessibilityTests.forEach { test ->
                TestStatusRow(
                    name = test.name,
                    status = test.status,
                    score = test.score
                )
                
                Spacer(modifier = Modifier.height(8.dp))
            }
            
            i18nTests.forEach { test ->
                TestStatusRow(
                    name = test.name,
                    status = test.status,
                    score = test.score
                )
                
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun TestStatusRow(
    name: String,
    status: TestStatus,
    score: Double,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f)
        )
        
        TestStatusBadge(status = status)
        
        Spacer(modifier = Modifier.width(8.dp))
        
        Text(
            text = "${(score * 100).toInt()}%",
            style = MaterialTheme.typography.bodySmall,
            color = getAccessibilityColor(score)
        )
    }
}

@Composable
fun TestStatusBadge(
    status: TestStatus,
    modifier: Modifier = Modifier
) {
    val (color, text) = when (status) {
        TestStatus.PASSED -> Color.Green to "PASSED"
        TestStatus.FAILED -> Color.Red to "FAILED"
        TestStatus.PENDING -> Orange to "PENDING"
        TestStatus.SKIPPED -> Color.Gray to "SKIPPED"
        TestStatus.WARNING -> Orange to "WARNING"
    }
    
    Box(
        modifier = modifier
            .background(color, RoundedCornerShape(4.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }
}

// Placeholder components for other tabs
@Composable
fun AccessibilityManagementComponent(
    settings: List<AccessibilitySettings>,
    tests: List<AccessibilityTest>,
    onSettingChange: (AccessibilitySettings, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Accessibility Management",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }
        
        items(settings) { setting ->
            AccessibilitySettingCard(
                setting = setting,
                onToggle = { onSettingChange(setting, !setting.enabled) }
            )
        }
    }
}

@Composable
fun AccessibilitySettingCard(
    setting: AccessibilitySettings,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
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
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = setting.feature.name.replace("_", " "),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium
                    )
                    
                    if (setting.description.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = setting.description,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                
                Switch(
                    checked = setting.enabled,
                    onCheckedChange = { onToggle() }
                )
            }
        }
    }
}

@Composable
fun LocalizationManagementComponent(
    locales: List<LocaleInfo>,
    translations: List<TranslationEntry>,
    onLocaleChange: (LocaleInfo, Boolean) -> Unit,
    onTranslationUpdate: (TranslationEntry) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Localization Management",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }
        
        items(locales) { locale ->
            LocaleCard(
                locale = locale,
                onToggle = { onLocaleChange(locale, !locale.enabled) }
            )
        }
    }
}

@Composable
fun LocaleCard(
    locale: LocaleInfo,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
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
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = locale.displayName,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium
                    )
                    
                    Spacer(modifier = Modifier.height(4.dp))
                    
                    Text(
                        text = "${(locale.completeness * 100).toInt()}% complete",
                        style = MaterialTheme.typography.bodySmall,
                        color = getLocalizationColor(locale.completeness)
                    )
                }
                
                Switch(
                    checked = locale.enabled,
                    onCheckedChange = { onToggle() }
                )
            }
        }
    }
}

@Composable
fun AccessibilityI18nTestingComponent(
    accessibilityTests: List<AccessibilityTest>,
    i18nTests: List<I18nTest>,
    onRunTest: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Testing",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }
        
        item {
            Text(
                text = "Accessibility Tests",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Medium
            )
        }
        
        items(accessibilityTests) { test ->
            AccessibilityTestCard(test = test)
        }
        
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Internationalization Tests",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Medium
            )
        }
        
        items(i18nTests) { test ->
            I18nTestCard(test = test)
        }
    }
}

@Composable
fun AccessibilityTestCard(
    test: AccessibilityTest,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = test.name,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium
                )
                TestStatusBadge(status = test.status)
            }
            
            if (test.description.isNotEmpty()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = test.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun I18nTestCard(
    test: I18nTest,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = test.name,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium
                )
                TestStatusBadge(status = test.status)
            }
            
            if (test.description.isNotEmpty()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = test.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun AccessibilityI18nReportsComponent(
    accessibilityAudits: List<AccessibilityAudit>,
    i18nReports: List<I18nReport>,
    onGenerateReport: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Reports",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }
        
        item {
            Text(
                text = "Accessibility Audits",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Medium
            )
        }
        
        items(accessibilityAudits) { audit ->
            AccessibilityAuditCard(audit = audit)
        }
        
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Internationalization Reports",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Medium
            )
        }
        
        items(i18nReports) { report ->
            I18nReportCard(report = report)
        }
    }
}

@Composable
fun AccessibilityAuditCard(
    audit: AccessibilityAudit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
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
                Text(
                    text = audit.name,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                
                ScoreChip(score = audit.overallScore)
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = audit.summary,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatChip(
                    label = "Critical",
                    value = audit.criticalIssues.toString(),
                    color = Color.Red
                )
                
                StatChip(
                    label = "Warnings",
                    value = audit.warnings.toString(),
                    color = Orange
                )
                
                StatChip(
                    label = "Passed",
                    value = audit.passed.toString(),
                    color = Color.Green
                )
            }
        }
    }
}

@Composable
fun I18nReportCard(
    report: I18nReport,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
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
                Text(
                    text = report.name,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                
                ScoreChip(score = report.completeness)
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "${report.translatedStrings}/${report.totalStrings} strings translated",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Locales: ${report.locales.joinToString(", ")}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun ScoreChip(
    score: Double,
    modifier: Modifier = Modifier
) {
    val color = getAccessibilityColor(score)
    
    Box(
        modifier = modifier
            .background(color, RoundedCornerShape(12.dp))
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(
            text = "${(score * 100).toInt()}%",
            style = MaterialTheme.typography.labelMedium,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun StatChip(
    label: String,
    value: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.labelLarge,
            color = color,
            fontWeight = FontWeight.Bold
        )
        
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}