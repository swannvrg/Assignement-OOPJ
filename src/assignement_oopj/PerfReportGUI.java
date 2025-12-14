package assignement_oopj;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PerfReportGUI extends JFrame {

    private JTextField txtCode, txtTitle, txtCredits, txtGrade, txtGradePoint, txtEmail;
    private JComboBox<String> cmbYear;
    private JComboBox<String> cmbSemester;
    private JTextField txtIntake;
    private final String userID;
    private JTable courseTable;
    private DefaultTableModel tableModel;

    private List<CourseResult> courses = new ArrayList<>();

    public PerfReportGUI(String userID) {
        this.userID = userID;
        setTitle("CRS Academic Report System");
        setSize(800, 580);
        setLayout(new BorderLayout(10, 10));

        // ===== TOP INPUT PANEL =====
        JPanel inputPanel = new JPanel(new GridLayout(9, 2, 5, 5));

        inputPanel.add(new JLabel("Course Code:"));
        txtCode = new JTextField();
        inputPanel.add(txtCode);

        inputPanel.add(new JLabel("Course Title:"));
        txtTitle = new JTextField();
        inputPanel.add(txtTitle);

        inputPanel.add(new JLabel("Credit Hours:"));
        txtCredits = new JTextField();
        inputPanel.add(txtCredits);

        inputPanel.add(new JLabel("Grade (A, B+, etc):"));
        txtGrade = new JTextField();
        inputPanel.add(txtGrade);

        inputPanel.add(new JLabel("Grade Point (4.0 scale):"));
        txtGradePoint = new JTextField();
        inputPanel.add(txtGradePoint);

        inputPanel.add(new JLabel("Year:"));
        cmbYear = new JComboBox<>(new String[]{"Year 1", "Year 2", "Year 3"});
        inputPanel.add(cmbYear);

        inputPanel.add(new JLabel("Semester:"));
        cmbSemester = new JComboBox<>(new String[]{
                "Semester 1", "Semester 2", "Semester 3"
        });
        inputPanel.add(cmbSemester);

        inputPanel.add(new JLabel("Intake Code:"));
        txtIntake = new JTextField();
        inputPanel.add(txtIntake);

        inputPanel.add(new JLabel("Recipient Email:"));
        txtEmail = new JTextField();
        inputPanel.add(txtEmail);

        add(inputPanel, BorderLayout.NORTH);

        // ===== TABLE =====
        String[] columns = {
                "Course Code", "Course Title",
                "Credit Hours", "Grade", "Grade Point"
        };

        tableModel = new DefaultTableModel(columns, 0);
        courseTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(courseTable);

        add(scrollPane, BorderLayout.CENTER);

        // ===== BUTTONS =====
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton btnAdd = new JButton("Add Course");
        JButton btnRemove = new JButton("Remove Selected");
        JButton btnPdf = new JButton("Generate PDF");
        JButton btnEmail = new JButton("Send Email");
        JButton btnDashboard = new JButton("Dashboard");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnRemove);
        buttonPanel.add(btnPdf);
        buttonPanel.add(btnEmail);
        buttonPanel.add(btnDashboard);

        add(buttonPanel, BorderLayout.SOUTH);

        // ===== ACTIONS =====
        btnAdd.addActionListener(e -> addCourse());
        btnRemove.addActionListener(e -> removeCourse());
        btnPdf.addActionListener(e -> generatePdf());
        btnEmail.addActionListener(e -> sendEmail());
        btnDashboard.addActionListener(e -> {
            EX_Dashboard.openAfterLoginWindow(userID);
            dispose();
        });
        
        


        

        setDefaultCloseOperation(DISPOSE_ON_CLOSE); 
        setVisible(true);

    
    }

    private void addCourse() {
        try {
            CourseResult course = new CourseResult(
                    txtCode.getText(),
                    txtTitle.getText(),
                    Integer.parseInt(txtCredits.getText()),
                    txtGrade.getText(),
                    Double.parseDouble(txtGradePoint.getText())
            );

            courses.add(course);

            tableModel.addRow(new Object[]{
                    course.getCourseCode(),
                    course.getCourseTitle(),
                    course.getCreditHours(),
                    course.getGrade(),
                    course.getGradePoint()
            });

            txtCode.setText("");
            txtTitle.setText("");
            txtCredits.setText("");
            txtGrade.setText("");
            txtGradePoint.setText("");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid course data.");
        }
    }

    private void removeCourse() {
        int selectedRow = courseTable.getSelectedRow();

        if (selectedRow >= 0) {
            courses.remove(selectedRow);
            tableModel.removeRow(selectedRow);
        } else {
            JOptionPane.showMessageDialog(this, "Select a course to remove.");
        }
    }

    private void generatePdf() {
        if (courses.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No courses added.");
            return;
        }

        String year = cmbYear.getSelectedItem().toString();
        String semester = cmbSemester.getSelectedItem().toString();
        String intake = txtIntake.getText();

        String title = year + " " + semester + " Academic Report";

        String path = PdfReportGenerator.generateAcademicReport(
                title,
                intake,
                courses
        );

        JOptionPane.showMessageDialog(this, "PDF created: " + path);
    }

    private void sendEmail() {
        if (courses.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No courses to send.");
            return;
        }

        try {
            String year = cmbYear.getSelectedItem().toString();
            String semester = cmbSemester.getSelectedItem().toString();
            String intake = txtIntake.getText();

            String title = year + " " + semester + " Academic Report";

            String pdfFile = PdfReportGenerator.generateAcademicReport(
                    title,
                    intake,
                    courses
            );

            EmailSender.sendEmail(
                    txtEmail.getText(),
                    "Academic Performance Report",
                    "Attached is the academic performance report.",
                    pdfFile
            );

            JOptionPane.showMessageDialog(this, "Email sent!");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Email error: " + ex.getMessage());
        }
    }

    
}




