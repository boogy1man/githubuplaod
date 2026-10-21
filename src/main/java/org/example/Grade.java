
package org.example;

public class Grade {

    private int semester;
    private String subject;
    private double credits;
    private double marks;

    public Grade(int semester, String subject,
                 double credits, double marks) {

        this.semester = semester;
        this.subject = subject;
        this.credits = credits;
        this.marks = marks;
    }

    public int getSemester() {
        return semester;
    }

    public String getSubject() {
        return subject;
    }

    public double getCredits() {
        return credits;
    }

    public double getMarks() {
        return marks;
    }

    public String getGrade() {

        if (marks >= 90) return "S";
        if (marks >= 85) return "A+";
        if (marks >= 80) return "A";
        if (marks >= 75) return "B+";
        if (marks >= 70) return "B";
        if (marks >= 65) return "C+";
        if (marks >= 60) return "C";
        if (marks >= 55) return "D";
        if (marks >= 50) return "P";

        return "F";
    }

    public double getGradePoint() {

        switch (getGrade()) {
            case "S": return 10.0;
            case "A+": return 9.0;
            case "A": return 8.5;
            case "B+": return 8.0;
            case "B": return 7.5;
            case "C+": return 7.0;
            case "C": return 6.5;
            case "D": return 6.0;
            case "P": return 5.5;
            default: return 0.0;
        }
    }
}
