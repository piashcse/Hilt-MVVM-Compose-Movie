package com.xiaomi.base.components.enterprise.security

import android.content.Context
import android.hardware.biometrics.BiometricManager
import android.os.Build
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import kotlinx.coroutines.delay
import java.security.MessageDigest
import java.util.regex.Pattern
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

/**
 * Security Level
 */
enum class SecurityLevel {
    LOW,
    MEDIUM,
    HIGH,
    CRITICAL
}

/**
 * Authentication Method
 */
enum class AuthenticationMethod {
    PASSWORD,
    PIN,
    BIOMETRIC,
    TWO_FACTOR,
    CERTIFICATE,
    TOKEN
}

/**
 * Validation Result
 */
data class ValidationResult(
    val isValid: Boolean,
    val securityLevel: SecurityLevel,
    val issues: List<SecurityIssue> = emptyList(),
    val recommendations: List<String> = emptyList(),
    val score: Int = 0 // 0-100
)

/**
 * Security Issue
 */
data class SecurityIssue(
    val type: SecurityIssueType,
    val severity: SecurityLevel,
    val message: String,
    val recommendation: String
)

/**
 * Security Issue Type
 */
enum class SecurityIssueType {
    WEAK_PASSWORD,
    INSECURE_CONNECTION,
    OUTDATED_ENCRYPTION,
    MISSING_AUTHENTICATION,
    DATA_EXPOSURE,
    PRIVILEGE_ESCALATION,
    INJECTION_VULNERABILITY,
    CONFIGURATION_ERROR
}

/**
 * Security Configuration
 */
data class SecurityConfig(
    val requiredSecurityLevel: SecurityLevel = SecurityLevel.MEDIUM,
    val enableBiometric: Boolean = true,
    val enableTwoFactor: Boolean = false,
    val passwordMinLength: Int = 8,
    val passwordRequireSpecialChars: Boolean = true,
    val passwordRequireNumbers: Boolean = true,
    val passwordRequireUppercase: Boolean = true,
    val sessionTimeout: Long = 300000L, // 5 minutes
    val maxLoginAttempts: Int = 3,
    val enableEncryption: Boolean = true
)

/**
 * Security Validation Component
 * Comprehensive security validation and enforcement
 * 
 * @param config Security configuration
 * @param onValidationResult Callback with validation results
 * @param content Content to be secured
 */
@Composable
fun SecurityValidationComponent(
    config: SecurityConfig = SecurityConfig(),
    onValidationResult: (ValidationResult) -> Unit = {},
    content: @Composable (ValidationResult, Boolean) -> Unit
) {
    var validationResult by remember { mutableStateOf(ValidationResult(false, SecurityLevel.LOW)) }
    var isValidating by remember { mutableStateOf(false) }
    val context = LocalContext.current
    
    LaunchedEffect(config) {
        isValidating = true
        delay(500) // Simulate validation time
        validationResult = performSecurityValidation(context, config)
        onValidationResult(validationResult)
        isValidating = false
    }
    
    content(validationResult, isValidating)
}

/**
 * Password Strength Validator Component
 * Validates and displays password strength
 * 
 * @param password Password to validate
 * @param config Security configuration
 * @param onStrengthChanged Callback when strength changes
 */
@Composable
fun PasswordStrengthValidatorComponent(
    password: String,
    config: SecurityConfig = SecurityConfig(),
    onStrengthChanged: (ValidationResult) -> Unit = {}
) {
    val strengthResult = remember(password) {
        validatePasswordStrength(password, config)
    }
    
    LaunchedEffect(strengthResult) {
        onStrengthChanged(strengthResult)
    }
    
    Column {
        // Strength indicator
        PasswordStrengthIndicator(
            result = strengthResult
        )
        
        // Issues and recommendations
        if (strengthResult.issues.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            SecurityIssuesList(
                issues = strengthResult.issues
            )
        }
    }
}

/**
 * Biometric Authentication Component
 * Handles biometric authentication
 * 
 * @param title Authentication title
 * @param subtitle Authentication subtitle
 * @param onSuccess Callback on successful authentication
 * @param onError Callback on authentication error
 * @param onCancel Callback on authentication cancellation
 */
