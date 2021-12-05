package durham.plugin.utils;

import durham.plugin.data.DataTaker;
import durham.plugin.invitation.InvitationMain;
import durham.plugin.mysql.MySQLTaker;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.sql.SQLException;
import java.util.Objects;

public class ConditionUtils {
    private static Statistic time;
    static{
        for (Statistic statistic:Statistic.values()){
            if (statistic.toString().equals("PLAY_ONE_MINUTE")|statistic.toString().equals("PLAY_ONE_TICK")){
                time = statistic;
                break;
            }
        }
        if (time==null){
            Bukkit.getServer().getConsoleSender().sendMessage(InvitationMain.prefix+"§c枚举获取异常");
        }
    }
    public static boolean CheckCondition(Player p) throws SQLException,IllegalArgumentException{
        YamlConfiguration message = YamlConfiguration.loadConfiguration(new File(InvitationMain.pl.getDataFolder(), "message.yml"));
        FileConfiguration config = InvitationMain.pl.getConfig();
        if (InvitationMain.mySQL){
            if (config.getBoolean("Anti-SmallAccount.IP.Enable")){
                MySQLTaker mySQLTaker = new MySQLTaker();
                String status = mySQLTaker.getStatus(Objects.requireNonNull(p.getAddress()).getAddress().getHostAddress());
                if (status.equalsIgnoreCase("true")){
                    p.sendMessage(InvitationMain.prefix+ Objects.requireNonNull(message.getString("alreadySameIP"))
                            .replace("&","§"));
                    return true;
                }
            }
        }
        else{
            if (config.getBoolean("Anti-SmallAccount.IP.Enable")){
                boolean status = new DataTaker().getStatus(Objects.requireNonNull(p.getAddress()).getAddress().getHostAddress());
                if (InvitationMain.pl.getConfig().getBoolean("Anti-SmallAccount")){
                    if (status){
                        p.sendMessage(InvitationMain.prefix+ Objects.requireNonNull(message.getString("alreadySameIP"))
                                .replace("&","§"));
                        return true;
                    }
                }
            }
        }
        if (config.getBoolean("Anti-SmallAccount.Time.Enable")){
            int ticks = config.getInt("Anti-SmallAccount.Time.Amount")*60*60*20;
            if (p.getStatistic(time)<ticks){
                p.sendMessage(InvitationMain.prefix+ Objects.requireNonNull(message.getString("TimeNotEnough"))
                        .replace("&","§").replace("%time%", Objects.requireNonNull(config.getString("Anti-SmallAccount.Time.Amount"))));
                return true;
            }
        }
        if (config.getBoolean("Anti-SmallAccount.Level.Enable")){
            if (p.getLevel()<config.getInt("Anti-SmallAccount.Level.Amount")){
                p.sendMessage(InvitationMain.prefix+ Objects.requireNonNull(message.getString("LevelNotEnough"))
                        .replace("&","§").replace("%level%", Objects.requireNonNull(config.getString("Anti-SmallAccount.Level.Amount"))));
                return true;
            }
        }
        return false;
    }
}