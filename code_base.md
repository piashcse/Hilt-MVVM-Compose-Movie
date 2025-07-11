# Android Compose Base - Codebase Documentation

## 1. Project Overview

This project serves as a comprehensive base for building modern Android applications using Jetpack Compose. It is structured following the principles of Clean Architecture, ensuring a scalable, maintainable, and testable codebase.

The project is pre-configured with essential libraries for modern Android development, including dependency injection with Hilt, networking with Retrofit, data persistence with Room, and image loading with Coil.

## 2. Architecture

The codebase is organized into three main layers:

-   **Data Layer**: Responsible for providing data to the application. It includes data sources (remote and local), repositories, and data transfer objects (DTOs).
-   **Domain Layer**: Contains the core business logic of the application. It consists of use cases, domain models, and repository interfaces.
-   **UI Layer**: Responsible for displaying the application's user interface. It includes Composable screens, ViewModels, and UI-related components.

### Key Directories:

-   `app/src/main/java/com/xiaomi/base/data`: Contains the data layer components.
-   `app/src/main/java/com/xiaomi/base/domain`: Contains the domain layer components.
-   `app/src/main/java/com/xiaomi/base/ui`: Contains the UI layer components.
-   `app/src/main/java/com/xiaomi/base/di`: Handles dependency injection setup using Hilt.
-   `app/src/main/java/com/xiaomi/base/navigation`: Manages navigation within the app.

## 3. Core Features & Libraries

-   **UI**: Jetpack Compose for building the UI.
-   **Dependency Injection**: Hilt for managing dependencies.
-   **Networking**: Retrofit and Gson for network requests.
-   **Database**: Room for local data storage.
-   **Asynchronous Programming**: Kotlin Coroutines.
-   **Image Loading**: Coil (via Landscapist).
-   **Navigation**: Jetpack Navigation for Compose.
-   **Logging**: Timber.

## 4. Project Structure Highlights

-   **`components/`**: A rich library of reusable UI components, categorized for easy access (e.g., `ai`, `animation`, `interaction`).
-   **`preview/`**: A robust system for previewing and cataloging Jetpack Compose components, making development and testing of UI elements more efficient.
-   **`templates/`**: An innovative "Lego Component System" that allows for the generation of components from natural language descriptions, significantly speeding up UI development.
-   **`utils/`**: A collection of utility classes for common tasks.

## 5. Getting Started

1.  Clone the repository.
2.  Open the project in Android Studio.
3.  Build and run the `app` module.
4.  Explore the `LegoComponentDemo` in the `templates/demo` package to see the template system in action.
5.  Explore the `PreviewCatalogActivity` in the `preview/catalog` package to browse the available UI components.

## 6. Build Configuration

-   The project uses Gradle with Kotlin DSL (`build.gradle.kts`).
-   Dependencies are managed in `gradle/libs.versions.toml`.
-   Product flavors (e.g., `develop`) are configured in `app/build.gradle.kts` to manage different build variants, such as API keys.