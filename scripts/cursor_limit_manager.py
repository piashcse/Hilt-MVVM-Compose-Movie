#!/usr/bin/env python3
"""
Cursor Hard Limit Manager
Script để quản lý hard limit của Cursor thông qua API
"""

import requests
import json
import sys
from typing import Dict, Any


class CursorLimitManager:
    def __init__(self):
        self.base_url = "https://cursor.com/api/dashboard"
        self.headers = {
            'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:140.0) Gecko/20100101 Firefox/140.0',
            'Accept': '*/*',
            'Accept-Language': 'vi-VN,vi;q=0.8,en-US;q=0.5,en;q=0.3',
            'Accept-Encoding': 'gzip, deflate, br, zstd',
            'Referer': 'https://cursor.com/dashboard?tab=settings',
            'Content-Type': 'application/json',
            'Origin': 'https://cursor.com',
            'Connection': 'keep-alive',
            'Cookie': 'IndrX2ZuSmZramJSX0NIYUZoRzRzUGZ0cENIVHpHNXk0VE0ya2ZiUkVzQU14X2Fub255bW91c1VzZXJJZCI%3D=IjVhODFmYzY3LTI2NTUtNDFhMy05NjkzLTNlMGE1MjJhNzEyYyI=; WorkosCursorSessionToken=user_01JTNDY0GQ93FFV3CWHV9V3MGC%3A%3AeyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJnb29nbGUtb2F1dGgyfHVzZXJfMDFKVE5EWTBHUTkzRkZWM0NXSFY5VjNNR0MiLCJ0aW1lIjoiMTc1MTQyNDIyMSIsInJhbmRvbW5lc3MiOiJmN2ZjZWJhNS00ZmFjLTQ4ODQiLCJleHAiOjE3NTY2MDgyMjEsImlzcyI6Imh0dHBzOi8vYXV0aGVudGljYXRpb24uY3Vyc29yLnNoIiwic2NvcGUiOiJvcGVuaWQgcHJvZmlsZSBlbWFpbCBvZmZsaW5lX2FjY2VzcyIsImF1ZCI6Imh0dHBzOi8vY3Vyc29yLmNvbSIsInR5cGUiOiJ3ZWIifQ.DLf1_IFyUNcREUYho4ePdgcZUg0IM89bz_9r6lBNuVQ; _dd_s=aid=9d5b2ee6-6f66-41aa-9d3f-3bbc976bc0d4^&rum=1^&id=81aacc8b-dadc-441c-a375-df5e2c0ab92b^&created=1751424222410^&expire=1751426372603',
            'Sec-Fetch-Dest': 'empty',
            'Sec-Fetch-Mode': 'cors',
            'Sec-Fetch-Site': 'same-origin',
            'TE': 'trailers'
        }

    def set_hard_limit(self) -> bool:
        """
        Thiết lập base control về 0
        Returns: True nếu thành công, False nếu thất bại
        """
        url = f"{self.base_url}/set-hard-limit"
        data = {
            "hardLimit": 0,
            "noUsageBasedAllowed": True,
            "hardLimitPerUser": 0
        }
        
        # Thêm Priority header cho set request
        headers = self.headers.copy()
        headers['Priority'] = 'u=0'
        
        try:
            print("🔄 Đang thiết lập base control...")
            response = requests.post(url, headers=headers, json=data)
            
            print(f"📊 Status Code: {response.status_code}")
            print(f"📝 Response: {response.text}")
            
            if response.status_code == 200:
                print("✅ Thiết lập base control thành công!")
                return True
            else:
                print(f"❌ Lỗi khi thiết lập base control: {response.status_code}")
                return False
                
        except requests.exceptions.RequestException as e:
            print(f"❌ Lỗi kết nối: {e}")
            return False

    def get_hard_limit(self) -> Dict[str, Any]:
        """
        Lấy thông tin base control hiện tại
        Returns: Dictionary chứa thông tin base control
        """
        url = f"{self.base_url}/get-hard-limit"
        data = {}
        
        # Thêm Priority header cho get request
        headers = self.headers.copy()
        headers['Priority'] = 'u=4'
        
        try:
            print("🔍 Đang kiểm tra base control...")
            response = requests.post(url, headers=headers, json=data)
            
            print(f"📊 Status Code: {response.status_code}")
            print(f"📝 Response: {response.text}")
            
            if response.status_code == 200:
                try:
                    result = response.json()
                    print("✅ Lấy thông tin base control thành công!")
                    return result
                except json.JSONDecodeError:
                    print("❌ Lỗi parse JSON response")
                    return {}
            else:
                print(f"❌ Lỗi khi lấy base control: {response.status_code}")
                return {}
                
        except requests.exceptions.RequestException as e:
            print(f"❌ Lỗi kết nối: {e}")
            return {}

    def check_success(self, limit_data: Dict[str, Any]) -> bool:
        """
        Kiểm tra xem kết quả có thành công không
        Returns: True nếu noUsageBasedAllowed = True
        """
        if limit_data.get("noUsageBasedAllowed") is True:
            print("🎉 Đã set thành công base control")
            return True
        else:
            print(f"⚠️  Kết quả không như mong đợi: {limit_data}")
            return False

    def run(self) -> bool:
        """
        Chạy toàn bộ quy trình
        Returns: True nếu thành công hoàn toàn
        """
        print("🚀 Bắt đầu quản lý Cursor Base Control...")
        print("=" * 50)
        
        # Bước 1: Set base control
        if not self.set_hard_limit():
            print("❌ Thất bại ở bước thiết lập base control")
            return False
        
        print("\n" + "-" * 30 + "\n")
        
        # Bước 2: Get base control để kiểm tra
        limit_data = self.get_hard_limit()
        if not limit_data:
            print("❌ Thất bại ở bước lấy thông tin base control")
            return False
        
        print("\n" + "-" * 30 + "\n")
        
        # Bước 3: Kiểm tra kết quả
        success = self.check_success(limit_data)
        
        print("\n" + "=" * 50)
        if success:
            print("🎊 HOÀN THÀNH THÀNH CÔNG!")
        else:
            print("💥 QUY TRÌNH THẤT BẠI!")
        
        return success


def main():
    """
    Hàm main để chạy script
    """
    manager = CursorLimitManager()
    success = manager.run()
    
    # Exit với code tương ứng
    sys.exit(0 if success else 1)


if __name__ == "__main__":
    main()