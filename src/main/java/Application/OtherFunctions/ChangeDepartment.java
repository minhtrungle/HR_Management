package Application.OtherFunctions;
import Connection.ConnectJDBC;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.*;
import java.util.Objects;

public class ChangeDepartment {
    public ChangeDepartment() {
        checkBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == 1) {
                    showTableEmpAndDept();
                    changeButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String empId = textEmpId.getText();
                            String deptId = textDeptId.getText();
                            String managerId = textManagerId.getText();
                            String sql = "UPDATE employees SET department_id = ?, manager_id = ? WHERE id = ?";

                            try {
                                Connection con = ConnectJDBC.getConnection();

                                PreparedStatement pre = con.prepareStatement(sql);

                                pre.setString(1, deptId);
                                pre.setString(2, managerId);
                                pre.setString(3, empId);


                                //Cập nhật dữ liệu
                                pre.executeUpdate();
                                //Đóng cửa sổ khi cập nhật xong
                                JOptionPane.showMessageDialog(null, "Chuyển phòng ban cho nhân viên thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                                JComponent component = (JComponent) e.getSource();
                                Window window = SwingUtilities.getWindowAncestor(component);
                                window.dispose();

                                pre.close();
                                con.close();
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    });
                } else {
                    changeButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String empId = textEmpId.getText();
                            String sql = "UPDATE employees SET department_id = ?, manager_id = ? WHERE id = ?";

                            try {
                                Connection con = ConnectJDBC.getConnection();

                                PreparedStatement pre = con.prepareStatement(sql);

                                pre.setString(1, null);
                                pre.setString(2, null);
                                pre.setString(3, empId);


                                //Cập nhật dữ liệu
                                pre.executeUpdate();
                                //Đóng cửa sổ khi cập nhật xong
                                JOptionPane.showMessageDialog(null, "Nhân viên tạm thời chưa có phòng ban", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                                JComponent component = (JComponent) e.getSource();
                                Window window = SwingUtilities.getWindowAncestor(component);
                                window.dispose();

                                pre.close();
                                con.close();
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    });
                }
            }
        });
        cancelButton.addActionListener(e -> {
            JComponent component = (JComponent) e.getSource();
            Window window = SwingUtilities.getWindowAncestor(component);
            window.dispose();
        });
        changeManagerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String managerId = textManagerId.getText();
                String deptId = textDeptId.getText();
                String sql = "UPDATE `employees` SET manager_id = ? WHERE department_id = ?";

                try {
                    Connection con = ConnectJDBC.getConnection();

                    PreparedStatement pre = con.prepareStatement(sql);


                    pre.setString(1, managerId);
                    pre.setString(2, deptId);
                    //Cập nhật dữ liệu
                    pre.executeUpdate();
                    //Đóng cửa sổ khi cập nhật xong
                    JComponent component = (JComponent) e.getSource();
                    Window window = SwingUtilities.getWindowAncestor(component);
                    window.dispose();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        changeManagerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String managerId = textManagerId.getText();
                String deptId = textDeptId.getText();
                String sql = "UPDATE `departments` SET manager_id = ? WHERE dept_id = ?";

                if (!Objects.equals(managerId, "") && !Objects.equals(deptId, "")) {
                    try {
                        Connection con = ConnectJDBC.getConnection();

                        PreparedStatement pre = con.prepareStatement(sql);


                        pre.setString(1, managerId);
                        pre.setString(2, deptId);
                        //Cập nhật dữ liệu
                        pre.executeUpdate();
                        //Đóng cửa sổ khi cập nhật xong
                        JOptionPane.showMessageDialog(null, "Cập nhật Manager Id thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                        JComponent component = (JComponent) e.getSource();
                        Window window = SwingUtilities.getWindowAncestor(component);
                        window.dispose();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null,
                            "Không được để trống",
                            "Cảnh báo", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
    private DefaultTableModel tableModel;
    private ResultSet resultSet;
    private void showTableEmpAndDept(){
        try {
            String empId = textEmpId.getText();
            final String sql = "SELECT id, department_id FROM employees WHERE id ='" +empId+"'";
            Object[] columnTitle = {"Empoyee Id", "Department Id"};
            tableModel = new DefaultTableModel(null, columnTitle);
            tableEmpAndDept.setModel(tableModel);

            Connection connection = ConnectJDBC.getConnection();
            Statement statement = connection.createStatement();
            tableModel.getDataVector().removeAllElements();

            resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                Object[] data = {
                        resultSet.getInt("id"),
                        resultSet.getInt("department_id"),
                };
                tableModel.addRow(data);
            }
        } catch (SQLException err){
            throw new RuntimeException(err);
        }
    }
    public JPanel getChangeDepartmentPanel() {
        return changeDepartmentPanel;
    }
    private JPanel changeDepartmentPanel;
    private JTextField textEmpId;
    private JCheckBox checkBox;
    private JTextField textDeptId;
    private JTable tableEmpAndDept;
    private JButton cancelButton;
    private JButton changeButton;
    private JTextField textManagerId;
    private JButton changeManagerButton;
}
