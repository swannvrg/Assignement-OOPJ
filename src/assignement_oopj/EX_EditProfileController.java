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

public class EX_EditProfileController {

    private final EX_EditProfile view;
    private String originalEmail;  // pour savoir si l'email a changé

    public EX_EditProfileController(EX_EditProfile view) {
        this.view = view;
        loadUserData(); //call the data from the user line
        initListeners(); //call listeners
    }
    //method to load all the data from the user line from the database
    private void loadUserData() {
        String originalId = view.getOriginalId();
        String[] data = Ex_write.getUserData(originalId);

        if (data == null) {
            JOptionPane.showMessageDialog(view,
                    "User not found",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            EX_Dashboard.openAfterLoginWindow(originalId);
            view.dispose();
            return;
        }

        String fullName = data[0];
        String email    = data[1];
        String role     = data[2];

        originalEmail = email;

        // Current ID = originalId 
        view.setUserData(originalId, fullName, email, role);
    }
    
    
    // listeners component of the edit controller 
    private void initListeners() {

        // Cancel close window
        view.getBtnCancel().addActionListener(e ->{
            String oldId    = view.getOriginalId();
            EX_Dashboard.openAfterLoginWindow(oldId);
            view.dispose();
             Ex_write.logTimestamp(oldId, "EDIT CANCEL");
        });

        // Save  validations and update file
        view.getBtnSave().addActionListener(e -> {
            String oldId    = view.getOriginalId();     // ID d'origine
            String newId    = view.getIdField().getText().trim();
            String newName  = view.getNameField().getText().trim();
            String newEmail = view.getEmailField().getText().trim();
            String newRole  = view.getCourseAdministratorRadio().isSelected()
                    ? view.getCourseAdministratorRadio().getText()
                    : view.getAcademicOfficerRadio().getText();

            // empty files = error
            if (newId.isEmpty() || newName.isEmpty() || newEmail.isEmpty()) {
                JOptionPane.showMessageDialog(view,
                        "ID, name and email cannot be empty.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // id rule = error
            if (!EX_rules.rule_ID(newId)) {
                JOptionPane.showMessageDialog(view,
                        "ID format is invalid.\nID : must be 5 – 15 characters long, letters and numbers.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // if id already use = error
            if (!newId.equals(oldId) && EX_rules.exist_ID(newId)) {
                JOptionPane.showMessageDialog(view,
                        "This ID is already used.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // invalid email format = error
            if (!EX_rules.rule_Email(newEmail)) {
                JOptionPane.showMessageDialog(view,
                        "The email format is invalid.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // if email already use = error
            if (!newEmail.equals(originalEmail) && EX_rules.exist_Email(newEmail)) {
                JOptionPane.showMessageDialog(view,
                        "The email is already used.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            
            //if all is good = update
            if (Ex_write.updateUserData(oldId, newId, newName, newEmail, newRole)) {
                JOptionPane.showMessageDialog(view,
                        "Account updated successfully.",
                        "Info",
                        JOptionPane.INFORMATION_MESSAGE);

                // log EDIT with new id
                Ex_write.logTimestamp(newId, "EDIT UPDATE");
                //open dashboard with the update of user id
                EX_Dashboard.openAfterLoginWindow(newId);
                view.dispose();
            } else {
                JOptionPane.showMessageDialog(view,
                        "Update failed.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
        view.getBtnResetPasswd().addActionListener(e->{
             String id_user = view.getOriginalId();
            // close the window
            view.dispose();
            // open edit popup
           EX_ResetPassword editView = new EX_ResetPassword(id_user);

            // call the controller
            new EX_ResetPasswordController(editView);
            
            // log EDIT with new id
             Ex_write.logTimestamp(id_user, "OPE RESET PASSWORD");
        });
    }
}
