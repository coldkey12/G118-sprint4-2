package kz.bitlab.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kz.bitlab.db.DBManager;
import kz.bitlab.models.City;
import kz.bitlab.models.Item;

import java.io.IOException;

@WebServlet(value = "/add-item")
public class AddItemServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("item_name");
        String description = req.getParameter("item_description");
        Double price = Double.valueOf(req.getParameter("item_price"));
        Long cityId = Long.parseLong(req.getParameter("item_city_id"));

        Item item = new Item();
        item.setName(name);
        item.setDescription(description);
        item.setPrice(price);
        City city = DBManager.getCityById(cityId);
        item.setCity(city);

        DBManager.addItem(item);

        resp.sendRedirect("/");
    }
}
