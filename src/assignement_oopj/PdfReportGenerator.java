<<<<<<< HEAD
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignement_oopj;
=======
package com.crs;
>>>>>>> 01bdfed0f975cb9adbd96cad8b180ff91c6d67e8

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;

public class PdfReportGenerator {

    public static String generateReport(CourseResult result) {
        try {
            String fileName = "CourseReport_" + result.getStudentName() + ".pdf";
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.open();

            Font titleFont = new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD);
            Font textFont = new Font(Font.FontFamily.HELVETICA, 12);

            document.add(new Paragraph("Course Result Report", titleFont));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Student Name: " + result.getStudentName(), textFont));
            document.add(new Paragraph("Course Name: " + result.getCourseName(), textFont));
            document.add(new Paragraph("Score: " + result.getScore(), textFont));
            document.add(new Paragraph("Grade Point: " + result.getGradePoint(), textFont));

            document.close();

            return fileName;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
<<<<<<< HEAD
=======

>>>>>>> 01bdfed0f975cb9adbd96cad8b180ff91c6d67e8
