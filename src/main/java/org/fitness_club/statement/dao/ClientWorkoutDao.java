package org.fitness_club.statement.dao;

import org.fitness_club.connection.ConnectionManager;
import org.fitness_club.statement.model.ClientWorkout;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ClientWorkoutDao implements StatementDao<ClientWorkout> {
    private static final Logger logger = Logger.getLogger(ClientWorkoutDao.class.getName());

    @Override
    public void clearTable() {
        try (Statement stmt = ConnectionManager.getConnection().createStatement()) { stmt.executeUpdate("DELETE FROM clients_workouts"); }
        catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public void insert(ClientWorkout cw) {
        try (Statement stmt = ConnectionManager.getConnection().createStatement()) {
            stmt.executeUpdate("INSERT INTO clients_workouts (client_id, workout_id) VALUES (" + cw.getClientId() + ", " + cw.getWorkoutId() + ")");
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public int getCount() {
        int count = 0;
        try (Statement stmt = ConnectionManager.getConnection().createStatement(); ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM clients_workouts")) {
            if (rs.next()) count = rs.getInt(1);
        } catch (SQLException e) { throw new RuntimeException(e); }
        return count;
    }

    @Override
    public List<ClientWorkout> getAll() {
        List<ClientWorkout> list = new ArrayList<>();
        try (Statement stmt = ConnectionManager.getConnection().createStatement(); ResultSet rs = stmt.executeQuery("SELECT * FROM clients_workouts")) {
            while (rs.next()) {
                ClientWorkout cw = new ClientWorkout();
                cw.setId(rs.getLong("id")); cw.setClientId(rs.getLong("client_id")); cw.setWorkoutId(rs.getLong("workout_id"));
                list.add(cw);
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return list;
    }

    @Override
    public ClientWorkout getById(Long id) {
        ClientWorkout cw = null;
        try (Statement stmt = ConnectionManager.getConnection().createStatement(); ResultSet rs = stmt.executeQuery("SELECT * FROM clients_workouts WHERE id = " + id)) {
            if (rs.next()) {
                cw = new ClientWorkout();
                cw.setId(rs.getLong("id")); cw.setClientId(rs.getLong("client_id")); cw.setWorkoutId(rs.getLong("workout_id"));
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return cw;
    }
}