# Kế hoạch tái cấu trúc Android-Compose-Base thành Template Base Framework

Tài liệu này mô tả kế hoạch tái cấu trúc dự án Android-Compose-Base để tạo thành một template base framework sử dụng cho hầu hết các loại dự án, học hỏi Clean Architecture concepts từ WeatherApp. Dự án mới sẽ sử dụng package `com.xiaomi.base` và được thiết kế như một template có đầy đủ cấu trúc để phát triển bất kỳ dự án Android nào.

## Mục tiêu

1. Chuyển đổi dự án thành một template base framework với package `com.xiaomi.base`
2. Thêm UseCase layer để tách biệt business logic khỏi ViewModel
3. Tách repository pattern rõ ràng hơn với interfaces trong domain layer
4. Thêm domain models để tách biệt data models khỏi UI
5. Tạo một template base framework có đầy đủ cấu trúc để phát triển bất kỳ dự án Android nào

## Phân tích hiện trạng

Dự án Android-Compose-Base hiện tại đã có cấu trúc MVVM cơ bản với các thành phần chính, nhưng còn tập trung vào domain movie cụ thể. Chúng ta cần chuyển đổi nó thành một template base framework với package `com.xiaomi.base` có thể áp dụng cho bất kỳ dự án nào:

### Cấu trúc hiện tại

```
com.piashcse.hilt_mvvm_compose_movie/
├── data/
│   ├── datasource/
│   │   ├── local/
│   │   │   ├── MovieWorldDataBase.kt
│   │   │   ├── dao/
│   │   │   └── typeconverter/
│   │   └── remote/
│   │       ├── ApiService.kt
│   │       ├── ApiURL.kt
│   │       └── paging_datasource/
│   ├── model/
│   │   ├── BaseModel.kt
│   │   ├── MovieItem.kt
│   │   ├── artist/
│   │   ├── moviedetail/
│   │   └── ...
│   └── repository/
│       ├── local/
│       │   ├── movie/
│       │   │   ├── LocalMovieRepository.kt
│       │   │   └── LocalMovieRepositoryImpl.kt
│       │   └── tvseries/
│       └── remote/
│           ├── movie/
│           │   ├── MovieRepository.kt
│           │   └── MovieRepositoryImpl.kt
│           ├── tvseries/
│           └── ...
├── di/
│   ├── DataBaseModule.kt
│   ├── NetworkModule.kt
│   └── RepositoryModule.kt
├── ui/
│   ├── component/
│   ├── screens/
│   │   ├── movies/
│   │   ├── tvseries/
│   │   └── ...
│   ├── state/
│   └── theme/
└── utils/
```

### Đánh giá

1. **Ưu điểm**:

   - Đã có sự phân tách giữa data và UI layer
   - Sử dụng Repository pattern để truy cập dữ liệu
   - Sử dụng Dependency Injection (Hilt) để quản lý dependencies
   - Có xử lý local và remote data sources

2. **Hạn chế**:
   - Thiếu Domain layer và UseCase để xử lý business logic
   - Các model được sử dụng trực tiếp từ data layer đến UI layer
   - Repository chỉ đơn thuần chuyển tiếp dữ liệu từ data source mà không có xử lý logic
   - Chưa có sự phân tách rõ ràng giữa các entity, domain model và presentation model

## Kế hoạch tái cấu trúc

Dựa trên mô hình Clean Architecture từ WeatherApp, chúng ta sẽ tái cấu trúc dự án như sau:

### Cấu trúc mới

