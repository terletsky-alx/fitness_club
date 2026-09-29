package org.fitness_club.statement.dao;

import org.fitness_club.connection.ConnectionManager;
import org.fitness_club.statement.model.Client;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ClientDao implements StatementDao<Client> {
    private static final Logger logger = Logger.getLogger(ClientDao.class.getName());

    @Override
    public void clearTable() {
        try (Statement stmt = ConnectionManager.getConnection().createStatement()) {
            stmt.executeUpdate("DELETE FROM clients");
        } catch (SQLException e) {
            logger.severe("Ошибка: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insert(Client client) {
        try (Statement stmt = ConnectionManager.getConnection().createStatement()) {
            stmt.executeUpdate("INSERT INTO clients (first_name, last_name, phone) " +
                    "VALUES ('" + client.getFirstName() + "', '" + client.getLastName() + "', '" + client.getPhone() + "')");
        } catch (SQLException e) {
            logger.severe("Ошибка: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getCount() {
        int count = 0;
        try (Statement stmt = ConnectionManager.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM clients")) {
            if (rs.next()) count = rs.getInt(1);
        } catch (SQLException e) {
            logger.severe("Ошибка: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return count;
    }

    @Override
    public List<Client> getAll() {
        List<Client> list = new ArrayList<>();
        try (Statement stmt = ConnectionManager.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM clients")) {
            while (rs.next()) {
                Client c = new Client();
                c.setId(rs.getLong("id"));
                c.setFirstName(rs.getString("first_name"));
                c.setLastName(rs.getString("last_name"));
                c.setPhone(rs.getString("phone"));
                list.add(c);
            }
        } catch (SQLException e) {
            logger.severe("Ошибка: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public Client getById(Long id) {
        Client client = null;
        try (Statement stmt = ConnectionManager.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM clients WHERE id = " + id)) {
            if (rs.next()) {
                client = new Client();
                client.setId(rs.getLong("id"));
                client.setFirstName(rs.getString("first_name"));
                client.setLastName(rs.getString("last_name"));
                client.setPhone(rs.getString("phone"));
            }
        } catch (SQLException e) {
            logger.severe("Ошибка: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return client;
    }

    // ЗАДАНИЕ 6: Обновить имя всех клиентов, посещающих тренировки определённого тренера
    public void updateClientsOfTrainer(long trainerId, String newFirstName) {
        try (Statement stmt = ConnectionManager.getConnection().createStatement()) {
            String sql = "UPDATE clients SET first_name = '" + newFirstName + "' WHERE id IN " +
                    "(SELECT client_id FROM clients_workouts WHERE workout_id IN " +
                    "(SELECT id FROM workouts WHERE trainer_id = " + trainerId + "))";
            int updated = stmt.executeUpdate(sql);
            System.out.println("Обновлено клиентов: " + updated);
        } catch (SQLException e) {
            logger.severe("Ошибка: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}