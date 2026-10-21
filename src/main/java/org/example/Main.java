
package org.example;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    public Main() {

        setTitle("Academic Tracker");
        setSize(900, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        CardLayout layout = new CardLayout();
        JPanel cards = new JPanel(layout);

        cards.add(new AttendanceGUI(), "attendance");
        cards.add(new GradeGUI(), "grades");

        JButton attendanceButton = new JButton("Attendance");
        JButton gradeButton = new JButton("Grade Card");

        attendanceButton.addActionListener(
                e -> layout.show(cards, "attendance"));

        gradeButton.addActionListener(
                e -> layout.show(cards, "grades"));

        JPanel buttons = new JPanel();
        buttons.add(attendanceButton);
        buttons.add(gradeButton);

        add(buttons, BorderLayout.NORTH);
        add(cards, BorderLayout.CENTER);
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new Main().setVisible(true);
        });
    }
}
