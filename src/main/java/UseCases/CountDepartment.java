package UseCases;

import Dao.DepartmentDAO;
import Dao.EmployeeDAO;
import Model.Department;
import Model.Employee;

import java.sql.SQLException;
import java.util.List;

public class CountDepartment {
    public Long coutDepartment() throws SQLException {
        DepartmentDAO deptDAO = new DepartmentDAO();
        List<Department> deptList = deptDAO.getAllDepartment();

        long count = deptList.stream().count();
        return count;
    }
}
