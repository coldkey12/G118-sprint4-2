package kz.bitlab.db;

import kz.bitlab.models.City;
import kz.bitlab.models.Item;
import kz.bitlab.models.User;

import java.sql.*;
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
            PreparedStatement statement = connection.prepareStatement(
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
}
