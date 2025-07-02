# API Service

Luôn log các thông tin khi request và response để debug.

## Weather API

[weather-basic-workflow.mdc](../.cursor/rules/weather-basic-workflow.mdc) - đây là các api cơ bản, sử dụng trong các ứng dụng đơn giản bổ xung thêm thời tiết
[weather-detailed-workflow.mdc](../.cursor/rules/weather-detailed-workflow.mdc) - tài liệu api đầy đủ

## Chat AI & Generate Text AI

Sử dụng API chat cho tất cả các tương tác AI. API này hỗ trợ tiếp tục cuộc trò chuyện thông qua conversation_id, nếu là 1 cuộc trò chuyện mới hoặc không có request trước đó thì conversation_id = null.
Lưu ý: conversation_id chỉ được lấy từ phản hồi từ API chứ không được tự tạo ra

```
curl --location 'https://ai.dreamapi.net/v1/chat-messages' \
--header 'Authorization: Bearer app-dify-api-key' \
--header 'Content-Type: application/json' \
--data '{
    "query": "Xin chào",
    "user": "user-abb739aa-9bdd-4e08-b9c2-77d82226d36d",
    "conversation_id": "33ab8c1e-4beb-4052-96d3-cde3b693d3a6",
    "response_mode": "blocking",
    "inputs": {
        "data": "{\"health_data\":\"json string\", \"character_id\":\"character_01\"}",
        "template": "{\"statusMessage\":\"A short status message about the user'\''s health\",\"assessmentLevel\":\"A brief assessment level\",\"assessmentScore\":\"A numerical score from 1 to 10\",\"healthAnalysis\":\"A detailed paragraph analyzing the user'\''s health\",\"recommendations\":[{\"title\":\"Recommendation title\",\"content\":\"Detailed explanation\"}],\"nutritionalAdvice\":\"Nutritional advice\",\"workoutTips\":\"Exercise advice\"}"
    }
}'
```

**Explanation:**

- **query**: Câu hỏi hoặc yêu cầu của người dùng.
- **user**: UUID, một định danh duy nhất cho người dùng.
- **conversation_id**: ID của cuộc trò chuyện, dùng để tiếp tục cuộc trò chuyện trước đó. Để trống để bắt đầu cuộc trò chuyện mới.
- **response_mode**: "blocking" để chờ phản hồi hoàn chỉnh.
- **inputs**: JSON chứa dữ liệu bổ sung để phân tích AI, như dữ liệu sức khỏe cụ thể hoặc ngữ cảnh.
- **template**: JSON string defining the structure of the expected output, allowing flexible and customizable data structures.

**Response example:**

```json
{
  "event": "message",
  "task_id": "d7b72f7f-510a-4e7f-b364-f80a48951b85",
  "id": "3630e973-d702-465e-8750-38f93e98e97a",
  "message_id": "3630e973-d702-465e-8750-38f93e98e97a",
  "conversation_id": "83bb686f-104a-45c7-9d10-51c9ce9602af",
  "mode": "advanced-chat",
  "answer": "Đây là một bữa ăn sáng truyền thống kiểu Anh, thường bao gồm trứng chiên, xúc xích, thịt xông khói, đậu nấu sốt cà chua, nấm, cà chua nướng và bánh mì nướng. Bữa ăn này thường đi kèm với một tách cà phê hoặc trà. Nó rất phong phú và đầy đủ dinh dưỡng! 🍳🥓🍅",
  "metadata": {
    "usage": {
      "prompt_tokens": 26865,
      "prompt_unit_price": "0.15",
      "prompt_price_unit": "0.000001",
      "prompt_price": "0.0040298",
      "completion_tokens": 92,
      "completion_unit_price": "0.60",
      "completion_price_unit": "0.000001",
      "completion_price": "0.0000552",
      "total_tokens": 26957,
      "total_price": "0.0040850",
      "currency": "USD",
      "latency": 4.071079017594457
    }
  },
  "created_at": 1735287876
}
```

Example with json format

