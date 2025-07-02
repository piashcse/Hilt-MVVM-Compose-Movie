# Context7 Auto-Check Demo Example

## Scenario: Adding Authentication to React App

### User Request
```
"Tôi muốn thêm authentication cho app React này"
```

### Auto Context Check Process

#### Step 1: Project Analysis (Automatic)
```javascript
// AI automatically scans package.json
{
  "name": "my-react-app",
  "version": "0.1.0",
  "dependencies": {
    "react": "^18.2.0",
    "react-dom": "^18.2.0",
    "react-router-dom": "^6.8.0",
    "@tanstack/react-query": "^4.24.0",
    "axios": "^1.3.0",
    "tailwindcss": "^3.2.0"
  }
}

// Detected Tech Stack:
// - React 18.2.0 (latest stable)
// - React Router 6.8.0 (data router era)
// - TanStack Query 4.24.0 (server state)
// - Axios 1.3.0 (HTTP client)
// - Tailwind CSS 3.2.0 (styling)
```

#### Step 2: Context7 Queries (Automatic)
```bash
# AI automatically performs these queries:

1. resolve-library-id: "react authentication"
   → Returns: Multiple auth libraries for React

2. resolve-library-id: "react router authentication"
   → Returns: Router-based auth patterns

3. resolve-library-id: "tanstack react-query authentication"
   → Returns: Server state auth patterns

4. resolve-library-id: "jwt authentication react"
   → Returns: JWT handling in React
```

#### Step 3: Context Resolution Results
```markdown
# Context7 Query Results:

## Authentication Libraries Found:
1. **Auth0 React SDK** (/auth0/auth0-react)
   - Trust Score: 9.8
   - Code Snippets: 1,200+
   - Best for: Enterprise apps, social login

2. **Firebase Auth** (/firebase/firebase-js-sdk)
   - Trust Score: 9.9
   - Code Snippets: 3,500+
   - Best for: Google ecosystem, real-time apps

3. **Supabase Auth** (/supabase/supabase-js)
   - Trust Score: 9.5
   - Code Snippets: 800+
   - Best for: Open source, PostgreSQL backend

4. **NextAuth.js** (/nextauthjs/next-auth)
   - Trust Score: 9.7
   - Code Snippets: 2,100+
   - Best for: Next.js apps, custom providers

## React Router 6 Auth Patterns:
- Protected routes với loader functions
- Authentication context với router state
- Redirect handling với useNavigate
- Route-based role permissions

## TanStack Query Auth Integration:
- Query invalidation on auth state change
- Automatic token refresh
- Optimistic updates cho user data
- Error boundary cho auth failures
```

#### Step 4: Context-Aware Recommendations
```markdown
# AI Response với Context:

Dựa trên tech stack hiện tại của bạn (React 18 + Router 6 + TanStack Query), 
tôi recommend các approach sau cho authentication:

## Option 1: Supabase Auth (Recommended)
**Lý do**: 
- Tích hợp tốt với TanStack Query
- Built-in PostgreSQL backend
- Real-time subscriptions
- Open source và cost-effective

**Implementation với current stack**:
```javascript
// 1. Install Supabase
npm install @supabase/supabase-js

// 2. Auth Context với React 18 patterns
import { createContext, useContext, useEffect, useState } from 'react';
import { supabase } from './supabase';

const AuthContext = createContext({});

export function AuthProvider({ children }) {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    // Get initial session
    supabase.auth.getSession().then(({ data: { session } }) => {
      setUser(session?.user ?? null);
      setLoading(false);
    });

    // Listen for auth changes
    const { data: { subscription } } = supabase.auth.onAuthStateChange(
      (event, session) => {
        setUser(session?.user ?? null);
        setLoading(false);
      }
    );

    return () => subscription.unsubscribe();
  }, []);

  return (
    <AuthContext.Provider value={{ user, loading }}>
      {children}
    </AuthContext.Provider>
  );
}

// 3. Protected Route với React Router 6
import { Navigate, useLocation } from 'react-router-dom';
import { useAuth } from './AuthContext';

function ProtectedRoute({ children }) {
  const { user, loading } = useAuth();
  const location = useLocation();

  if (loading) {
    return <div>Loading...</div>;
  }

  if (!user) {
    return <Navigate to="/login" state={{ from: location }} replace />;
  }

  return children;
}

// 4. TanStack Query integration
import { useQuery, useQueryClient } from '@tanstack/react-query';

function useUserProfile() {
  const { user } = useAuth();
  const queryClient = useQueryClient();

  return useQuery({
    queryKey: ['user-profile', user?.id],
    queryFn: () => fetchUserProfile(user.id),
    enabled: !!user,
    onError: (error) => {
      if (error.status === 401) {
        queryClient.clear();
        supabase.auth.signOut();
      }
    }
  });
}
```

## Option 2: Auth0 React SDK
**Lý do**:
- Enterprise-grade security
- Extensive social providers
- Advanced features (MFA, SSO)
- Great documentation

**Trade-offs**:
- More expensive
- Overkill cho simple apps
- Additional vendor dependency

## Implementation Plan

### Phase 1: Setup Authentication (2-3 hours)
1. Install và configure Supabase
2. Create AuthContext với React 18 patterns
3. Setup protected routes với Router 6
4. Implement login/logout flows

### Phase 2: Integration với Existing Code (1-2 hours)
1. Integrate với TanStack Query
2. Add user state management
3. Handle authentication errors
4. Update API calls với auth headers

### Phase 3: UI Components (2-3 hours)
1. Create login/register forms với Tailwind
2. Add user profile components
3. Implement loading states
4. Add error handling UI

### Phase 4: Testing & Polish (1-2 hours)
1. Test authentication flows
2. Handle edge cases
3. Add proper error messages
4. Optimize performance

## Next Steps
Bạn muốn tôi implement option nào? Tôi có thể:
1. Setup Supabase auth với current stack
2. Create Auth0 integration
3. Build custom JWT solution
4. Compare options chi tiết hơn
```

