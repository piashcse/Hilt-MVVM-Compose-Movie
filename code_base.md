# Android Compose Base - Codebase Documentation

## 📋 Tổng Quan Dự Án

**Android Compose Base** là một template framework Android sử dụng Jetpack Compose, được thiết kế để làm cơ sở cho việc phát triển các ứng dụng Android hiện đại. Dự án đang trong quá trình tái cấu trúc từ một demo ứng dụng thành một base framework có thể tái sử dụng.

### Thông Tin Cơ Bản
- **Package Name**: `com.xiaomi.base`
- **Version**: 2.2.0
- **Min SDK**: 23
- **Target SDK**: 35
- **Compile SDK**: 36
- **Build Tools**: Android Gradle Plugin 8.11.0
- **Kotlin Version**: 2.2.0

## 🏗️ Kiến Trúc Tổng Thể

### Kiến Trúc Chính
- **MVVM (Model-View-ViewModel)** với Repository Pattern
- **Clean Architecture** với phân tách rõ ràng các layer:
  - **Presentation Layer**: Compose UI + ViewModels
  - **Domain Layer**: Use Cases + Repository Interfaces
  - **Data Layer**: Repository Implementations + Data Sources

### Tech Stack Chính
- **UI Framework**: Jetpack Compose
- **Navigation**: Compose Navigation
- **Dependency Injection**: Hilt
- **Database**: Room
- **Network**: Retrofit + OkHttp + Gson
- **Async**: Kotlin Coroutines + Flow
- **Paging**: Paging 3
- **Logging**: Timber

## 📁 Cấu Trúc Thư Mục

```
app/
├── src/main/
│   ├── java/com/xiaomi/base/
│   │   ├── data/
│   │   │   ├── api/           # API services và DTOs
│   │   │   ├── repository/    # Repository implementations
│   │   │   └── mapper/        # Data mappers
│   │   ├── domain/
│   │   │   ├── model/         # Domain models
│   │   │   ├── repository/    # Repository interfaces
│   │   │   └── usecase/       # Use cases
│   │   ├── presentation/
│   │   │   ├── ui/
│   │   │   │   ├── screen/    # Compose screens
│   │   │   │   ├── component/ # Reusable components
│   │   │   │   └── theme/     # Theme và styling
│   │   │   ├── viewmodel/     # ViewModels
│   │   │   └── navigation/    # Navigation setup
│   │   ├── di/               # Dependency injection modules
│   │   └── BaseApplication.kt # Application class
│   ├── res/
│   │   ├── values/           # Strings, colors, themes
│   │   └── values-night/     # Dark theme resources
│   └── AndroidManifest.xml
├── build.gradle.kts          # App module build config
└── proguard-rules.pro

build.gradle.kts              # Project level build config
gradle/libs.versions.toml     # Version catalog
settings.gradle.kts           # Project settings
```

## 🔧 Cấu Hình Dự Án

### Dependencies Chính (từ libs.versions.toml)
```toml
[versions]
androidGradlePlugin = "8.11.0"
kotlin = "2.2.0"
compose-bom = "2024.12.01"
hilt = "2.54"
room = "2.6.1"
retrofit = "2.11.0"
paging = "3.3.5"
timber = "5.0.1"

[libraries]
# Compose UI
compose-bom = { group = "androidx.compose", name = "compose-bom", version.ref = "compose-bom" }
compose-ui = { group = "androidx.compose.ui", name = "ui" }
compose-material3 = { group = "androidx.compose.material3", name = "material3" }
compose-navigation = { group = "androidx.navigation", name = "navigation-compose", version = "2.8.5" }

# Hilt DI
hilt-android = { group = "com.google.dagger", name = "hilt-android", version.ref = "hilt" }
hilt-compiler = { group = "com.google.dagger", name = "hilt-compiler", version.ref = "hilt" }
hilt-navigation-compose = { group = "androidx.hilt", name = "hilt-navigation-compose", version = "1.2.0" }

# Room Database
room-runtime = { group = "androidx.room", name = "room-runtime", version.ref = "room" }
room-compiler = { group = "androidx.room", name = "room-compiler", version.ref = "room" }
room-ktx = { group = "androidx.room", name = "room-ktx", version.ref = "room" }

# Network
retrofit = { group = "com.squareup.retrofit2", name = "retrofit", version.ref = "retrofit" }
retrofit-gson = { group = "com.squareup.retrofit2", name = "converter-gson", version.ref = "retrofit" }
okhttp-logging = { group = "com.squareup.okhttp3", name = "logging-interceptor", version = "4.12.0" }

# Paging
paging-runtime = { group = "androidx.paging", name = "paging-runtime", version.ref = "paging" }
paging-compose = { group = "androidx.paging", name = "paging-compose", version.ref = "paging" }
```

