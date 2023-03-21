package Application.Employee;
import Application.Home.Home;
import Connection.ConnectJDBC;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

public class AddEmployee {
    public AddEmployee() {
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = textID.getText();
                String firstName = textFirstname.getText();
                String lastName = textLastname.getText();
                String email = textEmail.getText();
                String phone = textPhone.getText();
                String hireDate = textHireDate.getText();
                String job = textJob.getText();
                String salary = textSalary.getText();
                String commission = textCommission.getText();
                String managerId = textManagerId.getText();
                String departmentId = textDepartmentId.getText();
                String sql = "INSERT INTO `employees` (id, firstname, lastname, email, phone, hire_date, job, salary, commission, manager_id, department_id) VALUES (?,?,?,?,?,?,?,?,?,?,?)";

                if (!Objects.equals(id, "") && !Objects.equals(firstName, "") && !Objects.equals(lastName, "") && !Objects.equals(email, "") && !Objects.equals(phone, "")
                        && !Objects.equals(hireDate, "") && !Objects.equals(job, "") && !Objects.equals(salary, "") && !Objects.equals(commission, "") && !Objects.equals(managerId, "") && !Objects.equals(departmentId, "")) {
                    try {
                        Connection con = ConnectJDBC.getConnection();

                        PreparedStatement pre = con.prepareStatement(sql);
                        pre.setString(1, id);
                        pre.setString(2, firstName);
                        pre.setString(3, lastName);
                        pre.setString(4, email);
                        pre.setString(5, phone);
                        pre.setString(6, hireDate);
                        pre.setString(7, job);
                        pre.setString(8, salary);
                        pre.setString(9, commission);
                        pre.setString(10, managerId);
                        pre.setString(11, departmentId);

                        pre.executeUpdate();

                        JOptionPane.showMessageDialog(null, "Thêm nhân viên thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                        Home home = new Home();
                        home.getMainPanel();
                        //Đóng
                        JComponent component = (JComponent) e.getSource();
                        Window window = SwingUtilities.getWindowAncestor(component);
                        window.dispose();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    textID.setText("");
                    textFirstname.setText("");
                    textLastname.setText("");
                    textEmail.setText("");
                    textPhone.setText("");
                    textHireDate.setText("");
                    textJob.setText("");
                    textSalary.setText("");
                    textCommission.setText("");
                    textManagerId.setText("");
                    textDepartmentId.setText("");
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
    public JPanel getAddEmployeePanel(){
        return addEmployPanel;
    }
    private JButton cancelButton;
    private JButton addButton;
    private JTextField textID;
    private JTextField textFirstname;
    private JPanel addEmployPanel;
    private JTextField textLastname;
    private JTextField textEmail;
    private JTextField textPhone;
    private JTextField textHireDate;
    private JTextField textJob;
    private JTextField textSalary;
    private JTextField textCommission;
    private JTextField textManagerId;
    private JTextField textDepartmentId;
}
