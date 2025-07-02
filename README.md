# Appdexer - Vibe Coding 🤖

## Ví dụ:

#### 🧪 Test quy trình

`Tôi muốn kiểm tra quy trình của bạn, giả sử tôi có ý tưởng làm ứng dụng học tập, thì các bước của bạn sẽ làm gì?`

#### 🧠 Brainstorm cho dự án mới

`Tôi có ý tưởng làm ứng dụng thời tiết AI hãy brainstorm`

#### 🧠 Nâng cấp tính năng cho dự án sẵn có

`Từ project hiện tại tôi muốn nâng cấp tính năng AI hãy rà soát và lên kế hoạch`

#### 🧑‍🎨 Tạo prompt từ UI Design

`Phân tích các thiết kế trong design/ theo quy trình design-to-prompt`

### 📝 Tạo các file instruction cho từng module

```bash
README.md Từ kịch bản này hãy thiết kế instruction cho từng phần của ứng dụng, bạn có thể tạo instruction cho các module, mỗi phần sẽ có 1 file instruction .md riêng sau đó được liên kết từ Instruction.md chính. Instruction.md sẽ là outline, để 1 AI sẽ thực hiện lần lượt các phần sao cho thành 1 sản phẩm hoàn chỉnh. Theo logic là mỗi lần thực hiện 1 nhiệm vụ nào đó thì AI sẽ đọc file Instruction.md và truy cập vào các instruction con theo từng nhiệm vụ cụ thể. Các instruction cần chia nhỏ ra từng phần và liên kết với nhau thông qua Instruction.md
```

## Global Rules

Cấu hình trong IDE Cursor:

````# Global Rules for All Projects

## Communication Style
- Sử dụng tiếng Việt cho trò chuyện và giải thích với giọng điệu hài hước kiểu giới trẻ
- Trả lời rõ ràng, đầy đủ nhưng không dài dòng
- Luôn hỏi làm rõ khi yêu cầu không rõ ràng
- Thông báo khi bạn không chắc chắn về cách giải quyết
- Mỗi khi cần phản hồi của người dùng hãy gọi review gate chat, nếu không gọi được review gate chat thì kiểm tra nhiệm vụ trong file, nếu đã hoàn thành rồi thì cập nhật trạng thái và tiếp tục làm các công việc còn lại

## Icon resource
- Khi cần icon hãy tìm kiếm thông qua mcp lucide-icons, tùy theo dự án mà sử dụng các loại icon khác nhau

## Planning
- Luôn tạo kế hoạch cho các nhiệm vụ lớn vào file .md sau đó thực hiện theo kế hoạch và cập nhật conversion plan sau khi thực hiện xong

## Language
- Luôn kiểm tra và thêm các chuỗi dịch hoặc resource khi tạo
- Luôn sử dụng tiếng Anh làm ngôn ngữ chính

## Core Working Principles
- Luôn phân tích kỹ trước khi chỉnh sửa code
- Tập trung vào vấn đề chính, không lạc đề
- Xác định rõ nguyên nhân gốc rễ (root cause) trước khi sửa lỗi
- Chỉ thực hiện một thay đổi lớn mỗi lần và kiểm tra kỹ trước khi tiếp tục

## Problem Solving
- Ngừng ngay khi gặp vấn đề cần giải quyết
- Không nhảy vội vào việc sửa code khi gặp lỗi
- Luôn đưa ra 2-3 phương án khi giải quyết vấn đề phức tạp
- Dừng và xin hướng dẫn sau 3 lần thử không thành công

## Quality Standards
- Sử dụng tiếng Anh cho tất cả code và tài liệu kỹ thuật
- Viết code tự giải thích với tên biến/hàm rõ ràng
- Tuân thủ các nguyên tắc SOLID
- Implement xử lý lỗi một cách đúng đắn

## Documentation
- Ghi lại mọi quyết định quan trọng vào Decisions.md
- Cập nhật Codebase.md mỗi khi hoàn thiện một phần
- Đánh dấu các task đã hoàn thành trong Instruction.md
- Kết thúc mỗi nhiệm vụ với mô tả ngắn gọn về công việc đã làm

