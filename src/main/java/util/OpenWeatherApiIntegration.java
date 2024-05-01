package util;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import util.dto.ApiCitiesResponse;
import util.dto.ApiWeatherResponse;
import util.dto.WeatherResponse;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static util.Mapper.mapper;

@Slf4j
@RequiredArgsConstructor
public class OpenWeatherApiIntegration {
    private final static String API_KEY = "56d35719b8faab070e7324eab6b45e73";
    private final static String API_URL_WEATHER = "https://api.openweathermap.org/data/2.5/weather?lat=%f&lon=%f&appid=" + API_KEY;
    private final static String API_URL_COORDINATES = "http://api.openweathermap.org/geo/1.0/direct?q=%s&limit=100&appid=" + API_KEY;

    public String getCityCoordinatesByName(String name) throws ExecutionException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(API_URL_COORDINATES.formatted(name)))
                .GET()
                .build();
        HttpClient httpClient = HttpClient.newHttpClient();
        try {
            HttpResponse<String> httpResponse = httpClient.send(
                    httpRequest, HttpResponse.BodyHandlers.ofString());

            List<ApiCitiesResponse> apiCitiesResponses = mapper.readValue(httpResponse.body(),
                    mapper.getTypeFactory().constructCollectionType(List.class, ApiCitiesResponse.class));
            return mapper.writeValueAsString(apiCitiesResponses);
        } catch (Exception e) {
            log.error("Exception while retrieving city coordinates by name: %s".formatted(e.getMessage()));
            return null;
        }
    }

    public WeatherResponse getWeather(double lat, double lon) throws ExecutionException, InterruptedException, JsonProcessingException {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(API_URL_WEATHER.formatted(lat, lon)))
                .GET()
                .build();
        HttpClient httpClient = HttpClient.newHttpClient();
        try {
            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            ApiWeatherResponse apiWeatherResponse = mapper.readValue(httpResponse.body(), ApiWeatherResponse.class);
            Map<String, Object> main = (Map<String, Object>) apiWeatherResponse.main();
            Map<String, Object> wind = (Map<String, Object>) apiWeatherResponse.wind();

            return new WeatherResponse(
                    main.get("temp").toString(),
                    main.get("feels_like").toString(),
                    main.get("pressure").toString(),
                    main.get("humidity").toString(),
                    wind.get("speed").toString(),
                    wind.get("deg").toString()
            );
        } catch (Exception e) {
            log.error("Exception while retrieving weather data: %s".formatted(e.getMessage()));
            return null;
        }
    }
}
