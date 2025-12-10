/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment_oopj;

import assignment_oopj.EmailLog;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailHandler {
    private String userEmail;
    private String userPassword;
    private static final String LOG_FILE_PATH = "email_logs.txt"; // Log file path
    private ArrayList<EmailLog> sentEmails = new ArrayList<>(); // List to store sent emails

    // Constructor to initialize userEmail and userPassword
    public EmailHandler(String userEmail, String userPassword) {
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        loadEmailLogs(); // Load logs on initialization
    }

    // Configures the SMTP server for email sending
    private Session configureServer() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");

        return Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userEmail, userPassword);
            }
        });
    }

    // Log sent email details and save them to the log file
    private void logSentEmail(String to, String subject, String type, Date date, String status) {
        EmailLog log = new EmailLog(to, subject, type, date, status);
        sentEmails.add(log);

        // Save the log entry to the file
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
            Session session = configureServer();
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(userEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);

            Multipart multipart = new MimeMultipart();
            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText(body);
            multipart.addBodyPart(textPart);

            // If a PDF file path is provided, attach the file
            if (pdfPath != null) {
                MimeBodyPart attach = new MimeBodyPart();
                attach.attachFile(pdfPath);
                multipart.addBodyPart(attach);
            }

            message.setContent(multipart);
            Transport.send(message);

            // Log the email after it has been sent successfully
            logSentEmail(to, subject, type, new Date(), "Sent");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            // Log the failure
            logSentEmail(to, subject, type, new Date(), "Failed");
            return false;
        }
    }
}
