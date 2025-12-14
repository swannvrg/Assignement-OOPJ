package assignement_oopj;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.FileOutputStream;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;

public class PdfReportGenerator {

    // ===== CGPA CALCULATION =====
    public static double calculateCGPA(List<CourseResult> courses) {
        double totalPoints = 0.0;
        int totalCredits = 0;

        for (CourseResult c : courses) {
            totalPoints += c.getGradePoint() * c.getCreditHours();
            totalCredits += c.getCreditHours();
        }

        return totalCredits == 0 ? 0.0 : totalPoints / totalCredits;
    }

    // ===== PDF GENERATION =====
    public static String generateAcademicReport(
            String reportTitle,
            String intake,
            List<CourseResult> courses
    ) {
        String fileName = "Academic_Report.pdf";

        try {
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.open();

            // ===== TITLE =====
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
            Paragraph title = new Paragraph(reportTitle, titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(6);
            document.add(title);

            // ===== INTAKE + DATE =====
            Font infoFont = FontFactory.getFont(FontFactory.HELVETICA, 11);
            String date = new SimpleDateFormat("dd MMM yyyy").format(new Date());

            Paragraph info = new Paragraph(
                    "Intake: " + intake + "    |    Generated on: " + date,
                    infoFont
            );
            info.setAlignment(Element.ALIGN_CENTER);
            info.setSpacingAfter(15);
            document.add(info);

            // ===== TABLE =====
            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{2, 4, 2, 2, 3});
            table.setSpacingBefore(10);

            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11);
            addHeader(table, "Course Code", headerFont);
            addHeader(table, "Course Title", headerFont);
            addHeader(table, "Credit Hours", headerFont);
            addHeader(table, "Grade", headerFont);
            addHeader(table, "Grade Point (4.0 Scale)", headerFont);

            Font bodyFont = FontFactory.getFont(FontFactory.HELVETICA, 11);

            for (CourseResult c : courses) {
                table.addCell(new Phrase(c.getCourseCode(), bodyFont));
                table.addCell(new Phrase(c.getCourseTitle(), bodyFont));
                table.addCell(new Phrase(String.valueOf(c.getCreditHours()), bodyFont));
                table.addCell(new Phrase(c.getGrade(), bodyFont));
                table.addCell(new Phrase(String.format("%.1f", c.getGradePoint()), bodyFont));
            }

            document.add(table);

            // ===== CGPA =====
            double cgpa = calculateCGPA(courses);
            document.add(Chunk.NEWLINE);

            Font cgpaFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
            Paragraph cgpaPara = new Paragraph(
                    "Cumulative Grade Point Average (CGPA): " +
                            String.format("%.2f", cgpa),
                    cgpaFont
            );
            cgpaPara.setAlignment(Element.ALIGN_RIGHT);
            cgpaPara.setSpacingBefore(10);
            document.add(cgpaPara);

            document.close();
            return fileName;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void addHeader(PdfPTable table, String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(8);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.addCell(cell);
    }
}