### Memory Bank Update (Automatic)
```markdown
# Memory Bank Update: Auth Implementation Session

## Context Detected
- **Project Type**: React SPA
- **React Version**: 18.2.0 (latest stable)
- **Router**: React Router 6.8.0 (data router)
- **State Management**: TanStack Query 4.24.0
- **HTTP Client**: Axios 1.3.0
- **Styling**: Tailwind CSS 3.2.0

## Context7 Libraries Queried
1. ✅ Supabase Auth (/supabase/supabase-js)
2. ✅ Auth0 React (/auth0/auth0-react)
3. ✅ Firebase Auth (/firebase/firebase-js-sdk)
4. ✅ React Router Auth Patterns
5. ✅ TanStack Query Auth Integration

## Recommendations Applied
- Suggested Supabase for current stack
- Provided React 18 compatible patterns
- Integrated với Router 6 data loading
- Included TanStack Query error handling
- Used Tailwind for UI components

## Implementation Plan Generated
- Phase-based approach
- Time estimates included
- Stack-specific considerations
- Performance optimizations

## Cache Status
- **Auth Context**: Cached for 24h
- **React Patterns**: Fresh
- **Router Integration**: Fresh
- **Query Integration**: Fresh
```

## Benefits of Auto Context Check

### Without Context7 Auto-Check
```
User: "Tôi muốn thêm authentication"

AI Response (Generic):
"Bạn có thể sử dụng:
1. JWT tokens
2. Session-based auth
3. OAuth providers
4. Custom authentication

Bạn muốn implement cách nào?"

❌ Không biết tech stack hiện tại
❌ Suggestions không specific
❌ Không có implementation details
❌ Không consider existing patterns
```

### With Context7 Auto-Check
```
User: "Tôi muốn thêm authentication"

AI Response (Context-Aware):
"Dựa trên React 18 + Router 6 + TanStack Query stack của bạn,
tôi recommend Supabase Auth vì:
- Tích hợp tốt với TanStack Query
- Compatible với Router 6 data loading
- Provides real-time features
- Cost-effective cho startup

Tôi có thể implement ngay với:
- React 18 Suspense boundaries
- Router 6 protected routes
- Query invalidation on auth change
- Tailwind UI components

Bạn có muốn tôi bắt đầu setup không?"

✅ Hiểu rõ tech stack
✅ Specific recommendations
✅ Ready-to-use implementation
✅ Consider existing patterns
```

## Performance Comparison

### Traditional Approach
```
1. User asks question → 0s
2. AI provides generic answer → 2s
3. User clarifies tech stack → 30s
4. AI researches specific solution → 60s
5. AI provides implementation → 30s

Total Time: ~2 minutes
Context Accuracy: 60%
Implementation Quality: Medium
```

### Context7 Auto-Check Approach
```
1. User asks question → 0s
2. Auto context check → 3s
3. Context7 queries → 2s
4. AI provides specific solution → 5s

Total Time: ~10 seconds
Context Accuracy: 95%
Implementation Quality: High
```

## Error Scenarios & Handling

### Scenario 1: Context7 API Down
```
User: "Thêm authentication cho React app"

→ Auto context check fails
→ Use cached context (if available)
→ Fallback to basic React auth patterns
→ Warn user about limited context

AI Response:
"⚠️ Context service temporarily unavailable.
Sử dụng cached knowledge cho React auth.
Recommendations có thể không up-to-date.

Dựa trên cached context:
- React version: Unknown (assume latest)
- Suggest popular auth solutions
- Provide generic implementation"
```

### Scenario 2: Unknown Tech Stack
```
User: "Thêm auth cho app custom framework"

→ Auto context check finds unknown patterns
→ Query Context7 với generic terms
→ Provide framework-agnostic solutions
→ Ask for clarification

AI Response:
"Tôi detect custom framework không quen thuộc.
Có thể bạn clarify:
1. Framework/library nào đang dùng?
2. Backend technology?
3. Existing auth infrastructure?

Trong khi đó, đây là generic auth approaches..."
```

### Scenario 3: Conflicting Dependencies
```
Detected: React 16 + React Router 5 + old patterns

→ Context check reveals compatibility issues
→ Suggest upgrade path
→ Provide migration guide
→ Offer backward-compatible solutions

AI Response:
"⚠️ Detected legacy stack (React 16 + Router 5).
Recommend upgrade cho better auth patterns:

**Option 1: Upgrade Stack**
- React 16 → 18 (hooks, suspense)
- Router 5 → 6 (data loading)
- Modern auth patterns

**Option 2: Legacy-Compatible Auth**
- Class component patterns
- Router 5 route guards
- Manual state management

Which approach bạn prefer?"
```

Workflow này đảm bảo AI luôn có context cập nhật và cung cấp solutions phù hợp với tech stack hiện tại của người dùng.