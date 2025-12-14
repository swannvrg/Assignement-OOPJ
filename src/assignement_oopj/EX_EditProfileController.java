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
    private String expectedEmailOtp = null;
    private Timer otpTimer;
    private int otpSeconds = 0;


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

             // invalid email format = error
           if (!newEmail.equals(originalEmail)) {

                String otpUser = view.getOtpField().getText().trim();

                if (otpUser.isEmpty()) {
                    JOptionPane.showMessageDialog(view,
                            "Please enter the OTP code sent to your new email.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (expectedEmailOtp == null) {
                    JOptionPane.showMessageDialog(view,
                            "Please request an OTP first (click OTP).",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!EX_OTPController.isOtpValid(expectedEmailOtp, otpUser)) {
                    JOptionPane.showMessageDialog(view,
                            "Invalid OTP code.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
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
                expectedEmailOtp = null;
            } else {
                JOptionPane.showMessageDialog(view,
                        "Update failed.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
       view.getBtnResetPasswd().addActionListener(e -> {
           
            String id_user = view.getOriginalId();  // Récupérer l'ID utilisateur

            // Récupérer l'email de l'utilisateur depuis la base de données
            String userEmail = Ex_write.getUserData(id_user)[1];  // Récupère l'email via l'ID utilisateur

            // Générer un token (OTP)
            String token = EmailService.generateOTP();  // Génère un OTP unique pour le réinitialisation du mot de passe

            // Créer une instance de EmailService pour envoyer l'email
            EmailService emailService = new EmailService();

            // Appeler la méthode pour envoyer l'email avec le token
            boolean emailSent = emailService.sendPasswordResetToken(userEmail, token);

            // Vérifier si l'email a bien été envoyé
            if (emailSent) {
                System.out.println("email send");
                    // Fermer la fenêtre actuelle
                view.dispose();

                // Ouvrir la vue de réinitialisation de mot de passe
                EX_ResetPassword editView = new EX_ResetPassword(id_user, token);

                // Appeler le contrôleur pour la vue de réinitialisation
                new EX_ResetPasswordController(editView);
                // Log de l'action (réinitialisation du mot de passe)
            Ex_write.logTimestamp(id_user, "OTP SENT FOR RESET PASSWORD");
            } else {
                    JOptionPane.showMessageDialog(null, "There was an issue sending the OTP. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                    Ex_write.logTimestamp(id_user, "OTP FAILED TO SENT FOR RESET PASSWORD");
            }

            

            
        });
       
       view.getBtnEmailOtp().addActionListener(e -> {
            String newEmail = view.getEmailField().getText().trim();

            if (newEmail.isEmpty()) {
                JOptionPane.showMessageDialog(view,
                        "Please enter your email first.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!EX_rules.rule_Email(newEmail)) {
                JOptionPane.showMessageDialog(view,
                        "The email format is invalid.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Si l’email n’a pas changé, pas besoin d’OTP
            if (newEmail.equals(originalEmail)) {
                JOptionPane.showMessageDialog(view,
                        "This is your current email. No verification needed.",
                        "Info", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            // Si email déjà utilisé
            if (EX_rules.exist_Email(newEmail)) {
                JOptionPane.showMessageDialog(view,
                        "The email is already used.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            startOtpCooldown();

            String token = EmailService.generateOTP(); // ou EmailService.generateOTP()
            EmailService emailService = new EmailService();

            SwingWorker<Boolean, Void> worker = new SwingWorker<>() {
                @Override
                protected Boolean doInBackground() {
                    return emailService.sendEmailVerificationToken(newEmail, token);
                }

                @Override
                protected void done() {
                    try {
                        boolean sent = get();
                        if (sent) {
                            expectedEmailOtp = token;          // ✅ invalide l'ancien automatiquement
                            view.getOtpField().setText("");    // ✅ force la saisie du nouveau
                            JOptionPane.showMessageDialog(view,
                                    "Verification OTP sent to your new email.",
                                    "Info", JOptionPane.INFORMATION_MESSAGE);
                            Ex_write.logTimestamp(view.getOriginalId(), "EMAIL OTP SENT - EDIT");
                        } else {
                            JOptionPane.showMessageDialog(view,
                                    "There was an issue sending the OTP. Please try again.",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                            Ex_write.logTimestamp(view.getOriginalId(), "EMAIL OTP FAILED - EDIT");
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(view,
                                "Unexpected error while sending OTP.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                        ex.printStackTrace();
                    }
                }
            };

            worker.execute();
        });

    }
    private void startOtpCooldown() {
        otpSeconds = 30;
        JButton b = view.getBtnEmailOtp();
        b.setEnabled(false);
        b.setText("OTP (" + otpSeconds + ")");

        if (otpTimer != null && otpTimer.isRunning()) otpTimer.stop();

        otpTimer = new Timer(1000, ev -> {
            otpSeconds--;
            if (otpSeconds <= 0) {
                otpTimer.stop();
                b.setEnabled(true);
                b.setText("OTP");
            } else {
                b.setText("" + otpSeconds );
            }
        });
        otpTimer.start();
    }

    
}
