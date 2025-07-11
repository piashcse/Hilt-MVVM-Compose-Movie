package com.xiaomi.base.ui.kit.templates

/**
 * Xiaomi Templates - Pre-built Screen Templates
 * 
 * This file provides organized access to pre-built screen templates
 * that combine multiple components to create common UI patterns.
 * These templates serve as starting points for rapid development.
 * 
 * Inspired by ComposeX organization patterns for better developer experience.
 */

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.xiaomi.base.ui.kit.components.XiaomiActions
import com.xiaomi.base.ui.kit.components.XiaomiContainment
import com.xiaomi.base.ui.kit.foundation.XiaomiDesignSystem
import com.xiaomi.base.ui.kit.foundation.XiaomiTheme
import com.xiaomi.base.ui.kit.foundation.spacing.spacing

/**
 * Authentication Templates
 * 
 * Pre-built templates for authentication flows including
 * login, registration, password reset, and onboarding.
 */
object XiaomiAuthTemplates {
    
    /**
     * Basic Login Screen Template
     * 
     * A simple login screen with email/password fields and login button.
     */
    @Composable
    fun LoginScreen(
        onLoginClick: () -> Unit = {},
        onForgotPasswordClick: () -> Unit = {},
        onSignUpClick: () -> Unit = {},
        modifier: Modifier = Modifier
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(MaterialTheme.spacing.ScreenPaddingHorizontal),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Logo/Title area
            Text(
                text = "Welcome Back",
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.primary
            )
            
            Text(
                text = "Sign in to your account",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = MaterialTheme.spacing.Small)
            )
            
            // Form area (placeholder)
            XiaomiContainment.Cards.Basic(
                modifier = Modifier.padding(vertical = MaterialTheme.spacing.XXL)
            ) {
                Column(
                    modifier = Modifier.padding(MaterialTheme.spacing.Large),
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.Medium)
                ) {
                    Text(
                        text = "Email and password fields would go here",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    
                    XiaomiActions.Buttons.Primary(
                        onClick = onLoginClick,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text("Sign In")
                    }
                }
            }
            
            // Additional actions
            XiaomiActions.Buttons.Text(
                onClick = onForgotPasswordClick
            ) {
                Text("Forgot Password?")
            }
            
            XiaomiActions.Buttons.Text(
                onClick = onSignUpClick
            ) {
                Text("Don't have an account? Sign Up")
            }
        }
    }
    
    /**
     * Registration Screen Template
     */
    @Composable
    fun RegistrationScreen(
        onRegisterClick: () -> Unit = {},
        onSignInClick: () -> Unit = {},
        modifier: Modifier = Modifier
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(MaterialTheme.spacing.ScreenPaddingHorizontal),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Create Account",
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.primary
            )
            
            Text(
                text = "Join us today",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = MaterialTheme.spacing.Small)
            )
            
            // Registration form placeholder
            XiaomiContainment.Cards.Basic(
                modifier = Modifier.padding(vertical = MaterialTheme.spacing.XXL)
            ) {
                Column(
                    modifier = Modifier.padding(MaterialTheme.spacing.Large),
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.Medium)
                ) {
                    Text(
                        text = "Registration form fields would go here",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    
                    XiaomiActions.Buttons.Primary(
                        onClick = onRegisterClick,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text("Create Account")
                    }
                }
            }
            
            XiaomiActions.Buttons.Text(
                onClick = onSignInClick
            ) {
                Text("Already have an account? Sign In")
            }
        }
    }
}

/**
 * Dashboard Templates
 * 
 * Pre-built templates for dashboard and home screens
 * with various layouts and content organization patterns.
 */
object XiaomiDashboardTemplates {
    
