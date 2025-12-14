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

public class EX_Connexion extends JFrame {

    // component
    private JRadioButton login, sign;
    private JTextField nameInput, inputID, emailInput, otpInput;
    private JPasswordField inputPasswd, confirmPasswdInput;
    private JButton toggleOTPButton, togglePasswordButton, toggleConfirmPasswordButton;
    private JRadioButton courseAdministrator, academicOfficer;
    private JButton btnOk, btnCancel;
    private JLabel nameLabel, idLabel, emailLabel, passwdLabel, confirmPasswdLabel, roleLabel,otpLabel, otpHintLabel ;

    // constructor
    public EX_Connexion() {
        setTitle("Registration Form");  
        setSize(500, 550);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initUI(); //call interface component
        
        setLocationRelativeTo(null); // center the windows
        setVisible(true);
    }
    
    
    // interface component of the connexion page
    private void initUI() {
        setLayout(null);

        // Title
        JLabel titre = new JLabel("REGISTER");
        titre.setBounds(200, 5, 200, 30);
        add(titre);

        // btn login/sign up
        login = new JRadioButton("LOGIN");
        sign  = new JRadioButton("SIGN UP", true);
        login.setBounds(100, 30, 100, 30);
        sign.setBounds(250, 30, 120, 30);
        ButtonGroup groupLog = new ButtonGroup();
        groupLog.add(login);
        groupLog.add(sign);
        add(login);
        add(sign);

        // Name input
        nameLabel = new JLabel("Full Name");
        nameLabel.setBounds(50, 70, 100, 30);
        nameInput = new JTextField();
        nameInput.setBounds(150, 70, 200, 30);
        add(nameLabel);
        add(nameInput);

        // User ID input
        idLabel = new JLabel("USER ID");
        idLabel.setBounds(50, 110, 100, 30);
        inputID = new JTextField();
        inputID.setBounds(150, 110, 200, 30);
        add(idLabel);
        add(inputID);

        // Email input
        emailLabel = new JLabel("EMAIL");
        emailLabel.setBounds(50, 150, 100, 30);
        emailInput = new JTextField();
        emailInput.setBounds(150, 150, 200, 30);
        add(emailLabel);
        add(emailInput);

        // Send OTP button (à droite de l'email)
        toggleOTPButton = new JButton("OTP");
        toggleOTPButton.setBounds(360, 150, 70, 30);
        add(toggleOTPButton);

        // OTP input 
        otpLabel = new JLabel("OTP CODE");
        otpLabel.setBounds(50, 190, 100, 30);
        otpInput = new JTextField();
        otpInput.setBounds(150, 190, 200, 30);
        add(otpLabel);
        add(otpInput);

        // 
        otpHintLabel = new JLabel("Click OTP, then check your email to get the verification code.");
        otpHintLabel.setBounds(50, 220, 320, 15);
        otpHintLabel.setFont(new Font("Arial", Font.ITALIC, 11));
        add(otpHintLabel);

        // Password input 
        passwdLabel = new JLabel("PASSWORD");
        passwdLabel.setBounds(50, 240, 100, 30);
        inputPasswd = new JPasswordField();
        inputPasswd.setBounds(150, 240, 200, 30);
        add(passwdLabel);
        add(inputPasswd);

        // Show password
        togglePasswordButton = new JButton("Show");
        togglePasswordButton.setBounds(360, 240, 70, 30);
        add(togglePasswordButton);

        // Confirm password
        confirmPasswdLabel = new JLabel("CONFIRM IT");
        confirmPasswdLabel.setBounds(50, 280, 150, 30);
        confirmPasswdInput = new JPasswordField();
        confirmPasswdInput.setBounds(150, 280, 200, 30);
        add(confirmPasswdLabel);
        add(confirmPasswdInput);

        toggleConfirmPasswordButton = new JButton("Show");
        toggleConfirmPasswordButton.setBounds(360, 280, 70, 30);
        add(toggleConfirmPasswordButton);

        // Roles
        roleLabel = new JLabel("ROLES");
        roleLabel.setBounds(50, 320, 100, 30);
        courseAdministrator = new JRadioButton("Course Administrator");
        academicOfficer     = new JRadioButton("Academic Officer", true);
        courseAdministrator.setBounds(150, 320, 220, 30);
        academicOfficer.setBounds(150, 360, 220, 30);

        ButtonGroup groupRole = new ButtonGroup();
        groupRole.add(courseAdministrator);
        groupRole.add(academicOfficer);

        add(roleLabel);
        add(courseAdministrator);
        add(academicOfficer);

        // Buttons
        btnOk = new JButton("OK");
        btnCancel = new JButton("CANCEL");
        btnOk.setBounds(50, 420, 150, 40);
        btnCancel.setBounds(250, 420, 150, 40);
        add(btnOk);
        add(btnCancel);
    }

    // getter for the controller

    public JRadioButton getLoginRadio() { return login; }
    public JRadioButton getSignRadio() { return sign; }

    public JTextField getNameInput() { return nameInput; }
    public JTextField getInputID() { return inputID; }
    public JTextField getEmailInput() { return emailInput; }
    
    public JLabel getOtpLabel(){ return otpLabel; }
    public JTextField getOtpInput(){ return otpInput; }
    public JButton getToggleOTPButton(){ return toggleOTPButton; }

    public JPasswordField getInputPasswd() { return inputPasswd; }
    public JPasswordField getConfirmPasswdInput() { return confirmPasswdInput; }

    public JButton getTogglePasswordButton() { return togglePasswordButton; }
    public JButton getToggleConfirmPasswordButton() { return toggleConfirmPasswordButton; }

    public JRadioButton getCourseAdministratorRadio() { return courseAdministrator; }
    public JRadioButton getAcademicOfficerRadio() { return academicOfficer; }

    public JButton getOkButton() { return btnOk; }
    public JButton getCancelButton() { return btnCancel; }

    public JLabel getNameLabel() { return nameLabel; }
    public JLabel getIdLabel() { return idLabel; }
    public JLabel getEmailLabel() { return emailLabel; }
    public JLabel getPasswdLabel() { return passwdLabel; }
    public JLabel getConfirmPasswdLabel() { return confirmPasswdLabel; }
    public JLabel getRoleLabel() { return roleLabel; }
    public JLabel getOTPHintLabel() {return otpHintLabel;}

    // Main to lauch the app
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EX_Connexion view = new EX_Connexion();        
            new EX_ConnexionController(view);           
        });
    }
}