## Safety Measures
- Không tự ý tối ưu code khi không được yêu cầu
- Không xóa code không liên quan khi không được yêu cầu
- Cẩn thận khi xóa file hoặc chỉnh sửa file ngoài nhiệm vụ chính
- Tạo backup đơn giản trước những thay đổi lớn```

### 🤖 Context7 Auto-Check Workflow (Trae)

**Tự động kiểm tra context dự án mà không cần yêu cầu cụ thể**

```bash
# Workflow sẽ tự động:
# 1. Scan tech stack từ package files
# 2. Query Context7 MCP cho latest docs & best practices
# 3. Cache context cho session hiện tại
# 4. Apply context-aware suggestions
# 5. Update project knowledge base

# Ví dụ sử dụng:
"Tôi muốn thêm authentication cho app React này"
# → Auto detect React version & related libraries
# → Query Context7 cho React auth patterns
# → Suggest best practices cho current stack
# → Provide up-to-date implementation guide
````

**Xem chi tiết**: [Context7 Implementation Guide](instructions/workflows/context7-implementation-guide.md)

## ⚠️⚠️⚠️ Cảnh Báo

- Các thư mục chính là .cursor, .github, .appdexer, .trae và tools, scripts sẽ được cập nhật thường xuyên, chỉ cần copy đè lên là xong.

## Extension bổ sung

https://marketplace.cursorapi.com/items?itemName=iganbold.yoyo-ai-versioning

- Quản lý version local, hỗ trợ thêm khi code bằng AI

## Nguyên tắc

- Fix các bug nhỏ, code giao diện -> TRAE
- Code phức tạp, code sâu -> Cursor

## Cấu hình telegram

scripts/config/telegram_config.py

## ✨ Tính Năng Chính

### 🎯 Review Gate V2 Integration

- **Interactive Loop**: AI chờ feedback từ bạn thông qua text, voice, hoặc image upload
- **5x Request Value**: Một request chính có thể thực hiện công việc của 5 request riêng lẻ
- **Deep Iteration**: Sử dụng đầy đủ 25 tool calls trong một session
- **Multi-modal Input**: Hỗ trợ text, voice-to-text, và image analysis

### 🧠 AI-Powered Features

- **Design-to-Prompt**: Tự động phân tích design files và tạo instruction
- **Competitor Analysis**: AI phân tích đối thủ cạnh tranh và đề xuất tính năng
- **Smart Resource Management**: Tự động tải icons, cập nhật rules

## 🚀 Quick Start

### Prerequisites

- **Cursor IDE** với MCP support
- **Python >= 3.9** (cho MCP server)
- **Git**
- Platform-specific SDK (Android Studio, Xcode, Node.js...)

### 1. Clone & Setup

# Clone dự án

```bash
git clone https://github.com/babyskill/Base-AI-Project.git
cd Base-AI-Project
```

### 2.

Cài Extension Review Gate để tiết kiệm request Cursor trong thư mục

1. Mở Cursor IDE
2. Nhấn `Cmd+Shift+P`
3. Gõ `Extensions: Install from VSIX`
4. Chọn file: `./tools/Review-Gate/V2/review-gate-v2-2.6.3.vsix`
5. Restart Cursor khi được yêu cầu

```bash
./tools/Review-Gate/V2/review-gate-v2-2.6.3.vsix
```

Bật MCP Server

```bash
cd tools/Review-Gate/V2/
./install.sh hoặc ./install.bat ( Window )
```

Bật MCP

```bash
python ./review_gate_v2_mcp.py
```

### 3. Initialize Your Project

## 📖 Hướng Dẫn Sử Dụng

### 🆕 Tạo Dự Án Mới

#### Ví dụ: Tạo Android Weather App

- Tự động

1. **Setup Project Identity**
   **_Mở android studio tạo project trống đè lên project base_**

2. **Brainstorm với AI**

```
💬 User: "Tôi muốn tạo app thời tiết cho Android"

🤖 AI: Tuyệt! Tôi sẽ giúp bạn brainstorm ý tưởng này. Trước tiên:

1. App này dành cho đối tượng nào? (học sinh, người đi làm, nông dân...)
2. Tính năng chính bạn muốn có là gì?
3. Có muốn tích hợp AI không?

[Review Gate V2 sẽ mở popup để bạn trả lời chi tiết]

```

