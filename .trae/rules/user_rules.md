# Global Rules for All Projects

## 🔥 HIGHEST PRIORITY RULES (Trọng số cao nhất)

### Communication Style
- Sử dụng tiếng Việt cho trò chuyện và giải thích với giọng điệu hài hước kiểu gen z
- Trả lời rõ ràng, đầy đủ nhưng không dài dòng
- Luôn hỏi làm rõ khi yêu cầu không rõ ràng
- Thông báo khi bạn không chắc chắn về cách giải quyết
- **🔴 BẮT BUỘC: Mỗi khi cần phản hồi của người dùng hãy gọi review gate chat, nếu không gọi được review gate chat thì kiểm tra nhiệm vụ trong file, nếu đã hoàn thành rồi thì cập nhật trạng thái và tiếp tục làm các công việc còn lại**

### Icon Resource
- **🔴 BẮT BUỘC: Khi cần icon hãy tìm kiếm thông qua mcp lucide-icons, tùy theo dự án mà sử dụng các loại icon khác nhau**

### Planning
- **🔴 BẮT BUỘC: Luôn tạo kế hoạch cho các nhiệm vụ lớn vào file .md sau đó thực hiện theo kế hoạch và cập nhật conversion plan sau khi thực hiện xong**

### Language
- **🔴 BẮT BUỘC: Luôn kiểm tra và thêm các chuỗi dịch hoặc resource khi tạo**
- **🔴 BẮT BUỘC: Luôn sử dụng tiếng Anh làm ngôn ngữ chính**

### Core Working Principles
- **🔴 BẮT BUỘC: Luôn phân tích kỹ trước khi chỉnh sửa code**
- **🔴 BẮT BUỘC: Tập trung vào vấn đề chính, không lạc đề**
- **🔴 BẮT BUỘC: Xác định rõ nguyên nhân gốc rễ (root cause) trước khi sửa lỗi**
- **🔴 BẮT BUỘC: Chỉ thực hiện một thay đổi lớn mỗi lần và kiểm tra kỹ trước khi tiếp tục**

### Problem Solving
- **🔴 BẮT BUỘC: Ngừng ngay khi gặp vấn đề cần giải quyết**
- **🔴 BẮT BUỘC: Không nhảy vội vào việc sửa code khi gặp lỗi**
- **🔴 BẮT BUỘC: Luôn đưa ra 2-3 phương án khi giải quyết vấn đề phức tạp**
- **🔴 BẮT BUỘC: Dừng và xin hướng dẫn sau 3 lần thử không thành công**

### Quality Standards
- **🔴 BẮT BUỘC: Sử dụng tiếng Anh cho tất cả code và tài liệu kỹ thuật**
- **🔴 BẮT BUỘC: Viết code tự giải thích với tên biến/hàm rõ ràng**
- **🔴 BẮT BUỘC: Tuân thủ các nguyên tắc SOLID**
- **🔴 BẮT BUỘC: Implement xử lý lỗi một cách đúng đắn**

### Documentation
- **🔴 BẮT BUỘC: Ghi lại mọi quyết định quan trọng vào Decisions.md**
- **🔴 BẮT BUỘC: Cập nhật Codebase.md mỗi khi hoàn thiện một phần**
- **🔴 BẮT BUỘC: Đánh dấu các task đã hoàn thành trong Instruction.md**
- **🔴 BẮT BUỘC: Kết thúc mỗi nhiệm vụ với mô tả ngắn gọn về công việc đã làm**

### Safety Measures
- **🔴 BẮT BUỘC: Không tự ý tối ưu code khi không được yêu cầu**
- **🔴 BẮT BUỘC: Không xóa code không liên quan khi không được yêu cầu**
- **🔴 BẮT BUỘC: Cẩn thận khi xóa file hoặc chỉnh sửa file ngoài nhiệm vụ chính**
- **🔴 BẮT BUỘC: Tạo backup đơn giản trước những thay đổi lớn**

---

### Core Development Rules

- **[Project Identity Enforcement](../../.cursor/rules/project-identity-enforcement.mdc)** - 🔴 BẮT BUỘC kiểm tra .project-identity trước mọi task
- **[File Organization Rules](../../.cursor/rules/file-organization-rules.mdc)** - 🔴 BẮT BUỘC tổ chức file .md trong docs/project/
- **[Base Rules](../../.cursor/rules/base-rules.mdc)** - Quy tắc cơ bản cho tất cả projects
- **[Development Rules](../../.cursor/rules/development-rules.mdc)** - Quy tắc phát triển chung
- **[Development Control Rules](../../.cursor/rules/development-control-rules.mdc)** - Kiểm soát quy trình phát triển
- **[File Protection Rules](../../.cursor/rules/file-protection-rules.mdc)** - Bảo vệ và backup files

