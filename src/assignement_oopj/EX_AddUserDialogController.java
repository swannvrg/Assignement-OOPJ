/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignement_oopj;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 *
 * @author swann
 */



public class EX_AddUserDialogController {

    private final EX_AddUserDialog view;

    private String expectedOtp = null;
    private Timer otpTimer;
    private int otpSeconds = 0;

    public EX_AddUserDialogController(EX_AddUserDialog view) {
        this.view = view;
        initListeners();
    }

    private void initListeners() {

        // Show/Hide password
        view.getTogglePasswordButton().addActionListener(new ActionListener() {
            boolean visible = false;
            @Override public void actionPerformed(ActionEvent e) {
                if (visible) {
                    view.getInputPasswd().setEchoChar('*');
                    view.getTogglePasswordButton().setText("Show");
                } else {
                    view.getInputPasswd().setEchoChar((char) 0);
                    view.getTogglePasswordButton().setText("Hide");
                }
                visible = !visible;
            }
        });

        // Show/Hide confirm password
        view.getToggleConfirmPasswordButton().addActionListener(new ActionListener() {
            boolean visible = false;
            @Override public void actionPerformed(ActionEvent e) {
                if (visible) {
                    view.getConfirmPasswdInput().setEchoChar('*');
                    view.getToggleConfirmPasswordButton().setText("Show");
                } else {
                    view.getConfirmPasswdInput().setEchoChar((char) 0);
                    view.getToggleConfirmPasswordButton().setText("Hide");
                }
                visible = !visible;
            }
        });

        // OTP send/resend
        view.getToggleOTPButton().addActionListener(e -> {
            String email = view.getEmailInput().getText().trim();

            if (email.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Please enter the email first.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!EX_rules.rule_Email(email)) {
                JOptionPane.showMessageDialog(view, "The email format is invalid.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (EX_rules.exist_Email(email)) {
                JOptionPane.showMessageDialog(view, "The email is already used.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            startOtpCooldown3s();

            String newToken = EmailService.generateOTP(); // ou EmailService.generateOTP()
            EmailService emailService = new EmailService();

            SwingWorker<Boolean, Void> worker = new SwingWorker<>() {
                @Override protected Boolean doInBackground() {
                    return emailService.sendEmailVerificationToken(email, newToken);
                }
                @Override protected void done() {
                    try {
                        boolean sent = get();
                        if (sent) {
                            expectedOtp = newToken;                 // ✅ ancien OTP invalidé
                            view.getOtpInput().setText("");         // force le nouveau
                            JOptionPane.showMessageDialog(view, "OTP sent to your email.", "Info", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(view, "OTP sending failed.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(view, "OTP sending error.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            };
            worker.execute();
        });

        // OK -> create user
        view.getBtnOk().addActionListener(e -> {

            String fullName = view.getNameInput().getText().trim();
            String id = view.getInputID().getText().trim();
            String email = view.getEmailInput().getText().trim();
            String otp = view.getOtpInput().getText().trim();
            String pwd = new String(view.getInputPasswd().getPassword());
            String confirm = new String(view.getConfirmPasswdInput().getPassword());

            String role = view.getCourseAdministratorRadio().isSelected()
                    ? view.getCourseAdministratorRadio().getText()
                    : view.getAcademicOfficerRadio().getText();

            // empty
            if (fullName.isEmpty() || id.isEmpty() || email.isEmpty() || otp.isEmpty() || pwd.isEmpty() || confirm.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Please do not leave any fields empty.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // ID rules
            if (!EX_rules.rule_ID(id)) {
                JOptionPane.showMessageDialog(view,
                        "ID format is invalid.\nID: 5–15 characters (letters and numbers).",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (EX_rules.exist_ID(id)) {
                JOptionPane.showMessageDialog(view, "ID already used.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // email rules
            if (!EX_rules.rule_Email(email)) {
                JOptionPane.showMessageDialog(view, "The email format is invalid.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (EX_rules.exist_Email(email)) {
                JOptionPane.showMessageDialog(view, "The email is already used.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // OTP checks
            if (expectedOtp == null) {
                JOptionPane.showMessageDialog(view, "Please request an OTP first (click OTP).", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!EX_OTPController.isOtpValid(expectedOtp, otp)) {
                JOptionPane.showMessageDialog(view, "Invalid OTP code.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // password checks
            if (!EX_rules.comparePasswords(pwd, confirm)) {
                JOptionPane.showMessageDialog(view, "Passwords are not identical.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!EX_rules.rule_Passwd(pwd)) {
                JOptionPane.showMessageDialog(view,
                        "Password must be 8–16 characters and include:\n" +
                                "- 1 uppercase\n- 1 lowercase\n- 1 number\n- 1 special (* ! & @ # $ %)",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // write
            Ex_write.writeToFile(fullName, id, pwd, email, role);
            Ex_write.logTimestamp(id, "ADMIN CREATED USER");
            JOptionPane.showMessageDialog(view, "Account created successfully.", "Info", JOptionPane.INFORMATION_MESSAGE);

            expectedOtp = null;
            view.dispose();
        });

        // Cancel
        view.getBtnCancel().addActionListener(e -> view.dispose());
    }

    private void startOtpCooldown3s() {
        otpSeconds = 30;
        JButton b = view.getToggleOTPButton();
        b.setEnabled(false);
        b.setText("" + otpSeconds);

        if (otpTimer != null && otpTimer.isRunning()) otpTimer.stop();

        otpTimer = new Timer(1000, ev -> {
            otpSeconds--;
            if (otpSeconds <= 0) {
                otpTimer.stop();
                b.setEnabled(true);
                b.setText("OTP");
            } else {
                b.setText("" + otpSeconds);
            }
        });
        otpTimer.start();
    }
}

