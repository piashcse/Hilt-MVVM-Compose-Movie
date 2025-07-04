# Lego Component System - Template Documentation

## 🎯 Tổng Quan

Lego Component System là một hệ thống template thông minh được tích hợp AI, giúp tự động tạo ra các Android Compose components dựa trên mô tả bằng ngôn ngữ tự nhiên. Hệ thống này được thiết kế theo nguyên lý "Lego blocks" - các component có thể kết hợp linh hoạt để tạo ra UI phức tạp.

## 🏗️ Kiến Trúc Hệ Thống

### Core Components

```
com.xiaomi.base.templates/
├── TemplatePackage.kt          # Marker interface và package info
├── ai/                         # AI-powered features
│   ├── ComponentSelector.kt    # AI component selection
│   └── TemplateCustomizer.kt   # AI customization engine
├── demo/                       # Demo và examples
│   ├── DemoActivity.kt         # Demo activity
│   └── LegoComponentDemo.kt    # Interactive demo
├── engine/                     # Template processing engine
│   └── TemplateEngine.kt       # Core template processor
└── registry/                   # Component registry
    ├── ComponentMetadata.kt    # Component metadata
    └── ComponentRegistry.kt    # Central registry
```

### 1. Template Engine (`engine/`)

**TemplateEngine.kt** - Core của hệ thống, xử lý:
- **Placeholder Replacement**: `{{COMPONENT_NAME}}`, `{{PACKAGE_NAME}}`
- **Conditional Blocks**: `{{#if condition}}...{{/if}}`
- **Unless Blocks**: `{{#unless condition}}...{{/unless}}`
- **Loops**: `{{#each items}}...{{/each}}`
- **Project Context Integration**: Tự động inject thông tin project

### 2. Component Registry (`registry/`)

**ComponentRegistry.kt** - Quản lý tất cả components:
- Central registry cho tất cả component templates
- Search và filtering functionality
- Category và tag indexing
- Compatibility checking với project context

**ComponentMetadata.kt** - Metadata cho mỗi component:
- Component information (id, name, category, description)
- Customization options
- Dependencies và requirements
- Complexity level và estimated time
- Tags và use cases

### 3. AI-Powered Features (`ai/`)

**ComponentSelector.kt** - AI component selection:
- Natural language requirement analysis
- Intent detection (CREATE_BUTTON, CREATE_FORM, etc.)
- Keyword extraction và matching
- Component scoring và ranking
- Alternative suggestions

**TemplateCustomizer.kt** - AI customization:
- Smart default value generation
- Context-aware customizations
- Validation và error handling

### 4. Demo System (`demo/`)

**LegoComponentDemo.kt** - Interactive demo:
- Real-time component generation
- Natural language input processing
- Live preview của generated code
- Example prompts và use cases

## 🎨 Component Categories

### Core UI Components
- **BUTTON**: Interactive buttons và action triggers
- **INPUT**: Text fields và form inputs
- **CARD**: Content containers và display cards
- **LAYOUT**: Layout containers và positioning
- **NAVIGATION**: Navigation components và routing
- **DIALOG**: Modal dialogs và overlays
- **LIST**: List displays và data presentation
- **CHART**: Data visualization và charts
- **MEDIA**: Image, video và media components
- **FORM**: Form layouts và validation

### Integration Components
- **AUTHENTICATION**: Login, signup và authentication
- **PAYMENT**: Payment processing components
- **SOCIAL**: Social media integration
- **CAMERA**: Camera và image capture
- **UTILITY**: Helper components và utilities

### Enhanced Components
- **ANIMATION**: Animations và transitions
- **ACCESSIBILITY**: Accessibility components
- **GESTURE**: Gesture handling components
- **FEEDBACK**: User feedback components
- **AI_ML**: AI và Machine Learning components
- **ADAPTIVE**: Adaptive và responsive components
- **ENTERPRISE**: Enterprise-grade components
- **SECURITY**: Security và privacy components
- **TESTING**: Testing utilities và mocks

## 🔧 Complexity Levels

| Level | Time | Description | Skill Level |
|-------|------|-------------|-------------|
| **SIMPLE** | 5-10 min | Chỉ UI component, không có logic phức tạp | Beginner |
| **MEDIUM** | 15-30 min | UI + basic logic, có state management | Intermediate |
| **COMPLEX** | 45-60 min | UI + logic + integration, có external dependencies | Advanced |
| **EXPERT** | 2+ hours | Phức tạp cao, cần customization sâu | Expert |

## 🎯 Customization Options

### Option Types
- **COLOR**: Color picker
- **SIZE**: Size selection (small, medium, large)
- **TEXT**: Text input
- **BOOLEAN**: Toggle switch
- **ENUM**: Dropdown selection
- **NUMBER**: Number input
- **ICON**: Icon picker
- **DIMENSION**: Dimension input (dp, sp)
- **FILE**: File picker
- **RANGE**: Range slider

### Validation Rules
- Required field validation
- Type checking
- Range validation
- Dependency validation
- Custom validation rules

## 🚀 Cách Sử Dụng

### 1. Basic Usage

```kotlin
// Initialize components
val registry = ComponentRegistry
val selector = ComponentSelector(registry)
val engine = TemplateEngine()

// Analyze user requirement
val suggestion = selector.analyzeRequirement(
    requirement = "I want a blue submit button with icon",
    context = projectContext
)

// Generate component
val component = engine.generateComponent(
    metadata = suggestion.suggestedComponents.first().component,
    customizations = mapOf(
        "COMPONENT_NAME" to "SubmitButton",
        "COLOR" to "Blue",
        "HAS_ICON" to true
    ),
    projectContext = projectContext
)
```

