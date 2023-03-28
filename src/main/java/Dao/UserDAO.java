package Dao;

import Connection.ConnectJDBC;
import Model.Department;
import Model.Employee;
import Model.User;

import java.sql.*;

public class UserDAO {
    public User getById(long id) {
        User user = null;
        try {
            Connection conn = ConnectJDBC.getConnection();
            String sql = "SELECT * FROM `users` WHERE `id` = " + id + " LIMIT 1";
            Statement stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery(sql);

            if (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getLong("id"));
                user.setUsername(resultSet.getString("username"));
            }

            resultSet.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public User getByUserNameAndPassword(String username, String password) {
        User user = null;
        try {
            Connection conn = ConnectJDBC.getConnection();
            String sql = String.format("SELECT id, username FROM users WHERE username='%s' AND password='%s' LIMIT 1 ",
                    username, password);

            Statement stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery(sql);
            if (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getLong("id"));
                user.setUsername(resultSet.getString("username"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }
    public User getByUserName(String username) {
        User user = null;
        try {
            Connection conn = ConnectJDBC.getConnection();
            String sql = String.format("SELECT id, username FROM users WHERE username='%s' LIMIT 1 ",
                    username);

            Statement stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery(sql);
            if (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getLong("id"));
                user.setUsername(resultSet.getString("username"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }
    public void insertUser(User u) throws SQLException {
        Connection con = ConnectJDBC.getConnection();
        final String sql = String.format("INSERT INTO `users` VALUES (NULL, '%s', '%s')", u.getUsername(), u.getPassword());

        try {
            Statement sta = con.createStatement();

            long res = sta.executeUpdate(sql);

            if (res == 0) {
                System.out.println("Insert users thất bại");
            }

            sta.close();
            con.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void updatePasswordUser(User u, String username) throws SQLException {
        Connection con = ConnectJDBC.getConnection();

        User user = getByUserName(username);
        if (user == null) {
            throw new RuntimeException("Không có user thõa mãn!");
        }

        final String sql = String. format("UPDATE `users` SET `password` = '%s' WHERE username = '" +username+ "'",
                u.getPassword(), username);

        try {
            Statement sta = con.createStatement();

            long res = sta.executeUpdate(sql);

            if (res == 0) {
                System.out.println("Update users thất bại");
            }

            sta.close();
            con.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void deleteUser(String username) throws SQLException {
        Connection con = ConnectJDBC.getConnection();

        User user = getByUserName(username);
        if (user == null) {
            throw new RuntimeException("Không có phòng ban thõa mãn!");
        }

        final String sql = "DELETE FROM `users` WHERE username = '" +username+ "'";

        try {
            Statement sta = con.createStatement();

            long res = sta.executeUpdate(sql);

            if (res == 0) {
                System.out.println("Delete users thất bại");
            }

            sta.close();
            con.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
