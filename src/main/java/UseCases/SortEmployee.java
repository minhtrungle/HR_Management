package UseCases;

import Dao.EmployeeDAO;
import Model.Employee;

import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

public class SortEmployee {
    public static void sortSalary() throws SQLException {

        EmployeeDAO empDAO = new EmployeeDAO();
        List<Employee> empList = empDAO.getAllEmployee();
        empList.stream()
                //Giảm dần
                .sorted((o1, o2) -> {
                    if (o1.getSalary() < o2.getSalary()) {
                        return 1;
                    } else if (o1.getSalary() > o2.getSalary()) {
                        return -1;
                    }
                    return 0;
                }).forEach(employee -> System.out.println(employee));
    }

    public static void main(String[] args) throws SQLException {
        sortSalary();
    }
}
