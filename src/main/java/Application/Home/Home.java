package Application.Home;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.*;
import java.util.List;
import java.util.Objects;

import Application.Department.AddDepartment;
import Application.Department.DeleteDepartment;
import Application.Department.UpdateDepartment;
import Application.Employee.AddEmployee;
import Application.Employee.DeleteEmployee;
import Application.Employee.UpdateEmployee;
import Application.Login.Login;
import Application.OtherFunctions.ChangeDepartment;
import Application.OtherFunctions.IncomeTax;
import Application.OtherFunctions.Search;
import Dao.DepartmentDAO;
import Dao.EmployeeDAO;
import Model.Department;
import Model.Employee;
import UseCases.CountDepartment;
import UseCases.CountEmployee;
import UseCases.CountManager;

/**
 * Hiển thị các chức năng chọn và hiện danh sách employees và departments trong database
 * @author TrungLM
 */
public class Home {
    public Home() {
        addEmpButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(null,
                    "1. Trước khi thêm phải kiểm tra Id đã tồn tại chưa \n" +
                    "2. Nếu chưa thêm Manager và Department thì nhập 0",
                    "Chú ý",
                    JOptionPane.INFORMATION_MESSAGE);
            SwingUtilities.invokeLater(Home::createAddEmployeeGUI);
            //Loát lại table Employee
//                DefaultTableModel model = (DefaultTableModel) table1.getModel();
//                model.setRowCount(0);
//                showData1();
        });

        updateEmpButton.addActionListener(e -> SwingUtilities.invokeLater(Home::createUpdateEmployeeGUI));

        deleteEmpButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(null,
                    "Nếu sa thải trưởng phòng cần cập nhật lại danh sách trưởng phòng ở Employee và Department",
                    "Chú ý",
                    JOptionPane.INFORMATION_MESSAGE);
            SwingUtilities.invokeLater(Home::createDeleteEmployeeGUI);
        });

        addDeptButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(null,
                    "Nếu thêm nhân viên lên làm trưởng phòng thì sẽ tự động cập nhật phòng ban mới cho nhân viên đó",
                    "Chú ý",
                    JOptionPane.INFORMATION_MESSAGE);
            SwingUtilities.invokeLater(Home::createAddDepartmentGUI);
        });

        updateDeptButton.addActionListener(e -> SwingUtilities.invokeLater(Home::createUpdateDepartmentGUI));

        deleteDeptButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(null,
                    "Trước khi xóa phải chuyển phòng ban cho nhân viên, đồng ý thì tích còn không thì BƯỚC",
                    "Chú ý",
                    JOptionPane.INFORMATION_MESSAGE);
            SwingUtilities.invokeLater(Home::createDeleteDepartmentGUI);
        });

        refreshButton.addActionListener(e -> {
            showEmployee();
            showDepartment();
            showCurrentofEmpAndDept();
        });

        searchButton.addActionListener(e -> SwingUtilities.invokeLater(Home::createSearchEmployeeGUI));

        changeDepartmentButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(null,
                    "1. Nếu nhân viên tạm thời chưa được xếp phòng ban thì không tích vào ô \n" +
                    "2. Nếu đưa nhân viên lên làm quản lý thì danh sách quản lý bên Employee và Department phải cập nhật cùng \n",
                    "Chú ý",
                    JOptionPane.INFORMATION_MESSAGE);
            SwingUtilities.invokeLater(Home::createChangeDepartmentGUI);
        });

        incomeTaxButton.addActionListener(e -> SwingUtilities.invokeLater(Home::createIncomeTaxGUI));

        sortButton.addActionListener(e -> {
            try {
                Object[] columnTitle = {"Id", "First Name", "Last Name", "Email", "Phone",
                        "Hire Date", "Job", "Salary", "Commission", "Manager Id", "Department Id"};
                tableModel = new DefaultTableModel(null, columnTitle);
                table1.setModel(tableModel);
                tableModel.getDataVector().removeAllElements();

                EmployeeDAO empDAO = new EmployeeDAO();
                List<Employee> empList = empDAO.getAllEmployee();
                empList.stream()
                        //Giảm dần
                        .sorted((o1, o2) -> {
                            if (o1.getSalary() < o2.getSalary()) {
                                return 1;
                            } else if (o1.getSalary() > o2.getSalary()) {
                                return -1;
                            }
                            return 0;
                        })
                        .forEach(emp -> {
                            Object[] data = { emp.getId(), emp.getFirstname(), emp.getLastname(), emp.getEmail(), emp.getPhone(),
                                    emp.getHire_date(), emp.getJob(), emp.getSalary(), emp.getCommission(), emp.getManager_id(),
                                    emp.getDepartment_id(),};
                            tableModel.addRow(data);
                        });
            } catch (SQLException err){
                throw new RuntimeException(err);
            }
        });

        logOutButton.addActionListener(e -> {
            JComponent component = (JComponent) e.getSource();
            Window window = SwingUtilities.getWindowAncestor(component);
            window.dispose();
            JOptionPane.showMessageDialog(null,
                    "Bạn đã Logout thành công ",
                    "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE);
            new Login(null);
        });
        textSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                //Chuyển viết hoa thành thường
                String searchString = textSearch.getText().toLowerCase();
                searchEmployee(searchString);
            }
        });
    }
    public void searchEmployee( String str) {
        tableModel = (DefaultTableModel) table1.getModel();
        TableRowSorter<DefaultTableModel> tableRowSorter = new TableRowSorter<>(tableModel);
        table1.setRowSorter(tableRowSorter);
        tableRowSorter.setRowFilter(RowFilter.regexFilter(str));
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
    private void showEmployee(){
        try {
            Object[] columnTitle = {"Id", "First Name", "Last Name", "Email", "Phone",
                    "Hire Date", "Job", "Salary", "Commission", "Manager Id", "Department Id"};
            tableModel = new DefaultTableModel(null, columnTitle);
            table1.setModel(tableModel);
            tableModel.getDataVector().removeAllElements();

            EmployeeDAO empDAO = new EmployeeDAO();
            List<Employee> empList = empDAO.getAllEmployee();
            for ( int i = 0; i < empList.size(); i++) {
                Object[] data = {
                        empList.get(i).getId(),
                        empList.get(i).getFirstname(),
                        empList.get(i).getLastname(),
                        empList.get(i).getEmail(),
                        empList.get(i).getPhone(),
                        empList.get(i).getHire_date(),//yyyy/mm/dd
                        empList.get(i).getJob(),
                        empList.get(i).getSalary(),
                        empList.get(i).getCommission(),
                        empList.get(i).getManager_id(),
                        empList.get(i).getDepartment_id(),
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
            tableModel.getDataVector().removeAllElements();

            DepartmentDAO deptDAO = new DepartmentDAO();
            List<Department> listDept = deptDAO.getAllDepartment();
            for ( int i = 0; i < listDept.size(); i++) {
                Object[] data = {
                        listDept.get(i).getDept_id(),
                        listDept.get(i).getDept_name(),
                        listDept.get(i).getManager_id(),
                        listDept.get(i).getLocation_id(),
                };
                tableModel.addRow(data);
            }
        } catch (SQLException err){
            throw new RuntimeException(err);
        }
    }
    public void showCurrentofEmpAndDept() {
        try {
            long countEmp = new CountEmployee().coutEmployee();
            textCountEmployee.setText(String.valueOf(countEmp));

            long countManager = new CountManager().countManager();
            textCountManager.setText(String.valueOf(countManager));

            long countDept = new CountDepartment().coutDepartment();
            textCountDepartment.setText(String.valueOf(countDept));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public JPanel getMainPanel(){
        showEmployee();
        showDepartment();
        showCurrentofEmpAndDept();
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
    private JTextField textSearch;
    private JPanel countEmployee;
    private JLabel textCountManager;
    private JLabel textCountEmployee;
    private JLabel textCountDepartment;

}