# Android ASO Package Naming Workflow

## 🎯 Mục Tiêu

- **_BẮT BUỘC_** Package name tối ưu ASO với keyword-focus thay vì brand-focus
- **_BẮT BUỘC_** Kiểm tra availability trên Google Play Store ở 3+ quốc gia
- **_BẮT BUỘC_** Tránh rủi ro liên đới tài khoản với package name trùng lặp
- **_BẮT BUỘC_** Đánh dấu package name đã confirmed để AI không thay đổi
- **_KHUYẾN NGHỊ_** Automation workflow cho hiệu quả tối đa

## 📊 ASO Package Name Strategy

### Nguyên Tắc Đặt Tên Theo ASO

```markdown
GOOD Examples (ASO-Optimized):
✅ photoeditor.imagefilter.pictures.enhance
✅ qrcodescanner.barcodescanner.qrscanner.qrcodereader  
✅ heartmonitor.healthtracker.fitness.pulse
✅ documentpdf.officereader.filemanager.viewer
✅ weatherforecast.climatetracker.weather.alerts

BAD Examples (Brand-Focused):
❌ com.companyname.appname
❌ com.facebook.messenger
❌ com.spotify.music
❌ io.github.username.project
```

### Cấu Trúc Package Name ASO Chuẩn

```
[primary-keyword].[secondary-keyword].[tertiary-keyword].[action-keyword]

Examples:
- photo + editor + image + enhance = photoeditor.imagefilter.pictures.enhance
- qr + scanner + barcode + reader = qrcodescanner.barcodescanner.qrscanner.qrcodereader
- heart + monitor + health + tracker = heartmonitor.healthtracker.fitness.pulse
```

## 🔍 Keyword Research Automation

### App Category → Keywords Mapping

```markdown
Photo Editing Apps:
Primary: photo, image, picture, camera
Secondary: editor, filter, enhance, beauty
Tertiary: collage, frames, effects, vintage
Action: edit, create, make, design

Health & Fitness Apps:
Primary: health, fitness, heart, medical
Secondary: monitor, tracker, scanner, check
Tertiary: pulse, workout, nutrition, wellness
Action: track, measure, analyze, record

Office & Productivity Apps:
Primary: document, pdf, office, file
Secondary: reader, manager, scanner, converter
Tertiary: text, word, excel, presentation
Action: read, edit, convert, manage

QR/Barcode Apps:
Primary: qr, barcode, scanner, code
Secondary: reader, generator, decoder, maker
Tertiary: product, price, inventory, scan
Action: scan, read, generate, create
```

### Automated Keyword Scoring

```markdown
Keyword Selection Criteria:
🔥 High Search Volume Keywords (Primary): 70-90 competition score
⚡ Medium Competition Keywords (Secondary): 40-70 competition score  
🎯 Long-tail Keywords (Tertiary): 10-40 competition score
💪 Action Keywords: Strong action verbs

ASO Score Calculation:

- Primary keyword relevance: 40 points
- Secondary keyword search volume: 25 points
- Tertiary keyword uniqueness: 20 points
- Action keyword strength: 15 points
  Total ASO Score: /100 (Target: 75+ points)
```

## 🌍 Multi-Region Availability Check

### Automated Availability Validation

```bash
#!/bin/bash
# Auto-check package availability script

check_package_availability() {
    local package_name="$1"
    local regions=("us" "gb" "de" "jp" "au" "ca" "fr")
    local unavailable_count=0

    echo "🔍 Checking package availability: $package_name"
    echo "=================================="

    for region in "${regions[@]}"; do
        local url="https://play.google.com/store/apps/details?id=${package_name}&hl=${region}"
        local status=$(curl -s -o /dev/null -w "%{http_code}" "$url")

        if [ "$status" == "404" ]; then
            echo "✅ $region: AVAILABLE (404)"
        else
            echo "❌ $region: TAKEN ($status)"
            ((unavailable_count++))
        fi

        sleep 1  # Rate limiting
    done

    echo "=================================="
    if [ $unavailable_count -eq 0 ]; then
        echo "🎉 PACKAGE AVAILABLE IN ALL REGIONS!"
        echo "✅ Safe to use: $package_name"
        return 0
    else
        echo "⚠️  Package taken in $unavailable_count regions"
        echo "❌ Consider alternative: $package_name"
        return 1
    fi
}

# Usage: check_package_availability "photoeditor.imagefilter.pictures.enhance"
```

