
package org.example;

import java.sql.*;
import java.util.ArrayList;

public class AttendanceManager {

    public ArrayList<Subject> getSubjects() throws SQLException {

        ArrayList<Subject> subjects = new ArrayList<>();

        String sql = "SELECT * FROM subjects ORDER BY subject_id";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Subject subject = new Subject(
                        rs.getString("subject_name"),
                        rs.getInt("attended_classes"),
                        rs.getInt("total_classes")
                );

                subjects.add(subject);
            }
        }

        return subjects;
    }

    public void addSubject(String name) throws SQLException {

        String sql = "INSERT INTO subjects "
                + "(subject_name, attended_classes, total_classes) "
                + "VALUES (?, 0, 0)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.executeUpdate();
        }
    }

    public void deleteSubject(String name) throws SQLException {

        String sql = "DELETE FROM subjects WHERE subject_name = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.executeUpdate();
        }
    }

    public void updateAttendance(
            String name, int attended, int total) throws SQLException {

        String sql = "UPDATE subjects SET attended_classes = ?, "
                + "total_classes = ? WHERE subject_name = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, attended);
            ps.setInt(2, total);
            ps.setString(3, name);
            ps.executeUpdate();
        }
    }
}