````
{
    "event": "message",
    "task_id": "3d7b89ee-f3df-4e17-bef6-971e7565abc6",
    "id": "6ac329c2-89df-4277-8579-3e0e99430871",
    "message_id": "6ac329c2-89df-4277-8579-3e0e99430871",
    "conversation_id": "37c0aff2-d47a-4808-a983-84f4303a8271",
    "mode": "advanced-chat",
    "answer": "```json\n{\n  \"id\": \"course-mindful-parenting\",\n  \"name\": \"Nu\u00f4i D\u1ea1y Con V\u1edbi Ch\u00e1nh Ni\u1ec7m\",\n  \"category_id\": \"cd952c17-e1f6-4877-8c9c-a864bf4b17af\",\n  \"difficulty\": \"beginner\",\n  \"description\": \"Kh\u00f3a h\u1ecdc n\u00e0y cung c\u1ea5p cho cha m\u1eb9 nh\u1eefng c\u00f4ng c\u1ee5 v\u00e0 k\u1ef9 n\u0103ng th\u1ef1c t\u1ebf \u0111\u1ec3 nu\u00f4i d\u1ea1y con c\u00e1i v\u1edbi s\u1ef1 t\u1ec9nh th\u1ee9c, th\u1ea5u hi\u1ec3u v\u00e0 k\u1ebft n\u1ed1i s\u00e2u s\u1eafc.\",\n  \"tags\": [\"nu\u00f4i d\u1ea1y con\", \"ch\u00e1nh ni\u1ec7m\", \"k\u1ebft n\u1ed1i\", \"giao ti\u1ebfp\", \"c\u1ea3m x\u00fac\", \"gia \u0111\u00ecnh\"],\n  \"image\": \"\",\n  \"startnodeid\": \"intro-node\",\n  \"details\": {\n    \"title\": \"Nu\u00f4i D\u1ea1y Con V\u1edbi Ch\u00e1nh Ni\u1ec7m: X\u00e2y D\u1ef1ng M\u1ed1i Quan H\u1ec7 Gia \u0110\u00ecnh H\u1ea1nh Ph\u00fac v\u00e0 B\u1ec1n V\u1eefng\",\n    \"banner_image\": \"\",\n    \"content\": {\n      \"introduction\": \"Ch\u00e0o m\u1eebng b\u1ea1n \u0111\u1ebfn v\u1edbi kh\u00f3a h\u1ecdc 'Nu\u00f4i D\u1ea1y Con V\u1edbi Ch\u00e1nh Ni\u1ec7m'. Trong kh\u00f3a h\u1ecdc n\u00e0y, ch\u00fang ta s\u1ebd c\u00f9ng nhau kh\u00e1m ph\u00e1 nh\u1eefng nguy\u00ean t\u1eafc c\u1ed1t l\u00f5i c\u1ee7a vi\u1ec7c nu\u00f4i d\u1ea1y con c\u00e1i b\u1eb1ng ch\u00e1nh ni\u1ec7m, t\u1eeb \u0111\u00f3 x\u00e2y d\u1ef1ng m\u1ed9t m\u00f4i tr\u01b0\u1eddng gia \u0111\u00ecnh tr\u00e0n \u0111\u1ea7y y\u00eau th\u01b0\u01a1ng, th\u1ea5u hi\u1ec3u v\u00e0 k\u1ebft n\u1ed1i. Kh\u00f3a h\u1ecdc kh\u00f4ng ch\u1ec9 cung c\u1ea5p ki\u1ebfn th\u1ee9c l\u00fd thuy\u1ebft m\u00e0 c\u00f2n t\u1eadp trung v\u00e0o c\u00e1c b\u00e0i t\u1eadp th\u1ef1c h\u00e0nh, t\u00ecnh hu\u1ed1ng th\u1ef1c t\u1ebf v\u00e0 k\u1ecbch b\u1ea3n \u0111\u1ed1i tho\u1ea1i \u0111\u1ec3 b\u1ea1n c\u00f3 th\u1ec3 \u00e1p d\u1ee5ng ngay v\u00e0o cu\u1ed9c s\u1ed1ng h\u00e0ng ng\u00e0y.\",\n      \"illustration_image\": \"\"\n    }\n  },\n  \"nodes\": [\n    {\n      \"id\": \"intro-node\",\n      \"name\": \"Gi\u1edbi thi\u1ec7u v\u1ec1 Nu\u00f4i D\u1ea1y Con Ch\u00e1nh Ni\u1ec7m\",\n      \"description\": \"T\u00ecm hi\u1ec3u v\u1ec1 kh\u00e1i ni\u1ec7m, l\u1ee3i \u00edch v\u00e0 c\u00e1c nguy\u00ean t\u1eafc c\u01a1 b\u1ea3n c\u1ee7a vi\u1ec7c nu\u00f4i d\u1ea1y con ch\u00e1nh ni\u1ec7m.\",\n      \"content\": \"Nu\u00f4i d\u1ea1y con ch\u00e1nh ni\u1ec7m l\u00e0 g\u00ec? \u0110\u00f3 l\u00e0 vi\u1ec7c hi\u1ec7n di\u1ec7n tr\u1ecdn v\u1eb9n v\u1edbi con trong t\u1eebng kho\u1ea3nh kh\u1eafc, kh\u00f4ng ph\u00e1n x\u00e9t, ch\u1ea5p nh\u1eadn con ng\u01b0\u1eddi th\u1eadt c\u1ee7a con v\u00e0 ph\u1ea3n \u1ee9ng m\u1ed9t c\u00e1ch c\u00f3 \u00fd th\u1ee9c thay v\u00ec ph\u1ea3n \u1ee9ng theo th\u00f3i quen. L\u1ee3i \u00edch: Gi\u1ea3m c\u0103ng th\u1eb3ng cho c\u1ea3 cha m\u1eb9 v\u00e0 con c\u00e1i, t\u0103ng c\u01b0\u1eddng s\u1ef1 g\u1eafn k\u1ebft, c\u1ea3i thi\u1ec7n giao ti\u1ebfp, gi\u00fap con ph\u00e1t tri\u1ec3n kh\u1ea3 n\u0103ng t\u1ef1 \u0111i\u1ec1u ch\u1ec9nh c\u1ea3m x\u00fac v\u00e0 x\u00e2y d\u1ef1ng l\u00f2ng t\u1ef1 tr\u1ecdng. C\u00e1c nguy\u00ean t\u1eafc c\u01a1 b\u1ea3n: Hi\u1ec7n di\u1ec7n, Kh\u00f4ng ph\u00e1n x\u00e9t, Ch\u1ea5p nh\u1eadn, Ki\u00ean nh\u1eabn, Y\u00eau th\u01b0\u01a1ng.\",\n      \"type\": \"concept\",\n      \"children\": [\"node-1\"],\n      \"resources\": [\n        {\n          \"type\": \"article\",\n          \"title\": \"Nu\u00f4i d\u1ea1y con ch\u00e1nh ni\u1ec7m l\u00e0 g\u00ec?\",\n          \"url\": \"https://example.com/mindful-parenting-intro\",\n          \"description\": \"B\u00e0i vi\u1ebft gi\u1edbi thi\u1ec7u t\u1ed5ng quan v\u1ec1 nu\u00f4i d\u1ea1y con ch\u00e1nh ni\u1ec7m.\"\n        }\n      ]\n    },\n    {\n      \"id\": \"node-1\",\n      \"name\": \"N\u1ec1n T\u1ea3ng C\u1ee7a Nu\u00f4i D\u1ea1y Con Ch\u00e1nh Ni\u1ec7m\",\n      \"description\": \"Hi\u1ec3u r\u00f5 v\u1ec1 ch\u00e1nh ni\u1ec7m v\u00e0 c\u00e1ch \u00e1p d\u1ee5ng v\u00e0o vi\u1ec7c nu\u00f4i d\u1ea1y con.\",\n      \"content\": \"Ch\u00e1nh ni\u1ec7m l\u00e0 kh\u1ea3 n\u0103ng t\u1eadp trung v\u00e0o hi\u1ec7n t\u1ea1i, nh\u1eadn bi\u1ebft suy ngh\u0129, c\u1ea3m x\u00fac v\u00e0 c\u1ea3m gi\u00e1c c\u1ee7a b\u1ea3n th\u00e2n m\u00e0 kh\u00f4ng ph\u00e1n x\u00e9t. \u0110\u1ec3 \u00e1p d\u1ee5ng ch\u00e1nh ni\u1ec7m v\u00e0o vi\u1ec7c nu\u00f4i d\u1ea1y con, cha m\u1eb9 c\u1ea7n: 1. T\u1ef1 nh\u1eadn th\u1ee9c: Nh\u1eadn bi\u1ebft c\u1ea3m x\u00fac v\u00e0 ph\u1ea3n \u1ee9ng c\u1ee7a b\u1ea3n th\u00e2n. 2. T\u1ef1 \u0111i\u1ec1u ch\u1ec9nh: Ki\u1ec3m so\u00e1t c\u1ea3m x\u00fac v\u00e0 ph\u1ea3n \u1ee9ng c\u1ee7a m\u00ecnh. 3. L\u1eafng nghe t\u00edch c\u1ef1c: L\u1eafng nghe con v\u1edbi s\u1ef1 ch\u00fa t\u00e2m v\u00e0 th\u1ea5u hi\u1ec3u. 4. Giao ti\u1ebfp hi\u1ec7u qu\u1ea3: S\u1eed d\u1ee5ng ng\u00f4n ng\u1eef t\u00edch c\u1ef1c v\u00e0 t\u00f4n tr\u1ecdng. 5. Ch\u1ea5p nh\u1eadn: Ch\u1ea5p nh\u1eadn con ng\u01b0\u1eddi th\u1eadt c\u1ee7a con, bao g\u1ed3m c\u1ea3 \u0111i\u1ec3m m\u1ea1nh v\u00e0 \u0111i\u1ec3m y\u1ebfu. \\n\\n**V\u00ed d\u1ee5 th\u1ef1c t\u1ebf:** Khi con l\u00e0m \u0111\u1ed5 n\u01b0\u1edbc, thay v\u00ec m\u1eafng con, h\u00e3y h\u00edt th\u1edf s\u00e2u, nh\u1eadn bi\u1ebft c\u01a1n gi\u1eadn c\u1ee7a m\u00ecnh v\u00e0 n\u00f3i: 'M\u1eb9 th\u1ea5y con l\u00e0m \u0111\u1ed5 n\u01b0\u1edbc. Con c\u00f3 sao kh\u00f4ng? Ch\u00fang ta c\u00f9ng nhau lau nh\u00e9.'\\n\\n**B\u00e0i t\u1eadp:** Th\u1ef1c h\u00e0nh thi\u1ec1n \u0111\u1ecbnh 5 ph\u00fat m\u1ed7i ng\u00e0y \u0111\u1ec3 t\u0103ng c\u01b0\u1eddng kh\u1ea3 n\u0103ng ch\u00e1nh ni\u1ec7m.\",\n      \"type\": \"concept\",\n      \"children\": [\"node-2\"],\n      \"resources\": [\n        {\n          \"type\": \"video\",\n          \"title\": \"H\u01b0\u1edbng d\u1eabn thi\u1ec1n \u0111\u1ecbnh cho ng\u01b0\u1eddi m\u1edbi b\u1eaft \u0111\u1ea7u\",\n          \"url\": \"https://example.com/meditation-for-beginners\",\n          \"description\": \"Video h\u01b0\u1edbng d\u1eabn thi\u1ec1n \u0111\u1ecbnh \u0111\u01a1n gi\u1ea3n.\"\n        }\n      ]\n    },\n    {\n      \"id\": \"node-2\",\n      \"name\": \"Ph\u00e1t Tri\u1ec3n K\u1ebft N\u1ed1i C\u1ea3m X\u00fac V\u1edbi Con\",\n      \"description\": \"X\u00e2y d\u1ef1ng m\u1ed1i quan h\u1ec7 g\u1eafn b\u00f3 v\u00e0 tin c\u1eady v\u1edbi con th\u00f4ng qua s\u1ef1 th\u1ea5u hi\u1ec3u v\u00e0 \u0111\u1ed3ng c\u1ea3m.\",\n      \"content\": \"K\u1ebft n\u1ed1i c\u1ea3m x\u00fac l\u00e0 n\u1ec1n t\u1ea3ng c\u1ee7a m\u1ed1i quan h\u1ec7 cha m\u1eb9 - con c\u00e1i. \u0110\u1ec3 ph\u00e1t tri\u1ec3n k\u1ebft n\u1ed1i c\u1ea3m x\u00fac, cha m\u1eb9 c\u1ea7n: 1. D\u00e0nh th\u1eddi gian ch\u1ea5t l\u01b0\u1ee3ng cho con: Ch\u01a1i c\u00f9ng con, tr\u00f2 chuy\u1ec7n v\u1edbi con, l\u1eafng nghe con chia s\u1ebb. 2. Th\u1ec3 hi\u1ec7n t\u00ecnh y\u00eau th\u01b0\u01a1ng: \u00d4m con, n\u00f3i l\u1eddi y\u00eau th\u01b0\u01a1ng, khen ng\u1ee3i con. 3. Th\u1ea5u hi\u1ec3u c\u1ea3m x\u00fac c\u1ee7a con: G\u1ecdi t\u00ean c\u1ea3m x\u00fac c\u1ee7a con, gi\u00fap con nh\u1eadn bi\u1ebft v\u00e0 di\u1ec5n \u0111\u1ea1t c\u1ea3m x\u00fac. 4. \u0110\u1ed3ng c\u1ea3m v\u1edbi con: \u0110\u1eb7t m\u00ecnh v\u00e0o v\u1ecb tr\u00ed c\u1ee7a con \u0111\u1ec3 hi\u1ec3u con h\u01a1n.\\n\\n**K\u1ecbch b\u1ea3n \u0111\u1ed1i tho\u1ea1i:**\\nCon: 'Con kh\u00f4ng mu\u1ed1n \u0111i h\u1ecdc!'\\nCha m\u1eb9 (thay v\u00ec qu\u00e1t m\u1eafng): 'M\u1eb9/Ba th\u1ea5y con c\u00f3 v\u1ebb kh\u00f4ng vui. Con c\u00f3 th\u1ec3 chia s\u1ebb v\u1edbi m\u1eb9/ba \u0111i\u1ec1u g\u00ec khi\u1ebfn con kh\u00f4ng mu\u1ed1n \u0111i h\u1ecdc kh\u00f4ng?'\\n\\n**B\u00e0i t\u1eadp:** M\u1ed7i ng\u00e0y, h\u00e3y d\u00e0nh \u00edt nh\u1ea5t 15 ph\u00fat \u0111\u1ec3 tr\u00f2 chuy\u1ec7n v\u00e0 ch\u01a1i \u0111\u00f9a c\u00f9ng con m\u00e0 kh\u00f4ng b\u1ecb xao nh\u00e3ng b\u1edfi \u0111i\u1ec7n tho\u1ea1i hay c\u00f4ng vi\u1ec7c.\",\n      \"type\": \"concept\",\n      \"children\": [\"node-3\"],\n      \"resources\": [\n        {\n          \"type\": \"article\",\n          \"title\": \"T\u1ea7m quan tr\u1ecdng c\u1ee7a k\u1ebft n\u1ed1i c\u1ea3m x\u00fac gi\u1eefa cha m\u1eb9 v\u00e0 con c\u00e1i\",\n          \"url\": \"https://example.com/emotional-connection-parent-child\",\n          \"description\": \"B\u00e0i vi\u1ebft v\u1ec1 t\u1ea7m quan tr\u1ecdng c\u1ee7a k\u1ebft n\u1ed1i c\u1ea3m x\u00fac.\"\n        }\n      ]\n    },\n    {\n      \"id\": \"node-3\",\n      \"name\": \"K\u1ef9 N\u0103ng L\u1eafng Nghe v\u00e0 Giao Ti\u1ebfp Hi\u1ec7u Qu\u1ea3\",\n      \"description\": \"H\u1ecdc c\u00e1ch l\u1eafng nghe con m\u1ed9t c\u00e1ch ch\u00e2n th\u00e0nh v\u00e0 giao ti\u1ebfp v\u1edbi con m\u1ed9t c\u00e1ch t\u00f4n tr\u1ecdng.\",\n      \"content\": \"L\u1eafng nghe t\u00edch c\u1ef1c l\u00e0 ch\u00eca kh\u00f3a \u0111\u1ec3 giao ti\u1ebfp hi\u1ec7u qu\u1ea3. \u0110\u1ec3 l\u1eafng nghe con, cha m\u1eb9 c\u1ea7n: 1. T\u1eadp trung ho\u00e0n to\u00e0n v\u00e0o con: T\u1eaft \u0111i\u1ec7n tho\u1ea1i, ng\u1eebng l\u00e0m vi\u1ec7c kh\u00e1c. 2. L\u1eafng nghe b\u1eb1ng c\u1ea3 tr\u00e1i tim: C\u1ea3m nh\u1eadn c\u1ea3m x\u00fac c\u1ee7a con. 3. Kh\u00f4ng ng\u1eaft l\u1eddi con: \u0110\u1ec3 con n\u00f3i h\u1ebft \u00fd c\u1ee7a m\u00ecnh. 4. Ph\u1ea3n h\u1ed3i m\u1ed9t c\u00e1ch t\u00edch c\u1ef1c: G\u1eadt \u0111\u1ea7u, m\u1ec9m c\u01b0\u1eddi, \u0111\u1eb7t c\u00e2u h\u1ecfi m\u1edf. Giao ti\u1ebfp hi\u1ec7u qu\u1ea3 bao g\u1ed3m: 1. S\u1eed d\u1ee5ng ng\u00f4n ng\u1eef t\u00edch c\u1ef1c: Tr\u00e1nh ch\u1ec9 tr\u00edch, \u0111\u1ed5 l\u1ed7i, thay v\u00e0o \u0111\u00f3 h\u00e3y n\u00f3i v\u1ec1 c\u1ea3m x\u00fac c\u1ee7a b\u1ea1n v\u00e0 h\u00e0nh vi c\u1ee5 th\u1ec3 c\u1ee7a con. 2. S\u1eed d\u1ee5ng ng\u00f4n ng\u1eef 'T\u00f4i': 'M\u1eb9 c\u1ea3m th\u1ea5y lo l\u1eafng khi con \u0111i ch\u01a1i v\u1ec1 mu\u1ed9n m\u00e0 kh\u00f4ng b\u00e1o tr\u01b0\u1edbc.' 3. T\u00f4n tr\u1ecdng con: Kh\u00f4ng la m\u1eafng, x\u00fac ph\u1ea1m con.\\n\\n**K\u1ecbch b\u1ea3n \u0111\u1ed1i tho\u1ea1i:**\\nCon: 'B\u1ea1n A kh\u00f4ng ch\u01a1i v\u1edbi con n\u1eefa!'\\nCha m\u1eb9 (thay v\u00ec n\u00f3i 'C\u00f3 g\u00ec \u0111\u00e2u m\u00e0 bu\u1ed3n'): 'M\u1eb9/Ba th\u1ea5y con c\u00f3 v\u1ebb bu\u1ed3n. Con c\u00f3 mu\u1ed1n k\u1ec3 cho m\u1eb9/ba nghe chuy\u1ec7n g\u00ec \u0111\u00e3 x\u1ea3y ra kh\u00f4ng?'\\n\\n**B\u00e0i t\u1eadp:** Th\u1ef1c h\u00e0nh l\u1eafng nghe con trong 5 ph\u00fat m\u00e0 kh\u00f4ng ng\u1eaft l\u1eddi, kh\u00f4ng ph\u00e1n x\u00e9t v\u00e0 kh\u00f4ng \u0111\u01b0a ra l\u1eddi khuy\u00ean.\",\n      \"type\": \"practice\",\n      \"children\": [\"node-4\"],\n      \"resources\": [\n        {\n          \"type\": \"book\",\n          \"title\": \"Ngh\u1ec7 thu\u1eadt giao ti\u1ebfp v\u1edbi con\",\n          \"url\": \"https://example.com/communication-with-children\",\n          \"description\": \"S\u00e1ch v\u1ec1 k\u1ef9 n\u0103ng giao ti\u1ebfp v\u1edbi tr\u1ebb em.\"\n        }\n      ]\n    },\n    {\n      \"id\": \"node-4\",\n      \"name\": \"\u0110\u1eb7t Ranh Gi\u1edbi L\u00e0nh M\u1ea1nh\",\n      \"description\": \"Thi\u1ebft l\u1eadp c\u00e1c quy t\u1eafc v\u00e0 gi\u1edbi h\u1ea1n r\u00f5 r\u00e0ng, nh\u1ea5t qu\u00e1n v\u00e0 t\u00f4n tr\u1ecdng.\",\n      \"content\": \"Ranh gi\u1edbi l\u00e0nh m\u1ea1nh gi\u00fap con c\u1ea3m th\u1ea5y an to\u00e0n v\u00e0 h\u1ecdc c\u00e1ch t\u1ef1 \u0111i\u1ec1u ch\u1ec9nh h\u00e0nh vi. \u0110\u1ec3 \u0111\u1eb7t ranh gi\u1edbi, cha m\u1eb9 c\u1ea7n: 1. R\u00f5 r\u00e0ng: Quy t\u1eafc c\u1ea7n c\u1ee5 th\u1ec3 v\u00e0 d\u1ec5 hi\u1ec3u. 2. Nh\u1ea5t qu\u00e1n: \u00c1p d\u1ee5ng quy t\u1eafc m\u1ed9t c\u00e1ch nh\u1ea5t qu\u00e1n, kh\u00f4ng thay \u0111\u1ed5i t\u00f9y ti\u1ec7n. 3. T\u00f4n tr\u1ecdng: Gi\u1ea3i th\u00edch l\u00fd do \u0111\u1eb1ng sau quy t\u1eafc v\u00e0 l\u1eafng nghe \u00fd ki\u1ebfn c\u1ee7a con. 4. H\u1eadu qu\u1ea3 h\u1ee3p l\u00fd: N\u1ebfu con vi ph\u1ea1m quy t\u1eafc, c\u1ea7n c\u00f3 h\u1eadu qu\u1ea3 ph\u00f9 h\u1ee3p v\u00e0 \u0111\u00e3 \u0111\u01b0\u1ee3c th\u1ed1ng nh\u1ea5t tr\u01b0\u1edbc. 5. Linh ho\u1ea1t: \u0110i\u1ec1u ch\u1ec9nh ranh gi\u1edbi khi con l\u1edbn l\u00ean.\\n\\n**V\u00ed d\u1ee5 th\u1ef1c t\u1ebf:** Thay v\u00ec n\u00f3i 'Kh\u00f4ng \u0111\u01b0\u1ee3c xem tivi n\u1eefa!', h\u00e3y n\u00f3i '\u0110\u00e3 \u0111\u1ebfn gi\u1edd \u0111i ng\u1ee7 r\u1ed3i con. Con c\u00f3 th\u1ec3 xem tivi v\u00e0o ng\u00e0y mai sau khi l\u00e0m xong b\u00e0i t\u1eadp.'\\n\\n**B\u00e0i t\u1eadp:** L\u1eadp danh s\u00e1ch c\u00e1c quy t\u1eafc trong gia \u0111\u00ecnh v\u00e0 th\u1ea3o lu\u1eadn v\u1edbi con v\u1ec1 l\u00fd do v\u00e0 h\u1eadu qu\u1ea3 c\u1ee7a t\u1eebng quy t\u1eafc.\",\n      \"type\": \"concept\",\n      \"children\": [\"node-5\"],\n      \"resources\": [\n        {\n          \"type\": \"article\",\n          \"title\": \"C\u00e1ch \u0111\u1eb7t ranh gi\u1edbi l\u00e0nh m\u1ea1nh v\u1edbi con\",\n          \"url\": \"https://example.com/setting-healthy-boundaries\",\n          \"description\": \"B\u00e0i vi\u1ebft h\u01b0\u1edbng d\u1eabn c\u00e1ch \u0111\u1eb7t ranh gi\u1edbi v\u1edbi con.\"\n        }\n      ]\n    },\n    {\n      \"id\": \"node-5\",\n      \"name\": \"D\u1ea1y Con V\u1ec1 Ch\u00e1nh Ni\u1ec7m v\u00e0 \u0110i\u1ec1u H\u00f2a C\u1ea3m X\u00fac\",\n      \"description\": \"Gi\u00fap con nh\u1eadn bi\u1ebft, hi\u1ec3u v\u00e0 qu\u1ea3n l\u00fd c\u1ea3m x\u00fac c\u1ee7a m\u00ecnh m\u1ed9t c\u00e1ch hi\u1ec7u qu\u1ea3.\",\n      \"content\": \"D\u1ea1y con v\u1ec1 ch\u00e1nh ni\u1ec7m v\u00e0 \u0111i\u1ec1u h\u00f2a c\u1ea3m x\u00fac l\u00e0 gi\u00fap con ph\u00e1t tri\u1ec3n tr\u00ed tu\u1ec7 c\u1ea3m x\u00fac (EQ). Cha m\u1eb9 c\u00f3 th\u1ec3: 1. G\u1ecdi t\u00ean c\u1ea3m x\u00fac: 'M\u1eb9 th\u1ea5y con \u0111ang bu\u1ed3n/vui/t\u1ee9c gi\u1eadn.' 2. D\u1ea1y con v\u1ec1 c\u00e1c lo\u1ea1i c\u1ea3m x\u00fac kh\u00e1c nhau. 3. H\u01b0\u1edbng d\u1eabn con c\u00e1ch th\u1ec3 hi\u1ec7n c\u1ea3m x\u00fac m\u1ed9t c\u00e1ch l\u00e0nh m\u1ea1nh: V\u1ebd, vi\u1ebft nh\u1eadt k\u00fd, t\u1eadp th\u1ec3 d\u1ee5c, n\u00f3i chuy\u1ec7n v\u1edbi ng\u01b0\u1eddi l\u1edbn. 4. D\u1ea1y con c\u00e1c k\u1ef9 thu\u1eadt th\u01b0 gi\u00e3n: H\u00edt th\u1edf s\u00e2u, thi\u1ec1n \u0111\u1ecbnh, yoga. 5. L\u00e0m g\u01b0\u01a1ng cho con: Cha m\u1eb9 c\u0169ng c\u1ea7n th\u1ef1c h\u00e0nh ch\u00e1nh ni\u1ec7m v\u00e0 \u0111i\u1ec1u h\u00f2a c\u1ea3m x\u00fac.\\n\\n**K\u1ecbch b\u1ea3n \u0111\u1ed1i tho\u1ea1i:**\\nCon: 'Con gh\u00e9t em!'\\nCha m\u1eb9 (thay v\u00ec n\u00f3i 'Kh\u00f4ng \u0111\u01b0\u1ee3c n\u00f3i th\u1ebf!'): 'M\u1eb9/Ba th\u1ea5y con \u0111ang r\u1ea5t t\u1ee9c gi\u1eadn. Con c\u00f3 th\u1ec3 n\u00f3i cho m\u1eb9/ba bi\u1ebft \u0111i\u1ec1u g\u00ec khi\u1ebfn con t\u1ee9c gi\u1eadn kh\u00f4ng?'\\n\\n**B\u00e0i t\u1eadp:** C\u00f9ng con th\u1ef1c h\u00e0nh b\u00e0i t\u1eadp h\u00edt th\u1edf s\u00e2u khi con c\u1ea3m th\u1ea5y c\u0103ng th\u1eb3ng ho\u1eb7c t\u1ee9c gi\u1eadn.\",\n      \"type\": \"practice\",\n      \"children\": [\"node-6\"],\n      \"resources\": [\n        {\n          \"type\": \"app\",\n          \"title\": \"\u1ee8ng d\u1ee5ng thi\u1ec1n \u0111\u1ecbnh cho tr\u1ebb em\",\n          \"url\": \"https://example.com/meditation-app-for-kids\",\n          \"description\": \"\u1ee8ng d\u1ee5ng gi\u00fap tr\u1ebb em th\u1ef1c h\u00e0nh thi\u1ec1n \u0111\u1ecbnh.\"\n        }\n      ]\n    },\n    {\n      \"id\": \"node-6\",\n      \"name\": \"X\u1eed L\u00fd Xung \u0110\u1ed9t v\u00e0 Th\u00e1ch Th\u1ee9c Trong Gia \u0110\u00ecnh\",\n      \"description\": \"\u00c1p d\u1ee5ng ch\u00e1nh ni\u1ec7m \u0111\u1ec3 gi\u1ea3i quy\u1ebft c\u00e1c m\u00e2u thu\u1eabn v\u00e0 kh\u00f3 kh\u0103n m\u1ed9t c\u00e1ch b\u00ecnh t\u0129nh v\u00e0 hi\u1ec7u qu\u1ea3.\",\n      \"content\": \"Xung \u0111\u1ed9t l\u00e0 \u0111i\u1ec1u kh\u00f4ng th\u1ec3 tr\u00e1nh kh\u1ecfi trong gia \u0111\u00ecnh. \u0110\u1ec3 x\u1eed l\u00fd xung \u0111\u1ed9t m\u1ed9t c\u00e1ch ch\u00e1nh ni\u1ec7m, cha m\u1eb9 c\u1ea7n: 1. Gi\u1eef b\u00ecnh t\u0129nh: H\u00edt th\u1edf s\u00e2u, tr\u00e1nh ph\u1ea3n \u1ee9ng th\u00e1i qu\u00e1. 2. L\u1eafng nghe t\u1ea5t c\u1ea3 c\u00e1c b\u00ean: \u0110\u1ec3 m\u1ecdi ng\u01b0\u1eddi c\u00f3 c\u01a1 h\u1ed9i b\u00e0y t\u1ecf quan \u0111i\u1ec3m. 3. T\u00ecm hi\u1ec3u nguy\u00ean nh\u00e2n g\u1ed1c r\u1ec5 c\u1ee7a xung \u0111\u1ed9t. 4. T\u00ecm ki\u1ebfm gi\u1ea3i ph\u00e1p \u0111\u00f4i b\u00ean c\u00f9ng c\u00f3 l\u1ee3i. 5. Th\u1ec3 hi\u1ec7n s\u1ef1 tha th\u1ee9 v\u00e0 bao dung.\\n\\n**V\u00ed d\u1ee5 th\u1ef1c t\u1ebf:** Khi hai anh em tranh gi\u00e0nh \u0111\u1ed3 ch\u01a1i, thay v\u00ec qu\u00e1t m\u1eafng, h\u00e3y n\u00f3i: 'M\u1eb9 th\u1ea5y hai con \u0111ang tranh gi\u00e0nh \u0111\u1ed3 ch\u01a1i. Ch\u00fang ta c\u00f9ng nhau t\u00ecm c\u00e1ch gi\u1ea3i quy\u1ebft nh\u00e9. C\u00f3 th\u1ec3 hai con thay phi\u00ean nhau ch\u01a1i, ho\u1eb7c t\u00ecm m\u1ed9t tr\u00f2 ch\u01a1i kh\u00e1c m\u00e0 c\u1ea3 hai c\u00f9ng th\u00edch.'\\n\\n**B\u00e0i t\u1eadp:** Khi c\u00f3 xung \u0111\u1ed9t x\u1ea3y ra, h\u00e3y d\u00e0nh th\u1eddi gian \u0111\u1ec3 suy ng\u1eabm v\u1ec1 nguy\u00ean nh\u00e2n v\u00e0 c\u00e1ch gi\u1ea3i quy\u1ebft theo h\u01b0\u1edbng ch\u00e1nh ni\u1ec7m.\",\n      \"type\": \"concept\",\n      \"children\": [],\n      \"resources\": [\n        {\n          \"type\": \"article\",\n          \"title\": \"Gi\u1ea3i quy\u1ebft xung \u0111\u1ed9t gia \u0111\u00ecnh m\u1ed9t c\u00e1ch ch\u00e1nh ni\u1ec7m\",\n          \"url\": \"https://example.com/mindful-conflict-resolution\",\n          \"description\": \"B\u00e0i vi\u1ebft v\u1ec1 c\u00e1ch gi\u1ea3i quy\u1ebft xung \u0111\u1ed9t gia \u0111\u00ecnh.\"\n        }\n      ]\n    }\n  ]\n}\n```\n",
    "metadata": {
        "usage": {
            "prompt_tokens": 712,
            "prompt_unit_price": "0",
            "prompt_price_unit": "0.000001",
            "prompt_price": "0",
            "completion_tokens": 3116,
            "completion_unit_price": "0",
            "completion_price_unit": "0.000001",
            "completion_price": "0",
            "total_tokens": 3828,
            "total_price": "0",
            "currency": "USD",
            "latency": 39.49973866343498
        }
    },
    "created_at": 1741828937
}```

### Chat với file
````

curl --location 'https://ai.dreamapi.net/v1/chat-messages' \
--header 'Authorization: Bearer app-dify-api-key' \
--header 'Content-Type: application/json' \
--data '{
"inputs": {},
"query": "đây là gì",
"response_mode": "blocking",
"conversation_id": "",
"user": "abc-123",
"files": [{
"type": "image",
"transfer_method": "remote_url",
"url": "https://c.files.bbci.co.uk/3FE8/production/_104706361_1df2aee2-0fb6-4571-ab4a-8caf3505b856.jpg"
}]
}'

```

### Upload tệp tạm thời
curl --location 'https://temp.ws.pho.to/upload.php' \
--header 'Host: temp.ws.pho.to' \
--header 'user-agent: toonMeAndroid-v815' \
--form 'no_resize="1"' \
--form 'image=@"/Users/trungkientn/Desktop/Temp/Image 768x1024.png"'


Return: 
https://temp-images.ws.pho.to/bab5f50fb1956ac30254548b659bd6c9065f56c1.png
```
