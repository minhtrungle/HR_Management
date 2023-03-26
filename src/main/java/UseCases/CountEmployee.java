package UseCases;

import Dao.EmployeeDAO;
import Model.Employee;

import java.sql.SQLException;
import java.util.List;

public class CountEmployee {
    public Long coutEmployee() throws SQLException {
        EmployeeDAO empDAO = new EmployeeDAO();
        List<Employee> empList = empDAO.getAllEmployee();

        long count = empList.stream().count();
        return count;
    }
}
