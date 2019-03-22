package cz.loglim.jrest.dao;

import cz.loglim.jrest.res.Item;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ItemDaoSimple {

    // Constants (DB Credentials - these are duplicates of "persistance.xml" file configuration)
    private static final String JDBC_URL = "jdbc:mariadb://localhost:3306/api1";
    private static final String JDBC_DRIVER = "org.mariadb.jdbc.Driver";
    private static final String USER = "api1";
    private static final String PASSWORD = "api1";

    private static Connection getDbConnection() {
        Connection dbConnection;
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        try {
            dbConnection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
            return dbConnection;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private static Item getItemFromResultSet(ResultSet resultSet) {
        Item item = new Item();
        try {
            item.setId(resultSet.getLong("item_id"));
            item.setName(resultSet.getString("name"));
            item.setPrice(resultSet.getDouble("price"));
            item.setSize(resultSet.getInt("size"));
            return item;
        } catch (SQLException e) {
            return null;
        }
    }

    public static Item get(long id) {
        Item item = null;
        String sql = "select * from items where item_id = ?";
        try (Connection dbc = getDbConnection(); PreparedStatement pst = Objects.requireNonNull(dbc).prepareStatement(
                sql)) {
            pst.setLong(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                item = getItemFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return item;
    }

    public static List<Item> getAll() {
        List<Item> items = new ArrayList<>();
        String sql = "select * from items";
        try (Connection dbc = getDbConnection(); PreparedStatement pst = Objects.requireNonNull(dbc).prepareStatement(
                sql)) {
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                items.add(getItemFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return items;
    }

    public static boolean add(Item item) {
        String sql = "insert into items (name, price, size) values (?, ?, ?)";
        try (Connection dbc = getDbConnection(); PreparedStatement pst = Objects.requireNonNull(dbc).prepareStatement(
                sql)) {
            pst.setString(1, item.getName());
            pst.setDouble(2, item.getPrice());
            pst.setInt(3, item.getSize());
            pst.executeQuery();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    public static boolean update(long id, Item item) {
        String sql = "update items set name = ?, price = ?, size = ? where item_id = ?";
        try (Connection dbc = getDbConnection(); PreparedStatement pst = Objects.requireNonNull(dbc).prepareStatement(
                sql)) {
            pst.setString(1, item.getName());
            pst.setDouble(2, item.getPrice());
            pst.setInt(3, item.getSize());
            pst.setLong(4, id);
            pst.executeQuery();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    public static boolean delete(long id) {
        String sql = "delete from items where item_id = ?";
        try (Connection dbc = getDbConnection(); PreparedStatement pst = Objects.requireNonNull(dbc).prepareStatement(
                sql)) {
            pst.setLong(1, id);
            pst.executeQuery();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }
}
