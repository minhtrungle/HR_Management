package Dao;

import Connection.ConnectJDBC;
import Model.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

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
}
