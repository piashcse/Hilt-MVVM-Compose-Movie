---
description: Quy định thống nhất về Markdown và tài liệu
globs: *.md,*.markdown,*.mdx,Instruction.md,README*.md,Decisions.md,Changelog.md,Help.md,Project.md,Codebase.md
alwaysApply: true
---

## 1. Tổ Chức File Markdown

### 1.1 Nguyên Tắc Tổ Chức Cơ Bản
- ***BẮT BUỘC*** lưu trữ tất cả file instruction và markdown tài liệu trong thư mục `instructions/`
- ***BẮT BUỘC*** tạo thư mục con trong `instructions/` cho các nhóm tài liệu khác nhau
- ***KHUYẾN NGHỊ*** đặt tên file có ý nghĩa và sử dụng kebab-case (`ten-file.md`)
- ***BẮT BUỘC*** các file còn lại ở root chỉ bao gồm: README.md, Decisions.md, Changelog.md, Codebase.md

### 1.2 Cấu Trúc Thư Mục Instruction
```
instructions/
├── api/               # Tài liệu API
│   ├── endpoints.md
│   └── models.md
├── core/              # Tài liệu kiến trúc core/cấu trúc dự án
│   ├── architecture.md
│   └── workflows.md
├── modules/           # Tài liệu cho từng module
│   ├── auth/
│   └── dashboard/
├── workflows/         # Quy trình làm việc
│   └── deployment.md
└── README.md          # Hướng dẫn sử dụng thư mục instructions
```

### 1.3 Quy Định Di Chuyển & Di Trú
- Di chuyển tất cả file markdown hiện có vào thư mục `instructions/` trừ các file cơ bản ở root
- Tạo cấu trúc thư mục phân cấp dựa trên nội dung và mục đích sử dụng
- Cập nhật các liên kết trong tài liệu sau khi di chuyển

### 1.4 File Được Giữ Lại ở Root
- `README.md` - Giới thiệu dự án và hướng dẫn cài đặt/sử dụng cơ bản
- `Decisions.md` - Ghi lại các quyết định quan trọng trong dự án
- `Changelog.md` - Lịch sử thay đổi của dự án
- `Codebase.md` - Cấu trúc tổng quan của codebase
- Các file configuration cần thiết khác

## 2. Định Dạng & Cấu Trúc Nội Dung

