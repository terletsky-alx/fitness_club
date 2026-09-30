package org.fitness_club.statement.dao;

import org.fitness_club.connection.ConnectionManager;
import org.fitness_club.statement.model.Workout;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class WorkoutDao implements StatementDao<Workout> {
    private static final Logger logger = Logger.getLogger(WorkoutDao.class.getName());

    @Override
    public void clearTable() {
        try (Statement stmt = ConnectionManager.getConnection().createStatement()) {
            stmt.executeUpdate("DELETE FROM workouts");
        } catch (SQLException e) {
            logger.severe("Ошибка: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insert(Workout w) {
        try (Statement stmt = ConnectionManager.getConnection().createStatement()) {
            stmt.executeUpdate("INSERT INTO workouts (trainer_id, name, duration_minutes, difficulty, room_number) " +
                    "VALUES (" + w.getTrainerId() + ", '" + w.getName() +
                    "', " + w.getDurationMinutes() + ", '" + w.getDifficulty() + "', " + w.getRoomNumber() + ")");
        } catch (SQLException e) {
            logger.severe("Ошибка: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getCount() {
        int count = 0;
        try (Statement stmt = ConnectionManager.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM workouts")) {
            if (rs.next()) count = rs.getInt(1);
        } catch (SQLException e) {
            logger.severe("Ошибка: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return count;
    }

    @Override
    public List<Workout> getAll() {
        List<Workout> list = new ArrayList<>();
        try (Statement stmt = ConnectionManager.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM workouts")) {
            while (rs.next()) {
                Workout w = new Workout();
                w.setId(rs.getLong("id"));
                w.setTrainerId(rs.getLong("trainer_id"));
                w.setName(rs.getString("name"));
                w.setDurationMinutes(rs.getInt("duration_minutes"));
                list.add(w);
            }
        } catch (SQLException e) {
            logger.severe("Ошибка: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public Workout getById(Long id) {
        Workout w = null;
        try (Statement stmt = ConnectionManager.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM workouts WHERE id = " + id)) {
            if (rs.next()) {
                w = new Workout();
                w.setId(rs.getLong("id"));
                w.setTrainerId(rs.getLong("trainer_id"));
                w.setName(rs.getString("name"));
                w.setDurationMinutes(rs.getInt("duration_minutes"));
            }
        } catch (SQLException e) {
            logger.severe("Ошибка: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return w;
    }

    public List<Workout> getLongerThan(int minutes) {
        List<Workout> list = new ArrayList<>();
        try (Statement stmt = ConnectionManager.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM workouts WHERE duration_minutes > " + minutes + " ORDER BY duration_minutes")) {
            while (rs.next()) {
                Workout w = new Workout();
                w.setId(rs.getLong("id"));
                w.setTrainerId(rs.getLong("trainer_id"));
                w.setName(rs.getString("name"));
                w.setDurationMinutes(rs.getInt("duration_minutes"));
                list.add(w);
            }
        } catch (SQLException e) {
            logger.severe("Ошибка: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return list;
    }

    // ЗАДАНИЕ 4: Обновить название случайной тренировки у определённого тренера
    public void updateRandomWorkoutNameOfTrainer(long trainerId, String newName) {
        try (Statement stmt = ConnectionManager.getConnection().createStatement()) {
            String sql = "UPDATE workouts SET name = '" + newName + "' WHERE id = " +
                    "(SELECT id FROM workouts WHERE trainer_id = " + trainerId + " LIMIT 1)";
            int updated = stmt.executeUpdate(sql);
            System.out.println("Обновлено тренировок: " + updated);
        } catch (SQLException e) {
            logger.severe("Ошибка: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public List<Workout> getByTrainerLastNameStartsWith(String letter) {
        List<Workout> list = new ArrayList<>();
        try (Statement stmt = ConnectionManager.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT w.* FROM workouts w JOIN trainers t ON w.trainer_id = t.id " +
                             "WHERE t.last_name LIKE '" + letter + "%'")) {
            while (rs.next()) {
                Workout w = new Workout();
                w.setId(rs.getLong("id"));
                w.setTrainerId(rs.getLong("trainer_id"));
                w.setName(rs.getString("name"));
                w.setDurationMinutes(rs.getInt("duration_minutes"));
                list.add(w);
            }
        } catch (SQLException e) {
            logger.severe("Ошибка: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return list;
    }
}