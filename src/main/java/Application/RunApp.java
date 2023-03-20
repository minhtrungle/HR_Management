package Application;

import javax.swing.*;

public class RunApp {
    public static void main(String[] args) {
        Login login = new Login(null);
        SwingUtilities.invokeLater(RunApp::createGUI);
    }

    public static void createGUI() {
        Home UI = new Home();
        JPanel root = UI.getMainPanel();

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(root);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }
}

