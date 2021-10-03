package durham.plugin.mysql;

import durham.plugin.invitation.InvitationMain;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class MySQLUpdater {
    public void newPlayerData(UUID uuid, String name, String code) throws SQLException {
        MySQLConnection mySQLConnection = new MySQLConnection();
        Connection conn = mySQLConnection.getConn();
        String tablePrefix = mySQLConnection.getTable();
        String sql=String.format("INSERT INTO %s_playerdata(`uuid`, `name`, `frequency`, `code`, `inviter`) VALUES('%s', '%s', '%s', '%s', '无')",tablePrefix,uuid,name,0,code);
        if (conn==null){
            Bukkit.getServer().getConsoleSender().sendMessage(InvitationMain.prefix+"§cNull:创建失败新玩家数据！！无法连接到数据库");
            Bukkit.getServer().getConsoleSender().sendMessage(InvitationMain.prefix+"§cSQL:§a"+sql);
        }
        else{
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.execute();
            ps.close();
        }
    }
    public void addFrequency(UUID uuid, int frequency) throws SQLException {
        MySQLConnection mySQLConnection = new MySQLConnection();
        Connection conn = mySQLConnection.getConn();
        String tablePrefix = mySQLConnection.getTable();
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
        String tablePrefix = mySQLConnection.getTable();
        String sql = String.format("UPDATE %s_playerdata SET inviter='%s' WHERE uuid='%s';",tablePrefix,inviterName,uuid);
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.execute();
        ps.close();
    }
    public void resetPlayerData(String clearName) throws SQLException {
        MySQLConnection mySQLConnection = new MySQLConnection();
        Connection conn = mySQLConnection.getConn();
        String tablePrefix = mySQLConnection.getTable();
        String sql=String.format("UPDATE %s_playerdata SET inviter=?,frequency=? WHERE name='%s';",tablePrefix,clearName);
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1,"无");
        ps.setInt(2,0);
        ps.execute();
        ps.close();
    }
}
