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
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;


public class Ex_write {
    //this methode write into the login.txt file all the info about sign up
    public static void writeToFile(String fullName_user, String id_user, String passwd_user, String email_user, String role_user ) {
        try(FileWriter myWriter = new FileWriter("login.txt", true)){
            //write the line in the login file
            myWriter.write("ID USER : " + id_user + "; PASSWORD : " + passwd_user+"; FULL NAME : "+ fullName_user + "; EMAIL : "+ email_user + "; ROLE : " + role_user + "\n" );
            System.out.println("Sucess write");
        }catch (IOException e) {
               System.out.println("An error occurred");
               e.printStackTrace();
        }
     }
    // this method write the log in binary when people log in 
    public static void logTimestamp(String log_user, String action) {
        //creat the binary log 
        long timestamp = System.currentTimeMillis();
        String binaryTimestamp = Long.toBinaryString(timestamp);
        try (FileWriter myWriter = new FileWriter("log.txt", true)) {
            //write the binary in the log file
            myWriter.write(log_user + " : " + action + " ; timestamp : "  + binaryTimestamp + "\n");
            System.out.println(log_user+" : " + action + " ; timestamp : " + binaryTimestamp);
        } catch (IOException e) {
            System.out.println("Une erreur est survenue");
            e.printStackTrace();
        }
    }
    
    //this method can delete an user line into the file
    public static boolean deleteUser(String id_user) {
        File file = new File("login.txt");
        StringBuilder content = new StringBuilder();
        //read the file
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            boolean userFound = false;
            // if id_user same as in one line in "ID USER", user = delete
            while ((line = reader.readLine()) != null) {
                if (line.contains("ID USER : " + id_user)) {
                    userFound = true;
                    continue; // skip this line = delete user
                }
                content.append(line).append("\n");
            }
            //if not, return false
            if (!userFound) {
                return false;
            }

            // file rewriting
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(content.toString());
            }

            // binary log
            Ex_write.logTimestamp(id_user, "ACCOUNT DISABLE");

            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    //with those both methods, an user can edit his data
    //first one
    // Returns FULL NAME, EMAIL, ROLE for a given ID
    public static String[] getUserData(String id_user) {
        File file = new File("login.txt");
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            
            //read the login fil
            while ((line = reader.readLine()) != null) {  
                if (line.contains("ID USER : " + id_user)) {  // check if the line contains the ID_USER = id_user

                    String fullName = "";  // initialize variables for full name, email, and role
                    String email = "";
                    String role = "";

                    String[] parts = line.split(";");  // split the line 
                    for (String p : parts) {  // iterate over each part of the split line
                        p = p.trim();  // trim spaces around each part
                        if (p.startsWith("FULL NAME :")) {  // check if part starts with "FULL NAME :"
                            fullName = p.substring("FULL NAME :".length()).trim();  // extract full name
                        } else if (p.startsWith("EMAIL :")) {  // check if part starts with "EMAIL :"
                            email = p.substring("EMAIL :".length()).trim();  // extract email
                        } else if (p.startsWith("ROLE :")) {  // check if part starts with "ROLE :"
                            role = p.substring("ROLE :".length()).trim();  // extract role
                        }
                    }

                    return new String[]{ fullName, email, role };  // return the extracted information
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null; // user not found
    }

    //Second one 
   public static boolean updateUserData(String oldId,
                                        String newId,
                                        String newFullName,
                                        String newEmail,
                                        String newRole) {

       File inputFile = new File("login.txt");
       File tempFile  = new File("login_temp.txt");

       boolean updated = false;

       try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

           String line;
           //read the line one by one from the login.txt
           while ((line = reader.readLine()) != null) {
               //check if the line contains the old ID
               if (line.contains("ID USER : " + oldId)) {

                    String oldPassword = "";  // initialize variable for old password
                    String[] parts = line.split(";");  // Split the line
                    for (String p : parts) {  // iterate over each part of the split line
                        p = p.trim();  // trim spaces around each part
                        if (p.startsWith("PASSWORD :")) {  // check if part starts with "PASSWORD :"
                            oldPassword = p.substring("PASSWORD :".length()).trim();  // extract the old password
                        }
                    }

                   // rewrite the line with the NEW ID and updated data
                   String newLine =
                           "ID USER : " + newId +
                           "; PASSWORD : " + oldPassword +
                           "; FULL NAME : " + newFullName +
                           "; EMAIL : " + newEmail +
                           "; ROLE : " + newRole;

                   writer.write(newLine);
                   writer.newLine();
                   updated = true;

               } else {
                   writer.write(line);
                   writer.newLine();
               }
           }

       } catch (IOException e) {
           e.printStackTrace();
           return false;
       }
       //if updated work
       if (updated) {
           if (!inputFile.delete()) return false;
           if (!tempFile.renameTo(inputFile)) return false;
       } else {
           tempFile.delete();
       }

       return updated;
   }

    
}