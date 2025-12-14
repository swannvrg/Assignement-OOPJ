/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignement_oopj;

/**
 *
 * @author swann
 */

import java.util.List;
import javax.swing.*;

public class EX_DashboardController {

    private final EX_Dashboard view;

    public EX_DashboardController(EX_Dashboard view) {
        this.view = view;
        initListeners(); //call the listeners
        applyRolePermissions(); //call the  role permissio for dashboard role buttons
    }

    // listeners component of the dashboard controller 
    private void initListeners() {
        
        //change the language of dialog popup to be sure there are in english cause mine are in french
        UIManager.put("OptionPane.yesButtonText", "Yes");
        UIManager.put("OptionPane.noButtonText", "No");
        UIManager.put("OptionPane.okButtonText", "OK");
        UIManager.put("OptionPane.cancelButtonText", "Cancel");
        
        // show menu pop up
        view.getBtnMenu().addActionListener(e -> {
            view.getPopup().show(view.getBtnMenu(), 0, view.getBtnMenu().getHeight());
        });

        // open "Edit"
        view.getItemEdit().addActionListener(e -> view.getBtnEdit().doClick());

        //open "Disable account"
        view.getItemDisable().addActionListener(e -> view.getBtnDisable().doClick());

        //open "Logout"
        view.getItemLogout().addActionListener(e -> view.getBtnLogout().doClick());

        // method for logout
        view.getBtnLogout().addActionListener(e -> {
            String id_user = view.getIdUser();

            // close the window
            view.dispose();

            // open the connexion window
            EX_Connexion.main(null);

            // binary log
            String log_action_user = "LOG OUT";
            Ex_write.logTimestamp(id_user, log_action_user);
        });

        // method for edit the account information
        view.getBtnEdit().addActionListener(e -> {
            String id_user = view.getIdUser();
             // close the window
            view.dispose();
            // open edit popup => view
            EX_EditProfile editView = new EX_EditProfile(id_user);

            // call the controller
            new EX_EditProfileController(editView);

            // write on log file
            String log_action_user = "EDIT_OPEN";
            Ex_write.logTimestamp(id_user, log_action_user);
        });


        // method for disable = delete the account
        view.getBtnDisable().addActionListener(e -> {
        String id_user = view.getIdUser();

            int response = JOptionPane.showConfirmDialog(view,
                    "Are you sure you want to delete your account?",
                    "Confirm deletion",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);

            if (response == JOptionPane.YES_OPTION) {
                //while cancel or the right id isn't enter, the loop work again
                while (true) {

                    String id_user_confirm = JOptionPane.showInputDialog(
                            view,
                            "Enter your ID to confirm deletion :",
                            id_user  //default value (user id)
                    );

                    if (id_user_confirm == null) {
                        // Cancel
                        JOptionPane.showMessageDialog(view, "Deletion canceled", "Annulation", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }

                    id_user_confirm = id_user_confirm.trim();

                    // if iduser == empty => error
                    if (id_user_confirm.isEmpty()) {
                        JOptionPane.showMessageDialog(view,
                                "ID cannot be empty.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                        continue;
                    }

                    //if iduser != iduser =>error
                    if (!id_user_confirm.equals(id_user)) {
                        JOptionPane.showMessageDialog(view,
                                "The ID entered does not match your account.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                        continue;
                    }

                    // if iduser ==iduser =>error
                    boolean ok = Ex_write.deleteUser(id_user_confirm);

                    if (ok) {
                        JOptionPane.showMessageDialog(view,
                                "Deletion successful",
                                "Info",
                                JOptionPane.INFORMATION_MESSAGE);
                        view.dispose();          // close dashboard
                        EX_Connexion.main(null); // open login
                        return;
                    } else {
                        JOptionPane.showMessageDialog(view,
                                "User not found. No account was deleted.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

            } else {
                JOptionPane.showMessageDialog(view,
                        "Deletion canceled",
                        "Annulation",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        // listener to open Mail Log Check 
        view.getBtnMailLogCheck().addActionListener(e -> {
            // Fetch the email logs (from your storage or file)
            List<EmailLog> emailLogs = EmailService.getEmailLogs();

            // Create the panel to display emails
            ViewEmailPanel emailPanel = new ViewEmailPanel(emailLogs);
            view.dispose();

            // Create a frame for the email view
            JFrame emailFrame = new JFrame("All Sent Emails");
            emailFrame.setSize(600, 400);
            emailFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            emailFrame.add(emailPanel);
            emailFrame.setLocationRelativeTo(null);  // Center the frame
            emailFrame.setVisible(true);
        });
        
         // listener to open Academic Performance Reporting
        view.getAcademicPerformanceReport().addActionListener(e -> {
            String id_user = view.getIdUser();
            
             // Ouvre la fenêtre CRS Report & Email System
              new PerfReportGUI(id_user);
              view.dispose();
            
            //binary log
            String log_action_user = "OPEN ACADEMIC PERFORMANCE REPORTING";
            Ex_write.logTimestamp(id_user, log_action_user);
        });
        
         // listener to open Egilibity Check
        view.getEligibityCheck().addActionListener(e -> {
            String id_user = view.getIdUser();

            new MainMenu(id_user).setVisible(true);
            view.dispose();
            
            //binary log
            String log_action_user = "OPEN ELIGIBITY CHECK";
            Ex_write.logTimestamp(id_user, log_action_user);
        });
        
         // listener to open Course Recovery
        view.getCourseRecovery().addActionListener(e -> {
            String id_user = view.getIdUser();

            //lauch recovery plan
            SwingUtilities.invokeLater(() -> {
                new RecoveryPlanGUI(id_user);
            });
            view.dispose();

            //binary log
            String log_action_user = "OPEN COURSE RECOVERY";
            Ex_write.logTimestamp(id_user, log_action_user);
        });
        
         // listener to open User Management
        view.getUserManagement().addActionListener(e -> {
            String id_user = view.getIdUser();
            
            EX_UserManagement um = new EX_UserManagement(id_user);
            new EX_UserManagementController(um);
            view.dispose();

            //binary log
            String log_action_user = "OPEN USER MANAGEMENT";
            Ex_write.logTimestamp(id_user, log_action_user);
        });

    }
    //method to hide and show the right button that depend of the user role
    private void applyRolePermissions() {

        String role = view.getUserRole();

        // Hide all buttons first
        view.getBtnMailLogCheck().setVisible(false);
        view.getAcademicPerformanceReport().setVisible(false);
        view.getEligibityCheck().setVisible(false);
        view.getCourseRecovery().setVisible(false);
        view.getUserManagement().setVisible(false);
        

        // Show buttons depending on role
        if (role.equals("Academic Officer")) {
            
            view.getBtnMailLogCheck().setVisible(true);
            view.getAcademicPerformanceReport().setVisible(true);
            view.getEligibityCheck().setVisible(true);
            view.getCourseRecovery().setVisible(true);
            view.getUserManagement().setVisible(true);
        }

        if (role.equals("Course Administrator")) {
            view.getBtnMailLogCheck().setVisible(true);
            view.getAcademicPerformanceReport().setVisible(true);
            view.getEligibityCheck().setVisible(true);
            view.getUserManagement().setVisible(true);
        }
    }
    
   
}