## Language

- Luôn kiểm tra và thêm các chuỗi dịch hoặc resource khi tạo

## Core Working Principles

- **🔴 BẮT BUỘC: Luôn kiểm tra .project-identity trước khi bắt đầu bất kỳ nhiệm vụ nào**
- **Luôn suy luận yêu cầu của người dùng trước khi thực hiện**
- Phân tích ý định thực sự đằng sau yêu cầu
- Luôn kiểm tra bộ nhớ bằng Context7 để tìm thông tin liên quan
- Luôn phân tích kỹ trước khi chỉnh sửa code
- Tập trung vào vấn đề chính, không lạc đề
- Xác định rõ nguyên nhân gốc rễ (root cause) trước khi sửa lỗi
- Chỉ thực hiện một thay đổi lớn mỗi lần và kiểm tra kỹ trước khi tiếp tục



#### Mandatory Project Identity Check Protocol

**🚨 CRITICAL: Thực hiện checklist này trước khi bắt đầu bất kỳ công việc nào:**

1. **Đọc và phân tích .project-identity**:
   - `projectType`: Xác định loại dự án (android, ios, web, flutter, etc.)
   - `projectStage`: Hiểu giai đoạn hiện tại (setup, brainstorm, development, production)
   - `mainLanguages`: Ngôn ngữ lập trình chính
   - `mainFrameworks`: Framework và công nghệ chính
   - `keyFeatures`: Tính năng quan trọng của dự án

2. **Load Workflow Rules phù hợp**:
   - Áp dụng `coreRules.always_applied` cho mọi task
   - Load `platformSpecificRules` dựa trên projectType
   - Tuân thủ workflow rules của stage hiện tại từ `projectLifecycle`

3. **Kiểm tra Context và Memory**:
   - Sử dụng Context7 để tìm thông tin liên quan đến project
   - Áp dụng kinh nghiệm từ các dự án tương tự
   - Kiểm tra integrations và features được bật

4. **Validation và Enforcement**:
   - Đảm bảo không skip stages trong `stageProgression`
   - Kiểm tra `newProjectDetection` triggers nếu là dự án mới
   - Cập nhật `projectStage` nếu cần thiết

**❌ NGHIÊM CẤM bắt đầu công việc mà không thực hiện checklist trên**

**✅ Chỉ tiếp tục sau khi đã:**
- Hiểu rõ project context và requirements
- Load đúng workflow rules
- Xác định approach phù hợp với project type và stage

## User Intent Analysis

- **Phân tích ngữ cảnh**: Hiểu rõ bối cảnh và mục tiêu của người dùng
- **Xác định mức độ ưu tiên**: Phân biệt yêu cầu cấp thiết và không cấp thiết
- **Đề xuất giải pháp tối ưu**: Không chỉ làm theo yêu cầu mà còn đề xuất cách tốt hơn nếu có
- **Xác nhận hiểu đúng**: Hỏi lại khi không chắc chắn về ý định của người dùng

## Problem Solving

- Ngừng ngay khi gặp vấn đề cần giải quyết
- Không nhảy vội vào việc sửa code khi gặp lỗi
- Luôn đưa ra 2-3 phương án khi giải quyết vấn đề phức tạp
- Dừng và xin hướng dẫn sau 3 lần thử không thành công

## Quality Standards

- Sử dụng tiếng Anh cho tất cả code và tài liệu kỹ thuật
- Viết code tự giải thích với tên biến/hàm rõ ràng
- Tuân thủ các nguyên tắc SOLID
- Implement xử lý lỗi một cách đúng đắn

## Documentation

### File Organization Rules

- **🔴 BẮT BUỘC: Tất cả file .md phải được đặt trong thư mục `docs/project/`**
- **NGHIÊM CẤM** tạo file .md ở root directory
- Sử dụng cấu trúc thư mục chuẩn:
  - `docs/project/` - Tất cả file documentation (.md)
  - `docs/templates/` - Template files
  - `instructions/` - Workflow và API docs
  - `.cursor/rules/` - Development rules (nguồn chính thức)
  - `.trae/rules/` - Alias/link đến .cursor/rules

### Documentation Standards

- Ghi lại mọi quyết định quan trọng vào docs/project/Decisions.md
- Cập nhật docs/project/Codebase.md mỗi khi hoàn thiện một phần
- Đánh dấu các task đã hoàn thành trong docs/project/Instruction.md
- Kết thúc mỗi nhiệm vụ với mô tả ngắn gọn về công việc đã làm

## Safety Measures

- Không tự ý tối ưu code khi không được yêu cầu
- Không xóa code không liên quan khi không được yêu cầu
- Cẩn thận khi xóa file hoặc chỉnh sửa file ngoài nhiệm vụ chính
- Tạo backup đơn giản trước những thay đổi lớn

