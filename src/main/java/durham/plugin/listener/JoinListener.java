package durham.plugin.listener;

import durham.plugin.data.DataChecker;
import durham.plugin.data.DataUpdater;
import durham.plugin.invitation.InvitationMain;
import durham.plugin.mysql.MySQLChecker;
import durham.plugin.mysql.MySQLUpdater;
import durham.plugin.utils.RandomUtil;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class JoinListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent e) throws IOException, SQLException {
        Player p = e.getPlayer();
        if (InvitationMain.mySQL) {
            MySQLChecker mySQLChecker = new MySQLChecker();
            if (!mySQLChecker.ifContainsUUID(p.getUniqueId())) {
                MySQLUpdater mySQLUpdater = new MySQLUpdater();
                String code = RandomUtil.createCode();
                mySQLUpdater.newPlayerData(p.getUniqueId(),p.getName(),code);
                tellPlayer(p,code);
            }
            if (!mySQLChecker.ifContainsIP(Objects.requireNonNull(p.getAddress()).getAddress().getHostAddress())){
                new MySQLUpdater().newIPData(p.getAddress().getAddress().getHostAddress());
            }
        }
        else{
            DataChecker dataChecker = new DataChecker();
            if (!DataChecker.ifContainsPlayer(p.getUniqueId())){
                DataUpdater dataUpdater = new DataUpdater();
                String code = RandomUtil.createCode();
                dataUpdater.newPlayerData(p,code);
                tellPlayer(p,code);
            }
            if (!dataChecker.ifContainsIP(Objects.requireNonNull(p.getAddress()).getAddress().getHostAddress())){
                new DataUpdater().newIPData(p.getAddress().getAddress().getHostAddress());
            }
        }
    }
    public void tellPlayer(Player p,String code){
        YamlConfiguration message = YamlConfiguration.loadConfiguration(new File(InvitationMain.pl.getDataFolder(), "message.yml"));
        p.sendMessage(InvitationMain.prefix+ Objects.requireNonNull(message.getString("Getting-Code"))
                .replace("&","§").replace("%code%",code).replace("%player%",p.getName()));
    }
}
