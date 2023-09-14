/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package task.management;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;


/**
 *
 * @author AMANI
 */



public class DatabaseConnection  implements AutoCloseable {
    final String DB_URL = "jdbc:mysql://localhost:3306/management";
    final String USER = "root";
    static final String PASSWORD = "";
    
    public Connection connection;
    public Statement statement;


    public DatabaseConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection =DriverManager.getConnection( DB_URL,USER, PASSWORD);
            statement=connection.createStatement();
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

  

    public void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
            }
        }
    }

    public PreparedStatement prepareStatement(String sql) throws SQLException {
        return connection.prepareStatement(sql);
}

//  

    
}

