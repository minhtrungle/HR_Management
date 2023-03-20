package Application.Login;

import Application.Home.Home;
import Connection.ConnectJDBC;

import java.sql.Connection;
import Model.User;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login extends JDialog{
    public Login(JFrame parent) {
        super(parent);
        setTitle("Login");
        setContentPane(loginPanel);
        setMinimumSize(new Dimension(450, 480));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        signInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userName = textUsername.getText();
                String passWord = String.valueOf(textPassword.getPassword());
                final String sql = "SELECT * FROM `users` WHERE `username` = ? AND `password` = ?";

                try {
                    Connection con = ConnectJDBC.getConnection();

                    PreparedStatement pre = con.prepareStatement(sql);

                    pre.setString(1, userName);
                    pre.setString(2, passWord);

                    ResultSet res = pre.executeQuery();

                    if (res.next()) {
//                        JOptionPane.showMessageDialog(null,
//                                "Đăng nhập thành công");
                        new Home();
                        setVisible(false); //đóng cửa sổ

                    } else {
                        JOptionPane.showMessageDialog(null,
                                "username hoặc password không thõa mãn!",
                                "Try again",
                                JOptionPane.ERROR_MESSAGE);
                        textUsername.setText("");
                        textPassword.setText("");
//                        if (textPassword.setText("")) {
//
//                        }
                    }
                    pre.close();
                    res.close();
                    con.close();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComponent component = (JComponent) e.getSource();
                Window window = SwingUtilities.getWindowAncestor(component);
                window.dispose();
                Home home = new Home();
            }
        });
        setVisible(true);
    }
    public User user;
    private JTextField textUsername;
    private JPasswordField textPassword;
    private JButton cancelButton;
    private JButton signInButton;
    private JPanel loginPanel;
}