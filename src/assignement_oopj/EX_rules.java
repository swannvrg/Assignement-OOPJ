/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignement_oopj;

/**
 *
 * @author swann
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class EX_rules {
    
    //method for define how many characters and wich one for ID in sign up
    public static boolean rule_ID(String id){
        return id.matches("[a-zA-Z0-9]{5,15}"); //lowercase, uppercase and number beetwen min 5 and max 15 characters
    }
     //method for define how many characters and wich one for PASSWORD in sign up
    public static boolean rule_Passwd(String passwd){
        return passwd.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[*!&@#$%]).{8,16}$"); //lowercase, uppercase, digit, special char, between min 8 and  max 16 characters
    }
    
    //method to know if ID already use or not in sign up
    public static boolean exist_ID(String id) {
        File myObj = new File("login.txt");
        try (Scanner myReader = new Scanner(myObj)) {
            //here the loop read line by line, if ID USER = same id, return true
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine().trim();  
                data = data.replaceAll("\\s+", " ");  // replace multiple consecutive spaces with a single space

                
                if (data.contains("ID USER : " + id)) {
                   
                    return true;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //return false if loop stop 
        return false;
    }
    
    //method to compare both password in sign up
    public static boolean comparePasswords(String passwd, String confirmPasswd) {
        //here we just compar if passwd 1 equals to passwd 2
        if (passwd.equals(confirmPasswd)) {
            return true;  
        }
        return false;  
    }
    
    //method to check if the email format is valid or not for EMAIL in sign up 
    public static boolean rule_Email(String email) {
    // Regex pattern to validate an email address
    String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}(?:\\.[a-zA-Z]{2,6})*$";
    
    // verify whether the email matches the regular expression
    return email.matches(emailRegex);
    }
    
    //method to know if EMAIL already use or not in sign up
    public static boolean exist_Email(String email) {
        File myObj = new File("login.txt");
        try (Scanner myReader = new Scanner(myObj)) {
            //loop read line by line, if EMAIL = same email, return true 
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine().trim();  
                data = data.replaceAll("\\s+", " ");  // replace multiple consecutive spaces with a single space

                
                if (data.contains("EMAIL : " + email)) {
                   
                    return true;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //else return false when loop stop
        return false;
    }
    
   
    
}
