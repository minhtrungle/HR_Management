package UseCases;

import Dao.EmployeeDAO;
import Model.Employee;

import java.sql.SQLException;
import java.util.List;
import java.util.function.Predicate;

public class CountEmpOfDept {
    public Long coutEmployee(int idDept) throws SQLException {
        EmployeeDAO employeeDAO = new EmployeeDAO();
        List<Employee> employeeList = employeeDAO.getAllEmployee();

        long count = employeeList.stream()
                .filter(employee -> employee.getDepartment_id() == idDept)
                .count();
        return count;
    }
}
