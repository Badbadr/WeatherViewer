package rest.servlet.location;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
import rest.util.ParamsCollector;
import service.LocationService;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = "/api/location")
@NoArgsConstructor
public class LocationServlet extends HttpServlet {
    private static final LocationService locationService = new LocationService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String> params = ParamsCollector.collect(req.getQueryString());
        if (params.containsKey("name")) {
            try {
                resp.getWriter().write(locationService.getByName(params.get("name")));
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
