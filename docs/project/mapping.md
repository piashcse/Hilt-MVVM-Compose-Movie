# Bảng Ánh Xạ ID

File này dùng để quản lý việc thay đổi ID trong quá trình chỉnh sửa layout XML của dự án smali. Mục đích là để dễ dàng theo dõi các thay đổi và khắc phục lỗi.

## Quy Tắc Đặt Tên

- ID gốc: Giữ nguyên
- ID wrapper: Thêm prefix "custom*wrapper*"
- ID mới: Thêm prefix "custom\_"

## Bảng Ánh Xạ

| ID Gốc            | Wrapper ID                       | ID Mới                   | File              | Mục Đích  |
| ----------------- | -------------------------------- | ------------------------ | ----------------- | --------- |
| @+id/example_view | @+id/custom_wrapper_example_view | @+id/custom_example_view | activity_main.xml | Ví dụ mẫu |

## Thống Kê Thay Đổi

**Tổng số View đã thay đổi:** 0

**Danh sách file đã chỉnh sửa:**

- Chưa có file nào được chỉnh sửa

## Lưu Ý

- Luôn cập nhật bảng này trước khi chỉnh sửa file XML
- Kiểm tra kỹ các tham chiếu ID trong code smali trước khi thay đổi
- Nếu phát hiện lỗi, kiểm tra lại bảng này để xác định view có vấn đề
