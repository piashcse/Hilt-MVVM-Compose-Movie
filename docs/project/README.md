# Project Documentation Directory

## 📁 Thư mục này chứa tất cả file documentation (.md) của dự án

### 🔴 QUY TẮC BẮT BUỘC

- **TẤT CẢ file .md của dự án PHẢI được đặt trong thư mục này**
- **NGHIÊM CẤM tạo file .md ở root directory**
- **Tuân thủ cấu trúc tổ chức file theo quy tắc chuẩn**

### 📋 Danh sách file hiện có

- `Brainstorm_LogicReasoningSystem.md` - Brainstorm cho Logic Reasoning System
- `Changelog.md` - Lịch sử thay đổi dự án
- `Codebase.md` - Tài liệu về codebase
- `Decisions.md` - Ghi lại các quyết định quan trọng
- `Diagram.md` - Sơ đồ và biểu đồ dự án
- `Instruction.md` - Hướng dẫn và task tracking
- `Logic_Reasoning_System_Demo.md` - Demo Logic Reasoning System
- `MIGRATION_SUMMARY.md` - Tóm tắt migration
- `Planning_LogicReasoningSystem.md` - Kế hoạch Logic Reasoning System
- `Project.md` - Tổng quan dự án
- `mapping.md` - Mapping và cấu trúc

### 📝 Quy tắc đặt tên file

- **Brainstorm files**: `Brainstorm_[ProjectName].md`
- **Planning files**: `Planning_[FeatureName].md`
- **Core documentation**: `[Type].md` (Codebase.md, Decisions.md, etc.)
- **Feature documentation**: `[FeatureName]_[Type].md`

### 🔗 Liên kết với quy tắc khác

- Tuân thủ **[File Organization Rules](../../.cursor/rules/file-organization-rules.mdc)**
- Tích hợp với **[Project Identity Enforcement](../../.cursor/rules/project-identity-enforcement.mdc)**
- Sử dụng **[File Protection Rules](../../.cursor/rules/file-protection-rules.mdc)** khi di chuyển file

### 🚀 Hướng dẫn sử dụng

1. **Tạo file mới**: Luôn tạo trong thư mục này
2. **Di chuyển file**: Sử dụng backup trước khi di chuyển
3. **Cập nhật reference**: Kiểm tra và cập nhật tất cả đường dẫn liên quan
4. **Validation**: Đảm bảo không có file .md nào ở root directory

### ⚠️ Lưu ý quan trọng

- File này được tạo tự động khi tổ chức lại cấu trúc dự án
- Mọi thay đổi về tổ chức file phải tuân thủ quy tắc trong `.cursor/rules/file-organization-rules.mdc`
- Backup được tạo tự động trong thư mục `_backups/` khi di chuyển file

---

*Cập nhật lần cuối: $(date)*
*Tuân thủ: File Organization Rules v1.0*