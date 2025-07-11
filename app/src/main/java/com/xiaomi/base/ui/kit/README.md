# Xiaomi Base UI Kit

A comprehensive UI component library for Android applications built with Jetpack Compose, following Material Design 3 guidelines with Xiaomi brand identity.

## 🎯 Overview

The Xiaomi Base UI Kit provides a consistent, scalable, and maintainable design system for Android applications. It combines Material Design 3 principles with Xiaomi's brand guidelines to create a cohesive user experience.

## 📁 Structure

```
ui/kit/
├── foundation/           # Design tokens and core styling
│   ├── colors/          # Color tokens and schemes
│   ├── typography/      # Typography system
│   ├── spacing/         # Spacing tokens
│   ├── shapes/          # Shape tokens
│   └── XiaomiTheme.kt   # Main theme provider
├── components/          # UI components organized by category
│   ├── actions/         # Interactive components
│   │   ├── buttons/     # Button variants
│   │   ├── fab/         # Floating action buttons
│   │   └── chips/       # Chip components
│   ├── communication/   # Feedback components
│   │   ├── badges/      # Badge indicators
│   │   ├── progress/    # Progress indicators
│   │   └── snackbars/   # Snackbar notifications
│   ├── containment/     # Layout components
│   │   ├── cards/       # Card containers
│   │   ├── dividers/    # Content dividers
│   │   └── lists/       # List components
│   ├── navigation/      # Navigation components
│   │   ├── appbars/     # App bars
│   │   ├── bottom/      # Bottom navigation
│   │   ├── drawer/      # Navigation drawer
│   │   ├── rail/        # Navigation rail
│   │   └── tabs/        # Tab navigation
│   ├── selection/       # Input components
│   │   ├── checkboxes/  # Checkbox inputs
│   │   ├── menus/       # Menu components
│   │   ├── radiobuttons/# Radio button inputs
│   │   ├── sliders/     # Slider inputs
│   │   └── switches/    # Switch inputs
│   ├── textinputs/      # Text input components
│   │   ├── textfields/  # Text field inputs
│   │   └── pickers/     # Date/time pickers
│   └── specialized/     # Xiaomi-specific components
│       ├── biometric/   # Biometric authentication
│       ├── ai/          # AI-powered components
│       ├── performance/ # Performance monitoring
│       ├── security/    # Security features
│       └── animation/   # Custom animations
├── templates/           # Pre-built screen templates
│   ├── onboarding/      # Onboarding flows
│   ├── dashboard/       # Dashboard layouts
│   ├── profile/         # Profile screens
│   └── auth/            # Authentication screens
├── utils/               # Utility functions and extensions
│   ├── modifiers/       # Custom modifiers
│   ├── animations/      # Animation utilities
│   ├── previews/        # Preview utilities
│   └── extensions/      # Extension functions
├── catalog/             # Component catalog and documentation
└── legacy/              # Legacy components (migration support)
    ├── components/      # Old components
    └── screens/         # Old screen implementations
```

## 🚀 Getting Started

### 1. Apply the Theme

Wrap your app content with `XiaomiTheme`:

```kotlin
@Composable
fun MyApp() {
    XiaomiTheme {
        // Your app content
        MyAppContent()
    }
}
```

### 2. Use Components

Import and use UI Kit components:

```kotlin
import com.xiaomi.base.ui.kit.components.actions.buttons.XiaomiButton
import com.xiaomi.base.ui.kit.components.containment.cards.XiaomiCard

@Composable
fun MyScreen() {
    XiaomiCard {
        Text(
            text = "Welcome to Xiaomi",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(MaterialTheme.spacing.Large)
        )
        
        XiaomiButton(
            onClick = { /* Handle click */ },
            modifier = Modifier.padding(MaterialTheme.spacing.Large)
        ) {
            Text("Get Started")
        }
    }
}
```

### 3. Access Design Tokens

Use design tokens for consistent styling:

```kotlin
@Composable
fun MyComponent() {
    Column(
        modifier = Modifier.padding(MaterialTheme.spacing.Large),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.Medium)
    ) {
        Text(
            text = "Success Message",
            color = MaterialTheme.extendedColors.success,
            style = MaterialTheme.typography.titleMedium
        )
    }
}
```

## 🎨 Design Tokens

### Colors

- **Primary Colors**: Xiaomi orange theme with Material Design 3 tonal palette
- **Secondary Colors**: Complementary blue-gray palette
- **Extended Colors**: Success, warning, info colors for enhanced communication
- **Semantic Colors**: Context-specific color assignments

### Typography

- **Display**: Largest text (57sp, 45sp, 36sp)
- **Headline**: High-emphasis text (32sp, 28sp, 24sp)
- **Title**: Medium-emphasis text (22sp, 16sp, 14sp)
- **Body**: Main content text (16sp, 14sp, 12sp)
- **Label**: Component text (14sp, 12sp, 11sp)

