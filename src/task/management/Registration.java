/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package task.management;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

/**
 *
 * @author AMANI
 */

public class Registration extends User{
    
//    private Connection connection;
// 
//
//    public  registerUser(int id,String username, String email, String password) {
//        // Validate email format
//        if (!isValidEmail(email)) {
//            System.out.println("Invalid email format.");
//            return false;
//        }
//
//        // Check password length
//        if (password.length() < 8) {
//            System.out.println("Password must be at least 8 characters long.");
//            return false;
//        }
//
//        // Encrypt password
//        String encryptedPassword = encryptPassword(password);
//
//        try {
//            String query = "INSERT INTO user (username, email, password) VALUES (?, ?, ?)";
//            PreparedStatement statement = connection.prepareStatement(query);
//            statement.setString(1, username);
//            statement.setString(2, email);
//            statement.setString(3, encryptedPassword);
//            statement.setString(4, role);
//            int rowsAffected = statement.executeUpdate();
//
//            return rowsAffected > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        } finally {
//            try {
//                if (connection != null) {
//                    connection.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public boolean loginUser(String username, String password) {
//        try {
//            String query = "SELECT * FROM user WHERE username = ?";
//            PreparedStatement statement = connection.prepareStatement(query);
//            statement.setString(1, username);
//            ResultSet resultSet = statement.executeQuery();
//
//            if (resultSet.next()) {
//                // Retrieve the stored encrypted password from the database
//                String storedPassword = resultSet.getString("password");
//
//                // Verify the entered password by encrypting it and comparing it with the stored password
//                String encryptedPassword = encryptPassword(password);
//                return encryptedPassword.equals(storedPassword);
//            }
//
//            return false;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        } finally {
//            try {
//                if (connection != null) {
//                    connection.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    private boolean isValidEmail(String email) {
//        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
//        return Pattern.matches(emailRegex, email);
//    }
//
//    private String encryptPassword(String password) {
//        // Implement your password encryption logic here
//        // This is just a placeholder method using a simple encryption technique
//        StringBuilder encryptedPassword = new StringBuilder();
//        for (int i = 0; i < password.length(); i++) {
//            char c = password.charAt(i);
//            c = (char) (c + 1); // Shift each character by one position
//            encryptedPassword.append(c);
//        }
//        return encryptedPassword.toString();
//    }
}
