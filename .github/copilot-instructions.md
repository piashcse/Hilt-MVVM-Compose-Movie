# GitHub Copilot Instructions

> **📋 Base Rules Reference**  
> Tất cả các quy tắc cơ bản được quản lý tập trung tại:
>
> - [Review-Gate Guide](../tools/Review-Gate/readme.md) - Complete Review-Gate documentation
> - [Review-Gate Rule](../ReviewGate.mdc) - Rule configuration for Cursor IDE
>
> **⚠️ Lưu ý:** Vui lòng tham khảo các file rules chính thức ở trên thay vì nội dung trong file này.

# Copilot Instructions

Tài liệu này hướng dẫn cách sử dụng GitHub Copilot trong dự án này. Để đảm bảo Copilot hoạt động hiệu quả và tuân thủ quy trình, hãy tham khảo các hướng dẫn chi tiết bên dưới. Mỗi mục sẽ liên kết trực tiếp đến file hướng dẫn gốc trong thư mục `instructions/`.

## Danh sách hướng dẫn chi tiết

- [AI Product Builder Workflow](../.cursor/rules/ai-product-builder-workflow.mdc): Quy trình xây dựng sản phẩm AI dành cho người không biết lập trình, gồm 3 giai đoạn và nhiều cấp độ.
- [TDD Guidelines](../.cursor/rules/tdd-guidelines.mdc): Hướng dẫn Test-Driven Development cho mobile apps, bao gồm unit tests, integration tests và UI tests.
- [Database Management](../.cursor/rules/database-management.mdc): Quy tắc quản lý, sử dụng và cập nhật database trong dự án.
- [Validate Workflow](../.cursor/rules/validate-workflow.mdc): Script và quy trình kiểm thử, xác thực tuân thủ workflow AI Product Builder.
- [Weather Basic](../.cursor/rules/weather-basic-workflow.mdc): Hướng dẫn tích hợp nhanh các API thời tiết cơ bản.
- [Weather Detailed](../.cursor/rules/weather-detailed-workflow.mdc): Hướng dẫn chi tiết về các API thời tiết nâng cao, phù hợp cho sản phẩm chuyên sâu.

## Quy trình kiểm soát trong thư mục `.cursor/`

- Các file rule trong `.cursor/rules/` (ví dụ: `brainstorm-workflow.mdc`, `planning-validation-rules.mdc`, `development-control-rules.mdc`) luôn được áp dụng để kiểm soát nghiêm ngặt từng giai đoạn của workflow.
- Mọi hành động của Copilot phải tuân thủ các rule này, không được bỏ qua bất kỳ bước kiểm soát nào.
- Khi có thay đổi về quy trình, cần cập nhật các rule file tương ứng trong `.cursor/rules/` và bổ sung hướng dẫn vào tài liệu này.

## Workflow quản lý task trong plan

- Khi bắt đầu làm việc với bất kỳ feature hoặc phase nào, Copilot phải tạo task tương ứng trong file plan (ví dụ: `Planning_[TenDuAn].md`).
- Mỗi task cần có mô tả rõ ràng, trạng thái (Not Started/In Progress/Completed) và ngày thực hiện.
- Sau khi hoàn thành công việc, Copilot phải cập nhật trạng thái task thành "Completed" trong plan.
- Việc quản lý task này giúp theo dõi tiến độ và đảm bảo mọi hoạt động đều được ghi nhận rõ ràng trong tài liệu planning.

> **Lưu ý:** Quy trình này áp dụng cho tất cả các phase (brainstorm, planning, development) và phải được tuân thủ nghiêm ngặt để đảm bảo tính minh bạch và kiểm soát chất lượng dự án.

> **Lưu ý:** Nếu có thêm file hướng dẫn mới trong thư mục `instructions/`, hãy bổ sung liên kết tương ứng vào tài liệu này để Copilot có thể tham chiếu đầy đủ.
