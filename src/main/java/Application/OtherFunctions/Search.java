package Application.OtherFunctions;

import Dao.EmployeeDAO;
import Model.Employee;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Objects;


public class Search {
    private static EmployeeDAO employeeDAO = new EmployeeDAO();
    public Search() {
        searchButton.addActionListener(e -> {
            if (!Objects.equals(textId.getText(), "")) {
                showInforEmp();
            }
            if (!Objects.equals(textName.getText(), "")) {
                showInforEmp();
            }
            if (!Objects.equals(textPhone.getText(), "")) {
                showInforEmp();
            }
            if (!Objects.equals(textEmail.getText(), "")) {
                showInforEmp();
            }
        });
        cancelButton.addActionListener(e -> {
            JComponent component = (JComponent) e.getSource();
            Window window = SwingUtilities.getWindowAncestor(component);
            window.dispose();
        });
    }
    private DefaultTableModel tableModel;

    private void showInforEmp(){
        String id = textId.getText();
        String name = textName.getText();
        String phone = textPhone.getText();
        String email = textEmail.getText();

        try {
            Object[] columnTitle = {"Id", "First Name", "Email", "Phone"};
            tableModel = new DefaultTableModel(null, columnTitle);
            tableShowInforEmp.setModel(tableModel);
            tableModel.getDataVector().removeAllElements();

            if (!Objects.equals(id, "")) {
                Employee e1 = employeeDAO.getByID(Integer.parseInt(id));
                Object[] data = {e1.getId(), e1.getFirstname(), e1.getEmail(), e1.getPhone(),};
                tableModel.addRow(data);
            }
            if (!Objects.equals(name, "")) {
                Employee e2 = employeeDAO.getByFirstName(name);
                Object[] data = { e2.getId(), e2.getFirstname(), e2.getEmail(), e2.getPhone(),};
                tableModel.addRow(data);
            }
            if (!Objects.equals(phone, "")) {
                Employee e3 = employeeDAO.getByPhone(phone);
                Object[] data = {e3.getId(), e3.getFirstname(), e3.getEmail(), e3.getPhone(),};
                tableModel.addRow(data);
            }
            if (!Objects.equals(email, "")) {
                Employee e4 = employeeDAO.getByEmail(email);
                Object[] data = {e4.getId(), e4.getFirstname(), e4.getEmail(), e4.getPhone(),};
                tableModel.addRow(data);
            }
        } catch (SQLException err){
            throw new RuntimeException(err);
        }
    }
    public JPanel getSearchEmployeePanel() {
        return searchEmployeePanel;
    }
    private JPanel searchEmployeePanel;
    private JTextField textId;
    private JTable tableShowInforEmp;
    private JTextField textName;
    private JTextField textPhone;
    private JTextField textEmail;
    private JButton cancelButton;
    private JButton searchButton;
}
