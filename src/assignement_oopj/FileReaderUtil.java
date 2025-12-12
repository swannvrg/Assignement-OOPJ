package assignement_oopj;

import java.io.*;
import java.util.*;

public class FileReaderUtil {
    
    public static ArrayList<Student> readStudentsFromFile(String filename) {
        ArrayList<Student> students = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            boolean firstLine = true;
            
            while ((line = reader.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue; // Skip header
                }
                
                String[] data = line.split(",");
                if (data.length >= 6) {
                    Student student = new Student(
                        data[0].trim(), // StudentID
                        data[1].trim(), // FirstName
                        data[2].trim(), // LastName
                        data[3].trim(), // Major
                        data[4].trim(), // Year
                        data[5].trim()  // Email
                    );
                    students.add(student);
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error reading student file: " + e.getMessage());
        }
        return students;
    }
    
    public static ArrayList<Course> readCoursesFromFile(String filename) {
        ArrayList<Course> courses = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            boolean firstLine = true;
            
            while ((line = reader.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue; // Skip header
                }
                
                String[] data = line.split(",");
                if (data.length >= 6) {
                    Course course = new Course(
                        data[0].trim(), // CourseID
                        data[1].trim(), // CourseName
                        Integer.parseInt(data[2].trim()), // Credits
                        data[3].trim(), // Semester
                        data[4].trim(), // Instructor
                        Integer.parseInt(data[5].trim()) // Capacity
                    );
                    courses.add(course);
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error reading course file: " + e.getMessage());
        }
        return courses;
    }
}