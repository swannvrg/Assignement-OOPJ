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
import java.awt.*;

public class EX_EditProfile extends JFrame {

    private JTextField idField, nameField, emailField,otpField;
    private JRadioButton courseAdministrator, academicOfficer;
    private JButton btnSave, btnCancel, btnResetPasswd, btnEmailOtp;
    private JLabel otpLabel, otpHintLabel;

    private final String originalId;  // ID utilisé pour retrouver l'utilisateur

    public EX_EditProfile(String idUser) {
        this.originalId = idUser;

        setTitle("Edit Profile");
        setSize(400, 550);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initUI(); //call component

        setLocationRelativeTo(null);
        setVisible(true);
    }
    

private void initUI() {
    setLayout(null);

    JLabel idLabel = new JLabel("User ID:");
    idLabel.setBounds(30, 20, 300, 30);
    add(idLabel);

    idField = new JTextField();
    idField.setBounds(30, 50, 300, 30);
    add(idField);

    // Full name
    JLabel nameLabel = new JLabel("Full Name:");
    nameLabel.setBounds(30, 90, 300, 30);
    add(nameLabel);

    nameField = new JTextField();
    nameField.setBounds(30, 120, 300, 30);
    add(nameField);

    // Email
    JLabel emailLabel = new JLabel("Email:");
    emailLabel.setBounds(30, 160, 300, 30);
    add(emailLabel);

    emailField = new JTextField();
    emailField.setBounds(30, 190, 220, 30);
    add(emailField);

    // otp button
    btnEmailOtp = new JButton("OTP");
    btnEmailOtp.setBounds(260, 190, 70, 30);
    add(btnEmailOtp);

    // otp label
    otpLabel = new JLabel("OTP code :");
    otpLabel.setBounds(30, 230, 300, 30);
    add(otpLabel);

    otpField = new JTextField();
    otpField.setBounds(30, 260, 300, 30);
    add(otpField);

    // Lebel otp text
    otpHintLabel = new JLabel("Click OTP, then check your email to get the verification code.");
    otpHintLabel.setBounds(30, 292, 380, 15);
    otpHintLabel.setFont(new Font("Arial", Font.ITALIC, 11));
    add(otpHintLabel);

    // Role (descend)
    JLabel roleLabel = new JLabel("Role:");
    roleLabel.setBounds(30, 315, 300, 30);
    add(roleLabel);

    courseAdministrator = new JRadioButton("Course Administrator");
    academicOfficer     = new JRadioButton("Academic Officer");
    courseAdministrator.setBounds(30, 345, 220, 30);
    academicOfficer.setBounds(30, 375, 220, 30);

    ButtonGroup groupRole = new ButtonGroup();
    groupRole.add(courseAdministrator);
    groupRole.add(academicOfficer);

    add(courseAdministrator);
    add(academicOfficer);

    // Reset password
    btnResetPasswd = new JButton("Reset Password here");
    btnResetPasswd.setBounds(90, 415, 220, 30);
    add(btnResetPasswd);

    // Save / Cancel
    btnSave = new JButton("Save");
    btnSave.setBounds(60, 455, 100, 30);
    add(btnSave);

    btnCancel = new JButton("Cancel");
    btnCancel.setBounds(220, 455, 100, 30);
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
    public JTextField getOtpField() { return otpField; }
    public JLabel getOtpLabel() { return otpLabel; }
    public JLabel getOtpHintLabel() { return otpHintLabel; }
    public JButton getBtnEmailOtp() { return btnEmailOtp; }

 
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