### 2. Natural Language Examples

```
✅ "Create a primary button with icon and loading state"
✅ "I need an email input field with validation"
✅ "Make a password field with visibility toggle"
✅ "Create a large red submit button"
✅ "I want a multiline text area for comments"
✅ "Generate a login form with email and password"
✅ "Create a product card with image and price"
```

### 3. Advanced Customization

```kotlin
val customizations = mapOf(
    "COMPONENT_NAME" to "CustomButton",
    "BACKGROUND_COLOR" to "#FF5722",
    "TEXT_COLOR" to "#FFFFFF",
    "CORNER_RADIUS" to "8dp",
    "ELEVATION" to "4dp",
    "HAS_ICON" to true,
    "ICON_NAME" to "send",
    "HAS_LOADING_STATE" to true,
    "ANIMATION_DURATION" to "300ms"
)
```

## 🎨 Template Syntax

### Basic Placeholders
```kotlin
@Composable
fun {{COMPONENT_NAME}}(
    modifier: Modifier = Modifier,
    {{#if HAS_ICON}}icon: ImageVector? = null,{{/if}}
    onClick: () -> Unit
) {
    // Component implementation
}
```

### Conditional Blocks
```kotlin
{{#if HAS_LOADING_STATE}}
var isLoading by remember { mutableStateOf(false) }
{{/if}}

{{#unless IS_SIMPLE}}
// Advanced features
{{/unless}}
```

### Loops
```kotlin
{{#each MENU_ITEMS}}
NavigationItem(
    title = "{{this.title}}",
    icon = {{this.icon}},
    isSelected = selectedIndex == {{@index}}
)
{{/each}}
```

## 🔍 AI Intent Detection

Hệ thống AI có thể detect các intent sau:

- **CREATE_FORM**: "form", "input", "field", "submit", "register", "login"
- **CREATE_LIST**: "list", "items", "scroll", "recyclerview", "collection"
- **CREATE_NAVIGATION**: "navigation", "menu", "tab", "drawer", "bottom"
- **CREATE_CARD**: "card", "item", "product", "post", "article"
- **CREATE_BUTTON**: "button", "click", "action", "submit", "save"
- **CREATE_DIALOG**: "dialog", "popup", "modal", "alert", "confirmation"
- **CREATE_LAYOUT**: "layout", "screen", "page", "container", "wrapper"
- **CREATE_MEDIA**: "image", "video", "photo", "gallery", "camera"
- **CREATE_CHART**: "chart", "graph", "plot", "analytics", "statistics"

## 📊 Registry Statistics

```kotlin
val stats = ComponentRegistry.getRegistryStats()
println("Total components: ${stats.totalComponents}")
println("Categories: ${stats.componentsByCategory}")
println("Most used tags: ${stats.mostUsedTags}")
```

## 🔧 Extension Points

### 1. Custom Component Registration

```kotlin
val customComponent = ComponentMetadata(
    id = "custom_button",
    name = "Custom Button",
    category = ComponentCategory.BUTTON,
    description = "A custom button with special features",
    templatePath = "templates/custom_button.kt",
    customizationOptions = listOf(
        CustomizationOption(
            key = "STYLE",
            displayName = "Button Style",
            type = OptionType.ENUM,
            defaultValue = "primary",
            possibleValues = listOf("primary", "secondary", "outline"),
            description = "Visual style of the button"
        )
    )
)

ComponentRegistry.registerComponent(customComponent)
```

### 2. Custom Template Engine

```kotlin
class CustomTemplateEngine : TemplateEngine() {
    override fun processCustomPlaceholder(placeholder: String, context: Map<String, Any>): String {
        // Custom placeholder processing
        return when (placeholder) {
            "CUSTOM_LOGIC" -> generateCustomLogic(context)
            else -> super.processCustomPlaceholder(placeholder, context)
        }
    }
}
```

## 🚨 Lưu Ý Quan Trọng

### Removable Package
- Template package được đánh dấu là có thể xóa sau khi hoàn thành dự án
- Sử dụng `TemplatePackage.canSafelyRemove()` để kiểm tra
- Chỉ xóa khi không còn dependencies từ production code

### Performance Considerations
- Template processing được cache để tăng performance
- Component registry được index để search nhanh
- Lazy loading cho large templates

### Security
- Validate tất cả user input
- Sanitize template content
- Không execute arbitrary code từ templates

## 🎯 Best Practices

1. **Template Design**:
   - Giữ templates đơn giản và modular
   - Sử dụng meaningful placeholder names
   - Provide good default values

2. **Component Metadata**:
   - Viết description rõ ràng và chi tiết
   - Sử dụng tags phù hợp để dễ search
   - Estimate complexity level chính xác

3. **Customization Options**:
   - Nhóm related options lại với nhau
   - Provide validation rules
   - Use appropriate option types

4. **AI Integration**:
   - Train với diverse examples
   - Handle edge cases gracefully
   - Provide fallback options

## 🔮 Future Enhancements

- **Visual Template Editor**: GUI để tạo và edit templates
- **Template Marketplace**: Share và download community templates
- **Advanced AI**: Better intent detection và context understanding
- **Live Preview**: Real-time preview trong IDE
- **Template Versioning**: Version control cho templates
- **Multi-platform Support**: Templates cho iOS, Web, Desktop

---

**Tác giả**: AI Generated  
**Phiên bản**: 1.0.0  
**Cập nhật lần cuối**: 2024-12-19