### Build Configuration
- **Product Flavor**: "develop" với API key configuration
- **Build Types**: Debug và Release
- **Compose Compiler**: Enabled với stability configuration
- **MultiDex**: Enabled

## 📱 Presentation Layer

### Navigation Structure
```kotlin
// Định nghĩa trong Screen.kt
sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Home : Screen("home", "Home", Icons.Default.Home)
    object Popular : Screen("popular", "Popular", Icons.Default.Star)
    object TopRated : Screen("top_rated", "Top Rated", Icons.Default.ThumbUp)
    object Favorite : Screen("favorite", "Favorite", Icons.Default.Favorite)
    object ItemDetail : Screen("item_detail/{itemId}", "Detail", Icons.Default.Info)
}
```

### Main Screens
1. **HomeScreen**: Hiển thị các danh sách item (trending, popular, top rated, upcoming)
2. **PopularScreen**: Danh sách item phổ biến với paging
3. **TopRatedScreen**: Danh sách item được đánh giá cao
4. **FavoriteScreen**: Danh sách item yêu thích
5. **ItemDetailScreen**: Chi tiết item với thông tin đầy đủ

### UI Components
- **ItemCarousel**: Component hiển thị danh sách item theo dạng carousel
- **BaseAppTheme**: Theme chính của ứng dụng
- **MainActivity**: Activity chính với splash screen và theme setup

## 🗄️ Data Layer

### Domain Models
```kotlin
// Item.kt - Domain model chính
data class Item(
    val id: Int,
    val title: String,
    val description: String,
    val imageUrl: String,
    val rating: Double,
    val releaseDate: String,
    val language: String,
    val duration: Int,
    val categories: List<String>,
    val isFavorite: Boolean = false
)
```

### Repository Pattern
```kotlin
// ItemRepository.kt - Interface
interface ItemRepository {
    suspend fun getPopularItems(page: Int): Flow<PagingData<Item>>
    suspend fun getTopRatedItems(page: Int): Flow<PagingData<Item>>
    suspend fun getUpcomingItems(page: Int): Flow<PagingData<Item>>
    suspend fun getTrendingItems(page: Int): Flow<PagingData<Item>>
    suspend fun getFavoriteItems(): Flow<List<Item>>
    suspend fun getItemDetails(itemId: Int): Flow<Item?>
    suspend fun addToFavorites(itemId: Int)
    suspend fun removeFromFavorites(itemId: Int)
    suspend fun isFavorite(itemId: Int): Flow<Boolean>
}
```

### API Layer
```kotlin
// ApiService.kt - Retrofit interface
interface ApiService {
    @GET("items/popular")
    suspend fun getPopularItems(@Query("page") page: Int): ApiResponse<List<ItemDto>>
    
    @GET("items/top-rated")
    suspend fun getTopRatedItems(@Query("page") page: Int): ApiResponse<List<ItemDto>>
    
    @GET("items/{id}")
    suspend fun getItemDetails(@Path("id") itemId: Int): ApiResponse<ItemDto>
    
    // ... other endpoints
}
```

### Data Transfer Objects
```kotlin
// ItemDto.kt - API response model
data class ItemDto(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("original_language") val originalLanguage: String,
    @SerializedName("runtime") val runtime: Int?,
    @SerializedName("genre_ids") val genreIds: List<Int>?,
    @SerializedName("genres") val genres: List<GenreDto>?
)
```

## 🔄 Domain Layer

