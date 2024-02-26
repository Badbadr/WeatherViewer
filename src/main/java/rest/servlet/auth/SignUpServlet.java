package rest.servlet.auth;

import config.ServletContextConfig;
import entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.NoArgsConstructor;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.IWebExchange;
import service.UserService;

import java.io.IOException;

@WebServlet(urlPatterns = "/api/signup")
@NoArgsConstructor
public class SignUpServlet extends HttpServlet {

    private final static UserService userService = new UserService();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        try {
            User user = userService.createUser(login, password);
            User sessUser = (User) session.getAttribute("user");

            if (sessUser == null) {
                session.setAttribute("user", user);
            }

            resp.sendRedirect("/home");
        } catch (Exception e) {
            resp.sendError(500, "Error during sign up. User with such login already exist");
        }
    }
}
