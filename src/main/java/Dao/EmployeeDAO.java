package Dao;

import Model.Employee;
import Connection.ConnectJDBC;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object nhân viên
 * @author TrungLM
 */
public class EmployeeDAO {
    /**
     * Lấy toàn bộ dữ liệu từ bảng employees
     * @return employeeList
     */
    public static List<Employee> getAllEmployee() throws SQLException {
        Connection con = ConnectJDBC.getConnection();
        List<Employee> employeeList = new ArrayList<>();
        Employee e = new Employee();
        final String sql = "SELECT * FROM `employees`";

        try {
            Statement sta = con.createStatement();

            ResultSet res = sta.executeQuery(sql);

            while (res.next()) {
                e.setId(res.getInt("id"));
                e.setFirstname(res.getString("firstname"));
                e.setLastname(res.getString("lastname"));
                e.setEmail(res.getString("email"));
                e.setPhone(res.getString("phone"));
                e.setHire_date(res.getString("hire_date"));
                e.setJob(res.getString("job"));
                e.setSalary(res.getInt("salary"));
                e.setCommission(res.getDouble("commission"));
                e.setManager_id(res.getInt("manager_id"));
                e.setDepartment_id(res.getInt("department_id"));

                employeeList.add(e);
            }

            res.close();
            sta.close();
            con.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return employeeList;
    }

    public Employee getByID(int id) throws SQLException {
        Connection con = ConnectJDBC.getConnection();
        final String sql = "SELECT * FROM `employees` WHERE `id` = " + id;
        Employee e = null;

        try {
            Statement sta = con.createStatement();

            ResultSet res = sta.executeQuery(sql);

            if(res.next()) {
                e = new Employee();
                e.setId(res.getInt("id"));
                e.setFirstname(res.getString("firstname"));
                e.setLastname(res.getString("lastname"));
                e.setEmail(res.getString("email"));
                e.setPhone(res.getString("phone"));
                e.setHire_date(res.getString("hire_date"));
                e.setJob(res.getString("job"));
                e.setSalary(res.getInt("salary"));
                e.setCommission(res.getDouble("commission"));
                e.setManager_id(res.getInt("manager_id"));
                e.setDepartment_id(res.getInt("department_id"));
            }

            res.close();
            sta.close();
            con.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return e;
    }
    public static Employee getSalaryOfEmployeeByID(int id) throws SQLException {
        Connection con = ConnectJDBC.getConnection();
        Employee e = new Employee();
        final String sql = "SELECT salary FROM `employees` WHERE id = " + id;

        try {
            Statement sta = con.createStatement();

            ResultSet res = sta.executeQuery(sql);

            if (res.next()) {
                e.setSalary(res.getInt("salary"));
            }

            res.close();
            sta.close();
            con.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return e;
    }
    public Employee getByFirstName(String name) throws SQLException {
        Connection con = ConnectJDBC.getConnection();
        final String sql = "SELECT * FROM `employees` WHERE `firstname` LIKE '%" + name + "%'";
        Employee e = null;

        try {
            Statement sta = con.createStatement();

            ResultSet res = sta.executeQuery(sql);

            if(res.next()) {
                e = new Employee();
                e.setId(res.getInt("id"));
                e.setFirstname(res.getString("firstname"));
                e.setLastname(res.getString("lastname"));
                e.setEmail(res.getString("email"));
                e.setPhone(res.getString("phone"));
                e.setHire_date(res.getString("hire_date"));
                e.setJob(res.getString("job"));
                e.setSalary(res.getInt("salary"));
                e.setCommission(res.getDouble("commission"));
                e.setManager_id(res.getInt("manager_id"));
                e.setDepartment_id(res.getInt("department_id"));
            }

            res.close();
            sta.close();
            con.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return e;
    }

    public Employee getByPhone(String phone) throws SQLException {
        Connection con = ConnectJDBC.getConnection();
        final String sql = "SELECT * FROM `employees` WHERE `phone` LIKE '%" + phone + "%'";
        Employee e = null;

        try {
            Statement sta = con.createStatement();

            ResultSet res = sta.executeQuery(sql);

            if(res.next()) {
                e = new Employee();
                e.setId(res.getInt("id"));
                e.setFirstname(res.getString("firstname"));
                e.setLastname(res.getString("lastname"));
                e.setEmail(res.getString("email"));
                e.setPhone(res.getString("phone"));
                e.setHire_date(res.getString("hire_date"));
                e.setJob(res.getString("job"));
                e.setSalary(res.getInt("salary"));
                e.setCommission(res.getDouble("commission"));
                e.setManager_id(res.getInt("manager_id"));
                e.setDepartment_id(res.getInt("department_id"));
            }

            res.close();
            sta.close();
            con.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return e;
    }
    public Employee getByEmail(String email) throws SQLException {
        Connection con = ConnectJDBC.getConnection();
        final String sql = "SELECT * FROM `employees` WHERE `email` LIKE '%" + email + "%'";
        Employee e = null;

        try {
            Statement sta = con.createStatement();

            ResultSet res = sta.executeQuery(sql);

            if(res.next()) {
                e = new Employee();
                e.setId(res.getInt("id"));
                e.setFirstname(res.getString("firstname"));
                e.setLastname(res.getString("lastname"));
                e.setEmail(res.getString("email"));
                e.setPhone(res.getString("phone"));
                e.setHire_date(res.getString("hire_date"));
                e.setJob(res.getString("job"));
                e.setSalary(res.getInt("salary"));
                e.setCommission(res.getDouble("commission"));
                e.setManager_id(res.getInt("manager_id"));
                e.setDepartment_id(res.getInt("department_id"));
            }

            res.close();
            sta.close();
            con.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return e;
    }

    public void insertEmployee(Employee e) throws SQLException {
        Connection con = ConnectJDBC.getConnection();
        final String sql = String.format("INSERT INTO `employees` VALUES ('%d', '%s', '%s', '%s', '%s', '%s', '%s', '%d', '%f', '%d', '%d')",
               e.getId(), e.getFirstname(), e.getLastname(), e.getEmail(), e.getPhone(), e.getHire_date(), e.getJob(), e.getSalary(),
                e.getCommission(), e.getManager_id(), e.getDepartment_id());

        try {
            Statement sta = con.createStatement();

            long res = sta.executeUpdate(sql);

            if (res == 0) {
                System.out.println("Insert employees thất bại");
            }

            sta.close();
            con.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void updateEmployee(Employee e, int id) throws SQLException {
        Connection con = ConnectJDBC.getConnection();

        Employee emp = getByID(id);
        if (emp == null) {
            throw new RuntimeException("Không có nhân viên thõa mãn!");
        }

        final String sql = String.format("UPDATE `employees` SET `firstname`='%s', `lastname` = '%s', `email` = '%s', `phone` = '%s'," +
                        "`hire_date` = '%s', `job` = '%s', `salary` = '%d', `commission` = '%f', `manager_id` = '%d', `department_id` = '%d'  WHERE `id` = '%d",
                e.getFirstname(), e.getLastname(), e.getEmail(), e.getPhone(), e.getHire_date(), e.getJob(), e.getSalary(),
                e.getCommission(), e.getManager_id(), e.getDepartment_id(), id);

        try {
            Statement sta = con.createStatement();

            long res = sta.executeUpdate(sql);

            if (res == 0) {
                System.out.println("Update employees thất bại");
            }

            sta.close();
            con.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void deleteEmployee(int id) throws SQLException {
        Connection con = ConnectJDBC.getConnection();

        Employee emp = getByID(id);
        if (emp == null) {
            throw new RuntimeException("Không có nhân viên thõa mãn!");
        }

        final String sql =  "DELETE FROM `students` WHERE `id` = " + id;

        try {
            Statement sta = con.createStatement();

            long res = sta.executeUpdate(sql);

            if (res == 0) {
                System.out.println("Delete employees thất bại");
            }

            sta.close();
            con.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
