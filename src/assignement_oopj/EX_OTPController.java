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
import java.util.function.Consumer;

public class EX_OTPController {

    private final EX_ResetPassword view;
    private Timer resendTimer;
    private int resendSeconds = 30;

    public EX_OTPController(EX_ResetPassword view) {
        this.view = view;
    }

    // Cooldown 30s
    public void startResendCooldown() {
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

    // Validation OTP (avec empty check)
    public boolean validateOtpOrShowError(String userID) {
        String userOTP = view.getOTPInput().getText();
        String sentOTP = view.getSentOTP();

        if (userOTP == null || userOTP.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Please enter the OTP code.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            Ex_write.logTimestamp(userID, "EMPTY OTP - RESET PASSWORD");
            return false;
        }

        if (!isOtpValid(sentOTP, userOTP)) {
            JOptionPane.showMessageDialog(null,
                    "Invalid OTP. Please check the code you received by email.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            Ex_write.logTimestamp(userID, "OTP INVALID - RESET PASSWORD");
            return false;
        }

        return true;
    }

    /**
     * Envoie un nouvel OTP (async) + invalide l’ancien si succès (setExpectedOtp)
     * + vide le champ OTP.
     *
     * onSuccessCallback: optionnel si vous voulez faire quelque chose après succès.
     */
    public void resendOtp(String userID, Consumer<String> onSuccessCallback) {
        String userEmail = view.getUserMail();

        // Désactive immédiatement + cooldown 30s
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
                        // ✅ remplace l'ancien OTP => l'ancien devient invalide
                        view.setExpectedOtp(newToken);
                        view.getOTPInput().setText("");

                        JOptionPane.showMessageDialog(null,
                                "A new OTP has been sent to your email.",
                                "Info", JOptionPane.INFORMATION_MESSAGE);
                        Ex_write.logTimestamp(userID, "OTP RESENT - RESET PASSWORD");

                        if (onSuccessCallback != null) {
                            onSuccessCallback.accept(newToken);
                        }

                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Failed to resend OTP. Please try again later.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                        Ex_write.logTimestamp(userID, "OTP RESEND FAILED - RESET PASSWORD");
                    }

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null,
                            "Unexpected error while resending OTP.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    Ex_write.logTimestamp(userID, "OTP RESEND ERROR - RESET PASSWORD");
                    ex.printStackTrace();
                }
            }
        };

        worker.execute();
    }
     public static boolean isOtpValid(String sentOtp, String userOtp) {
        if (sentOtp == null || userOtp == null) return false;

        String exp = sentOtp.trim();
        String usr = userOtp.trim();

        if (exp.isEmpty() || usr.isEmpty()) return false;

        return exp.equals(usr);
    }
}

