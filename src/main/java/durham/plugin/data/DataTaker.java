package durham.plugin.data;

import durham.plugin.invitation.InvitationMain;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class DataTaker {
    public static int getFrequency(String name) {
        YamlConfiguration playerData = YamlConfiguration.loadConfiguration(new File(InvitationMain.pl.getDataFolder(), "PlayerData.yml"));
        return playerData.getInt(name + ".frequency");
    }
    public static String getCode(String name) {
        YamlConfiguration playerData = YamlConfiguration.loadConfiguration(new File(InvitationMain.pl.getDataFolder(), "PlayerData.yml"));
        return playerData.getString(name + ".code");
    }
    public static String getInviter(String name) {
        YamlConfiguration playerData = YamlConfiguration.loadConfiguration(new File(InvitationMain.pl.getDataFolder(), "PlayerData.yml"));
        return playerData.getString(name + ".inviter");
    }
    public static boolean getStatus(String ip) {
        YamlConfiguration ipList = YamlConfiguration.loadConfiguration(new File(InvitationMain.pl.getDataFolder(), "IPList.yml"));
        return ipList.getBoolean(ip.replace(".", "-"));
    }
}