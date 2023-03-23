package Application.Employee;
import Application.Home.Home;
import Application.OtherFunctions.ChangeDepartment;
import Dao.DepartmentDAO;
import Dao.EmployeeDAO;
import Model.Department;
import Model.Employee;
import UseCases.CheckExistEmployee;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Objects;

public class DeleteEmployee {
    private  static DepartmentDAO deptDAO = new DepartmentDAO();
    private  static EmployeeDAO empDAO = new EmployeeDAO();
    private  static Employee emp = new Employee();

    public DeleteEmployee() {
        deleteButton.addActionListener(e -> {
            String id = textId.getText();

            //Kiểm tra không để trống
            if (!Objects.equals(id, "")) {
                try {

                    empDAO.deleteEmployee(Integer.parseInt(id));
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
        });
        cancelButton.addActionListener(e -> {
            JComponent component = (JComponent) e.getSource();
            Window window = SwingUtilities.getWindowAncestor(component);
            window.dispose();
        });
        checkIdCheckBox.addItemListener(e -> {
            if (e.getStateChange() == 1) {
                int id = Integer.parseInt(textId.getText());
                try {
                    int idDept = empDAO.getByID(id).getDepartment_id();
                    Department dept = deptDAO.getByID(idDept);
                    if (id == dept.getManager_id()) {
                        JOptionPane.showMessageDialog(null, "Đây là trưởng phòng, cần cập nhật lại trưởng phòng", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Không có nhân viên", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        changeManagerButton.addActionListener(e -> SwingUtilities.invokeLater(DeleteEmployee::createcChangeManagerGUI));
    }
    private static void createcChangeManagerGUI() {
        ChangeDepartment change = new ChangeDepartment();
        JPanel changeManager = change.getChangeDepartmentPanel();

        JFrame jFrame = new JFrame();
        jFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        jFrame.setContentPane(changeManager);
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
    }
    public JPanel getDeleteEmployeePanel() {
        return  deleteEmployeePanel;
    }
    private JPanel deleteEmployeePanel;
    private JTextField textId;
    private JButton cancelButton;
    private JButton deleteButton;
    private JCheckBox checkIdCheckBox;
    private JButton changeManagerButton;
}