## Context & Memory Management

- **Luôn kiểm tra Context7** trước khi bắt đầu công việc
- Tìm kiếm thông tin liên quan trong bộ nhớ dự án
- Sử dụng kinh nghiệm từ các dự án tương tự
- Cập nhật bộ nhớ với thông tin mới sau khi hoàn thành task

## Workflow Optimization

- Ưu tiên hiệu quả và tốc độ thực hiện
- Tránh lặp lại công việc đã làm
- Sử dụng templates và patterns có sẵn
- Tự động hóa các tác vụ lặp đi lặp lại

## Error Prevention

- Kiểm tra kỹ trước khi thực hiện thay đổi lớn
- Validate input và output
- Test các thay đổi trước khi commit
- Có kế hoạch rollback khi cần thiết

---

# Android Development Workflow

> **🔗 MANDATORY RULES SYNCHRONIZATION**  
> **BẮT BUỘC** sử dụng các rules từ `.cursor/rules/` làm nguồn chính thức cho tất cả Android workflows.  
> Phần này chỉ là alias/link đến các rules chính thức trong `.cursor/rules/`

## 🎯 Android Primary Rules Sources (MANDATORY)

### Core Android Development Rules

- **[Android Workflow](../../.cursor/rules/android-workflow.mdc)** - Quy trình phát triển Android chính
- **[Android Code Deduplication](../../.cursor/rules/android-code-deduplication.mdc)** - Tránh trùng lặp code Android
- **[TDD Mobile Workflow](../../.cursor/rules/tdd-mobile-workflow.mdc)** - Test-driven development cho mobile
- **[Android Project Template](../../.cursor/rules/android-project-template.mdc)** - Template dự án Android

## 🚀 Android Development Workflow Summary

### Blueprint-First Development

- **BẮT BUỘC** tạo blueprint trước khi viết code cho mỗi tính năng
- **BẮT BUỘC** kiểm tra module registry để tránh trùng lặp
- **BẮT BUỘC** cập nhật module registry sau khi hoàn thành tính năng
- **BẮT BUỘC** tuân thủ cấu trúc package tiêu chuẩn
- **BẮT BUỘC** sử dụng các base classes đã có
- **NGHIÊM CẤM** tạo code trùng lặp chức năng đã có

### Standard Package Structure

```
com.base.app/
├── base/                 # Base classes
│   ├── activity/         # Base Activities
│   ├── fragment/         # Base Fragments
│   ├── viewmodel/        # Base ViewModels
│   ├── adapter/          # Base Adapters
│   └── view/             # Base Custom Views
├── core/                 # Core modules
│   ├── di/               # Dependency Injection
│   ├── network/          # Network components
│   ├── storage/          # Local storage
│   ├── analytics/        # Analytics tracking
│   └── utils/            # Utility classes
├── data/                 # Data layer
│   ├── repository/       # Repositories implementation
│   ├── datasource/       # Data sources (remote, local)
│   ├── model/            # Data models (entities, DTOs)
│   └── mapper/           # Mappers
├── domain/               # Domain layer
│   ├── usecase/          # Use cases (business logic)
│   ├── model/            # Domain models
│   └── repository/       # Repository interfaces
└── ui/                   # UI layer
    ├── components/       # Shared UI components
    └── features/         # Feature packages
        ├── feature1/     # Tính năng 1
        ├── feature2/     # Tính năng 2
        └── ...
```

### Architecture Standards

- **BẮT BUỘC** sử dụng **MVVM + Clean Architecture** với 3 lớp:
  - **UI Layer**: Composables + ViewModels
  - **Domain Layer**: Use Cases + Models + Repository Interfaces
  - **Data Layer**: Repository Implementations + Data Sources
- **BẮT BUỘC** đảm bảo **Unidirectional Data Flow**: UI → ViewModel → UseCase → Repository

### Tech Stack Chuẩn

```kotlin
// UI Framework
implementation "androidx.compose.ui:compose-ui:$compose_version"
implementation "androidx.compose.material3:material3:$material3_version"

// Dependency Injection
implementation "com.google.dagger:hilt-android:$hilt_version"

// Async Programming
implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutines_version"

// Networking
implementation "com.squareup.retrofit2:retrofit:$retrofit_version"
implementation "com.squareup.okhttp3:okhttp:$okhttp_version"

// Database
implementation "androidx.room:room-runtime:$room_version"
implementation "androidx.room:room-ktx:$room_version"

// Image Loading
implementation "io.coil-kt:coil-compose:$coil_version"
```

### Code Quality & Anti-Duplication Rules

