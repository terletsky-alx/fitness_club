package org.fitness_club.statement.dao;

import org.fitness_club.connection.ConnectionManager;
import org.fitness_club.statement.model.Trainer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class TrainerDao implements StatementDao<Trainer> {
    private static final Logger logger = Logger.getLogger(TrainerDao.class.getName());

    @Override
    public void clearTable() {
        try (Statement stmt = ConnectionManager.getConnection().createStatement()) {
            stmt.executeUpdate("DELETE FROM trainers");
        } catch (SQLException e) {
            logger.severe("Ошибка: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insert(Trainer trainer) {
        try (Statement stmt = ConnectionManager.getConnection().createStatement()) {
            stmt.executeUpdate("INSERT INTO trainers (first_name, last_name, specialization) " +
                    "VALUES ('" + trainer.getFirstName() + "', '" + trainer.getLastName() + "', '" + trainer.getSpecialization() + "')");
        } catch (SQLException e) {
            logger.severe("Ошибка: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getCount() {
        int count = 0;
        try (Statement stmt = ConnectionManager.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM trainers")) {
            if (rs.next()) count = rs.getInt(1);
        } catch (SQLException e) {
            logger.severe("Ошибка: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return count;
    }

    @Override
    public List<Trainer> getAll() {
        List<Trainer> list = new ArrayList<>();
        try (Statement stmt = ConnectionManager.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM trainers")) {
            while (rs.next()) {
                Trainer t = new Trainer();
                t.setId(rs.getLong("id"));
                t.setFirstName(rs.getString("first_name"));
                t.setLastName(rs.getString("last_name"));
                t.setSpecialization(rs.getString("specialization"));
                list.add(t);
            }
        } catch (SQLException e) {
            logger.severe("Ошибка: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public Trainer getById(Long id) {
        Trainer trainer = null;
        try (Statement stmt = ConnectionManager.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM trainers WHERE id = " + id)) {
            if (rs.next()) {
                trainer = new Trainer();
                trainer.setId(rs.getLong("id"));
                trainer.setFirstName(rs.getString("first_name"));
                trainer.setLastName(rs.getString("last_name"));
                trainer.setSpecialization(rs.getString("specialization"));
            }
        } catch (SQLException e) {
            logger.severe("Ошибка: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return trainer;
    }

    // ЗАДАНИЕ 2: Сортировка тренеров по имени и фамилии
    public List<Trainer> getAllSortedByName() {
        List<Trainer> list = new ArrayList<>();
        try (Statement stmt = ConnectionManager.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM trainers ORDER BY first_name, last_name")) {
            while (rs.next()) {
                Trainer t = new Trainer();
                t.setId(rs.getLong("id"));
                t.setFirstName(rs.getString("first_name"));
                t.setLastName(rs.getString("last_name"));
                t.setSpecialization(rs.getString("specialization"));
                list.add(t);
            }
        } catch (SQLException e) {
            logger.severe("Ошибка: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return list;
    }

    // ЗАДАНИЕ 7: Подсчёт тренировок у каждого тренера (с подзапросом)
    public void printTrainersWithWorkoutCount() {
        try (Statement stmt = ConnectionManager.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT first_name, last_name, specialization, " +
                             "(SELECT COUNT(*) FROM workouts WHERE trainer_id = trainers.id) AS workout_count " +
                             "FROM trainers")) {
            System.out.println("\n[Количество тренировок у тренеров]");
            while (rs.next()) {
                System.out.println(rs.getString("first_name") + " " + rs.getString("last_name") +
                        " (" + rs.getString("specialization") + ") - тренировок: " +
                        rs.getInt("workout_count"));
            }
        } catch (SQLException e) {
            logger.severe("Ошибка: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}