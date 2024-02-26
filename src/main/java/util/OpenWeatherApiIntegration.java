package util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import util.dto.ApiCitiesResponse;
import util.dto.ApiWeatherResponse;
import util.dto.WeatherResponse;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static util.Mapper.mapper;

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
        CompletableFuture<HttpResponse<String>> httpResponseFuture = httpClient.sendAsync(
                httpRequest, HttpResponse.BodyHandlers.ofString());
        httpResponseFuture.thenApply(response -> {
            try {
                List<ApiCitiesResponse> apiCitiesResponses = mapper.readValue(response.body(), mapper.getTypeFactory().constructCollectionType(
                        List.class, ApiCitiesResponse.class));
                return mapper.writeValueAsString(apiCitiesResponses);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        });
        return httpResponseFuture.get().body();
    }

    public WeatherResponse getWeather(double lat, double lon) throws ExecutionException, InterruptedException, JsonProcessingException {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(API_URL_WEATHER.formatted(lat, lon)))
                .GET()
                .build();
        HttpClient httpClient = HttpClient.newHttpClient();
        CompletableFuture<HttpResponse<String>> httpResponseFuture = httpClient.sendAsync(
                httpRequest, HttpResponse.BodyHandlers.ofString());
        httpResponseFuture.thenApply(response -> {
            try {
                ApiWeatherResponse apiWeatherResponse = mapper.readValue(response.body(), ApiWeatherResponse.class);

                WeatherResponse weatherResponse = new WeatherResponse(
                        ((Map<String, Double>) apiWeatherResponse.main()).get("temp").toString(),
                        ((Map<String, Double>) apiWeatherResponse.main()).get("feels_like").toString(),
                        ((Map<String, Integer>) apiWeatherResponse.main()).get("pressure").toString(),
                        ((Map<String, Integer>) apiWeatherResponse.main()).get("humidity").toString(),
                        ((Map<String, Double>) apiWeatherResponse.wind()).get("speed").toString(),
                        ((Map<String, Integer>) apiWeatherResponse.wind()).get("deg").toString()
                );
                return weatherResponse;
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        });
        return mapper.readValue(httpResponseFuture.get().body(), WeatherResponse.class);
    }
}
