package rest.servlet.auth;

import entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.NoArgsConstructor;
import service.UserService;

import java.io.IOException;

@WebServlet(urlPatterns = "/api/signin")
@NoArgsConstructor
public class SignInServlet extends HttpServlet {

    private final static UserService userService = new UserService();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            HttpSession session = req.getSession();
            String login = req.getParameter("login");
            String password = req.getParameter("password");

            User user = userService.getUser(login, password).orElseThrow();

            Integer userId = (Integer) session.getAttribute("userId");

            if (userId == null) {
                session.setAttribute("user", user);
            }

            resp.sendRedirect("/home");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
