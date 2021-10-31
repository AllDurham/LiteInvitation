package durham.plugin.data;

import durham.plugin.invitation.InvitationMain;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class DataUpdater {
    public void newPlayerData(Player p, String code) throws IOException {
        YamlConfiguration allCodes = YamlConfiguration.loadConfiguration(new File(InvitationMain.pl.getDataFolder(), "AllCodes.yml"));
        YamlConfiguration playerData = YamlConfiguration.loadConfiguration(new File(InvitationMain.pl.getDataFolder(), "PlayerData.yml"));
        List<String> codesList = allCodes.getStringList("AllCodes");
        codesList.add(p.getUniqueId().toString());
        allCodes.set("AllCodes",codesList);
        allCodes.set("CodeList."+code,p.getName());
        playerData.set(p.getName()+".frequency",0);
        playerData.set(p.getName()+".code",code);
        playerData.set(p.getName()+".inviter","none");
        allCodes.save(new File(InvitationMain.pl.getDataFolder(), "AllCodes.yml"));
        playerData.save(new File(InvitationMain.pl.getDataFolder(), "PlayerData.yml"));
    }
    public void newIPData(String ip) throws IOException {
        YamlConfiguration ipList = YamlConfiguration.loadConfiguration(new File(InvitationMain.pl.getDataFolder(), "IPList.yml"));
        ipList.set(ip.replace(".","-"),false);
        ipList.save(new File(InvitationMain.pl.getDataFolder(), "IPList.yml"));
    }
    public void addFrequency(String playerName,int frequency) throws IOException {
        YamlConfiguration playerData = YamlConfiguration.loadConfiguration(new File(InvitationMain.pl.getDataFolder(), "PlayerData.yml"));
        DataTaker dataTaker = new DataTaker();
        int value = dataTaker.getFrequency(playerName);
        playerData.set(playerName+".frequency",value+frequency);
        playerData.save(new File(InvitationMain.pl.getDataFolder(), "PlayerData.yml"));
    }
    public void setInviter(String playerName,String inviterName) throws IOException {
        YamlConfiguration playerData = YamlConfiguration.loadConfiguration(new File(InvitationMain.pl.getDataFolder(), "PlayerData.yml"));
        playerData.set(playerName+".inviter",inviterName);
        playerData.save(new File(InvitationMain.pl.getDataFolder(), "PlayerData.yml"));
    }
    public void setStatus(String ip,boolean status) throws IOException {
        YamlConfiguration ipList = YamlConfiguration.loadConfiguration(new File(InvitationMain.pl.getDataFolder(), "IPList.yml"));
        ipList.set(ip.replace(".","-"),status);
        ipList.save(new File(InvitationMain.pl.getDataFolder(), "IPList.yml"));
    }
    public void resetPlayerData(String clearName) throws IOException {
        YamlConfiguration playerData = YamlConfiguration.loadConfiguration(new File(InvitationMain.pl.getDataFolder(), "PlayerData.yml"));
        playerData.set(clearName+".frequency",0);
        playerData.set(clearName+".inviter","none");
        playerData.save(new File(InvitationMain.pl.getDataFolder(), "PlayerData.yml"));
    }
}
