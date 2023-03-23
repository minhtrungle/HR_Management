package UseCases;

import Dao.EmployeeDAO;
import Model.Employee;

import java.sql.SQLException;

public class IncomeTaxUseCase {
    public static Double  getIncomeTaxById(int id, int number) throws SQLException {
        EmployeeDAO employeeDAO = new EmployeeDAO();
        Employee e = employeeDAO.getSalaryOfEmployeeByID(id);
        long salaryVND = e.getSalary();
        long TNTT = salaryVND - 11000 - 4400 * number; //Thu nhập tính thuế
        Double incomeTax = 0.00;
        if (salaryVND < 11000) {
            incomeTax = 0.00;
        }
        if (TNTT > 0 && TNTT <= 5000) {
            incomeTax = TNTT * 5 / 100.00;
        }
        if (TNTT > 5000 && TNTT <= 10000) {
            incomeTax = TNTT * 10 / 100.00;
        }
        if (TNTT > 10000 && TNTT <= 18000) {
            incomeTax = TNTT * 15 / 100.00;
        }
        if (TNTT > 18000 && TNTT <= 32000) {
            incomeTax = TNTT * 20 / 100.00;
        }
        if (TNTT > 32000 && TNTT <= 52000) {
            incomeTax = TNTT * 25 / 100.00;
        }
        if (TNTT > 52000 && TNTT <= 80000) {
            incomeTax = TNTT * 30 / 100.00;
        }
        if (TNTT > 80000) {
            incomeTax = TNTT * 35 / 100.00;
        }

      return incomeTax;
    }
}
