/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignement_oopj;
import javax.swing.*;
/**
 *
 * @author swann
 */


public class EX_UserManagementController {

    private final EX_UserManagement view;

    public EX_UserManagementController(EX_UserManagement view) {
        this.view = view;
        initListeners();
    }

    private void initListeners() {

        // Back -> dashboard
        view.getBtnBack().addActionListener(e -> {
            EX_Dashboard.openAfterLoginWindow(view.getAdminId());
            view.dispose();
        });

        // Add user -> popup signup-like
        view.getBtnAddUser().addActionListener(e -> {
            EX_AddUserDialog dlg = new EX_AddUserDialog(view);
            new EX_AddUserDialogController(dlg);
            dlg.setVisible(true);
        });

        // Edit user -> select user -> open EditProfile
        view.getBtnEditUser().addActionListener(e -> {
            EX_SelectUserDialog selector = new EX_SelectUserDialog(view, "Select a user to edit");
            selector.setVisible(true);

            String selectedId = selector.getSelectedUserId();
            if (selectedId == null) return;

            EX_EditProfile edit = new EX_EditProfile(selectedId);
            new EX_EditProfileController(edit);
        });

        // Disable user -> select user -> confirm ID -> delete
        view.getBtnDisableUser().addActionListener(e -> {
            EX_SelectUserDialog selector = new EX_SelectUserDialog(view, "Select a user to disable");
            selector.setVisible(true);

            String selectedId = selector.getSelectedUserId();
            if (selectedId == null) return;

            int response = JOptionPane.showConfirmDialog(view,
                    "Are you sure you want to delete this account?\nUser: " + selectedId,
                    "Confirm deletion",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);

            if (response != JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(view, "Deletion canceled", "Info", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            while (true) {
                String confirm = JOptionPane.showInputDialog(
                        view,
                        "Enter the user ID to confirm deletion:",
                        selectedId
                );

                if (confirm == null) {
                    JOptionPane.showMessageDialog(view, "Deletion canceled", "Info", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                confirm = confirm.trim();
                if (confirm.isEmpty()) {
                    JOptionPane.showMessageDialog(view, "ID cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                    continue;
                }
                if (!confirm.equals(selectedId)) {
                    JOptionPane.showMessageDialog(view, "The ID entered does not match.", "Error", JOptionPane.ERROR_MESSAGE);
                    continue;
                }

                boolean ok = Ex_write.deleteUser(confirm);
                if (ok) {
                    JOptionPane.showMessageDialog(view, "Deletion successful", "Info", JOptionPane.INFORMATION_MESSAGE);
                    Ex_write.logTimestamp(view.getAdminId(), "ADMIN DELETE USER: " + confirm);
                } else {
                    JOptionPane.showMessageDialog(view, "User not found. No account was deleted.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                return;
            }
        });
    }
}

