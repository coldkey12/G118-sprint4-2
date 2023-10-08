package kz.bitlab.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kz.bitlab.db.DBManager;

import java.io.IOException;

@WebServlet(value = "/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email_area");
        String password = req.getParameter("password_area");
        System.out.println(email);
        System.out.println(password);
        if(DBManager.login(email,password)){
            resp.sendRedirect("/");
        } else {
            resp.sendRedirect("/sign-in");
        }
    }
}
