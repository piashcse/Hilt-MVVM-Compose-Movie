# Kế Hoạch Refactoring: Universal Android Compose Base

**Ngày tạo**: 2024-12-19  
**Mục tiêu**: Chuyển đổi cấu trúc từ movie-oriented sang universal base framework có thể adapt cho bất kỳ loại dự án nào  
**Scope**: Architecture refactoring, model optimization, screen restructuring

## 🎯 Tổng Quan

### Hiện Trạng

- ✅ Clean Architecture đã triển khai tốt
- ✅ Generic Item model thay vì Movie-specific
- ✅ Comprehensive component system
- 🔄 Navigation và screens vẫn mang tính movie app
- 🔄 Database entities có một số redundant cho health/photo apps
- 🔄 Domain model cần tối ưu cho use cases mới

### Mục Tiêu

- 🎯 Pure universal base framework cho mọi loại Android app
- 🎯 Generic architecture patterns không tied vào domain cụ thể
- 🎯 Maintainable và scalable architecture
- 🎯 Rich component library có thể customize cho bất kỳ use case nào

## 📋 Phase 1: Domain Model Optimization

### 1.1 Refactor Item Model

**File**: `app/src/main/java/com/xiaomi/base/domain/model/Item.kt`

**Current Issues:**

```kotlin
data class Item(
    val duration: Int? = null, // Movie-specific
    val language: String? = null, // Movie-specific
    val releaseDate: Date? = null, // Movie-specific
    val backdropUrl: String? = null, // Movie-specific
    val rating: Float = 0f // Could be more generic
)
```

**New Universal Item Model:**

```kotlin
data class Item(
    val id: Int,
    val title: String,
    val description: String? = null,
    val imageUrl: String? = null,
    val thumbnailUrl: String? = null, // Renamed from backdropUrl
    val score: Float = 0f, // Renamed from rating (more universal)
    val createdDate: Date? = null, // Renamed from releaseDate
    val lastModified: Date? = null, // New field
    val status: ItemStatus = ItemStatus.ACTIVE, // New field
    val metadata: Map<String, Any> = emptyMap(), // New flexible field
    val categories: List<Category> = emptyList(),
    val isFavorite: Boolean = false,
    val tags: List<String> = emptyList() // New field
)

enum class ItemStatus {
    ACTIVE, INACTIVE, DRAFT, ARCHIVED, DELETED
}
```

### 1.2 Create Additional Universal Models

**Generic Extension Models:**

```kotlin
// UserData.kt - Generic user-generated content
data class UserData(
    val id: Int,
    val userId: String,
    val dataType: String, // "metric", "content", "preference", etc.
    val value: String, // JSON or simple value
    val unit: String? = null,
    val timestamp: Date,
    val metadata: Map<String, Any> = emptyMap()
)

// AppConfig.kt - App-wide configuration
data class AppConfig(
    val key: String,
    val value: String,
    val category: ConfigCategory,
    val isUserEditable: Boolean = false,
    val description: String? = null
)

enum class ConfigCategory {
    UI, BEHAVIOR, INTEGRATION, USER_PREFERENCE
}
```

## 📋 Phase 2: Database Schema Optimization

### 2.1 Remove Movie-Specific Entities

**Files to modify:**

- `FavoriteCreatorEntity.kt` → Remove or generalize to `FavoriteUserEntity`
- Update `AppDatabase.kt` to remove creator-related tables

### 2.2 Create Universal Database Schema

**New Entities:**

```kotlin
// UserProfileEntity.kt
@Entity(tableName = "user_profiles")
data class UserProfileEntity(
    @PrimaryKey val userId: String,
    val displayName: String,
    val email: String?,
    val profileImagePath: String?,
    val preferences: String, // JSON string
    val createdDate: Date,
    val lastActiveDate: Date
)

// UserDataEntity.kt (for health metrics, photo metadata, etc.)
@Entity(tableName = "user_data")
data class UserDataEntity(
    @PrimaryKey val id: String,
    val userId: String,
    val dataType: String, // "health_metric", "photo_asset", etc.
    val data: String, // JSON string for flexible storage
    val timestamp: Date,
    val tags: String // Comma-separated tags
)
```

