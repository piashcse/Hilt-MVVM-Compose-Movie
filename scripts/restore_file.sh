#!/bin/bash

# Script phục hồi file từ backup
# Usage: ./restore_file.sh path/to/backup_file path/to/destination_file

# Kiểm tra tham số đầu vào
if [ -z "$1" ] || [ -z "$2" ]; then
  echo "❌ Thiếu tham số!"
  echo "Cách sử dụng: ./restore_file.sh path/to/backup_file path/to/destination_file"
  echo "Hoặc: ./restore_file.sh find \"tên file cần tìm\" - để tìm kiếm file trong backup"
  exit 1
fi

WORKSPACE_ROOT=$(git rev-parse --show-toplevel 2>/dev/null || pwd)
BACKUP_LOG="$WORKSPACE_ROOT/_backups/backup_log.md"
BACKUP_DIR="$WORKSPACE_ROOT/_backups"

# Tìm kiếm file trong backup
if [ "$1" = "find" ] && [ ! -z "$2" ]; then
  echo "🔍 Đang tìm kiếm file có tên \"$2\" trong backup..."
  
  if [ ! -d "$BACKUP_DIR" ]; then
    echo "❌ Thư mục backup không tồn tại: $BACKUP_DIR"
    exit 1
  fi
  
  # Tìm kiếm trong thư mục backup
  FOUND_FILES=$(find "$BACKUP_DIR" -type f -name "*$2*" | sort)
  FOUND_COUNT=$(echo "$FOUND_FILES" | grep -v "^$" | wc -l)
  
  if [ "$FOUND_COUNT" -eq 0 ]; then
    echo "❌ Không tìm thấy file nào có tên \"$2\" trong backup."
    
    # Tìm kiếm trong log nếu không tìm thấy file trực tiếp
    if [ -f "$BACKUP_LOG" ]; then
      echo "🔍 Đang tìm trong log backup..."
      LOG_RESULTS=$(grep -A 3 -B 1 "$2" "$BACKUP_LOG")
      
      if [ ! -z "$LOG_RESULTS" ]; then
        echo "📋 Tìm thấy trong log backup:"
        echo "$LOG_RESULTS"
      else
        echo "❌ Không tìm thấy trong log backup."
      fi
    fi
    
    exit 1
  else
    echo "✅ Tìm thấy $FOUND_COUNT file:"
    echo "$FOUND_FILES" | nl
    echo ""
    echo "💡 Để khôi phục file, sử dụng: ./restore_file.sh đường_dẫn_backup đường_dẫn_đích"
    exit 0
  fi
fi

# Lấy đường dẫn đầy đủ
BACKUP_PATH=$(realpath "$1")
DEST_PATH=$(realpath "$2")
DATETIME=$(date '+%Y-%m-%d - %H:%M:%S')
USER=$(whoami)

# Kiểm tra file backup tồn tại
if [ ! -f "$BACKUP_PATH" ]; then
  echo "❌ File backup không tồn tại: $BACKUP_PATH"
  exit 1
fi

# Tạo thư mục đích nếu chưa tồn tại
mkdir -p "$(dirname "$DEST_PATH")"

# Thực hiện khôi phục
echo "🔄 Đang khôi phục file từ: $BACKUP_PATH"
echo "📍 Đến: $DEST_PATH"

cp "$BACKUP_PATH" "$DEST_PATH"

if [ $? -eq 0 ]; then
  echo "✅ Khôi phục thành công!"
  
  # Cập nhật log nếu tồn tại
  if [ -f "$BACKUP_LOG" ]; then
    RELATIVE_BACKUP=$(realpath --relative-to="$WORKSPACE_ROOT" "$BACKUP_PATH")
    RELATIVE_DEST=$(realpath --relative-to="$WORKSPACE_ROOT" "$DEST_PATH")
    
    LOG_ENTRY="## $DATETIME - RESTORE\n- Backup: \`$RELATIVE_BACKUP\`\n- Restored to: \`$RELATIVE_DEST\`\n- Người thực hiện: $USER\n\n"
    echo -e "$LOG_ENTRY$(cat "$BACKUP_LOG")" > "$BACKUP_LOG.tmp"
    mv "$BACKUP_LOG.tmp" "$BACKUP_LOG"
    
    echo "📝 Đã cập nhật log restore tại: $BACKUP_LOG"
  fi
else
  echo "❌ Khôi phục thất bại!"
fi

exit 0 