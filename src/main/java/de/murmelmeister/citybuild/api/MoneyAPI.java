package de.murmelmeister.citybuild.api;

import java.math.BigDecimal;
import java.math.MathContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class MoneyAPI {

    private Connection connection;

    public MoneyAPI(Connection connection) {
        setConnection(connection);
    }

    public void createTable() {
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS moneyTable (uuid VARCHAR(36), name VARCHAR(100), " +
                    "money DECIMAL(65, 30))");
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public BigDecimal getDefaultMoney() {
        return BigDecimal.ZERO;
    }

    public boolean hasAccount(UUID uuid) {
        boolean ret = false;
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement("SELECT * FROM moneyTable WHERE uuid='" + uuid.toString() + "'");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ret = true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ret;
    }

    public void createNewAccount(UUID uuid, String name) {
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement("INSERT INTO moneyTable (uuid, name, money) " +
                    "VALUES ('" + uuid.toString() + "','" + name + "','" + this.getDefaultMoney() + "')");
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteAccount(UUID uuid) {
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement("DELETE FROM moneyTable WHERE uuid='" + uuid.toString() + "'");
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public BigDecimal getMoney(UUID uuid) {
        BigDecimal ret = getDefaultMoney();
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement("SELECT * FROM moneyTable WHERE uuid='" + uuid.toString() + "'");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ret = BigDecimal.valueOf(resultSet.getDouble("money"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public void setMoney(UUID uuid, BigDecimal amount) {
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement("UPDATE moneyTable SET money='" + amount.doubleValue() + "' WHERE uuid='" + uuid.toString() + "'");
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addMoney(UUID uuid, BigDecimal amount) {
        BigDecimal money = this.getMoney(uuid).add(amount, MathContext.DECIMAL128);
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement("UPDATE moneyTable SET money='" + money.doubleValue() + "' WHERE uuid='" + uuid.toString() + "'");
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeMoney(UUID uuid, BigDecimal amount) {
        BigDecimal money = this.getMoney(uuid).subtract(amount, MathContext.DECIMAL128);
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement("UPDATE moneyTable SET money='" + money.doubleValue() + "' WHERE uuid='" + uuid.toString() + "'");
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void resetMoney(UUID uuid) {
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement("UPDATE moneyTable SET money='" + this.getDefaultMoney().doubleValue() + "' WHERE uuid='" + uuid.toString() + "'");
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean hasEnoughMoney(UUID uuid, double money) {
        return (money <= this.getMoney(uuid).doubleValue());
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
