package Application.Employee;
import Connection.ConnectJDBC;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Objects;
public class UpdateEmployee {

    public UpdateEmployee() {
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = textId.getText();
                String firstName = textFirstName.getText();
                String lastName = textLastName.getText();
                String email = textEmail.getText();
                String phone = textPhone.getText();
                String hireDate = textHireDate.getText();
                String job = textJob.getText();
                String salary = textSalary.getText();
                String commission = textCommission.getText();
                String managerId = textManagerId.getText();
                String departmentId = textDepartmentId.getText();
                String sql = "UPDATE `employees` SET firstname = ?, lastname = ?, email = ?, phone = ?, " +
                        "hire_date = ?, job = ?, salary = ?, commission = ?, manager_id = ?, department_id = ? WHERE id = ?";
                //Kiểm tra không được để trống ô nhập id!=""
                if (!Objects.equals(id, "") && !Objects.equals(firstName, "") && !Objects.equals(lastName, "") && !Objects.equals(email, "") && !Objects.equals(phone, "")
                        && !Objects.equals(hireDate, "") && !Objects.equals(job, "") && !Objects.equals(salary, "") && !Objects.equals(commission, "") && !Objects.equals(managerId, "") && !Objects.equals(departmentId, "")) {
                    try {
                        Connection con = ConnectJDBC.getConnection();

                        PreparedStatement pre = con.prepareStatement(sql);

                        pre.setString(1, firstName);
                        pre.setString(2, lastName);
                        pre.setString(3, email);
                        pre.setString(4, phone);
                        pre.setString(5, hireDate);
                        pre.setString(6, job);
                        pre.setString(7, salary);
                        pre.setString(8, commission);
                        pre.setString(9, managerId);
                        pre.setString(10, departmentId);
                        pre.setString(11, id);
                        //Cập nhật dữ liệu
                        pre.executeUpdate();
                        //Đóng cửa sổ khi cập nhật xong
                        JOptionPane.showMessageDialog(null, "Cập nhật nhân viên thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
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

    public JPanel getUpdateEmployeePanel(){
        return updateEmployeePanel;
    }
    private JPanel updateEmployeePanel;
    private JTextField textId;
    private JTextField textFirstName;
    private JTextField textLastName;
    private JTextField textEmail;
    private JTextField textPhone;
    private JTextField textHireDate;
    private JTextField textJob;
    private JTextField textSalary;
    private JTextField textCommission;
    private JTextField textManagerId;
    private JTextField textDepartmentId;
    private JButton cancelButton;
    private JButton updateButton;
}
