# Quy Tắc Quản Lý Database

## Nguyên Tắc Cơ Bản

- **_BẮT BUỘC_** kiểm tra file `db-schema.sql` trước khi tạo hoặc sử dụng database
- **_BẮT BUỘC_** cập nhật file `db-schema.sql` sau khi tạo database mới
- **_BẮT BUỘC_** ghi rõ mục đích của mỗi database trong tài liệu
- **_BẮT BUỘC_** tránh tạo database trùng lặp hoặc thừa thãi
- **_KHUYẾN NGHỊ_** sử dụng lại database hiện có thay vì tạo mới nếu có thể

## Quy Trình Sử Dụng Database

### Trước Khi Sử Dụng Database

1. Kiểm tra file `db-schema.sql` để xác định cấu trúc database hiện tại
2. Tìm database phù hợp với nhu cầu sử dụng trong schema
3. Chỉ tạo database mới khi không tìm thấy database phù hợp

### Khi Cần Tạo Database Mới

1. Tạo bản thiết kế cấu trúc database (ERD hoặc mô tả chi tiết)
2. Xin phê duyệt từ team lead/kiến trúc sư hệ thống
3. Tạo database theo thiết kế đã được phê duyệt
4. **_BẮT BUỘC_** cập nhật file `db-schema.sql` với cấu trúc mới
5. Cập nhật tài liệu với mô tả về database mới

### Cập Nhật Schema

1. Khi thực hiện thay đổi cấu trúc database:
   - Thêm/xóa bảng
   - Thêm/xóa/sửa cột
   - Thay đổi relationship
   - Thay đổi constraints
2. **_BẮT BUỘC_** cập nhật ngay file `db-schema.sql`
3. Ghi chú rõ lý do thay đổi trong commit message

## Cấu Trúc File db-schema.sql

File `db-schema.sql` nên được tổ chức như sau:

```sql
-- DATABASE: <tên_database>
-- MỤC ĐÍCH: <mô tả ngắn về mục đích sử dụng>
-- NGÀY TẠO: <ngày tạo ban đầu>
-- NGƯỜI TẠO: <người tạo ban đầu>
-- LỊCH SỬ CẬP NHẬT:
-- <ngày> - <người cập nhật> - <mô tả ngắn về thay đổi>

CREATE DATABASE IF NOT EXISTS <tên_database>;
USE <tên_database>;

-- Định nghĩa bảng và cấu trúc
CREATE TABLE IF NOT EXISTS <tên_bảng> (
    ...
);

-- Thiết lập quan hệ và ràng buộc
ALTER TABLE <tên_bảng>
ADD CONSTRAINT ...;

-- Dữ liệu ban đầu (nếu cần)
INSERT INTO <tên_bảng> VALUES ...;

-- Kết thúc định nghĩa database này
-- ----------------------------------------
```

## Quản Lý Phiên Bản Database

- Sử dụng comment để đánh dấu phiên bản và thay đổi
- Giữ lại lịch sử thay đổi trong file schema
- Với các thay đổi lớn, nên tạo migration script riêng

## Tối Ưu Hóa Và Bảo Trì

- Định kỳ rà soát các database không sử dụng
- Ghi chú rõ các database đã deprecated và lịch trình xóa
- Tối ưu hóa cấu trúc database định kỳ

## Quy Ước Đặt Tên

- Tên database: chữ thường, sử dụng dấu gạch dưới để ngăn cách từ
- Tên bảng: số nhiều, chữ thường, gạch dưới ngăn cách
- Tên cột: chữ thường, gạch dưới ngăn cách
- Khóa ngoại: `<tên_bảng_tham_chiếu>_id`
- Index: `idx_<tên_bảng>_<tên_cột>`

## Báo Cáo Và Kiểm Soát

- Định kỳ kiểm tra sự đồng bộ giữa schema thực tế và file `db-schema.sql`
- Báo cáo các database không được sử dụng
- Lập kế hoạch dọn dẹp database không cần thiết

## Ví Dụ Minh Họa

```sql
-- DATABASE: user_management
-- MỤC ĐÍCH: Quản lý thông tin người dùng và phân quyền
-- NGÀY TẠO: 2024-05-15
-- NGƯỜI TẠO: dev_team
-- LỊCH SỬ CẬP NHẬT:
-- 2024-05-20 - lead_dev - Thêm bảng user_preferences

CREATE DATABASE IF NOT EXISTS user_management;
USE user_management;

CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS roles (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    description TEXT
);

CREATE TABLE IF NOT EXISTS user_roles (
    user_id INT NOT NULL,
    role_id INT NOT NULL,
    assigned_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id, role_id)
);

ALTER TABLE user_roles
ADD CONSTRAINT fk_user_roles_user
FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
ADD CONSTRAINT fk_user_roles_role
FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE;

-- Dữ liệu ban đầu
INSERT INTO roles (name, description) VALUES
('admin', 'Quản trị viên với toàn quyền'),
('user', 'Người dùng thông thường');

-- Kết thúc định nghĩa database user_management
-- ----------------------------------------
```
