package durham.plugin.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class MySQLChecker {
    public static boolean ifContainsCode(String code) throws SQLException {
        MySQLConnection mySQLConnection = new MySQLConnection();
        Connection conn = mySQLConnection.getConn();
        String tablePrefix = MySQLConnection.tablePrefix;
        String sql = "SELECT name FROM " + tablePrefix + "_playerdata WHERE code = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, code);
        ResultSet rs = ps.executeQuery();
        boolean result = rs.next();
        ps.close();
        rs.close();
        conn.close();
        return result;
    }
    public static boolean ifContainsUUID(UUID uuid) throws SQLException {
        MySQLConnection mySQLConnection = new MySQLConnection();
        Connection conn = mySQLConnection.getConn();
        String tablePrefix = MySQLConnection.tablePrefix;
        String sql = "SELECT name FROM " + tablePrefix + "_playerdata WHERE uuid = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, uuid.toString());
        ResultSet rs = ps.executeQuery();
        boolean result = rs.next();
        ps.close();
        rs.close();
        conn.close();
        return result;
    }
    public static boolean ifContainsPlayerName(String checkName) throws SQLException {
        MySQLConnection mySQLConnection = new MySQLConnection();
        Connection conn = mySQLConnection.getConn();
        String tablePrefix = MySQLConnection.tablePrefix;
        String sql = "SELECT uuid FROM " + tablePrefix + "_playerdata WHERE name = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, checkName);
        ResultSet rs = ps.executeQuery();
        boolean result = rs.next();
        ps.close();
        rs.close();
        conn.close();
        return result;
    }
    public static boolean ifContainsIP(String ip) throws SQLException {
        MySQLConnection mySQLConnection = new MySQLConnection();
        Connection conn = mySQLConnection.getConn();
        String tablePrefix = MySQLConnection.tablePrefix;
        String sql = "SELECT * FROM " + tablePrefix + "_ipdata WHERE ip = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, ip.replace(".", "-"));
        ResultSet rs = ps.executeQuery();
        boolean result = rs.next();
        ps.close();
        rs.close();
        conn.close();
        return result;
    }
}
