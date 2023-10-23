package kz.bitlab.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kz.bitlab.db.DBManager;
import kz.bitlab.models.Blog;
import kz.bitlab.models.Comment;

import java.io.IOException;
import java.util.List;

@WebServlet("/blog-details")
public class BlogDetailsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        List<Comment> comments = DBManager.getCommentsByBlogId(id);
        req.setAttribute("comments", comments);
        Blog blog = DBManager.getBlogById(id);
        req.setAttribute("blog", blog);
        req.getRequestDispatcher("blog-details.jsp").forward(req,resp);
    }
}
