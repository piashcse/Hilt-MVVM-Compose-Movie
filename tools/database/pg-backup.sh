#!/bin/bash

# Script backup dữ liệu Supabase sử dụng pg_dump
# Tác giả: ThangPhap Team
# Ngày: $(date +"%d-%m-%Y")

# Màu sắc cho output
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
NC='\033[0m' # No Color

# Tạo thư mục backup
BACKUP_DIR="./pg_backups/$(date +"%Y%m%d_%H%M%S")"
mkdir -p $BACKUP_DIR

echo -e "${YELLOW}Bắt đầu quá trình backup dữ liệu sử dụng pg_dump...${NC}"

# Đường dẫn đến pg_dump phiên bản 15
PG_DUMP="/usr/local/opt/postgresql@15/bin/pg_dump"

# Kiểm tra pg_dump đã được cài đặt chưa
if ! command -v $PG_DUMP &> /dev/null; then
    echo -e "${RED}pg_dump phiên bản 15 chưa được cài đặt. Vui lòng cài đặt PostgreSQL 15 trước khi tiếp tục.${NC}"
    exit 1
fi

# Thiết lập thông tin kết nối
DB_HOST="aws-0-ap-southeast-1.pooler.supabase.com"
DB_PORT="5432"
DB_USER="postgres.aggkdikmvlbtaumbbszf"
DB_NAME="postgres"
DB_PASSWORD="qd4jWJ0A37ZLK46c"

# Tạo connection string
DB_URL="postgresql://$DB_USER:$DB_PASSWORD@$DB_HOST:$DB_PORT/$DB_NAME"

# Backup toàn bộ database
echo -e "${YELLOW}Đang backup toàn bộ database...${NC}"
PGPASSWORD=$DB_PASSWORD $PG_DUMP -h $DB_HOST -U $DB_USER -p $DB_PORT -d $DB_NAME -F c -f $BACKUP_DIR/full_backup.dump || {
    echo -e "${RED}Lỗi khi backup toàn bộ database!${NC}"
    exit 1
}

# Backup chỉ schema
echo -e "${YELLOW}Đang backup schema...${NC}"
PGPASSWORD=$DB_PASSWORD $PG_DUMP -h $DB_HOST -U $DB_USER -p $DB_PORT -d $DB_NAME -s -f $BACKUP_DIR/schema_backup.sql || {
    echo -e "${RED}Lỗi khi backup schema!${NC}"
    exit 1
}

# Danh sách các bảng cần backup riêng
TABLES=(
    "achievements"
    "ai_usage_limits"
    "badges"
    "categories"
    "certificates"
    "content_votes"
    "course_contents"
    "course_leaderboard"
    "courses"
    "diagram_contents"
    "diagrams"
    "experts"
    "leaderboard"
    "leaderboard_entries"
    "leaderboards"
    "post_tags"
    "posts"
    "quests"
    "reward_points_history"
    "tags"
    "time_leaderboard"
    "user_achievements"
    "user_ai_usage"
    "user_badges"
    "user_learning_history"
    "user_learning_stats"
    "user_learning_streak"
    "user_progress"
    "user_quests"
    "user_subscription_history"
    "users"
)

# Backup từng bảng riêng biệt
echo -e "${YELLOW}Đang backup dữ liệu từng bảng...${NC}"
for table in "${TABLES[@]}"; do
    echo -e "  Đang backup bảng ${GREEN}$table${NC}..."
    PGPASSWORD=$DB_PASSWORD $PG_DUMP -h $DB_HOST -U $DB_USER -p $DB_PORT -d $DB_NAME -t "public.$table" -a -f $BACKUP_DIR/${table}_data.sql || {
        echo -e "${RED}Lỗi khi backup bảng $table!${NC}"
        # Tiếp tục với bảng tiếp theo thay vì dừng lại
        continue
    }
done

# Tạo file README hướng dẫn khôi phục
cat > $BACKUP_DIR/README.md << EOF
# Backup Dữ Liệu ThangPhap (pg_dump)

Backup được tạo vào: $(date +"%d-%m-%Y %H:%M:%S")

## Cách khôi phục dữ liệu

### Khôi phục toàn bộ database:
\`\`\`
pg_restore -h <host> -U <username> -d <database> -c -v full_backup.dump
\`\`\`

### Khôi phục chỉ schema:
\`\`\`
psql -h <host> -U <username> -d <database> -f schema_backup.sql
\`\`\`

### Khôi phục dữ liệu từng bảng:
\`\`\`
psql -h <host> -U <username> -d <database> -f <table>_data.sql
\`\`\`

## Danh sách các bảng đã backup riêng

$(for table in "${TABLES[@]}"; do echo "- $table"; done)

## Lưu ý

- Khi khôi phục toàn bộ database, tất cả dữ liệu hiện tại sẽ bị xóa và thay thế bằng dữ liệu backup
- Nếu chỉ muốn khôi phục một số bảng, hãy sử dụng các file backup riêng của từng bảng
EOF

# Tạo file nén
echo -e "${YELLOW}Đang nén các file backup...${NC}"
tar -czf $BACKUP_DIR.tar.gz -C $(dirname $BACKUP_DIR) $(basename $BACKUP_DIR) || {
    echo -e "${RED}Lỗi khi nén file backup!${NC}"
    exit 1
}

# Xóa thư mục tạm sau khi đã nén
rm -rf $BACKUP_DIR

echo -e "${GREEN}Backup hoàn tất!${NC}"
echo -e "File backup: ${GREEN}$BACKUP_DIR.tar.gz${NC}"
echo -e "${YELLOW}Lưu ý: Hãy lưu trữ file backup này ở nơi an toàn trước khi thực hiện các thay đổi.${NC}" 