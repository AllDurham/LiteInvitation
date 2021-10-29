package durham.plugin.utils;

import durham.plugin.data.DataTaker;
import durham.plugin.invitation.InvitationMain;
import durham.plugin.mysql.MySQLTaker;
import org.bukkit.Statistic;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class ConditionUtils {
    public static boolean CheckCondition(Player p) throws SQLException,IllegalArgumentException{
        YamlConfiguration message = YamlConfiguration.loadConfiguration(new File(InvitationMain.pl.getDataFolder(), "message.yml"));
        FileConfiguration config = InvitationMain.pl.getConfig();
        if (InvitationMain.mySQL){
            if (config.getBoolean("Anti-SmallAccount.IP.Enable")){
                MySQLTaker mySQLTaker = new MySQLTaker();
                String status = mySQLTaker.getStatus(p.getAddress().getHostName());
                if (status.equalsIgnoreCase("true")){
                    p.sendMessage(InvitationMain.prefix+message.getString("alreadySameIP")
                            .replace("&","§"));
                    return true;
                }
            }
        }
        else{
            if (config.getBoolean("Anti-SmallAccount.IP.Enable")){
                boolean status = new DataTaker().getStatus(p.getAddress().getHostName());
                if (InvitationMain.pl.getConfig().getBoolean("Anti-SmallAccount")){
                    if (status){
                        p.sendMessage(InvitationMain.prefix+message.getString("alreadySameIP")
                                .replace("&","§"));
                        return true;
                    }
                }
            }
        }
        if (config.getBoolean("Anti-SmallAccount.Time.Enable")){
            int ticks = config.getInt("Anti-SmallAccount.Time.Amount")*60*60*20;
            if (p.getStatistic(Statistic.PLAY_ONE_TICK)<ticks){
                p.sendMessage(InvitationMain.prefix+message.getString("TimeNotEnough")
                        .replace("&","§").replace("%time%",config.getString("Anti-SmallAccount.Time.Amount")));
                return true;
            }
        }
        if (config.getBoolean("Anti-SmallAccount.Kills.Enable")){
            try {
                if (p.getStatistic(Statistic.KILL_ENTITY)<config.getInt("Anti-SmallAccount.Kills.Amount")){
                    p.sendMessage(InvitationMain.prefix+message.getString("KillsNotEnough")
                            .replace("&","§").replace("%kills%",config.getString("Anti-SmallAccount.Kills.Amount")));
                    return true;
                }
            }
            catch (IllegalArgumentException e){
                p.sendMessage(InvitationMain.prefix+message.getString("KillsNotEnough")
                        .replace("&","§").replace("%kills%",config.getString("Anti-SmallAccount.Kills.Amount")));
                return true;
            }
        }
        p.sendMessage(String.valueOf(p.getLevel()));
        if (config.getBoolean("Anti-SmallAccount.Level.Enable")){
            if (p.getLevel()<config.getInt("Anti-SmallAccount.Level.Amount")){
                p.sendMessage(InvitationMain.prefix+message.getString("LevelNotEnough")
                        .replace("&","§").replace("%level%",config.getString("Anti-SmallAccount.Level.Amount")));
                return true;
            }
        }
        return false;
    }
}