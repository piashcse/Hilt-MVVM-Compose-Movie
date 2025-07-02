# Mobile Utility App Rules (.appdexer system)

## App Detection & Classification

### Auto-Detection Triggers:

```markdown
When user mentions:
□ "health app", "fitness app", "heart monitor", "nutrition tracker"
□ "photo editing", "image filter", "photo enhancement", "AI photo"
□ "office app", "document tool", "productivity app", "PDF editor"
□ "utility app", "tool app", "Android app", "mobile app"

→ Automatically classify as Mobile Utility App
→ Apply mobile-utility-workflow.mdc rules
→ Switch to Android-first development mode
```

### Classification Logic:

```javascript
function classifyApp(userInput) {
  const healthKeywords = [
    "health",
    "fitness",
    "heart",
    "nutrition",
    "medical",
    "wellness",
  ];
  const photoKeywords = [
    "photo",
    "image",
    "filter",
    "editing",
    "camera",
    "enhance",
  ];
  const officeKeywords = [
    "office",
    "document",
    "productivity",
    "PDF",
    "text",
    "presentation",
  ];

  if (containsAny(userInput, healthKeywords)) return "HEALTH_APP";
  if (containsAny(userInput, photoKeywords)) return "PHOTO_APP";
  if (containsAny(userInput, officeKeywords)) return "OFFICE_APP";

  return "GENERAL_APP";
}
```

## Revenue-First Architecture Rules

### Automatic Revenue Integration:

```markdown
For Mobile Utility Apps, auto-enable:
□ Firebase Analytics tracking
□ Google AdMob integration  
□ Google Play Billing setup
□ Firebase Remote Config for ad strategies
□ Regional revenue optimization
□ A/B testing framework
□ User retention analytics
□ LTV tracking systems
```

### Smart AI Cost Management:

```markdown
Auto-implement cost optimization:
□ Batch API requests (10x efficiency)
□ Local caching system (80% reduction in calls)
□ Smart refresh timing (weekly/bi-weekly)
□ Usage prediction algorithms
□ Fallback content systems
□ Cost tracking per user
□ Budget alerting mechanisms
```

## Platform Priority Rules

### Android-First Development:

```markdown
Primary Platform (Android):
□ Kotlin + Jetpack Compose
□ Clean Architecture + MVVM
□ Room database + Repository pattern
□ Retrofit + OkHttp networking
□ Hilt dependency injection
□ Firebase integration suite
□ Google Play services

Secondary Platform (iOS):
□ Swift + SwiftUI adaptation
□ Core Data alignment with Room
□ URLSession networking layer
□ Firebase iOS SDK integration
□ StoreKit billing alignment
□ Human Interface Guidelines compliance
```

### Cross-Platform Compatibility:

```markdown
Shared Components:
□ Same Firebase backend configuration
□ Identical AI API endpoints
□ Unified analytics event tracking
□ Consistent remote config parameters
□ Synchronized user data models
□ Cross-platform feature parity
□ Unified testing strategies
```

## TDD Integration Rules

### Test-First Development Enforcement:

```markdown
Before any implementation code:
□ Unit tests for business logic
□ Integration tests for data flow
□ UI tests for critical user flows
□ AI integration tests with fallbacks
□ Revenue feature tests (ads + IAP)
□ Regional strategy tests
□ Performance benchmark tests
□ Accessibility compliance tests
```

### Quality Gates:

```markdown
Code cannot be committed without:
□ 85% unit test coverage minimum
□ 70% integration test coverage minimum  
□ 100% critical user flow coverage
□ All tests passing on CI/CD
□ Performance benchmarks met
□ Security scan clearance
□ Cross-platform parity validation
```

## Regional Strategy Implementation

### Automatic Regional Detection:

```markdown
Auto-configure based on target markets:

High Revenue Markets (Aggressive Ads):
□ Vietnam, India, Brazil, Indonesia
□ Max ad frequency, persistent banners
□ Rewarded ads for premium features
□ Higher tolerance for user friction

Premium Markets (IAP Focus):
□ United States, Japan, Germany, UK
□ Minimal ads, premium feature focus
□ Subscription optimization
□ High-quality user experience priority

Balanced Markets (Retention Focus):
□ European Union, Canada, Australia
□ Balanced ads and user experience
□ Long-term retention optimization
□ Moderate monetization approach
```

### Firebase Remote Config Auto-Setup:

```json
{
  "auto_generated_config": {
    "default_strategy": "retention_focused",
    "regional_overrides": {
      "VN": "max_revenue",
      "US": "iap_conversion",
      "EU": "retention_focused"
    },
    "manual_control": true,
    "a_b_testing": true,
    "real_time_switching": true
  }
}
```

## Smart Question System

### Context-Aware Questioning:

```markdown
Health Apps (Max 5 Questions):

1. "Primary health goal?" [Auto-detect: weight loss, fitness, wellness, medical]
2. "Activity level?" [Auto-detect: beginner, intermediate, advanced, professional]
3. "Interaction style?" [Auto-detect: daily, weekly, on-demand, automated]
4. "Most valuable feature?" [Auto-detect: tracking, insights, recommendations, community]
5. "Premium willingness?" [Auto-detect: yes, maybe, prefer free with ads]

Photo Apps (Max 5 Questions):

1. "Photo type?" [Auto-detect: portrait, landscape, food, social, professional]
2. "Experience level?" [Auto-detect: beginner, hobbyist, semi-pro, professional]
3. "Workflow preference?" [Auto-detect: quick, detailed, batch, auto-enhance]
4. "Important features?" [Auto-detect: filters, advanced tools, AI enhancement, organization]
5. "Budget preference?" [Auto-detect: free only, small monthly, one-time, professional]

Office Apps (Max 5 Questions):

1. "Work type?" [Auto-detect: documents, presentations, data, project management]
2. "Collaboration needs?" [Auto-detect: solo, small team, large team, client sharing]
3. "Work style?" [Auto-detect: templates, custom, AI assistance, manual control]
4. "Productivity features?" [Auto-detect: automation, organization, collaboration, analytics]
5. "Business budget?" [Auto-detect: free tier, basic paid, professional, enterprise]
```