```
com.xiaomi.base/
├── data/                 # Data Layer
│   ├── datasource/
│   │   ├── local/
│   │   │   ├── database/  # Database configuration
│   │   │   │   └── AppDatabase.kt
│   │   │   ├── dao/       # Data Access Objects
│   │   │   │   └── BaseDao.kt
│   │   │   └── typeconverter/ # Type converters for Room
│   │   │       └── DateConverter.kt
│   │   └── remote/
│   │       ├── api/       # API services
│   │       │   ├── ApiService.kt
│   │       │   └── ApiURL.kt
│   │       └── paging/    # Paging sources
│   │           └── BasePagingSource.kt
│   ├── model/            # Data models (DTO)
│   │   └── BaseDto.kt
│   ├── mapper/           # Mappers để chuyển đổi giữa data models và domain models
│   │   └── BaseMapper.kt
│   └── repository/       # Repository implementations
│       ├── local/
│       │   └── BaseLocalRepositoryImpl.kt
│       └── remote/
│           └── BaseRemoteRepositoryImpl.kt
├── domain/               # Domain Layer (mới)
│   ├── model/            # Domain models
│   │   └── BaseModel.kt
│   ├── repository/       # Repository interfaces
│   │   ├── BaseLocalRepository.kt
│   │   └── BaseRemoteRepository.kt
│   └── usecase/          # Use cases
│       └── BaseUseCase.kt
├── di/
│   ├── DataModule.kt
│   ├── DomainModule.kt   # Module mới để provide use cases
│   ├── NetworkModule.kt
│   └── RepositoryModule.kt
├── ui/                   # Presentation Layer
│   ├── base/             # Base components (Activities, Fragments, ViewModels)
│   │   ├── BaseActivity.kt
│   │   └── BaseViewModel.kt
│   ├── component/        # Reusable UI components
│   │   ├── LoadingComponent.kt
│   │   └── ErrorComponent.kt
│   ├── screens/          # Feature screens
│   │   ├── feature1/     # Example feature 1
│   │   │   ├── Feature1Screen.kt
│   │   │   └── Feature1ViewModel.kt
│   │   ├── feature2/     # Example feature 2
│   │   │   ├── Feature2Screen.kt
│   │   │   └── Feature2ViewModel.kt
│   │   └── home/         # Home screen
│   │       ├── HomeScreen.kt
│   │       └── HomeViewModel.kt
│   ├── state/            # UI state management
│   │   └── UiState.kt
│   └── theme/            # App theming
│       ├── Color.kt
│       ├── Theme.kt
│       └── Typography.kt
└── utils/                # Utility classes
    ├── Constants.kt
    ├── Extensions.kt
    └── NetworkUtils.kt
```

### Các thay đổi chính

1. **Thêm Domain Layer**:

   - Tạo các domain models độc lập với data models
   - Chuyển repository interfaces vào domain layer
   - Thêm use cases để xử lý business logic

2. **Cải tiến Data Layer**:

   - Thêm mappers để chuyển đổi giữa data models và domain models
   - Repository implementations sẽ implement interfaces từ domain layer

3. **Cải tiến Presentation Layer (UI)**:

   - ViewModels sẽ sử dụng use cases thay vì repository trực tiếp
   - Sử dụng domain models trong UI thay vì data models

4. **Dependency Injection**:
   - Thêm DomainModule để provide use cases
   - Cập nhật RepositoryModule để bind implementations với interfaces

## Lộ trình triển khai

### Giai đoạn 1: Chuẩn bị

1. Tạo cấu trúc thư mục mới
2. Xác định domain models cần thiết
3. Xác định các use cases cần thiết

### Giai đoạn 2: Domain Layer

1. Tạo domain models
2. Chuyển repository interfaces vào domain layer
3. Tạo các use cases

### Giai đoạn 3: Data Layer

1. Tạo mappers để chuyển đổi giữa data models và domain models
2. Cập nhật repository implementations để implement interfaces từ domain layer

### Giai đoạn 4: Presentation Layer

1. Cập nhật ViewModels để sử dụng use cases
2. Cập nhật UI để sử dụng domain models

### Giai đoạn 5: Dependency Injection

1. Tạo DomainModule
2. Cập nhật RepositoryModule

### Giai đoạn 6: Testing

1. Viết unit tests cho use cases
2. Viết unit tests cho repositories
3. Viết integration tests

## Vấn đề cần giải quyết

### 1. BuildConfig

- **Vấn đề**: Dự án sử dụng buildConfigField trong productFlavors nhưng chưa bật tính năng buildConfig
- **Giải pháp**: Thêm cấu hình buildFeatures.buildConfig = true trong build.gradle.kts của app module:

```kotlin
android {
    // Các cấu hình khác...
    buildFeatures {
        buildConfig = true
    }
}
```

### 2. Xử lý chuyển đổi model

- **Vấn đề**: Cần có cơ chế chuyển đổi giữa data models và domain models
- **Giải pháp**: Tạo các mapper classes để xử lý việc chuyển đổi, ví dụ:

```kotlin
class ItemMapper {
    fun mapToDomain(itemDto: ItemDto): Item {
        return Item(
            id = itemDto.id,
            title = itemDto.title,
            description = itemDto.description,
            // Các trường khác...
        )
    }

    fun mapToData(item: Item): ItemDto {
        return ItemDto(
            id = item.id,
            title = item.title,
            description = item.description,
            // Các trường khác...
        )
    }
}
```

