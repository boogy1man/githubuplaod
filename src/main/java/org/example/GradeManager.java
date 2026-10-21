
package org.example;

import java.sql.*;
import java.util.ArrayList;

public class GradeManager {

    public void saveGrade(Grade grade) throws SQLException {

        String sql = "INSERT INTO grade_records "
                + "(semester, subject_name, credits, marks, grade, grade_point) "
                + "VALUES (?, ?, ?, ?, ?, ?) "
                + "ON DUPLICATE KEY UPDATE credits = VALUES(credits), "
                + "marks = VALUES(marks), grade = VALUES(grade), "
                + "grade_point = VALUES(grade_point)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, grade.getSemester());
            ps.setString(2, grade.getSubject());
            ps.setDouble(3, grade.getCredits());
            ps.setDouble(4, grade.getMarks());
            ps.setString(5, grade.getGrade());
            ps.setDouble(6, grade.getGradePoint());

            ps.executeUpdate();
        }
    }

    public ArrayList<Grade> getGrades(int semester) throws SQLException {

        ArrayList<Grade> grades = new ArrayList<>();

        String sql = "SELECT * FROM grade_records "
                + "WHERE semester = ? ORDER BY subject_name";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, semester);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {

                    Grade grade = new Grade(
                            rs.getInt("semester"),
                            rs.getString("subject_name"),
                            rs.getDouble("credits"),
                            rs.getDouble("marks")
                    );

                    grades.add(grade);
                }
            }
        }

        return grades;
    }

    public void deleteGrade(int semester, String subject)
            throws SQLException {

        String sql = "DELETE FROM grade_records "
                + "WHERE semester = ? AND subject_name = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, semester);
            ps.setString(2, subject);
            ps.executeUpdate();
        }
    }

    public double calculateSGPA(ArrayList<Grade> grades) {

        double totalCredits = 0;
        double totalPoints = 0;

        for (Grade grade : grades) {
            totalCredits += grade.getCredits();
            totalPoints += grade.getCredits() * grade.getGradePoint();
        }

        if (totalCredits == 0) {
            return 0;
        }

        return totalPoints / totalCredits;
    }
}
