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
        UIManager.put("OptionPane.okButtonText", "Validate");
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
            // open edit popup
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
            String id_user = view.getIdUser();
            
            // close the window
            //view.dispose(); //uncomment when its work to close the dashboard popup
            
            System.out.println("btn mail log fonctionne");
            
            //binary log
            String log_action_user = "OPEN MAIL LOG CHECK";
            Ex_write.logTimestamp(id_user, log_action_user);
        });
        
         // listener to open EMail Notification
        view.getEmailNotification().addActionListener(e -> {
            String id_user = view.getIdUser();
            // close the window //when its work to close the dashboard popup
            //view.dispose();
            System.out.println("btn mail notif fonctionne");
            
            //binary log
            String log_action_user = "OPEN EMAIL NOTIFICATION";
            Ex_write.logTimestamp(id_user, log_action_user);
        });
        
         // listener to open Egilibity Check
        view.getEligibityCheck().addActionListener(e -> {
            String id_user = view.getIdUser();
            // close the window //when its work to close the dashboard popup
            //view.dispose();
            System.out.println("btn eligibity fonctionne");
            
            //binary log
            String log_action_user = "OPEN ELIGIBITY CHECK";
            Ex_write.logTimestamp(id_user, log_action_user);
        });
        
         // listener to open Course Recovery
        view.getCourseRecovery().addActionListener(e -> {
            String id_user = view.getIdUser();
            // close the window //when its work to close the dashboard popup
            //view.dispose();
            System.out.println("btn Course Recovery fonctionne");
            
            //binary log
            String log_action_user = "OPEN COURSE RECOVERY";
            Ex_write.logTimestamp(id_user, log_action_user);
        });

    }
    //method to hide and show the right button that depend of the user role
    private void applyRolePermissions() {

        String role = view.getUserRole();

        // Hide all buttons first
        view.getBtnMailLogCheck().setVisible(false);
        view.getEmailNotification().setVisible(false);
        view.getEligibityCheck().setVisible(false);
        view.getCourseRecovery().setVisible(false);

        // Show buttons depending on role
        if (role.equals("Academic Officer")) {
            view.getBtnMailLogCheck().setVisible(true);
            view.getEmailNotification().setVisible(true);
        }

        if (role.equals("Course Administrator")) {
            view.getEligibityCheck().setVisible(true);
            view.getCourseRecovery().setVisible(true);
        }
    }
    
   
}

