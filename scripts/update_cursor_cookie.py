#!/usr/bin/env python3
"""
Cursor Cookie Updater
Script để cập nhật cookie trong các file cursor limit scripts
"""

import os
import re
import sys
from pathlib import Path

def update_cookie_in_file(file_path: str, new_cookie: str) -> bool:
    """
    Cập nhật cookie trong file
    """
    try:
        with open(file_path, 'r', encoding='utf-8') as f:
            content = f.read()
        
        # Pattern để tìm cookie line
        cookie_pattern = r"'Cookie': '[^']*'"
        new_cookie_line = f"'Cookie': '{new_cookie}'"
        
        # Thay thế cookie
        updated_content = re.sub(cookie_pattern, new_cookie_line, content)
        
        # Kiểm tra xem có thay đổi không
        if updated_content != content:
            with open(file_path, 'w', encoding='utf-8') as f:
                f.write(updated_content)
            print(f"✅ Đã cập nhật cookie trong {file_path}")
            return True
        else:
            print(f"⚠️  Không tìm thấy cookie pattern trong {file_path}")
            return False
            
    except Exception as e:
        print(f"❌ Lỗi khi cập nhật {file_path}: {e}")
        return False

def main():
    """
    Main function
    """
    if len(sys.argv) != 2:
        print("❌ Sử dụng: python3 update_cursor_cookie.py '<new_cookie>'")
        print("\n📝 Ví dụ:")
        print("python3 update_cursor_cookie.py 'IndrX2ZuSmZramJSX0NIYUZoRzRzUGZ0cENIVHpHNXk0VE0ya2ZiUkVzQU14X2Fub255bW91c1VzZXJJZCI%3D=...'")
        sys.exit(1)
    
    new_cookie = sys.argv[1]
    
    # Danh sách các file cần cập nhật
    script_dir = Path(__file__).parent
    files_to_update = [
        script_dir / "cursor_limit_manager.py",
        script_dir / "quick_cursor_limit.py"
    ]
    
    print("🔄 Bắt đầu cập nhật cookie trong các script...")
    print(f"🍪 Cookie mới: {new_cookie[:50]}...")
    print("=" * 60)
    
    success_count = 0
    
    for file_path in files_to_update:
        if file_path.exists():
            if update_cookie_in_file(str(file_path), new_cookie):
                success_count += 1
        else:
            print(f"⚠️  File không tồn tại: {file_path}")
    
    print("\n" + "=" * 60)
    print(f"🎯 Kết quả: Đã cập nhật {success_count}/{len(files_to_update)} files")
    
    if success_count == len(files_to_update):
        print("🎉 Hoàn thành! Bây giờ bạn có thể chạy các script cursor limit.")
    else:
        print("⚠️  Một số file không được cập nhật. Vui lòng kiểm tra lại.")

if __name__ == "__main__":
    main()