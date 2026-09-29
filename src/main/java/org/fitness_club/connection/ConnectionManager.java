package org.fitness_club.connection;

import java.sql.*;
import java.util.logging.Logger;

public class ConnectionManager {

    private static final Logger logger = Logger.getLogger(ConnectionManager.class.getName());
    private static Connection connection = null;

    public static Connection getConnection() {
        if(connection == null){
            try {
                Class.forName("org.postgresql.Driver");
            }
            catch(ClassNotFoundException e) {
                closeConnection();
                logger.severe("Driver for DataBase not found: " + e.getMessage());
            }
            final String login = "postgres";
            final String password = "3557";
            final String url = "jdbc:postgresql://localhost:5433/fitness_club";

            try {
                connection = DriverManager.getConnection(url, login, password);
                logger.info("Connection with DataBase successfully established.");
            }
            catch(SQLException e) {
                closeConnection();
                logger.info("Failed to establish  connection with DataBase: " + e.getMessage());
            }
        }
        return connection;
    }

    public static void closeConnection() {
        if(connection != null) {
            try {
                connection.close();
                logger.info("Connection with DataBase successfully closed.");
            }
            catch(SQLException e) {
                logger.severe("Failed to close DataBase connection: " + e.getMessage());
            }
            finally {
                connection = null;
            }
        }
        else {
            logger.info("Connection with DataBase already closed.");
        }
    }


}
