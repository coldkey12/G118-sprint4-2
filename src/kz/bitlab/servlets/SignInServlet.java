package kz.bitlab.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kz.bitlab.db.DBManager;
import kz.bitlab.models.User;

import java.io.IOException;

@WebServlet("/sign-in")
public class SignInServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("user_email");
        String password = req.getParameter("user_password");
        User currentUser = DBManager.getUserByEmail(email);
        String redirect = "/?emailError";
        if(currentUser!=null){
            redirect = "/?passwordError";
            if(currentUser.getPassword().equals(password)){
                HttpSession session = req.getSession();
                session.setAttribute("currentUser", currentUser);
                redirect = "/";
            }
        }
        resp.sendRedirect(redirect);
    }
}
