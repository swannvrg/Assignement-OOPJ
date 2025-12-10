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
import java.awt.event.*;

public class EX_ConnexionController {

    private final EX_Connexion view;

     public EX_ConnexionController(EX_Connexion view) {
        this.view = view;
        initListeners();//call the listeners
    }
     // listeners component of the connexion controller 
    private void initListeners() {

        // Show/Hide password and confirm password
        //password
        view.getTogglePasswordButton().addActionListener(new ActionListener() {
            boolean isPasswordVisible = false;

            @Override
            public void actionPerformed(ActionEvent e) {
                JPasswordField pwd = view.getInputPasswd();
                if (isPasswordVisible) {
                    pwd.setEchoChar('*');
                    view.getTogglePasswordButton().setText("Show");
                } else {
                    pwd.setEchoChar((char) 0);
                    view.getTogglePasswordButton().setText("Hide");
                }
                isPasswordVisible = !isPasswordVisible;
            }
        });
         //confirm password
        view.getToggleConfirmPasswordButton().addActionListener(new ActionListener() {
            boolean isPasswordVisible = false;

            @Override
            public void actionPerformed(ActionEvent e) {
                JPasswordField pwd = view.getConfirmPasswdInput();
                if (isPasswordVisible) {
                    pwd.setEchoChar('*');
                    view.getToggleConfirmPasswordButton().setText("Show");
                } else {
                    pwd.setEchoChar((char) 0);
                    view.getToggleConfirmPasswordButton().setText("Hide");
                }
                isPasswordVisible = !isPasswordVisible;
            }
        });

        //  Radio LOGIN : hide signup fields
        view.getLoginRadio().addActionListener(e -> {
            if (view.getLoginRadio().isSelected()) {
                // hide signup fields
                view.getNameLabel().setVisible(false);
                view.getNameInput().setVisible(false);

                view.getEmailLabel().setVisible(false);
                view.getEmailInput().setVisible(false);

                view.getConfirmPasswdLabel().setVisible(false);
                view.getConfirmPasswdInput().setVisible(false);
                view.getToggleConfirmPasswordButton().setVisible(false);

                view.getCourseAdministratorRadio().setVisible(false);
                view.getAcademicOfficerRadio().setVisible(false);
                view.getRoleLabel().setVisible(false);

                // set the right position for the login fields
                view.getIdLabel().setBounds(50, 110, 100, 30);
                view.getInputID().setBounds(150, 110, 200, 30);
                view.getPasswdLabel().setBounds(50, 150, 100, 30);
                view.getInputPasswd().setBounds(150, 150, 200, 30);
                view.getTogglePasswordButton().setBounds(360, 150, 70, 30);
                view.getOkButton().setBounds(50, 200, 150, 40);
                view.getCancelButton().setBounds(250, 200, 150, 40);
            }
        });

        // Radio SIGN UP : show signup fields
        view.getSignRadio().addActionListener(e -> {
            if (view.getSignRadio().isSelected()) {
                // show signup fields
                view.getNameLabel().setVisible(true);
                view.getNameInput().setVisible(true);

                view.getEmailLabel().setVisible(true);
                view.getEmailInput().setVisible(true);

                view.getConfirmPasswdLabel().setVisible(true);
                view.getConfirmPasswdInput().setVisible(true);
                view.getToggleConfirmPasswordButton().setVisible(true);

                view.getCourseAdministratorRadio().setVisible(true);
                view.getAcademicOfficerRadio().setVisible(true);
                view.getRoleLabel().setVisible(true);

                 // set the right position for the sign up fields
                view.getIdLabel().setBounds(50, 110, 100, 30);
                view.getInputID().setBounds(150, 110, 200, 30);
                view.getPasswdLabel().setBounds(50, 190, 100, 30);
                view.getInputPasswd().setBounds(150, 190, 200, 30);
                view.getTogglePasswordButton().setBounds(360, 190, 70, 30);
                view.getOkButton().setBounds(50, 380, 150, 40);
                view.getCancelButton().setBounds(250, 380, 150, 40);
            }
        });

        //  Buton OK with the logic for EX_write, EX_login and  EX_rule
        view.getOkButton().addActionListener(e -> {
            String id_user            = view.getInputID().getText();
            String passwd_user        = new String(view.getInputPasswd().getPassword());
            String fullName_user      = view.getNameInput().getText();
            String email_user         = view.getEmailInput().getText();
            String confirmPasswd_user = new String(view.getConfirmPasswdInput().getPassword());

            String role_user = "";
            if (view.getCourseAdministratorRadio().isSelected()) {
                role_user = view.getCourseAdministratorRadio().getText();
            } else if (view.getAcademicOfficerRadio().isSelected()) {
                role_user = view.getAcademicOfficerRadio().getText();
            }
            //binary log
            String log_action_user = view.getSignRadio().isSelected() ? "SIGN UP" : "LOGIN";

            // SIGN UP logic
            if (view.getSignRadio().isSelected()) {
                //all the rules before write the sign up
                 // check if any field is empty
                 if (fullName_user.isEmpty() || id_user.isEmpty() || email_user.isEmpty() || passwd_user.isEmpty() || confirmPasswd_user.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please do not leave any fields empty", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
                //rule for if id already exist
                else if (EX_rules.exist_ID(id_user)) {
                    JOptionPane.showMessageDialog(null, "ID already used", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
                //rule for if email format is valid
                else if (!EX_rules.rule_Email(email_user)) { 
                    JOptionPane.showMessageDialog(null, "The email format is invalid", "Erreur", JOptionPane.ERROR_MESSAGE);
                } 
                //rule for  if email already exist
                else if (EX_rules.exist_Email(email_user)) {
                    JOptionPane.showMessageDialog(null, "The email is already used", "Erreur", JOptionPane.ERROR_MESSAGE);
                } 
                //rule for if both password avec identical
                else if (!EX_rules.comparePasswords(passwd_user, confirmPasswd_user)) {
                    JOptionPane.showMessageDialog(null, "Passwords are not identical", "Erreur", JOptionPane.ERROR_MESSAGE);
                    
                } 
                //rule for if id or password don't respect format rules
                //error if rules id or password == false
                else if(EX_rules.rule_ID(id_user) ==false || EX_rules.rule_Passwd(passwd_user) ==false){
                    JOptionPane.showMessageDialog(null,
                            "Incorrect username or password, please follow the creation rules. \n "
                            + "Rules : \n "
                            + "ID : must be 5 – 15 characters long, letters and numbers \n"
                            + "Password : must be 8 – 16 characters long, a combination of Uppercase/Lowercase/Numbers and special symbols (*, !, &, @, #, $, %).",
                            "Erreur", JOptionPane.ERROR_MESSAGE); 
                } 
                
                //SIGN UP IF ALL VALID
                //signup is write in this part if all is good
                else if (EX_rules.rule_ID(id_user) && EX_rules.rule_Passwd(passwd_user )) {
                    Ex_write.writeToFile(fullName_user, id_user, passwd_user, email_user, role_user);
                    JOptionPane.showMessageDialog(null, "Account created successfully", "Bienvenue", JOptionPane.INFORMATION_MESSAGE);

                    view.getNameInput().setText("");
                    view.getInputID().setText("");
                    view.getEmailInput().setText("");
                    view.getInputPasswd().setText("");
                    view.getConfirmPasswdInput().setText("");

                    Ex_write.logTimestamp(id_user, log_action_user);
                    
                }

            } else { 
                // LOGIN logic
                // check if the fields are empty first
                if (id_user.isEmpty() || passwd_user.isEmpty()) {
                    JOptionPane.showMessageDialog(null,
                            "Please do not leave any fields empty",
                            "Erreur", JOptionPane.ERROR_MESSAGE);
                }
                // check if login exists
                else if (EX_login.exist_Login(id_user, passwd_user)) {
                    EX_Dashboard.openAfterLoginWindow(id_user);
                    view.dispose();
                    JOptionPane.showMessageDialog(null, "Connexion successful", "Bienvenue", JOptionPane.INFORMATION_MESSAGE);

                    view.getInputID().setText("");
                    view.getInputPasswd().setText("");

                    Ex_write.logTimestamp(id_user, log_action_user);
                } 
                else { // if the login fails (invalid ID or password)
                    JOptionPane.showMessageDialog(null,
                            "Connexion failed \n ID or PASSWORD are invalid",
                            "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }

        });

        // cancel button
        view.getCancelButton().addActionListener(e -> {
            view.dispose(); // close the windows
        });
    }
}

