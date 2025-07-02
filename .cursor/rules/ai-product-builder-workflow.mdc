# AI Product Builder Workflow

## Tổng Quan

Quy trình AI Product Builder được thiết kế để giúp những người không biết lập trình có thể xây dựng sản phẩm phần mềm hoàn chỉnh thông qua việc tương tác với AI. Quy trình được chia thành 3 giai đoạn chính, mỗi giai đoạn có 3 cấp độ từ cơ bản đến nâng cao.

## 🎯 Mục Tiêu

- **Target Audience**: Doanh nhân, freelancer, người có ý tưởng nhưng không biết code
- **Output**: Sản phẩm phần mềm hoàn chỉnh có thể deploy và sử dụng ngay
- **Approach**: AI chủ động hỏi và hướng dẫn từng bước

## 📋 Quy Trình 3 Giai Đoạn

### Giai Đoạn 1: BRAINSTORM 🧠

**Mục tiêu**: Thu thập và phân tích ý tưởng một cách có cấu trúc

#### Cấp Độ 1: Foundation (Cơ bản)

- **AI hỏi**: 5 câu hỏi cốt lõi về ý tưởng
- **Output**: Problem statement, target audience, value proposition
- **File tạo**: `Brainstorm_[TenDuAn].md` (Phase 1)

#### Cấp Độ 2: Structure (Trung bình)

- **AI hỏi**: 6 câu hỏi về features và cấu trúc
- **Output**: Feature priority matrix, user journey, tech requirements
- **File update**: `Brainstorm_[TenDuAn].md` (Phase 2)

#### Cấp Độ 3: Advanced Analysis (Nâng cao)

- **AI thực hiện**: Competitor research tự động
- **AI hỏi**: 5 câu hỏi về business và scaling
- **Output**: Market analysis, architecture đề xuất, risk assessment
- **File complete**: `Brainstorm_[TenDuAn].md` (Phase 3) ✅

### Giai Đoạn 2: PLANNING 📊

**Mục tiêu**: Tạo kế hoạch phát triển chi tiết dựa trên brainstorm

#### Prerequisites Check ✅

- File `Brainstorm_[TenDuAn].md` hoàn chỉnh tất cả 3 phases
- User đã xác nhận thông tin brainstorm
- Risk assessment đã được thực hiện

#### Planning Process

1. **Validation**: AI kiểm tra prerequisites
2. **Architecture Design**: Dựa trên brainstorm đề xuất kiến trúc
3. **Phase Planning**: Chia thành 3 phases development
4. **Resource Planning**: Timeline, budget, team requirements
5. **Approval**: User review và approve planning

#### Output

- **File tạo**: `Planning_[TenDuAn].md`
- **Sections**: Executive Summary, Development Phases, Architecture, Risk Management

### Giai Đoạn 3: DEVELOPMENT 💻

**Mục tiêu**: Triển khai sản phẩm theo planning đã được approve

#### Prerequisites Check ✅

- File `Planning_[TenDuAn].md` hoàn chỉnh
- Planning đã được approve (✅ Planning Approval)
- Development environment ready

#### Development Phases

##### Phase 1: MVP Development (Cơ bản)

- **Duration**: [Timeline từ planning]
- **Features**: Must-Have features only
- **Activities**: Setup, core features, basic testing
- **Deliverable**: Working MVP

##### Phase 2: Enhanced Features (Trung bình)

- **Prerequisites**: Phase 1 completed ✅
- **Features**: Should-Have features
- **Activities**: UX improvements, optimizations
- **Deliverable**: Enhanced product

##### Phase 3: Advanced & Scaling (Nâng cao)

- **Prerequisites**: Phase 2 completed ✅
- **Features**: Could-Have features
- **Activities**: Advanced integrations, scaling
- **Deliverable**: Production-ready product

## 🔒 Validation & Control System

### Brainstorm Phase Control

```
User Request → AI checks if brainstorm needed
↓
If new idea → Start Phase 1 Foundation
If continuing → Continue current phase
If complete → Allow planning
```

### Planning Phase Control

