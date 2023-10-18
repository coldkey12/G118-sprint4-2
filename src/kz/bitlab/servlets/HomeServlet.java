package kz.bitlab.servlets;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kz.bitlab.db.DBManager;
import kz.bitlab.models.City;
import kz.bitlab.models.Item;
import kz.bitlab.models.User;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@WebServlet(value = "/")
public class HomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Date birthDate1 = new Date();
        LocalDate date2 = LocalDate.now();

        System.out.println(birthDate1.getMonth() + " " + birthDate1.getYear());
        System.out.println(date2.getMonth() + " " + date2.getYear());


        User currentUser = (User) req.getSession().getAttribute("currentUser");

        if(currentUser==null){
            req.getRequestDispatcher("sign-in.jsp").forward(req,resp);
        }

        List<Item> items = DBManager.getItems();
        List<City> cities = DBManager.getCities();
        req.setAttribute("cities", cities);
        req.setAttribute("items",items);

        req.getRequestDispatcher("home.jsp").forward(req,resp);
    }
}