3. **AI Phân Tích Design (nếu có)**

```bash
# Nếu bạn có design files trong thư mục design/
Sử dụng quy trình design to prompt để phân tích thiết kế ./design/weather-app-mockup.png
```

4. **Tạo Instruction Tự Động**
   AI sẽ tạo các file instruction modular:

- `Instruction.md` (outline chính)
- `instructions/WeatherApp_Core_Instruction.md`
- `instructions/WeatherApp_UI_Instruction.md`
- `instructions/WeatherApp_AI_Instruction.md`

5. **Begin Development**

```bash
# AI bắt đầu implement theo instruction
# Review Gate V2 sẽ hỗ trợ iterative development
```

#### Output Example:

```
✅ Project Structure Created
✅ Android Workflow Applied
✅ Instruction Files Generated
✅ Database Schema Created (db-schema.sql)
✅ UI Components Planned
✅ AI Features Integrated
```

### 🔧 Phát Triển Tính Năng Mới

#### Ví dụ: Thêm Voice Search cho Weather App

1. **Planning Phase**

```
💬 "Thêm tính năng voice search để tìm kiếm thành phố"

🤖 AI (Planner):
- Phân tích yêu cầu: Voice search cho location
- Nghiên cứu best practices
- Tạo plan chi tiết

[AI sẽ tạo file: instructions/VoiceSearch_Feature_Instruction.md]
```

2. **Architecture Phase**

```
🤖 AI (Architect):
- Speech-to-Text integration (Android SpeechRecognizer)
- Location validation logic
- UI components (FloatingActionButton + Animation)
- Error handling cho offline/no-permission cases
```

3. **Builder Phase**

```
🤖 AI (Builder):
- Implement VoiceSearchManager
- Create UI components
- Integrate with WeatherRepository
- Add voice feedback
```

4. **Tester Phase**

```
🤖 AI (Tester):
- Unit tests cho voice recognition
- UI tests cho voice button
- Integration tests
- Performance testing
```

### ⬆️ Nâng Cấp Dự Án Hiện Có

#### Ví dụ: Nâng cấp Weather App thêm AI Weather Assistant

1. **AI Analysis Current Project**

```
🤖 AI: Trước khi bắt đầu nâng cấp, tôi cần hiểu rõ hơn về dự án hiện tại:

🔍 Analyzing codebase...
📁 Current structure: Android MVVM with Repository pattern
🗃️ Database: Room + SQLite (weather_cache table)
🌐 APIs: OpenWeatherMap, WeatherAPI
⚡ Current features: Basic weather, 7-day forecast, location search

Bạn muốn AI assistant có những chức năng gì?
```

2. **Create Upgrade Plan**

```
📋 Upgrade Plan: AI Weather Assistant
├── 🧠 AI Integration (GPT-4 Weather Analysis)
├── 💬 Chat Interface (Natural language queries)
├── 🎯 Smart Recommendations (Clothing, activities)
├── 🔄 Context-aware responses
└── 🎨 Updated UI for chat interaction

⚠️ Impact Analysis:
- New dependency: OpenAI SDK
- Database changes: conversation_history table
- UI changes: ChatFragment + RecyclerView
- Performance: API call optimization needed
```

3. **Safety Backup**

```
🔒 Creating backup...
📁 _backups/2024-01-15/
├── app/src/main/java/com/weather/
├── app/src/main/res/
└── app/build.gradle

✅ Backup completed! Proceeding with upgrade...
```

4. **Incremental Implementation**

```
Phase 1: AI Integration
✅ Added OpenAI SDK dependency
✅ Created AIWeatherAssistant service
⏳ Implementing chat UI...

[Review Gate V2 cho phép bạn điều chỉnh real-time]

💬 User: "Làm cho chat bubble có animation smooth hơn"
🤖 AI: Đang cải thiện animation với Material Motion...
```

## 🎛️ Review Gate V2 Features

### 📝 Text Input

```
💬 "Thêm dark mode support cho toàn bộ app"
💬 "Optimize performance cho devices cũ"
💬 "Add unit tests cho WeatherRepository"
```

