# Telegram Notification System

## Overview

Hệ thống thông báo tự động qua Telegram cho việc hoàn thành công việc và báo cáo tiến độ

## Configuration Files

- **Workflow File**: `.cursor/rules/telegram-notification-workflow.mdc`
- **Python Notifier**: `./scripts/telegram_notifier.py`
- **Shell Wrapper**: `./scripts/notify.sh`

## Features

### Core Features

- **Auto Screenshot**: Tự động chụp màn hình khi hoàn thành task
- **Work Completion Report**: Báo cáo chi tiết về công việc đã hoàn thành
- **Proxy Support**: Hỗ trợ SOCKS5 proxy cho kết nối
- **Multi Platform**: Hỗ trợ macOS, Linux, Windows
- **Error Handling**: Xử lý lỗi và fallback mechanisms

### Configuration

- **Bot Token**: Configured in `telegram_notifier.py`
- **Chat ID**: Group chat for project notifications
- **Proxy Settings**: SOCKS5 proxy for secure connections
- **Screenshot Path**: `/tmp/screenshot_[timestamp].png`

## Usage Examples

### Basic Usage

```bash
./scripts/notify.sh "Database Setup"
```

### With Status and Details

```bash
./scripts/notify.sh "API Development" completed "All endpoints working"
```

### Without Screenshot

```bash
./scripts/notify.sh "Build Process" failed "Compilation error" --no-screenshot
```

## Integration Points

### Automatic Triggers

- **Project Completion**: Tự động gửi thông báo khi hoàn thành project
- **Milestone Reports**: Báo cáo khi đạt được milestone quan trọng
- **Error Notifications**: Thông báo khi gặp lỗi nghiêm trọng
- **Daily Progress**: Báo cáo tiến độ hàng ngày (optional)

### Automation Triggers

- Khi hoàn thành một phase trong workflow
- Khi deploy thành công
- Khi test coverage đạt target
- Khi build production thành công
- Khi phát hiện lỗi critical

## Report Format

### Standard Report Structure

- **Task Name**: Tên công việc đã hoàn thành
- **Status**: `completed|failed|in_progress|started`
- **Timestamp**: Thời gian hoàn thành
- **Details**: Mô tả chi tiết về công việc
- **Screenshot**: Ảnh chụp màn hình (nếu có)
- **Project Context**: Thông tin về dự án hiện tại

### Example Report

```
📋 Task: API Development
✅ Status: completed
⏰ Time: 2024-12-19 14:30:00
📝 Details: All endpoints working
📊 Project: Base-AI-Project
📱 Screenshot: attached
```