### 2.3 Update Database Configuration

**File**: `AppDatabase.kt`

```kotlin
@Database(
    entities = [
        FavoriteItemEntity::class,
        FavoriteCategoryEntity::class,
        UserProfileEntity::class, // New
        UserDataEntity::class, // New
        // Remove FavoriteCreatorEntity
    ],
    version = 2, // Increment version
    exportSchema = false
)
```

## 📋 Phase 3: Navigation & Screen Restructuring

### 3.1 Remove Movie-Specific Screens

**Current screens to refactor:**

- `popular/` → `trending/` or `discover/`
- `toprated/` → `featured/` or `highlights/`
- Keep: `home/`, `favorite/`, `itemdetail/`

### 3.2 Create Universal Navigation Structure

**File**: `navigation/Screen.kt`

```kotlin
sealed class Screen(val route: String, @StringRes val title: Int, val icon: ImageVector) {
    object Home : Screen("home", R.string.home, Icons.Default.Home)
    object Discover : Screen("discover", R.string.discover, Icons.Default.Explore)
    object Featured : Screen("featured", R.string.featured, Icons.Default.Star)
    object Favorites : Screen("favorites", R.string.favorites, Icons.Default.Favorite)
    object Profile : Screen("profile", R.string.profile, Icons.Default.Person)
    object Settings : Screen("settings", R.string.settings, Icons.Default.Settings)

    // Dynamic screens
    object ItemDetail : Screen("item_detail/{itemId}", R.string.item_detail, Icons.Default.Home)
    object CategoryView : Screen("category/{categoryId}", R.string.category, Icons.Default.Category)

    // Specialized screens (can be enabled based on app type)
    object HealthDashboard : Screen("health_dashboard", R.string.health_dashboard, Icons.Default.MonitorHeart)
    object PhotoEditor : Screen("photo_editor", R.string.photo_editor, Icons.Default.Edit)
}
```

### 3.3 Create Flexible Screen Components

**New Universal Screens:**

- `dashboard/` - Main dashboard with customizable widgets
- `dataview/` - Universal data visualization (charts, lists, grids)
- `editor/` - Universal editor interface (photo, text, data)
- `profile/` - User profile and preferences
- `analytics/` - Data analytics and insights

## 📋 Phase 4: Component System Enhancement

### 4.1 Create Universal UI Components

**New Components:**

```kotlin
// DataVisualization Components
@Composable
fun MetricCard(
    title: String,
    value: String,
    unit: String?,
    trend: TrendDirection?,
    onClick: () -> Unit = {}
)

@Composable
fun ProgressChart(
    data: List<ChartDataPoint>,
    chartType: ChartType = ChartType.LINE
)

@Composable
fun StatsGrid(
    stats: List<StatItem>,
    columns: Int = 2
)

// Content Display Components
@Composable
fun UniversalCard(
    item: Item,
    displayMode: CardDisplayMode = CardDisplayMode.STANDARD,
    onItemClick: (Item) -> Unit,
    onFavoriteClick: (Item) -> Unit = {}
)

@Composable
fun MediaViewer(
    mediaUrl: String,
    mediaType: MediaType,
    onEdit: (() -> Unit)? = null
)
```

### 4.2 Create Domain-Agnostic Extension Components

**Generic Input Components:**

```kotlin
@Composable
fun UniversalFormInput(
    inputType: InputType,
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    validator: ((String) -> Boolean)? = null
)

@Composable
fun GenericProgressCard(
    title: String,
    currentValue: Float,
    targetValue: Float,
    unit: String? = null,
    onUpdate: ((Float) -> Unit)? = null
)
```

**Content Management Components:**

```kotlin
@Composable
fun ContentToolbar(
    availableActions: List<ContentAction>,
    onActionSelect: (ContentAction) -> Unit
)

@Composable
fun ActionPreview(
    originalContent: Any,
    previewAction: PreviewAction?,
    onApply: () -> Unit,
    onCancel: () -> Unit
)
```

