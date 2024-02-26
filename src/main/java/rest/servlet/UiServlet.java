package rest.servlet;

import config.ServletContextConfig;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;

@WebServlet(urlPatterns = {"/home"})
public class UiServlet extends HttpServlet {

    private JakartaServletWebApplication application;
    private ITemplateEngine templateEngine;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        application = ServletContextConfig.getApplication();
        templateEngine = ServletContextConfig.getTemplateEngine();
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        renderMainPage(req, resp);
    }

    private void renderMainPage(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        IWebExchange exchange = application.buildExchange(req, resp);
        WebContext webContext = new WebContext(exchange, exchange.getLocale());
        webContext.setVariable("session", req.getSession());
        templateEngine.process("index", webContext, resp.getWriter());
    }
}
