/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignement_oopj;
import javax.swing.*;
import java.awt.Font;
/**
 *
 * @author swann
 */
public class EX_ResetPassword extends JFrame {
    
    private final String userID, userEmail;
    private String sentOTP;
    private JLabel textLabel, textLabel2, OTPLabel, OTPtext, NewPasswdLabel,NewPasswdLabelConfirm ;
    private JTextField OTPInput;
    private JPasswordField NewPasswdInput, NewPasswdConfirmInput;
    private JButton btnSave, btnCancel, btnResendOtp, toggleNewPasswordButton, toggleConfirmPasswordButton;
    public void setExpectedOtp(String otp) { this.sentOTP = otp; }
    public String getExpectedOtp() { return sentOTP; }
    
    public EX_ResetPassword(String idUser, String sentOTP){
        this.userID = idUser;
        String[] data = Ex_write.getUserData(idUser);
        this.userEmail = data [1];
        this.sentOTP = sentOTP;
        
        setTitle("Reset Password");
        setSize(400, 370);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initUI(); //call component

        setLocationRelativeTo(null);
        setVisible(true);
    }
    // interface component of the reset password page
    private void initUI() {
       textLabel = new JLabel("<html>We have just sent you an email to the following address: <br>" 
                       + userEmail + "</html>");
        textLabel2 = new JLabel("If you can't access this email, please reset it in the Edit section.");
        textLabel.setBounds(20, 10, 400, 40);  
        textLabel2.setBounds(20, 50, 400, 20);  
        textLabel2.setFont(new Font("Arial", Font.ITALIC, 12));
        add(textLabel);
        add(textLabel2);

        // OTP section
        OTPLabel = new JLabel("OTP NUMBER");
        OTPLabel.setBounds(20, 70, 300, 30);
        add(OTPLabel);

        OTPtext = new JLabel("Please enter the code sent by email:");
        OTPtext.setBounds(20, 90, 300, 30);
        OTPtext.setFont(new Font("Arial", Font.ITALIC, 12));
        add(OTPtext);

        OTPInput = new JTextField("");
        OTPInput.setBounds(20, 120, 170, 30);
        add(OTPInput);
       
        btnResendOtp = new JButton("Resend OTP");
        btnResendOtp.setBounds(200, 120, 130, 30);
        btnResendOtp.setEnabled(false); 
        add(btnResendOtp);

        // NEWPasswd section
        NewPasswdLabel = new JLabel("NEW PASSWORD");
        NewPasswdLabel.setBounds(20, 150, 300, 30);
        add(NewPasswdLabel);

        NewPasswdInput = new JPasswordField();
        NewPasswdInput.setBounds(20, 180, 200, 30);
        add(NewPasswdInput);

        toggleNewPasswordButton = new JButton("Show");
        toggleNewPasswordButton.setBounds(230, 180, 100, 30);
        add(toggleNewPasswordButton);

        // NEWPasswdConfirm section
        NewPasswdLabelConfirm = new JLabel("CONFIRM NEW PASSWORD");
        NewPasswdLabelConfirm.setBounds(20, 210, 300, 30);
        add(NewPasswdLabelConfirm);

        NewPasswdConfirmInput = new JPasswordField();
        NewPasswdConfirmInput.setBounds(20, 240, 200, 30);
        add(NewPasswdConfirmInput);

        toggleConfirmPasswordButton = new JButton("Show");
        toggleConfirmPasswordButton.setBounds(230, 240, 100, 30);
        add(toggleConfirmPasswordButton);

        // save button
        btnSave = new JButton("Save");
        btnSave.setBounds(60, 280, 100, 30);
        add(btnSave);

        // cancel button
        btnCancel = new JButton("Cancel");
        btnCancel.setBounds(220, 280, 100, 30);
        add(btnCancel);
    }
    
    //getter for controller  
    public String getUserID(){return userID;}
    public String getUserMail(){return userEmail;}
    
    public JTextField getOTPInput(){return OTPInput;}
    public JButton getBtnResendOtp() { return btnResendOtp; }

    public JPasswordField NewPasswdInput(){return NewPasswdInput;}
    public JPasswordField NewPasswdConfirmInput(){return NewPasswdConfirmInput;}
    public JButton getToggleNewPasswordButton(){ return toggleNewPasswordButton; }
    public JButton getToggleConfirmPasswordButton(){ return toggleConfirmPasswordButton; }

     public JButton getBtnSave() {return btnSave; }
    public JButton getBtnCancel() {return btnCancel;}
    public String getSentOTP(){return sentOTP;}
    
}
