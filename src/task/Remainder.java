/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package task;

/**
 *
 * @author AMANI
 */


import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import task.management.DatabaseConnection;
import task.management.Email;

public class Remainder extends taskRemainder{
//    private int id;
    private Date reminder_date;
    private String email;
    private String message;
    private int task_id;

    public Remainder(int id, Date reminder_date, String email, String message, int taskId) {
        super(id);

//        this.id = id;
//        this.reminder_date = new Date(System.currentTimeMillis());
     this.reminder_date = reminder_date;
        this.email = email;
        this.message = message;
        this.task_id = taskId;
    }

//    public int getId() {
//        return id;
//    }

    public Date getReminderDate() {
        return reminder_date;
    }

    public void setReminderDate(Date reminderDate) {
        this.reminder_date = reminderDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getTaskId() {
        return task_id;
    }

//    public void setTaskId(int taskId) {
//        this.task_id = taskId;
//    }
    
@Override
    public boolean add() {
        boolean addedSuccess = false;

        // Check if all required fields are filled
        if ( email.isEmpty() || message == null) {
            JOptionPane.showMessageDialog(null, "Please fill in all fields.", "Task Addition Error",
                    JOptionPane.ERROR_MESSAGE);
            return addedSuccess;
        }
          // Validate email format
        if (!isValidEmail(email)) {
            JOptionPane.showMessageDialog(null, "Invalid email format.", "Registration Error", JOptionPane.ERROR_MESSAGE);
            return addedSuccess;
        }
        

        // Create a database connection
        try {
            try (DatabaseConnection conn = new DatabaseConnection();
                    PreparedStatement statement = conn
                            .prepareStatement("INSERT INTO reminders  (reminder_date, email, message, task_id) VALUES (?, ?, ?, ?)")) {
                statement.setDate(1, reminder_date);
                statement.setString(2, email);
                statement.setString(3, message);
                statement.setInt(4, task_id);

                // Execute the SQL statement
                int rowsAffected = statement.executeUpdate();

                // Check the result
                if (rowsAffected > 0) {

                    addedSuccess = true;
                    // Show success message
//                    JOptionPane.showMessageDialog(null, "Remainder  added successfully.", "Task Addition",JOptionPane.INFORMATION_MESSAGE);
                     Date currentDate = new Date(System.currentTimeMillis());
            if (reminder_date.compareTo(currentDate) <= 0) {
                // Send the email
                String senderEmail = "amanidavid96@gmail.com";
                String senderPassword = "mjln yrud ycrw evwi";
                String host = "smtp.gmail.com";
                String port = "587";

                Email emailSender = new Email(senderEmail, senderPassword, host, port);
                 emailSender.sendEmail(email, "Reminder: "  + message, message);
            }
                } else {
                    // Show error message if the task addition failed
                    JOptionPane.showMessageDialog(null, "Failed to add Remainder.", "Remainder Addition Error",
                            JOptionPane.ERROR_MESSAGE);
                }
                return addedSuccess;
            }
        } catch (SQLException e) {
            // Show error message if there was an exception during database operation
            JOptionPane.showMessageDialog(null, "An error occurred while adding the task.", "Task Addition Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace(); // Print the stack trace
            return addedSuccess;
        }
    } 
    private static boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return Pattern.matches(emailRegex, email);
    }
     
@Override
    public boolean update() {
        boolean updatedSuccess = false;
          if ( email.isEmpty() || message == null) {
            JOptionPane.showMessageDialog(null, "Please fill in all fields.", "Task Addition Error",
                    JOptionPane.ERROR_MESSAGE);
            return updatedSuccess;
        }
          // Validate email format
        if (!isValidEmail(email)) {
            JOptionPane.showMessageDialog(null, "Invalid email format.", "Registration Error", JOptionPane.ERROR_MESSAGE);
            return updatedSuccess;
        }

        try (DatabaseConnection conn = new DatabaseConnection();
             PreparedStatement statement = conn.prepareStatement(
                     "UPDATE reminders SET reminder_date = ?, email = ?, message = ?, task_id = ? WHERE id = ?")) {

            statement.setDate(1, reminder_date);
            statement.setString(2, email);
            statement.setString(3, message);
            statement.setInt(4, task_id);
            statement.setInt(5, id);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                updatedSuccess = true;
                JOptionPane.showMessageDialog(null, "Reminder updated successfully.", "Reminder Update",
                        JOptionPane.INFORMATION_MESSAGE);
                
                 
            } else {
                JOptionPane.showMessageDialog(null, "Failed to update reminder.", "Reminder Update Error",
                        JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "An error occurred while updating the reminder.", "Reminder Update Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        return updatedSuccess;
    }
@Override
    public boolean delete() {
        boolean deletedSuccess = false;

        try (DatabaseConnection conn = new DatabaseConnection();
             PreparedStatement statement = conn.prepareStatement("DELETE FROM reminders WHERE id = ?")) {

            statement.setInt(1, id);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                deletedSuccess = true;
                JOptionPane.showMessageDialog(null, "Reminder deleted successfully.", "Reminder Deletion",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Failed to delete reminder.", "Reminder Deletion Error",
                        JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "An error occurred while deleting the reminder.", "Reminder Deletion Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        return deletedSuccess;
    }
}
