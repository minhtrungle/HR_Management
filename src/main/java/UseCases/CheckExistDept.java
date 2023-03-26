package UseCases;

import Dao.DepartmentDAO;
import Model.Department;


import java.sql.SQLException;

public class CheckExistDept {
    public boolean checkID(int id) throws SQLException {
        DepartmentDAO deptDAO = new DepartmentDAO();
        Department dept = deptDAO.getByID(id);
        if (dept != null) {
            return true;
        }
        return false;
    }
}
