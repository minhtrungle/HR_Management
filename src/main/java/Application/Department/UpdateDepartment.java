package Application.Department;
import Application.Employee.AddEmployee;
import Application.Employee.DeleteEmployee;
import Application.OtherFunctions.ChangeDepartment;
import Connection.ConnectJDBC;
import Application.Employee.UpdateEmployee;
import Dao.DepartmentDAO;
import Model.Department;
import UseCases.CheckExistDept;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Objects;

public class UpdateDepartment {
    private static DepartmentDAO deptDAO = new DepartmentDAO();
    public UpdateDepartment() {
        updateButton.addActionListener(e -> {
            String idDept = textDeptId.getText();
            String nameDept = textDeptName.getText();
            String managerId = textManagerId.getText();
            String locationId = textLocationId.getText();

            //Kiểm tra không được để trống ô nhập id!=""
            if (!Objects.equals(idDept, "") && !Objects.equals(nameDept, "") && !Objects.equals(managerId, "") && !Objects.equals(locationId, "")) {
                try {
                    Department dept = new Department();

                    dept.setDept_name(nameDept);
                    dept.setManager_id(Integer.parseInt(managerId));
                    dept.setLocation_id(Integer.parseInt(locationId));

                    deptDAO.updateDepartment(dept, Integer.parseInt(idDept));
                    //Đóng cửa sổ khi cập nhật xong
                    JOptionPane.showMessageDialog(null,
                            "Cập nhật phòng ban thành công",
                            "Thông báo",
                            JOptionPane.INFORMATION_MESSAGE);
                    JComponent component = (JComponent) e.getSource();
                    Window window = SwingUtilities.getWindowAncestor(component);
                    window.dispose();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            else {
                JOptionPane.showMessageDialog(null,
                        "Không được để trống",
                        "Cảnh báo",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        getInforButton.addActionListener(e -> {
            int idDept = Integer.parseInt(textDeptId.getText());
            try {
                if (new CheckExistDept().checkID(idDept) == true) {
                    Department dept = deptDAO.getByID(idDept);
                    textDeptName.setText(dept.getDept_name());
                    textManagerId.setText(String.valueOf(dept.getManager_id()));
                    textLocationId.setText(String.valueOf(dept.getLocation_id()));
                    //Có phòng ban thì hiển thị danh sách nhân viên phòng ban đó
                    showTableEmpOfDept();
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Không có phòng ban",
                            "Thông báo",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        addEmployeeButton.addActionListener(e -> SwingUtilities.invokeLater(UpdateDepartment::createAddEmpFromDeptGUI));

        updateEmployeeButton.addActionListener(e -> SwingUtilities.invokeLater(UpdateDepartment::createUpdateEmpFromDeptGUI));

        deleteEmployeeButton.addActionListener(e -> SwingUtilities.invokeLater(UpdateDepartment::createDeleteEmpFromDeptGUI));

        cancelButton.addActionListener(e -> {
            JComponent component = (JComponent) e.getSource();
            Window window = SwingUtilities.getWindowAncestor(component);
            window.dispose();
        });
    }
    private DefaultTableModel tableModel;
    private ResultSet resultSet;
    private void showTableEmpOfDept(){
        try {
            String idDept = textDeptId.getText();
            String sql = "select * from employees where department_id = '"+idDept+"';";

            Object[] columnTitle = {"Id", "Firstname", "Lastname", "Email", "Phone",
                    "Hire date", "Job", "Salary", "Commission", "Manager id", "Department id"};
            tableModel = new DefaultTableModel(null, columnTitle);
            tableEmpOfDept.setModel(tableModel);

            Connection connection = ConnectJDBC.getConnection();
            Statement statement = connection.createStatement();
            tableModel.getDataVector().removeAllElements();

            resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                Object[] data = {
                        resultSet.getInt("id"),
                        resultSet.getString("firstname"),
                        resultSet.getString("lastname"),
                        resultSet.getString("email"),
                        resultSet.getString("phone"),
                        resultSet.getDate("hire_date"), //yyyy/mm/dd
                        resultSet.getString("job"),
                        resultSet.getInt("salary"),
                        resultSet.getDouble("commission"),
                        resultSet.getInt("manager_id"),
                        resultSet.getInt("department_id"),
                };
                tableModel.addRow(data);
            }
        } catch (SQLException err){
            throw new RuntimeException(err);
        }
    }
    private static void createAddEmpFromDeptGUI() {
        AddEmployee add = new AddEmployee();
        JPanel addEmp = add.getAddEmployeePanel();

        JFrame jFrame = new JFrame();
        jFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        jFrame.setContentPane(addEmp);
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
    }
    private static void createUpdateEmpFromDeptGUI() {
        UpdateEmployee update = new UpdateEmployee();
        JPanel updateEmp = update.getUpdateEmployeePanel();

        JFrame jFrame = new JFrame();
        jFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        jFrame.setContentPane(updateEmp);
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
    }
    private static void createDeleteEmpFromDeptGUI() {
        ChangeDepartment delete = new ChangeDepartment();
        JPanel deleteEmp = delete.getChangeDepartmentPanel();

        JFrame jFrame = new JFrame();
        jFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        jFrame.setContentPane(deleteEmp);
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
    }
    public JPanel getUpdateDepartmentPanel() {
        return updateDepartmentPanel;
    }
    private JPanel updateDepartmentPanel;
    private JButton updateEmployeeButton;
    private JButton getInforButton;
    private JTextField textDeptName;
    private JTextField textManagerId;
    private JTextField textLocationId;
    private JTable tableEmpOfDept;
    private JButton cancelButton;
    private JButton updateButton;
    private JTextField textDeptId;
    private JButton addEmployeeButton;
    private JButton deleteEmployeeButton;
}
