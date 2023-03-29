package Application.Employee;
import Dao.DepartmentDAO;
import Dao.EmployeeDAO;
import Model.Department;
import Model.Employee;
import UseCases.CheckExistEmployee;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Objects;

public class AddEmployee {
    private static EmployeeDAO empDAO = new EmployeeDAO();
    private static DepartmentDAO deptDAO = new DepartmentDAO();
    private static Employee emp = new Employee();
    JDateChooser dateChooser = new JDateChooser();
    public AddEmployee() {
        dateChooser.setDateFormatString("yyyy/MM/dd");
        jPanelHireDate.add(dateChooser);

        addButton.addActionListener(e -> {
            String id = textID.getText();
            String firstName = textFirstname.getText();
            String lastName = textLastname.getText();
            String email = textEmail.getText();
            String phone = textPhone.getText();
            SimpleDateFormat sdfm = new SimpleDateFormat("yyyy/MM/dd");
            String hireDate = sdfm.format(dateChooser.getDate());
            String salary = textSalary.getText();
            String commission = textCommission.getText();
//            String managerId = textManagerId.getText();
//            String departmentId = textDepartmentId.getText();

            if (!Objects.equals(id, "") && !Objects.equals(firstName, "") && !Objects.equals(lastName, "") && !Objects.equals(email, "")
                    && !Objects.equals(phone, "") && !Objects.equals(salary, "")
                    && !Objects.equals(commission, "")) {
                try {
                    emp.setId(Integer.parseInt(id));
                    emp.setFirstname(firstName);
                    emp.setLastname(lastName);
                    emp.setEmail(email);
                    emp.setPhone(phone);
                    emp.setHire_date(hireDate);

                    int index = comboBoxJob.getSelectedIndex();
                    if (index > 0) {
                        String selectedValue = (String) comboBoxJob.getItemAt(index);
                        emp.setJob(selectedValue);
                    }
                    emp.setSalary(Integer.parseInt(salary));
                    emp.setCommission(Double.parseDouble(commission));
                    //emp.setManager_id(Integer.parseInt(managerId));
                    //emp.setDepartment_id(Integer.parseInt(departmentId));
                    int index1 = comboBoxdeptId.getSelectedIndex();
                    if (index1 > 0) {
                        String selectedValue = (String) comboBoxdeptId.getItemAt(index);
                        emp.setDepartment_id(Integer.parseInt(selectedValue));
                        Department dept = deptDAO.getByID(Integer.parseInt(selectedValue));
                        int managerIdOfDept = dept.getManager_id();
                        textManagerId.setText(String.valueOf(managerIdOfDept));
                        emp.setManager_id(managerIdOfDept);
                    }

                    //Add
                    empDAO.insertEmployee(emp);

                    JOptionPane.showMessageDialog(null,
                            "Thêm nhân viên thành công",
                            "Thông báo",
                            JOptionPane.INFORMATION_MESSAGE);
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
                textSalary.setText("");
                textCommission.setText("");
//                textManagerId.setText("");
//                textDepartmentId.setText("");
            } else {
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

        checkButton.addActionListener(e -> {
            int id = Integer.parseInt(textID.getText());
            try {
                if (new CheckExistEmployee().checkID(id) == true) {
                    JOptionPane.showMessageDialog(null,
                            "Nhân viên đã tồn tại",
                            "Thông báo",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Có thể thêm nhân viên này",
                            "Thông báo",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
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
    private JTextField textSalary;
    private JTextField textCommission;
    private JTextField textManagerId;
    private JTextField textDepartmentId;
    private JButton checkButton;
    private JComboBox comboBoxJob;
    private JPanel jPanelHireDate;
    private JComboBox comboBoxdeptId;
}