### 2.1 Cấu Trúc Tài Liệu
- Sử dụng tối đa 3 cấp độ heading (# ## ###)
- Bắt đầu mỗi tài liệu với heading cấp 1 (#) và mô tả ngắn gọn
- Chia nội dung thành các phần rõ ràng với heading phù hợp
- Sử dụng danh sách đầu dòng (bullet points) cho các điểm đơn lẻ
- Sử dụng danh sách đánh số cho các bước tuần tự
- Sử dụng bảng cho dữ liệu cấu trúc hoặc so sánh

### 2.2 Định Dạng Văn Bản
- Sử dụng **in đậm** cho các thuật ngữ quan trọng
- Sử dụng *in nghiêng* để nhấn mạnh
- Sử dụng `code blocks` cho mã, lệnh, và đầu ra
- Sử dụng > blockquotes cho ghi chú hoặc trích dẫn
- Bao tên file, biến, và lệnh bằng backticks (`)

### 2.3 Quy Định README Mỗi Thư Mục
- Mỗi thư mục con cần có file README.md mô tả nội dung thư mục
- File README.md cần liệt kê tất cả các file trong thư mục kèm mô tả ngắn gọn
- Sử dụng bảng hoặc danh sách cho tổ chức nội dung

## 3. Quy Định Liên Kết & Tham Chiếu

### 3.1 Liên Kết Giữa Các File
- Khi liên kết đến file trong cùng thư mục: `[Tiêu đề](ten-file.md)`
- Khi liên kết đến file trong thư mục khác: `[Tiêu đề](../ten-thu-muc/ten-file.md)`
- Khi liên kết đến section trong cùng file: `[Tiêu đề](#ten-section)`
- Khi liên kết đến section trong file khác: `[Tiêu đề](ten-file.md#ten-section)`

### 3.2 Tham Chiếu & Trích Dẫn
- Sử dụng liên kết tương đối để kết nối giữa các tài liệu
- Cung cấp liên kết đến API documentation khi đề cập
- Kết nối các tài liệu liên quan với nhau
- Tạo mục lục cho tài liệu dài

## 4. Quy Trình Tạo & Cập Nhật

### 4.1 Quy Trình Tạo File Mới
1. Xác định đúng thư mục con trong `instructions/` để lưu file
2. Tạo file với tên có ý nghĩa và định dạng kebab-case
3. Bắt đầu file với heading cấp 1 (#) và mô tả ngắn gọn
4. Cập nhật file `instructions/README.md` nếu cần thiết

### 4.2 Quy Định Cập Nhật
- Cập nhật tài liệu song song với phát triển code
- Đánh dấu rõ ràng các phần đã hoàn thành và đang tiến hành
- Ghi chú các thay đổi quan trọng trong tài liệu
- Đảm bảo tài liệu luôn phản ánh trạng thái hiện tại của dự án
- Ghi lại các quyết định thiết kế quan trọng trong Decisions.md
- Cập nhật Codebase.md khi thêm mới hoặc thay đổi đáng kể cấu trúc code
- Cập nhật Changelog.md sau mỗi phiên bản hoặc milestone

## 5. Quy Định Ngôn Ngữ & Trình Bày

### 5.1 Ngôn Ngữ
- Viết tiếng Việt rõ ràng, dễ hiểu
- Giải thích các thuật ngữ kỹ thuật khi cần thiết
- Giữ giọng điệu nhất quán và chuyên nghiệp
- Sử dụng tiếng Anh cho tên biến, code, command và thuật ngữ kỹ thuật không có từ tiếng Việt tương đương

### 5.2 Trình Bày
- Sử dụng khoảng trắng và dòng trống để phân tách các phần nội dung
- Đảm bảo định dạng nhất quán trong toàn bộ tài liệu
- Tuân thủ quy tắc đặt tên section đồng nhất trong toàn bộ project

## 6. Hiện Thực Hóa Quy Định
- Kiểm tra và cập nhật danh sách file .md hiện có
- Di chuyển các file vào thư mục instructions với cấu trúc phù hợp
- Điều chỉnh các liên kết trong tài liệu sau khi di chuyển
- Đảm bảo thư mục `instructions/` đã được đưa vào version control

## 7. Workflow Tích Hợp Với Code
- ***BẮT BUỘC*** có file instruction/kế hoạch trước khi bắt đầu code
- ***BẮT BUỘC*** backup tất cả file liên quan trước khi tiến hành thay đổi lớn
- ***BẮT BUỘC*** cập nhật documentation sau khi hoàn thành
- ***BẮT BUỘC*** lưu tất cả file instruction vào thư mục `instructions/` theo cấu trúc phù hợp 

## 8. Quy Định Tối Ưu Cho AI

### 8.1 Hạn Chế Code Block
- ***BẮT BUỘC*** không đưa code triển khai chi tiết vào markdown
- ***BẮT BUỘC*** chỉ sử dụng reference/pseudocode khi cần minh họa
- ***BẮT BUỘC*** giữ code block dưới 10 dòng
- ***BẮT BUỘC*** đảm bảo file markdown dưới 250 dòng để tối ưu cho Cursor

### 8.1.1 Nguyên Tắc Đặc Biệt Cho Instruction Files
- ***BẮT BUỘC*** instruction files chỉ chứa chỉ dẫn và yêu cầu
- ***NGHIÊM CẤM*** bao gồm code implementation trong instruction files
- ***BẮT BUỘC*** mô tả logic và flow bằng natural language
- ***BẮT BUỘC*** sử dụng pseudocode hoặc flowchart thay vì real code
- ***BẮT BUỘC*** tập trung vào "what" và "why" thay vì "how" cụ thể

### 8.2 Tối Ưu Mô Tả
- ***BẮT BUỘC*** mô tả ý tưởng/thuật toán thay vì code cụ thể
- ***BẮT BUỘC*** sử dụng tham chiếu đến file code thay vì copy code
- ***BẮT BUỘC*** sử dụng liên kết đến vị trí code trong repo
- ***KHUYẾN NGHỊ*** mô tả high-level concept thay vì chi tiết triển khai

### 8.3 Tách Biệt Tài Liệu Và Code
- ***BẮT BUỘC*** tạo file code riêng và tham chiếu đến chúng từ markdown
- ***BẮT BUỘC*** đặt code mẫu vào thư mục `examples/` và tham chiếu
- ***BẮT BUỘC*** đặt các code snippet phức tạp vào file riêng

### 8.4 Định Dạng Tối Giản Cho AI
- ***BẮT BUỘC*** sử dụng bullet points và ngôn ngữ tự nhiên đơn giản
- ***BẮT BUỘC*** tối giản cấu trúc văn bản để AI dễ xử lý
- ***BẮT BUỘC*** tránh sử dụng định dạng phức tạp không cần thiết
- ***BẮT BUỘC*** tổ chức nội dung dễ đọc cho AI (đơn giản, rõ ràng)

### 8.5 Lý Do Tối Ưu
- File markdown với nhiều code dễ vượt quá giới hạn context window của AI
- AI có khả năng hiểu mô tả ngôn ngữ tự nhiên tốt hơn code lớn
- Tách biệt code giúp dễ dàng cập nhật mà không ảnh hưởng đến tài liệu
- Tối ưu không gian và tốc độ xử lý của AI đồng thời giảm chi phí