
package org.example;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class AttendanceGUI extends JPanel {

    AttendanceManager manager = new AttendanceManager();

    JComboBox<String> subjectBox = new JComboBox<>();

    JTextField newSubject = new JTextField(10);
    JTextField attended = new JTextField(5);
    JTextField total = new JTextField(5);

    JPanel subjectPanel = new JPanel();

    public AttendanceGUI() {

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel title = new JLabel("Attendance Tracker", JLabel.CENTER);
        add(title, BorderLayout.NORTH);

        subjectPanel.setLayout(new BoxLayout(subjectPanel, BoxLayout.Y_AXIS));
        add(subjectPanel, BorderLayout.CENTER);

        JPanel controls = new JPanel(new GridLayout(3, 1, 5, 5));

        JPanel addPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        addPanel.add(new JLabel("New Subject:"));
        addPanel.add(newSubject);

        JButton addButton = new JButton("Add Subject");
        addPanel.add(addButton);

        JPanel updatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        updatePanel.add(new JLabel("Subject:"));
        updatePanel.add(subjectBox);
        updatePanel.add(new JLabel("Attended:"));
        updatePanel.add(attended);
        updatePanel.add(new JLabel("Total:"));
        updatePanel.add(total);

        JButton updateButton = new JButton("Update");
        updatePanel.add(updateButton);

        JPanel deletePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton deleteButton = new JButton("Delete Selected Subject");
        deletePanel.add(deleteButton);

        controls.add(addPanel);
        controls.add(updatePanel);
        controls.add(deletePanel);

        add(controls, BorderLayout.SOUTH);

        addButton.addActionListener(e -> addSubject());
        updateButton.addActionListener(e -> updateAttendance());
        deleteButton.addActionListener(e -> deleteSubject());

        subjectBox.addActionListener(e -> showAttendance());

        loadSubjects();
    }

    void loadSubjects() {

        try {
            ArrayList<Subject> list = manager.getSubjects();

            subjectBox.removeAllItems();
            subjectPanel.removeAll();

            for (Subject s : list) {

                subjectBox.addItem(s.getName());

                int percentage = s.getPercentage();

                JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT));
                row.setMaximumSize(new Dimension(700, 35));

                row.add(new JLabel(s.getName()));
                row.add(new JLabel("Attendance: " + percentage + "%"));
                row.add(new JLabel(
                        "(" + s.getAttended() + "/" + s.getTotal() + ")"));

                subjectPanel.add(row);
            }

            subjectPanel.revalidate();
            subjectPanel.repaint();

            if (subjectBox.getItemCount() > 0) {
                subjectBox.setSelectedIndex(0);
                showAttendance();
            } else {
                attended.setText("");
                total.setText("");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    void showAttendance() {

        String name = (String) subjectBox.getSelectedItem();

        if (name == null) {
            return;
        }

        try {
            ArrayList<Subject> list = manager.getSubjects();

            for (Subject s : list) {
                if (s.getName().equals(name)) {
                    attended.setText("" + s.getAttended());
                    total.setText("" + s.getTotal());
                    break;
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    void addSubject() {

        String name = newSubject.getText().trim();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter subject name");
            return;
        }

        try {
            manager.addSubject(name);
            newSubject.setText("");
            loadSubjects();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    this, "Could not add subject: " + e.getMessage());
        }
    }

    void updateAttendance() {

        String name = (String) subjectBox.getSelectedItem();

        if (name == null) {
            JOptionPane.showMessageDialog(this, "Select a subject");
            return;
        }

        try {
            int a = Integer.parseInt(attended.getText().trim());
            int t = Integer.parseInt(total.getText().trim());

            if (a < 0 || t < 0 || a > t) {
                JOptionPane.showMessageDialog(
                        this, "Enter valid attendance values");
                return;
            }

            manager.updateAttendance(name, a, t);
            loadSubjects();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Enter whole numbers");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    void deleteSubject() {

        String name = (String) subjectBox.getSelectedItem();

        if (name == null) {
            JOptionPane.showMessageDialog(this, "Select a subject");
            return;
        }

        int answer = JOptionPane.showConfirmDialog(
                this, "Delete " + name + "?",
                "Confirm", JOptionPane.YES_NO_OPTION);

        if (answer != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            manager.deleteSubject(name);
            loadSubjects();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
}
