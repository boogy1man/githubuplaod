
package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class GradeGUI extends JPanel {

    GradeManager manager = new GradeManager();

    JComboBox<Integer> semester =
            new JComboBox<>(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8});

    JTextField subject = new JTextField(10);
    JTextField credits = new JTextField(5);
    JTextField marks = new JTextField(5);

    DefaultTableModel model = new DefaultTableModel(
            new String[]{"Subject", "Credits", "Marks",
                    "Grade", "Grade Point"}, 0) {

        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    JTable table = new JTable(model);
    JLabel sgpa = new JLabel("SGPA: 0.00");

    public GradeGUI() {

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel title = new JLabel("Grade Card", JLabel.CENTER);

        JPanel input = new JPanel(new FlowLayout(FlowLayout.LEFT));
        input.setBorder(BorderFactory.createTitledBorder("Enter Grade"));

        JButton addButton = new JButton("Add / Update");
        JButton deleteButton = new JButton("Delete Selected");

        input.add(new JLabel("Semester:"));
        input.add(semester);
        input.add(new JLabel("Subject:"));
        input.add(subject);
        input.add(new JLabel("Credits:"));
        input.add(credits);
        input.add(new JLabel("Marks:"));
        input.add(marks);
        input.add(addButton);
        input.add(deleteButton);

        JPanel top = new JPanel(new BorderLayout());
        top.add(title, BorderLayout.NORTH);
        top.add(input, BorderLayout.CENTER);

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(sgpa, BorderLayout.SOUTH);

        addButton.addActionListener(e -> addGrade());
        deleteButton.addActionListener(e -> deleteGrade());
        semester.addActionListener(e -> loadGrades());

        loadGrades();
    }

    void addGrade() {

        try {
            int sem = (Integer) semester.getSelectedItem();
            String name = subject.getText().trim();
            double c = Double.parseDouble(credits.getText().trim());
            double m = Double.parseDouble(marks.getText().trim());

            if (name.isEmpty() || c <= 0 || m < 0 || m > 100) {
                JOptionPane.showMessageDialog(this, "Invalid values");
                return;
            }

            Grade g = new Grade(sem, name, c, m);
            manager.saveGrade(g);

            subject.setText("");
            credits.setText("");
            marks.setText("");

            loadGrades();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Enter valid numbers");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    void loadGrades() {

        model.setRowCount(0);

        int sem = (Integer) semester.getSelectedItem();

        try {
            ArrayList<Grade> list = manager.getGrades(sem);

            for (Grade g : list) {
                model.addRow(new Object[]{
                        g.getSubject(),
                        g.getCredits(),
                        g.getMarks(),
                        g.getGrade(),
                        g.getGradePoint()
                });
            }

            sgpa.setText(String.format(
                    "SGPA: %.2f", manager.calculateSGPA(list)));

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    void deleteGrade() {

        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a grade");
            return;
        }

        String name = model.getValueAt(row, 0).toString();
        int sem = (Integer) semester.getSelectedItem();

        int answer = JOptionPane.showConfirmDialog(
                this, "Delete " + name + "?",
                "Confirm", JOptionPane.YES_NO_OPTION);

        if (answer != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            manager.deleteGrade(sem, name);
            loadGrades();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
}
