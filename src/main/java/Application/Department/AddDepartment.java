package Application.Department;
import Connection.ConnectJDBC;
import Dao.DepartmentDAO;
import Dao.EmployeeDAO;
import Model.Department;
import Model.Employee;
import UseCases.CheckExistDept;
import UseCases.CheckExistEmployee;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.*;
import java.util.Objects;

public class AddDepartment {
    private static DepartmentDAO deptDAO = new DepartmentDAO();
    private static EmployeeDAO empDAO = new EmployeeDAO();
    private static Department dept = new Department();
    private static Employee emp = new Employee();
    public AddDepartment() {
        okCheckBox.addItemListener(event -> {
            //Khi tích chọn
            if (event.getStateChange() == 1) {

                showTableEmpNoManager();

                addButton.addActionListener(e -> {
                    String deptId = textDepartmentId.getText();
                    String deptName = textDepartmentName.getText();
                    String managerId = textManagerId.getText();
                    String locationId = textLocationId.getText();

                    try {
                        dept.setDept_id(Integer.parseInt(deptId));
                        dept.setDept_name(deptName);
                        dept.setManager_id(Integer.parseInt(managerId));
                        dept.setLocation_id(Integer.parseInt(locationId));

                        deptDAO.insertDepartment(dept);

                        //Cập nhật lại trưởng phòng bên employees khi chọn nhân viên lên làm trưởng phòng và đứng dưới chủ tịch 1
                        emp.setManager_id(1);
                        emp.setDepartment_id(Integer.parseInt(deptId));

                        empDAO.updateManagerAfterChoiceManager(emp, Integer.parseInt(managerId));

                        JOptionPane.showMessageDialog(null,
                                "Thêm phòng ban thành công",
                                "Thông báo",
                                JOptionPane.INFORMATION_MESSAGE);
                        //Đóng
                        JComponent component = (JComponent) e.getSource();
                        Window window = SwingUtilities.getWindowAncestor(component);
                        window.dispose();
                        return;
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    textDepartmentId.setText("");
                    textDepartmentName.setText("");
                    textManagerId.setText("");
                    textLocationId.setText("");
                });
            } else {
                addButton.addActionListener(e -> {
                    int deptId = Integer.parseInt(textDepartmentId.getText());
                    String deptName = textDepartmentName.getText();
                    int locationId = Integer.parseInt(textLocationId.getText());
                    try {
                        dept.setDept_id(deptId);
                        dept.setDept_name(deptName);
                        dept.setManager_id(0);
                        dept.setLocation_id(locationId);

                        deptDAO.insertDepartment(dept);

                        JOptionPane.showMessageDialog(null,
                                "Thêm phòng ban thành công và tạm thời chưa có trưởng phòng",
                                "Thông báo",
                                JOptionPane.INFORMATION_MESSAGE);

                        //Đóng
                        JComponent component = (JComponent) e.getSource();
                        Window window = SwingUtilities.getWindowAncestor(component);
                        window.dispose();
                        return;
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    textDepartmentId.setText("");
                    textDepartmentName.setText("");
                    textManagerId.setText("");
                    textLocationId.setText("");
                });
            }
        });

        cancelButton.addActionListener(e -> {
            JComponent component = (JComponent) e.getSource();
            Window window = SwingUtilities.getWindowAncestor(component);
            window.dispose();
        });

        checkButton.addActionListener(e -> {
            int id = Integer.parseInt(textDepartmentId.getText());
            try {
                if (new CheckExistDept().checkID(id) == true) {
                    JOptionPane.showMessageDialog(null,
                            "Đã có phòng ban này",
                            "Thông báo",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Có thể thêm phòng ban này",
                            "Thông báo",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
    }
    private DefaultTableModel tableModel;
    private ResultSet resultSet;
    private void showTableEmpNoManager(){
        try {
            Object[] columnTitle = {"Employee Id", "Name", "Job", "Current Manager", "Current Department"};
            tableModel = new DefaultTableModel(null, columnTitle);
            tableEmpNoManager.setModel(tableModel);

            Connection connection = ConnectJDBC.getConnection();
            Statement statement = connection.createStatement();
            tableModel.getDataVector().removeAllElements();

            resultSet = statement.executeQuery("SELECT * FROM employees WHERE id != 1 AND manager_id != 1");
            while (resultSet.next()){
                Object[] data = {
                        resultSet.getInt("id"),
                        resultSet.getString("lastname") + ' ' + resultSet.getString("firstname"),
                        resultSet.getString("job"),
                        resultSet.getInt("manager_id"),
                        resultSet.getInt("department_id"),
                };
                tableModel.addRow(data);
            }
        } catch (SQLException err){
            throw new RuntimeException(err);
        }
    }
    public JPanel getAddDepartmentPane() {
        return addDepartmentPanel;
    }
    private JCheckBox okCheckBox;
    private JTextField textDepartmentId;
    private JTextField textDepartmentName;
    private JTextField textLocationId;
    private JTextField textManagerId;
    private JButton cancelButton;
    private JButton addButton;
    private JPanel addDepartmentPanel;
    private JTable tableEmpNoManager;
    private JButton checkButton;
}
