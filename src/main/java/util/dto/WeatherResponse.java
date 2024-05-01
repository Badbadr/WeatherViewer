package util.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record WeatherResponse(

    String temperature,
    String feelsLikeTemperature,
    String pressure,
    String humidity,
    String windSpeed,
    String windDegree
) {
}