```
Planning Request → Check Brainstorm_[TenDuAn].md exists
↓
Check all 3 phases completed ✅
↓
Check user confirmation ✅
↓
Create Planning_[TenDuAn].md
```

### Development Phase Control

```
Development Request → Check Planning_[TenDuAn].md exists
↓
Check planning approval ✅
↓
Check current phase prerequisites
↓
Allow development in current phase only
```

## 📁 File Structure & Dependencies

### Required Files

```
project-root/
├── Brainstorm_[TenDuAn].md     ← Phase 1 output
├── Planning_[TenDuAn].md       ← Phase 2 output
├── .project-identity           ← Auto-generated
├── instructions/               ← Documentation
└── .cursor/rules/              ← AI behavior rules
```

### Dependencies Flow

```
Brainstorm Complete → Planning Allowed
Planning Approved → Development Allowed
Phase N Complete → Phase N+1 Allowed
```

## 🤖 AI Behavior Rules

### Brainstorm Phase

- **_BẮT BUỘC_** hỏi từng câu một, không hỏi hàng loạt
- **_BẮT BUỘC_** phân tích và feedback sau mỗi câu trả lời
- **_BẮT BUỘC_** tự động thực hiện competitor research
- **_BẮT BUỘC_** xác nhận hiểu biết trước khi chuyển phase

### Planning Phase

- **_BẮT BUỘC_** validate brainstorm completion trước khi planning
- **_BẮT BUỘC_** dựa trên brainstorm để tạo planning
- **_BẮT BUỘC_** chia development thành 3 phases rõ ràng
- **_BẮT BUỘC_** đề xuất architecture phù hợp

### Development Phase

- **_BẮT BUỘC_** validate planning approval trước khi code
- **_BẮT BUỘC_** follow architecture trong planning
- **_BẮT BUỘC_** implement theo thứ tự phase (MVP → Enhanced → Advanced)
- **_BẮT BUỘC_** update progress trong planning file

## 🚫 Error Messages & Guidance

### No Brainstorm Error

```
🚫 KHÔNG THỂ BẮT ĐẦU PLANNING

❌ Chưa tìm thấy file Brainstorm_[TenDuAn].md
❌ Cần thực hiện brainstorm trước khi planning

🔄 Hãy bắt đầu với: "Tôi muốn brainstorm ý tưởng [mô tả ngắn]"
```

### Incomplete Brainstorm Error

```
🚫 BRAINSTORM CHƯA HOÀN THIỆN

Trạng thái hiện tại:
✅ Phase 1: Foundation - Completed
❌ Phase 2: Structure - Incomplete
❌ Phase 3: Advanced Analysis - Not started

🔄 Cần hoàn thành tất cả phases trước khi planning
```

### No Planning Error

```
🚫 KHÔNG THỂ BẮT ĐẦU DEVELOPMENT

❌ Chưa tìm thấy file Planning_[TenDuAn].md
❌ Development chỉ được thực hiện sau khi có planning hoàn chỉnh

🔄 Quy trình bắt buộc:
1. Brainstorm ý tưởng → tạo Brainstorm_[TenDuAn].md
2. Planning chi tiết → tạo Planning_[TenDuAn].md
3. Approval planning → ✅ Planning Approval
4. Bắt đầu development
```

## 📈 Progress Tracking

### Brainstorm Progress

```markdown
## Brainstorm Completion ✅/⏳/❌

- [x] Phase 1: Foundation completed and confirmed
- [x] Phase 2: Structure completed and confirmed
- [x] Phase 3: Advanced Analysis completed and confirmed
- [x] All information validated with user
- [x] Ready for planning phase
```

### Planning Progress

```markdown
## Planning Approval ✅/❌

- [x] Planning reviewed by stakeholders
- [x] Technical approach approved
- [x] Timeline and budget approved
- [x] Ready to begin development
```

### Development Progress

```markdown
## Development Progress

### Phase 1: MVP Development ⏳

- [x] Project setup - Completed 2024-01-15
- [x] Database schema - Completed 2024-01-16
- [ ] Authentication system - In Progress
- [ ] Core feature 1 - Not Started
```

## 🎯 Success Criteria

### Brainstorm Success

