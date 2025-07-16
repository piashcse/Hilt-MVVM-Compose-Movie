# Movie World (Jetpack Compose) 
[![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-1.8.3-%230075FF.svg)](https://developer.android.com/jetpack/compose)
![badge-Android](https://img.shields.io/badge/Platform-Android-brightgreen)
[![API](https://img.shields.io/badge/API-23%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=23)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.2.0-blue.svg?style=flat&logo=kotlin)](https://kotlinlang.org)
[![GitHub license](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](https://www.apache.org/licenses/LICENSE-2.0)
<a href="https://github.com/piashcse"><img alt="License" src="https://img.shields.io/static/v1?label=GitHub&message=piashcse&color=C51162"/></a>

Movie World app built with Jetpack Compose, Hilt, Coroutines, Flow, Room and ViewModel based on MVVM architecture. The app follows the architecture to ensure clean, maintainable code and delivers a responsive, modern UI experience, leveraging [The Movie DB API](https://www.themoviedb.org). </br>


<p align="center">
  <img width="30%" height="50%" src="https://github.com/piashcse/Hilt-MVVM-Compose-Movie/blob/master/screenshots/1730809622225_100.PNG" />
 <img width="30%" height="50%" src="https://github.com/piashcse/Hilt-MVVM-Compose-Movie/blob/master/screenshots/1730809653767_100.PNG" />
  <img width="30%" height="50%" src="https://github.com/piashcse/Hilt-MVVM-Compose-Movie/blob/master/screenshots/1732207539374_100.PNG" />
  <img width="30%" height="50%" src="https://github.com/piashcse/Hilt-MVVM-Compose-Movie/blob/master/screenshots/1730809637511_100.PNG" />
  <img width="30%" height="50%" src="https://github.com/piashcse/Hilt-MVVM-Compose-Movie/blob/master/screenshots/1741789387634_100.PNG" />
  <img width="30%" height="50%" src="https://github.com/piashcse/Hilt-MVVM-Compose-Movie/blob/master/screenshots/1732207549776_100.PNG" />
</p>

<p align="center" width="100%">
   <img width="35%" height="50%" src="https://github.com/piashcse/Hilt-MVVM-Compose-Movie/blob/master/screenshots/movie_world.gif" />
 </p>

# Main Features
- Movie
  - Movie List  
  - Movie Search
  - Movie Detail
  - Recommended Movie
  - Favorite Movie in room DB
- TV Series
  - TV Series List
  - TV Series Search
  - TV Series Detail
  - Recommended TV Series
  - Favorite TV Series in room DB
- Celebrities
  - Popular Celebrities
  - Trending Celebrities
  - Celebrity Search
- Artist detail
- Filter with genre 
- Pagination with paging3
- Bottom navigation
- Network connection state with SnackBar


## Architecture 🏗️
  - MVVM Architecture (Model - ComposableView - ViewModel)
  - Repository pattern

<p align="center">
  <img width="72%" height="722%" src="https://github.com/piashcse/Hilt-MVVM-Compose-Movie/blob/master/screenshots/mvvm.png" />
</p>
<p align="center">
<b>Fig.  MVVM (Model - ComposableView - ViewModel) design pattern.</b>
</p>

## API Key 🔑
You will need to provide a developer key to fetch the data from TMDB API.
* Generate a new key (v3 auth) from [here](https://www.themoviedb.org/settings/api). Copy the key and go back to the project.
* Add the key to build config in `./app/build.gradle`:

```kotlin
defaultConfig {
    ...
    buildConfigField("String", "API_KEY", '"TMDB_API_KEY"')
    ...
}
```

## Built With 🛠
- [Kotlin](https://kotlinlang.org/) - First class and official programming language for Android development.
- [Jetpack Compose](https://developer.android.com/jetpack/compose) - Jetpack Compose is Android’s modern toolkit for building native UI.
- [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - For asynchronous and more..
- [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/) - A cold asynchronous data stream that sequentially emits values and completes normally or with an exception.
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture) - Collection of libraries that help you design robust, testable, and maintainable apps.
  - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - Data objects that notify views when the underlying database changes.
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores UI-related data that isn't destroyed on UI changes.
  - [Paging3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview) - The Paging library helps you load and display pages of data from a larger dataset from local storage or over network
- [Dependency Injection](https://developer.android.com/training/dependency-injection)
  - [Hilt](https://dagger.dev/hilt) - Easier way to incorporate Dagger DI into Android apps.
- [Room](https://developer.android.com/training/data-storage/room) - The Room database provides an abstraction layer over SQLite to allow fluent database access while harnessing the full power of SQLite
- [Retrofit](https://square.github.io/retrofit/) - A type-safe HTTP client for Android and Java.
- [Material Components for Android](https://github.com/material-components/material-components-android) - Modular and customizable Material Design UI components for Android.
- [Timber](https://github.com/JakeWharton/timber) - A logger with a small, extensible API which provides utility on top of Android's normal Log class.

## 👨 Developed By

<a href="https://twitter.com/piashcse" target="_blank">
  <img src="https://avatars.githubusercontent.com/piashcse" width="80" align="left">
</a>

**Mehedi Hassan Piash**

[![Twitter](https://img.shields.io/badge/-Twitter-1DA1F2?logo=x&logoColor=white&style=for-the-badge)](https://twitter.com/piashcse)
[![Medium](https://img.shields.io/badge/-Medium-00AB6C?logo=medium&logoColor=white&style=for-the-badge)](https://medium.com/@piashcse)
[![Linkedin](https://img.shields.io/badge/-LinkedIn-0077B5?logo=linkedin&logoColor=white&style=for-the-badge)](https://www.linkedin.com/in/piashcse/)
[![Web](https://img.shields.io/badge/-Web-0073E6?logo=appveyor&logoColor=white&style=for-the-badge)](https://piashcse.github.io/)
[![Blog](https://img.shields.io/badge/-Blog-0077B5?logo=readme&logoColor=white&style=for-the-badge)](https://piashcse.blogspot.com)

# License
```
Copyright 2024 piashcse (Mehedi Hassan Piash)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

