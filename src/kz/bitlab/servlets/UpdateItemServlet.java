package kz.bitlab.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kz.bitlab.db.DBManager;
import kz.bitlab.models.Item;

import java.io.IOException;

@WebServlet(value = "/update-item")
public class UpdateItemServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("item_name_update");
        String description = req.getParameter("item_description_update");
        Double price = Double.valueOf(req.getParameter("item_price_update"));
        Long id = Long.valueOf(req.getParameter("id"));
        Long cityId = Long.valueOf(req.getParameter("item_city_id_update"));

        Item item = new Item();
        item.setName(name);
        item.setDescription(description);
        item.setPrice(price);
        item.setId(id);
        item.setCity(DBManager.getCityById(cityId));

        DBManager.updateItem(item);
        resp.sendRedirect("/");
    }
}
