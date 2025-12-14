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
import java.awt.event.*;

public class EX_ConnexionController {

    private final EX_Connexion view;
    private String expectedOtp = null;
    private Timer otpTimer;
    private int otpSeconds = 0;
    

     public EX_ConnexionController(EX_Connexion view) {
        this.view = view;
        initListeners();//call the listeners
    }

     // listeners component of the connexion controller 
    private void initListeners() {

        // Show/Hide password and confirm password
        //password
        view.getTogglePasswordButton().addActionListener(new ActionListener() {
            boolean isPasswordVisible = false;

            @Override
            public void actionPerformed(ActionEvent e) {
                JPasswordField pwd = view.getInputPasswd();
                if (isPasswordVisible) {
                    pwd.setEchoChar('*');
                    view.getTogglePasswordButton().setText("Show");
                } else {
                    pwd.setEchoChar((char) 0);
                    view.getTogglePasswordButton().setText("Hide");
                }
                isPasswordVisible = !isPasswordVisible;
            }
        });
         //confirm password
        view.getToggleConfirmPasswordButton().addActionListener(new ActionListener() {
            boolean isPasswordVisible = false;

            @Override
            public void actionPerformed(ActionEvent e) {
                JPasswordField pwd = view.getConfirmPasswdInput();
                if (isPasswordVisible) {
                    pwd.setEchoChar('*');
                    view.getToggleConfirmPasswordButton().setText("Show");
                } else {
                    pwd.setEchoChar((char) 0);
                    view.getToggleConfirmPasswordButton().setText("Hide");
                }
                isPasswordVisible = !isPasswordVisible;
            }
        });

        view.getLoginRadio().addActionListener(e -> {
            if (view.getLoginRadio().isSelected()) {

                view.getNameLabel().setVisible(false);
                view.getNameInput().setVisible(false);

                view.getEmailLabel().setVisible(false);
                view.getEmailInput().setVisible(false);


                view.getOtpLabel().setVisible(false);
                view.getOtpInput().setVisible(false);
                view.getToggleOTPButton().setVisible(false);
                view.getOTPHintLabel().setVisible(false);

                view.getConfirmPasswdLabel().setVisible(false);
                view.getConfirmPasswdInput().setVisible(false);
                view.getToggleConfirmPasswordButton().setVisible(false);

                view.getCourseAdministratorRadio().setVisible(false);
                view.getAcademicOfficerRadio().setVisible(false);
                view.getRoleLabel().setVisible(false);

                // positions login
                view.getIdLabel().setBounds(50, 110, 100, 30);
                view.getInputID().setBounds(150, 110, 200, 30);

                view.getPasswdLabel().setBounds(50, 150, 100, 30);
                view.getInputPasswd().setBounds(150, 150, 200, 30);
                view.getTogglePasswordButton().setBounds(360, 150, 70, 30);

                view.getOkButton().setBounds(50, 200, 150, 40);
                view.getCancelButton().setBounds(250, 200, 150, 40);
            }
        });


        // Radio SIGN UP : show signup fields
        view.getSignRadio().addActionListener(e -> {
            if (view.getSignRadio().isSelected()) {

                view.getNameLabel().setVisible(true);
                view.getNameInput().setVisible(true);

                view.getEmailLabel().setVisible(true);
                view.getEmailInput().setVisible(true);

                
                view.getOtpLabel().setVisible(true);
                view.getOtpInput().setVisible(true);
                view.getToggleOTPButton().setVisible(true);
                view.getOTPHintLabel().setVisible(true);

                view.getConfirmPasswdLabel().setVisible(true);
                view.getConfirmPasswdInput().setVisible(true);
                view.getToggleConfirmPasswordButton().setVisible(true);

                view.getCourseAdministratorRadio().setVisible(true);
                view.getAcademicOfficerRadio().setVisible(true);
                view.getRoleLabel().setVisible(true);

                // positions signup (cohérentes avec initUI)
                view.getIdLabel().setBounds(50, 110, 100, 30);
                view.getInputID().setBounds(150, 110, 200, 30);

                view.getEmailLabel().setBounds(50, 150, 100, 30);
                view.getEmailInput().setBounds(150, 150, 200, 30);
                view.getToggleOTPButton().setBounds(360, 150, 70, 30);

                view.getOtpLabel().setBounds(50, 190, 100, 30);
                view.getOtpInput().setBounds(150, 190, 200, 30);

                view.getPasswdLabel().setBounds(50, 240, 100, 30);
                view.getInputPasswd().setBounds(150, 240, 200, 30);
                view.getTogglePasswordButton().setBounds(360, 240, 70, 30);

                view.getConfirmPasswdLabel().setBounds(50, 280, 150, 30);
                view.getConfirmPasswdInput().setBounds(150, 280, 200, 30);
                view.getToggleConfirmPasswordButton().setBounds(360, 280, 70, 30);

                view.getOkButton().setBounds(50, 420, 150, 40);
                view.getCancelButton().setBounds(250, 420, 150, 40);
            }
        });


        //  Buton OK with the logic for EX_write, EX_login and  EX_rule
        view.getOkButton().addActionListener(e -> {
            String id_user            = view.getInputID().getText();
            String fullName_user      = view.getNameInput().getText();
            String email_user         = view.getEmailInput().getText();
            String otp_user           = view.getOtpInput().getText().trim();
            String passwd_user        = new String(view.getInputPasswd().getPassword());
            String confirmPasswd_user = new String(view.getConfirmPasswdInput().getPassword());

            String role_user = "";
            if (view.getCourseAdministratorRadio().isSelected()) {
                role_user = view.getCourseAdministratorRadio().getText();
            } else if (view.getAcademicOfficerRadio().isSelected()) {
                role_user = view.getAcademicOfficerRadio().getText();
            }
            //binary log
            String log_action_user = view.getSignRadio().isSelected() ? "SIGN UP" : "LOGIN";

            // SIGN UP logic
            if (view.getSignRadio().isSelected()) {
                //all the rules before write the sign up
                 // check if any field is empty
                 // empty fields (inclure OTP)
                if (fullName_user.isEmpty() || id_user.isEmpty() || email_user.isEmpty()
                        || otp_user.isEmpty()
                        || passwd_user.isEmpty() || confirmPasswd_user.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please do not leave any fields empty", "Error", JOptionPane.ERROR_MESSAGE);
                }
                //rule for if id already exist
                else if (EX_rules.exist_ID(id_user)) {
                    JOptionPane.showMessageDialog(null, "ID already used", "Error", JOptionPane.ERROR_MESSAGE);
                }
                //rule for if email format is valid
                else if (!EX_rules.rule_Email(email_user)) { 
                    JOptionPane.showMessageDialog(null, "The email format is invalid", "Error", JOptionPane.ERROR_MESSAGE);
                } 
                //rule for  if email already exist
                else if (EX_rules.exist_Email(email_user)) {
                    JOptionPane.showMessageDialog(null, "The email is already used", "Error", JOptionPane.ERROR_MESSAGE);
                } 
                // OTP jamais envoyé
                else if (expectedOtp == null) {
                    JOptionPane.showMessageDialog(null, "Please request an OTP first (click OTP).", "Error", JOptionPane.ERROR_MESSAGE);
                }

                // OTP incorrect
                else if (!EX_OTPController.isOtpValid(expectedOtp, otp_user)) {
                    JOptionPane.showMessageDialog(null, "Invalid OTP code.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                //rule for if both password avec identical
                else if (!EX_rules.comparePasswords(passwd_user, confirmPasswd_user)) {
                    JOptionPane.showMessageDialog(null, "Passwords are not identical", "Error", JOptionPane.ERROR_MESSAGE);
                    
                } 
                //rule for if id or password don't respect format rules
                //error if rules id or password == false
                else if(EX_rules.rule_ID(id_user) ==false || EX_rules.rule_Passwd(passwd_user) ==false){
                    JOptionPane.showMessageDialog(null,
                            "Incorrect username or password, please follow the creation rules. \n "
                            + "Rules : \n "
                            + "ID : must be 5 – 15 characters long, letters and numbers \n"
                            + "Password : must be 8 – 16 characters long, a combination of Uppercase/Lowercase/Numbers and special symbols (*, !, &, @, #, $, %).",
                            "Error", JOptionPane.ERROR_MESSAGE); 
                } 
                
                //SIGN UP IF ALL VALID
                //signup is write in this part if all is good
                else if (EX_rules.rule_ID(id_user) && EX_rules.rule_Passwd(passwd_user )) {
                    Ex_write.writeToFile(fullName_user, id_user, passwd_user, email_user, role_user);
                    JOptionPane.showMessageDialog(null, "Account created successfully", "Welcome", JOptionPane.INFORMATION_MESSAGE);

                    view.getNameInput().setText("");
                    view.getInputID().setText("");
                    view.getEmailInput().setText("");
                    view.getInputPasswd().setText("");
                    view.getConfirmPasswdInput().setText("");
                    view.getOtpInput().setText("");
                    expectedOtp = null;

                    Ex_write.logTimestamp(id_user, log_action_user);
                    
                }

            } else { 
                // LOGIN logic
                // check if the fields are empty first
                if (id_user.isEmpty() || passwd_user.isEmpty()) {
                    JOptionPane.showMessageDialog(null,
                            "Please do not leave any fields empty",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
                // check if login exists
                else if (EX_login.exist_Login(id_user, passwd_user)) {
                    EX_Dashboard.openAfterLoginWindow(id_user);
                    view.dispose();
                    JOptionPane.showMessageDialog(null, "Connection successful", "Welcome", JOptionPane.INFORMATION_MESSAGE);

                    view.getInputID().setText("");
                    view.getInputPasswd().setText("");

                    Ex_write.logTimestamp(id_user, log_action_user);
                } 
                else { // if the login fails (invalid ID or password)
                    JOptionPane.showMessageDialog(null,
                            "Connection failed \n ID or PASSWORD are invalid",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

        });

        // cancel button
        view.getCancelButton().addActionListener(e -> {
            view.dispose(); // close the windows
        });
        view.getToggleOTPButton().addActionListener(e -> {
            String email = view.getEmailInput().getText().trim();

            if (email.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter your email first.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!EX_rules.rule_Email(email)) {
                JOptionPane.showMessageDialog(null, "Invalid email format.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            startOtpCooldown();

            String newToken = EmailService.generateOTP(); // ou EmailService.generateOTP()
            EmailService emailService = new EmailService();

            SwingWorker<Boolean, Void> worker = new SwingWorker<>() {
                @Override
                protected Boolean doInBackground() {
                    return emailService.sendEmailVerificationToken(email, newToken); 
                    // ⚠️ idéalement créez une méthode dédiée: sendAccountVerificationOtp(email, token)
                }

                @Override
                protected void done() {
                    try {
                        boolean sent = get();
                        if (sent) {
                            expectedOtp = newToken;                // ✅ ancien OTP invalidé
                            view.getOtpInput().setText("");        // ✅ force la saisie du nouveau
                            JOptionPane.showMessageDialog(null, "OTP sent to your email.", "Info", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, "OTP sending failed.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "OTP sending error.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            };
            worker.execute();
        });
    }
         private void startOtpCooldown() {
            otpSeconds = 30;
            JButton b = view.getToggleOTPButton();
            b.setEnabled(false);
            b.setText("OTP (" + otpSeconds + ")");

            if (otpTimer != null && otpTimer.isRunning()) otpTimer.stop();

            otpTimer = new Timer(1000, ev -> {
                otpSeconds--;
                if (otpSeconds <= 0) {
                    otpTimer.stop();
                    b.setEnabled(true);
                    b.setText("OTP");
                } else {
                    b.setText("" + otpSeconds );
                }
            });
            otpTimer.start();
        }
         

}

