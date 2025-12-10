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

//method for login
public class EX_login {
    public static boolean exist_Login(String id, String mdp){
        File myObj = new File("login.txt");
        try(Scanner myReader = new Scanner(myObj)){
            //in the loop, while id user and password are the same in one line, the loop work 
            //if same => return true
            //if not => loop stop when no more line
            while(myReader.hasNextLine()){
                String data = myReader.nextLine();
                if(data.contains("ID USER : " + id)&& data.contains("; PASSWORD : " + mdp) ){
                    return true; 
                }
            }
        }catch (FileNotFoundException e){
            System.out.println("An error occured");
            e.printStackTrace();
        }
        //here if not equal id and password, the method return false
        return false;
    }
}
