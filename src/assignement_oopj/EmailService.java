/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignement_oopj;


import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import jakarta.mail.*;
import jakarta.mail.internet.*;

public class EmailService {
    private static final String userEmail = "l9gic2k@gmail.com"; // Your Gmail address
    private static final String userPassword = "sgff ebxu ieip fcbd"; // Your generated App Password
    private static final String LOG_FILE_PATH = "email_logs.txt";  // Log file path
    private final ArrayList<EmailLog> sentEmails = new ArrayList<>();  // List to store sent emails

    public EmailService() {
        loadEmailLogs();  // Load email logs on initialization
    }
    
    public static String generateOTP() {
       
        int otp = (int)(Math.random() * 999999);  
        return String.format("%06d", otp);  
}

    // Configures the SMTP server for email sending
    private Session configureServer() {
        System.setProperty("mail.debug", "true"); // Enable detailed logging for JavaMail
        
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2"); // Ensures secure connection with TLS 1.2

        // Create and return the Session object
        return Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userEmail, userPassword); // Use App Password here
            }
        });
    }

    // Log sent email details and save them to the log file
    private void logSentEmail(String to, String subject, String type, Date date, String status) {
        EmailLog log = new EmailLog(to, subject, type, date, status);
        sentEmails.add(log);
        
        // Debugging output
        System.out.println("Logging email: " + to + ", " + status);  // This line will print to console

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE_PATH, true))) {
            writer.write(to + "," + subject + "," + type + "," + date.getTime() + "," + status);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load all sent emails from the file when the program starts
    private void loadEmailLogs() {
        sentEmails.clear();
        File file = new File(LOG_FILE_PATH);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 5);
                if (parts.length == 5) {
                    String to = parts[0];
                    String subject = parts[1];
                    String type = parts[2];
                    Date date = new Date(Long.parseLong(parts[3]));
                    String status = parts[4];
                    sentEmails.add(new EmailLog(to, subject, type, date, status));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Send a generic email with optional attachment (PDF)
  public boolean sendEmail(String to, String subject, String body, String type, String pdfPath) {
    try {
        // Debugging output
        /*System.out.println("Sending email to: " + to);
        System.out.println("Subject: " + subject);
        System.out.println("Body: " + body);*/

        // Create the Session with the SMTP server configuration
        Session session = configureServer();
        Message message = new MimeMessage(session);
        
        // Set the email details
        message.setFrom(new InternetAddress(userEmail));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(subject);
        message.setText(body);  // Set the body of the email (no multipart or attachments for now)

        // Send the email
        Transport.send(message);
        System.out.println("Email sent successfully!");

        // Log the email after it has been sent successfully
        logSentEmail(to, subject, type, new Date(), "Sent");
        
        return true;
    } catch (Exception e) {
        e.printStackTrace();  // Print the error for debugging
        // Log the failure after the exception is caught
        logSentEmail(to, subject, type, new Date(), "Failed");
        return false;
    }
}

// Method to send password reset token email
public boolean sendPasswordResetToken(String to, String token) {
    String body = "You requested a password reset.\n\n" +
                  "Your reset token is: " + token + "\n\n" +
                  "If you did not request this, please ignore this email.";

    // Call the sendEmail method to send the password reset email
    return sendEmail(to, "Password Reset Token", body, "PasswordResetToken", null);
    }
}