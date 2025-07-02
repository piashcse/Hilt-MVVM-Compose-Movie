#!/bin/bash

# Script dọn dẹp các backup cũ
# Usage: ./cleanup_backups.sh [options]
# Options:
#   --zip-older-than N: Nén các backup cũ hơn N ngày
#   --delete-older-than N: Xóa các backup cũ hơn N ngày
#   --stats: Hiển thị thống kê về backup
#   --help: Hiển thị trợ giúp

WORKSPACE_ROOT=$(git rev-parse --show-toplevel 2>/dev/null || pwd)
BACKUP_DIR="$WORKSPACE_ROOT/_backups"
TODAY=$(date +%s)
ZIP_DAYS=7
DELETE_DAYS=90
SHOW_STATS=false

# Hiển thị trợ giúp
show_help() {
  echo "Công cụ dọn dẹp backup"
  echo "Cách sử dụng: ./cleanup_backups.sh [options]"
  echo ""
  echo "Options:"
  echo "  --zip-older-than N: Nén các backup cũ hơn N ngày (mặc định: 7)"
  echo "  --delete-older-than N: Xóa các backup cũ hơn N ngày (mặc định: 90)"
  echo "  --stats: Hiển thị thống kê về backup"
  echo "  --help: Hiển thị trợ giúp này"
  echo ""
  echo "Ví dụ:"
  echo "  ./cleanup_backups.sh --stats"
  echo "  ./cleanup_backups.sh --zip-older-than 3 --delete-older-than 30"
  exit 0
}

# Kiểm tra thư mục backup tồn tại
if [ ! -d "$BACKUP_DIR" ]; then
  echo "❌ Thư mục backup không tồn tại: $BACKUP_DIR"
  exit 1
fi

# Xử lý tham số đầu vào
while [ "$#" -gt 0 ]; do
  case "$1" in
    --zip-older-than)
      ZIP_DAYS="$2"
      shift 2
      ;;
    --delete-older-than)
      DELETE_DAYS="$2"
      shift 2
      ;;
    --stats)
      SHOW_STATS=true
      shift
      ;;
    --help)
      show_help
      ;;
    *)
      echo "❌ Không nhận diện được tham số: $1"
      show_help
      ;;
  esac
done

# Hiển thị thống kê
if [ "$SHOW_STATS" = true ]; then
  echo "📊 Thống kê backup:"
  
  # Số lượng thư mục backup
  BACKUP_COUNT=$(find "$BACKUP_DIR" -maxdepth 1 -type d -name "????-??-??" | wc -l)
  echo "- Số lượng ngày backup: $BACKUP_COUNT"
  
  # Tổng dung lượng
  TOTAL_SIZE=$(du -sh "$BACKUP_DIR" | awk '{print $1}')
  echo "- Tổng dung lượng: $TOTAL_SIZE"
  
  # Backup mới nhất
  NEWEST_BACKUP=$(find "$BACKUP_DIR" -maxdepth 1 -type d -name "????-??-??" | sort | tail -n 1)
  if [ ! -z "$NEWEST_BACKUP" ]; then
    echo "- Backup mới nhất: $(basename "$NEWEST_BACKUP")"
  fi
  
  # Backup cũ nhất
  OLDEST_BACKUP=$(find "$BACKUP_DIR" -maxdepth 1 -type d -name "????-??-??" | sort | head -n 1)
  if [ ! -z "$OLDEST_BACKUP" ]; then
    echo "- Backup cũ nhất: $(basename "$OLDEST_BACKUP")"
  fi
  
  # Số lượng file đã backup
  FILE_COUNT=$(find "$BACKUP_DIR" -type f -not -path "*/\.*" -not -name "backup_log.md" | wc -l)
  echo "- Tổng số file đã backup: $FILE_COUNT"
  
  echo ""
fi

# Nén các backup cũ
if [ "$ZIP_DAYS" -gt 0 ]; then
  echo "🗜️ Đang nén các backup cũ hơn $ZIP_DAYS ngày..."
  
  find "$BACKUP_DIR" -maxdepth 1 -type d -name "????-??-??" | while read backup_dir; do
    # Bỏ qua nếu đã là file zip
    if [[ "$backup_dir" == *.zip ]]; then
      continue
    fi
    
    # Tính tuổi của backup
    BACKUP_DATE=$(basename "$backup_dir")
    BACKUP_TIMESTAMP=$(date -j -f "%Y-%m-%d" "$BACKUP_DATE" "+%s" 2>/dev/null || date -d "$BACKUP_DATE" "+%s")
    DAYS_OLD=$(( ( $TODAY - $BACKUP_TIMESTAMP ) / 86400 ))
    
    if [ "$DAYS_OLD" -gt "$ZIP_DAYS" ]; then
      echo "📦 Nén: $BACKUP_DATE (${DAYS_OLD} ngày)"
      
      # Kiểm tra nếu file zip đã tồn tại
      if [ -f "${backup_dir}.zip" ]; then
        echo "  ⚠️ File zip đã tồn tại, bỏ qua"
        continue
      fi
      
      # Nén thư mục
      (cd "$BACKUP_DIR" && zip -rq "$(basename "$backup_dir").zip" "$(basename "$backup_dir")")
      
      if [ $? -eq 0 ]; then
        echo "  ✅ Nén thành công"
        # Xóa thư mục gốc sau khi nén
        rm -rf "$backup_dir"
      else
        echo "  ❌ Nén thất bại"
      fi
    fi
  done
  
  echo "✅ Đã hoàn thành nén backup"
  echo ""
fi

# Xóa các backup cũ
if [ "$DELETE_DAYS" -gt 0 ]; then
  echo "🗑️ Đang xóa các backup cũ hơn $DELETE_DAYS ngày..."
  
  find "$BACKUP_DIR" -maxdepth 1 -type f -name "????-??-??.zip" | while read backup_zip; do
    # Tính tuổi của backup
    BACKUP_DATE=$(basename "$backup_zip" .zip)
    BACKUP_TIMESTAMP=$(date -j -f "%Y-%m-%d" "$BACKUP_DATE" "+%s" 2>/dev/null || date -d "$BACKUP_DATE" "+%s")
    DAYS_OLD=$(( ( $TODAY - $BACKUP_TIMESTAMP ) / 86400 ))
    
    if [ "$DAYS_OLD" -gt "$DELETE_DAYS" ]; then
      echo "🗑️ Xóa: $BACKUP_DATE (${DAYS_OLD} ngày)"
      
      # Kiểm tra trong log nếu có đánh dấu là quan trọng
      if grep -q "IMPORTANT.*$BACKUP_DATE" "$BACKUP_DIR/backup_log.md" 2>/dev/null; then
        echo "  ⚠️ Backup được đánh dấu IMPORTANT, bỏ qua"
        continue
      fi
      
      # Xóa file zip
      rm -f "$backup_zip"
      
      if [ $? -eq 0 ]; then
        echo "  ✅ Xóa thành công"
      else
        echo "  ❌ Xóa thất bại"
      fi
    fi
  done
  
  echo "✅ Đã hoàn thành xóa backup"
fi

exit 0 