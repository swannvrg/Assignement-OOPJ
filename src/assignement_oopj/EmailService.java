/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignement_oopj;

public class EmailService {
   private String userEmail = "marcelariandy@gmail.com"; // Your email
    private String userPassword = System.getenv("GMAIL_APP_PASSWORD"); // Get app password from environment variable
    private EmailHandler emailHandler; // EmailHandler instance for actual email sending

    public EmailService() {
        // Initialize EmailHandler with the email and password
        emailHandler = new EmailHandler(userEmail, userPassword);
    }

    // Method to send account confirmation email
    public boolean sendAccountConfirmation(String to) {
        String subject = "Account Confirmation";
        String body = "Your account has been successfully created.";
        return emailHandler.sendEmail(to, subject, body, "Confirmation", null);
    }

    // Method to send password recovery email
    public boolean sendPasswordRecovery(String to, String token) {
        String subject = "Password Recovery";
        String body = "Use this code to reset your password: " + token;
        return emailHandler.sendEmail(to, subject, body, "Recovery", null);
    }

    // Method to send eligibility report email with attachment
    /*public boolean sendEligibilityReport(Student student, String pdfPath) {
        if (student == null || student.getEmail() == null || student.getEmail().trim().isEmpty()) {
            return false;
        }

        String to = student.getEmail().trim();
        String subject = "Eligibility Report - " + student.getFullName();
        String body =
                "Dear " + student.getFullName() + ",\n\n" +
                "Please find attached your course eligibility / CGPA report.\n\n" +
                "Best regards,\n" +
                "Enrollment & Examination Office";

        return emailHandler.sendEmail(to, subject, body, "EligibilityReport", pdfPath);
    }

    // Method to send recovery plan report email with attachment
    public boolean sendRecoveryPlanReport(Student student, String pdfPath) {
        if (student == null || student.getEmail() == null || student.getEmail().trim().isEmpty()) {
            return false;
        }

        String to = student.getEmail().trim();
        String subject = "Recovery Plan Progress Report - " + student.getFullName();
        String body =
                "Dear " + student.getFullName() + ",\n\n" +
                "Please find attached your Recovery Plan progress report.\n\n" +
                "Best regards,\n" +
                "Academic Office";

        return emailHandler.sendEmail(to, subject, body, "RecoveryPlanReport", pdfPath);
    }*/

    // Method to send password reset token email
    public boolean sendPasswordResetToken(String to, String token) {
        String body =
                "You requested a password reset.\n\n" +
                "Your reset token is: " + token + "\n\n" +
                "If you did not request this, please ignore this email.";

        return emailHandler.sendEmail(to, "Password Reset Token", body, "PasswordResetToken", null);
    }
}