@Composable
fun BiometricAuthenticationComponent(
    title: String = "Biometric Authentication",
    subtitle: String = "Use your fingerprint or face to authenticate",
    onSuccess: () -> Unit = {},
    onError: (String) -> Unit = {},
    onCancel: () -> Unit = {}
) {
    val context = LocalContext.current
    var isAvailable by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    
    LaunchedEffect(Unit) {
        isAvailable = isBiometricAvailable(context)
    }
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.Fingerprint,
                contentDescription = "Biometric",
                modifier = Modifier.size(48.dp),
                tint = if (isAvailable) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                }
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = if (isAvailable) subtitle else "Biometric authentication not available",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
            
            errorMessage?.let { error ->
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = error,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Button(
                onClick = {
                    if (isAvailable) {
                        authenticateWithBiometric(
                            context = context,
                            title = title,
                            subtitle = subtitle,
                            onSuccess = onSuccess,
                            onError = { error ->
                                errorMessage = error
                                onError(error)
                            },
                            onCancel = onCancel
                        )
                    }
                },
                enabled = isAvailable,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Authenticate")
            }
        }
    }
}

/**
 * Two-Factor Authentication Component
 * Handles 2FA verification
 * 
 * @param phoneNumber Phone number for SMS
 * @param onCodeSent Callback when code is sent
 * @param onVerificationSuccess Callback on successful verification
 * @param onVerificationError Callback on verification error
 */
