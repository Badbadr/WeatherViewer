package util.dto;

public record WeatherResponse(
    String temperature,
    String feelsLikeTemperature,
    String pressure,
    String humidity,
    String windSpeed,
    String windDegree
) {
}
