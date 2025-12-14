/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignement_oopj;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author swann
 */


public class EX_SelectUserDialog extends JDialog {

    private JList<String> list;
    private DefaultListModel<String> model;
    private String selectedUserId = null;

    public EX_SelectUserDialog(JFrame parent, String titleText) {
        super(parent, titleText, true);
        setSize(420, 380);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        initUI(titleText);

        setLocationRelativeTo(parent);
    }

    private void initUI(String titleText) {
        JLabel title = new JLabel(titleText);
        title.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(title, BorderLayout.NORTH);

        model = new DefaultListModel<>();
        for (String id : loadAllUserIds()) {
            String[] data = Ex_write.getUserData(id);
            if (data != null) {
                // Affiche "ID - FULLNAME"
                model.addElement(id + " - " + data[0]);
            } else {
                model.addElement(id);
            }
        }

        list = new JList<>(model);
        add(new JScrollPane(list), BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnOk = new JButton("OK");
        JButton btnCancel = new JButton("Cancel");
        bottom.add(btnCancel);
        bottom.add(btnOk);
        add(bottom, BorderLayout.SOUTH);

        btnCancel.addActionListener(e -> dispose());

        btnOk.addActionListener(e -> {
            String chosen = list.getSelectedValue();
            if (chosen == null) {
                JOptionPane.showMessageDialog(this, "Please select a user.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // récupère l'ID avant " - "
            if (chosen.contains(" - ")) selectedUserId = chosen.substring(0, chosen.indexOf(" - ")).trim();
            else selectedUserId = chosen.trim();

            dispose();
        });
    }

    public String getSelectedUserId() {
        return selectedUserId;
    }

    private List<String> loadAllUserIds() {
        List<String> ids = new ArrayList<>();
        File file = new File("login.txt");

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("ID USER :")) {
                    String id = extractIdFromLine(line);
                    if (id != null && !id.isEmpty()) ids.add(id);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ids;
    }

    private String extractIdFromLine(String line) {
        // Exemple: "ID USER : xxx; PASSWORD : ...; FULL NAME : ..."
        String[] parts = line.split(";");
        for (String p : parts) {
            p = p.trim();
            if (p.startsWith("ID USER :")) {
                return p.substring("ID USER :".length()).trim();
            }
        }
        return null;
    }
}

