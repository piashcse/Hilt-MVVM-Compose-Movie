# Code Base Analysis & Planning Directory

## Mục Đích
Thư mục này chứa các tài liệu phân tích mã cơ sở và kế hoạch phát triển được tạo ra từ **Codebase Analysis Workflow**.

## Cấu Trúc

### 📁 `context/`
Chứa các tài liệu phân tích và ngữ cảnh của mã cơ sở:

- **CodebaseAnalysis_[ProjectName]_[Date].md** - Phân tích tổng quan về codebase
- **deep-dive/** - Phân tích chi tiết từng module/layer
  - `core-module.md` - Phân tích module cốt lõi
  - `api-layer.md` - Phân tích tầng API
  - `data-layer.md` - Phân tích tầng dữ liệu
  - `ui-layer.md` - Phân tích tầng giao diện
- **architecture/** - Tài liệu kiến trúc
  - `system-design.md` - Thiết kế hệ thống
  - `data-flow.md` - Luồng dữ liệu
  - `component-interaction.md` - Tương tác giữa các component
- **technical-requirements.md** - Yêu cầu kỹ thuật

### 📁 `plan/`
Chứa các kế hoạch phát triển và implementation:

- **DevelopmentPlan_[FeatureName]_[Date].md** - Kế hoạch phát triển chi tiết
- **FinalPlan_[FeatureName]_[Date].md** - Kế hoạch cuối cùng với navigation
- **options/** - Các lựa chọn implementation
  - `incremental-plan.md` - Kế hoạch triển khai từng bước
  - `bigbang-plan.md` - Kế hoạch triển khai một lần
  - `hybrid-plan.md` - Kế hoạch kết hợp
- **implementation/** - Chi tiết implementation
  - `algorithms.md` - Thuật toán cốt lõi
  - `database.md` - Thiết kế database
  - `api-spec.md` - Đặc tả API
  - `ui-guidelines.md` - Hướng dẫn UI/UX
- **considerations/** - Các cân nhắc quan trọng
  - `breaking-changes.md` - Phân tích breaking changes
  - `performance.md` - Cân nhắc về performance
  - `security.md` - Cân nhắc về bảo mật

## Quy Trình Sử Dụng

### 1. Phân Tích Mã Cơ Sở (Bước 1)
```bash
# AI sẽ tự động tạo các file phân tích trong context/
CodebaseAnalysis_[ProjectName]_[Date].md
```

### 2. Lập Kế Hoạch (Bước 2)
```bash
# AI sẽ tạo kế hoạch dựa trên phân tích trong plan/
DevelopmentPlan_[FeatureName]_[Date].md
```

### 3. Review và Rà Soát (Bước 3)
```bash
# AI sẽ tạo kế hoạch cuối cùng với deep links
FinalPlan_[FeatureName]_[Date].md
```

### 4. Xác Nhận và Thực Hiện (Bước 4)
```bash
# Người dùng review và xác nhận kế hoạch
# AI chờ confirmation trước khi bắt đầu implementation
```

## Tính Năng Chính

### 🔗 Deep Links
Tất cả tài liệu đều có deep links đến:
- File code cụ thể
- Functions và classes
- Configuration files
- Test files
- Documentation

### 🗺️ Interactive Navigation
Kế hoạch cuối cùng cung cấp:
- Navigation menu để jump đến sections
- Multiple implementation options
- Decision points với alternatives
- Quick start guides

### 📊 Visual Aids
- Architecture diagrams
- Data flow charts
- Component interaction maps
- Timeline và milestone charts

## Workflow Integration

### Kết Hợp với Workflows Hiện Tại
- **Brainstorm Workflow** → Cung cấp input cho analysis
- **Planning Workflow** → Sử dụng analysis làm foundation
- **File Protection Rules** → Backup trước khi modify
- **4-Role Development** → Tuân thủ quy trình Planner → Architect → Builder → Tester

### Automation
- AI tự động detect project type
- Auto-generate appropriate templates
- Smart linking đến relevant code
- Real-time validation của plans

## Best Practices

### Đặt Tên File
```
# Analysis files
CodebaseAnalysis_[ProjectName]_YYYY-MM-DD.md

# Planning files  
DevelopmentPlan_[FeatureName]_YYYY-MM-DD.md
FinalPlan_[FeatureName]_YYYY-MM-DD.md

# Deep dive files
[module-name]-analysis.md
[feature-name]-implementation.md
```

### Version Control
- Commit analysis files sau khi hoàn thành
- Tag major planning milestones
- Archive outdated plans trong `archive/` folder
- Maintain change log cho major updates

### Maintenance
- Update analysis khi codebase thay đổi significantly
- Review plans khi requirements change
- Validate deep links periodically
- Archive completed plans

## Troubleshooting

### Khi Deep Links Không Hoạt Động
1. Kiểm tra file path có chính xác không
2. Verify file vẫn tồn tại
3. Update links nếu file đã được moved
4. Report issue để AI update

### Khi Analysis Không Đầy Đủ
1. Re-run analysis với broader scope
2. Manually add missing components
3. Request specific deep-dive analysis
4. Update templates nếu cần

### Khi Plans Không Khả Thi
1. Review constraints và assumptions
2. Request alternative approaches
3. Break down into smaller phases
4. Consult với team members

---

## 📞 Support

Nếu có vấn đề với workflow hoặc cần hỗ trợ:
1. Check [Codebase Analysis Workflow](./.cursor/rules/codebase-analysis-workflow.mdc)
2. Review existing analysis và plans
3. Request AI assistance với specific questions
4. Update workflow rules nếu cần improvements

---

*Generated by Codebase Analysis Workflow*  
*Last Updated: [Date]*