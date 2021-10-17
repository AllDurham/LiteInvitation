package durham.plugin.mysql;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class MySQLTaker {
    public int getFrequency(UUID uuid) throws SQLException {
        MySQLConnection mySQLConnection = new MySQLConnection();
        Connection conn = mySQLConnection.getConn();
        String tablePrefix = MySQLConnection.tablePrefix;
        String sql=String.format("SELECT * FROM `%s_playerdata` WHERE `uuid` = '%s'",tablePrefix,uuid);
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        int Frequency = 114514;
        if(rs.next()) {
            Frequency = rs.getInt("frequency");}
        ps.close();
        rs.close();
        return Frequency;
    }
    public String getCode(UUID uuid) throws SQLException {
        MySQLConnection mySQLConnection = new MySQLConnection();
        Connection conn = mySQLConnection.getConn();
        String tablePrefix = MySQLConnection.tablePrefix;
        String sql=String.format("SELECT * FROM `%s_playerdata` WHERE `uuid` = '%s'",tablePrefix,uuid);
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        String code = "未知错误";
        if(rs.next()) {
            code = rs.getString("code");}
        ps.close();
        rs.close();
        return code;
    }
    public String getInviter(UUID uuid) throws SQLException {
        MySQLConnection mySQLConnection = new MySQLConnection();
        Connection conn = mySQLConnection.getConn();
        String tablePrefix = MySQLConnection.tablePrefix;
        String sql=String.format("SELECT * FROM `%s_playerdata` WHERE `uuid` = '%s'",tablePrefix,uuid);
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        String inviter = "未知错误";
        if(rs.next()) {
            inviter = rs.getString("inviter");}
        ps.close();
        rs.close();
        return inviter;
    }
    public String getName(String code) throws SQLException {
        MySQLConnection mySQLConnection = new MySQLConnection();
        Connection conn = mySQLConnection.getConn();
        String tablePrefix = MySQLConnection.tablePrefix;
        String sql=String.format("SELECT * FROM `%s_playerdata` WHERE `code` = '%s'",tablePrefix,code);
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        String name = "未知错误";
        if(rs.next()) {
            name = rs.getString("name");}
        ps.close();
        rs.close();
        return name;
    }
    public String getUUID(String code) throws SQLException {
        MySQLConnection mySQLConnection = new MySQLConnection();
        Connection conn = mySQLConnection.getConn();
        String tablePrefix = MySQLConnection.tablePrefix;
        String sql=String.format("SELECT * FROM `%s_playerdata` WHERE `code` = '%s'",tablePrefix,code);
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        String uuid = null;
        if(rs.next()) {
            uuid = rs.getString("uuid");}
        ps.close();
        rs.close();
        return uuid;
    }
    public String getNameUUID(String name) throws SQLException {
        MySQLConnection mySQLConnection = new MySQLConnection();
        Connection conn = mySQLConnection.getConn();
        String tablePrefix = MySQLConnection.tablePrefix;
        String sql=String.format("SELECT * FROM `%s_playerdata` WHERE `name` = '%s'",tablePrefix,name);
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        String uuid = null;
        if(rs.next()) {
            uuid = rs.getString("uuid");}
        ps.close();
        rs.close();
        return uuid;
    }
    public int getNameFrequency(String name) throws SQLException {
        MySQLConnection mySQLConnection = new MySQLConnection();
        Connection conn = mySQLConnection.getConn();
        String tablePrefix = MySQLConnection.tablePrefix;
        String sql=String.format("SELECT * FROM `%s_playerdata` WHERE `name` = '%s'",tablePrefix,name);
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        int Frequency = 114514;
        if(rs.next()) {
            Frequency = rs.getInt("frequency");}
        ps.close();
        rs.close();
        return Frequency;
    }
    public String getStatus(String ip) throws SQLException {
        MySQLConnection mySQLConnection = new MySQLConnection();
        Connection conn = mySQLConnection.getConn();
        String tablePrefix = MySQLConnection.tablePrefix;
        String sql=String.format("SELECT * FROM `%s_ipdata` WHERE `ip` = '%s'",tablePrefix, ip.replace(".","-"));
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        String status = "未知错误";
        if(rs.next()) {
            status = rs.getString("status");}
        ps.close();
        rs.close();
        return status;
    }
}
