# Hướng Dẫn Sử Dụng Review Gate V2

## 🎯 Tổng Quan

Review Gate V2 là một công cụ mạnh mẽ tích hợp với Cursor IDE giúp biến mỗi request AI thành một phiên làm việc tương tác dài hạn. Thay vì kết thúc sớm, AI sẽ mở popup để nhận feedback và sub-prompts từ bạn.

## ✅ Trạng Thái Setup

- ✅ **MCP Server**: Đã cài đặt tại `/Users/trungkientn/cursor-extensions/review-gate-v2/`
- ✅ **MCP Configuration**: Đã cấu hình cả local (.cursor/mcp.json) và global (~/.cursor/mcp.json)
- ✅ **Python Environment**: Virtual environment với các dependencies đã ready
- ✅ **Extension File**: review-gate-v2-2.6.3.vsix sẵn sàng cài đặt
- ✅ **Rule File**: ReviewGateV2.mdc đã copy vào .cursor/rules/
- ✅ **Speech-to-Text**: Faster-Whisper đã sẵn sàng cho voice input

## 🚀 Các Bước Kích Hoạt

### Bước 1: Cài Đặt Extension (QUAN TRỌNG)

1. Mở Cursor IDE
2. Nhấn `Cmd+Shift+P`
3. Gõ `Extensions: Install from VSIX`
4. Chọn file: `/Users/trungkientn/cursor-extensions/review-gate-v2/review-gate-v2-2.6.3.vsix`
5. Restart Cursor khi được yêu cầu

### Bước 3: Test Hoạt Động

```bash
# Test manual trigger
Cmd+Shift+R

# Test AI integration
"Use the review_gate_chat tool to get my feedback"
```

## 🎤 Tính Năng Chính

### 1. Text Input

- Gửi feedback và sub-prompts bằng text thông thường
- AI sẽ thực hiện yêu cầu và tiếp tục vòng lặp review

### 2. Image Upload

- Upload screenshots, mockups, diagrams
- Hỗ trợ: PNG, JPG, JPEG, GIF, BMP, WebP
- AI có thể nhìn thấy và phân tích hình ảnh

### 3. Speech-to-Text

- Click microphone icon để ghi âm
- Nói rõ ràng trong 2-3 giây
- Faster-Whisper sẽ transcribe locally (không gửi lên cloud)
- Text được tự động điền vào popup

### 4. Professional Interface

- Popup với thiết kế orange glow đẹp mắt
- Real-time status indicators
- Smooth animations và responsive feedback

## 🔧 Cách Sử Dụng

### Workflow Cơ Bản

1. **Đưa ra yêu cầu chính**: "Tạo một React component với authentication"
2. **AI thực hiện task**: Coding, analysis, tool calls
3. **Review Gate popup xuất hiện**: AI chờ feedback từ bạn
4. **Bạn có thể**:
   - Gõ text: "Thêm validation cho form"
   - Upload hình: Screenshot của error cần fix
   - Nói: Click mic và nói "Add dark mode support"
   - Kết thúc: Gõ "TASK_COMPLETE" khi hài lòng
5. **AI tiếp tục**: Thực hiện sub-request và lặp lại

### Lệnh Kết Thúc

Để kết thúc review loop, gõ một trong các từ:

- `TASK_COMPLETE`
- `Done`
- `Quit`
- `q`

### Shortcuts Hữu Ích

- `Cmd+Shift+R`: Mở popup thủ công
- F12: Mở browser console để debug (nếu cần)

## 🎯 Tips Sử Dụng Hiệu Quả

### Voice Commands

- Nói rõ ràng và với tốc độ vừa phải
- Dừng một chút giữa các từ quan trọng
- Faster-Whisper hoạt động tốt với tiếng Anh tự nhiên

### Image Context

- Upload screenshots khi gặp lỗi visual
- Chia sẻ mockups hoặc design references
- Diagrams kiến trúc để AI hiểu được context

### Text Prompts

- Càng cụ thể càng tốt
- Đề cập đến file/function names nếu có
- Chia nhỏ requests phức tạp thành nhiều bước

## ⚠️ Troubleshooting

### MCP Server Không Hoạt Động

```bash
# Check logs
tail -f /tmp/review_gate_v2.log

# Test server
cd /Users/trungkientn/cursor-extensions/review-gate-v2
./venv/bin/python review_gate_v2_mcp.py --version
```

### Popup Không Xuất Hiện

1. Kiểm tra extension đã install chưa
2. Verify rule đã copy đúng chưa
3. Restart Cursor hoàn toàn
4. Test manual trigger: `Cmd+Shift+R`

### Speech-to-Text Lỗi

```bash
# Test SoX
sox --version
sox -d -r 16000 -c 1 test.wav trim 0 3 && rm test.wav
```

### Extension Lỗi

- Mở F12 trong Cursor để xem browser console
- Uninstall và reinstall extension nếu cần
- Check compatibility với Cursor version

## 📊 Lợi Ích

### Tiết Kiệm Requests

- Thay vì 5 requests riêng biệt → 1 request với 5 sub-prompts
- Tận dụng tối đa 25 tool calls available per request
- Hiệu quả hơn với monthly limit (~500 requests)

### Workflow Mượt Mà

- Không cần bắt đầu conversation mới cho mỗi iteration
- Giữ nguyên context xuyên suốt phiên làm việc
- AI nhớ được tất cả thay đổi từ đầu đến cuối

### Multi-Modal Experience

- Kết hợp text, voice, và visual context
- Flexible input methods tùy tình huống
- Rich feedback loop với AI

## 🔮 Advanced Usage

### Complex Workflows

```
Initial: "Build a full authentication system"
↓ AI creates basic auth
Sub-prompt 1: "Add password reset" (voice)
↓ AI implements reset
Sub-prompt 2: [Upload screenshot] "Fix this layout issue"
↓ AI fixes UI
Sub-prompt 3: "Add rate limiting" (text)
↓ AI adds protection
Final: "TASK_COMPLETE"
```

### Integration Với Dự Án

- Sử dụng cho feature development phức tạp
- Debug sessions với visual context
- Code reviews với voice feedback
- Architecture discussions với diagrams

## 📞 Hỗ Trợ

### Files Quan Trọng

- **MCP Config**: `.cursor/mcp.json` (local), `~/.cursor/mcp.json` (global)
- **Rule File**: `.cursor/rules/ReviewGateV2.mdc`
- **Extension**: `/Users/trungkientn/cursor-extensions/review-gate-v2/review-gate-v2-2.6.3.vsix`
- **Server**: `/Users/trungkientn/cursor-extensions/review-gate-v2/review_gate_v2_mcp.py`

### Test Script

Chạy test để verify setup:

```bash
python3 test_review_gate.py
```

### Logs Location

- MCP Server logs: `/tmp/review_gate_v2.log`
- Cursor extension logs: F12 → Console trong Cursor

---

**🎉 Bây giờ bạn đã sẵn sàng sử dụng Review Gate V2! Hãy thử đưa ra một task phức tạp và trải nghiệm quy trình review tương tác mới.**
