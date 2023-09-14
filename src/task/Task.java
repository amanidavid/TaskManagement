/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package task;
//import java.util.Date;
import java.sql.Date;
import task.management.DatabaseConnection;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.sql.PreparedStatement;

//import task.taskRemainder.Priority;

/**
 *
 * @author AMANI
 */ 
public class Task extends taskRemainder {
    private String title;
    private String description;
    private Priority priority;
     private final Date start_Date; // Immutable start date
     

    public Task(int id, String title, String description, Priority priority) {
        super(id);
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.start_Date = new Date(System.currentTimeMillis()); // Automatically generate start date      
//        this.startDate = new Date();
    }

 
    
     public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }
    
     public Date getStartDate() {
        return start_Date;
    }

    /**
     *
     * @return
     * @throws SQLException
     */
  @Override
    public boolean add() {
        boolean addedSuccess = false;

        // Check if all required fields are filled
        if (title.isEmpty() || description.isEmpty() || priority == null) {
            JOptionPane.showMessageDialog(null, "Please fill in all fields.", "Task Addition Error",
                    JOptionPane.ERROR_MESSAGE);
            return addedSuccess;
        }

        // Create a database connection
        try {
            try (DatabaseConnection conn = new DatabaseConnection();
                    PreparedStatement statement = conn
                            .prepareStatement("INSERT INTO tasks (title, description, priority, start_Date) VALUES (?, ?, ?, ?)")) {
                statement.setString(1, title);
                statement.setString(2, description);
                statement.setString(3, priority.toString());
                statement.setDate(4, start_Date);

                // Execute the SQL statement
                int rowsAffected = statement.executeUpdate();

                // Check the result
                if (rowsAffected > 0) {

                    addedSuccess = true;
                    // Show success message
                    JOptionPane.showMessageDialog(null, "Task added successfully.", "Task Addition",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    // Show error message if the task addition failed
                    JOptionPane.showMessageDialog(null, "Failed to add task.", "Task Addition Error",
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




 @Override
    public boolean delete() {
        boolean deletedSuccess = false;
        
        // Check if the task ID is valid
        if (getId() <= 0) {
            JOptionPane.showMessageDialog(null, "Invalid task ID.", "Task Deletion Error", JOptionPane.ERROR_MESSAGE);
            return deletedSuccess;
        }
        
        // Create a database connection
        try (DatabaseConnection conn = new DatabaseConnection();
             PreparedStatement statement = conn.prepareStatement("DELETE FROM tasks WHERE id = ?")) {
            
            statement.setInt(1, getId());
            
            // Execute the SQL statement
            int rowsAffected = statement.executeUpdate();
            
            // Check the result
            if (rowsAffected > 0) {
                deletedSuccess = true;
                // Show success message
                JOptionPane.showMessageDialog(null, "Task deleted successfully.", "Task Deletion", JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Show error message if the task deletion failed
                JOptionPane.showMessageDialog(null, "Failed to delete task.", "Task Deletion Error", JOptionPane.ERROR_MESSAGE);
            }
            
            return deletedSuccess;
        } catch (SQLException e) {
            // Show error message if there was an exception during database operation
            JOptionPane.showMessageDialog(null, "An error occurred while deleting the task.", "Task Deletion Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace(); // Print the stack trace
            return deletedSuccess;
        }
    }
    
      @Override
    public boolean update() {
        boolean updatedSuccess = false;
        
        // Check if all required fields are filled
        if (title.isEmpty() || description.isEmpty() || priority == null) {
            JOptionPane.showMessageDialog(null, "Please fill in all fields.", "Task Update Error", JOptionPane.ERROR_MESSAGE);
            return updatedSuccess;
        }
        
        // Create a database connection
        try (DatabaseConnection conn = new DatabaseConnection();
             PreparedStatement statement = conn.prepareStatement("UPDATE tasks SET title = ?, description = ?, priority = ? WHERE id = ?")) {
            
            statement.setString(1, title);
            statement.setString(2, description);
            statement.setString(3, priority.toString());
            statement.setInt(4, getId());
            
            // Execute the SQL statement
            int rowsAffected = statement.executeUpdate();
            
            // Check the result
            if (rowsAffected > 0) {
                updatedSuccess = true;
                // Show success message
                JOptionPane.showMessageDialog(null, "Task updated successfully.", "Task Update", JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Show error message if the task update failed
                JOptionPane.showMessageDialog(null, "Failed to update task.", "Task Update Error", JOptionPane.ERROR_MESSAGE);
            }
            
            return updatedSuccess;
        } catch (SQLException e) {
            // Show error message if there was an exception during database operation
            JOptionPane.showMessageDialog(null, "An error occurred while updating the task.", "Task Update Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace(); // Print the stack trace
            return updatedSuccess;
        }
    }
    
    

}



//    @Override
//    public boolean delete() {
//        // Implementation for deleting a task
//        // Perform necessary database operations
//    }

//    @Override
//    public boolean update() {
//        // Implementation for updating a task
//        // Perform necessary database operations
//    }
    
    

