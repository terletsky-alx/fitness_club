package org.fitness_club;

import org.fitness_club.connection.ConnectionManager;
import org.fitness_club.menus.MainMenu;

import java.util.logging.Logger;
public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        if (ConnectionManager.getConnection() == null) {
            logger.severe("Failed to connect to the database. The program has terminated.");
            return;
        }

        System.out.println("Database connection established. Launching the fitness club software..");

        try {
            new MainMenu().print();
        } catch (Exception e) {
            logger.severe("Menu error: " + e.getMessage());
        }

        ConnectionManager.closeConnection();
        System.out.println("Program was ended. Connection closed.");
    }
}
