package Application.Department;
import Application.Home.Home;
import Connection.ConnectJDBC;
import Dao.DepartmentDAO;
import Model.Department;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.*;
import java.util.Objects;

public class AddDepartment {

    public AddDepartment() {
        okCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent event) {
                //Khi tích chọn
                if (event.getStateChange() == 1) {
                    showTableEmpNoManager();
                    addButton.addActionListener(e -> {
                        String deptId = textDepartmentId.getText();
                        String deptName = textDepartmentName.getText();
                        String managerId = textManagerId.getText();
                        String locationId = textLocationId.getText();
                        final String sql = "INSERT INTO `departments` (dept_id, dept_name, manager_id, location_id) VALUES (?, ?, ?, ?)";

                        try {
                            Connection con = ConnectJDBC.getConnection();

                            PreparedStatement pre = con.prepareStatement(sql);

                            pre.setString(1, deptId);
                            pre.setString(2, deptName);
                            pre.setString(3, managerId);
                            pre.setString(4, locationId);

//                                DepartmentDAO departmentDAO = new DepartmentDAO();
//                                Department dep = departmentDAO.getByID(Integer.parseInt(deptId));
//                                if (dep != null) {
//                                    JOptionPane.showMessageDialog(null,
//                                            "Department Id đã tồn tại",
//                                            "Cảnh báo", JOptionPane.ERROR_MESSAGE);
//                                }
                            pre.executeUpdate();

                            JOptionPane.showMessageDialog(null, "Thêm phòng ban thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                            Home home = new Home();
                            home.getMainPanel();
                            //Đóng
                            JComponent component = (JComponent) e.getSource();
                            Window window = SwingUtilities.getWindowAncestor(component);
                            window.dispose();

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
                        String deptId = textDepartmentId.getText();
                        String deptName = textDepartmentName.getText();
                        String locationId = textLocationId.getText();
                        final String sql = "INSERT INTO `departments` (dept_id, dept_name, manager_id, location_id) VALUES (?, ?, ?, ?)";
                        try {
                            Connection con = ConnectJDBC.getConnection();
                            PreparedStatement pre = con.prepareStatement(sql);

                            pre.setString(1, deptId);
                            pre.setString(2, deptName);
                            pre.setString(3, null);
                            pre.setString(4, locationId);

                            pre.executeUpdate();

                            JOptionPane.showMessageDialog(null, "Thêm phòng ban thành công và tạm thời chưa có trưởng phòng", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                            Home home = new Home();
                            home.getMainPanel();
                            //Đóng
                            JComponent component = (JComponent) e.getSource();
                            Window window = SwingUtilities.getWindowAncestor(component);
                            window.dispose();

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        textDepartmentId.setText("");
                        textDepartmentName.setText("");
                        textManagerId.setText("");
                        textLocationId.setText("");
                    });
                }
            }

        });
        cancelButton.addActionListener(e -> {
            JComponent component = (JComponent) e.getSource();
            Window window = SwingUtilities.getWindowAncestor(component);
            window.dispose();
        });


    }

    private DefaultTableModel tableModel;
    private ResultSet resultSet;
    private void showTableEmpNoManager(){
        try {
            Object[] columnTitle = {"Manager Id", "Department Id"};
            tableModel = new DefaultTableModel(null, columnTitle);
            tableEmpNoManager.setModel(tableModel);

            Connection connection = ConnectJDBC.getConnection();
            Statement statement = connection.createStatement();
            tableModel.getDataVector().removeAllElements();

            resultSet = statement.executeQuery("select distinct e.manager_id, m.department_id \n" +
                    "from employees e \n" +
                    "inner join employees m\n" +
                    "on e.manager_id = m.id;");
            while (resultSet.next()){
                Object[] data = {
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
}
