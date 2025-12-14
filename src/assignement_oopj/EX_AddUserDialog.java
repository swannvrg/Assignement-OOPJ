/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignement_oopj;
import javax.swing.*;
import java.awt.*;
/**
 *
 * @author swann
 */




public class EX_AddUserDialog extends JDialog {

    private JTextField nameInput, inputID, emailInput, otpInput;
    private JPasswordField inputPasswd, confirmPasswdInput;
    private JLabel otpHintLabel;

    private JRadioButton courseAdministrator, academicOfficer;
    private JButton btnOk, btnCancel, toggleOTPButton, togglePasswordButton, toggleConfirmPasswordButton;

    public EX_AddUserDialog(JFrame parent) {
        super(parent, "Add User", true);
        setSize(460, 540);
        setLayout(null);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        initUI();

        setLocationRelativeTo(parent);
    }

    private void initUI() {
        JLabel titre = new JLabel("ADD USER");
        titre.setBounds(190, 10, 200, 30);
        add(titre);

        JLabel nameLabel = new JLabel("Full Name");
        nameLabel.setBounds(50, 60, 100, 30);
        add(nameLabel);

        nameInput = new JTextField();
        nameInput.setBounds(150, 60, 200, 30);
        add(nameInput);

        JLabel idLabel = new JLabel("USER ID");
        idLabel.setBounds(50, 100, 100, 30);
        add(idLabel);

        inputID = new JTextField();
        inputID.setBounds(150, 100, 200, 30);
        add(inputID);

        JLabel emailLabel = new JLabel("EMAIL");
        emailLabel.setBounds(50, 140, 100, 30);
        add(emailLabel);

        emailInput = new JTextField();
        emailInput.setBounds(150, 140, 200, 30);
        add(emailInput);

        toggleOTPButton = new JButton("OTP");
        toggleOTPButton.setBounds(360, 140, 70, 30);
        add(toggleOTPButton);

        JLabel otpLabel = new JLabel("OTP CODE");
        otpLabel.setBounds(50, 180, 100, 30);
        add(otpLabel);

        otpInput = new JTextField();
        otpInput.setBounds(150, 180, 200, 30);
        add(otpInput);

        otpHintLabel = new JLabel("Click OTP, then check your email to get the verification code.");
        otpHintLabel.setBounds(50, 210, 380, 15);
        otpHintLabel.setFont(new Font("Arial", Font.ITALIC, 11));
        add(otpHintLabel);

        JLabel passwdLabel = new JLabel("PASSWORD");
        passwdLabel.setBounds(50, 230, 100, 30);
        add(passwdLabel);

        inputPasswd = new JPasswordField();
        inputPasswd.setBounds(150, 230, 200, 30);
        add(inputPasswd);

        togglePasswordButton = new JButton("Show");
        togglePasswordButton.setBounds(360, 230, 70, 30);
        add(togglePasswordButton);

        JLabel confirmPasswdLabel = new JLabel("CONFIRM IT");
        confirmPasswdLabel.setBounds(50, 270, 150, 30);
        add(confirmPasswdLabel);

        confirmPasswdInput = new JPasswordField();
        confirmPasswdInput.setBounds(150, 270, 200, 30);
        add(confirmPasswdInput);

        toggleConfirmPasswordButton = new JButton("Show");
        toggleConfirmPasswordButton.setBounds(360, 270, 70, 30);
        add(toggleConfirmPasswordButton);

        JLabel roleLabel = new JLabel("ROLES");
        roleLabel.setBounds(50, 310, 100, 30);
        add(roleLabel);

        courseAdministrator = new JRadioButton("Course Administrator");
        academicOfficer = new JRadioButton("Academic Officer", true);
        courseAdministrator.setBounds(150, 310, 220, 30);
        academicOfficer.setBounds(150, 350, 220, 30);

        ButtonGroup groupRole = new ButtonGroup();
        groupRole.add(courseAdministrator);
        groupRole.add(academicOfficer);

        add(courseAdministrator);
        add(academicOfficer);

        btnOk = new JButton("OK");
        btnCancel = new JButton("CANCEL");
        btnOk.setBounds(50, 420, 150, 40);
        btnCancel.setBounds(250, 420, 150, 40);
        add(btnOk);
        add(btnCancel);
    }

    // getters
    public JTextField getNameInput() { return nameInput; }
    public JTextField getInputID() { return inputID; }
    public JTextField getEmailInput() { return emailInput; }
    public JTextField getOtpInput() { return otpInput; }
    public JPasswordField getInputPasswd() { return inputPasswd; }
    public JPasswordField getConfirmPasswdInput() { return confirmPasswdInput; }

    public JRadioButton getCourseAdministratorRadio() { return courseAdministrator; }
    public JRadioButton getAcademicOfficerRadio() { return academicOfficer; }

    public JButton getBtnOk() { return btnOk; }
    public JButton getBtnCancel() { return btnCancel; }
    public JButton getToggleOTPButton() { return toggleOTPButton; }
    public JButton getTogglePasswordButton() { return togglePasswordButton; }
    public JButton getToggleConfirmPasswordButton() { return toggleConfirmPasswordButton; }
}

