package durham.plugin.utils;

import durham.plugin.data.DataTaker;
import durham.plugin.data.DataUpdater;
import durham.plugin.invitation.InvitationMain;
import durham.plugin.mysql.MySQLChecker;
import durham.plugin.mysql.MySQLTaker;
import durham.plugin.mysql.MySQLUpdater;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class CommandUtils {
    public void onLookCommand(Player p) throws SQLException {
        YamlConfiguration message = YamlConfiguration.loadConfiguration(new File(InvitationMain.pl.getDataFolder(), "message.yml"));
        List<String> dataMessage = message.getStringList("PlayerLook");
        if (InvitationMain.mySQL){
            MySQLTaker mySQLTaker = new MySQLTaker();
            String code = mySQLTaker.getCode(p.getUniqueId());
            String inviter = mySQLTaker.getInviter(p.getUniqueId());
            int frequency = mySQLTaker.getFrequency(p.getUniqueId());
            for (String s : dataMessage) {
                p.sendMessage(s.replace("&", "§")
                        .replace("%prefix%", InvitationMain.prefix)
                        .replace("%player%",p.getName())
                        .replace("%code%",code)
                        .replace("%amount%",String.valueOf(frequency))
                        .replace("%inviter%",inviter));
            }
        }
        else{
            DataTaker dataTaker = new DataTaker();
            String code = dataTaker.getCode(p.getName());
            String inviter = dataTaker.getInviter(p.getName());
            int frequency = dataTaker.getFrequency(p.getName());
            for (String s : dataMessage) {
                p.sendMessage(s.replace("&", "§")
                        .replace("%prefix%", InvitationMain.prefix)
                        .replace("%player%",p.getName())
                        .replace("%code%",code)
                        .replace("%amount%",String.valueOf(frequency))
                        .replace("%inviter%",inviter));
            }
        }
    }
    public void onAcceptCommand(Player p,String code) throws IOException, SQLException {
        YamlConfiguration message = YamlConfiguration.loadConfiguration(new File(InvitationMain.pl.getDataFolder(), "message.yml"));
        List<String> successMessage = message.getStringList("Accept-Success");
        List<String> successCommand = InvitationMain.pl.getConfig().getStringList("Invite-Command");
        if (InvitationMain.mySQL){
            MySQLChecker mySQLChecker = new MySQLChecker();
            MySQLTaker mySQLTaker = new MySQLTaker();
            MySQLUpdater mySQLUpdater = new MySQLUpdater();
            String status = mySQLTaker.getStatus(p.getAddress().getHostName());
            if (!mySQLTaker.getInviter(p.getUniqueId()).equals("无")){
                p.sendMessage(InvitationMain.prefix+message.getString("alreadyInvite")
                        .replace("&","§"));
                return;
            }
            if (!mySQLChecker.ifContainsCode(code)){
                p.sendMessage(InvitationMain.prefix+message.getString("NotCode")
                        .replace("&","§")
                        .replace("%code%",code));
                return;
            }
            if (mySQLTaker.getName(code).equals(p.getName())){
                p.sendMessage(InvitationMain.prefix+message.getString("InviteSelf").replace("&","§"));
                return;
            }
            if (InvitationMain.pl.getConfig().getBoolean("Anti-SmallAccount")){
                if (status.equalsIgnoreCase("true")){
                    p.sendMessage(InvitationMain.prefix+message.getString("alreadySameIP")
                            .replace("&","§"));
                    return;
                }
            }
            String inviterName = mySQLTaker.getName(code);
            String uuid = mySQLTaker.getUUID(code);
            mySQLUpdater.addFrequency(UUID.fromString(uuid),1);
            mySQLUpdater.setInviter(p.getUniqueId(),inviterName);
            String yourCode = mySQLTaker.getCode(p.getUniqueId());
            int frequency = mySQLTaker.getNameFrequency(inviterName);
            for (String s : successCommand) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), s
                        .replace("&", "§")
                        .replace("%prefix%", InvitationMain.prefix)
                        .replace("%player%",p.getName())
                        .replace("%inviter%",inviterName)
                        .replace("%inviter_frequency%",String.valueOf(frequency)));
            }
            for (String s : successMessage) {
                p.sendMessage(s.replace("&", "§")
                        .replace("%prefix%", InvitationMain.prefix)
                        .replace("%player%",p.getName())
                        .replace("%code%",yourCode)
                        .replace("%inviter%",inviterName)
                        .replace("%inviter_code%",code));
            }
            mySQLUpdater.setStatus(p.getAddress().getHostName(),true);
            inviterOnlineMessage(inviterName,message,code,frequency,p.getName());
            frequencyCommand(inviterName,frequency);
        }
        else{
            YamlConfiguration playerData = YamlConfiguration.loadConfiguration(new File(InvitationMain.pl.getDataFolder(), "PlayerData.yml"));
            YamlConfiguration allCodes = YamlConfiguration.loadConfiguration(new File(InvitationMain.pl.getDataFolder(), "AllCodes.yml"));
            boolean status = new DataTaker().getStatus(p.getAddress().getHostName());
            if (!playerData.getString(p.getName()+".inviter").equals("无")){
                p.sendMessage(InvitationMain.prefix+message.getString("alreadyInvite")
                        .replace("&","§"));
                return;
            }
            if (!allCodes.contains("CodeList."+code)){
                p.sendMessage(InvitationMain.prefix+message.getString("NotCode")
                        .replace("&","§")
                        .replace("%code%",code));
                return;
            }
            if (allCodes.getString("CodeList."+code).equals(p.getName())){
                p.sendMessage(InvitationMain.prefix+message.getString("InviteSelf").replace("&","§"));
                return;
            }
            if (InvitationMain.pl.getConfig().getBoolean("Anti-SmallAccount")){
                if (status){
                    p.sendMessage(InvitationMain.prefix+message.getString("alreadySameIP")
                            .replace("&","§"));
                    return;
                }
            }
            String inviterName = allCodes.getString("CodeList."+code);
            DataUpdater dataUpdater = new DataUpdater();
            DataTaker dataTaker = new DataTaker();
            dataUpdater.addFrequency(inviterName,1);
            dataUpdater.setInviter(p.getName(),inviterName);
            String yourCode = dataTaker.getCode(p.getName());
            int frequency = dataTaker.getFrequency(inviterName);
            for (String s : successCommand) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), s
                        .replace("&", "§")
                        .replace("%prefix%", InvitationMain.prefix)
                        .replace("%player%",p.getName())
                        .replace("%inviter%",inviterName)
                        .replace("%inviter_frequency%",String.valueOf(frequency)));
            }
            for (String s : successMessage) {
                p.sendMessage(s.replace("&", "§")
                        .replace("%prefix%", InvitationMain.prefix)
                        .replace("%player%",p.getName())
                        .replace("%code%",yourCode)
                        .replace("%inviter%",inviterName)
                        .replace("%inviter_code%",code));
            }
            dataUpdater.setStatus(p.getAddress().getHostName(),true);
            inviterOnlineMessage(inviterName,message,code,frequency,p.getName());
            frequencyCommand(inviterName,frequency);
        }
    }
    public void onCheckCommand(CommandSender sender, String checkName) throws SQLException {
        YamlConfiguration message = YamlConfiguration.loadConfiguration(new File(InvitationMain.pl.getDataFolder(), "message.yml"));
        List<String> dataMessage = message.getStringList("PlayerCheck");
        if (InvitationMain.mySQL){
            MySQLChecker mySQLChecker = new MySQLChecker();
            if (!mySQLChecker.ifContainsPlayerName(checkName)){
                sender.sendMessage(InvitationMain.prefix+message.getString("NotPlayer").replace("&","§"));
                return;
            }
            MySQLTaker mySQLTaker = new MySQLTaker();
            UUID uuid = UUID.fromString(mySQLTaker.getNameUUID(checkName));
            String code = mySQLTaker.getCode(uuid);
            String inviter = mySQLTaker.getInviter(uuid);
            int frequency = mySQLTaker.getFrequency(uuid);
            for (String s : dataMessage) {
                sender.sendMessage(s.replace("&", "§")
                        .replace("%prefix%", InvitationMain.prefix)
                        .replace("%player%",checkName)
                        .replace("%code%",code)
                        .replace("%amount%",String.valueOf(frequency))
                        .replace("%inviter%",inviter));
            }
        }
        else{
            YamlConfiguration playerData = YamlConfiguration.loadConfiguration(new File(InvitationMain.pl.getDataFolder(), "PlayerData.yml"));
            if (!playerData.contains(checkName)){
                sender.sendMessage(InvitationMain.prefix+message.getString("NotPlayer").replace("&","§"));
                return;
            }
            DataTaker dataTaker = new DataTaker();
            String code = dataTaker.getCode(checkName);
            String inviter = dataTaker.getInviter(checkName);
            int frequency = dataTaker.getFrequency(checkName);
            for (String s : dataMessage) {
                sender.sendMessage(s.replace("&", "§")
                        .replace("%prefix%", InvitationMain.prefix)
                        .replace("%player%",checkName)
                        .replace("%code%",code)
                        .replace("%amount%",String.valueOf(frequency))
                        .replace("%inviter%",inviter));
            }
        }
    }
    public void onClearCommand(CommandSender sender,String clearName) throws IOException, SQLException {
        YamlConfiguration message = YamlConfiguration.loadConfiguration(new File(InvitationMain.pl.getDataFolder(), "message.yml"));
        if (InvitationMain.mySQL){
            MySQLChecker mySQLChecker = new MySQLChecker();
            if (!mySQLChecker.ifContainsPlayerName(clearName)){
                sender.sendMessage(InvitationMain.prefix+message.getString("NotPlayer").replace("&","§"));
                return;
            }
            MySQLUpdater mySQLUpdater = new MySQLUpdater();
            mySQLUpdater.resetPlayerData(clearName);
        }
        else{
            YamlConfiguration playerData = YamlConfiguration.loadConfiguration(new File(InvitationMain.pl.getDataFolder(), "PlayerData.yml"));
            if (!playerData.contains(clearName)){
                sender.sendMessage(InvitationMain.prefix+message.getString("NotPlayer").replace("&","§"));
                return;
            }
            DataUpdater dataUpdater = new DataUpdater();
            dataUpdater.resetPlayerData(clearName);
        }
        sender.sendMessage(InvitationMain.prefix+message.getString("clearPlayer").replace("&","§").replace("%player%",clearName));
    }
    public void frequencyCommand(String inviter,int frequency){
        Set<String> set = InvitationMain.pl.getConfig().getConfigurationSection("Frequency-Command").getKeys(false);
        if (set.contains(String.valueOf(frequency))){
            List<String> list = InvitationMain.pl.getConfig().getStringList("Frequency-Command."+frequency);
            for (String s : list){
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), s
                        .replace("&", "§")
                        .replace("%player%",inviter)
                        .replace("%prefix%",InvitationMain.prefix)
                        .replace("%inviter_frequency%",String.valueOf(frequency)));
            }
        }
    }
    public void inviterOnlineMessage(String inviterName,YamlConfiguration message,String inviterCode,int frequency,String playerName){
        if (Bukkit.getPlayer(inviterName)!=null){
            List<String> inviterMessage = message.getStringList("InviterWasOnline");
            Player p = Bukkit.getPlayer(inviterName);
            for (String s : inviterMessage) {
                p.sendMessage(s.replace("&", "§")
                        .replace("%prefix%", InvitationMain.prefix)
                        .replace("%inviter%",inviterName)
                        .replace("%code%",inviterCode)
                        .replace("%amount%",String.valueOf(frequency))
                        .replace("%player%",playerName));
            }
        }
    }
}