## 📋 Phase 5: Use Cases Refactoring

### 5.1 Create Universal Use Cases

**File**: `domain/usecase/common/`

```kotlin
// GetUserDataUseCase.kt
class GetUserDataUseCase @Inject constructor(
    private val repository: UserDataRepository
) {
    suspend operator fun invoke(
        userId: String,
        dataType: String? = null,
        timeRange: TimeRange? = null
    ): Flow<List<UserData>>
}

// SearchItemsUseCase.kt
class SearchItemsUseCase @Inject constructor(
    private val repository: ItemRepository
) {
    suspend operator fun invoke(
        query: String,
        filters: SearchFilters = SearchFilters(),
        sortBy: SortOption = SortOption.RELEVANCE
    ): Flow<PagingData<Item>>
}
```

### 5.2 Create Domain-Flexible Use Cases

**Data Management Use Cases:**

```kotlin
// ProcessUserDataUseCase.kt - Generic data processing
// CalculateMetricsUseCase.kt - Universal metrics calculation
// GenerateInsightsUseCase.kt - Generic insights generation
```

**Content Processing Use Cases:**

```kotlin
// ProcessContentUseCase.kt - Universal content processing
// ApplyContentActionUseCase.kt - Generic content modifications
// ExportContentUseCase.kt - Universal export functionality
```

## 📋 Phase 6: Preview System Enhancement

### 6.1 Enhance Demo System

**Current preview system is excellent, enhance it:**

- Add generic app demos in `preview/demos/generic/`
- Add different domain showcases in `preview/demos/showcases/`
- Create universal component previews với customizable props

### 6.2 Create Interactive Playground

**New Feature:**

```kotlin
@Composable
fun ComponentPlayground() {
    // Interactive playground to test components
    // with different configurations
}
```

## 🚀 Implementation Timeline

### Week 1: Foundation

- [ ] Refactor Item model và related classes
- [ ] Update database schema và migrations
- [ ] Create universal base components

### Week 2: Navigation & Screens

- [ ] Refactor navigation structure
- [ ] Create universal screen templates
- [ ] Implement new dashboard concept

### Week 3: Universal Components

- [ ] Domain-agnostic form components
- [ ] Content management components
- [ ] Data visualization components

### Week 4: Use Cases & Integration

- [ ] Implement universal use cases
- [ ] Create domain-flexible use cases
- [ ] Integration testing và documentation

## ✅ Acceptance Criteria

### Universal Base Framework

- [ ] Can be used as base for any Android app
- [ ] No movie-specific terminology or concepts
- [ ] Flexible data models accommodating various app types
- [ ] Rich component library for rapid development

### Domain Adaptability Ready

- [ ] Generic data tracking capabilities
- [ ] Flexible goal/target management system
- [ ] Universal data visualization components
- [ ] Customizable user profile system

### Content Management Ready

- [ ] Universal asset management system
- [ ] Flexible content manipulation interface
- [ ] Generic action/filter application framework
- [ ] Universal export và sharing capabilities

### Technical Excellence

- [ ] Maintains Clean Architecture principles
- [ ] 90%+ test coverage for core functionality
- [ ] Performance optimized
- [ ] Well documented API

## 🔧 Migration Strategy

### Backward Compatibility

- Keep existing interfaces during transition
- Create adapter classes for old components
- Gradual migration path for existing features

### Data Migration

- Database migration scripts for schema changes
- Data preservation during entity restructuring
- Rollback strategy if needed

## 📚 Documentation Updates

### API Documentation

- [ ] Update all component documentation
- [ ] Create usage examples for each app type
- [ ] Migration guide from movie app

### Developer Guide

- [ ] Setup guide for new app types
- [ ] Component selection guide
- [ ] Best practices document

---

**Status**: 📋 Ready for Implementation  
**Priority**: P0 - Foundation refactoring  
**Dependencies**: None  
**Risk Level**: Medium (database migration)
