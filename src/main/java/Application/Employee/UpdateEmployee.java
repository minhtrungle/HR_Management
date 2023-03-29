package Application.Employee;
import Dao.EmployeeDAO;
import Model.Employee;
import UseCases.CheckExistEmployee;
import com.toedter.calendar.JDateChooser;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Objects;
public class UpdateEmployee {
    private static EmployeeDAO empDAO = new EmployeeDAO();
    private static Employee emp = new Employee();
    public UpdateEmployee() {

        updateButton.addActionListener(e -> {
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

            //Kiểm tra không được để trống ô nhập, id!=""
            if (!Objects.equals(id, "") && !Objects.equals(firstName, "") && !Objects.equals(lastName, "") && !Objects.equals(email, "")
                    && !Objects.equals(phone, "") && !Objects.equals(hireDate, "") && !Objects.equals(job, "") && !Objects.equals(salary, "")
                    && !Objects.equals(commission, "") && !Objects.equals(managerId, "") && !Objects.equals(departmentId, "")) {
                try {
                    emp.setFirstname(firstName);
                    emp.setLastname(lastName);
                    emp.setEmail(email);
                    emp.setPhone(phone);
                    emp.setHire_date(hireDate);
                    emp.setJob(job);
                    emp.setSalary(Integer.parseInt(salary));
                    emp.setCommission(Double.parseDouble(commission));
                    emp.setManager_id(Integer.parseInt(managerId));
                    emp.setDepartment_id(Integer.parseInt(departmentId));
                    //Update
                    empDAO.updateEmployee(emp, Integer.parseInt(id));

                    //Đóng cửa sổ khi cập nhật xong
                    JOptionPane.showMessageDialog(null,
                            "Cập nhật nhân viên thành công",
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

        cancelButton.addActionListener(e -> {
            JComponent component = (JComponent) e.getSource();
            Window window = SwingUtilities.getWindowAncestor(component);
            window.dispose();
        });

        getButton.addActionListener(e -> {
            int id = Integer.parseInt(textId.getText());
            try {
                if (new CheckExistEmployee().checkID(id) == true) {
                    Employee emp = empDAO.getByID(id);
                    textFirstName.setText(emp.getFirstname());
                    textLastName.setText(emp.getLastname());
                    textEmail.setText(emp.getEmail());
                    textPhone.setText(emp.getPhone());
                    textHireDate.setText(emp.getHire_date());
                    textJob.setText(emp.getJob());
                    textSalary.setText(String.valueOf(emp.getSalary()));
                    textCommission.setText(String.valueOf(emp.getCommission()));
                    textManagerId.setText(String.valueOf(emp.getManager_id()));
                    textDepartmentId.setText(String.valueOf(emp.getDepartment_id()));
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Không có nhân viên",
                            "Thông báo",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
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
    private JButton getButton;
}
