package assignement_oopj;

public class Student {
    private final String studentID;
    private final String firstName;
    private final String lastName;
    private final String major;
    private final String year;
    private final String email;

    public Student(String studentID, String firstName, String lastName, 
                  String major, String year, String email) {
        this.studentID = studentID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.major = major;
        this.year = year;
        this.email = email;
    }

    // Getters
    public String getStudentID() { return studentID; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getMajor() { return major; }
    public String getYear() { return year; }
    public String getEmail() { return email; }

    @Override
    public String toString() {
        return studentID + " - " + firstName + " " + lastName;
    }
}