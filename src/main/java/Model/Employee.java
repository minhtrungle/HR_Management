package Model;
/**
 * Model nhân viên
 * @author TrungLM
 */
public class Employee implements Comparable<Employee> {
    private int id;
    private String firstname;
    private String lastname;

    private String email;
    private  String phone;
    private String hire_date;
    private String job;
    private int salary;
    private double commission;
    private int manager_id;
    private int department_id;

    public Employee() {
    }

    public Employee(int id, String firstname, String lastname) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public Employee(int id, String firstname, String lastname, String email, String phone, String hire_date, String job, int salary, double commission, int manager_id, int department_id) {
        if (salary < 0) {
            throw new RuntimeException("Error");
        }
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phone = phone;
        this.hire_date = hire_date;
        this.job = job;
        this.salary = salary;
        this.commission = commission;
        this.manager_id = manager_id;
        this.department_id = department_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHire_date() {
        return hire_date;
    }

    public void setHire_date(String hire_date) {
        this.hire_date = hire_date;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        if (salary < 0) {
            throw new RuntimeException("Error");
        }
        this.salary = salary;
    }

    public double getCommission() {
        return commission;
    }

    public void setCommission(double commission) {
        this.commission = commission;
    }

    public int getManager_id() {
        return manager_id;
    }

    public void setManager_id(int manager_id) {
        this.manager_id = manager_id;
    }

    public int getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(int department_id) {
        this.department_id = department_id;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", hire_date='" + hire_date + '\'' +
                ", job='" + job + '\'' +
                ", salary=" + salary +
                ", commission=" + commission +
                ", manager_id=" + manager_id +
                ", department_id=" + department_id +
                '}';
    }

    @Override
    public int compareTo(Employee o) {
        String mFullName = lastname + " " + firstname;
        String oFullName = o.lastname + " " + o.firstname;
        return mFullName.compareTo(oFullName);
    }
}