    /**
     * Basic Dashboard Template
     * 
     * A simple dashboard with header, stats cards, and content sections.
     */
    @Composable
    fun BasicDashboard(
        userName: String = "User",
        onProfileClick: () -> Unit = {},
        onSettingsClick: () -> Unit = {},
        modifier: Modifier = Modifier
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(MaterialTheme.spacing.ScreenPaddingHorizontal)
        ) {
            // Header
            Column(
                modifier = Modifier.padding(vertical = MaterialTheme.spacing.Large)
            ) {
                Text(
                    text = "Hello, $userName",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Welcome back to your dashboard",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            // Stats cards
            Column(
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.Medium)
            ) {
                XiaomiContainment.Cards.Feature {
                    Column(
                        modifier = Modifier.padding(MaterialTheme.spacing.Large)
                    ) {
                        Text(
                            text = "Quick Stats",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = "Your overview at a glance",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                
                XiaomiContainment.Cards.Basic {
                    Column(
                        modifier = Modifier.padding(MaterialTheme.spacing.Large)
                    ) {
                        Text(
                            text = "Recent Activity",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = "Your latest actions and updates",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

/**
 * Onboarding Templates
 * 
 * Pre-built templates for user onboarding flows
 * including welcome screens, feature introductions, and setup wizards.
 */
object XiaomiOnboardingTemplates {
    
    /**
     * Welcome Screen Template
     */
    @Composable
    fun WelcomeScreen(
        onGetStartedClick: () -> Unit = {},
        onSkipClick: () -> Unit = {},
        modifier: Modifier = Modifier
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(MaterialTheme.spacing.ScreenPaddingHorizontal),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Welcome to Xiaomi",
                style = MaterialTheme.typography.displayMedium,
                color = MaterialTheme.colorScheme.primary
            )
            
            Text(
                text = "Discover amazing features and possibilities",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = MaterialTheme.spacing.Medium)
            )
            
            Column(
                modifier = Modifier.padding(top = MaterialTheme.spacing.XXL),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.Medium)
            ) {
                XiaomiActions.Buttons.Primary(
                    onClick = onGetStartedClick,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text("Get Started")
                }
                
                XiaomiActions.Buttons.Text(
                    onClick = onSkipClick
                ) {
                    Text("Skip for now")
                }
            }
        }
    }
}

/**
 * Profile Templates
 * 
 * Pre-built templates for user profile screens
 * including profile viewing, editing, and settings.
 */
object XiaomiProfileTemplates {
    
    /**
     * Basic Profile Screen Template
     */
    @Composable
    fun BasicProfile(
        userName: String = "John Doe",
        userEmail: String = "john.doe@example.com",
        onEditClick: () -> Unit = {},
        onSettingsClick: () -> Unit = {},
        onLogoutClick: () -> Unit = {},
        modifier: Modifier = Modifier
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(MaterialTheme.spacing.ScreenPaddingHorizontal)
        ) {
            // Profile header
            XiaomiContainment.Cards.Elevated(
                modifier = Modifier.padding(vertical = MaterialTheme.spacing.Large)
            ) {
                Column(
                    modifier = Modifier.padding(MaterialTheme.spacing.Large),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = userName,
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Text(
                        text = userEmail,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    
                    XiaomiActions.Buttons.Outlined(
                        onClick = onEditClick,
                        modifier = Modifier.padding(top = MaterialTheme.spacing.Medium)
                    ) {
                        Text("Edit Profile")
                    }
                }
            }
            
            // Profile actions
            Column(
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.Medium)
            ) {
                XiaomiContainment.Cards.Clickable(
                    onClick = onSettingsClick
                ) {
                    Text(
                        text = "Settings",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(MaterialTheme.spacing.Large)
                    )
                }
                
                XiaomiActions.Buttons.Text(
                    onClick = onLogoutClick
                ) {
                    Text(
                        text = "Logout",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}

/**
 * Template Registry
 * 
 * Central registry for all available templates
 */
object XiaomiTemplateRegistry {
    
    /**
     * Get all available template categories
     */
    fun getTemplateCategories() = mapOf(
        "Authentication" to listOf("Login", "Registration", "Password Reset"),
        "Dashboard" to listOf("Basic Dashboard", "Analytics Dashboard"),
        "Onboarding" to listOf("Welcome", "Feature Tour", "Setup Wizard"),
        "Profile" to listOf("Basic Profile", "Detailed Profile", "Settings")
    )
    
    /**
     * Get template count by category
     */
    fun getTemplateCounts() = mapOf(
        "Authentication" to 2,
        "Dashboard" to 1,
        "Onboarding" to 1,
        "Profile" to 1
    )
}

// Preview composables
@Preview(name = "Login Template")
@Composable
fun LoginTemplatePreview() {
    XiaomiTheme {
        XiaomiAuthTemplates.LoginScreen()
    }
}

@Preview(name = "Dashboard Template")
@Composable
fun DashboardTemplatePreview() {
    XiaomiTheme {
        XiaomiDashboardTemplates.BasicDashboard()
    }
}

@Preview(name = "Welcome Template")
@Composable
fun WelcomeTemplatePreview() {
    XiaomiTheme {
        XiaomiOnboardingTemplates.WelcomeScreen()
    }
}

@Preview(name = "Profile Template")
@Composable
fun ProfileTemplatePreview() {
    XiaomiTheme {
        XiaomiProfileTemplates.BasicProfile()
    }
}