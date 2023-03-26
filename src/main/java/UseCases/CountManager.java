package UseCases;

import Dao.EmployeeDAO;
import Model.Employee;

import java.sql.SQLException;
import java.util.List;
import java.util.function.Consumer;

public class CountManager {
    public long countManager() throws SQLException {
        EmployeeDAO empDAO = new EmployeeDAO();
        List<Employee> empList = empDAO.getAllEmployee();

        long count = empList.stream().filter(e -> e.getManager_id() == 1).count();
        return count;
    }
}
