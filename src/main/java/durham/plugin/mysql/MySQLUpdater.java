package durham.plugin.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class MySQLUpdater {
    public static void newPlayerData(UUID uuid, String name, String code) throws SQLException {
        MySQLConnection mySQLConnection = new MySQLConnection();
        Connection conn = mySQLConnection.getConn();
        String tablePrefix = MySQLConnection.tablePrefix;
        String sql = "INSERT INTO " + tablePrefix + "_playerdata(`uuid`, `name`, `frequency`, `code`, `inviter`) VALUES(?, ?, 0, ?, '')";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, uuid.toString());
        ps.setString(2, name);
        ps.setString(3, code);
        ps.executeUpdate();
        ps.close();
        conn.close();
    }

    public static void addFrequency(UUID uuid, int frequency) throws SQLException {
        MySQLConnection mySQLConnection = new MySQLConnection();
        Connection conn = mySQLConnection.getConn();
        String tablePrefix = MySQLConnection.tablePrefix;
        int value = MySQLTaker.getFrequency(uuid);
        String sql = "UPDATE " + tablePrefix + "_playerdata SET frequency=? WHERE uuid=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, value + frequency);
        ps.setString(2, uuid.toString());
        ps.executeUpdate();
        ps.close();
        conn.close();
    }

    public static void setInviter(UUID uuid, String inviterName) throws SQLException {
        MySQLConnection mySQLConnection = new MySQLConnection();
        Connection conn = mySQLConnection.getConn();
        String tablePrefix = MySQLConnection.tablePrefix;
        String sql = "UPDATE " + tablePrefix + "_playerdata SET inviter=? WHERE uuid=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, inviterName);
        ps.setString(2, uuid.toString());
        ps.executeUpdate();
        ps.close();
        conn.close();
    }

    public static void newIPData(String ip) throws SQLException {
        MySQLConnection mySQLConnection = new MySQLConnection();
        Connection conn = mySQLConnection.getConn();
        String tablePrefix = MySQLConnection.tablePrefix;
        String sql = "INSERT INTO " + tablePrefix + "_ipdata(`status`, `ip`) VALUES('false', ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, ip.replace(".", "-"));
        ps.executeUpdate();
        ps.close();
        conn.close();
    }

    public static void setStatus(String ip, boolean status) throws SQLException {
        MySQLConnection mySQLConnection = new MySQLConnection();
        Connection conn = mySQLConnection.getConn();
        String tablePrefix = MySQLConnection.tablePrefix;
        String sql = "UPDATE " + tablePrefix + "_ipdata SET status=? WHERE ip=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setBoolean(1, status);
        ps.setString(2, ip.replace(".", "-"));
        ps.executeUpdate();
        ps.close();
        conn.close();
    }

    public static void resetPlayerData(String clearName) throws SQLException {
        MySQLConnection mySQLConnection = new MySQLConnection();
        Connection conn = mySQLConnection.getConn();
        String tablePrefix = MySQLConnection.tablePrefix;
        String sql = "UPDATE " + tablePrefix + "_playerdata SET inviter='', frequency=0 WHERE name=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, clearName);
        ps.executeUpdate();
        ps.close();
        conn.close();
    }
}