### Spacing

- **Base Unit**: 4dp
- **Scale**: 2dp, 4dp, 8dp, 12dp, 16dp, 20dp, 24dp, 32dp, 40dp, 48dp, 64dp, 80dp
- **Semantic Tokens**: Content padding, component spacing, screen margins

### Shapes

- **Corner Radius**: 0dp, 4dp, 8dp, 12dp, 16dp, 28dp, 50dp
- **Component Shapes**: Button, card, input field specific shapes
- **Directional Shapes**: Top, bottom, start, end rounded variants

## 🧩 Component Categories

### Actions
Interactive components that trigger user actions:
- **Buttons**: Primary, secondary, text, outlined variants
- **FABs**: Floating action buttons with different sizes
- **Chips**: Filter, input, assist, suggestion chips

### Communication
Components that provide feedback to users:
- **Badges**: Notification indicators
- **Progress**: Linear and circular progress indicators
- **Snackbars**: Temporary messages and actions

### Containment
Components that group and organize content:
- **Cards**: Flexible content containers
- **Dividers**: Visual content separation
- **Lists**: Structured content presentation

### Navigation
Components for app navigation:
- **App Bars**: Top and bottom app bars
- **Navigation**: Bottom navigation, drawer, rail, tabs

### Selection
Components for user input and selection:
- **Checkboxes**: Multi-selection inputs
- **Radio Buttons**: Single-selection inputs
- **Sliders**: Range selection inputs
- **Switches**: Toggle inputs
- **Menus**: Dropdown selections

### Text Inputs
Components for text entry:
- **Text Fields**: Single and multi-line text inputs
- **Pickers**: Date, time, and option pickers

### Specialized
Xiaomi-specific components:
- **Biometric**: Fingerprint, face recognition
- **AI**: Smart suggestions, voice commands
- **Performance**: System monitoring, optimization
- **Security**: Privacy controls, permissions
- **Animation**: Brand-specific motion design

## 📱 Templates

Pre-built screen templates for common use cases:
- **Onboarding**: Welcome flows and feature introductions
- **Dashboard**: Home screens and data visualization
- **Profile**: User account and settings screens
- **Authentication**: Login, signup, and verification flows

## 🛠 Development Guidelines

### Component Creation

1. **Follow Material Design 3**: Adhere to MD3 principles and patterns
2. **Use Design Tokens**: Always use foundation tokens for styling
3. **Provide Previews**: Include comprehensive preview composables
4. **Document APIs**: Add KDoc comments for all public APIs
5. **Support Accessibility**: Implement proper semantics and content descriptions

### Naming Conventions

- **Components**: `Xiaomi[ComponentName]` (e.g., `XiaomiButton`)
- **Tokens**: Descriptive names (e.g., `Primary40`, `SpacingLarge`)
- **Files**: PascalCase matching the main component name

### Code Organization

- **Single Responsibility**: One component per file
- **Logical Grouping**: Group related variants together
- **Clear Hierarchy**: Organize by Material Design categories

## 🔄 Migration Guide

### Phase 1: Foundation Setup ✅
- [x] Create design token system
- [x] Implement theme provider
- [x] Set up component structure

### Phase 2: Core Components (In Progress)
- [ ] Migrate existing buttons
- [ ] Migrate existing cards
- [ ] Migrate existing text fields
- [ ] Migrate existing navigation components

### Phase 3: Advanced Components
- [ ] Implement specialized Xiaomi components
- [ ] Create animation system
- [ ] Build template library

### Phase 4: Integration & Documentation
- [ ] Update existing screens to use UI Kit
- [ ] Create comprehensive documentation
- [ ] Set up automated testing

## 📖 Component Catalog

View all available components in the interactive catalog:

```kotlin
@Composable
fun PreviewCatalog() {
    XiaomiTheme {
        ComponentCatalog()
    }
}
```

## 🎯 Benefits

- **Consistency**: Unified design language across the application
- **Efficiency**: Faster development with pre-built components
- **Maintainability**: Centralized styling and behavior management
- **Scalability**: Easy to extend and customize for new requirements
- **Quality**: Tested and optimized components following best practices

## 🤝 Contributing

1. Follow the established patterns and conventions
2. Add comprehensive tests for new components
3. Update documentation and previews
4. Ensure accessibility compliance
5. Test across different screen sizes and orientations

## 📚 Resources

- [Material Design 3](https://m3.material.io/)
- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Xiaomi Design Guidelines](https://www.mi.com/global/design)
- [Accessibility Guidelines](https://developer.android.com/guide/topics/ui/accessibility)

---

**Version**: 1.0.0  
**Last Updated**: 2024  
**Maintainer**: Xiaomi Base Android Team