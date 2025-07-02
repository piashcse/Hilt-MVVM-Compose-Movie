# Instructions Directory

## Mục đích
Thư mục này chứa các hướng dẫn và tài liệu **cụ thể cho từng dự án**. Các workflow chung đã được di chuyển vào `.cursor/rules/` để đồng bộ hóa.

## Cấu trúc mới

### 📁 `.cursor/rules/` - Workflows chung cho tất cả dự án
- `brainstorm-detailed-workflow.mdc` (trước đây: `Brainstorm.md`)
- `android-aso-package-workflow.mdc` (trước đây: `android-aso-package-workflow.md`)
- `tdd-guidelines.mdc` (trước đây: `TDD_Guidelines.md`)
- `ai-product-builder-workflow.mdc` (trước đây: `AI-Product-Builder-Workflow.md`)
- `review-gate-v2-setup.mdc` (trước đây: `Review_Gate_V2_Setup.md`)
- `database-management.mdc` (trước đây: `database-management.md`)
- `validate-workflow.mdc` (trước đây: `validate-workflow.md`)
- `weather-basic-workflow.mdc` (trước đây: `weather-basic.md`)
- `weather-detailed-workflow.mdc` (trước đây: `weather-detailed.md`)

### 📁 `instructions/` - Tài liệu cụ thể cho dự án
- `API_Docs.md` - Tài liệu API cho dự án hiện tại
- `README.md` - File này
- Các file khác sẽ được tạo theo nhu cầu cụ thể của từng dự án

## Nguyên tắc sử dụng

### ✅ Sử dụng `.cursor/rules/` khi:
- Workflow có thể áp dụng cho nhiều dự án
- Quy tắc development chung
- Template và pattern tái sử dụng
- Cấu hình hệ thống

### ✅ Sử dụng `instructions/` khi:
- Tài liệu cụ thể cho dự án hiện tại
- API documentation riêng
- Business logic đặc thù
- Hướng dẫn deployment cụ thể
- Requirements và specifications

## Workflows

### Base Workflows
- [Base Workflows](workflows/base-workflows.md) - Quy trình cơ bản cho tất cả dự án
- [Context7 Auto-Check](workflows/context7-implementation-guide.md) - Tự động kiểm tra context dự án
- [Context7 Demo Example](workflows/context7-demo-example.md) - Ví dụ thực tế về auto context check

## Lợi ích của cấu trúc mới

1. **Đồng bộ hóa**: Tất cả workflows chung đều ở một nơi
2. **Tái sử dụng**: Dễ dàng áp dụng cho dự án mới
3. **Bảo trì**: Cập nhật một lần, áp dụng cho tất cả
4. **Tổ chức**: Phân biệt rõ ràng giữa chung và riêng
5. **Mở rộng**: Dễ dàng thêm workflow mới

## Migration hoàn tất

✅ Đã di chuyển tất cả workflow files từ `instructions/` → `.cursor/rules/`
✅ Đã cập nhật tất cả references trong:
- `.project-identity`
- `.github/copilot-instructions.md`
- `.cursor/rules/project-upgrade-workflow.mdc`
- `instructions/API_Docs.md`

## Sử dụng tiếp theo

Khi tạo dự án mới:
1. Copy toàn bộ `.cursor/rules/` để có đầy đủ workflows
2. Tạo `instructions/` mới cho tài liệu cụ thể của dự án
3. Cập nhật `.project-identity` theo nhu cầu dự án