### 3. Quản lý dependency giữa các layer

- **Vấn đề**: Cần đảm bảo luồng dependency đúng theo Clean Architecture (Domain layer không phụ thuộc vào Data và UI layer)
- **Giải pháp**: Sử dụng dependency inversion và injection để đảm bảo luồng dependency đúng:
  - Domain layer chỉ chứa interfaces và không phụ thuộc vào bất kỳ layer nào khác
  - Data layer implement các interfaces từ Domain layer
  - UI layer sử dụng các use cases từ Domain layer

## Ví dụ triển khai

### 1. Domain Layer

#### Domain Model

```kotlin
// domain/model/Item.kt
data class Item(
    val id: Int,
    val title: String,
    val description: String,
    val imageUrl: String,
    val date: String,
    val rating: Double
)
```

#### Repository Interface

```kotlin
// domain/repository/DataRepository.kt
interface DataRepository {
    suspend fun getItems(page: Int): Flow<List<Item>>
    suspend fun getItemDetails(itemId: Int): Flow<Item>
    suspend fun searchItems(query: String): Flow<List<Item>>
}
```

#### UseCase

```kotlin
// domain/usecase/GetItemsUseCase.kt
class GetItemsUseCase @Inject constructor(
    private val dataRepository: DataRepository
) {
    suspend operator fun invoke(page: Int): Flow<List<Item>> {
        return dataRepository.getItems(page)
    }
}
```

### 2. Data Layer

#### Data Model

```kotlin
// data/model/ItemDto.kt
data class ItemDto(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("image_url") val imageUrl: String?,
    @SerializedName("date") val date: String?,
    @SerializedName("rating") val rating: Double
)
```

#### Mapper

```kotlin
// data/mapper/ItemMapper.kt
class ItemMapper @Inject constructor() {
    fun mapToDomain(itemDto: ItemDto): Item {
        return Item(
            id = itemDto.id,
            title = itemDto.title,
            description = itemDto.description,
            imageUrl = itemDto.imageUrl ?: "",
            date = itemDto.date ?: "",
            rating = itemDto.rating
        )
    }

    fun mapToData(item: Item): ItemDto {
        return ItemDto(
            id = item.id,
            title = item.title,
            description = item.description,
            imageUrl = item.imageUrl,
            date = item.date,
            rating = item.rating
        )
    }
}
```

#### Repository Implementation

```kotlin
// data/repository/DataRepositoryImpl.kt
class DataRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val itemMapper: ItemMapper
) : DataRepository {

    override suspend fun getItems(page: Int): Flow<List<Item>> = flow {
        try {
            val response = apiService.getItems(page)
            val items = response.results.map { itemMapper.mapToDomain(it) }
            emit(items)
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getItemDetails(itemId: Int): Flow<Item> = flow {
        try {
            val response = apiService.getItemDetails(itemId)
            emit(itemMapper.mapToDomain(response))
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun searchItems(query: String): Flow<List<Item>> = flow {
        try {
            val response = apiService.searchItems(query)
            val items = response.results.map { itemMapper.mapToDomain(it) }
            emit(items)
        } catch (e: Exception) {
            throw e
        }
    }
}
```

### 3. Presentation Layer

#### UiState

```kotlin
// ui/common/UiState.kt
data class UiState<T>(
    val isLoading: Boolean = false,
    val data: T? = null,
    val error: String? = null
) {
    val isSuccess: Boolean get() = data != null && !isLoading && error == null
    val isError: Boolean get() = error != null && !isLoading
}
```

#### ViewModel

```kotlin
// ui/screens/feature1/FeatureViewModel.kt
@HiltViewModel
class FeatureViewModel @Inject constructor(
    private val getItemsUseCase: GetItemsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState<List<Item>>())
    val uiState: StateFlow<UiState<List<Item>>> = _uiState.asStateFlow()

    private val _currentPage = MutableStateFlow(1)

    init {
        loadItems()
    }

    fun loadItems() {
        viewModelScope.launch {
            _uiState.value = UiState(isLoading = true)
            try {
                getItemsUseCase(_currentPage.value).collect { items ->
                    _uiState.value = UiState(data = items)
                }
            } catch (e: Exception) {
                _uiState.value = UiState(error = e.message ?: "Unknown error")
            }
        }
    }

    fun loadNextPage() {
        _currentPage.value += 1
        loadItems()
    }
}
```

