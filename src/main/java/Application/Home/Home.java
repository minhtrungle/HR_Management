package Application.Home;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

import Application.Department.AddDepartment;
import Application.Department.DeleteDepartment;
import Application.Department.UpdateDepartment;
import Application.Employee.AddEmployee;
import Application.Employee.DeleteEmployee;
import Application.Employee.UpdateEmployee;
import Application.OtherFunctions.ChangeDepartment;
import Application.OtherFunctions.IncomeTax;
import Application.OtherFunctions.Search;
import Connection.ConnectJDBC;
/**
 * Hiển thị các chức năng chọn và hiện danh sách employees và departments trong database
 * @author TrungLM
 */
public class Home {
    public Home() {

        addEmpButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Trước khi thêm phải kiểm tra Id đã tồn tại chưa \n" +
                    "Nếu chưa thêm Manager và Department thì nhập 0", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            SwingUtilities.invokeLater(Home::createAddEmployeeGUI);
            //Loát lại table Employee
//                DefaultTableModel model = (DefaultTableModel) table1.getModel();
//                model.setRowCount(0);
//                showData1();
        });

        updateEmpButton.addActionListener(e -> SwingUtilities.invokeLater(Home::createUpdateEmployeeGUI));

        deleteEmpButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Nếu sa thải trưởng phòng cần cập nhật lại danh sách trưởng phòng ở Employee và Department", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            SwingUtilities.invokeLater(Home::createDeleteEmployeeGUI);
        });

        addDeptButton.addActionListener(e -> SwingUtilities.invokeLater(Home::createAddDepartmentGUI));

        updateDeptButton.addActionListener(e -> SwingUtilities.invokeLater(Home::createUpdateDepartmentGUI));

        deleteDeptButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Trước khi xóa phải chuyển phòng ban cho nhân viên, đồng ý thì tích còn không thì BƯỚC", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            SwingUtilities.invokeLater(Home::createDeleteDepartmentGUI);
        });

        refreshButton.addActionListener(e -> {
            showEmployee();
            showDepartment();
        });

        searchButton.addActionListener(e -> SwingUtilities.invokeLater(Home::createSearchEmployeeGUI));

        changeDepartmentButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Nếu nhân viên tạm thời chưa được xếp phòng ban thì không tích vào ô. \n" +
                    "Nếu chuyển quản lý thì danh sách quản lý đó bên Employee và Department phải cập nhật cùng ", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            SwingUtilities.invokeLater(Home::createChangeDepartmentGUI);
        });

        incomeTaxButton.addActionListener(e -> SwingUtilities.invokeLater(Home::createIncomeTaxGUI));
    }


    /**
     * Các hàm để khi bấm button sẽ gọi bên .form để hiện form của Employee
     */
    private static void createAddEmployeeGUI(){
        //Tạo đối tượng add bên AddEmployee
        AddEmployee add = new AddEmployee();
        //Thêm panel addEmployeePanel bên AddEmployee.form
        JPanel addEmp = add.getAddEmployeePanel();
        //JFrame(): Xây dựng một Frame mới, ban đầu là không nhìn thấy (invisible).
        JFrame jFrame = new JFrame();
        //đóng mặc định của JFrame bằng cách chuyển một trong bốn hằng số vào phương thức setDefaultCloseOperation()
        //HIDE_ON_CLOSE JFrame sẽ bị ẩn đi khi người dùng đóng nó lại. Chương trình vẫn sẽ hoạt động
        jFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        jFrame.setContentPane(addEmp);
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
    }

    private static void createUpdateEmployeeGUI() {
        UpdateEmployee update = new UpdateEmployee();
        JPanel updateEmp = update.getUpdateEmployeePanel();

        JFrame jFrame = new JFrame();
        jFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        jFrame.setContentPane(updateEmp);
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
    }
    private static void createDeleteEmployeeGUI() {
        DeleteEmployee delete = new DeleteEmployee();
        JPanel deleteEmp = delete.getDeleteEmployeePanel();

        JFrame jFrame = new JFrame();
        jFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        jFrame.setContentPane(deleteEmp);
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
    }
    /**
     * Các hàm để khi bấm button sẽ gọi bên .form để hiện form của Department
     */
    private static void createAddDepartmentGUI() {
        AddDepartment add = new AddDepartment();
        JPanel addDept = add.getAddDepartmentPane();

        JFrame jFrame = new JFrame();
        jFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        jFrame.setContentPane(addDept);
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
    }
    private static void createUpdateDepartmentGUI() {
        UpdateDepartment update = new UpdateDepartment();
        JPanel updateDept = update.getUpdateDepartmentPanel();

        JFrame jFrame = new JFrame();
        jFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        jFrame.setContentPane(updateDept);
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
    }
    private static void createDeleteDepartmentGUI() {
        DeleteDepartment delete = new DeleteDepartment();
        JPanel deleteDept = delete.getDeleteDepartmentPanel();

        JFrame jFrame = new JFrame();
        jFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        jFrame.setContentPane(deleteDept);
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
    }

    /**
     * Các hàm các chức năng khác
     */
    private static void createSearchEmployeeGUI() {
        Search search = new Search();
        JPanel searchEmp = search.getSearchEmployeePanel();

        JFrame jFrame = new JFrame();
        jFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        jFrame.setContentPane(searchEmp);
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
    }
    private static void createChangeDepartmentGUI() {
        ChangeDepartment change = new ChangeDepartment();
        JPanel changeDept = change.getChangeDepartmentPanel();

        JFrame jFrame = new JFrame();
        jFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        jFrame.setContentPane(changeDept);
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
    }
    private static void createIncomeTaxGUI() {
        IncomeTax getIncomeTax = new IncomeTax();
        JPanel incomtaxEmp = getIncomeTax.getIncomeTaxPanel();

        JFrame jFrame = new JFrame();
        jFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        jFrame.setContentPane(incomtaxEmp);
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
    }
    private DefaultTableModel tableModel;
    private ResultSet resultSet;
    private void showEmployee(){
        try {
            Object[] columnTitle = {"Id", "First Name", "Last Name", "Email", "Phone",
                    "Hire Date", "Job", "Salary", "Commission", "Manager Id", "Department Id"};
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
    private void showDepartment() {
        try {
            Object[] columnTitle = {"Department Id", "Department Name", "Manager Id", "Location Id"};
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
        showEmployee();
        showDepartment();
        return homePanel;
    }
    private JPanel homePanel;
    private JTable table1;
    private JTable table2;
    private JButton updateEmpButton;
    private JButton deleteEmpButton;
    private JButton addDeptButton;
    private JButton updateDeptButton;
    private JButton deleteDeptButton;
    private JButton addEmpButton;
    private JButton searchButton;
    private JButton refreshButton;
    private JButton changeDepartmentButton;
    private JButton incomeTaxButton;
    private JButton sortButton;
    private JButton logOutButton;
}