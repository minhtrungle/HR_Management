package UseCases;

import Dao.EmployeeDAO;
import Model.Employee;

import java.sql.SQLException;

public class CheckExistEmployee {
    public boolean checkID(int id) throws SQLException {
        EmployeeDAO empDAO = new EmployeeDAO();
        Employee emp = empDAO.getByID(id);
        if (emp != null) {
            return true;
        }
        return false;
    }
}
