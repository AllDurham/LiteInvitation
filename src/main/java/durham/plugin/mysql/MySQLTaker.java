package durham.plugin.mysql;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class MySQLTaker {
    public static int getFrequency(UUID uuid) throws SQLException {
        int frequency = 0;
        MySQLConnection mySQLConnection = new MySQLConnection();
        Connection conn = mySQLConnection.getConn();
        String tablePrefix = MySQLConnection.tablePrefix;
        String sql = "SELECT frequency FROM " + tablePrefix + "_playerdata WHERE uuid = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, uuid.toString());
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            frequency = rs.getInt("frequency");
        }
        rs.close();
        ps.close();
        conn.close();
        return frequency;
    }

    public static String getCode(UUID uuid) throws SQLException {
        String code = "未知错误";
        MySQLConnection mySQLConnection = new MySQLConnection();
        Connection conn = mySQLConnection.getConn();
        String tablePrefix = MySQLConnection.tablePrefix;
        String sql = "SELECT code FROM " + tablePrefix + "_playerdata WHERE uuid = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, uuid.toString());
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            code = rs.getString("code");
        }
        ps.close();
        rs.close();
        conn.close();
        return code;
    }

    public static String getInviter(String uuid) throws SQLException {
        MySQLConnection mySQLConnection = new MySQLConnection();
        Connection conn = mySQLConnection.getConn();
        String tablePrefix = MySQLConnection.tablePrefix;
        String sql = "SELECT inviter FROM " + tablePrefix + "_playerdata WHERE uuid = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, uuid);
        ResultSet rs = ps.executeQuery();
        String inviter = "";
        if (rs.next()) {
            inviter = rs.getString("inviter");
        }
        ps.close();
        rs.close();
        conn.close();
        return inviter;
    }

    public static String getName(String code) throws SQLException {
        MySQLConnection mySQLConnection = new MySQLConnection();
        Connection conn = mySQLConnection.getConn();
        String tablePrefix = MySQLConnection.tablePrefix;
        String sql = "SELECT name FROM " + tablePrefix + "_playerdata WHERE code = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, code);
        ResultSet rs = ps.executeQuery();
        String name = "";
        if (rs.next()) {
            name = rs.getString("name");
        }
        ps.close();
        rs.close();
        conn.close();
        return name;
    }

    public static String getUUID(String code) throws SQLException {
        MySQLConnection mySQLConnection = new MySQLConnection();
        Connection conn = mySQLConnection.getConn();
        String tablePrefix = MySQLConnection.tablePrefix;
        String sql = "SELECT uuid FROM " + tablePrefix + "_playerdata WHERE code = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, code);
        ResultSet rs = ps.executeQuery();
        String uuid = "";
        if (rs.next()) {
            uuid = rs.getString("uuid");
        }
        ps.close();
        rs.close();
        conn.close();
        return uuid;
    }

    public static String getUUIDByName(String name) throws SQLException {
        MySQLConnection mySQLConnection = new MySQLConnection();
        Connection conn = mySQLConnection.getConn();
        String tablePrefix = MySQLConnection.tablePrefix;
        String sql = "SELECT uuid FROM " + tablePrefix + "_playerdata WHERE name = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();
        String uuid = "";
        if (rs.next()) {
            uuid = rs.getString("uuid");
        }
        ps.close();
        rs.close();
        conn.close();
        return uuid;
    }

    public static int getNameFrequency(String name) throws SQLException {
        MySQLConnection mySQLConnection = new MySQLConnection();
        Connection conn = mySQLConnection.getConn();
        String tablePrefix = MySQLConnection.tablePrefix;
        String sql = "SELECT frequency FROM " + tablePrefix + "_playerdata WHERE name = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();
        int frequency = 0;
        if (rs.next()) {
            frequency = rs.getInt("frequency");
        }
        ps.close();
        rs.close();
        conn.close();
        return frequency;
    }

    public static boolean getStatus(String ip) throws SQLException {
        MySQLConnection mySQLConnection = new MySQLConnection();
        Connection conn = mySQLConnection.getConn();
        String tablePrefix = MySQLConnection.tablePrefix;
        String sql = "SELECT status FROM " + tablePrefix + "_ipdata WHERE ip = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, ip.replace(".", "-"));
        ResultSet rs = ps.executeQuery();
        boolean status = false;
        if (rs.next()) {
            status = rs.getBoolean("status");
        }
        ps.close();
        rs.close();
        return status;
    }

}
