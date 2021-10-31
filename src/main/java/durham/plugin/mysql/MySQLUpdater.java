package durham.plugin.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class MySQLUpdater {
    public void newPlayerData(UUID uuid, String name, String code) throws SQLException {
        MySQLConnection mySQLConnection = new MySQLConnection();
        Connection conn = mySQLConnection.getConn();
        String tablePrefix = MySQLConnection.tablePrefix;
        String sql=String.format("INSERT INTO %s_playerdata(`uuid`, `name`, `frequency`, `code`, `inviter`) VALUES('%s', '%s', '%s', '%s', 'none')",tablePrefix,uuid,name,0,code);
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.execute();
        ps.close();
    }
    public void addFrequency(UUID uuid, int frequency) throws SQLException {
        MySQLConnection mySQLConnection = new MySQLConnection();
        Connection conn = mySQLConnection.getConn();
        String tablePrefix = MySQLConnection.tablePrefix;
        MySQLTaker mySQLTaker = new MySQLTaker();
        int value = mySQLTaker.getFrequency(uuid);
        String sql = String.format("UPDATE %s_playerdata SET frequency='%s' WHERE uuid='%s';",tablePrefix,value+frequency,uuid);
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.execute();
        ps.close();
    }
    public void setInviter(UUID uuid, String inviterName) throws SQLException {
        MySQLConnection mySQLConnection = new MySQLConnection();
        Connection conn = mySQLConnection.getConn();
        String tablePrefix = MySQLConnection.tablePrefix;
        String sql = String.format("UPDATE %s_playerdata SET inviter='%s' WHERE uuid='%s';",tablePrefix,inviterName,uuid);
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.execute();
        ps.close();
    }
    public void newIPData(String ip) throws SQLException {
        MySQLConnection mySQLConnection = new MySQLConnection();
        Connection conn = mySQLConnection.getConn();
        String tablePrefix = MySQLConnection.tablePrefix;
        String sql=String.format("INSERT INTO %s_ipdata(`status`,`ip`) VALUES('false', '%s')",tablePrefix,ip.replace(".","-"));
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.execute();
        ps.close();
    }
    public void setStatus(String ip,boolean status) throws SQLException {
        MySQLConnection mySQLConnection = new MySQLConnection();
        Connection conn = mySQLConnection.getConn();
        String tablePrefix = MySQLConnection.tablePrefix;
        String sql = String.format("UPDATE %s_ipdata SET status='%s' WHERE ip='%s';",tablePrefix,status,ip.replace(".","-"));
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.execute();
        ps.close();
    }
    public void resetPlayerData(String clearName) throws SQLException {
        MySQLConnection mySQLConnection = new MySQLConnection();
        Connection conn = mySQLConnection.getConn();
        String tablePrefix = MySQLConnection.tablePrefix;
        String sql=String.format("UPDATE %s_playerdata SET inviter=?,frequency=? WHERE name='%s';",tablePrefix,clearName);
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1,"none");
        ps.setInt(2,0);
        ps.execute();
        ps.close();
    }
}
