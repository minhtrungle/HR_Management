package Application.OtherFunctions;

import Dao.EmployeeDAO;
import Model.Employee;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import static UseCases.IncomeTaxUseCase.getIncomeTaxById;

public class IncomeTax {
    public IncomeTax() {
        getButton.addActionListener(e -> showIncomeTax());

        cancelButton.addActionListener(e -> {
            JComponent component = (JComponent) e.getSource();
            Window window = SwingUtilities.getWindowAncestor(component);
            window.dispose();
        });
    }
    private DefaultTableModel tableModel;
    private void showIncomeTax(){
        try {
            String empId = textEmpId.getText();
            String number = textNumofDepd.getText();
            EmployeeDAO employeeDAO = new EmployeeDAO();
            Employee e = employeeDAO.getByID(Integer.parseInt(empId));

            Object[] columnTitle = {"Id", "First Name", "Last Name", "Job", "Salary", "Department Id", "Income Tax"};
            tableModel = new DefaultTableModel(null, columnTitle);
            tableShow.setModel(tableModel);
            tableModel.getDataVector().removeAllElements();

            Object[] data = {
                    e.getId(),
                    e.getFirstname(),
                    e.getLastname(),
                    e.getJob(),
                    e.getSalary(),
                    e.getDepartment_id(),
                    getIncomeTaxById(Integer.parseInt(empId), Integer.parseInt(number)),
            };
            tableModel.addRow(data);

        } catch (SQLException err){
            throw new RuntimeException(err);
        }
    }
    public JPanel getIncomeTaxPanel() {
        return IncomeTaxPanel;
    }
    private JPanel IncomeTaxPanel;
    private JTextField textEmpId;
    private JTable tableShow;
    private JButton cancelButton;
    private JButton getButton;
    private JTextField textNumofDepd;
}
