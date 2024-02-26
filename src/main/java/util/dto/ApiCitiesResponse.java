package util.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ApiCitiesResponse(
   String name,
   Double lat,
   Double lon,
   String country,
   String state
) {
}
