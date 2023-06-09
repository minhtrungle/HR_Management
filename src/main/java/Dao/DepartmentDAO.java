package Dao;

import Model.Department;
import Model.Employee;
import Connection.ConnectJDBC;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object phòng ban
 * @author TrungLM
 */
public class DepartmentDAO {
    /**
     * Lấy toàn bộ dữ liệu từ bảng departments
     * @return employeeList
     */
    public static List<Department> getAllDepartment() throws SQLException {
        Connection con = ConnectJDBC.getConnection();
        List<Department> departmentList = new ArrayList<>();
        final String sql = "SELECT * FROM `departments`";

        try {
            Statement sta = con.createStatement();

            ResultSet res = sta.executeQuery(sql);

            while (res.next()) {
                Department d = new Department();
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
        return departmentList;
    }

    public Department getByID(int id) throws SQLException {
        Connection con = ConnectJDBC.getConnection();
        final String sql = "SELECT * FROM `departments` WHERE `dept_id` = " + id;
        Department d = null;

        try {
            Statement sta = con.createStatement();

            ResultSet res = sta.executeQuery(sql);

            if (res.next()) {
                d = new Department();
                d.setDept_id(res.getInt("dept_id"));
                d.setDept_name(res.getString("dept_name"));
                d.setManager_id(res.getInt("manager_id"));
                d.setLocation_id(res.getInt("location_id"));
            }

            res.close();
            sta.close();
            con.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return d;
    }

    public void insertDepartment(Department d) throws SQLException {
        Connection con = ConnectJDBC.getConnection();
        final String sql = String.format("INSERT INTO `departments` VALUES (?, ?, ?, ?)");

        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, d.getDept_id());
            pre.setString(2, d.getDept_name());
            pre.setInt(3, d.getManager_id());
            pre.setInt(4, d.getLocation_id());

            long res = pre.executeUpdate();

            if (res == 0) {
                System.out.println("Insert departments thất bại");
            }

            pre.close();
            con.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void updateDepartment(Department d, int id) throws SQLException {
        Connection con = ConnectJDBC.getConnection();

        Department dep = getByID(id);
        if (dep == null) {
            throw new RuntimeException("Không có phòng ban thõa mãn!");
        }

        final String sql = String.format("UPDATE `departments` SET `dept_name` = '%s', `manager_id` = '%d', `location_id` = '%d' WHERE dept_id = " + id,
               d.getDept_name(), d.getManager_id(), d.getLocation_id(), id);

        try {
            Statement sta = con.createStatement();

            long res = sta.executeUpdate(sql);

            if (res == 0) {
                System.out.println("Update departments thất bại");
            }

            sta.close();
            con.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void deleteDepartment(int id) throws SQLException {
        Connection con = ConnectJDBC.getConnection();

        Department dep = getByID(id);
        if (dep == null) {
            throw new RuntimeException("Không có phòng ban thõa mãn!");
        }

        final String sql = "DELETE FROM `departments` WHERE `dept_id` = " + id;

        try {
            Statement sta = con.createStatement();

            long res = sta.executeUpdate(sql);

            if (res == 0) {
                System.out.println("Delete departments thất bại");
            }

            sta.close();
            con.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void insertEmployeeByDept(Employee e, int idDept) throws SQLException {
        Connection con = ConnectJDBC.getConnection();
        final String sql = String.format("INSERT INTO `employees` VALUES ('%d', '%s', '%s', '%s', '%s', '%s', '%s', '%d', '%f', '%d', '%d')",
                e.getId(), e.getFirstname(), e.getLastname(), e.getEmail(), e.getPhone(), e.getHire_date(), e.getJob(), e.getSalary(),
                e.getCommission(), e.getManager_id(), idDept);

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
    public void deleteEmployeeByDept(int id) throws SQLException {
        Connection con = ConnectJDBC.getConnection();

//        if (emp == null) {
//            throw new RuntimeException("Không có nhân viên thõa mãn!");
//        }

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
