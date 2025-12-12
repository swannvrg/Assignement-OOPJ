/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignement_oopj;
import javax.swing.*;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.Window;

/**
 *
 * @author swann
 */
public class EX_ResetPasswordController {
    private final EX_ResetPassword view ;
    private Timer resendTimer;
    private int resendSeconds = 30;
    
    public EX_ResetPasswordController(EX_ResetPassword view){
        this.view = view;
        initListeners(); //call listeners
    }
    
    private void startResendCooldown() {
        resendSeconds = 30;
        view.getBtnResendOtp().setEnabled(false);
        view.getBtnResendOtp().setText("Resend (" + resendSeconds + ")");

        if (resendTimer != null && resendTimer.isRunning()) {
            resendTimer.stop();
        }

        resendTimer = new Timer(1000, e -> {
            resendSeconds--;
            if (resendSeconds <= 0) {
                resendTimer.stop();
                view.getBtnResendOtp().setEnabled(true);
                view.getBtnResendOtp().setText("Resend OTP");
            } else {
                view.getBtnResendOtp().setText("Resend (" + resendSeconds + ")");
            }
        });
        resendTimer.start();
    }

    
    
    // listeners component of the reset passwd controller 
    private void initListeners(){
        startResendCooldown();
        // Toggle NEW password 
        view.getToggleNewPasswordButton().addActionListener(new ActionListener() {
            boolean isPasswordVisible = false;

            @Override
            public void actionPerformed(ActionEvent e) {
                JPasswordField pwd = view.NewPasswdInput();
                if (isPasswordVisible) {
                    pwd.setEchoChar('*');
                    view.getToggleNewPasswordButton().setText("Show");
                } else {
                    pwd.setEchoChar((char) 0);
                    view.getToggleNewPasswordButton().setText("Hide");
                }
                isPasswordVisible = !isPasswordVisible;
            }
        });
         // Toggle NEW password confirm
        view.getToggleConfirmPasswordButton().addActionListener(new ActionListener() {
            boolean isPasswordVisible = false;

            @Override
            public void actionPerformed(ActionEvent e) {
                JPasswordField pwd = view.NewPasswdConfirmInput();
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
        
         // Cancel close window
        view.getBtnCancel().addActionListener(e ->{
            String userID = view.getUserID();
            System.out.println("btn cancel");
             // write on log file
            String log_action_user = "CANCEL RESET PASSWORD";
            Ex_write.logTimestamp(userID, log_action_user);
            //open edit window
            EX_Dashboard.openAfterLoginWindow(userID);
            // close the window
            view.dispose();
        });
        
         // Save  validations and update file
        view.getBtnSave().addActionListener(e -> {
            String userID = view.getUserID();
            String userOTP = view.getOTPInput().getText();
            String sentOTP = view.getSentOTP();
            
            if (userOTP == null || userOTP.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "Please enter the OTP code.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                Ex_write.logTimestamp(userID, "EMPTY OTP - RESET PASSWORD");
                return;
            }

            if (!EX_rules.isOtpValid(sentOTP, userOTP)) {
                JOptionPane.showMessageDialog(null,
                        "Invalid OTP. Please check the code you received by email.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                Ex_write.logTimestamp(userID, "OTP INVALID - RESET PASSWORD");
                return;
            }
            String newPass = view.NewPasswdInput().getText();
            String confirm = view.NewPasswdConfirmInput().getText();

            if (newPass == null || confirm == null || newPass.trim().isEmpty() || confirm.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "Please do not leave any fields empty.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                Ex_write.logTimestamp(userID, "EMPTY PASSWORD FIELDS - RESET PASSWORD");
                return;
            }
            
            if (!EX_rules.rule_Passwd(newPass) || !EX_rules.rule_Passwd(confirm)) {
                JOptionPane.showMessageDialog(null,
                        "Password must be 8-16 characters and include: uppercase, lowercase, digit, and one of these: * ! & @ # $ %",
                        "Error", JOptionPane.ERROR_MESSAGE);
                Ex_write.logTimestamp(userID, "PASSWORD RULE FAILED - RESET PASSWORD");
                return;
            }

            if (!EX_rules.comparePasswords(newPass, confirm)) {
                JOptionPane.showMessageDialog(null,
                        "Passwords do not match.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                Ex_write.logTimestamp(userID, "PASSWORDS NOT MATCHING - RESET PASSWORD");
                return;
            }
            boolean updated = Ex_write.updateUserPassword(userID, newPass);

            if (!updated) {
                JOptionPane.showMessageDialog(null,
                        "Could not update password. Please try again.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                Ex_write.logTimestamp(userID, "PASSWORD UPDATE FAILED - RESET PASSWORD");
                return;
            }

            JOptionPane.showMessageDialog(null,
                    "Password updated successfully.",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            Ex_write.logTimestamp(userID, "PASSWORD UPDATED - RESET PASSWORD");

            EX_Dashboard.openAfterLoginWindow(userID);
            view.dispose();
        });
        //resend OTP after 30s
        view.getBtnResendOtp().addActionListener(e -> {
            String userID = view.getUserID();
            String userEmail = view.getUserMail();

            // Désactive immédiatement + lance cooldown 30s
            startResendCooldown();

            EmailService emailService = new EmailService();
            String newToken = EmailService.generateOTP();

            SwingWorker<Boolean, Void> worker = new SwingWorker<>() {
                @Override
                protected Boolean doInBackground() {
                    return emailService.sendPasswordResetToken(userEmail, newToken);
                }

                @Override
                protected void done() {
                    try {
                        boolean sent = get();

                        if (sent) {
                            view.setExpectedOtp(newToken);// remplace l'ancien OTP
                            view.getOTPInput().setText("");
                            JOptionPane.showMessageDialog(null,
                                    "A new OTP has been sent to your email.",
                                    "Info", JOptionPane.INFORMATION_MESSAGE);
                            Ex_write.logTimestamp(userID, "OTP RESENT - RESET PASSWORD");
                        } else {
                            JOptionPane.showMessageDialog(null,
                                    "Failed to resend OTP. Please try again later.",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                            Ex_write.logTimestamp(userID, "OTP RESEND FAILED - RESET PASSWORD");

                            // Option: si vous voulez permettre de réessayer immédiatement en cas d'échec:
                            // stopResendCooldownAndEnable();
                        }

                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null,
                                "Unexpected error while resending OTP.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                        Ex_write.logTimestamp(userID, "OTP RESEND ERROR - RESET PASSWORD");
                        ex.printStackTrace();

                        // Option: réessai immédiat possible:
                        // stopResendCooldownAndEnable();
                    }
                }
            };

            worker.execute();
        });
        
    }
    
    
}
