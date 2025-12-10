/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignement_oopj;

/**
 *
 * @author swann
 */

import javax.swing.*;

public class EX_EditProfile extends JFrame {

    private JTextField idField, nameField, emailField;
    private JRadioButton courseAdministrator, academicOfficer;
    private JButton btnSave, btnCancel, btnResetPasswd;
  

    private final String originalId;  // ID utilisé pour retrouver l'utilisateur

    public EX_EditProfile(String idUser) {
        this.originalId = idUser;

        setTitle("Edit Profile");
        setSize(400, 450);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initUI(); //call component

        setLocationRelativeTo(null);
        setVisible(true);
    }
    // interface component of the edit page
    private void initUI() {
        JLabel idLabel = new JLabel("User ID:");
        idLabel.setBounds(30, 20, 300, 30);
        add(idLabel);

        idField = new JTextField();
        idField.setBounds(30, 50, 300, 30);
        add(idField);

        //full name
        JLabel nameLabel = new JLabel("Full Name:");
        nameLabel.setBounds(30, 90, 300, 30);
        add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(30, 120, 300, 30);
        add(nameField);

        //email
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(30, 160, 300, 30);
        add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(30, 190, 300, 30);
        add(emailField);

        //role
        JLabel roleLabel = new JLabel("Role:");
        roleLabel.setBounds(30, 230, 300, 30);
        add(roleLabel);

        courseAdministrator = new JRadioButton("Course Administrator");
        academicOfficer     = new JRadioButton("Academic Officer");
        courseAdministrator.setBounds(30, 260, 200, 30);
        academicOfficer.setBounds(30, 290, 200, 30);

        ButtonGroup groupRole = new ButtonGroup();
        groupRole.add(courseAdministrator);
        groupRole.add(academicOfficer);

        add(courseAdministrator);
        add(academicOfficer);
        
        //reset password
        btnResetPasswd = new JButton("Reset your Password here");
        btnResetPasswd.setBounds(90, 330,200,30);
        add(btnResetPasswd);

        //save button
        btnSave = new JButton("Save");
        btnSave.setBounds(60, 370, 100, 30);
        add(btnSave);

        //cancel button
        btnCancel = new JButton("Cancel");
        btnCancel.setBounds(220, 370, 100, 30);
        add(btnCancel);
    }

    // getter for the controller

    public String getOriginalId() {return originalId;}
    public JTextField getIdField() {return idField;}
    public JTextField getNameField() {return nameField;}
    public JTextField getEmailField() {return emailField;}
    public JRadioButton getCourseAdministratorRadio() {return courseAdministrator;}
    public JRadioButton getAcademicOfficerRadio() {return academicOfficer;}
    public JButton getBtnSave() {return btnSave; }
    public JButton getBtnCancel() {return btnCancel;}
    public JButton getBtnResetPasswd(){return btnResetPasswd;}
 
    // method to pre-fill all fields
    public void setUserData(String id, String fullName, String email, String role) {
        idField.setText(id);
        nameField.setText(fullName);
        emailField.setText(email);

        if ("Course Administrator".equalsIgnoreCase(role)) {
            courseAdministrator.setSelected(true);
        } else {
            academicOfficer.setSelected(true);
        }
    }
}
