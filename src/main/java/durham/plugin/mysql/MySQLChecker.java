package durham.plugin.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class MySQLChecker {
    public boolean ifContainsCode(String code) throws SQLException {
        MySQLConnection mySQLConnection = new MySQLConnection();
        Connection conn = mySQLConnection.getConn();
        String tablePrefix = mySQLConnection.getTable();
        String sql=String.format("SELECT * FROM `%s_playerdata` WHERE `code` = '%s'",tablePrefix,code);
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }
    public boolean ifContainsUUID(UUID uuid) throws SQLException {
        MySQLConnection mySQLConnection = new MySQLConnection();
        Connection conn = mySQLConnection.getConn();
        String tablePrefix = mySQLConnection.getTable();
        String sql=String.format("SELECT * FROM `%s_playerdata` WHERE `uuid` = '%s'",tablePrefix,uuid);
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }
    public boolean ifContainsPlayerName(String checkName) throws SQLException {
        MySQLConnection mySQLConnection = new MySQLConnection();
        Connection conn = mySQLConnection.getConn();
        String tablePrefix = mySQLConnection.getTable();
        String sql=String.format("SELECT * FROM `%s_playerdata` WHERE `name` = '%s'",tablePrefix,checkName);
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }
    public boolean ifContainsIP(String ip) throws SQLException {
        MySQLConnection mySQLConnection = new MySQLConnection();
        Connection conn = mySQLConnection.getConn();
        String tablePrefix = mySQLConnection.getTable();
        String sql=String.format("SELECT * FROM `%s_ipdata` WHERE `ip` = '%s'",tablePrefix,ip.replace(".","-"));
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }
}