- ✅ Problem statement rõ ràng và validated
- ✅ Target audience được xác định cụ thể
- ✅ Feature priority matrix hoàn chỉnh
- ✅ Competitor analysis (min 3 competitors)
- ✅ Technical requirements realistic
- ✅ Risk assessment comprehensive

### Planning Success

- ✅ Planning document hoàn chỉnh tất cả sections
- ✅ Architecture phù hợp với requirements
- ✅ Timeline và budget realistic
- ✅ Development phases rõ ràng
- ✅ User approval planning document

### Development Success

- ✅ Code matches planning specifications
- ✅ Features align với brainstorm requirements
- ✅ Quality standards đạt planning benchmarks
- ✅ Progress tracked accurately
- ✅ Each phase delivers working product

## 🔄 Quality Gates

### Before Planning

- Brainstorm document 100% complete
- All 3 phases confirmed by user
- Competitor research completed
- Risk assessment done

### Before Development

- Planning document 100% complete
- Architecture approved
- Development environment ready
- Current phase objectives clear

### Before Next Phase

- Current phase 100% complete
- Features tested and working
- User feedback collected (if applicable)
- Technical debt addressed

## 🛠️ Technical Implementation

### Rules Configuration

- `brainstorm-workflow.mdc` - Controls brainstorm process
- `planning-validation-rules.mdc` - Validates planning prerequisites
- `development-control-rules.mdc` - Controls development phases
- All rules set to `alwaysApply: true` for strict enforcement

### File Validation Logic

```python
def can_start_planning():
    return (
        file_exists("Brainstorm_[TenDuAn].md") and
        brainstorm.phase1_complete and
        brainstorm.phase2_complete and
        brainstorm.phase3_complete and
        brainstorm.user_confirmed
    )

def can_start_development():
    return (
        can_start_planning() and
        file_exists("Planning_[TenDuAn].md") and
        planning.is_complete and
        planning.is_approved
    )
```

## 📚 Templates & Examples

### Brainstorm Template

- Complete template trong `brainstorm-workflow.mdc`
- Structured sections cho tất cả 3 phases
- Progress tracking checkboxes
- User confirmation requirements

### Planning Template

- Complete template trong `planning-validation-rules.mdc`
- 3-phase development structure
- Architecture documentation
- Risk management sections
- Approval checkboxes

## 🎉 Expected Outcomes

### For Non-Technical Users

- Có thể tạo sản phẩm phần mềm mà không cần biết code
- Hiểu rõ process và requirements của sản phẩm
- Có planning và documentation professional
- Có sản phẩm working có thể deploy và sử dụng

### For Technical Quality

- Code follows best practices và architecture
- Proper error handling và security
- Comprehensive testing và documentation
- Scalable và maintainable codebase

### For Business Value

- Product phù hợp với market needs
- Clear value proposition và target audience
- Competitive analysis và positioning
- Risk assessment và mitigation strategies

## 🔧 Customization & Extension

### Adding New Question Types

- Extend brainstorm phases với specific domain questions
- Add industry-specific competitor research
- Include domain-specific risk assessments

### Adding New Development Phases

- Extend 3-phase model cho complex projects
- Add specialized phases (security, performance, etc.)
- Include deployment và maintenance phases

### Integration với External Tools

- Connect với project management tools
- Integration với design tools cho UI/UX
- Connect với deployment platforms
- Integration với analytics và monitoring tools

---

## 🚀 Getting Started

Để bắt đầu sử dụng AI Product Builder:

1. **Khởi tạo**: "Tôi muốn brainstorm ý tưởng [mô tả ngắn]"
2. **Follow AI guidance**: Trả lời các câu hỏi của AI một cách chi tiết
3. **Review progress**: Kiểm tra file brainstorm được tạo
4. **Continue phases**: Hoàn thành tất cả 3 phases brainstorm
5. **Move to planning**: Cho phép AI tạo planning document
6. **Approve planning**: Review và approve planning
7. **Start development**: Bắt đầu development theo phases

> **Lưu ý**: AI sẽ không cho phép bỏ qua bất kỳ bước nào. Quy trình được thiết kế để đảm bảo chất lượng và completeness của sản phẩm cuối cùng.
