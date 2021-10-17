package durham.plugin.mysql;

import durham.plugin.invitation.InvitationMain;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.SQLException;

public class MySQLHelper {
    public static void MySQLInstalled() throws SQLException {
        Connection conn = new MySQLConnection().getConn();
        if(conn!=null){
            Bukkit.getServer().getConsoleSender().sendMessage(InvitationMain.prefix+"§a成功连接到数据库");
            conn.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS "+MySQLConnection.tablePrefix+"_playerdata" +
                            "(uuid varchar(36) NOT NULL,name varchar(64) NOT NULL,frequency integer NOT NULL,code varchar(20) NOT NULL,inviter varchar(32) NOT NULL)").execute();
            conn.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS "+MySQLConnection.tablePrefix+"_ipdata" +
                            "(status varchar(12) NOT NULL,ip varchar(32) NOT NULL)").execute();
        }
        else{
            Bukkit.getServer().getConsoleSender().sendMessage(InvitationMain.prefix+"§c无法访问到数据库 请尝试检查配置");
            Bukkit.getServer().getConsoleSender().sendMessage(InvitationMain.prefix+"§c自动为您切换到Yaml存储模式");
            InvitationMain.mySQL = false;
        }
    }
}
