package org.fitness_club.prepared_statement.dao;

import org.fitness_club.connection.ConnectionManager;
import org.fitness_club.prepared_statement.model.Equipment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class EquipmentDao {
    private static final Logger logger = Logger.getLogger(EquipmentDao.class.getName());

    // Операция 1: Вставка нового оборудования
    public void insert(Equipment eq) {
        String sql = "INSERT INTO equipment (name, type, manufacturer, purchase_year) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pStmt = ConnectionManager.getConnection().prepareStatement(sql)) {
            pStmt.setString(1, eq.getName());
            pStmt.setString(2, eq.getType());
            pStmt.setString(3, eq.getManufacturer());
            pStmt.setInt(4, eq.getPurchaseYear());
            pStmt.executeUpdate();
        } catch (SQLException e) {
            logger.severe("Ошибка при вставке оборудования: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    // Операция 2: Получить всё оборудование
    public List<Equipment> getAll() {
        List<Equipment> list = new ArrayList<>();
        String sql = "SELECT * FROM equipment";
        try (PreparedStatement pStmt = ConnectionManager.getConnection().prepareStatement(sql);
             ResultSet rs = pStmt.executeQuery()) {
            while (rs.next()) {
                list.add(mapEquipment(rs));
            }
        } catch (SQLException e) {
            logger.severe("Ошибка при получении оборудования: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return list;
    }

    // Операция 3: Найти оборудование по типу
    public List<Equipment> getByType(String type) {
        List<Equipment> list = new ArrayList<>();
        String sql = "SELECT * FROM equipment WHERE type LIKE ?";
        try (PreparedStatement pStmt = ConnectionManager.getConnection().prepareStatement(sql)) {
            pStmt.setString(1, type + "%");
            ResultSet rs = pStmt.executeQuery();
            while (rs.next()) {
                list.add(mapEquipment(rs));
            }
        } catch (SQLException e) {
            logger.severe("Ошибка при поиске оборудования: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return list;
    }

    // Операция 4: Удалить оборудование по id
    public void delete(Long id) {
        String sql = "DELETE FROM equipment WHERE id = ?";
        try (PreparedStatement pStmt = ConnectionManager.getConnection().prepareStatement(sql)) {
            pStmt.setLong(1, id);
            pStmt.executeUpdate();
        } catch (SQLException e) {
            logger.severe("Ошибка при удалении оборудования: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    // Вспомогательный метод: преобразует ResultSet в объект Equipment
    private Equipment mapEquipment(ResultSet rs) throws SQLException {
        Equipment eq = new Equipment();
        eq.setId(rs.getLong("id"));
        eq.setName(rs.getString("name"));
        eq.setType(rs.getString("type"));
        eq.setManufacturer(rs.getString("manufacturer"));
        eq.setPurchaseYear(rs.getInt("purchase_year"));
        return eq;
    }
}