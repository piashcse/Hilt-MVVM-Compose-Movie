# Hướng dẫn chi tiết sử dụng Weather API từ weather.com

## Tổng quan

Weather API từ The Weather Company (một đơn vị của IBM) cung cấp dữ liệu thời tiết đáng tin cậy nhất thế giới. API này phục vụ hơn 3.5 tỷ vị trí toàn cầu với độ chính xác cao, cập nhật theo thời gian thực và được sử dụng bởi các tổ chức lớn, ứng dụng thời tiết hàng đầu, và dịch vụ dự báo chuyên nghiệp.

## Các API chính

### 1. API Điều kiện thời tiết hiện tại

**Endpoint**: `/v3/wx/observations/current/{geocode}`

API này cung cấp điều kiện thời tiết hiện tại tại một vị trí cụ thể:

- Cập nhật mỗi 15-30 phút
- Bao gồm: nhiệt độ, nhiệt độ cảm nhận, độ ẩm, áp suất, tốc độ gió, hướng gió, tầm nhìn, mây phủ
- Cung cấp mã biểu tượng thời tiết để hiển thị trực quan
- Chỉ số UV, điểm nóng/lạnh, và dữ liệu chất lượng không khí (nếu có)

### 2. API Dự báo hàng ngày

**Endpoint**: `/v3/wx/forecast/daily/{days}/{geocode}`

API này cung cấp dự báo từ 1-15 ngày:

- Cập nhật mỗi 6 giờ
- Chi tiết cho từng ngày: nhiệt độ cao/thấp, bình minh/hoàng hôn, gió, điều kiện
- Chỉ số UV, xác suất mưa, độ ẩm, dữ liệu lượng mưa
- Có thể yêu cầu số ngày cụ thể: 1day, 3day, 5day, 7day, 10day, 15day

### 3. API Dự báo theo giờ

**Endpoint**: `/v3/wx/forecast/hourly/{hours}/{geocode}`

API này cung cấp dự báo chi tiết theo giờ:

- Dự báo chi tiết lên đến 240 giờ (10 ngày)
- Cập nhật thường xuyên
- Bao gồm: nhiệt độ, độ ẩm, tốc độ gió, hướng gió, xác suất mưa, lượng mưa
- Mã biểu tượng thời tiết và mô tả cho mỗi giờ
- Có thể yêu cầu số giờ cụ thể: 2hour, 6hour, 12hour, 24hour, etc.

### 4. API Cảnh báo thời tiết

**Endpoints**:

- Tiêu đề cảnh báo: `/v3/alerts/headlines/{geocode}`
- Chi tiết cảnh báo: `/v3/alerts/detail/{alertId}`

API này cung cấp thông tin về các cảnh báo thời tiết nguy hiểm:

- Cảnh báo chính thức từ các cơ quan khí tượng quốc gia
- Bao gồm bão, lũ lụt, nắng nóng, sấm sét, bão tuyết
- Mức độ nghiêm trọng, thời gian, vị trí bị ảnh hưởng
- Hướng dẫn an toàn kèm theo mỗi cảnh báo

### 5. API Dữ liệu lịch sử

**Endpoints**:

- Lịch sử khí hậu ngày: `/v3/wx/almanac/daily/{month}/{day}/{geocode}`
- Lịch sử khí hậu tháng: `/v3/wx/almanac/monthly/{month}/{geocode}`

API này cung cấp:

- Dữ liệu lịch sử thời tiết lên đến 30+ năm
- Các giá trị điển hình và kỷ lục cho ngày/tháng cụ thể
- Dữ liệu nhiệt độ, lượng mưa, tuyết, và các điều kiện khác
- Có thể sử dụng cho phân tích xu hướng và ứng dụng nghiên cứu

### 6. API Hình ảnh thời tiết

**Endpoint**: `/v3/wx/imagery/{layer}/{geocode}`

API này cung cấp các lớp hình ảnh thời tiết khác nhau:

- Radar mưa với độ phân giải cao
- Vệ tinh mây
- Nhiệt độ bề mặt
- Dự báo lượng mưa
- Các lớp chuyên biệt: ô nhiễm, bão, nhiệt độ, gió, tuyết
- Định dạng: PNG, GIF (động cho radar)

### 7. API Dịch vụ vị trí

**Endpoints**:

- Tìm kiếm vị trí: `/v3/location/search?query={searchTerm}`
- Chi tiết vị trí: `/v3/location/{locid}`
- Tọa độ ngược: `/v3/location/point?geocode={lat},{lon}`

API này hỗ trợ:

- Tìm kiếm vị trí theo tên, mã bưu chính, hoặc địa chỉ
- Lấy thông tin chi tiết về một vị trí cụ thể
- Chuyển đổi tọa độ thành địa chỉ và ngược lại
- Lấy thông tin múi giờ, dân số, và dữ liệu địa lý

## Xác thực và bảo mật

### API Key

Tất cả các yêu cầu đều yêu cầu API key, thêm vào header yêu cầu:

```
headers: {
  'X-ApiKey': '9f8b7c4a3d2e1f0a9a8b7c4a3d2e1f0'
}
```

## Định dạng dữ liệu

### Định dạng geocode

Tọa độ vị trí là thông số bắt buộc để nhận dữ liệu thời tiết:

- Format: `{latitude},{longitude}`
- Ví dụ: `21.0285,105.8542` (Hà Nội)
- Độ chính xác: 4 chữ số thập phân được khuyến nghị

### Đơn vị đo lường

Chọn hệ đo lường phù hợp sử dụng tham số `units`:

- `e`: Hệ Anh (°F)
- `m`: Hệ mét (°C) - Khuyến nghị cho Việt Nam
- `h`: Hệ hỗn hợp
- `s`: Hệ SI

### Ngôn ngữ

40+ ngôn ngữ được hỗ trợ cho văn bản mô tả thời tiết:

```
language=vi-VN (tiếng Việt)
language=en-US (tiếng Anh)
language=zh-CN (tiếng Trung)
```

## Xử lý lỗi và tối ưu hiệu suất

### Mã lỗi phổ biến

- `401`: Không được ủy quyền - Kiểm tra API key
- `404`: Không tìm thấy - Vị trí không hợp lệ hoặc endpoint không tồn tại
- `429`: Quá nhiều yêu cầu - Vượt quá giới hạn tần suất
- `500`: Lỗi máy chủ - Vấn đề với dịch vụ

### Chiến lược bộ nhớ cache

Để tối ưu hiệu suất và giảm chi phí:

- Lưu dữ liệu thời tiết hiện tại: 15-30 phút
- Lưu dự báo hàng ngày: 3-6 giờ
- Lưu dự báo theo giờ: 1-3 giờ
- Lưu dữ liệu vị trí: 24 giờ hoặc lâu hơn

### Mapping biểu tượng thời tiết

API trả về mã biểu tượng thời tiết (`iconCode`) để hiển thị trực quan. Một số mã phổ biến:

- `0-4`: Bão, sấm sét
- `5-8`: Mưa hỗn hợp, mưa đá
- `9-11`: Mưa nhỏ đến mưa vừa
- `12-18`: Mưa lớn, mưa đá, tuyết
- `19-29`: Tuyết với cường độ khác nhau
- `30-34`: Nóng, lạnh, gió
- `35-39`: Một phần có mây/nhiều mây
- `40-43`: Mưa vừa đến nhẹ
- `44-47`: Một phần có mây/nắng nhiều

Tham khảo tài liệu đầy đủ về mã biểu tượng để có danh sách hoàn chỉnh.

## Ví dụ mã nguồn

### JavaScript: Lấy điều kiện thời tiết hiện tại

```javascript
async function getCurrentWeather(latitude, longitude, apiKey) {
  try {
    const response = await fetch(
      `https://weather.dreamapi.net/v3/wx/observations/current/${latitude},${longitude}?apiKey=${apiKey}&units=m&language=vi-VN`,
      {
        headers: {
          "X-ApiKey": apiKey,
        },
      }
    );

    if (!response.ok) {
      throw new Error(`Weather API error: ${response.status}`);
    }

    const data = await response.json();

    return {
      temperature: data.temperature,
      feelsLike: data.temperatureFeelsLike,
      humidity: data.relativeHumidity,
      windSpeed: data.windSpeed,
      windDirection: data.windDirectionCardinal,
      condition: data.wxPhraseLong,
      iconCode: data.iconCode,
      uvIndex: data.uvIndex,
    };
  } catch (error) {
    console.error("Error fetching weather:", error);
    throw error;
  }
}
```

### Python: Lấy dự báo 5 ngày

```python
import requests

def get_five_day_forecast(latitude, longitude, api_key):
    url = f"https://weather.dreamapi.net/v3/wx/forecast/daily/5day/{latitude},{longitude}"

    headers = {
        "X-ApiKey": api_key
    }

    params = {
        "apiKey": api_key,
        "units": "m",
        "language": "vi-VN"
    }

    try:
        response = requests.get(url, headers=headers, params=params)
        response.raise_for_status()  # Raise exception for 4xx/5xx responses

        forecast_data = response.json()

        # Xử lý dữ liệu dự báo
        processed_forecast = []
        for i in range(5):
            daily = {
                "date": forecast_data["validTimeLocal"][i],
                "day_of_week": forecast_data["dayOfWeek"][i],
                "temp_max": forecast_data["temperatureMax"][i],
                "temp_min": forecast_data["temperatureMin"][i],
                "sunrise": forecast_data["sunriseTimeLocal"][i],
                "sunset": forecast_data["sunsetTimeLocal"][i],
                "narrative": forecast_data["narrative"][i],
                "icon_code": forecast_data["daypart"][0]["iconCode"][i*2],
                "precip_chance": forecast_data["daypart"][0]["precipChance"][i*2]
            }
            processed_forecast.append(daily)

        return processed_forecast

    except requests.exceptions.RequestException as e:
        print(f"Error fetching forecast: {e}")
        return None
```

## Giới hạn và lưu ý

2. Dữ liệu thời tiết có thể bị trễ 5-10 phút so với thời gian thực.
3. Cần hiển thị nguồn dữ liệu "Powered by IBM's The Weather Company" trên ứng dụng của bạn.
4. Mức độ chi tiết và độ chính xác khác nhau giữa các vị trí đô thị và vùng sâu vùng xa.
5. Cập nhật theo dõi các thay đổi API thông qua tài liệu API và thông báo từ The Weather Company.