@Composable
fun TwoFactorAuthenticationComponent(
    phoneNumber: String = "",
    onCodeSent: () -> Unit = {},
    onVerificationSuccess: () -> Unit = {},
    onVerificationError: (String) -> Unit = {}
) {
    var verificationCode by remember { mutableStateOf("") }
    var isCodeSent by remember { mutableStateOf(false) }
    var isVerifying by remember { mutableStateOf(false) }
    var countdown by remember { mutableStateOf(0) }
    
    // Countdown timer for resend
    LaunchedEffect(isCodeSent) {
        if (isCodeSent) {
            countdown = 60
            while (countdown > 0) {
                delay(1000)
                countdown--
            }
        }
    }
    
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
                    imageVector = Icons.Default.Security,
                    contentDescription = "2FA",
                    modifier = Modifier.size(24.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Two-Factor Authentication",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            if (!isCodeSent) {
                Text(
                    text = "We'll send a verification code to $phoneNumber",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Button(
                    onClick = {
                        isCodeSent = true
                        onCodeSent()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Send Code")
                }
            } else {
                Text(
                    text = "Enter the 6-digit code sent to $phoneNumber",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                OutlinedTextField(
                    value = verificationCode,
                    onValueChange = { if (it.length <= 6) verificationCode = it },
                    label = { Text("Verification Code") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Button(
                    onClick = {
                        if (verificationCode.length == 6) {
                            isVerifying = true
                            // Simulate verification
                            // In real app, this would call verification API
                            if (verificationCode == "123456") {
                                onVerificationSuccess()
                            } else {
                                onVerificationError("Invalid verification code")
                            }
                            isVerifying = false
                        }
                    },
                    enabled = verificationCode.length == 6 && !isVerifying,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (isVerifying) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(16.dp),
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text("Verify")
                    }
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                TextButton(
                    onClick = {
                        if (countdown == 0) {
                            isCodeSent = true
                            onCodeSent()
                        }
                    },
                    enabled = countdown == 0
                ) {
                    Text(
                        if (countdown > 0) "Resend in ${countdown}s" else "Resend Code"
                    )
                }
            }
        }
    }
}

/**
 * Security Dashboard Component
 * Overview of security status and recommendations
 * 
 * @param validationResult Current security validation result
 * @param onActionClick Callback when action is clicked
 */
@Composable
fun SecurityDashboardComponent(
    validationResult: ValidationResult,
    onActionClick: (String) -> Unit = {}
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Security score
            SecurityScoreIndicator(
                score = validationResult.score,
                level = validationResult.securityLevel
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Issues summary
            if (validationResult.issues.isNotEmpty()) {
                Text(
                    text = "Security Issues (${validationResult.issues.size})",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                validationResult.issues.take(3).forEach { issue ->
                    SecurityIssueItem(
                        issue = issue,
                        onClick = { onActionClick("fix_${issue.type}") }
                    )
                }
                
                if (validationResult.issues.size > 3) {
                    TextButton(
                        onClick = { onActionClick("view_all_issues") }
                    ) {
                        Text("View All Issues")
                    }
                }
            }
            
            // Recommendations
            if (validationResult.recommendations.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = "Recommendations",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                validationResult.recommendations.take(2).forEach { recommendation ->
                    RecommendationItem(
                        text = recommendation,
                        onClick = { onActionClick("apply_recommendation") }
                    )
                }
            }
        }
    }
}

/**
 * Password Strength Indicator
 */
@Composable
private fun PasswordStrengthIndicator(
    result: ValidationResult
) {
    val strengthColor = when (result.securityLevel) {
        SecurityLevel.LOW -> MaterialTheme.colorScheme.error
        SecurityLevel.MEDIUM -> Color(0xFFFF9800) // Orange
        SecurityLevel.HIGH -> MaterialTheme.colorScheme.primary
        SecurityLevel.CRITICAL -> Color(0xFF4CAF50) // Green
    }
    
    val strengthText = when (result.securityLevel) {
        SecurityLevel.LOW -> "Weak"
        SecurityLevel.MEDIUM -> "Fair"
        SecurityLevel.HIGH -> "Good"
        SecurityLevel.CRITICAL -> "Strong"
    }
    
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Password Strength: $strengthText",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
            
            Text(
                text = "${result.score}/100",
                style = MaterialTheme.typography.bodySmall,
                color = strengthColor
            )
        }
        
        Spacer(modifier = Modifier.height(4.dp))
        
        LinearProgressIndicator(
            progress = result.score / 100f,
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .clip(RoundedCornerShape(2.dp)),
            color = strengthColor
        )
    }
}

/**
 * Security Score Indicator
 */
@Composable
private fun SecurityScoreIndicator(
    score: Int,
    level: SecurityLevel
) {
    val scoreColor = when (level) {
        SecurityLevel.LOW -> MaterialTheme.colorScheme.error
        SecurityLevel.MEDIUM -> Color(0xFFFF9800)
        SecurityLevel.HIGH -> MaterialTheme.colorScheme.primary
        SecurityLevel.CRITICAL -> Color(0xFF4CAF50)
    }
    
    val scoreIcon = when (level) {
        SecurityLevel.LOW -> Icons.Default.Warning
        SecurityLevel.MEDIUM -> Icons.Default.Info
        SecurityLevel.HIGH -> Icons.Default.CheckCircle
        SecurityLevel.CRITICAL -> Icons.Default.Verified
    }
    
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = scoreIcon,
            contentDescription = "Security Level",
            modifier = Modifier.size(32.dp),
            tint = scoreColor
        )
        
        Spacer(modifier = Modifier.width(12.dp))
        
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "Security Score",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            
            Text(
                text = "$score/100 - ${level.name.lowercase().replaceFirstChar { it.uppercase() }}",
                style = MaterialTheme.typography.bodyMedium,
                color = scoreColor
            )
        }
    }
}

/**
 * Security Issues List
 */
@Composable
private fun SecurityIssuesList(
    issues: List<SecurityIssue>
) {
    Column {
        issues.forEach { issue ->
            SecurityIssueItem(
                issue = issue,
                onClick = {}
            )
        }
    }
}

/**
 * Security Issue Item
 */
