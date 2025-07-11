# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

**Android-Compose-Base** is a comprehensive Android template project using Jetpack Compose with a Xiaomi-branded Material3 UI Kit. The project follows Clean Architecture with MVVM pattern and uses Hilt for dependency injection.

**Current State**: Recently recovered from major compilation crisis (85% error reduction). ~20 minor Material3 API compatibility issues remain.

## 🚀 Essential Workflows (MANDATORY SEQUENCE)

### 1. Project Analysis Phase

```bash
# ALWAYS start by checking project identity
cat .project-identity
cat .project-personality
```

### 2. Brainstorm Phase (MANDATORY for new projects)

- **NEVER skip brainstorming** for new projects
- Use: "Tôi muốn brainstorm ý tưởng [your app idea]"
- Creates structured plan and updates .project-identity

### 3. 4-Role Development Process

- **Planner**: Analyzes requirements and creates plan
- **Architect**: Designs technical architecture
- **Builder**: Implements the solution
- **Tester**: Validates and tests implementation

### Communication Style

- Sử dụng tiếng Việt cho trò chuyện và giải thích với giọng điệu hài hước kiểu giới trẻ gen-z
- Trả lời rõ ràng, đầy đủ nhưng không dài dòng
- Luôn hỏi làm rõ khi yêu cầu không rõ ràng
- Thông báo khi bạn không chắc chắn về cách giải quyết

### Core Working Principles

- **Luôn suy luận yêu cầu của người dùng trước khi thực hiện**
- Phân tích ý định thực sự đằng sau yêu cầu
- Luôn phân tích kỹ trước khi chỉnh sửa code
- Tập trung vào vấn đề chính, không lạc đề
- Xác định rõ nguyên nhân gốc rễ (root cause) trước khi sửa lỗi
- Chỉ thực hiện một thay đổi lớn mỗi lần và kiểm tra kỹ trước khi tiếp tục

### User Intent Analysis

- **Phân tích ngữ cảnh**: Hiểu rõ bối cảnh và mục tiêu của người dùng
- **Xác định mức độ ưu tiên**: Phân biệt yêu cầu cấp thiết và không cấp thiết
- **Đề xuất giải pháp tối ưu**: Không chỉ làm theo yêu cầu mà còn đề xuất cách tốt hơn nếu có
- **Xác nhận hiểu đúng**: Hỏi lại khi không chắc chắn về ý định của người dùng

### Problem Solving

- Ngừng ngay khi gặp vấn đề cần giải quyết
- Không nhảy vội vào việc sửa code khi gặp lỗi
- Luôn đưa ra 2-3 phương án khi giải quyết vấn đề phức tạp
- Dừng và xin hướng dẫn sau 3 lần thử không thành công

### Quality Standards

- Sử dụng tiếng Anh cho tất cả code và tài liệu kỹ thuật
- Viết code tự giải thích với tên biến/hàm rõ ràng
- Tuân thủ các nguyên tắc SOLID
- Implement xử lý lỗi một cách đúng đắn

### Documentation

- Ghi lại mọi quyết định quan trọng vào Decisions.md
- Cập nhật Codebase.md mỗi khi hoàn thiện một phần
- Đánh dấu các task đã hoàn thành trong Instruction.md
- Kết thúc mỗi nhiệm vụ với mô tả ngắn gọn về công việc đã làm

### Safety Measures

- Không tự ý tối ưu code khi không được yêu cầu
- Không xóa code không liên quan khi không được yêu cầu
- Cẩn thận khi xóa file hoặc chỉnh sửa file ngoài nhiệm vụ chính
- Tạo backup đơn giản trước những thay đổi lớn

### Context & Memory Management

- Tìm kiếm thông tin liên quan trong bộ nhớ dự án
- Sử dụng kinh nghiệm từ các dự án tương tự
- Cập nhật bộ nhớ với thông tin mới sau khi hoàn thành task

### Workflow Optimization

- Ưu tiên hiệu quả và tốc độ thực hiện
- Tránh lặp lại công việc đã làm
- Sử dụng templates và patterns có sẵn
- Tự động hóa các tác vụ lặp đi lặp lại

### Error Prevention

- Kiểm tra kỹ trước khi thực hiện thay đổi lớn
- Validate input và output
- Test các thay đổi trước khi commit
- Có kế hoạch rollback khi cần thiết

## 🎯 Primary Rules Sources (MANDATORY)

### Core Development Rules

