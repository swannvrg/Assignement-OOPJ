package assignement_oopj;

public class Course {
    private final String courseID;
    private final String courseName;
    private final int credits;
    private final String semester;
    private final String instructor;
    private final int capacity;

    public Course(String courseID, String courseName, int credits, 
                  String semester, String instructor, int capacity) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.credits = credits;
        this.semester = semester;
        this.instructor = instructor;
        this.capacity = capacity;
    }

    // Getters
    public String getCourseID() { return courseID; }
    public String getCourseName() { return courseName; }
    public int getCredits() { return credits; }
    public String getSemester() { return semester; }
    public String getInstructor() { return instructor; }
    public int getCapacity() { return capacity; }

    @Override
    public String toString() {
        return courseID + " - " + courseName;
    }
}