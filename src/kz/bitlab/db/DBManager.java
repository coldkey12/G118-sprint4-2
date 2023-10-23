package kz.bitlab.db;

import kz.bitlab.models.*;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class DBManager {
    private static Connection connection;

    static {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/G118",
                    "postgres",
                    "12070107Don"
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Item> getItems(){
        List<Item> items = new ArrayList<>();
        try {
            var statement = prepare(
                    "SELECT i.id, i.name, i.description, i.price, i.city_id, " +
                            "c.name as city_name, c.code FROM sprint.items i " +
                            "INNER JOIN sprint.cities c on i.city_id = c.id " +
                            "ORDER BY i.price DESC"
            );
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Item item = new Item();
                item.setId(resultSet.getLong("id"));
                item.setName(resultSet.getString("name"));
                item.setDescription(resultSet.getString("description"));
                item.setPrice(resultSet.getDouble("price"));

                City city = new City();
                city.setId(resultSet.getLong("city_id"));
                city.setName(resultSet.getString("city_name"));
                city.setCode(resultSet.getString("code"));

                item.setCity(city);
                items.add(item);
            }
            statement.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return items;
    }

    public static boolean login(String email, String password){
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM sprint.users WHERE email = ? AND password = ?"
            );
            statement.setString(1,email);
            statement.setString(2,password);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                System.out.println("Success");
                return true;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public static List<City> getCities(){
        List<City> cities = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM sprint.cities"
            );
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                City city = new City();
                city.setId(resultSet.getLong("id"));
                city.setName(resultSet.getString("name"));
                city.setCode(resultSet.getString("code"));
                cities.add(city);
            }
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cities;
    }

    public static void addItem(Item item){
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO sprint.items(name,description,price,city_id) " +
                            "VALUES( ? , ? , ? , ? )"
            );
            statement.setString(1,item.getName());
            statement.setString(2,item.getDescription());
            statement.setDouble(3,item.getPrice());
            statement.setLong(4,item.getCity().getId());
            statement.executeUpdate();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static City getCityById(Long cityId){
        City city = null;
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM sprint.cities WHERE id = ?"
            );
            statement.setLong(1,cityId);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                city = new City();
                city.setId(cityId);
                city.setName(resultSet.getString("name"));
                city.setCode(resultSet.getString("code"));
            }
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return city;
    }

    public static void deleteItemById(Long id){
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM sprint.items WHERE id = ?"
            );
            statement.setLong(1,id);
            statement.executeUpdate();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateItem(Item item) {
        if(item==null){
            return;
        }
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE sprint.items " +
                            "set name = ?, description = ?, price = ? " +
                            "WHERE id = ?"
            );

            statement.setString(1,item.getName());
            statement.setString(2,item.getDescription());
            statement.setDouble(3,item.getPrice());
            statement.setLong(4,item.getId());
            statement.executeUpdate();
            statement.close();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static User getUserByEmail(String email){
        User user = null;
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM sprint.users WHERE email = ?"
            );
            statement.setString(1,email);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                user = new User();
                user.setId(resultSet.getLong("id"));
                user.setEmail(email);
                user.setPassword(resultSet.getString("password"));
                user.setFullName(resultSet.getString("full_name"));
            }
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }


    public static void addUser(User user) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO sprint.users(email,password,full_name) " +
                            "VALUES(?,?,?)"
            );
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getFullName());
            statement.executeUpdate();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static List<Blog> getBlogs() {
        List<Blog> blogs = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM sprint.blogs ORDER BY post_date DESC"
            );
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                Blog blog = new Blog();
                blog.setId(resultSet.getLong("id"));
                blog.setTitle(resultSet.getString("title"));
                blog.setContent(resultSet.getString("content"));
                blog.setPostDate(resultSet.getObject("post_date", LocalDateTime.class));
                Long userId = resultSet.getLong("user_id");
                blog.setUser(getUserById(userId));
                blogs.add(blog);
            }
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return blogs;
    }

    public static User getUserById(Long id){
        User user = null;
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM sprint.users WHERE id = ?"
            );
            statement.setLong(1,id);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                user = new User();
                user.setId(id);
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setFullName(resultSet.getString("full_name"));
            }
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public static void addBlog(Blog blog) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO sprint.blogs(title,content,post_date,user_id) " +
                            "VALUES(?,?,now(),?)"
            );
            statement.setString(1,blog.getTitle());
            statement.setString(2,blog.getContent());
            statement.setLong(3,blog.getUser().getId());
            statement.executeUpdate();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Blog getBlogById(Long id) {
        Blog blog = null;
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT b.*, u.email, u.password, u.full_name " +
                            "FROM sprint.blogs b " +
                            "INNER JOIN sprint.users u " +
                            "ON b.user_id = u.id " +
                            "WHERE b.id = ?"
            );
            statement.setLong(1,id);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                blog = new Blog();
                blog.setId(id);
                blog.setTitle(resultSet.getString("title"));
                blog.setContent(resultSet.getString("content"));
                blog.setPostDate(resultSet.getObject("post_date", LocalDateTime.class));

                User user = new User();
                user.setId(resultSet.getLong("user_id"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setFullName(resultSet.getString("full_name"));
                blog.setUser(user);
            }
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return blog;
    }

    public static void updateBlog(Blog blog) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE sprint.blogs " +
                            "SET title = ?, content = ?, user_id = ? " +
                            "WHERE id = ?"
            );
            statement.setString(1, blog.getTitle());
            statement.setString(2, blog.getContent());
            statement.setLong(3, blog.getUser().getId());
            statement.setLong(4, blog.getId());
            statement.executeUpdate();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Comment> getCommentsByBlogId(Long blogId) {
        List<Comment> comments = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT c.*, b.title, u.full_name FROM sprint.comments c " +
                            "INNER JOIN sprint.blogs b on c.blog_id = b.id " +
                            "INNER JOIN sprint.users u on c.user_id = u.id " +
                            "WHERE c.blog_id = ? " +
                            "ORDER BY c.post_date DESC "
            );
            statement.setLong(1,blogId);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                Comment comment = new Comment();
                comment.setId(resultSet.getLong("id"));
                comment.setDescription(resultSet.getString("description"));
                comment.setPostDate(resultSet.getObject("post_date", LocalDateTime.class));

                User user = new User();
                user.setId(resultSet.getLong("user_id"));
                user.setFullName(resultSet.getString("full_name"));
                comment.setUser(user);

                Blog blog = new Blog();
                blog.setId(resultSet.getLong("blog_id"));
                blog.setTitle(resultSet.getString("title"));
                comment.setBlog(blog);

                comments.add(comment);
            }
            statement.close();
        } catch (Exception e){
            e.printStackTrace();
        }
        return comments;
    }

    public static void addComment(String commentDescription, Long blogId, Long userId) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO sprint.comments(description, user_id, blog_id) " +
                            "VALUES(?,?,?)"
            );
            statement.setString(1,commentDescription);
            statement.setLong(2, userId);
            statement.setLong(3,blogId);
            statement.executeUpdate();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static PreparedStatement prepare(String sqlQuery) throws SQLException {
        return connection.prepareStatement(sqlQuery);
    }
}
