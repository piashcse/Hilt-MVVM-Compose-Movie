# Hướng dẫn sử dụng Weather API (Phiên bản cơ bản)

> Tài liệu này được thiết kế cho các sản phẩm không chuyên về thời tiết nhưng cần tích hợp tính năng thời tiết đơn giản.

## Giới thiệu

Weather API của The Weather Company (IBM) - self hosted cung cấp dữ liệu thời tiết đáng tin cậy từ các nguồn toàn cầu. Hướng dẫn này giúp bạn tích hợp các tính năng thời tiết cơ bản vào ứng dụng của mình với nỗ lực tối thiểu.

## API cơ bản cần thiết

### 1. API Điều kiện thời tiết hiện tại

**Endpoint**: `/v3/wx/observations/current/{geocode}`

API này cung cấp điều kiện thời tiết hiện tại tại một vị trí cụ thể:

- Cập nhật mỗi 15-30 phút
- Bao gồm: nhiệt độ, độ ẩm, điều kiện thời tiết, tốc độ gió
- Trả về mã biểu tượng thời tiết để hiển thị trực quan

```json
// Ví dụ response đơn giản
{
  "temperature": 28,
  "temperatureFeelsLike": 30,
  "windSpeed": 12,
  "relativeHumidity": 65,
  "iconCode": 30,
  "wxPhraseLong": "Partly Cloudy",
  "uvIndex": 7
}
```

### 2. API Dự báo hàng ngày ngắn hạn

**Endpoint**: `/v3/wx/forecast/daily/3day/{geocode}`

API này cung cấp dự báo 3 ngày, đủ cho hầu hết các ứng dụng không chuyên về thời tiết:

- Cập nhật mỗi 6 giờ
- Bao gồm: nhiệt độ cao/thấp, mô tả thời tiết, xác suất mưa
- Mô tả ngắn gọn về điều kiện thời tiết hàng ngày

## Xác thực

### API Key

```
headers: {
  'X-ApiKey': '9f8b7c4a3d2e1f0a9a8b7c4a3d2e1f0'
}
```

## Tham số cơ bản

### Định dạng geocode

Tọa độ vị trí là thông số bắt buộc để nhận dữ liệu thời tiết:

- Format: `{latitude},{longitude}`
- Ví dụ: `21.0285,105.8542` (Hà Nội)

### Đơn vị đo lường

Chọn hệ đo lường phù hợp sử dụng tham số `units`:

- `e`: Hệ Anh (°F)
- `m`: Hệ mét (°C) - Khuyến nghị cho Việt Nam
- `h`: Hệ hỗn hợp
- `s`: Hệ SI

### Ngôn ngữ

Chọn ngôn ngữ hiển thị:

```
language=vi-VN (tiếng Việt)
language=en-US (tiếng Anh)
```

## Ví dụ tích hợp đơn giản

### HTML/JavaScript

```html
<div id="weather-widget">
  <div class="current-temp">--°C</div>
  <div class="weather-condition">---</div>
  <div class="forecast">
    <div class="day">
      <div class="day-name">---</div>
      <div class="temp-high">--°C</div>
      <div class="temp-low">--°C</div>
    </div>
    <!-- Thêm các thẻ day tương tự -->
  </div>
</div>

<script>
  const API_KEY = "your_api_key";
  const LAT = 21.0285;
  const LON = 105.8542;

  // Lấy thời tiết hiện tại
  async function getCurrentWeather() {
    try {
      const response = await fetch(
        `https://weather.dreamapi.net/v3/wx/observations/current/${LAT},${LON}?apiKey=${API_KEY}&units=m&language=vi-VN`,
        {
          headers: { "X-ApiKey": API_KEY },
        }
      );

      if (!response.ok) throw new Error("Weather data unavailable");

      const data = await response.json();

      // Cập nhật UI
      document.querySelector(".current-temp").textContent = `${Math.round(
        data.temperature
      )}°C`;
      document.querySelector(".weather-condition").textContent =
        data.wxPhraseLong;
    } catch (error) {
      console.error("Error fetching current weather:", error);
      document.querySelector(".weather-condition").textContent =
        "Không thể tải dữ liệu thời tiết";
    }
  }

  // Gọi hàm khi trang tải
  document.addEventListener("DOMContentLoaded", getCurrentWeather);
</script>
```

## Các trường dữ liệu chính

### Thời tiết hiện tại

- `temperature`: Nhiệt độ hiện tại
- `temperatureFeelsLike`: Nhiệt độ cảm nhận được
- `relativeHumidity`: Độ ẩm tương đối (%)
- `windSpeed`: Tốc độ gió
- `windDirectionCardinal`: Hướng gió (N, S, E, W)
- `uvIndex`: Chỉ số UV
- `wxPhraseLong`: Mô tả điều kiện thời tiết
- `iconCode`: Mã biểu tượng thời tiết

### Dự báo

- `temperatureMax`: Nhiệt độ cao nhất trong ngày
- `temperatureMin`: Nhiệt độ thấp nhất trong ngày
- `dayOfWeek`: Ngày trong tuần
- `narrative`: Mô tả thời tiết
- `iconCode`: Mã biểu tượng thời tiết
- `precipChance`: Xác suất mưa (%)

## Xử lý lỗi

Các lỗi phổ biến và cách xử lý:

1. **API key không hợp lệ (401)**

   - Kiểm tra lại API key
   - Đảm bảo key vẫn hoạt động

2. **Vượt quá giới hạn (429)**
   - Giảm tần suất gọi API
   - Cân nhắc nâng cấp gói dịch vụ
3. **Dữ liệu không khả dụng**
   - Hiển thị thông báo thân thiện với người dùng
   - Sử dụng dữ liệu đã lưu trong bộ nhớ cache nếu có

## Yêu cầu hiển thị nguồn dữ liệu

The Weather Company yêu cầu hiển thị nguồn dữ liệu trên ứng dụng của bạn:

```html
<div class="attribution">Powered by IBM's The Weather Company</div>
```

## Tối ưu hiệu suất

1. **Lưu dữ liệu vào bộ nhớ cache**

   - Thời tiết hiện tại: 15-30 phút
   - Dự báo: 3-6 giờ

2. **Giảm thiểu API calls**

   - Chỉ tải dữ liệu khi cần
   - Sử dụng `fields` để chỉ lấy dữ liệu cần thiết

3. **Xử lý offline**
   - Lưu dữ liệu gần đây cho trường hợp mất kết nối

## Giới hạn

1. Giới hạn tần suất gọi tùy theo gói dịch vụ (thường 10-30 lượt gọi/phút cho gói cơ bản)
