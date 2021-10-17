package durham.plugin.mysql;

import durham.plugin.invitation.InvitationMain;
import org.bukkit.configuration.file.FileConfiguration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {
    static String tablePrefix;
    Connection conn;
    public MySQLConnection() throws SQLException {
        FileConfiguration config = InvitationMain.pl.getConfig();
        String host = config.getString("mysql.host","localhost");
        String port = config.getString("mysql.port","3306");
        String database = config.getString("mysql.database","minecraft");
        String user = config.getString("mysql.user","root");
        String password = config.getString("mysql.password","123456");
        String url = "jdbc:mysql://" + host + ":" + port + "/" + database;
        tablePrefix = config.getString("mysql.table","Invitation");
        conn = DriverManager.getConnection(url, user, password);
    }
    public Connection getConn(){
        return conn;
    }
}
