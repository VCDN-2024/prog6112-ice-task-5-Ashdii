package TutoringScheduler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

public class TutoringScheduler extends JFrame {

    private JTextField studentNameField;
    private JComboBox<String> dayComboBox;
    private JComboBox<String> timeComboBox;
    private JButton submitButton;
    private JList<String> sessionList;
    private DefaultListModel<String> listModel;

    private static final String FILE_NAME = "tutoring_sessions.txt";

    public TutoringScheduler() {
        // Set up the frame
        setTitle("Tutoring Session Scheduler");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        // Create and add components
        studentNameField = new JTextField(20);
        dayComboBox = new JComboBox<>(new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"});
        timeComboBox = new JComboBox<>(new String[]{"3:00 PM", "4:00 PM", "5:00 PM", "6:00 PM", "7:00 PM", "8:00 PM"});
        submitButton = new JButton("Submit");

        listModel = new DefaultListModel<>();
        sessionList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(sessionList);

        // Add components to the frame
        add(new JLabel("Student Name:"));
        add(studentNameField);
        add(new JLabel("Session Day:"));
        add(dayComboBox);
        add(new JLabel("Session Time:"));
        add(timeComboBox);
        add(submitButton);
        add(scrollPane);

        // Load saved sessions from file
        loadSessionsFromFile();

        // Add button action listener
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveSession();
            }
        });
    }

    private void saveSession() {
        String studentName = studentNameField.getText();
        String sessionDay = (String) dayComboBox.getSelectedItem();
        String sessionTime = (String) timeComboBox.getSelectedItem();

        if (studentName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter the student's name.");
            return;
        }

        String sessionDetails = studentName + " - " + sessionDay + " - " + sessionTime;
        listModel.addElement(sessionDetails);

        // Save to file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(sessionDetails);
            writer.newLine();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error saving session details.");
        }

        studentNameField.setText("");  // Clear the input field after saving
    }

    private void loadSessionsFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                listModel.addElement(line);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error loading saved sessions.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TutoringScheduler scheduler = new TutoringScheduler();
            scheduler.setVisible(true);
        });
    }
}


//Refrence 
//Website: Trail: Creating a GUI with Swing (no date) Trail: Creating a GUI With Swing (The Java™ Tutorials). Available at: https://docs.oracle.com/javase/tutorial/uiswing/#:~:text=This%20trail%20tells%20you%20how%20to%20create%20graphical%20user%20interfaces (Accessed: 26 September 2024).