package assignement_oopj;

import java.io.*;
import java.util.ArrayList;

public class RecoveryPlan {
    private String studentID;
    private String studentName;
    private String courseID;
    private String courseName;
    private ArrayList<String> milestones;
    private ArrayList<String> recommendations;
    private String status;
    private String grade;
    
    public RecoveryPlan(String studentID, String studentName, String courseID, String courseName) {
        this.studentID = studentID;
        this.studentName = studentName;
        this.courseID = courseID;
        this.courseName = courseName;
        this.milestones = new ArrayList<>();
        this.recommendations = new ArrayList<>();
        this.status = "Pending";
        this.grade = "Not Graded";
    }
    
    // Simple getters
    public String getStudentID() { return studentID; }
    public String getStudentName() { return studentName; }
    public String getCourseID() { return courseID; }
    public String getCourseName() { return courseName; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }
    public ArrayList<String> getMilestones() { return milestones; }
    public ArrayList<String> getRecommendations() { return recommendations; }
    
    // Simple methods
    public void addMilestone(String milestone) {
        milestones.add(milestone);
    }
    
    public void addRecommendation(String recommendation) {
        recommendations.add(recommendation);
    }
    
    public void saveToFile() {
        try {
            String filename = studentID + "_" + courseID + "_Recovery.txt";
            PrintWriter writer = new PrintWriter(new FileWriter(filename));
            
            writer.println("=== RECOVERY PLAN ===");
            writer.println("Student: " + studentName + " (" + studentID + ")");
            writer.println("Course: " + courseName + " (" + courseID + ")");
            writer.println("Status: " + status);
            writer.println("Grade: " + grade);
            
            writer.println("\n=== RECOMMENDATIONS ===");
            for (String rec : recommendations) {
                writer.println("- " + rec);
            }
            
            writer.println("\n=== MILESTONES ===");
            for (String ms : milestones) {
                writer.println("- " + ms);
            }
            
            writer.close();
            System.out.println("Saved to: " + filename);
        } catch (IOException e) {
            System.out.println("Error saving: " + e.getMessage());
        }
    }
    
   
    
    @Override
    public String toString() {
        return studentID + " - " + studentName + " | " + courseID + " | Status: " + status;
    }
    
     // save individual plan
    public void saveToTextFile() {
        try {
            String filename = studentID + "_" + courseID + "_Recovery.txt";
            PrintWriter writer = new PrintWriter(new FileWriter(filename));
            
            writer.println("=== RECOVERY PLAN ===");
            writer.println("Student: " + studentName + " (" + studentID + ")");
            writer.println("Course: " + courseName + " (" + courseID + ")");
            writer.println("Status: " + status);
            writer.println("Grade: " + grade);
            
            writer.println("\n=== RECOMMENDATIONS ===");
            for (String rec : recommendations) {
                writer.println("- " + rec);
            }
            
            writer.println("\n=== MILESTONES ===");
            for (String ms : milestones) {
                writer.println("- " + ms);
            }
            
            writer.close();
            System.out.println("Text file saved: " + filename);
        } catch (IOException e) {
            System.out.println("Error saving text file: " + e.getMessage());
        }
    }
}
    
