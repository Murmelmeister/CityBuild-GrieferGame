package de.murmelmeister.citybuild.api;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class PlayTimeAPI {

    private Connection connection;

    public PlayTimeAPI(Connection connection) {
        setConnection(connection);
    }

    public void createTable() {
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS playtime (uuid VARCHAR(36), name VARCHAR(100), " +
                                                                                   "minutes BIGINT(100), hours BIGINT(100), days BIGINT(200), years BIGINT(255))");
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public long getDefaultMinutes() {
        return 0;
    }

    public long getDefaultHours() {
        return 0;
    }

    public long getDefaultDays() {
        return 0;
    }

    public long getDefaultYears() {
        return 0;
    }

    public boolean hasAccount(UUID uuid) {
        boolean ret = false;
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement("SELECT * FROM playtime WHERE uuid='" + uuid.toString() + "'");
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
            PreparedStatement preparedStatement = getConnection().prepareStatement("INSERT INTO playtime (uuid, name, minutes, hours, days, years) " +
                                                                                   "VALUES ('" + uuid.toString() + "','" + name + "','" + this.getDefaultMinutes() + "','" + this.getDefaultHours() + "','" + this.getDefaultDays() + "','" + this.getDefaultYears() + "')");
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteAccount(UUID uuid) {
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement("DELETE FROM playtime WHERE uuid='" + uuid.toString() + "'");
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public long getMinutes(UUID uuid) {
        long ret = getDefaultMinutes();
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement("SELECT * FROM `playtime` WHERE `uuid`='" + uuid.toString() + "'");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ret = resultSet.getLong("minutes");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public long getHours(UUID uuid) {
        long ret = getDefaultHours();
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement("SELECT * FROM `playtime` WHERE `uuid`='" + uuid.toString() + "'");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ret = resultSet.getLong("hours");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public long getDays(UUID uuid) {
        long ret = getDefaultDays();
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement("SELECT * FROM `playtime` WHERE `uuid`='" + uuid.toString() + "'");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ret = resultSet.getLong("days");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public long getYears(UUID uuid) {
        long ret = getDefaultYears();
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement("SELECT * FROM `playtime` WHERE `uuid`='" + uuid.toString() + "'");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ret = resultSet.getLong("years");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public void setMinutes(UUID uuid, long time) {
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement("UPDATE `playtime` SET `minutes`='" + time + "' WHERE `uuid`='" + uuid.toString() + "'");
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setHours(UUID uuid, long time) {
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement("UPDATE `playtime` SET `hours`='" + time + "' WHERE `uuid`='" + uuid.toString() + "'");
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setDays(UUID uuid, long time) {
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement("UPDATE `playtime` SET `days`='" + time + "' WHERE `uuid`='" + uuid.toString() + "'");
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setYears(UUID uuid, long time) {
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement("UPDATE `playtime` SET `years`='" + time + "' WHERE `uuid`='" + uuid.toString() + "'");
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addMinutes(UUID uuid) {
        long currentTime = this.getMinutes(uuid);
        currentTime++;
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement("UPDATE `playtime` SET `minutes`='" + currentTime + "' WHERE `uuid`='" + uuid.toString() + "'");
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addHours(UUID uuid) {
        long currentTime = this.getHours(uuid);
        currentTime++;
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement("UPDATE `playtime` SET `hours`='" + currentTime + "' WHERE `uuid`='" + uuid.toString() + "'");
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addDays(UUID uuid) {
        long currentTime = this.getDays(uuid);
        currentTime++;
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement("UPDATE `playtime` SET `days`='" + currentTime + "' WHERE `uuid`='" + uuid.toString() + "'");
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addYears(UUID uuid) {
        long currentTime = this.getYears(uuid);
        currentTime++;
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement("UPDATE `playtime` SET `years`='" + currentTime + "' WHERE `uuid`='" + uuid.toString() + "'");
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

}