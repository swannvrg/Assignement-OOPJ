package assignement_oopj;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class RecoveryPlanGUI extends JFrame {
    private ArrayList<Student> students;
    private ArrayList<Course> courses;
    private ArrayList<RecoveryPlan> recoveryPlans;
    
    private JComboBox<String> studentCombo, courseCombo, planCombo;
    private JTextArea milestoneArea, recommendationArea, displayArea, editMilestoneArea, editRecommendationArea;
    private JTextField weekField, taskField, recommendationField;
    private JComboBox<String> gradeCombo;
    
    public RecoveryPlanGUI() {
        // Load from CSV or use sample
        students = FileReaderUtil.readStudentsFromFile("student_information (1).csv");
        courses = FileReaderUtil.readCoursesFromFile("course_information-APU-LP-0650 (2).csv");
        
        // LOAD SAVED PLANS FROM CSV FILE
        recoveryPlans = CSVFileManager.loadFromCSV();
        System.out.println("Loaded " + recoveryPlans.size() + " saved plans from CSV.");
        
        // If no CSV, use sample
        if (students.isEmpty()) {
            students.add(new Student("S001", "Fiona", "Smith", "CS", "Senior", "test@email.com"));
            students.add(new Student("S002", "George", "Wilson", "Math", "Sophomore", "test@email.com"));
        }
        if (courses.isEmpty()) {
            courses.add(new Course("C101", "Algorithms", 4, "Spring", "Dr. Brown", 100));
            courses.add(new Course("C102", "Data Structures", 3, "Fall", "Dr. Lee", 100));
        }
        
        setupGUI();
        
        // Auto-save when closing
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                autoSaveToCSV();
                System.out.println("Auto-saved to CSV on close.");
            }
        });
    }
    
    private void autoSaveToCSV() {
        CSVFileManager.saveToCSV(recoveryPlans);
    }
    
    private void setupGUI() {
        setTitle("Recovery Plan System");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Create tabbed pane
        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Create", createTab());
        tabs.addTab("Manage", manageTab());
        tabs.addTab("View", viewTab());
        
        add(tabs, BorderLayout.CENTER);
    }
    
    private JPanel createTab() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Top - Selection
        JPanel topPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        studentCombo = new JComboBox<>();
        courseCombo = new JComboBox<>();
        
        // Fill combos
        for (Student s : students) studentCombo.addItem(s.toString());
        for (Course c : courses) courseCombo.addItem(c.toString());
        
        topPanel.add(new JLabel("Student:"));
        topPanel.add(studentCombo);
        topPanel.add(new JLabel("Course:"));
        topPanel.add(courseCombo);
        
        // Middle - Milestones
        JPanel middlePanel = new JPanel(new BorderLayout());
        JPanel inputPanel = new JPanel(new GridLayout(1, 4, 5, 5));
        weekField = new JTextField();
        taskField = new JTextField();
        JButton addMilestoneBtn = new JButton("Add Milestone");
        JButton clearMilestonesBtn = new JButton("Clear All");
        
        inputPanel.add(new JLabel("Week:"));
        inputPanel.add(weekField);
        inputPanel.add(new JLabel("Task:"));
        inputPanel.add(taskField);
        inputPanel.add(addMilestoneBtn);
        inputPanel.add(clearMilestonesBtn);
        
        milestoneArea = new JTextArea(5, 50);
        milestoneArea.setEditable(false);
        JScrollPane msScroll = new JScrollPane(milestoneArea);
        
        middlePanel.add(inputPanel, BorderLayout.NORTH);
        middlePanel.add(msScroll, BorderLayout.CENTER);
        
        // Recommendations
        JPanel recPanel = new JPanel(new BorderLayout());
        JPanel recInputPanel = new JPanel(new GridLayout(1, 3, 5, 5));
        recommendationField = new JTextField();
        JButton addRecBtn = new JButton("Add Recommendation");
        JButton clearRecsBtn = new JButton("Clear All");
        
        recInputPanel.add(new JLabel("Recommendation:"));
        recInputPanel.add(recommendationField);
        recInputPanel.add(addRecBtn);
        recInputPanel.add(clearRecsBtn);
        
        recommendationArea = new JTextArea(5, 50);
        recommendationArea.setEditable(false);
        JScrollPane recScroll = new JScrollPane(recommendationArea);
        
        recPanel.add(recInputPanel, BorderLayout.NORTH);
        recPanel.add(recScroll, BorderLayout.CENTER);
        
        // Create Button
        JButton createBtn = new JButton("CREATE RECOVERY PLAN");
        createBtn.setBackground(Color.GREEN);
        createBtn.setFont(new Font("Arial", Font.BOLD, 14));
        
        // Add to panel
        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(middlePanel, BorderLayout.CENTER);
        panel.add(recPanel, BorderLayout.SOUTH);
        panel.add(createBtn, BorderLayout.SOUTH);
        
        // Actions
        addMilestoneBtn.addActionListener(e -> addMilestone());
        clearMilestonesBtn.addActionListener(e -> milestoneArea.setText(""));
        addRecBtn.addActionListener(e -> addRecommendation());
        clearRecsBtn.addActionListener(e -> recommendationArea.setText(""));
        createBtn.addActionListener(e -> createPlan());
        
        return panel;
    }
    
    private JPanel manageTab() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Left - Select plan
        JPanel leftPanel = new JPanel(new BorderLayout());
        planCombo = new JComboBox<>();
        JButton loadBtn = new JButton("Load Plan");
        JButton deleteBtn = new JButton("Delete Plan");
        deleteBtn.setBackground(Color.RED);
        deleteBtn.setForeground(Color.WHITE);
        
        updatePlanCombo(); // Initial update
        
        JPanel selectPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        selectPanel.add(new JLabel("Select Plan:"));
        selectPanel.add(planCombo);
        selectPanel.add(loadBtn);
        selectPanel.add(deleteBtn);
        
        leftPanel.add(selectPanel, BorderLayout.NORTH);
        
        // Right - Manage (with tabs for Edit/Status)
        JTabbedPane manageTabs = new JTabbedPane();
        manageTabs.addTab("Edit Plan", editPlanPanel());
        manageTabs.addTab("Update Status", statusPanel());
        
        // Actions
        loadBtn.addActionListener(e -> loadPlan());
        deleteBtn.addActionListener(e -> deletePlan());
        
        panel.add(leftPanel, BorderLayout.WEST);
        panel.add(manageTabs, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel editPlanPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Milestones edit
        JPanel milestoneEditPanel = new JPanel(new BorderLayout());
        editMilestoneArea = new JTextArea(8, 40);
        editMilestoneArea.setEditable(true);
        JScrollPane msEditScroll = new JScrollPane(editMilestoneArea);
        
        JButton updateMilestonesBtn = new JButton("Update Milestones");
        updateMilestonesBtn.addActionListener(e -> updateMilestones());
        
        milestoneEditPanel.add(new JLabel("Edit Milestones (one per line):"), BorderLayout.NORTH);
        milestoneEditPanel.add(msEditScroll, BorderLayout.CENTER);
        milestoneEditPanel.add(updateMilestonesBtn, BorderLayout.SOUTH);
        
        // Recommendations edit
        JPanel recEditPanel = new JPanel(new BorderLayout());
        editRecommendationArea = new JTextArea(8, 40);
        editRecommendationArea.setEditable(true);
        JScrollPane recEditScroll = new JScrollPane(editRecommendationArea);
        
        JButton updateRecommendationsBtn = new JButton("Update Recommendations");
        updateRecommendationsBtn.addActionListener(e -> updateRecommendations());
        
        recEditPanel.add(new JLabel("Edit Recommendations (one per line):"), BorderLayout.NORTH);
        recEditPanel.add(recEditScroll, BorderLayout.CENTER);
        recEditPanel.add(updateRecommendationsBtn, BorderLayout.SOUTH);
        
        // Combine
        panel.add(milestoneEditPanel, BorderLayout.NORTH);
        panel.add(recEditPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel statusPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        
        // Status
        JPanel statusPanel = new JPanel(new GridLayout(1, 3, 5, 5));
        JComboBox<String> statusCombo = new JComboBox<>(new String[]{"Pending", "In Progress", "Completed"});
        JButton updateStatusBtn = new JButton("Update Status");
        
        statusPanel.add(new JLabel("Status:"));
        statusPanel.add(statusCombo);
        statusPanel.add(updateStatusBtn);
        
        // Grade
        JPanel gradePanel = new JPanel(new GridLayout(1, 3, 5, 5));
        String[] grades = {"Not Graded", "A+", "A", "A-", "B+", "B", "B-", "C+", "C", "C-", "D", "F"};
        gradeCombo = new JComboBox<>(grades);
        JButton updateGradeBtn = new JButton("Update Grade");
        
        gradePanel.add(new JLabel("Grade:"));
        gradePanel.add(gradeCombo);
        gradePanel.add(updateGradeBtn);
        
        // Save button
        JButton saveBtn = new JButton("SAVE PLAN TO FILE");
        saveBtn.setBackground(Color.BLUE);
        saveBtn.setForeground(Color.WHITE);
        saveBtn.setFont(new Font("Arial", Font.BOLD, 14));
        
        // Actions
        updateStatusBtn.addActionListener(e -> updateStatus((String)statusCombo.getSelectedItem()));
        updateGradeBtn.addActionListener(e -> updateGrade());
        saveBtn.addActionListener(e -> savePlan());
        
        panel.add(statusPanel);
        panel.add(gradePanel);
        panel.add(new JLabel()); // spacer
        panel.add(saveBtn);
        
        return panel;
    }
    
    private JPanel viewTab() {
        JPanel panel = new JPanel(new BorderLayout());
        
        displayArea = new JTextArea(20, 70);
        displayArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(displayArea);
        
        JButton refreshBtn = new JButton("REFRESH VIEW");
        refreshBtn.addActionListener(e -> refreshView());
        
        panel.add(new JLabel("All Recovery Plans:"), BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        panel.add(refreshBtn, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private void addMilestone() {
        String week = weekField.getText();
        String task = taskField.getText();
        
        if (week.isEmpty() || task.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter week and task!");
            return;
        }
        
        milestoneArea.append("Week " + week + ": " + task + "\n");
        weekField.setText("");
        taskField.setText("");
    }
    
    private void addRecommendation() {
        String rec = recommendationField.getText();
        if (rec.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter recommendation!");
            return;
        }
        
        recommendationArea.append("• " + rec + "\n");
        recommendationField.setText("");
    }
    
    private void createPlan() {
        String studentInfo = (String) studentCombo.getSelectedItem();
        String courseInfo = (String) courseCombo.getSelectedItem();
        
        if (studentInfo == null || courseInfo == null) {
            JOptionPane.showMessageDialog(this, "Select student and course!");
            return;
        }
        
        // SIMPLE FIX: Just use the full strings
        String studentID = studentInfo;
        String studentName = studentInfo;
        String courseID = courseInfo;
        String courseName = courseInfo;
        
        // If format is "ID - Name", split it
        if (studentInfo.contains(" - ")) {
            studentID = studentInfo.substring(0, studentInfo.indexOf(" - "));
            studentName = studentInfo.substring(studentInfo.indexOf(" - ") + 3);
        }
        
        if (courseInfo.contains(" - ")) {
            courseID = courseInfo.substring(0, courseInfo.indexOf(" - "));
            courseName = courseInfo.substring(courseInfo.indexOf(" - ") + 3);
        }
        
        // Create plan
        RecoveryPlan plan = new RecoveryPlan(studentID, studentName, courseID, courseName);
        
        // Add milestones
        String msText = milestoneArea.getText();
        if (!msText.trim().isEmpty()) {
            String[] msLines = msText.split("\n");
            for (String line : msLines) {
                if (!line.trim().isEmpty()) {
                    plan.addMilestone(line.trim());
                }
            }
        }
        
        // Add recommendations
        String recText = recommendationArea.getText();
        if (!recText.trim().isEmpty()) {
            String[] recLines = recText.split("\n");
            for (String line : recLines) {
                if (!line.trim().isEmpty()) {
                    plan.addRecommendation(line.replace("• ", "").trim());
                }
            }
        }
        
        // Save to list
        recoveryPlans.add(plan);
        
        // AUTO-SAVE TO CSV
        autoSaveToCSV();
        
        // Clear and show message
        milestoneArea.setText("");
        recommendationArea.setText("");
        updatePlanCombo();
        
        JOptionPane.showMessageDialog(this, 
            "✓ Plan created and saved to CSV!\n" +
            "Student: " + studentName + "\n" +
            "Course: " + courseName + "\n" +
            "Total saved plans: " + recoveryPlans.size());
    }
    
    private void updatePlanCombo() {
        planCombo.removeAllItems();
        for (RecoveryPlan plan : recoveryPlans) {
            planCombo.addItem(plan.toString());
        }
    }
    
    private void loadPlan() {
        int index = planCombo.getSelectedIndex();
        if (index >= 0 && index < recoveryPlans.size()) {
            RecoveryPlan plan = recoveryPlans.get(index);
            
            // Load current grade
            gradeCombo.setSelectedItem(plan.getGrade());
            
            // Load milestones for editing
            editMilestoneArea.setText("");
            for (String ms : plan.getMilestones()) {
                editMilestoneArea.append(ms + "\n");
            }
            
            // Load recommendations for editing
            editRecommendationArea.setText("");
            for (String rec : plan.getRecommendations()) {
                editRecommendationArea.append(rec + "\n");
            }
            
            JOptionPane.showMessageDialog(this, "Plan loaded: " + plan.getStudentName());
        } else {
            JOptionPane.showMessageDialog(this, "No plan selected or no plans exist!");
        }
    }
    
    private void deletePlan() {
        int index = planCombo.getSelectedIndex();
        if (index >= 0 && index < recoveryPlans.size()) {
            int confirm = JOptionPane.showConfirmDialog(this, 
                "Delete this recovery plan?\n" + recoveryPlans.get(index).toString(),
                "Confirm Delete", JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                recoveryPlans.remove(index);
                updatePlanCombo();
                
                // AUTO-SAVE TO CSV
                autoSaveToCSV();
                
                JOptionPane.showMessageDialog(this, "Plan deleted and CSV updated!");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Select a plan to delete!");
        }
    }
    
    private void updateMilestones() {
        int index = planCombo.getSelectedIndex();
        if (index >= 0 && index < recoveryPlans.size()) {
            RecoveryPlan plan = recoveryPlans.get(index);
            plan.getMilestones().clear(); // Clear old milestones
            
            String[] newMilestones = editMilestoneArea.getText().split("\n");
            for (String ms : newMilestones) {
                if (!ms.trim().isEmpty()) {
                    plan.addMilestone(ms.trim());
                }
            }
            
            // AUTO-SAVE TO CSV
            autoSaveToCSV();
            
            JOptionPane.showMessageDialog(this, "Milestones updated and CSV saved!");
        } else {
            JOptionPane.showMessageDialog(this, "Select a plan first!");
        }
    }
    
    private void updateRecommendations() {
        int index = planCombo.getSelectedIndex();
        if (index >= 0 && index < recoveryPlans.size()) {
            RecoveryPlan plan = recoveryPlans.get(index);
            plan.getRecommendations().clear(); // Clear old recommendations
            
            String[] newRecs = editRecommendationArea.getText().split("\n");
            for (String rec : newRecs) {
                if (!rec.trim().isEmpty()) {
                    plan.addRecommendation(rec.trim());
                }
            }
            
            // AUTO-SAVE TO CSV
            autoSaveToCSV();
            
            JOptionPane.showMessageDialog(this, "Recommendations updated and CSV saved!");
        } else {
            JOptionPane.showMessageDialog(this, "Select a plan first!");
        }
    }
    
    private void updateStatus(String status) {
        int index = planCombo.getSelectedIndex();
        if (index >= 0 && index < recoveryPlans.size()) {
            recoveryPlans.get(index).setStatus(status);
            updatePlanCombo(); // Refresh combo
            
            // AUTO-SAVE TO CSV
            autoSaveToCSV();
            
            JOptionPane.showMessageDialog(this, "Status updated to: " + status + " (CSV saved)");
        } else {
            JOptionPane.showMessageDialog(this, "Select a plan first!");
        }
    }
    
    private void updateGrade() {
        int index = planCombo.getSelectedIndex();
        if (index >= 0 && index < recoveryPlans.size()) {
            String grade = (String) gradeCombo.getSelectedItem();
            recoveryPlans.get(index).setGrade(grade);
            
            // AUTO-SAVE TO CSV
            autoSaveToCSV();
            
            JOptionPane.showMessageDialog(this, "Grade updated to: " + grade + " (CSV saved)");
        } else {
            JOptionPane.showMessageDialog(this, "Select a plan first!");
        }
    }
    
    private void savePlan() {
        int index = planCombo.getSelectedIndex();
        if (index >= 0 && index < recoveryPlans.size()) {
            // Save individual text file
            recoveryPlans.get(index).saveToTextFile();
            
            // Also save ALL to CSV
            autoSaveToCSV();
            
            JOptionPane.showMessageDialog(this, 
                "✓ Plan saved!\n" +
                "1. Individual text file created\n" +
                "2. ALL plans saved to CSV file\n" +
                "Close and reopen to test persistence!");
        } else {
            JOptionPane.showMessageDialog(this, "Select a plan first!");
        }
    }
    
    private void refreshView() {
        if (recoveryPlans.isEmpty()) {
            displayArea.setText("No recovery plans created yet.");
            return;
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("=== ALL RECOVERY PLANS ===\n\n");
        
        for (int i = 0; i < recoveryPlans.size(); i++) {
            RecoveryPlan plan = recoveryPlans.get(i);
            sb.append("PLAN #").append(i + 1).append("\n");
            sb.append("────────────────────────────────────\n");
            sb.append("Student: ").append(plan.getStudentName()).append("\n");
            sb.append("Course: ").append(plan.getCourseName()).append("\n");
            sb.append("Status: ").append(plan.getStatus()).append("\n");
            sb.append("Grade: ").append(plan.getGrade()).append("\n");
            
            sb.append("\nRecommendations:\n");
            for (String rec : plan.getRecommendations()) {
                sb.append("  • ").append(rec).append("\n");
            }
            
            sb.append("\nMilestones:\n");
            for (String ms : plan.getMilestones()) {
                sb.append("  - ").append(ms).append("\n");
            }
            
            sb.append("\n════════════════════════════════════\n\n");
        }
        
        displayArea.setText(sb.toString());
    }
    
    
}
