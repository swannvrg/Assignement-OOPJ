/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment_oopj;

import java.util.Date;

public class EmailLog {
    private String to;         // Recipient email address
    private String subject;    // Email subject
    private String type;       // Type of the email (e.g., Confirmation, Recovery)
    private Date date;         // Timestamp of when the email was sent
    private String status;     // Status of the email (Sent or Failed)

    // Constructor to initialize the email log
    public EmailLog(String to, String subject, String type, Date date, String status) {
        this.to = to;
        this.subject = subject;
        this.type = type;
        this.date = date;
        this.status = status;
    }

    // Getters and Setters
    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Optional: Override toString() for better log formatting when needed
    @Override
    public String toString() {
        return "EmailLog [to=" + to + ", subject=" + subject + ", type=" + type + ", date=" + date + ", status=" + status + "]";
    }
}
