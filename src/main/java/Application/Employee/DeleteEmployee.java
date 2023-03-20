package Application.Employee;
import Connection.ConnectJDBC;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

public class DeleteEmployee {

    public DeleteEmployee() {
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = textId.getText();
                String sql = "DELETE FROM `employees` WHERE id = ?";

                //Kiểm tra không để trống
                if (!Objects.equals(id, "")) {
                    Connection con = null;
                    try {
                        con = ConnectJDBC.getConnection();

                        PreparedStatement pre = con.prepareStatement(sql);

                        pre.setString(1, id);
                        //Cập nhật dữ liệu
                        pre.executeUpdate();
                        //Đóng cửa sổ khi cập nhật xong
                        JOptionPane.showMessageDialog(null, "Xóa nhân viên thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                        JComponent component = (JComponent) e.getSource();
                        Window window = SwingUtilities.getWindowAncestor(component);
                        window.dispose();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Không được để trống",
                            "Cảnh báo", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComponent component = (JComponent) e.getSource();
                Window window = SwingUtilities.getWindowAncestor(component);
                window.dispose();
            }
        });
    }

    public JPanel getDeleteEmployeePanel() {
        return  deleteEmployeePanel;
    }
    private JPanel deleteEmployeePanel;
    private JTextField textId;
    private JButton cancelButton;
    private JButton deleteButton;
}
