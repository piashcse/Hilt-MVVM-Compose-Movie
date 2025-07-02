# Backend Structure Document

<!-- Template cho tài liệu Backend Structure -->

## 1. Kiến Trúc Tổng Quan

### 1.1 Architecture Pattern

- **Pattern**: [MVC/Clean Architecture/Hexagonal/etc]
- **Mô tả**: [Mô tả ngắn gọn về pattern được sử dụng]
- **Lý do chọn**: [Lý do tại sao pattern này được chọn]

### 1.2 Cấu Trúc Thư Mục

```
src/
├── api/            # API routes và controllers
├── config/         # Cấu hình ứng dụng
├── middleware/     # Middleware functions
├── models/         # Data models
├── services/       # Business logic
├── utils/          # Utility functions
└── index.js        # Entry point
```

### 1.3 Flow Xử Lý Request

[Mô tả flow của một request từ khi nhận đến khi trả về response]

1. Request đến API endpoint
2. Middleware xác thực và xử lý trước
3. Controller nhận và xử lý request
4. Service thực hiện business logic
5. Model tương tác với database
6. Response được tạo và trả về

## 2. Database Schema

### 2.1 Entity Relationship Diagram

```
[ERD diagram có thể được thêm vào đây, sử dụng Mermaid hoặc syntax khác]
```

### 2.2 Bảng: [Tên Bảng 1]

- **Mô tả**: [Mô tả mục đích của bảng]
- **Columns**:

| Column Name | Data Type | Constraints   | Description |
| ----------- | --------- | ------------- | ----------- |
| id          | [type]    | [constraints] | [mô tả]     |
| field1      | [type]    | [constraints] | [mô tả]     |
| field2      | [type]    | [constraints] | [mô tả]     |
| created_at  | [type]    | [constraints] | [mô tả]     |
| updated_at  | [type]    | [constraints] | [mô tả]     |

- **Indexes**:
  - [index1]: [columns] - [type] - [mô tả]
  - [index2]: [columns] - [type] - [mô tả]
- **Relationships**:
  - [relationship1]: [description]
  - [relationship2]: [description]

### 2.3 Bảng: [Tên Bảng 2]

- **Mô tả**: [Mô tả mục đích của bảng]
- **Columns**:

| Column Name | Data Type | Constraints   | Description |
| ----------- | --------- | ------------- | ----------- |
| id          | [type]    | [constraints] | [mô tả]     |
| field1      | [type]    | [constraints] | [mô tả]     |
| field2      | [type]    | [constraints] | [mô tả]     |
| created_at  | [type]    | [constraints] | [mô tả]     |
| updated_at  | [type]    | [constraints] | [mô tả]     |

- **Indexes**:
  - [index1]: [columns] - [type] - [mô tả]
  - [index2]: [columns] - [type] - [mô tả]
- **Relationships**:
  - [relationship1]: [description]
  - [relationship2]: [description]

<!-- Tiếp tục với các bảng khác... -->

## 3. API Endpoints

### 3.1 Authentication Endpoints

#### `POST /api/auth/login`

- **Description**: [Mô tả chức năng]
- **Request Body**:
  ```json
  {
    "email": "string",
    "password": "string"
  }
  ```
- **Response**:
  ```json
  {
    "token": "string",
    "user": {
      "id": "string",
      "name": "string",
      "email": "string",
      "role": "string"
    }
  }
  ```
- **Status Codes**:
  - 200 OK: Success
  - 401 Unauthorized: Invalid credentials
  - 500 Internal Server Error: Server error

#### `POST /api/auth/register`

- **Description**: [Mô tả chức năng]
- **Request Body**:
  ```json
  {
    "name": "string",
    "email": "string",
    "password": "string"
  }
  ```
- **Response**:
  ```json
  {
    "token": "string",
    "user": {
      "id": "string",
      "name": "string",
      "email": "string",
      "role": "string"
    }
  }
  ```
- **Status Codes**:
  - 201 Created: User created successfully
  - 400 Bad Request: Invalid input
  - 409 Conflict: Email already exists
  - 500 Internal Server Error: Server error

### 3.2 Resource Endpoints

#### `GET /api/resources`

- **Description**: [Mô tả chức năng]
- **Query Parameters**:
  - `page`: number (optional) - [mô tả]
  - `limit`: number (optional) - [mô tả]
  - `sort`: string (optional) - [mô tả]
