/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignement_oopj;

/**
 *
 * @author swann
 */
public class EX_ResetPasswordController {
    private final EX_ResetPassword view ;
    
    public EX_ResetPasswordController(EX_ResetPassword view){
        this.view = view;
        initListeners(); //call listeners
    }
    
    // listeners component of the reset passwd controller 
    private void initListeners(){
         // Cancel close window
        view.getBtnCancel().addActionListener(e ->{
            String userID = view.getUserID();
            System.out.println("btn cancel");
             // write on log file
            String log_action_user = "CANCEL RESET PASSWORD";
            Ex_write.logTimestamp(userID, log_action_user);
            //open edit window
            EX_Dashboard.openAfterLoginWindow(userID);
            // close the window
            view.dispose();
        });
        
         // Save  validations and update file
        view.getBtnSave().addActionListener(e -> {
            String userID = view.getUserID();
            System.out.println("btn save");
             // write on log file
            String log_action_user = "UPDATE RESET PASSWORD";
            Ex_write.logTimestamp(userID, log_action_user);    
            //open edit window
            EX_Dashboard.openAfterLoginWindow(userID);
            // close the window
            view.dispose();
        });
    }
}
