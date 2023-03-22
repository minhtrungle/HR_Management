package Application.Department;
import Application.Home.Home;
import Application.OtherFunctions.ChangeDepartment;
import Connection.ConnectJDBC;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.*;
import java.util.Objects;

public class DeleteDepartment {
    public DeleteDepartment() {
        getInforButton.addActionListener(e -> showTableEmpOfDept());
        checkBoxDelete.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == 1) {
                    changeDepartmentButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            SwingUtilities.invokeLater(DeleteDepartment::createChangeDepartmentGUI);
                        }
                    });
                    //Không còn nhân viên mới xóa được
                    if (showTableEmpOfDept() == false) {
                        JOptionPane.showMessageDialog(null, "Không còn nhân viên có thể xóa phòng ban", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                        deleteButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                String idDept = textDeptId.getText();
                                final String sql = "DELETE FROM `employees` WHERE dept_id = ?";
                                if (!Objects.equals(idDept, "")) {
                                    try {
                                        Connection con = ConnectJDBC.getConnection();

                                        PreparedStatement pre = con.prepareStatement(sql);

                                        pre.setString(1, idDept);
                                        //Cập nhật dữ liệu
                                        pre.executeUpdate();
                                        //Đóng cửa sổ khi cập nhật xong
                                        JOptionPane.showMessageDialog(null, "Xóa phòng ban thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                                        JComponent component = (JComponent) e.getSource();
                                        Window window = SwingUtilities.getWindowAncestor(component);
                                        window.dispose();
                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(null,
                                            "Không được để trống",
                                            "Cảnh báo", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        });
                    }
                } else {
                    deleteButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            JOptionPane.showMessageDialog(null, "Không tích chọn ở trên thì BƯỚC", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                        }
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
    private boolean showTableEmpOfDept(){
        if (true) {
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
        return false;
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
    public JPanel getDeleteDepartmentPanel() {
        return deleteDepartmentPanel;
    }
    private JPanel deleteDepartmentPanel;
    private JTextField textDeptId;
    private JButton getInforButton;
    private JTable tableEmpOfDept;
    private JCheckBox checkBoxDelete;
    private JButton cancelButton;
    private JButton deleteButton;
    private JButton changeDepartmentButton;
}
