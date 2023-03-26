package UseCases;

import Dao.DepartmentDAO;
import Dao.EmployeeDAO;
import Model.Department;

import java.sql.SQLException;

public class CheckIsManager {
    public boolean checkID(int idEmp) throws SQLException {
        DepartmentDAO deptDAO = new DepartmentDAO();
        EmployeeDAO empDAO = new EmployeeDAO();

        int idDept = empDAO.getByID(idEmp).getDepartment_id();
        Department dept = deptDAO.getByID(idDept);
        int idManger = dept.getManager_id();

        if (idEmp == idManger) {
            return true;
        }
        return false;
    }
}
