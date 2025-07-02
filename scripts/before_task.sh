#!/bin/bash

# Cursor Base Control Manager - Bash Version
# Script để set và check cursor base control bằng curl

echo "🚀 Bắt đầu quản lý Cursor Base Control..."
echo "================================================"

# Cookie - CẬP NHẬT COOKIE NÀY TRƯỚC KHI CHẠY
COOKIE="IndrX2ZuSmZramJSX0NIYUZoRzRzUGZ0cENIVHpHNXk0VE0ya2ZiUkVzQU14X2Fub255bW91c1VzZXJJZCI%3D=IjVhODFmYzY3LTI2NTUtNDFhMy05NjkzLTNlMGE1MjJhNzEyYyI=; WorkosCursorSessionToken=user_01JTNDY0GQ93FFV3CWHV9V3MGC%3A%3AeyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJnb29nbGUtb2F1dGgyfHVzZXJfMDFKVE5EWTBHUTkzRkZWM0NXSFY5VjNNR0MiLCJ0aW1lIjoiMTc1MTQyNDIyMSIsInJhbmRvbW5lc3MiOiJmN2ZjZWJhNS00ZmFjLTQ4ODQiLCJleHAiOjE3NTY2MDgyMjEsImlzcyI6Imh0dHBzOi8vYXV0aGVudGljYXRpb24uY3Vyc29yLnNoIiwic2NvcGUiOiJvcGVuaWQgcHJvZmlsZSBlbWFpbCBvZmZsaW5lX2FjY2VzcyIsImF1ZCI6Imh0dHBzOi8vY3Vyc29yLmNvbSIsInR5cGUiOiJ3ZWIifQ.DLf1_IFyUNcREUYho4ePdgcZUg0IM89bz_9r6lBNuVQ; _dd_s=aid=9d5b2ee6-6f66-41aa-9d3f-3bbc976bc0d4^&rum=1^&id=81aacc8b-dadc-441c-a375-df5e2c0ab92b^&created=1751424222410^&expire=1751426372603"

# Common headers
USER_AGENT="Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:140.0) Gecko/20100101 Firefox/140.0"
ACCEPT="*/*"
ACCEPT_LANGUAGE="vi-VN,vi;q=0.8,en-US;q=0.5,en;q=0.3"
ACCEPT_ENCODING="gzip, deflate, br, zstd"
REFERER="https://cursor.com/dashboard?tab=settings"
CONTENT_TYPE="application/json"
ORIGIN="https://cursor.com"
CONNECTION="keep-alive"
SEC_FETCH_DEST="empty"
SEC_FETCH_MODE="cors"
SEC_FETCH_SITE="same-origin"
TE="trailers"

echo "📤 Bước 1: Thiết lập base control..."
echo "------------------------------"

# Set hard limit
SET_RESPONSE=$(curl --silent --location 'https://cursor.com/api/dashboard/set-hard-limit' \
  --header "User-Agent: $USER_AGENT" \
  --header "Accept: $ACCEPT" \
  --header "Accept-Language: $ACCEPT_LANGUAGE" \
  --header "Accept-Encoding: $ACCEPT_ENCODING" \
  --header "Referer: $REFERER" \
  --header "Content-Type: $CONTENT_TYPE" \
  --header "Origin: $ORIGIN" \
  --header "Connection: $CONNECTION" \
  --header "Cookie: $COOKIE" \
  --header "Sec-Fetch-Dest: $SEC_FETCH_DEST" \
  --header "Sec-Fetch-Mode: $SEC_FETCH_MODE" \
  --header "Sec-Fetch-Site: $SEC_FETCH_SITE" \
  --header "Priority: u=0" \
  --header "TE: $TE" \
  --data '{"hardLimit":0,"noUsageBasedAllowed":true,"hardLimitPerUser":0}' \
  --write-out "HTTPSTATUS:%{http_code}")

# Parse response
SET_BODY=$(echo $SET_RESPONSE | sed -E 's/HTTPSTATUS:[0-9]{3}$//')
SET_STATUS=$(echo $SET_RESPONSE | tr -d '\n' | sed -E 's/.*HTTPSTATUS:([0-9]{3})$/\1/')

echo "📊 Status Code: $SET_STATUS"
echo "📝 Response: $SET_BODY"

if [ "$SET_STATUS" -eq 200 ]; then
    echo "✅ Thiết lập base control thành công!"
else
    echo "❌ Lỗi khi thiết lập base control: $SET_STATUS"
    exit 1
fi

echo ""
echo "📥 Bước 2: Kiểm tra base control..."
echo "------------------------------"

# Get hard limit
GET_RESPONSE=$(curl --silent --location 'https://cursor.com/api/dashboard/get-hard-limit' \
  --header "User-Agent: $USER_AGENT" \
  --header "Accept: $ACCEPT" \
  --header "Accept-Language: $ACCEPT_LANGUAGE" \
  --header "Accept-Encoding: $ACCEPT_ENCODING" \
  --header "Referer: $REFERER" \
  --header "Content-Type: $CONTENT_TYPE" \
  --header "Origin: $ORIGIN" \
  --header "Connection: $CONNECTION" \
  --header "Cookie: $COOKIE" \
  --header "Sec-Fetch-Dest: $SEC_FETCH_DEST" \
  --header "Sec-Fetch-Mode: $SEC_FETCH_MODE" \
  --header "Sec-Fetch-Site: $SEC_FETCH_SITE" \
  --header "Priority: u=4" \
  --header "TE: $TE" \
  --data '{}' \
  --write-out "HTTPSTATUS:%{http_code}")

# Parse response
GET_BODY=$(echo $GET_RESPONSE | sed -E 's/HTTPSTATUS:[0-9]{3}$//')
GET_STATUS=$(echo $GET_RESPONSE | tr -d '\n' | sed -E 's/.*HTTPSTATUS:([0-9]{3})$/\1/')

echo "📊 Status Code: $GET_STATUS"
echo "📝 Response: $GET_BODY"

if [ "$GET_STATUS" -eq 200 ]; then
    echo "✅ Lấy thông tin base control thành công!"
else
    echo "❌ Lỗi khi lấy base control: $GET_STATUS"
    exit 1
fi

echo ""
echo "🔍 Bước 3: Kiểm tra kết quả..."
echo "------------------------------"

# Check if noUsageBasedAllowed is true
if echo "$GET_BODY" | grep -q '"noUsageBasedAllowed":true'; then
    echo "🎉 Đã set thành công base control"
    echo "================================================"
    echo "🎊 HOÀN THÀNH THÀNH CÔNG!"
    exit 0
else
    echo "⚠️  Kết quả không như mong đợi"
    echo "📋 Dữ liệu nhận được: $GET_BODY"
    echo "================================================"
    echo "💥 QUY TRÌNH THẤT BẠI!"
    exit 1
fi