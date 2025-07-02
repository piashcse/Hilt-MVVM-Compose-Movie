#!/bin/bash

# Script thực hiện backup file thay vì xóa trực tiếp
# Usage: ./backup_file.sh path/to/file_or_directory "Lý do xóa" "Người thực hiện"

# Kiểm tra tham số đầu vào
if [ -z "$1" ]; then
  echo "❌ Thiếu đường dẫn file/thư mục cần backup!"
  echo "Cách sử dụng: ./backup_file.sh path/to/file_or_directory \"Lý do xóa\" \"Người thực hiện\""
  exit 1
fi

# Lấy đường dẫn đầy đủ
FILE_PATH=$(realpath "$1")
REASON="${2:-"Không có lý do cụ thể"}"
USER="${3:-"$(whoami)"}"
DATE=$(date +%Y-%m-%d)
DATETIME=$(date '+%Y-%m-%d - %H:%M:%S')
BACKUP_DIR="_backups/$DATE"
BACKUP_LOG="_backups/backup_log.md"
WORKSPACE_ROOT=$(git rev-parse --show-toplevel 2>/dev/null || pwd)

# Kiểm tra file/thư mục tồn tại
if [ ! -e "$FILE_PATH" ]; then
  echo "❌ File/thư mục không tồn tại: $FILE_PATH"
  exit 1
fi

# Tạo thư mục backup nếu chưa tồn tại
mkdir -p "$WORKSPACE_ROOT/$BACKUP_DIR"
mkdir -p "$WORKSPACE_ROOT/$BACKUP_DIR/$(dirname "$(realpath --relative-to="$WORKSPACE_ROOT" "$FILE_PATH")")"

# Đường dẫn tương đối từ workspace root
RELATIVE_PATH=$(realpath --relative-to="$WORKSPACE_ROOT" "$FILE_PATH")
BACKUP_PATH="$BACKUP_DIR/$RELATIVE_PATH"

# Thực hiện backup
if [ -d "$FILE_PATH" ]; then
  # Backup thư mục
  echo "📦 Đang backup thư mục: $RELATIVE_PATH"
  cp -r "$FILE_PATH" "$WORKSPACE_ROOT/$BACKUP_DIR/$(dirname "$RELATIVE_PATH")/"
  echo "✅ Backup thư mục thành công!"
else
  # Backup file
  echo "📄 Đang backup file: $RELATIVE_PATH"
  cp "$FILE_PATH" "$WORKSPACE_ROOT/$BACKUP_DIR/$(dirname "$RELATIVE_PATH")/"
  echo "✅ Backup file thành công!"
fi

# Cập nhật log
LOG_ENTRY="## $DATETIME\n- File: \`$RELATIVE_PATH\`\n- Backup: \`$BACKUP_PATH\`\n- Lý do: $REASON\n- Người thực hiện: $USER\n\n"

if [ -f "$WORKSPACE_ROOT/$BACKUP_LOG" ]; then
  # Thêm vào đầu file log nếu file đã tồn tại
  echo -e "$LOG_ENTRY$(cat "$WORKSPACE_ROOT/$BACKUP_LOG")" > "$WORKSPACE_ROOT/$BACKUP_LOG"
else
  # Tạo file log mới nếu chưa tồn tại
  echo -e "$LOG_ENTRY" > "$WORKSPACE_ROOT/$BACKUP_LOG"
fi

echo "📝 Đã cập nhật log tại: $BACKUP_LOG"
echo "🔄 Bạn có thể xóa file gốc sau khi đã kiểm tra backup"
echo "💡 Để khôi phục: cp \"$WORKSPACE_ROOT/$BACKUP_PATH\" \"$FILE_PATH\""

# Kết quả thành công
exit 0 