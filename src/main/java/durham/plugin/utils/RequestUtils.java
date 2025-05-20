package durham.plugin.utils;

import durham.plugin.data.DataTaker;
import durham.plugin.invitation.InvitationMain;
import durham.plugin.mysql.MySQLTaker;
import org.bukkit.OfflinePlayer;
import java.sql.SQLException;

public class RequestUtils {
    public static String requestCode(OfflinePlayer player) throws SQLException {
        if (InvitationMain.mySQL) {
            return MySQLTaker.getCode(player.getUniqueId());
        }
        return DataTaker.getCode(player.getName());
    }

    public static String requestFrequency(OfflinePlayer player) throws SQLException {
        if (InvitationMain.mySQL) {
            return String.valueOf(MySQLTaker.getFrequency(player.getUniqueId()));
        }
        return String.valueOf(DataTaker.getFrequency(player.getName()));
    }

    public static String requestInviter(OfflinePlayer player) throws SQLException {
        if (InvitationMain.mySQL) {
            return MySQLTaker.getInviter(player.getUniqueId().toString());
        }
        return DataTaker.getInviter(player.getName());
    }

}