### Use Cases
```kotlin
// GetPopularItemsUseCase.kt
class GetPopularItemsUseCase @Inject constructor(
    private val repository: ItemRepository
) {
    operator fun invoke(): Flow<PagingData<Item>> {
        return repository.getPopularItems(1) // Paging handled by repository
    }
}
```

### Mapper Pattern
- **ItemMapper**: Chuyển đổi từ ItemDto sang Item domain model
- Xử lý null safety và data transformation
- Mapping các field phức tạp như genres, dates

## 🔌 Dependency Injection

### Network Module
```kotlin
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }
    
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.example.com/v1/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    
    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}
```

## 📱 Application Setup

### BaseApplication
```kotlin
@HiltAndroidApp
class BaseApplication : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        
        // Initialize Timber for logging
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
```

### Manifest Configuration
- **Internet Permission**: Cho network calls
- **Application Class**: BaseApplication
- **Main Activity**: MainActivity với launcher intent
- **Theme**: AppTheme

## 🎨 Resources

### Strings (strings.xml)
```xml
<resources>
    <string name="app_name">Base App</string>
    <string name="home_title">Home</string>
    <string name="popular_title">Popular</string>
    <string name="top_rated_title">Top Rated</string>
    <string name="favorite_title">Favorite</string>
    <string name="upcoming_title">Upcoming</string>
    <string name="trending_title">Trending</string>
    <!-- ... more strings -->
</resources>
```

### Theme Support
- **Light Theme**: Định nghĩa trong `values/themes.xml`
- **Dark Theme**: Định nghĩa trong `values-night/themes.xml`
- **Colors**: Định nghĩa trong `values/colors.xml`

## 🔄 Trạng Thái Hiện Tại

### Đã Hoàn Thành
- ✅ Cấu trúc dự án cơ bản
- ✅ Navigation setup với Compose Navigation
- ✅ Dependency injection với Hilt
- ✅ Network layer với Retrofit
- ✅ Domain models và repository interfaces
- ✅ Basic UI screens với Compose
- ✅ Theme và styling setup

### Đang Phát Triển
- 🔄 Repository implementations (nhiều method còn TODO)
- 🔄 Database layer với Room
- 🔄 Paging implementation
- 🔄 Error handling
- 🔄 Local storage cho favorites

### Cần Hoàn Thiện
- ❌ Unit tests
- ❌ Integration tests
- ❌ Error handling comprehensive
- ❌ Offline support
- ❌ Performance optimization
- ❌ Accessibility support

## 🚀 Hướng Dẫn Phát Triển

### Để Thêm Screen Mới
1. Tạo route trong `Screen.kt`
2. Thêm destination trong `NavGraph.kt`
3. Tạo Composable screen trong `presentation/ui/screen/`
4. Tạo ViewModel nếu cần trong `presentation/viewmodel/`
5. Thêm navigation logic

### Để Thêm API Endpoint Mới
1. Thêm method trong `ApiService.kt`
2. Tạo DTO tương ứng trong `data/api/`
3. Thêm method trong Repository interface
4. Implement trong Repository implementation
5. Tạo Use Case nếu cần

### Để Thêm Database Entity
1. Tạo Entity class với Room annotations
2. Thêm DAO interface
3. Update Database class
4. Thêm migration nếu cần
5. Update Repository implementation

## 📚 Tài Liệu Tham Khảo

### File Quan Trọng
- `ConversionPlan.md`: Kế hoạch tái cấu trúc dự án
- `CodebaseAnalysis_UniversalDemo_2024-12-19.md`: Phân tích codebase chi tiết
- `README.md`: Hướng dẫn sử dụng và setup
- `.project-identity`: Cấu hình identity của dự án

### Best Practices
- Sử dụng Kotlin Coroutines cho async operations
- Implement proper error handling với sealed classes
- Follow Material Design 3 guidelines
- Sử dụng Compose best practices
- Maintain clean architecture principles
- Write comprehensive tests

---

**Lưu ý**: Đây là một dự án template đang trong quá trình phát triển. Nhiều tính năng còn ở trạng thái TODO và cần được hoàn thiện để sử dụng trong production.