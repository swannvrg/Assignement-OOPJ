/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignement_oopj;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class ViewEmailPanel extends JPanel {
    private JButton btnRefresh, btnClose;
    private JTable emailTable;
    
    public ViewEmailPanel(List<EmailLog> emailLogs) {
        setLayout(new BorderLayout());
        
        // Create the table to show emails
        String[] columnNames = {"Recipient", "Subject", "Type", "Date", "Status"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        // Populate the table with email data
        for (EmailLog emailLog : emailLogs) {
            model.addRow(new Object[]{
                emailLog.getTo(),
                emailLog.getSubject(),
                emailLog.getType(),
                emailLog.getDate(),
                emailLog.getStatus()
            });
        }
        
        emailTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(emailTable);
        add(scrollPane, BorderLayout.CENTER);

        // Buttons (Refresh & Close)
        JPanel buttonPanel = new JPanel();
        btnRefresh = new JButton("Refresh");
        btnClose = new JButton("Close");
        buttonPanel.add(btnRefresh);
        buttonPanel.add(btnClose);
        
        add(buttonPanel, BorderLayout.SOUTH);
        
        // Action listener for Close Button
        btnClose.addActionListener(e -> {
            Window window = SwingUtilities.windowForComponent(this);
            if (window != null) {
                window.dispose(); // Close the email view window
            }
        });
        
        // Action listener for Refresh Button
        btnRefresh.addActionListener(e -> {
            // Refresh logic to fetch new emails and update the table
            refreshEmailTable();
        });
    }
    
    // Method to refresh the email table with updated email logs
    private void refreshEmailTable() {
        // Fetch the latest email logs (You can modify this to fetch the data from your log files)
        List<EmailLog> newEmailLogs = loadEmailLogs();
        
        // Update the table with new email data
        DefaultTableModel model = (DefaultTableModel) emailTable.getModel();
        model.setRowCount(0);  // Clear existing data

        for (EmailLog emailLog : newEmailLogs) {
            model.addRow(new Object[]{
                emailLog.getTo(),
                emailLog.getSubject(),
                emailLog.getType(),
                emailLog.getDate(),
                emailLog.getStatus()
            });
        }
    }

    // A placeholder method to simulate loading email logs from a file or database
    private List<EmailLog> loadEmailLogs() {
        // You should load the emails from your log file or database
        return EmailService.getEmailLogs(); // Implement this method in EmailService to return stored email logs
    }
}