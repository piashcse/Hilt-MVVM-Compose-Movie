#!/usr/bin/env python3
"""
Quick Cursor Limit Script
Script đơn giản để set và check cursor limit
"""

import requests
import json

# Headers chung
headers = {
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

print("🚀 Bắt đầu set cursor hard limit...")

# 1. Set hard limit
set_headers = headers.copy()
set_headers['Priority'] = 'u=0'

set_data = {
    "hardLimit": 0,
    "noUsageBasedAllowed": True,
    "hardLimitPerUser": 0
}

try:
    print("📤 Đang gửi request set hard limit...")
    set_response = requests.post(
        'https://cursor.com/api/dashboard/set-hard-limit',
        headers=set_headers,
        json=set_data
    )
    print(f"✅ Set response: {set_response.status_code} - {set_response.text}")
except Exception as e:
    print(f"❌ Lỗi set hard limit: {e}")
    exit(1)

print("\n" + "-"*40 + "\n")

# 2. Get hard limit để kiểm tra
get_headers = headers.copy()
get_headers['Priority'] = 'u=4'

get_data = {}

try:
    print("📥 Đang kiểm tra hard limit...")
    get_response = requests.post(
        'https://cursor.com/api/dashboard/get-hard-limit',
        headers=get_headers,
        json=get_data
    )
    print(f"📊 Get response: {get_response.status_code} - {get_response.text}")
    
    if get_response.status_code == 200:
        try:
            result = get_response.json()
            print(f"\n📋 Kết quả: {json.dumps(result, indent=2)}")
            
            # Kiểm tra điều kiện thành công
            if result.get("noUsageBasedAllowed") is True:
                print("\n🎉 THÀNH CÔNG! noUsageBasedAllowed = True")
            else:
                print("\n⚠️  Kết quả không như mong đợi")
                
        except json.JSONDecodeError:
            print("❌ Không thể parse JSON response")
    else:
        print(f"❌ Lỗi get hard limit: {get_response.status_code}")
        
except Exception as e:
    print(f"❌ Lỗi get hard limit: {e}")
    exit(1)

print("\n🏁 Hoàn thành!")