### Smart Answer Processing:

```markdown
Auto-Analysis of Responses:
□ User persona generation
□ Revenue model recommendation
□ Feature priority matrix creation
□ Technical requirement inference
□ Regional strategy suggestion
□ AI integration approach
□ Testing strategy formulation
```

## AI Integration Automation

### Smart AI Feature Detection:

```markdown
Health Apps - Auto AI Features:
□ Weekly health insights generation (batch of 10)
□ Personalized exercise recommendations
□ Smart medication reminders
□ Health risk predictions
□ Nutrition suggestions

Photo Apps - Auto AI Features:
□ Auto-enhance suggestions per photo
□ Style recommendations based on content
□ Batch processing suggestions
□ Smart crop/composition guidance
□ Color palette recommendations

Office Apps - Auto AI Features:
□ Document structure suggestions
□ Smart templates based on patterns
□ Productivity insights from usage
□ Task prioritization suggestions
□ Meeting summary extraction
```

### Cost Optimization Automation:

```markdown
Auto-Implement Efficiency:
□ Batch API calls (10x cost reduction)
□ Intelligent caching (80% call reduction)
□ Usage prediction (pre-generate for active users)
□ Smart refresh timing (weekly/bi-weekly)
□ Fallback content systems
□ Budget monitoring and alerts
□ Performance optimization
```

## Development Automation Rules

### Project Setup Automation:

```markdown
Auto-Generate Project Structure:
□ Android: Kotlin + Compose + Clean Architecture
□ iOS: Swift + SwiftUI + Clean Architecture  
□ Shared: Firebase configuration
□ Database: Room (Android) + Core Data (iOS)
□ Networking: Retrofit (Android) + URLSession (iOS)
□ Testing: JUnit5 + Espresso (Android) + XCTest (iOS)
□ Analytics: Firebase Analytics + Crashlytics
□ Revenue: AdMob + Play Billing + StoreKit
```

### Feature Implementation Automation:

```markdown
Auto-Implement Core Features:
□ User onboarding with 5 strategic questions
□ Core app functionality based on category
□ AI integration with smart caching
□ Revenue features (ads + IAP)
□ Analytics and tracking
□ Regional strategy configuration
□ Performance optimization
□ Security implementation
```

## Quality Assurance Rules

### Automated Testing Requirements:

```markdown
Auto-Generate Test Suites:
□ Unit tests for all business logic
□ Integration tests for data layer
□ UI tests for critical flows
□ AI integration tests with fallbacks
□ Revenue feature tests
□ Regional strategy tests
□ Performance benchmark tests
□ Accessibility tests
□ Security tests
□ Cross-platform parity tests
```

### Performance Benchmarks:

```markdown
Auto-Enforce Performance Standards:
□ App startup time < 3 seconds
□ UI responsiveness < 100ms
□ Memory usage < 150MB
□ Battery efficiency optimized
□ Network usage minimized
□ Storage usage reasonable
□ Crash rate < 1%
□ ANR rate < 0.5%
```

## Deployment Automation

### Store Preparation Automation:

```markdown
Auto-Generate Store Assets:
□ App icons (multiple sizes)
□ Screenshots (multiple devices)
□ Store listing text (title, description, keywords)
□ Privacy policy (based on data usage)
□ Terms of service (based on features)
□ App store optimization (ASO)
□ Release notes generation
□ Compliance documentation
```

### Release Pipeline Automation:

```markdown
Auto-Configure CI/CD:
□ Build automation (Android + iOS)
□ Test automation (all categories)
□ Code quality checks
□ Security scanning
□ Performance validation
□ Store submission preparation
□ Beta testing distribution
□ Production release automation
```

## Monitoring and Analytics

### Auto-Implement Tracking:

```markdown
Revenue Tracking:
□ ARPU per region
□ ARPDAU tracking
□ Ad revenue vs IAP revenue
□ LTV calculation
□ Conversion rate monitoring
□ Churn rate analysis
□ Regional performance comparison

User Behavior Tracking:
□ Feature adoption rates
□ Session duration and frequency
□ User journey analysis
□ Retention metrics (D1, D7, D30)
□ Engagement scoring
□ Support interaction analysis

Technical Performance Tracking:
□ App performance metrics
□ Crash and ANR tracking
□ API cost monitoring
□ Test coverage tracking
□ Security incident monitoring
□ Cross-platform parity validation
```

## Maintenance Automation

### Continuous Optimization:

```markdown
Auto-Schedule Tasks:
□ Weekly revenue performance analysis
□ A/B testing results evaluation  
□ User feedback analysis
□ AI API cost optimization
□ Performance benchmark review
□ Security updates
□ Regional strategy adjustment
□ Cross-platform feature sync
```

### Update Management:

```markdown
Auto-Handle Updates:
□ Dependency updates
□ Security patches
□ Performance optimizations
□ Feature enhancements
□ Bug fixes
□ Regional configuration updates
□ AI model improvements
□ User experience enhancements
```
