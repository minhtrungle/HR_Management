package Application;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import Connection.ConnectJDBC;
public class Home {
    public Home() {
        employeeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        departmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public void Home() {
        showData1();
        showData2();
    }
    public JPanel getMainPanel(){
        showData1();
        showData2();
        return homePanel;
    }

    private DefaultTableModel tableModel;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;
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
                        resultSet.getDate("hire_date"),
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
    private JPanel homePanel;
    private JTable table1;
    private JTable table2;
    private JButton employeeButton;
    private JButton departmentButton;
}