### Package Name AI Manual Creation Workflow

```markdown
## Step-by-Step Manual Creation Process

1. **AI Creates Unique Package Names** (3-5 options):

   - AI analyzes app features and target keywords manually
   - Creates UNIQUE combinations for each specific app
   - NO hard-coded templates or generation functions
   - Calculates estimated ASO score for each option

2. **Availability Check với Script**:

   - `./package-availability-checker.sh check package.name.here`
   - Test all AI-created packages against multiple regions
   - Log results in package-availability.md
   - Mark AVAILABLE packages for next step

3. **Final Selection Criteria**:

   - Highest ASO Score (75+ points)
   - Available in ALL tested regions
   - No conflicts with existing portfolio
   - Easy to pronounce and remember

4. **Package Confirmation & Locking**:
   - Add chosen package to build.gradle with special comment
   - Update package-availability.md với status CONFIRMED
   - Add to AI instruction để prevent changes
```

## 🛡️ Risk Prevention System

### Portfolio Conflict Prevention

```markdown
## Package Name Registry (Prevent Duplicates)

Maintain file: `portfolio-packages.md`

Current Portfolio Packages:

- photoeditor.imagefilter.pictures.enhance (App: PhotoMaster Pro)
- qrcodescanner.barcodescanner.qrscanner.qrcodereader (App: QR Master)
- heartmonitor.healthtracker.fitness.pulse (App: Heart Check)

Conflict Check Rules:
❌ No shared primary+secondary keywords
❌ No similar domain patterns
❌ No identical tertiary keyword sequences
✅ Maximum similarity threshold: 40%
```

### Build.Gradle Marking System

```gradle
android {
    compileSdk 34

    defaultConfig {
        // 🔒 ASO-OPTIMIZED PACKAGE NAME - DO NOT CHANGE
        // ✅ AVAILABILITY CONFIRMED: US, GB, DE, JP, AU, CA, FR (Date: 2024-01-15)
        // 📊 ASO SCORE: 82/100 (Primary: photo+editor | Secondary: image+filter)
        // 🎯 TARGET KEYWORDS: photo editing, image filters, picture enhancement
        // 🚫 AI INSTRUCTION: This package name is LOCKED for ASO optimization
        applicationId "photoeditor.imagefilter.pictures.enhance"

        minSdk 24
        targetSdk 34
        versionCode 1
        versionName "1.0"
    }
}
```

## 🤖 AI Integration Rules

### AI Package Naming Guidelines

````markdown
## Rules for AI when creating Android apps:

### BEFORE Setting Package Name:

1. ✅ Check if applicationId already has ASO comment markers
2. ✅ If marked with 🔒 or "DO NOT CHANGE" → NEVER modify
3. ✅ If not marked → follow ASO package workflow below

### ASO Package Manual Creation Process:

1. 🎯 Identify app category and primary function
2. 🧠 AI manually creates 3-5 unique ASO-optimized package options
3. 📝 Each package uses different keyword combinations (NO templates)
4. 🔍 Run availability check script for all AI-created options
5. 📊 Calculate ASO scores for available packages
6. 🎉 Select highest-scoring available package
7. 🔒 Mark with protection comments in build.gradle

### Protection Comment Template:

```gradle
// 🔒 ASO-OPTIMIZED PACKAGE NAME - DO NOT CHANGE
// ✅ AVAILABILITY CONFIRMED: [regions] (Date: [YYYY-MM-DD])
// 📊 ASO SCORE: [score]/100 (Primary: [keywords] | Secondary: [keywords])
// 🎯 TARGET KEYWORDS: [keyword list]
// 🚫 AI INSTRUCTION: This package name is LOCKED for ASO optimization
applicationId "your.aso.optimized.package"
```
````

### Package Name Validation Checklist:

- [ ] Contains 2-4 relevant keywords
- [ ] No brand/company names in package
- [ ] Available in US, GB, DE markets minimum
- [ ] ASO score 75+ points
- [ ] No conflicts with portfolio packages
- [ ] Marked with protection comments

````

## 📈 ASO Monitoring & Analytics

### Package Performance Tracking
```markdown
## Post-Launch ASO Monitoring

Track Performance Metrics:
- 🔍 Organic search impressions for target keywords
- 📱 Install conversion rate from search
- 🏆 Keyword ranking positions (top 10 tracking)
- 📊 Competitor analysis for similar packages

Monthly ASO Review:
- Analyze keyword performance data
- Compare with competitor package strategies
- Identify optimization opportunities
- Plan package evolution for updates

Success KPIs:
- Target keywords in top 10: 70%+ target
- Organic search traffic: 40%+ of total installs
- Keyword ranking improvement: +5 positions monthly
- Search-to-install conversion: 15%+ target
````

## 🚀 Implementation Workflow

### Workflow Integration với Project Setup

```markdown
## Integration với .project-identity

Khi tạo Android project mới:

1. **Auto-detect app category** từ project description
2. **Generate ASO package suggestions** theo category
3. **Run availability validation** cho tất cả suggestions
4. **Present top 3 options** với ASO scores
5. **User selects preferred option** hoặc AI auto-selects highest score
6. **Lock package** với protection comments
7. **Update portfolio registry** với package mới
8. **Set up ASO monitoring** cho keywords

File cập nhật:

- build.gradle (:app) - với package được lock
- portfolio-packages.md - thêm package mới
- aso-keywords-tracking.md - setup monitoring
- .project-identity - ghi nhận ASO strategy
```

### Team Collaboration Guidelines

```markdown
## Team ASO Guidelines

For Multiple Developers:

- 🚫 NEVER change package name without ASO team approval
- ✅ Always check portfolio-packages.md before suggesting new names
- 📝 Document package reasoning in project documentation
- 🔍 Run availability check before proposing package names
- 📊 Include ASO score justification in proposals

ASO Review Process:

1. Developer proposes package với full analysis
2. ASO team validates availability và scoring
3. Marketing team approves keyword strategy
4. Technical lead confirms implementation
5. Package gets locked với protection comments
```

## 📁 File Structure cho ASO Workflow

```
instructions/
├── android-aso-package-workflow.md (this file)
├── aso-tools/
│   ├── package-availability-checker.sh
│   ├── aso-score-calculator.py
│   └── keyword-research-templates.md
└── tracking/
    ├── portfolio-packages.md
    ├── aso-keywords-tracking.md
    └── package-performance-reports.md
```

## 🎯 Success Stories Examples

```markdown
Real ASO Success Cases:

📱 QR Code Scanner App:

- Package: qrcodescanner.barcodescanner.qrscanner.qrcodereader
- ASO Score: 89/100
- Results: Top 3 for "QR scanner", "barcode reader", "QR code"
- Organic traffic: 78% of total installs

📸 Photo Editor App:

- Package: photoeditor.imagefilter.pictures.enhance
- ASO Score: 84/100
- Results: Top 5 for "photo editor", "image filter", "picture enhance"
- Organic traffic: 65% of total installs

❤️ Heart Monitor App:

- Package: heartmonitor.healthtracker.fitness.pulse
- ASO Score: 81/100
- Results: Top 8 for "heart monitor", "health tracker", "pulse check"
- Organic traffic: 52% of total installs
```

---

_Workflow này đảm bảo package name không chỉ technical sound mà còn ASO-optimized để maximize organic discovery và minimize account risks._
