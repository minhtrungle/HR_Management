package Application.Admin;

import Dao.UserDAO;
import Model.User;
import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class UserAdmin {
    private UserDAO userDAO = new UserDAO();
    private User user = new User();
    public UserAdmin() {
            addButton.addActionListener(e -> {
                String HRUsername = textHRUsername.getText();
                String HRPassword = String.valueOf(textHRPassword.getPassword());
                String username = textNewUsername.getText();
                String password = String.valueOf(textNewPassword.getText());

                if (Objects.equals(HRUsername, "trunglm") && Objects.equals(HRPassword, "trunglm")) {
                    try {
                        user.setUsername(username);
                        user.setPassword(password);

                        userDAO.insertUser(user);

                        JOptionPane.showMessageDialog(null,
                                "Thêm user thành công",
                                "Thông báo",
                                JOptionPane.INFORMATION_MESSAGE);
                        //Đóng
                        JComponent component = (JComponent) e.getSource();
                        Window window = SwingUtilities.getWindowAncestor(component);
                        window.dispose();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Chỉ có HR Manager mới có thể sử dụng tác vụ này",
                            "Thông báo",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            });
            updateButton.addActionListener(e -> {
                String HRUsername = textHRUsername.getText();
                String HRPassword = String.valueOf(textHRPassword.getPassword());
                String userName = textNewUsername.getText();
                String passWord = String.valueOf(textNewPassword.getText());

                if (Objects.equals(HRUsername, "trunglm") && Objects.equals(HRPassword, "trunglm")) {
                    try {
                        user.setPassword(passWord);

                        userDAO.updatePasswordUser(user, userName);

                        JOptionPane.showMessageDialog(null,
                                "Đổi mật khẩu user thành công",
                                "Thông báo",
                                JOptionPane.INFORMATION_MESSAGE);
                        //Đóng
                        JComponent component = (JComponent) e.getSource();
                        Window window = SwingUtilities.getWindowAncestor(component);
                        window.dispose();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Chỉ có HR Manager mới có thể sử dụng tác vụ này",
                            "Thông báo",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            });
            deleteButton.addActionListener(e -> {
                String HRUsername = textHRUsername.getText();
                String HRPassword = String.valueOf(textHRPassword.getPassword());
                String userName = textNewUsername.getText();

                if (Objects.equals(HRUsername, "trunglm") && Objects.equals(HRPassword, "trunglm")) {
                    try {
                        userDAO.deleteUser(userName);

                        JOptionPane.showMessageDialog(null,
                                "Xóa user thành công",
                                "Thông báo",
                                JOptionPane.INFORMATION_MESSAGE);
                        //Đóng
                        JComponent component = (JComponent) e.getSource();
                        Window window = SwingUtilities.getWindowAncestor(component);
                        window.dispose();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Chỉ có HR Manager mới có thể sử dụng tác vụ này",
                            "Thông báo",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            });
            cancelButton.addActionListener(e -> {
                JComponent component = (JComponent) e.getSource();
                Window window = SwingUtilities.getWindowAncestor(component);
                window.dispose();
            });
        }



    public JPanel getUserPanel() {
        return userPanel;
    }
    private JPanel userPanel;
    private JTextField textHRUsername;
    private JPasswordField textHRPassword;
    private JTextField textNewUsername;
    private JTextField textNewPassword;
    private JButton cancelButton;
    private JButton deleteButton;
    private JButton addButton;
    private JButton updateButton;
}
