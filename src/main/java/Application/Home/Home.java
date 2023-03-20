package Application.Home;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import Application.Employee.AddEmployee;
import Application.Employee.DeleteEmployee;
import Application.Employee.UpdateEmployee;
import Connection.ConnectJDBC;
/**
 * Hiển thị các chức năng chọn và hiện danh sách employees và departments trong database
 * @author TrungLM
 */
public class Home {
    public Home() {

        addEmpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(Home::createAddEmployeeGUI);
                //Loát lại table Employee
                DefaultTableModel model = (DefaultTableModel) table1.getModel();
                model.setRowCount(0);
                showData1();
            }
        });
        updateEmpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(Home::createUpdateEmployeeGUI);
            }
        });
        deleteEmpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(Home::createDeleteEmployeeGUI);
            }
        });
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showData1();
                showData2();
            }
        });
    }
    /**
     * Các hàm để khi bấm button sẽ gọi bên .form để hiện form của Employee
     */
    private static void createAddEmployeeGUI(){
        //Tạo đối tượng add bên AddEmployee
        AddEmployee add = new AddEmployee();
        //Thêm panel addEmployeePanel bên AddEmployee.form
        JPanel updateRoot = add.getAddEmployeePanel();
        //JFrame(): Xây dựng một Frame mới, ban đầu là không nhìn thấy (invisible).
        JFrame jFrame = new JFrame();
        //đóng mặc định của JFrame bằng cách chuyển một trong bốn hằng số vào phương thức setDefaultCloseOperation()
        //HIDE_ON_CLOSE JFrame sẽ bị ẩn đi khi người dùng đóng nó lại. Chương trình vẫn sẽ hoạt động
        jFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        jFrame.setContentPane(updateRoot);
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
    }

    private static void createUpdateEmployeeGUI() {
        UpdateEmployee update = new UpdateEmployee();
        JPanel updateRoot = update.getUpdateEmployeePanel();

        JFrame jFrame = new JFrame();
        jFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        jFrame.setContentPane(updateRoot);
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
    }
    private static void createDeleteEmployeeGUI() {
        DeleteEmployee delete = new DeleteEmployee();
        JPanel deleteRoot = delete.getDeleteEmployeePanel();

        JFrame jFrame = new JFrame();
        jFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        jFrame.setContentPane(deleteRoot);
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
    }
    /**
     * Các hàm để khi bấm button sẽ gọi bên .form để hiện form của Department
     */
    private static void createAddDepartmentGUI() {

    }
    private static void createUpdateDepartmentGUI() {

    }
    private static void createDeleteDepartmentGUI() {

    }
    private DefaultTableModel tableModel;
    private ResultSet resultSet;
    private void showData1(){
        try {
            Object[] columnTitle = {"id", "firstname", "lastname", "email", "phone",
                    "hire_date", "job", "salary", "commission", "manager_id", "department_id"};
            tableModel = new DefaultTableModel(null, columnTitle);
            table1.setModel(tableModel);

            Connection connection = ConnectJDBC.getConnection();
            Statement statement = connection.createStatement();
            tableModel.getDataVector().removeAllElements();

            resultSet = statement.executeQuery("SELECT * FROM employees");
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
    private void showData2() {
        try {
            Object[] columnTitle = {"dept_id", "dept_name", "manager_id", "location_id"};
            tableModel = new DefaultTableModel(null, columnTitle);
            table2.setModel(tableModel);

            Connection connection = ConnectJDBC.getConnection();
            Statement statement = connection.createStatement();
            tableModel.getDataVector().removeAllElements();

            resultSet = statement.executeQuery("SELECT * FROM departments");
            while (resultSet.next()){
                Object[] data = {
                        resultSet.getString("dept_id"),
                        resultSet.getString("dept_name"),
                        resultSet.getString("manager_id"),
                        resultSet.getString("location_id")
                };
                tableModel.addRow(data);
            }
        } catch (SQLException err){
            throw new RuntimeException(err);
        }
    }
    public JPanel getMainPanel(){
        showData1();
        showData2();
        return homePanel;
    }
    private JPanel homePanel;
    private JTable table1;
    private JTable table2;
    private JButton employeeButton;
    private JButton updateEmpButton;
    private JButton deleteEmpButton;
    private JButton addDeptButton;
    private JButton updateDeptButton;
    private JButton deleteDeptButton;
    private JButton addEmpButton;
    private JButton searchButton;
    private JButton refreshButton;
}
