package com.crs;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private JTextField txtName, txtCourse, txtScore, txtEmail;

    public MainFrame() {
        setTitle("CRS Report & Email System");
        setSize(400, 350);
        setLayout(new GridLayout(6, 2));

        add(new JLabel("Student Name:"));
        txtName = new JTextField();
        add(txtName);

        add(new JLabel("Course Name:"));
        txtCourse = new JTextField();
        add(txtCourse);

        add(new JLabel("Score:"));
        txtScore = new JTextField();
        add(txtScore);

        add(new JLabel("Recipient Email:"));
        txtEmail = new JTextField();
        add(txtEmail);

        JButton btnPdf = new JButton("Generate PDF");
        JButton btnEmail = new JButton("Send Email");

        add(btnPdf);
        add(btnEmail);

        btnPdf.addActionListener(e -> generatePdf());
        btnEmail.addActionListener(e -> sendEmail());

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void generatePdf() {
        try {
            CourseResult result = new CourseResult(
                txtName.getText(),
                txtCourse.getText(),
                Double.parseDouble(txtScore.getText())
            );

            String path = PdfReportGenerator.generateReport(result);

            JOptionPane.showMessageDialog(this, "PDF created: " + path);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void sendEmail() {
        try {
            CourseResult result = new CourseResult(
                txtName.getText(),
                txtCourse.getText(),
                Double.parseDouble(txtScore.getText())
            );

            String pdfFile = PdfReportGenerator.generateReport(result);

            EmailSender.sendEmail(
                txtEmail.getText(),
                "Course Result Report",
                "Here is the student's report.",
                pdfFile
            );

            JOptionPane.showMessageDialog(this, "Email sent!");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Email Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new MainFrame();
    }
}

