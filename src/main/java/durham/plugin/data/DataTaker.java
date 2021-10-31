package durham.plugin.data;

import durham.plugin.invitation.InvitationMain;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class DataTaker {
    public int getFrequency(String name) {
        YamlConfiguration playerData = YamlConfiguration.loadConfiguration(new File(InvitationMain.pl.getDataFolder(), "PlayerData.yml"));
        return playerData.getInt(name+".frequency");
    }
    public String getCode(String name) {
        YamlConfiguration playerData = YamlConfiguration.loadConfiguration(new File(InvitationMain.pl.getDataFolder(), "PlayerData.yml"));
        return playerData.getString(name+".code");
    }
    public String getInviter(String name) {
        YamlConfiguration playerData = YamlConfiguration.loadConfiguration(new File(InvitationMain.pl.getDataFolder(), "PlayerData.yml"));
        if (playerData.getString(name+".inviter").equals("none"))return "无";
        return playerData.getString(name+".inviter");
    }
    public boolean getStatus(String ip) {
        YamlConfiguration ipList = YamlConfiguration.loadConfiguration(new File(InvitationMain.pl.getDataFolder(), "IPList.yml"));
        return ipList.getBoolean(ip.replace(".","-"));
    }
}
