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
import java.io.*;
import java.awt.Font;

public class EX_Dashboard extends JFrame {

    private JButton btnLogout,btnEdit, btnDisable, btnMenu; //btn for menu of settings
    private JPopupMenu popup; //for menu of settings
    private JMenuItem itemEdit, itemDisable, itemLogout; //for menu of settings
    private JLabel welcomeLabel, roleLabel;
    private final String idUser; //id from constructor
    private final String roleUser; //same for the role
    private JButton btnMailLogCheck, EmailNotification; //btn for academic officer view
    private JButton EligibityCheck, CourseRecovery; //btn for course admin view
    
   

   

    // constructor
    public EX_Dashboard(String idUser) {
        this.idUser = idUser;
        String[] data = Ex_write.getUserData(idUser);
        this.roleUser = data [2];

        setTitle("Dashboard");
        setSize(400, 250);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initUI(); //call the component

        setLocationRelativeTo(null); // center the windows
        setVisible(true);
    }
    
    // interface component of the dashboard page
    private void initUI() {

    welcomeLabel = new JLabel("Welcome, " + idUser + " !");
    welcomeLabel.setBounds(20, 20, 300, 30);
    welcomeLabel.setFont(new Font("Arial", Font.BOLD, 14));
    add(welcomeLabel);
    roleLabel = new JLabel(roleUser);
    roleLabel.setBounds(20, 35, 300, 30);
    add(roleLabel);

    // button hide
    btnLogout  = new JButton("Logout");
    btnEdit    = new JButton("Edit");
    btnDisable = new JButton("Disable");

    // burger menu button
    btnMenu = new JButton("☰ Settings");
    btnMenu.setBounds(270, 20, 100, 30);
    add(btnMenu);

    // pop up menu with action
    popup = new JPopupMenu(); 

    itemEdit    = new JMenuItem("Edit profile");
    itemDisable = new JMenuItem("Disable account");
    itemLogout  = new JMenuItem("Logout");

    popup.add(itemEdit);
    popup.add(itemDisable);
    popup.addSeparator();
    popup.add(itemLogout);

    // btn for academic officer
    btnMailLogCheck = new JButton("Mail Log Check");
    btnMailLogCheck.setBounds(100, 90, 200, 30);
    btnMailLogCheck.setVisible(false);
    add(btnMailLogCheck);

    EmailNotification = new JButton("Email Notification");
    EmailNotification.setBounds(100, 130, 200, 30);
    EmailNotification.setVisible(false);
    add(EmailNotification);

    // btn for course admin
    EligibityCheck = new JButton("Eligibility Check");
    EligibityCheck.setBounds(100, 90, 200, 30);
    EligibityCheck.setVisible(false);
    add(EligibityCheck);

    CourseRecovery = new JButton("Course Recovery Plan");
    CourseRecovery.setBounds(100, 130, 200, 30);
    CourseRecovery.setVisible(false);
    add(CourseRecovery);
}
    // method to open the dashboard from the connexion window
    public static void openAfterLoginWindow(String id_user) {
        EX_Dashboard view = new EX_Dashboard(id_user);
        new EX_DashboardController(view);//to interact with the method into the controller file
        
    }

    // getter for the controller
    public JButton getBtnLogout()  { return btnLogout; }
    public JButton getBtnEdit()    { return btnEdit; }
    public JButton getBtnDisable() { return btnDisable; }
    public JButton getBtnMenu()    { return btnMenu; }
    public JPopupMenu getPopup()   { return popup; }
    public JMenuItem getItemEdit()    { return itemEdit; }
    public JMenuItem getItemDisable() { return itemDisable; }
    public JMenuItem getItemLogout()  { return itemLogout; }
    
    public String getIdUser()      { return idUser; }
    public String getUserRole() { return roleUser; }
    
    public JButton getBtnMailLogCheck() { return btnMailLogCheck; }
    public JButton getEmailNotification() { return EmailNotification; }
    public JButton getEligibityCheck() { return EligibityCheck; }
    public JButton getCourseRecovery() { return CourseRecovery; }
 
}