#### UI Component

```kotlin
// ui/screens/feature1/FeatureScreen.kt
@Composable
fun FeatureScreen(
    viewModel: FeatureViewModel = hiltViewModel(),
    onItemClick: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            uiState.isLoading && uiState.data == null -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            uiState.isError -> {
                ErrorView(
                    message = uiState.error ?: "Unknown error",
                    onRetry = { viewModel.loadItems() },
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            uiState.isSuccess -> {
                ItemList(
                    items = uiState.data ?: emptyList(),
                    onItemClick = onItemClick,
                    onLoadMore = { viewModel.loadNextPage() }
                )
            }
        }
    }
}

@Composable
fun ItemList(
    items: List<Item>,
    onItemClick: (Int) -> Unit,
    onLoadMore: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.fillMaxSize()
    ) {
        items(items) { item ->
            ItemCard(
                item = item,
                onClick = { onItemClick(item.id) }
            )
        }

        item {
            LaunchedEffect(items.size) {
                onLoadMore()
            }
            CircularProgressIndicator(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.Center)
            )
        }
    }
}

@Composable
fun ItemCard(
    item: Item,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column {
            AsyncImage(
                model = item.imageUrl,
                contentDescription = item.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            )

            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = item.date.take(4),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = null,
                        tint = Color.Yellow,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = item.rating.toString(),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
            }
        }
    }
}
```

### 4. Dependency Injection

```kotlin
// di/DomainModule.kt
@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    @Singleton
    fun provideGetItemsUseCase(dataRepository: DataRepository): GetItemsUseCase {
        return GetItemsUseCase(dataRepository)
    }

    // Provide other use cases...
}

// di/RepositoryModule.kt
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideDataRepository(
        apiService: ApiService,
        itemMapper: ItemMapper
    ): DataRepository {
        return DataRepositoryImpl(apiService, itemMapper)
    }

    // Provide other repositories...
}
```

## Kết luận

Việc tái cấu trúc dự án Android-Compose-Base thành một template base framework với package `com.xiaomi.base` theo mô hình Clean Architecture sẽ mang lại nhiều lợi ích:

1. **Tách biệt rõ ràng giữa các layer**: Domain layer độc lập với các layer khác, giúp dễ dàng thay đổi implementation mà không ảnh hưởng đến business logic.

2. **Dễ dàng kiểm thử**: Các use case và domain model có thể được kiểm thử độc lập với UI và data source.

3. **Khả năng mở rộng tốt hơn**: Cấu trúc rõ ràng giúp dễ dàng thêm tính năng mới mà không làm ảnh hưởng đến code hiện tại.

4. **Tái sử dụng code tối đa**: Template base framework với package `com.xiaomi.base` có thể được tái sử dụng cho bất kỳ dự án Android nào, không còn phụ thuộc vào domain cụ thể như movie. Các nhóm phát triển có thể nhanh chóng bắt đầu dự án mới mà không cần thiết lập lại kiến trúc cơ bản.

5. **Dễ dàng bảo trì**: Mỗi layer có trách nhiệm rõ ràng, giúp dễ dàng tìm và sửa lỗi, đồng thời giảm thiểu rủi ro khi thay đổi code.

6. **Tính linh hoạt cao**: Với cấu trúc template base, các dự án mới có thể nhanh chóng được khởi tạo và phát triển với một nền tảng vững chắc, đồng thời vẫn có thể tùy chỉnh theo yêu cầu cụ thể của từng dự án.

Lộ trình triển khai đã được phác thảo rõ ràng, với các bước cụ thể và ví dụ triển khai chi tiết. Việc tái cấu trúc sẽ được thực hiện theo từng giai đoạn, đảm bảo ứng dụng vẫn hoạt động bình thường trong quá trình chuyển đổi.

Sau khi hoàn thành tái cấu trúc, dự án sẽ trở thành một template base framework mạnh mẽ với package `com.xiaomi.base`, có đầy đủ cấu trúc để phát triển bất kỳ dự án Android nào trong tương lai. Template này sẽ cung cấp một nền tảng vững chắc với các thành phần cơ bản đã được thiết lập sẵn, giúp các nhà phát triển tập trung vào việc xây dựng các tính năng đặc thù của dự án thay vì phải lo lắng về cấu trúc cơ bản.

```

```