@Composable
private fun SecurityIssueItem(
    issue: SecurityIssue,
    onClick: () -> Unit
) {
    val severityColor = when (issue.severity) {
        SecurityLevel.LOW -> MaterialTheme.colorScheme.onSurfaceVariant
        SecurityLevel.MEDIUM -> Color(0xFFFF9800)
        SecurityLevel.HIGH -> MaterialTheme.colorScheme.error
        SecurityLevel.CRITICAL -> Color(0xFFD32F2F)
    }
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = "Issue",
                modifier = Modifier.size(16.dp),
                tint = severityColor
            )
            
            Spacer(modifier = Modifier.width(8.dp))
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = issue.message,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Medium
                )
                
                Text(
                    text = issue.recommendation,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

/**
 * Recommendation Item
 */
@Composable
private fun RecommendationItem(
    text: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Lightbulb,
                contentDescription = "Recommendation",
                modifier = Modifier.size(16.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            
            Spacer(modifier = Modifier.width(8.dp))
            
            Text(
                text = text,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.weight(1f)
            )
            
            IconButton(
                onClick = onClick,
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Apply",
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

/**
 * Helper Functions
 */

/**
 * Perform comprehensive security validation
 */
private fun performSecurityValidation(
    context: Context,
    config: SecurityConfig
): ValidationResult {
    val issues = mutableListOf<SecurityIssue>()
    val recommendations = mutableListOf<String>()
    var score = 100
    
    // Check device security
    if (!isDeviceSecure(context)) {
        issues.add(
            SecurityIssue(
                type = SecurityIssueType.MISSING_AUTHENTICATION,
                severity = SecurityLevel.HIGH,
                message = "Device lock screen not enabled",
                recommendation = "Enable device lock screen with PIN, password, or biometric"
            )
        )
        score -= 30
    }
    
    // Check app permissions
    if (hasExcessivePermissions(context)) {
        issues.add(
            SecurityIssue(
                type = SecurityIssueType.PRIVILEGE_ESCALATION,
                severity = SecurityLevel.MEDIUM,
                message = "App has excessive permissions",
                recommendation = "Review and revoke unnecessary permissions"
            )
        )
        score -= 15
    }
    
    // Check encryption
    if (!config.enableEncryption) {
        issues.add(
            SecurityIssue(
                type = SecurityIssueType.DATA_EXPOSURE,
                severity = SecurityLevel.HIGH,
                message = "Data encryption disabled",
                recommendation = "Enable data encryption for sensitive information"
            )
        )
        score -= 25
    }
    
    // Generate recommendations
    if (config.requiredSecurityLevel == SecurityLevel.HIGH) {
        recommendations.add("Enable two-factor authentication")
        recommendations.add("Use biometric authentication when available")
    }
    
    val securityLevel = when {
        score >= 90 -> SecurityLevel.CRITICAL
        score >= 70 -> SecurityLevel.HIGH
        score >= 50 -> SecurityLevel.MEDIUM
        else -> SecurityLevel.LOW
    }
    
    return ValidationResult(
        isValid = score >= 70,
        securityLevel = securityLevel,
        issues = issues,
        recommendations = recommendations,
        score = score
    )
}

/**
 * Validate password strength
 */
private fun validatePasswordStrength(
    password: String,
    config: SecurityConfig
): ValidationResult {
    val issues = mutableListOf<SecurityIssue>()
    var score = 0
    
    // Length check
    if (password.length >= config.passwordMinLength) {
        score += 25
    } else {
        issues.add(
            SecurityIssue(
                type = SecurityIssueType.WEAK_PASSWORD,
                severity = SecurityLevel.HIGH,
                message = "Password too short",
                recommendation = "Use at least ${config.passwordMinLength} characters"
            )
        )
    }
    
    // Uppercase check
    if (config.passwordRequireUppercase && password.any { it.isUpperCase() }) {
        score += 15
    } else if (config.passwordRequireUppercase) {
        issues.add(
            SecurityIssue(
                type = SecurityIssueType.WEAK_PASSWORD,
                severity = SecurityLevel.MEDIUM,
                message = "No uppercase letters",
                recommendation = "Include at least one uppercase letter"
            )
        )
    }
    
    // Numbers check
    if (config.passwordRequireNumbers && password.any { it.isDigit() }) {
        score += 15
    } else if (config.passwordRequireNumbers) {
        issues.add(
            SecurityIssue(
                type = SecurityIssueType.WEAK_PASSWORD,
                severity = SecurityLevel.MEDIUM,
                message = "No numbers",
                recommendation = "Include at least one number"
            )
        )
    }
    
    // Special characters check
    if (config.passwordRequireSpecialChars && password.any { !it.isLetterOrDigit() }) {
        score += 15
    } else if (config.passwordRequireSpecialChars) {
        issues.add(
            SecurityIssue(
                type = SecurityIssueType.WEAK_PASSWORD,
                severity = SecurityLevel.MEDIUM,
                message = "No special characters",
                recommendation = "Include at least one special character"
            )
        )
    }
    
    // Complexity bonus
    if (password.length > 12) score += 10
    if (password.any { it.isLowerCase() }) score += 10
    if (hasVariedCharacters(password)) score += 10
    
    val securityLevel = when {
        score >= 80 -> SecurityLevel.CRITICAL
        score >= 60 -> SecurityLevel.HIGH
        score >= 40 -> SecurityLevel.MEDIUM
        else -> SecurityLevel.LOW
    }
    
    return ValidationResult(
        isValid = score >= 60,
        securityLevel = securityLevel,
        issues = issues,
        score = score
    )
}

/**
 * Check if biometric authentication is available
 */
private fun isBiometricAvailable(context: Context): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val biometricManager = androidx.biometric.BiometricManager.from(context)
        biometricManager.canAuthenticate(androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_WEAK) == androidx.biometric.BiometricManager.BIOMETRIC_SUCCESS
    } else {
        false
    }
}

/**
 * Authenticate with biometric
 */
private fun authenticateWithBiometric(
    context: Context,
    title: String,
    subtitle: String,
    onSuccess: () -> Unit,
    onError: (String) -> Unit,
    onCancel: () -> Unit
) {
    if (context is FragmentActivity) {
        val biometricPrompt = BiometricPrompt(
            context as androidx.fragment.app.FragmentActivity,
            androidx.core.content.ContextCompat.getMainExecutor(context),
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    onSuccess()
                }
                
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    if (errorCode == BiometricPrompt.ERROR_USER_CANCELED) {
                        onCancel()
                    } else {
                        onError(errString.toString())
                    }
                }
                
                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    onError("Authentication failed")
                }
            }
        )
        
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(title)
            .setSubtitle(subtitle)
            .setNegativeButtonText("Cancel")
            .build()
        
        biometricPrompt.authenticate(promptInfo)
    }
}

