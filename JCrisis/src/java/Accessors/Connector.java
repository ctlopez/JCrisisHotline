/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Accessors;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author NH228U02
 */
public class Connector {
    public static Connection createDBConnection() throws SQLException {
        String databaseUrl = "localhost";
        String port = "3306";
        String databaseName = "JCrisis_Hotline_DB";
        String databaseUserName = "timekeeper_client";
        String password = "let_me_in";
        String connectionString = "jdbc:mysql://" + databaseUrl + ":" 
                + port
                + "/" + databaseName 
                + "?useSSL=false"
                +"&user=" + databaseUserName + "&password="
                + password;
        return DriverManager.getConnection(connectionString);
    }
}
