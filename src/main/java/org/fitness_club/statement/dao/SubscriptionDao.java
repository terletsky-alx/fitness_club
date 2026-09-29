package org.fitness_club.statement.dao;

import org.fitness_club.connection.ConnectionManager;
import org.fitness_club.statement.model.Subscription;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class SubscriptionDao implements StatementDao<Subscription> {
    private static final Logger logger = Logger.getLogger(SubscriptionDao.class.getName());

    @Override
    public void clearTable() {
        try (Statement stmt = ConnectionManager.getConnection().createStatement()) { stmt.executeUpdate("DELETE FROM subscriptions"); }
        catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public void insert(Subscription sub) {
        try (Statement stmt = ConnectionManager.getConnection().createStatement()) {
            stmt.executeUpdate("INSERT INTO subscriptions (client_id, type, start_date, end_date) " +
                    "VALUES (" + sub.getClientId() + ", '" + sub.getType() + "', '" + sub.getStartDate() + "', '" + sub.getEndDate() + "')");
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public int getCount() {
        int count = 0;
        try (Statement stmt = ConnectionManager.getConnection().createStatement(); ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM subscriptions")) {
            if (rs.next()) count = rs.getInt(1);
        } catch (SQLException e) { throw new RuntimeException(e); }
        return count;
    }

    @Override
    public List<Subscription> getAll() {
        List<Subscription> list = new ArrayList<>();
        try (Statement stmt = ConnectionManager.getConnection().createStatement(); ResultSet rs = stmt.executeQuery("SELECT * FROM subscriptions")) {
            while (rs.next()) {
                Subscription s = new Subscription();
                s.setId(rs.getLong("id")); s.setClientId(rs.getLong("client_id")); s.setType(rs.getString("type"));
                s.setStartDate(rs.getDate("start_date").toLocalDate()); s.setEndDate(rs.getDate("end_date").toLocalDate());
                list.add(s);
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return list;
    }

    @Override
    public Subscription getById(Long id) {
        Subscription sub = null;
        try (Statement stmt = ConnectionManager.getConnection().createStatement(); ResultSet rs = stmt.executeQuery("SELECT * FROM subscriptions WHERE id = " + id)) {
            if (rs.next()) {
                sub = new Subscription();
                sub.setId(rs.getLong("id")); sub.setClientId(rs.getLong("client_id")); sub.setType(rs.getString("type"));
                sub.setStartDate(rs.getDate("start_date").toLocalDate()); sub.setEndDate(rs.getDate("end_date").toLocalDate());
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return sub;
    }
}