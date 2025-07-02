# Base Workflows Configuration

## Mandatory Brainstorm Workflow

### Description

BẮT BUỘC thực hiện brainstorm workflow trước khi khởi tạo bất kỳ dự án mới nào

### Workflow Files

- Primary: `.cursor/rules/brainstorm-workflow.mdc`
- Detailed: `.cursor/rules/brainstorm-detailed-workflow.mdc`

### Enforcement Rules

- NGHIÊM CẤM bắt đầu planning khi chưa hoàn thành brainstorm
- BẮT BUỘC tạo file `Brainstorm_[TenDuAn].md` trước khi tiếp tục
- BẮT BUỘC hoàn thành tất cả 3 phases: Foundation → Structure → Advanced
- BẮT BUỘC xác nhận với người dùng trước khi chuyển sang planning

### Trigger Conditions

- Người dùng có ý tưởng mới
- Khởi động dự án trống
- Không tồn tại file `Brainstorm_[TenDuAn].md`
- Yêu cầu tạo dự án mới từ đầu

## Platform-Specific Workflows

### Android Workflow

- **Related Files**: `.cursor/rules/android-workflow.mdc`, `.cursor/rules/tdd-mobile-workflow.mdc`
- **Architecture**: MVVM + Clean Architecture với 3 lớp (UI, Domain, Data)
- **Tech Stack**: Kotlin, Jetpack Compose, Hilt, Room, Retrofit
- **Principles**: Blueprint-First Development, Module Registry, Code deduplication

### iOS Workflow

- **Related Files**: `.cursor/rules/ios-workflow.mdc`, `.cursor/rules/tdd-mobile-workflow.mdc`
- **Architecture**: MVVM + Clean Architecture với 3 lớp (Presentation, Domain, Data)
- **Tech Stack**: Swift, SwiftUI, Combine, Swinject, Alamofire
- **Principles**: Blueprint-First Development, @MainActor cho UI updates

### Flutter Workflow

- **Related Files**: `.cursor/rules/mobile-utility-workflow.mdc`, `.cursor/rules/tdd-mobile-workflow.mdc`
- **Architecture**: Clean Architecture + BLoC pattern
- **Tech Stack**: Dart, Flutter Widgets, BLoC/Riverpod, Dio, get_it

### Mobile Utility Workflow

- **Path**: `.cursor/rules/mobile-utility-workflow.mdc`
- **Categories**: Health & Fitness, Photo Editing, Office Utility Apps
- **Revenue Optimization**: 3-phase approach với Firebase Remote Config

### TDD Mobile Workflow

- **Path**: `.cursor/rules/tdd-mobile-workflow.mdc`
- **Coverage**: 85% unit tests, 70% integration tests, 100% critical flows
- **Cycle**: Red → Green → Refactor

## Global Enforcement Rules

### New Project Detection

**Triggers:**

- Người dùng đề cập 'ý tưởng mới'
- Người dùng nói 'tạo dự án mới'
- Không tồn tại file `Brainstorm_[TenDuAn].md` trong project
- Project folder trống hoặc chỉ có template files

**Mandatory Action:** Redirect to Brainstorm Workflow

**Blocking Message:**

```
🚫 PHÁT HIỆN DỰ ÁN MỚI - BẮT BUỘC THỰC HIỆN BRAINSTORM TRƯỚC

✅ Hãy bắt đầu với: 'Tôi muốn brainstorm ý tưởng [mô tả ngắn]'

📋 Workflow sẽ thực hiện:
1. Foundation Questions (5 câu hỏi cơ bản)
2. Structure Analysis (features & architecture)
3. Advanced Analysis (competitors & risks)

⚠️ Không thể tiếp tục development khi chưa hoàn thành brainstorm.
```

## Workflow Integration

### Validation Points

- **Before Planning**: Check `Brainstorm_[TenDuAn].md` exists and complete
- **Before Development**: Validate brainstorm confirmation status
- **Before Architecture**: Ensure technical requirements from brainstorm
- **Before Implementation**: Verify feature priorities from brainstorm

### Integration with Existing Workflows

- **Android**: MUST start with brainstorm validation, Blueprint-First requires foundation
- **iOS**: MUST start with brainstorm validation, Architecture decisions require insights
- **Flutter**: MUST start with brainstorm validation, Cross-platform strategy requires analysis
- **Mobile Utility**: Already includes 5 strategic questions, Revenue optimization requires foundation
- **TDD Mobile**: MUST validate brainstorm before test creation, Test scenarios require user stories
