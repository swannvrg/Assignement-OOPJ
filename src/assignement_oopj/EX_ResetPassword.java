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
    private JLabel textLabel, textLabel2, OTPLabel, OTPtext, NewPasswdLabel,NewPasswdLabelConfirm ;
    private JTextField OTPInput,NewPasswdInput, NewPasswdConfirmInput;
    private JButton btnSave, btnCancel;
    
    public EX_ResetPassword(String idUser){
        this.userID = idUser;
        String[] data = Ex_write.getUserData(idUser);
        this.userEmail = data [1];
        
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
        OTPInput.setBounds(20, 120, 300, 30);
        add(OTPInput);

        // NEWPasswd section
        NewPasswdLabel = new JLabel("NEW PASSWORD");
        NewPasswdLabel.setBounds(20, 150, 300, 30);
        add(NewPasswdLabel);

        NewPasswdInput = new JTextField();
        NewPasswdInput.setBounds(20, 180, 300, 30);
        add(NewPasswdInput);

        // NEWPasswdConfirm section
        NewPasswdLabelConfirm = new JLabel("CONFIRM NEW PASSWORD");
        NewPasswdLabelConfirm.setBounds(20, 210, 300, 30);
        add(NewPasswdLabelConfirm);

        NewPasswdConfirmInput = new JTextField();
        NewPasswdConfirmInput.setBounds(20, 240, 300, 30);
        add(NewPasswdConfirmInput);

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
    public JTextField NewPasswdInput(){return NewPasswdInput;}
    public JTextField NewPasswdConfirmInput(){return NewPasswdConfirmInput;}
    
     public JButton getBtnSave() {return btnSave; }
    public JButton getBtnCancel() {return btnCancel;}
    
}
