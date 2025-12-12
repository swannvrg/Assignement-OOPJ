<<<<<<< HEAD
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignement_oopj;

/**
 *
 * @author swann
 */
=======
package com.crs;

>>>>>>> 01bdfed0f975cb9adbd96cad8b180ff91c6d67e8
public class CourseResult {
    private String studentName;
    private String courseName;
    private double score;

    public CourseResult(String studentName, String courseName, double score) {
        this.studentName = studentName;
        this.courseName = courseName;
        this.score = score;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getCourseName() {
        return courseName;
    }

    public double getScore() {
        return score;
    }

    public double getGradePoint() {
        if (score >= 85) return 4.0;
        if (score >= 75) return 3.5;
        if (score >= 65) return 3.0;
        if (score >= 50) return 2.0;
        return 0.0;
    }
}
