# Validation Script cho AI Product Builder Workflow

## Mục đích

Script này demo và validate việc AI tuân thủ nghiêm ngặt quy trình 3 giai đoạn của AI Product Builder.

## Test Cases

### Test Case 1: User yêu cầu planning khi chưa có brainstorm

**Input**: "Tôi muốn lên kế hoạch cho app di động"

**Expected AI Response**:

```
🚫 KHÔNG THỂ BẮT ĐẦU PLANNING

❌ Chưa tìm thấy file Brainstorm_[TenDuAn].md
❌ Cần thực hiện brainstorm trước khi planning

🔄 Hãy bắt đầu với: "Tôi muốn brainstorm ý tưởng [mô tả ngắn]"
```

**Validation**: AI **_NGHIÊM CẤM_** bắt đầu planning, phải yêu cầu brainstorm trước

### Test Case 2: User yêu cầu development khi chưa có planning

**Input**: "Viết code cho app weather"

**Expected AI Response**:

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

**Validation**: AI **_NGHIÊM CẤM_** viết code, phải yêu cầu hoàn thành quy trình trước

### Test Case 3: Brainstorm phase đúng quy trình

**Input**: "Tôi muốn brainstorm ý tưởng app quản lý thời tiết"

**Expected AI Behavior**:

1. **Phase 1**: AI hỏi 5 câu hỏi Foundation một cách tuần tự
2. **Phase 2**: AI hỏi 6 câu hỏi Structure sau khi hoàn thành Phase 1
3. **Phase 3**: AI tự động research competitor + 5 câu hỏi business
4. AI tạo file `Brainstorm_WeatherApp.md` với đầy đủ 3 phases

**Validation**:

- AI **_BẮT BUỘC_** hỏi từng câu một, không hỏi hàng loạt
- AI **_BẮT BUỘC_** phân tích feedback sau mỗi câu trả lời
- AI **_BẮT BUỘC_** thực hiện competitor research tự động
- AI **_BẮT BUỘC_** xác nhận hiểu biết trước khi chuyển phase

### Test Case 4: Planning với brainstorm hoàn chỉnh

**Prerequisites**: File `Brainstorm_WeatherApp.md` hoàn chỉnh tất cả 3 phases

**Input**: "Tôi muốn lên planning cho weather app"

**Expected AI Behavior**:

1. AI kiểm tra file `Brainstorm_WeatherApp.md` exists ✅
2. AI validate tất cả 3 phases completed ✅
3. AI dựa trên brainstorm để tạo planning
4. AI tạo file `Planning_WeatherApp.md` với architecture và 3 development phases
5. AI yêu cầu user review và approve planning

**Validation**:

- AI **_BẮT BUỘC_** validate brainstorm completion trước khi planning
- AI **_BẮT BUỘC_** dựa trên brainstorm để tạo planning
- AI **_BẮT BUỘC_** chia development thành 3 phases rõ ràng

### Test Case 5: Development với planning approved

**Prerequisites**:

- File `Brainstorm_WeatherApp.md` hoàn chỉnh ✅
- File `Planning_WeatherApp.md` hoàn chỉnh ✅
- Planning approval confirmed ✅

**Input**: "Bắt đầu development weather app"

**Expected AI Behavior**:

1. AI kiểm tra file `Planning_WeatherApp.md` exists ✅
2. AI kiểm tra planning approval status ✅
3. AI bắt đầu Phase 1: MVP Development only
4. AI follow architecture trong planning
5. AI update progress trong planning file

**Validation**:

- AI **_BẮT BUỘC_** validate planning approval trước khi code
- AI **_BẮT BUỘC_** implement theo thứ tự phase (MVP → Enhanced → Advanced)
- AI **_BẮT BUỘC_** follow architecture đã được approve

## Error Scenarios Testing

### Scenario 1: Brainstorm chưa hoàn thành

**Setup**: File `Brainstorm_WeatherApp.md` chỉ có Phase 1, thiếu Phase 2 và 3

**Input**: "Planning cho weather app"

**Expected AI Response**:

```
🚫 BRAINSTORM CHƯA HOÀN THIỆN

Trạng thái hiện tại:
✅ Phase 1: Foundation - Completed
❌ Phase 2: Structure - Incomplete
❌ Phase 3: Advanced Analysis - Not started

🔄 Cần hoàn thành tất cả phases trước khi planning
```

### Scenario 2: Planning chưa được approve

**Setup**: File `Planning_WeatherApp.md` exists nhưng chưa có ✅ Planning Approval

**Input**: "Bắt đầu code weather app"

**Expected AI Response**:

```
🚫 PLANNING CHƯA ĐƯỢC APPROVAL

❌ Planning document chưa được approve
❌ Cần ✅ Planning Approval trước khi development

🔄 Vui lòng review planning và confirm approval
```

## Success Criteria Validation

### Brainstorm Success Criteria

- ✅ Problem statement rõ ràng và validated
- ✅ Target audience được xác định cụ thể
- ✅ Feature priority matrix hoàn chỉnh
- ✅ Competitor analysis (min 3 competitors)
- ✅ Technical requirements realistic
- ✅ Risk assessment comprehensive

### Planning Success Criteria

- ✅ Planning document hoàn chỉnh tất cả sections
- ✅ Architecture phù hợp với requirements
- ✅ Timeline và budget realistic
- ✅ Development phases rõ ràng
- ✅ User approval planning document

### Development Success Criteria

- ✅ Code matches planning specifications
- ✅ Features align với brainstorm requirements
- ✅ Progress tracked accurately
- ✅ Each phase delivers working product

## Manual Validation Checklist

### Pre-Testing Setup

- [ ] Clear tất cả files từ tests trước
- [ ] Đảm bảo rules được load correctly:
  - [ ] `brainstorm-workflow.mdc` active
  - [ ] `planning-validation-rules.mdc` active
  - [ ] `development-control-rules.mdc` active
- [ ] Kiểm tra `alwaysApply: true` cho tất cả rules

### During Testing

- [ ] AI có thực sự từ chối planning khi chưa có brainstorm?
- [ ] AI có thực sự từ chối development khi chưa có planning?
- [ ] AI có hỏi từng câu một trong brainstorm (không hỏi hàng loạt)?
- [ ] AI có tự động thực hiện competitor research?
- [ ] AI có validate file dependencies correctly?
- [ ] Error messages có xuất hiện đúng format?

### Post-Testing Validation

- [ ] Files được tạo theo đúng naming convention
- [ ] File content có structure theo template
- [ ] Progress tracking được update correctly
- [ ] User guidance rõ ràng về next steps

## Regression Testing

Chạy lại test cases sau mỗi lần update rules để đảm bảo:

- Workflow validation vẫn hoạt động
- Error messages vẫn hiển thị correct
- File dependencies vẫn được enforce
- AI behavior consistent với quy định

## Performance Testing

- Thời gian AI response khi validate files
- Accuracy của file content parsing
- Consistency của behavior across multiple sessions

---

## Notes for Testers

1. **Strict Adherence**: AI phải tuân thủ 100% quy trình, không có exceptions
2. **Clear Feedback**: Mọi rejection phải có explanation rõ ràng và next steps
3. **File Dependencies**: Dependencies phải được check trước mọi action
4. **User Experience**: Dù strict nhưng vẫn phải user-friendly và helpful

> **Quan trọng**: Nếu AI cho phép bỏ qua bất kỳ bước nào trong quy trình, đó là BUG và cần fix immediately.
