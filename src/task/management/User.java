package task.management;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class User {
    private int id;
    private String username;
    private String email;
    private String password;

    public User() {
        
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public static boolean registerUser(String username, String email, String password) throws SQLException {
        boolean registrationSuccess = false;
        // Check if all inputs are filled
        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill in all fields.", "Registration Error", JOptionPane.ERROR_MESSAGE);
            return registrationSuccess;
        }

        // Validate email format
        if (!isValidEmail(email)) {
            JOptionPane.showMessageDialog(null, "Invalid email format.", "Registration Error", JOptionPane.ERROR_MESSAGE);
            return registrationSuccess;
        }

        // Check password length
        if (password.length() < 8) {
            JOptionPane.showMessageDialog(null, "Password must be at least 8 characters long.", "Registration Error", JOptionPane.ERROR_MESSAGE);
            return registrationSuccess;
        }

        // Database connection and registration logic
        DatabaseConnection conn = new DatabaseConnection();
        try (PreparedStatement statement = conn.prepareStatement("INSERT INTO users(username, email, password) VALUES (?, ?, ?)")) {

            statement.setString(1, username);
            statement.setString(2, email);
            //statement.setString(3, password);
            statement.setString(3, encryptPassword(password));


            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Registration successful!", "Registration", JOptionPane.INFORMATION_MESSAGE);
                registrationSuccess = true;
                
                         // Send registration email
                String senderEmail = "amanidavid96@gmail.com";
                String senderPassword = "mjln yrud ycrw evwi";
                String host = "smtp.gmail.com";
                String port = "587";

                Email emailSender = new Email(senderEmail, senderPassword, host, port);
                String subject = "Welcome to our platform!";
                String messageContent = "Dear " + username + ",\n\nWelcome to our platform! Thank you for registering.";

                emailSender.sendEmail(email, subject, messageContent);
                
            } else {
                JOptionPane.showMessageDialog(null, "Registration failed. Please try again.", "Registration Error", JOptionPane.ERROR_MESSAGE);
            }
            return registrationSuccess;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "An error occurred during registration. Please try again.", "Registration Error", JOptionPane.ERROR_MESSAGE);
            return registrationSuccess;
        }
    }

    private static boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return Pattern.matches(emailRegex, email);
    }
    
    public static boolean loginUser(String email, String password) throws SQLException {
        boolean loginSuccess = false;
     

        // Check if email and password are provided
        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please provide email and password.", "Login Error", JOptionPane.ERROR_MESSAGE);
            return loginSuccess;
        }

        // Validate email format
        if (!isValidEmail(email)) {
            JOptionPane.showMessageDialog(null, "Invalid email format.", "Login Error", JOptionPane.ERROR_MESSAGE);
            return loginSuccess;
        }

     
        // Database connection and login logic
        DatabaseConnection conn = new DatabaseConnection();
        try (PreparedStatement statement = conn.prepareStatement("SELECT * FROM users WHERE email = ? AND password = ?")) {

            statement.setString(1, email);
            statement.setString(2, encryptPassword(password));

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
      
            loginSuccess = true;
      ;
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            conn.close();
        }

        return loginSuccess;
    }
    
      private static String encryptPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(password.getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte hashByte : hashBytes) {
                sb.append(Integer.toString((hashByte & 0xff) + 0x100, 16).substring(1));
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            return "";
        }
      } 
   

}
