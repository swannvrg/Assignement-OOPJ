package assignement_oopj;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EX_ResetPasswordController {

    private final EX_ResetPassword view;
    private final EX_OTPController otpManager;

    public EX_ResetPasswordController(EX_ResetPassword view) {
        this.view = view;
        this.otpManager = new EX_OTPController(view);
        initListeners();
    }

    private void initListeners() {

        // Démarre cooldown dès l’ouverture
        otpManager.startResendCooldown();

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

        // Toggle CONFIRM password
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

        // Cancel
        view.getBtnCancel().addActionListener(e -> {
            String userID = view.getUserID();
            Ex_write.logTimestamp(userID, "CANCEL RESET PASSWORD");
            EX_Dashboard.openAfterLoginWindow(userID);
            view.dispose();
        });

        // Save
        view.getBtnSave().addActionListener(e -> {
            String userID = view.getUserID();

            // ✅ OTP check via la classe dédiée
            if (!otpManager.validateOtpOrShowError(userID)) {
                return;
            }

            // Passwords (JPasswordField)
            String newPass = new String(view.NewPasswdInput().getPassword());
            String confirm = new String(view.NewPasswdConfirmInput().getPassword());

            if (newPass.trim().isEmpty() || confirm.trim().isEmpty()) {
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

        // Resend OTP (délégué)
        view.getBtnResendOtp().addActionListener(e -> {
            String userID = view.getUserID();
            otpManager.resendOtp(userID, null);
        });
    }
}
