# Configuration Files

## Telegram Configuration

File `telegram_config.py` chứa các thông tin cấu hình cho Telegram Bot notification system. Bạn chỉ cần cấu hình một lần và có thể sử dụng cho nhiều dự án.

### Cấu hình

```python
# Telegram Bot Configuration
TELEGRAM_BOT_TOKEN = "your_bot_token_here"
TELEGRAM_CHAT_ID = "your_chat_id_here"

# SOCKS5 Proxy Configuration (optional)
SOCKS5_PROXY = {
    'http': 'socks5://username:password@host:port',
    'https': 'socks5://username:password@host:port'
}
```

### Cách sử dụng

1. Tạo Telegram Bot thông qua BotFather và lấy token
2. Tìm Chat ID của group hoặc channel bạn muốn gửi thông báo đến
3. Cập nhật thông tin vào file `telegram_config.py`
4. Sử dụng script `notify.sh` để gửi thông báo

```bash
./scripts/notify.sh "Task Name" [status] [details] [--no-screenshot]
```

### Ưu điểm của việc tách file cấu hình

1. **Tái sử dụng**: Không cần cấu hình lại cho mỗi dự án
2. **Bảo mật**: Có thể đặt file cấu hình ở nơi an toàn và symlink đến các dự án
3. **Dễ quản lý**: Tập trung tất cả cấu hình vào một nơi
4. **Linh hoạt**: Dễ dàng thay đổi cấu hình mà không ảnh hưởng đến code chính

### Lưu ý

Script vẫn hỗ trợ fallback về .env nếu không tìm thấy file cấu hình, đảm bảo tính tương thích ngược với các dự án cũ.