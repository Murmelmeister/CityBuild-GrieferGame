package de.murmelmeister.citybuild.api;

import java.math.BigDecimal;
import java.math.MathContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class BankAPI {

    private Connection connection;

    public BankAPI(Connection connection) {
        setConnection(connection);
    }

    public void createTable() {
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS bankTable (uuid VARCHAR(36), name VARCHAR(100), " +
                    "bankMoney DECIMAL(65, 30))");
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public BigDecimal getDefaultBankMoney() {
        return BigDecimal.ZERO;
    }

    public boolean hasAccount(UUID uuid) {
        boolean ret = false;
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement("SELECT * FROM bankTable WHERE uuid='" + uuid.toString() + "'");
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
            PreparedStatement preparedStatement = getConnection().prepareStatement("INSERT INTO bankTable (uuid, name, bankMoney) " +
                    "VALUES ('" + uuid.toString() + "','" + name + "','" + this.getDefaultBankMoney() + "')");
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteAccount(UUID uuid) {
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement("DELETE FROM bankTable WHERE uuid='" + uuid.toString() + "'");
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public BigDecimal getBankMoney(UUID uuid) {
        BigDecimal ret = getDefaultBankMoney();
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement("SELECT * FROM `bankTable` WHERE `uuid`='" + uuid.toString() + "'");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ret = BigDecimal.valueOf(resultSet.getDouble("bankMoney"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public void setBankMoney(UUID uuid, BigDecimal amount) {
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement("UPDATE `bankTable` SET `bankMoney`='" + amount.doubleValue() + "' WHERE `uuid`='" + uuid.toString() + "'");
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addBankMoney(UUID uuid, BigDecimal amount) {
        BigDecimal bankMoney = this.getBankMoney(uuid).add(amount, MathContext.DECIMAL128);
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement("UPDATE `bankTable` SET `bankMoney`='" + bankMoney.doubleValue() + "' WHERE `uuid`='" + uuid.toString() + "'");
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeBankMoney(UUID uuid, BigDecimal amount) {
        BigDecimal bankMoney = this.getBankMoney(uuid).subtract(amount, MathContext.DECIMAL128);
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement("UPDATE `bankTable` SET `bankMoney`='" + bankMoney.doubleValue() + "' WHERE `uuid`='" + uuid.toString() + "'");
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void resetBankMoney(UUID uuid) {
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement("UPDATE `bankTable` SET `bankMoney`='" + this.getDefaultBankMoney().doubleValue() + "' WHERE `uuid`='" + uuid.toString() + "'");
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean hasEnoughBankMoney(UUID uuid, double money) {
        return (money <= this.getBankMoney(uuid).doubleValue());
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
