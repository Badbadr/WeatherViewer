package rest.servlet;

import config.ServletContextConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;
import service.LocationService;
import util.dto.WeatherResponse;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@WebServlet(urlPatterns = "/api/weather")
@NoArgsConstructor
public class WeatherServlet extends HttpServlet {

    private final LocationService locationService = new LocationService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        double lat = Double.parseDouble(req.getParameter("latlon").split(",")[0]);
        double lon = Double.parseDouble(req.getParameter("latlon").split(",")[1]);
        try {
            WeatherResponse weatherResponse = locationService.getWeatherByLatLon(lat, lon);

            JakartaServletWebApplication application = ServletContextConfig.getApplication();
            ITemplateEngine templateEngine = ServletContextConfig.getTemplateEngine();
            IWebExchange exchange = application.buildExchange(req, resp);
            WebContext webContext = new WebContext(exchange, exchange.getLocale());
            webContext.setVariable("session", req.getSession());
            webContext.setVariable("weather", weatherResponse);
            System.out.println(weatherResponse);
            templateEngine.process("index", webContext, resp.getWriter());
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
