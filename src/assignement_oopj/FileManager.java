package assignement_oopj;

import java.io.*;
import java.util.ArrayList;

public class FileManager {
    private static final String PLANS_FILE = "all_recovery_plans.dat";
    
    // Save all plans to file
    public static void saveAllPlans(ArrayList<RecoveryPlan> plans) {
        try {
            FileOutputStream fileOut = new FileOutputStream(PLANS_FILE);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(plans);
            out.close();
            fileOut.close();
            System.out.println("All plans saved to " + PLANS_FILE);
        } catch (IOException e) {
            System.out.println("Error saving plans: " + e.getMessage());
        }
    }
    
    // Load all plans from file
    @SuppressWarnings("unchecked")
    public static ArrayList<RecoveryPlan> loadAllPlans() {
        ArrayList<RecoveryPlan> plans = new ArrayList<>();
        File file = new File(PLANS_FILE);
        
        if (!file.exists()) {
            System.out.println("No saved plans found. Starting fresh.");
            return plans;
        }
        
        try {
            FileInputStream fileIn = new FileInputStream(PLANS_FILE);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            plans = (ArrayList<RecoveryPlan>) in.readObject();
            in.close();
            fileIn.close();
            System.out.println("Loaded " + plans.size() + " plans from file.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading plans: " + e.getMessage());
        }
        
        return plans;
    }
}