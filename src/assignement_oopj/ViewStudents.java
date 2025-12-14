package assignement_oopj;


import javax.swing.JFrame;
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.util.Scanner;

public class ViewStudents extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ViewStudents.class.getName());

    
    private MainMenu mainMenu;
    public ViewStudents(MainMenu menu) {
        initComponents();
        this.mainMenu = menu;
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 3, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 0, 0));
        jLabel1.setText("ViewStudentslist");

        jButton1.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 0, 255));
        jButton1.setText("ViewEligible");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        jButton2.setForeground(new java.awt.Color(0, 0, 255));
        jButton2.setText("ViewIneligible");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        jButton3.setForeground(new java.awt.Color(0, 0, 255));
        jButton3.setText("ViewFailed");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        jButton4.setForeground(new java.awt.Color(0, 0, 255));
        jButton4.setText("ViewPassed");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("Back");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton3)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton4)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton2)
                            .addComponent(jButton1))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(117, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(102, 102, 102))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButton5)
                        .addGap(229, 229, 229))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(72, 72, 72)
                .addComponent(jButton1)
                .addGap(31, 31, 31)
                .addComponent(jButton2)
                .addGap(38, 38, 38)
                .addComponent(jButton4)
                .addGap(50, 50, 50)
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 73, Short.MAX_VALUE)
                .addComponent(jButton5)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
        File f = new File("EligibleStudents.txt");
        

        Scanner r = new Scanner(f);
        StringBuilder sb = new StringBuilder();

        while (r.hasNextLine()) {
            String line = r.nextLine().trim();
            if (line.isEmpty()){
                continue;
            }

            String[] data = line.split(",");
            

            String name = data[0];
            String age = data[1];
            String gpa = data[2];
            String tp = data[3];

            sb.append("Name: ").append(name).append(", Age: ").append(age).append(", GPA: ").append(gpa).append(", TP: ").append(tp).append("\n");
        }
        r.close();

        JOptionPane.showMessageDialog(this, sb.toString(), "Eligible Students", JOptionPane.INFORMATION_MESSAGE);

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error reading file: " + e.getMessage());
        return;
    }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
        File l = new File("IneligibleStudents.txt");
        

        Scanner n = new Scanner(l);
        StringBuilder sb2 = new StringBuilder();

        while (n.hasNextLine()) {
            String line = n.nextLine().trim();
            if (line.isEmpty()){ 
                continue; 
            }

            String[] data = line.split(",");
           

            String name = data[0];
            String age = data[1];
            String gpa = data[2];
            String tp = data[3];

            sb2.append("Name: ").append(name).append(", Age: ").append(age).append(", GPA: ").append(gpa).append(", TP: ").append(tp).append("\n");
        }
        n.close();

        
        JOptionPane.showMessageDialog(this, sb2.toString(), "InEligible Students", JOptionPane.INFORMATION_MESSAGE);

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error reading file: " + e.getMessage());
        return;
    }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        this.dispose();
        mainMenu.setVisible(true);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        try {
        File b = new File("ProgressStudents.txt");
       

        Scanner g = new Scanner(b);
        StringBuilder sb3 = new StringBuilder();

        while (g.hasNextLine()) {
            String line = g.nextLine().trim();
            if (line.isEmpty()){
                continue;
            } 

            String[] data = line.split(",");
            

            String name = data[0];
            String MATHNum = data[1];
            String EngNum = data[2];
            String SciNum = data[3];
            String HisNum = data[4];
            String tp = data[5];
            String result = data[6];
            String gpa = data[7];

            sb3.append("Name: ").append(name).append(", Math mark: ").append(MATHNum).append(", English mark: ").append(EngNum).append(", Science mark: ").append(SciNum).append(", History mark: ").append(HisNum).append(", TP: ").append(tp).append(", Result: ").append(result).append(",CGPA: ").append(gpa).append("\n");
        }
        g.close();

        

        JOptionPane.showMessageDialog(this, sb3.toString(), "Progress Students", JOptionPane.INFORMATION_MESSAGE);

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error reading file: " + e.getMessage());
        return;
    }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        try {
        File t = new File("FailedStudents.txt");
        

        Scanner k = new Scanner(t);
        StringBuilder sb4 = new StringBuilder();

        while (k.hasNextLine()) {
            String line = k.nextLine().trim();
            if (line.isEmpty()){
                continue; 
            }
            String[] data = line.split(",");
            

            String name = data[0];
            String MATHNum = data[1];
            String EngNum = data[2];
            String SciNum = data[3];
            String HisNum = data[4];
            String tp = data[5];
            String result = data[6];
            String gpa = data[7];

            sb4.append("Name: ").append(name).append(", Math mark: ").append(MATHNum).append(", English mark: ").append(EngNum).append(", Science mark: ").append(SciNum).append(", History mark: ").append(HisNum).append(", TP: ").append(tp).append(", Result: ").append(result).append(",CGPA: ").append(gpa).append("\n");
        }
        k.close();

       

        JOptionPane.showMessageDialog(this, sb4.toString(), "failed Students", JOptionPane.INFORMATION_MESSAGE);

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error reading file: " + e.getMessage());
        return;
    }
    }//GEN-LAST:event_jButton3ActionPerformed

    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