### 🎤 Voice Commands

```
🗣️ "Setup routing cho settings screen"
🗣️ "Phân tích design file mới nhất"
```

### 🖼️ Image Analysis

- Upload mockups để AI analyze và implement
- Share screenshots của bugs để AI debug
- Provide reference designs từ apps khác

### 🔄 Interactive Iteration

```
🤖 AI: Feature implemented!

[Review Gate V2 popup opens]

👤 User options:
1. "TASK_COMPLETE" → Kết thúc session
2. "Thêm animation" → Continue iteration
3. Upload screenshot → Visual feedback
4. Voice input → Quick adjustments
```

## 🛠️ Advanced Workflows

### 🎯 Voice-First Development

```bash
# Common voice commands
🗣️ "Tạo component header với navigation"
🗣️ "Thêm dark mode support"
🗣️ "Tìm weather icons cho mobile app"
🗣️ "Setup database cho user preferences"
🗣️ "Fix crash when location permission denied"
```

## 📁 Project Structure

```
Base-AI-Project/
├── .cursor/
│   ├── rules/                  # Workflow rules (40+ files)
│   └── ReviewGateV2.mdc       # Main Review Gate configuration
├── instructions/               # AI instruction files
│   ├── setup/                 # Project setup guides
│   ├── android/               # Android-specific instructions
│   ├── ios/                   # iOS-specific instructions
│   └── reusable/              # Reusable instruction templates
├── experiences/               # Knowledge base từ past projects
├── tools/                     # Development tools & scripts
├── design/                    # Design files for analysis
├── assets/                    # Icons, images, resources
├── scripts/                   # Automation scripts
├── .project-identity          # Project type detection
├── .project-personality       # AI personality configuration
├── db-schema.sql             # Database schema management
└── README.md                 # This file
```

## 🎯 Best Practices

### ✅ Do's

- **Always start with brainstorming** - Đừng vội code
- **Use Review Gate V2** cho mọi development session
- **Follow 4-role workflow** (Planner → Architect → Builder → Tester)
- **Voice commands** cho các tác vụ repetitive
- **Backup before major changes** (automatic với workflow)
- **Update instructions** song song với code

### ❌ Don'ts

- **Đừng skip planning phase** để "code cho nhanh"
- **Đừng ignore project identity** khi switch projects
- **Đừng skip architect phase** - dẫn đến inconsistent code
- **Đừng quên test** sau mỗi feature
- **Đừng commit code** mà chưa review qua Review Gate

## 🔧 Troubleshooting

### ❓ Common Issues

**Q: Review Gate V2 không hoạt động?**

```bash
# Check MCP connection
@review_gate_chat "test connection"

# Restart MCP server if needed
npm run mcp:restart
```

**Q: Voice commands không được nhận diện?**

```bash
# Test voice system
appdexer_test_voice_system --quick-test

# Check microphone permissions
```

**Q: AI không follow instruction?**

```bash
# Check project identity
cat .project-identity

# Verify instruction file format
@appdexer_ai_analyze_context instructions/
```

## 🤝 Contributing

### Cách Đóng Góp Workflow Mới

1. Tạo file `.mdc` trong `.cursor/rules/`
2. Follow cấu trúc có sẵn
3. Test với ít nhất 2 project types khác nhau
4. Update documentation

### Báo Cáo Bug

Sử dụng Review Gate V2 để upload screenshots + mô tả chi tiết.

## 📚 Resources & References

- **[Review-Gate Original](https://github.com/LakshmanTurlapati/Review-Gate)** - Inspiration source
- **[Cursor IDE Documentation](https://cursor.sh/docs)** - Official docs
- **[MCP Protocol](https://modelcontextprotocol.io/)** - Model Context Protocol
- **Voice Commands Guide**: `instructions/voice-commands.md`
- **Project Templates**: `docs/templates/`

## 📄 License

MIT License - Feel free to use and modify for your projects.

---

**🚀 Ready to 5x your development productivity?**

Start your first project with Review Gate V2 and experience the future of AI-assisted development!

```bash
@review_gate_chat "Tôi muốn tạo app [your-idea] cho [platform]"
```

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
