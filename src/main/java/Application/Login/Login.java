package Application.Login;

import Application.App.RunApp;
import Application.Home.Home;
import Connection.ConnectJDBC;

import java.sql.Connection;
import Model.User;
import Service.AuthenService;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login extends JDialog{
    private static boolean isLoginSuccess = false;
    // Khai báo Service
    private static AuthenService authenService = new AuthenService();
    int count = 3;
    public Login(JFrame parent) {
        super(parent);
        setTitle("Login");
        setContentPane(loginPanel);
        setMinimumSize(new Dimension(450, 480));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        signInButton.addActionListener(e -> {
            String userName = textUsername.getText();
            String passWord = String.valueOf(textPassword.getPassword());
            isLoginSuccess = authenService.login(userName, passWord);

            if (count >= 0) {
                if (isLoginSuccess == true) {
                    JOptionPane.showMessageDialog(null,
                            "Đăng nhập thành công",
                            "Thông báo",
                            JOptionPane.INFORMATION_MESSAGE);
                    //Đóng cửa sổ
                    setVisible(false);
                    //Mở Home khi đăng nhập thành công
                    SwingUtilities.invokeLater(Login::createShowHomeGUI);
                }
                else if (isLoginSuccess == false) {
                    count--;
                    JOptionPane.showMessageDialog(null,
                            "Bạn nhập sai mất rồiii :(, còn " + count + " lần nhập sai",
                            "Thông báo",
                            JOptionPane.ERROR_MESSAGE);
                    if (count == 0) {
                        JOptionPane.showMessageDialog(null,
                                "Bạn đã hết số lần nhập sai, hãy liên hệ quản trị viên",
                                "Thông báo",
                                JOptionPane.ERROR_MESSAGE);
                        //Hết 3 lần hệ thống tự thoát
                        System.exit(0);
                    }
                }
            }
            });

        cancelButton.addActionListener(e -> {
            //Tạo đối tượng từ Jcomponent để xử lý xự kiện
            JComponent component = (JComponent) e.getSource();
            //Tạo object để chứa
            Window window = SwingUtilities.getWindowAncestor(component);
            //Đóng JFram
            window.dispose();
        });

        setVisible(true);
    }

    public static void createShowHomeGUI() {
        Home home = new Home();
        JPanel root = home.getMainPanel();

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(root);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JTextField textUsername;
    private JPasswordField textPassword;
    private JButton cancelButton;
    private JButton signInButton;
    private JPanel loginPanel;
}