package Application;

import Dao.EmployeeDAO;
import Model.Department;
import Connection.ConnectJDBC;
import Model.Employee;
import UseCases.SortEmployee;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MainTest {
    public static void main(String[] args) throws SQLException {

        EmployeeDAO employeeDAO = new EmployeeDAO();
        List<Employee> employeeList = employeeDAO.getAllEmployee();
        System.out.println(employeeList);
    }
}
