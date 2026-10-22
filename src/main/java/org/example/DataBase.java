
package org.example;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

public class DataBase {

    public static void initialize() throws SQLException {

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement()) {

            st.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS subjects (" +
                            "subject_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            "subject_name TEXT NOT NULL UNIQUE, " +
                            "attended_classes INTEGER NOT NULL DEFAULT 0, " +
                            "total_classes INTEGER NOT NULL DEFAULT 0)"
            );

            st.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS grade_records (" +
                            "grade_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            "semester INTEGER NOT NULL, " +
                            "subject_name TEXT NOT NULL, " +
                            "credits REAL NOT NULL, " +
                            "marks REAL NOT NULL, " +
                            "grade TEXT NOT NULL, " +
                            "grade_point REAL NOT NULL, " +
                            "UNIQUE (semester, subject_name))"
            );
        }
    }
}
