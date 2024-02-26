package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import entity.Location;
import lombok.RequiredArgsConstructor;
import repository.jpa.LocationRepository;
import util.OpenWeatherApiIntegration;
import util.dto.WeatherResponse;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository = new LocationRepository();
    private final OpenWeatherApiIntegration openWeatherApi = new OpenWeatherApiIntegration();

    public List<Location> searchSavedLocations(String name, int page, int pageSize) {
        return locationRepository.search(name, page, pageSize);
    }

    public Location save(Location location) {
        return locationRepository.save(location);
    }

    public String getByName(String name) throws ExecutionException, InterruptedException {
        return openWeatherApi.getCityCoordinatesByName(name);
    }

    public WeatherResponse getWeatherByLatLon(double lat, double lon) throws ExecutionException, InterruptedException, JsonProcessingException {
        return openWeatherApi.getWeather(lat, lon);
    }
}
