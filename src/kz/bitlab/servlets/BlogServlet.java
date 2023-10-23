package kz.bitlab.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kz.bitlab.db.DBManager;
import kz.bitlab.models.Blog;
import kz.bitlab.models.Comment;
import kz.bitlab.models.User;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet("/blogs")
public class BlogServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Blog> blogs = DBManager.getBlogs();
        req.setAttribute("novosty", blogs);
        req.getRequestDispatcher("/blogs.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title = req.getParameter("blog_title");
        String content = req.getParameter("blog_content");
        User user = (User) req.getSession().getAttribute("currentUser");
        Blog blog = new Blog(title,content,user);
        DBManager.addBlog(blog);
        resp.sendRedirect("/blogs");
    }
}
