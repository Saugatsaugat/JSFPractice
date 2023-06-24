/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

/**
 *
 * @author saugat
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcController {
    Connection con;
    public JdbcController() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String database = "test";
        String server = "localhost";
        String username = "saugat";
        String password = "Nepal@123";
        int port = 3306;
        String ConnectionString = "jdbc:mysql://" + server + ":" + port + "/" + database;
        con = DriverManager.getConnection(ConnectionString, username, password);
    }
    public Connection getCon(){
        return con;
    }  
    
}
