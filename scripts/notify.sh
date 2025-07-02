#!/bin/bash

# Telegram Notification Wrapper Script
# Usage: ./notify.sh "Task Name" [status] [details] [--no-screenshot]

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PYTHON_SCRIPT="$SCRIPT_DIR/telegram_notifier.py"

# Check if Python script exists
if [ ! -f "$PYTHON_SCRIPT" ]; then
    echo "❌ Error: telegram_notifier.py not found at $PYTHON_SCRIPT"
    exit 1
fi

# Check if at least task name is provided
if [ $# -lt 1 ]; then
    echo "Usage: $0 \"Task Name\" [status] [details] [--no-screenshot]"
    echo "Status options: completed, failed, in_progress, started (default: completed)"
    echo "Examples:"
    echo "  $0 \"Database Setup\""
    echo "  $0 \"API Development\" completed \"All endpoints working\""
    echo "  $0 \"Build Process\" failed \"Compilation error in main.go\" --no-screenshot"
    exit 1
fi

# Set default status if not provided
TASK_NAME="$1"
STATUS="${2:-completed}"
DETAILS="$3"
SCREENSHOT_FLAG="$4"

# Check if Python 3 is available
if command -v python3 &> /dev/null; then
    PYTHON_CMD="python3"
elif command -v python &> /dev/null; then
    PYTHON_CMD="python"
else
    echo "❌ Error: Python not found. Please install Python 3."
    exit 1
fi

# Install required packages if not available
echo "📦 Checking Python dependencies..."
$PYTHON_CMD -c "import requests" 2>/dev/null || {
    echo "📦 Installing requests package..."
    pip3 install requests || pip install requests || {
        echo "❌ Error: Failed to install requests package"
        exit 1
    }
}

# Run the Python script
echo "📤 Sending notification to Telegram..."
if [ -n "$SCREENSHOT_FLAG" ]; then
    $PYTHON_CMD "$PYTHON_SCRIPT" "$TASK_NAME" "$STATUS" "$DETAILS" "$SCREENSHOT_FLAG"
else
    $PYTHON_CMD "$PYTHON_SCRIPT" "$TASK_NAME" "$STATUS" "$DETAILS"
fi