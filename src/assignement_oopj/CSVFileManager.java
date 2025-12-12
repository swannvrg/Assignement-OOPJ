package assignement_oopj;

import java.io.*;
import java.util.ArrayList;

public class CSVFileManager {
    private static final String PLANS_CSV = "recovery_plans.csv";
    
    // Save ALL plans to CSV file
    public static void saveToCSV(ArrayList<RecoveryPlan> plans) {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(PLANS_CSV));
            
            // Write header
            writer.println("StudentID,StudentName,CourseID,CourseName,Status,Grade,Milestones,Recommendations");
            
            // Write each plan
            for (RecoveryPlan plan : plans) {
                // Join milestones with semicolons
                String milestonesStr = String.join(";", plan.getMilestones());
                // Join recommendations with semicolons
                String recsStr = String.join(";", plan.getRecommendations());
                
                writer.println(
                    plan.getStudentID() + "," +
                    escapeCommas(plan.getStudentName()) + "," +
                    plan.getCourseID() + "," +
                    escapeCommas(plan.getCourseName()) + "," +
                    plan.getStatus() + "," +
                    plan.getGrade() + "," +
                    escapeCommas(milestonesStr) + "," +
                    escapeCommas(recsStr)
                );
            }
            
            writer.close();
            System.out.println("Saved " + plans.size() + " plans to CSV: " + PLANS_CSV);
        } catch (IOException e) {
            System.out.println("Error saving to CSV: " + e.getMessage());
        }
    }
    
    // Load ALL plans from CSV file
    public static ArrayList<RecoveryPlan> loadFromCSV() {
        ArrayList<RecoveryPlan> plans = new ArrayList<>();
        File file = new File(PLANS_CSV);
        
        if (!file.exists()) {
            System.out.println("No CSV file found. Starting fresh.");
            return plans;
        }
        
        try {
            BufferedReader reader = new BufferedReader(new FileReader(PLANS_CSV));
            String line;
            boolean firstLine = true;
            
            while ((line = reader.readLine()) != null) {
                if (firstLine) {
                    firstLine = false; // Skip header
                    continue;
                }
                
                String[] data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                
                if (data.length >= 8) {
                    // Remove quotes if present
                    String studentID = data[0].trim();
                    String studentName = removeQuotes(data[1].trim());
                    String courseID = data[2].trim();
                    String courseName = removeQuotes(data[3].trim());
                    String status = data[4].trim();
                    String grade = data[5].trim();
                    String milestonesStr = removeQuotes(data[6].trim());
                    String recsStr = removeQuotes(data[7].trim());
                    
                    // Create plan
                    RecoveryPlan plan = new RecoveryPlan(studentID, studentName, courseID, courseName);
                    plan.setStatus(status);
                    plan.setGrade(grade);
                    
                    // Split and add milestones
                    if (!milestonesStr.isEmpty()) {
                        String[] milestones = milestonesStr.split(";");
                        for (String ms : milestones) {
                            if (!ms.trim().isEmpty()) {
                                plan.addMilestone(ms.trim());
                            }
                        }
                    }
                    
                    // Split and add recommendations
                    if (!recsStr.isEmpty()) {
                        String[] recommendations = recsStr.split(";");
                        for (String rec : recommendations) {
                            if (!rec.trim().isEmpty()) {
                                plan.addRecommendation(rec.trim());
                            }
                        }
                    }
                    
                    plans.add(plan);
                }
            }
            
            reader.close();
            System.out.println("Loaded " + plans.size() + " plans from CSV");
        } catch (IOException e) {
            System.out.println("Error loading CSV: " + e.getMessage());
        }
        
        return plans;
    }
    
    // Helper: Escape commas in text
    private static String escapeCommas(String text) {
        if (text.contains(",") || text.contains("\"")) {
            return "\"" + text.replace("\"", "\"\"") + "\"";
        }
        return text;
    }
    
    // Helper: Remove quotes
    private static String removeQuotes(String text) {
        if (text.startsWith("\"") && text.endsWith("\"")) {
            return text.substring(1, text.length() - 1).replace("\"\"", "\"");
        }
        return text;
    }
}