- **Response**:
  ```json
  {
    "data": [
      {
        "id": "string",
        "name": "string",
        "description": "string",
        "created_at": "timestamp"
      }
    ],
    "pagination": {
      "total": "number",
      "page": "number",
      "limit": "number",
      "pages": "number"
    }
  }
  ```
- **Status Codes**:
  - 200 OK: Success
  - 401 Unauthorized: Not authenticated
  - 500 Internal Server Error: Server error

#### `GET /api/resources/:id`

- **Description**: [Mô tả chức năng]
- **Path Parameters**:
  - `id`: string - [mô tả]
- **Response**:
  ```json
  {
    "id": "string",
    "name": "string",
    "description": "string",
    "created_at": "timestamp",
    "updated_at": "timestamp"
  }
  ```
- **Status Codes**:
  - 200 OK: Success
  - 401 Unauthorized: Not authenticated
  - 404 Not Found: Resource not found
  - 500 Internal Server Error: Server error

<!-- Tiếp tục với các endpoints khác... -->

## 4. Authentication & Authorization

### 4.1 Authentication Strategy

- **Method**: [JWT/Session/OAuth/etc]
- **Token Storage**: [Mô tả cách lưu trữ token]
- **Token Expiration**: [Mô tả policy về thời gian hết hạn]
- **Refresh Strategy**: [Mô tả cách refresh tokens]

### 4.2 Authorization Model

- **Roles**: [Liệt kê và mô tả các roles]
  - [role1]: [mô tả quyền]
  - [role2]: [mô tả quyền]
- **Permissions**: [Mô tả hệ thống permissions]
- **Access Control Logic**: [Mô tả cách kiểm soát quyền truy cập]

## 5. Middleware Stack

### 5.1 Core Middleware

- **[middleware1]**: [Mô tả chức năng và cách cấu hình]
- **[middleware2]**: [Mô tả chức năng và cách cấu hình]
- **[middleware3]**: [Mô tả chức năng và cách cấu hình]

### 5.2 Custom Middleware

- **[custom middleware1]**: [Mô tả chức năng và cách sử dụng]
- **[custom middleware2]**: [Mô tả chức năng và cách sử dụng]

## 6. Error Handling

### 6.1 Error Structure

```json
{
  "error": {
    "code": "string",
    "message": "string",
    "details": {}
  }
}
```

### 6.2 Error Types

- **Validation Errors**: [Mô tả cách xử lý]
- **Authentication Errors**: [Mô tả cách xử lý]
- **Authorization Errors**: [Mô tả cách xử lý]
- **Not Found Errors**: [Mô tả cách xử lý]
- **Internal Server Errors**: [Mô tả cách xử lý]

### 6.3 Logging Strategy

- **Error Log Levels**: [Mô tả các levels và khi nào sử dụng]
- **Log Storage**: [Mô tả nơi lưu trữ logs]
- **Sensitive Information Handling**: [Mô tả cách xử lý thông tin nhạy cảm]

## 7. Caching Strategy

### 7.1 Cache Layers

- **Database Query Cache**: [Mô tả chiến lược]
- **API Response Cache**: [Mô tả chiến lược]
- **In-Memory Data Cache**: [Mô tả chiến lược]

### 7.2 Cache Invalidation

- [Mô tả chiến lược vô hiệu hóa cache]
- [Triggers cho việc vô hiệu hóa]

## 8. Background Jobs & Scheduling

### 8.1 Job Types

- **[job type 1]**: [Mô tả mục đích và logic]
- **[job type 2]**: [Mô tả mục đích và logic]

### 8.2 Scheduling Strategy

- **Periodic Jobs**: [Mô tả các jobs định kỳ]
- **Event-Triggered Jobs**: [Mô tả các jobs trigger bởi sự kiện]

## 9. File Storage

### 9.1 Storage Locations

- **[location 1]**: [Mô tả loại files và cách tổ chức]
- **[location 2]**: [Mô tả loại files và cách tổ chức]

### 9.2 File Access Control

- [Mô tả cách kiểm soát quyền truy cập files]
- [Chiến lược URL generation cho files]

## 10. Deployment & Environments

### 10.1 Environment Configuration

- **Development**: [Mô tả cấu hình]
- **Staging**: [Mô tả cấu hình]
- **Production**: [Mô tả cấu hình]

### 10.2 Environment Variables

| Variable | Purpose | Example   |
| -------- | ------- | --------- |
| [name]   | [mô tả] | [example] |
| [name]   | [mô tả] | [example] |
| [name]   | [mô tả] | [example] |

### 10.3 Deployment Process

- [Mô tả quy trình deployment]
- [Chiến lược rollback]