@.cursor/rules/base-rules.mdc
@.cursor/rules/development-rules.mdc
@.cursor/rules/development-control-rules.mdc
@.cursor/rules/file-protection-rules.mdc

### Mobile Development Workflows

@.cursor/rules/mobile-utility-workflow.mdc
@.cursor/rules/android-workflow.mdc
@.cursor/rules/ios-workflow.mdc
@.cursor/rules/tdd-mobile-workflow.mdc

### Project Management

@.cursor/rules/planning-workflow.mdc
@.cursor/rules/planning-enforcement.mdc
@.cursor/rules/planning-validation-rules.mdc
@.cursor/rules/brainstorm-overview.mdc
@.cursor/rules/brainstorm-detailed-workflow.mdc
@.cursor/rules/expert-brainstorm-workflow.mdc
@.cursor/rules/brainstorm-expert-integration.mdc
@.cursor/rules/expert-brainstorm-guide.mdc

### Code Quality & Architecture

@.cursor/rules/android-code-deduplication.mdc
@.cursor/rules/universal-code-deduplication.mdc
@.cursor/rules/android-project-template.mdc
@.cursor/rules/ios-project-template.mdc

### Integration & API

@.cursor/rules/api-integration-rules.mdc
@.cursor/rules/backend-rules-optimized.mdc
@.cursor/rules/frontend-rules.mdc

### Specialized Workflows

@.cursor/rules/git-workflow.mdc
@.cursor/rules/i18n-rules.mdc
@.cursor/rules/resource-management.mdc
@.cursor/rules/terminal-rules.mdc
@.cursor/rules/design-to-prompt.mdc

### Project Setup & Identity

@.cursor/rules/project-creation-workflow.mdc
@.cursor/rules/project-identity-template.mdc
@.cursor/rules/project-identification-rules.mdc
@.cursor/rules/tech-stack-selection.mdc

### Advanced Features

@.cursor/rules/memory-bank-workflow.mdc
@.cursor/rules/experience-system-workflow.mdc
@.cursor/rules/context7-auto-workflow.mdc
@.cursor/rules/ReviewGateV2.mdc
@.cursor/rules/four-role-development.mdc

## ⚠️ CRITICAL ENFORCEMENT RULES

### Mandatory Compliance

1. **BẮT BUỘC** tuân thủ 100% các rules trong `.cursor/rules/`
2. **NGHIÊM CẤM** tạo rules mới mà không sync với `.cursor/rules/`
3. **BẮT BUỘC** cập nhật memory khi có thay đổi trong `.cursor/rules/`
4. **BẮT BUỘC** sử dụng relative paths để đảm bảo tính di động

### Project Structure Understanding

- **Template Framework**: Đây là framework template, không phải ứng dụng cụ thể
- **Workflow-First**: Luôn áp dụng workflow phù hợp từ `.cursor/rules/`
- **Platform Agnostic**: Hỗ trợ Android, iOS, Flutter, Web development
- **AI-Powered**: Tích hợp sâu với AI tools và MCP protocols

### Documentation Rules

- **ALL .md files** MUST be placed in `docs/project/`
- Update `docs/project/Codebase.md` with implementation summaries
- Mark completed tasks in `docs/project/Instruction.md`
- Document decisions in `docs/project/Decisions.md`

### Backup Protocol

- **NEVER delete files directly** - move to `_backups/`
- Maintain directory structure in backups
- Create timestamped backup folders: `_backups/YYYY-MM-DD/`

## Essential Commands

### Build Commands

```bash
# Build entire project
./gradlew build

# Build debug APK (develop flavor)
./gradlew assembleDevelopDebug

# Clean build
./gradlew clean build

# Build with detailed output for debugging
./gradlew build --stacktrace --info
```

### Test Commands

```bash
# Run unit tests
./gradlew test

# Run instrumented tests (requires device/emulator)
./gradlew connectedAndroidTest

# Test specific flavor
./gradlew testDevelopDebugUnitTest
```

### Lint Commands

```bash
# Run lint checks
./gradlew lint

# Lint specific flavor
./gradlew lintDevelopDebug
```

### Development Commands

```bash
# Install and run on device/emulator
./gradlew installDevelopDebug

# Pre-commit validation
./gradlew clean build test lint
```

## Architecture Overview

### Project Structure

