/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignement_oopj;
import javax.swing.*;
/**
 *
 * @author swann
 */
public class EX_UserManagement extends JFrame {

    private final String adminId;
    private JButton btnAddUser, btnEditUser, btnDisableUser, btnBack;

    public EX_UserManagement(String adminId) {
        this.adminId = adminId;

        setTitle("User Management");
        setSize(420, 260);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initUI();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initUI() {
        JLabel title = new JLabel("USER MANAGEMENT");
        title.setBounds(135, 15, 200, 30);
        add(title);

        btnAddUser = new JButton("Add User");
        btnAddUser.setBounds(110, 60, 200, 35);
        add(btnAddUser);

        btnEditUser = new JButton("Edit User");
        btnEditUser.setBounds(110, 100, 200, 35);
        add(btnEditUser);

        btnDisableUser = new JButton("Disable User");
        btnDisableUser.setBounds(110, 140, 200, 35);
        add(btnDisableUser);

        btnBack = new JButton("Back");
        btnBack.setBounds(110, 185, 200, 30);
        add(btnBack);
    }

    // getters
    public String getAdminId() { return adminId; }
    public JButton getBtnAddUser() { return btnAddUser; }
    public JButton getBtnEditUser() { return btnEditUser; }
    public JButton getBtnDisableUser() { return btnDisableUser; }
    public JButton getBtnBack() { return btnBack; }
}


