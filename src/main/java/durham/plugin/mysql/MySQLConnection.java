package durham.plugin.mysql;

import durham.plugin.invitation.InvitationMain;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {
    FileConfiguration config = InvitationMain.pl.getConfig();
    static Connection conn;
    static String tablePrefix;
    String url;
    String user;
    String password;
    public void createMySQL() throws SQLException {
        String host = config.getString("mysql.host","localhost");
        String port = config.getString("mysql.port","3306");
        String database = config.getString("mysql.database","minecraft");
        user = config.getString("mysql.user","root");
        password = config.getString("mysql.password","123456");
        tablePrefix = config.getString("mysql.table","Invitation");
        url = "jdbc:mysql://" + host + ":" + port + "/" + database;
        if(connect()){
            Bukkit.getServer().getConsoleSender().sendMessage(InvitationMain.prefix+"§a成功连接到数据库");
            createTable();
        }
        else{
            Bukkit.getServer().getConsoleSender().sendMessage(InvitationMain.prefix+"§c无法访问到数据库 请尝试检查配置");
            Bukkit.getServer().getConsoleSender().sendMessage(InvitationMain.prefix+"§c自动为您切换到Yaml存储模式");
            InvitationMain.mySQL = false;
        }
    }
    private boolean connect(){
        if (conn==null) {
            try {
                conn = DriverManager.getConnection(url, user, password);
            } catch (SQLException e) {
                return false;
            }
        }
        return true;
    }
    private void createTable() throws SQLException {
        conn.prepareStatement(
                "CREATE TABLE IF NOT EXISTS "+tablePrefix+"_playerdata" +
                        "(uuid varchar(36) NOT NULL,name varchar(64) NOT NULL,frequency integer NOT NULL,code varchar(20) NOT NULL,inviter varchar(32) NOT NULL)").execute();
        conn.prepareStatement(
                "CREATE TABLE IF NOT EXISTS "+tablePrefix+"_ipdata" +
                        "(status varchar(12) NOT NULL,ip varchar(32) NOT NULL)").execute();
    }
    public Connection getConn(){
        return conn;
    }
    public String getTable(){
        return tablePrefix;
    }
}