/**
 * Check if device is secure
 */
private fun isDeviceSecure(context: Context): Boolean {
    val keyguardManager = context.getSystemService(Context.KEYGUARD_SERVICE) as android.app.KeyguardManager
    return keyguardManager.isDeviceSecure
}

/**
 * Check if app has excessive permissions
 */
private fun hasExcessivePermissions(context: Context): Boolean {
    // This would check actual app permissions
    return false // Placeholder
}

/**
 * Check if password has varied characters
 */
private fun hasVariedCharacters(password: String): Boolean {
    val charTypes = mutableSetOf<String>()
    password.forEach { char ->
        when {
            char.isUpperCase() -> charTypes.add("upper")
            char.isLowerCase() -> charTypes.add("lower")
            char.isDigit() -> charTypes.add("digit")
            !char.isLetterOrDigit() -> charTypes.add("special")
        }
    }
    return charTypes.size >= 3
}

/**
 * Security Utilities
 */
class SecurityUtils {
    companion object {
        /**
         * Hash password with salt
         */
        fun hashPassword(password: String, salt: String): String {
            val digest = MessageDigest.getInstance("SHA-256")
            val hash = digest.digest((password + salt).toByteArray())
            return hash.joinToString("") { "%02x".format(it) }
        }
        
        /**
         * Generate secure random salt
         */
        fun generateSalt(): String {
            val random = java.security.SecureRandom()
            val salt = ByteArray(16)
            random.nextBytes(salt)
            return salt.joinToString("") { "%02x".format(it) }
        }
        
        /**
         * Encrypt data
         */
        fun encryptData(data: String, key: SecretKey): ByteArray {
            val cipher = Cipher.getInstance("AES/GCM/NoPadding")
            cipher.init(Cipher.ENCRYPT_MODE, key)
            return cipher.doFinal(data.toByteArray())
        }
        
        /**
         * Decrypt data
         */
        fun decryptData(encryptedData: ByteArray, key: SecretKey): String {
            val cipher = Cipher.getInstance("AES/GCM/NoPadding")
            cipher.init(Cipher.DECRYPT_MODE, key)
            return String(cipher.doFinal(encryptedData))
        }
    }
}