- **BẮT BUỘC** trước khi tạo class/function/resource mới:
  1. Tìm kiếm trong project với pattern: `class/interface/fun [Tên]`
  2. Kiểm tra module-registry.md
  3. Kiểm tra component-catalog.md
  4. Tìm trong package `core/utils` trước khi tạo utility functions
  5. Tìm trong package `ui/components` trước khi tạo UI components mới
  6. Ưu tiên mở rộng code hiện có thay vì tạo mới

- **BẮT BUỘC** khi gặp logic tương tự ở 2+ nơi:
  1. Trích xuất vào base class/util class
  2. Tạo extension function cho code dùng chung
  3. Cập nhật tất cả occurrences để sử dụng code dùng chung

### TDD Mobile Workflow

- **BẮT BUỘC** viết tests trước khi viết implementation code
- **BẮT BUỘC** follow Red-Green-Refactor cycle nghiêm ngặt
- **BẮT BUỘC** 85% unit test coverage minimum
- **BẮT BUỘC** 70% integration test coverage minimum
- **BẮT BUỘC** 100% critical user flow UI test coverage
- **NGHIÊM CẤM** implementation code trước khi có failing tests

### Testing Stack

```kotlin
// Testing Dependencies
dependencies {
    // Unit Testing
    testImplementation 'junit:junit:5.9.2'
    testImplementation 'org.mockito:mockito-core:5.1.1'
    testImplementation 'io.mockk:mockk:1.13.4'
    testImplementation 'org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4'
    
    // Android Testing
    androidTestImplementation 'androidx.test.ext:junit:1.1.5'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.5.1'
    androidTestImplementation 'androidx.compose.ui:ui-test-junit4:1.4.0'
    
    // Integration Testing
    androidTestImplementation 'androidx.room:room-testing:2.5.0'
    androidTestImplementation 'okhttp3:mockwebserver:4.10.0'
}
```

### Performance & Optimization

- **BẮT BUỘC** sử dụng **Vector Drawables** cho icons
- **BẮT BUỘC** sử dụng **WebP** format cho images
- **BẮT BUỘC** enable R8/ProGuard để minify code
- **BẮT BUỘC** sử dụng Android App Bundle
- **BẮT BUỘC** ABI filtering - chỉ hỗ trợ ARM (armeabi-v7a, arm64-v8a)

### Quality Checklist

- [ ] MVVM + Clean Architecture được implement đúng
- [ ] Unidirectional Data Flow được đảm bảo
- [ ] R8/ProGuard configuration hoạt động
- [ ] Vector Drawables được sử dụng cho icons
- [ ] WebP format được sử dụng cho images
- [ ] Unit tests cover ViewModels và UseCases
- [ ] UI tests cover critical user flows
- [ ] Error handling được implement đầy đủ
- [ ] Performance monitoring được setup
- [ ] APK size được tối ưu
- [ ] Code tuân thủ cấu trúc package định nghĩa
- [ ] Classes được đặt đúng package
- [ ] Không có code trùng lặp chức năng
- [ ] Sử dụng các base classes khi thích hợp
- [ ] UI components được tái sử dụng khi có thể
- [ ] Blueprint được hoàn thiện trước khi viết code
- [ ] Module Registry được cập nhật sau khi hoàn thành

## ⚠️ CRITICAL ENFORCEMENT RULES

### Mandatory Compliance

1. **BẮT BUỘC** tuân thủ 100% các rules trong `.cursor/rules/android-workflow.mdc`
2. **BẮT BUỘC** tuân thủ 100% các rules trong `.cursor/rules/android-code-deduplication.mdc`
3. **BẮT BUỘC** tuân thủ 100% các rules trong `.cursor/rules/tdd-mobile-workflow.mdc`
4. **NGHIÊM CẤM** tạo rules mới trong `.trae/rules/` mà không sync với `.cursor/rules/`
5. **BẮT BUỘC** cập nhật alias links khi có thay đổi trong `.cursor/rules/`

### Synchronization Protocol

- Mọi thay đổi Android workflow rules phải được thực hiện trong `.cursor/rules/` trước
- File này chỉ được cập nhật để sync alias links
- Không được override hoặc modify nội dung rules gốc

## 🔄 Android Rules Hierarchy Priority

1. `.cursor/rules/android-workflow.mdc` - **PRIMARY SOURCE** (Highest Priority)
2. `.cursor/rules/android-code-deduplication.mdc` - **PRIMARY SOURCE** (Highest Priority)
3. `.cursor/rules/tdd-mobile-workflow.mdc` - **PRIMARY SOURCE** (Highest Priority)
4. `.trae/rules/user_rules.md` - Alias/Link layer only (Lowest Priority)

---

**📝 Lưu ý**: Để xem chi tiết đầy đủ các quy trình Android, vui lòng tham khảo trực tiếp các file trong `.cursor/rules/`. Phần này chỉ là tóm tắt và alias links.
