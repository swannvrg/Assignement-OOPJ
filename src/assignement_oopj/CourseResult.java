package assignement_oopj;

public class CourseResult {

    private String courseCode;
    private String courseTitle;
    private int creditHours;
    private String grade;
    private double gradePoint;

    public CourseResult(String courseCode, String courseTitle,
                        int creditHours, String grade, double gradePoint) {
        this.courseCode = courseCode;
        this.courseTitle = courseTitle;
        this.creditHours = creditHours;
        this.grade = grade;
        this.gradePoint = gradePoint;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public int getCreditHours() {
        return creditHours;
    }

    public String getGrade() {
        return grade;
    }

    public double getGradePoint() {
        return gradePoint;
    }
}

