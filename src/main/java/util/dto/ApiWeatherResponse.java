package util.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ApiWeatherResponse(
    Object main,
    Object wind
) {
}
