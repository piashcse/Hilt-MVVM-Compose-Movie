# Test Driven Development (TDD) Guidelines

## 🎯 Nguyên Tắc TDD Cốt Lõi

### Red-Green-Refactor Cycle

```
🔴 RED    → Viết test thất bại đầu tiên
🟢 GREEN  → Viết code tối thiểu để test pass
🔵 REFACTOR → Cải thiện code giữ nguyên test
```

**BẮT BUỘC tuân thủ nguyên tắc:**

- **_BẮT BUỘC_** viết test TRƯỚC khi viết production code
- **_BẮT BUỘC_** chỉ viết đủ code để test pass
- **_BẮT BUỘC_** refactor sau khi test green
- **_BẮT BUỘC_** commit sau mỗi complete cycle
- **_NGHIÊM CẤM_** viết production code mà không có test cover

📋 **Platform-Specific TDD**: Xem [project-setup.md](setup/project-setup.md#8-test-driven-development-tdd-setup---bắt-buộc) cho workflow cụ thể của từng platform

## 🔴 Red Phase - Write Failing Test

### Quy Tắc Red Phase

- **Viết test nhỏ nhất có thể** cho một behavior cụ thể
- **Test PHẢI fail** với lý do đúng (not implemented yet)
- **Tập trung vào 1 behavior** trong 1 test
- **Sử dụng naming convention rõ ràng**

### Test Naming Convention

```kotlin
// Android/Kotlin
@Test
fun `GIVEN validInput WHEN processData THEN returnsCorrectResult`()

// iOS/Swift
func testProcessData_ValidInput_ReturnsCorrectResult()

// JavaScript/TypeScript
test('GIVEN valid input WHEN processing data THEN returns correct result')
```

### Test Structure Template

```kotlin
@Test
fun `GIVEN [precondition] WHEN [action] THEN [expected result]`() {
    // GIVEN - Setup test data và mock
    val input = TestData.validInput()
    every { mockService.getData() } returns expectedData

    // WHEN - Execute the action
    val result = systemUnderTest.processData(input)

    // THEN - Verify the outcome
    assertEquals(expectedResult, result)
    verify { mockService.getData() }
}
```

## 🟢 Green Phase - Make Test Pass

### Quy Tắc Green Phase

- **Viết code tối thiểu** để test pass
- **Tránh over-engineering** ở giai đoạn này
- **Hard-code values nếu cần** - sẽ refactor sau
- **Chỉ focus vào behavior đang test**

### Minimal Implementation Examples

```kotlin
// BAD - Over-engineered trong Green phase
class UserService(
    private val repository: UserRepository,
    private val validator: UserValidator,
    private val logger: Logger,
    private val cache: Cache
) {
    fun getUser(id: String): User {
        if (!validator.isValidId(id)) throw IllegalArgumentException()
        val cached = cache.get(id)
        if (cached != null) {
            logger.info("Cache hit for $id")
            return cached
        }
        // ... complex logic
    }
}

// GOOD - Minimal trong Green phase
class UserService {
    fun getUser(id: String): User {
        return User(id, "John Doe") // Hard-coded to pass test
    }
}
```

## 🔵 Refactor Phase - Improve Code

### Quy Tắc Refactor Phase

- **Eliminate duplication** giữa test và production code
- **Improve naming** cho methods, variables, classes
- **Extract methods/classes** khi logic phức tạp
- **Add error handling** nếu cần thiết
- **Optimize performance** nếu có bottleneck

### Refactor Safety Rules

- **Run tests BEFORE refactor** để đảm bảo green
- **Run tests AFTER each change** trong quá trình refactor
- **Commit after successful refactor** để có safe point
- **Revert nếu tests fail** và thử approach khác

## 📊 Test Coverage Guidelines

### Coverage Targets

- **Core Business Logic**: 95-100% coverage
- **API Controllers**: 85-95% coverage
- **UI Components**: 70-85% coverage
- **Utilities**: 90-100% coverage
- **Data Models**: 80-90% coverage

### Coverage Quality Check

```bash
# Android
./gradlew testDebugUnitTestCoverage
# Kiểm tra build/reports/jacoco/

# iOS
xcodebuild test -scheme YourApp -enableCodeCoverage YES
# Kiểm tra DerivedData/Coverage/

# Flutter
flutter test --coverage
# Kiểm tra coverage/lcov.info

# Web/Node.js
npm run test:coverage
# Kiểm tra coverage/index.html
```

## 🧪 Test Types & Strategy

### Test Pyramid

```
     🔺 E2E Tests (5-10%)
    🔸🔸 Integration Tests (15-25%)
   🔹🔹🔹 Unit Tests (70-80%)
```

### 1. Unit Tests (Foundation)

**Mục đích:** Test individual components/functions isolated

```kotlin
// Android ViewModel Unit Test
@Test
fun `GIVEN empty input WHEN searching THEN shows empty state`() {
    // Test individual ViewModel behavior
    viewModel.search("")
    assertEquals(UiState.Empty, viewModel.uiState.value)
}
```

### 2. Integration Tests (Components Working Together)

**Mục đích:** Test multiple components interaction

```kotlin
// Android Repository Integration Test
@Test
fun `GIVEN network available WHEN fetching data THEN saves to local database`() {
    // Test Repository + API + Database integration
    val result = repository.fetchAndSaveData()
    assertTrue(result.isSuccess)
    assertNotNull(localDatabase.getData())
}
```

### 3. E2E Tests (Complete User Flows)

**Mục đích:** Test complete user journeys

```kotlin
// Android UI Test
@Test
fun `GIVEN user login WHEN navigating to profile THEN shows user data`() {
    // Test complete flow: Login -> Navigation -> Data Display
    onView(withId(R.id.loginButton)).perform(click())
    onView(withId(R.id.profileTab)).perform(click())
    onView(withText("John Doe")).check(matches(isDisplayed()))
}
```

## 🎭 Mocking Strategies

### Mock Types

- **Stub**: Returns predefined responses
- **Mock**: Verifies interactions
- **Spy**: Partial mocking of real objects
- **Fake**: Simplified working implementation

### Android Mocking with MockK

```kotlin
// Mock external dependencies
@Mock private lateinit var apiService: ApiService
@Mock private lateinit var database: UserDatabase

@Before
fun setup() {
    MockKAnnotations.init(this)

    // Stub behavior
    every { apiService.getUser(any()) } returns flowOf(userData)

    // Mock with verification
    every { database.save(any()) } just runs
}

@Test
fun `test saves user data`() {
    repository.saveUser(userData)

    // Verify interaction
    verify { database.save(userData) }
}
```

### iOS Mocking with Protocol

```swift
protocol UserServiceProtocol {
    func getUser(id: String) async -> User?
}

class MockUserService: UserServiceProtocol {
    var getUserResult: User?
    var getUserCallCount = 0

    func getUser(id: String) async -> User? {
        getUserCallCount += 1
        return getUserResult
    }
}
```

## 🚀 TDD Best Practices

### Do's ✅

- **Write descriptive test names** explaining behavior
- **Test one behavior per test** method
- **Use AAA pattern** (Arrange, Act, Assert)
- **Mock external dependencies**
- **Run tests frequently** (sau mỗi small change)
- **Commit after each green cycle**
- **Refactor both test và production code**

### Don'ts ❌

- **Don't test implementation details** - test behavior
- **Don't write multiple assertions** for different behaviors
- **Don't mock everything** - mock external dependencies only
- **Don't ignore failing tests** - fix immediately
- **Don't skip refactor phase** - tech debt accumulates
- **Don't test private methods** - test through public interface

## 🔧 TDD Tools Setup

### Android TDD Tools

```kotlin
// build.gradle
dependencies {
    // Unit Testing
    testImplementation 'junit:junit:4.13.2'
    testImplementation 'io.mockk:mockk:1.13.4'
    testImplementation 'org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4'
    testImplementation 'androidx.arch.core:core-testing:2.2.0'
    testImplementation 'app.cash.turbine:turbine:0.12.1' // Flow testing

    // UI Testing
    androidTestImplementation 'androidx.compose.ui:ui-test-junit4:$compose_version'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.5.1'
}
```

### iOS TDD Tools

```swift
// Package.swift
.testTarget(
    name: "YourAppTests",
    dependencies: [
        "YourApp",
        .product(name: "Quick", package: "Quick"),
        .product(name: "Nimble", package: "Nimble")
    ]
)
```

### Flutter TDD Tools

```yaml
# pubspec.yaml
dev_dependencies:
  flutter_test:
    sdk: flutter
  mockito: ^5.4.2
  build_runner: ^2.3.3
  bloc_test: ^9.1.0 # Bloc testing
  golden_toolkit: ^0.13.0 # Widget golden tests
```

## 📈 TDD Metrics & Monitoring

### Key Metrics to Track

- **Test Coverage %** (aim for >80% on core logic)
- **Test Execution Time** (keep fast for quick feedback)
- **Test Reliability** (no flaky tests)
- **Red-Green-Refactor Cycle Time** (aim for <10 minutes)

### Coverage Reports

```bash
# Generate coverage reports
npm run test:coverage        # Web/Node.js
flutter test --coverage     # Flutter
./gradlew testCoverage      # Android
xcodebuild test -enableCodeCoverage YES # iOS
```

## 🎯 TDD Integration với Development Workflow

### Daily TDD Workflow

1. **Start with failing test** cho feature mới
2. **Make it pass** với minimal code
3. **Refactor** để improve design
4. **Commit** sau mỗi complete cycle
5. **Push** khi feature hoàn thành

### Team TDD Practices

- **Pair programming** on complex logic
- **Code review** focus on test quality
- **Daily standup** mention TDD progress
- **Retrospective** discuss TDD improvements

### TDD trong CI/CD

```yaml
# GitHub Actions workflow
name: TDD Pipeline
on: [push, pull_request]

jobs:
  test:
    steps:
      - name: Run Unit Tests
        run: npm test
      - name: Check Coverage
        run: npm run test:coverage -- --threshold=80
      - name: Integration Tests
        run: npm run test:integration
      - name: E2E Tests
        run: npm run test:e2e
```

## 🚨 Common TDD Anti-Patterns

### ❌ Testing Implementation Details

```kotlin
// BAD - Testing private method
@Test
fun `test private calculation method`() {
    val result = calculator.invokePrivate("calculateInternal", 5, 3)
    assertEquals(8, result)
}

// GOOD - Testing public behavior
@Test
fun `GIVEN two numbers WHEN adding THEN returns sum`() {
    val result = calculator.add(5, 3)
    assertEquals(8, result)
}
```

### ❌ Too Many Mocks

```kotlin
// BAD - Mocking everything
@Mock private lateinit var service1: Service1
@Mock private lateinit var service2: Service2
@Mock private lateinit var service3: Service3
@Mock private lateinit var util1: Util1
@Mock private lateinit var util2: Util2

// GOOD - Only mock external dependencies
@Mock private lateinit var apiService: ApiService // External
// Use real objects for internal logic
```

### ❌ Slow Tests

```kotlin
// BAD - Testing with Thread.sleep
@Test
fun `test async operation`() {
    service.startAsyncOperation()
    Thread.sleep(5000) // ❌ Slow and unreliable
    assertTrue(service.isCompleted())
}

// GOOD - Using proper async testing
@Test
fun `test async operation`() = runTest {
    service.startAsyncOperation()
    advanceUntilIdle() // ✅ Fast and deterministic
    assertTrue(service.isCompleted())
}
```

Quy tắc này áp dụng cho TẤT CẢ loại dự án: Android, iOS, Flutter, Web, Backend! 🎯