```
com.xiaomi.base/
├── data/           # Data layer: repositories, data sources, DTOs
├── domain/         # Domain layer: use cases, domain models, repository interfaces
├── ui/             # Presentation layer
│   ├── kit/        # Xiaomi UI Kit (Material3-based component library)
│   ├── screens/    # Feature screens with ViewModels
│   └── theme/      # Application theming
├── di/             # Hilt dependency injection modules
└── navigation/     # Navigation component setup
```

### UI Kit System

The project features a comprehensive UI Kit at `ui/kit/` organized by Material Design categories:

- **foundation/**: Core design tokens (colors, typography, spacing, shapes)
- **components/**: Reusable UI components categorized by function
  - actions/ (Buttons, FABs, Chips)
  - communication/ (Badges, Progress, Snackbars)
  - containment/ (Cards, Lists, Dividers)
  - navigation/ (AppBars, BottomNav, Tabs)
  - selection/ (Checkboxes, Switches, Sliders)
  - specialized/ (Xiaomi-specific: AI, Biometric, Security)
- **templates/**: Pre-built screen templates
- **utils/**: Extension functions and utilities

### Key Architectural Patterns

1. **Clean Architecture Layers**:

   - Domain layer contains business logic and is framework-independent
   - Data layer implements repository interfaces from domain
   - UI layer uses ViewModels with StateFlow for state management

2. **Dependency Injection**:

   - Hilt modules in `di/` package
   - ViewModels use `@HiltViewModel` annotation
   - Repositories injected via constructor injection

3. **Navigation**:
   - Uses Navigation Compose
   - Routes defined as sealed classes
   - Navigation handled in `navigation/` package

## Development Guidelines

### Material3 Migration

The project is migrating to Material3. When working with components:

- Use `MaterialTheme.spacing.*` instead of hardcoded dp values
- Import from `androidx.compose.material3.*` not `material.*`
- Check component signatures - many have changed (e.g., Snackbar `message` → `content`)

### Common Patterns

```kotlin
// Spacing
MaterialTheme.spacing.Large // instead of 24.dp

// Component scopes
Tab(...) {
    // This is now TabIndicatorScope
}

// Tooltips
BasicTooltipBox(
    positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(),
    tooltip = { ... }
) { ... }
```

### Known Issues to Fix

1. **XiaomiSnackbars.kt**: Update `message` parameter to `content`
2. **XiaomiTabs.kt**: Fix indicator scope receiver type
3. **Selection components**: Update Material3 API usage
4. **XiaomiTooltips.kt**: Fix nullable parameter handling

### Testing

- Component previews in each file
- UI test package mirrors main package structure
- Use component catalog for visual testing

## Important Files

- **FINAL_CRISIS_RESOLUTION_REPORT.md**: Details of recent fixes and migration patterns
- **app/src/main/java/com/xiaomi/base/ui/kit/README.md**: UI Kit documentation
- **.project-identity**: Project configuration and development stage tracking
- **libs.versions.toml**: Centralized dependency versions

## Tech Stack

- **Kotlin**: 2.2.0
- **Compose BOM**: 2025.06.01
- **Hilt**: 2.56.2
- **Room**: 2.7.2
- **Retrofit**: 3.0.0
- **Min SDK**: 23 (Android 6.0)
- **Target SDK**: 35 (Android 15)

## ⚠️ Common Pitfalls to Avoid

- Starting coding without brainstorming
- Skipping the planning phase
- Creating duplicate functionality
- Ignoring project identity settings
- Not backing up before major changes
- Implementing without understanding requirements
- Not following the 4-role development process

## Git Workflow

### Commit Convention

- Tuân thủ quy ước commit (feat, fix, docs, style, refactor...)
- Sử dụng tiếng Anh cho commit messages
- Format: `type(scope): description`
- Examples:
  - `feat(camera): add new filter effects`
  - `fix(ui): resolve layout issue in preview`
  - `docs(readme): update installation guide`

### Branch Management

- Sử dụng feature branches cho mỗi tính năng mới
- Merge vào main branch sau khi review
- Xóa feature branches sau khi merge thành công

## Internationalization (i18n)

### String Resources

- Luôn kiểm tra và thêm các chuỗi dịch hoặc resource khi tạo
- Sử dụng tiếng Anh làm ngôn ngữ mặc định
- Hỗ trợ đa ngôn ngữ với tiếng Anh làm fallback
- Tạo string keys có ý nghĩa và dễ hiểu

### Resource Management

- Tổ chức strings theo feature hoặc screen
- Sử dụng plurals cho các string có số lượng
- Implement proper formatting cho dates, numbers, currencies
