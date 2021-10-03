package durham.plugin.utils;

import durham.plugin.data.DataTaker;
import durham.plugin.invitation.InvitationMain;
import durham.plugin.mysql.MySQLTaker;
import org.bukkit.OfflinePlayer;
import java.sql.SQLException;

public class RequestUtils {
    public String requestCode(OfflinePlayer player) throws SQLException {
        if (InvitationMain.mySQL){
            MySQLTaker mySQLTaker = new MySQLTaker();
            return mySQLTaker.getCode(player.getUniqueId());
        }
        else{
            DataTaker dataTaker = new DataTaker();
            return dataTaker.getCode(player.getName());
        }
    }
    public String requestFrequency(OfflinePlayer player) throws SQLException {
        if (InvitationMain.mySQL){
            MySQLTaker mySQLTaker = new MySQLTaker();
            return String.valueOf(mySQLTaker.getFrequency(player.getUniqueId()));
        }
        else{
            DataTaker dataTaker = new DataTaker();
            return String.valueOf(dataTaker.getFrequency(player.getName()));
        }
    }
    public String requestInviter(OfflinePlayer player) throws SQLException {
        if (InvitationMain.mySQL){
            MySQLTaker mySQLTaker = new MySQLTaker();
            return mySQLTaker.getInviter(player.getUniqueId());
        }
        else{
            DataTaker dataTaker = new DataTaker();
            return dataTaker.getInviter(player.getName());
        }
    }
}
