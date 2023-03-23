package Application;

import Dao.EmployeeDAO;
import Model.Department;
import Connection.ConnectJDBC;
import Model.Employee;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MainTest {
    public static void main(String[] args) throws SQLException {
        Connection con = ConnectJDBC.getConnection();
        List<Department> departmentList = new ArrayList<>();
        Department d = new Department();
        final String sql = "SELECT * FROM `departments`";

        try {
            Statement sta = con.createStatement();

            ResultSet res = sta.executeQuery(sql);

            while (res.next()) {
                d.setDept_id(res.getInt("dept_id"));
                d.setDept_name(res.getString("dept_name"));
                d.setManager_id(res.getInt("manager_id"));
                d.setLocation_id(res.getInt("location_id"));

                departmentList.add(d);
            }

            res.close();
            sta.close();
            con.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println( departmentList +"\n" );
        EmployeeDAO employeeDAO = new EmployeeDAO();
        Employee e = employeeDAO.getByID(1);
        System.out.println(e);
    }
}
