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
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class CommandUtils {
    public static void onLookCommand(Player p) throws SQLException {
        YamlConfiguration message = YamlConfiguration.loadConfiguration(new File(InvitationMain.pl.getDataFolder(), "message.yml"));
        YamlConfiguration config = YamlConfiguration.loadConfiguration(new File(InvitationMain.pl.getDataFolder(), "config.yml"));
        List<String> dataMessage = message.getStringList("PlayerLook");
        if (InvitationMain.mySQL) {
            String code = MySQLTaker.getCode(p.getUniqueId());
            String inviter = MySQLTaker.getInviter(p.getUniqueId().toString());
            if (inviter.isEmpty()) {
                inviter = config.getString("player-placeholder", "");
            }
            int frequency = MySQLTaker.getFrequency(p.getUniqueId());
            for (String s : dataMessage) {
                p.sendMessage(s.replace("&", "§").
                        replace("%prefix%", InvitationMain.prefix).
                        replace("%player%", p.getName()).
                        replace("%code%", code).
                        replace("%amount%", String.valueOf(frequency)).
                        replace("%inviter%", inviter));
            }
            return;
        }
        String code = DataTaker.getCode(p.getName());
        String inviter = DataTaker.getInviter(p.getName());
        if (inviter.isEmpty()) {
            inviter = config.getString("player-placeholder", "");
        }
        int frequency = DataTaker.getFrequency(p.getName());
        for (String s : dataMessage) {
            p.sendMessage(s.replace("&", "§").
                    replace("%prefix%", InvitationMain.prefix).
                    replace("%player%", p.getName()).replace("%code%", code).
                    replace("%amount%", String.valueOf(frequency)).
                    replace("%inviter%", inviter));
        }
    }
    public static void onAcceptCommand(Player p, String code) throws IOException, SQLException {
        YamlConfiguration message = YamlConfiguration.loadConfiguration(new File(InvitationMain.pl.getDataFolder(), "message.yml"));
        List<String> successMessage = message.getStringList("Accept-Success");
        List<String> successCommand = InvitationMain.pl.getConfig().getStringList("Invite-Command");
        if (InvitationMain.mySQL) {
            if (!MySQLTaker.getInviter(p.getUniqueId().toString()).isEmpty()) {
                p.sendMessage(InvitationMain.prefix + Objects.requireNonNull(message.getString("alreadyInvite")).replace("&", "§"));
                return;
            }
            if (!MySQLChecker.ifContainsCode(code)) {
                p.sendMessage(InvitationMain.prefix + Objects.requireNonNull(message.getString("NotCode")).replace("&", "§").replace("%code%", code));
                return;
            }
            if (MySQLTaker.getName(code).equals(p.getName())) {
                p.sendMessage(InvitationMain.prefix + Objects.requireNonNull(message.getString("InviteSelf")).replace("&", "§"));
                return;
            }
            if (!InvitationMain.pl.getConfig().getBoolean("allow-invite-each") && MySQLTaker.getInviter(MySQLTaker.getUUID(code)).equals(p.getName())) {
                p.sendMessage(InvitationMain.prefix + Objects.requireNonNull(message.getString("InviteEach")).replace("&", "§"));
                return;
            }
            if (!p.hasPermission("LiteInvitation.Bypass") && ConditionUtils.CheckCondition(p)) {
                return;
            }
            String inviterName = MySQLTaker.getName(code);
            String uuid = MySQLTaker.getUUID(code);
            MySQLUpdater.addFrequency(UUID.fromString(uuid), 1);
            MySQLUpdater.setInviter(p.getUniqueId(), inviterName);
            String yourCode = MySQLTaker.getCode(p.getUniqueId());
            int frequency = MySQLTaker.getNameFrequency(inviterName);
            runSuccessCommand(successCommand, p, inviterName, frequency);
            for (String s : successMessage) {
                p.sendMessage(s.replace("&", "§").replace("%prefix%", InvitationMain.prefix).replace("%player%", p.getName()).replace("%code%", yourCode).replace("%inviter%", inviterName).replace("%inviter_code%", code));
            }
            MySQLUpdater.setStatus((Objects.requireNonNull(p.getAddress())).getAddress().getHostAddress(), true);
            inviterOnlineMessage(inviterName, message, code, frequency, p.getName());
            frequencyCommand(inviterName, frequency);
            return;
        }
        YamlConfiguration allCodes = YamlConfiguration.loadConfiguration(new File(InvitationMain.pl.getDataFolder(), "AllCodes.yml"));
        if (!DataTaker.getInviter(p.getName()).isEmpty()) {
            p.sendMessage(InvitationMain.prefix + Objects.requireNonNull(message.getString("alreadyInvite")).replace("&", "§"));
            return;
        }
        if (!allCodes.contains("CodeList." + code)) {
            p.sendMessage(InvitationMain.prefix + Objects.requireNonNull(message.getString("NotCode")).replace("&", "§").replace("%code%", code));
            return;
        }
        if (Objects.equals(allCodes.getString("CodeList." + code), p.getName())) {
            p.sendMessage(InvitationMain.prefix + Objects.requireNonNull(message.getString("InviteSelf")).replace("&", "§"));
            return;
        }
        if (!InvitationMain.pl.getConfig().getBoolean("allow-invite-each") && DataTaker.getInviter(allCodes.getString("CodeList." + code)).equals(p.getName())) {
            p.sendMessage(InvitationMain.prefix + Objects.requireNonNull(message.getString("InviteEach")).replace("&", "§"));
            return;
        }
        if (!p.hasPermission("LiteInvitation.Bypass") && ConditionUtils.CheckCondition(p)) {
            return;
        }
        String inviterName2 = allCodes.getString("CodeList." + code);
        DataUpdater.addFrequency(inviterName2, 1);
        DataUpdater.setInviter(p.getName(), inviterName2);
        String yourCode2 = DataTaker.getCode(p.getName());
        int frequency2 = DataTaker.getFrequency(inviterName2);
        runSuccessCommand(successCommand, p, inviterName2, frequency2);
        for (String s2 : successMessage) {
            p.sendMessage(s2.replace("&", "§").replace("%prefix%", InvitationMain.prefix).replace("%player%", p.getName()).replace("%code%", yourCode2).replace("%inviter%", Objects.requireNonNull(inviterName2)).replace("%inviter_code%", code));
        }
        DataUpdater.setStatus((Objects.requireNonNull(p.getAddress())).getAddress().getHostAddress(), true);
        inviterOnlineMessage(inviterName2, message, code, frequency2, p.getName());
        frequencyCommand(inviterName2, frequency2);
    }

    public static void runSuccessCommand(List<String> successCommand, Player p, String inviterName, int frequency) {
        for (String s : successCommand) {
            if (s.startsWith("[msg]")) {
                String message = s.substring(5).replace("&", "§").replace("%prefix%", InvitationMain.prefix).replace("%player%", p.getName()).replace("%inviter%", inviterName).replace("%inviter_frequency%", String.valueOf(frequency));
                p.sendMessage(message);
            } else if (s.startsWith("[command]")) {
                String command = s.substring(9).replace("&", "§").replace("%prefix%", InvitationMain.prefix).replace("%player%", p.getName()).replace("%inviter%", inviterName).replace("%inviter_frequency%", String.valueOf(frequency));
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
            } else if (s.startsWith("[player]")) {
                String command2 = s.substring(8).replace("&", "§").replace("%prefix%", InvitationMain.prefix).replace("%player%", p.getName()).replace("%inviter%", inviterName).replace("%inviter_frequency%", String.valueOf(frequency));
                Bukkit.dispatchCommand(p, command2);
            } else {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), s.replace("&", "§").replace("%prefix%", InvitationMain.prefix).replace("%player%", p.getName()).replace("%inviter%", inviterName).replace("%inviter_frequency%", String.valueOf(frequency)));
            }
        }
    }

    public static void onCheckCommand(CommandSender sender, String checkName) throws SQLException {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(new File(InvitationMain.pl.getDataFolder(), "config.yml"));
        YamlConfiguration message = YamlConfiguration.loadConfiguration(new File(InvitationMain.pl.getDataFolder(), "message.yml"));
        List<String> dataMessage = message.getStringList("PlayerCheck");
        if (InvitationMain.mySQL) {
            if (!MySQLChecker.ifContainsPlayerName(checkName)) {
                sender.sendMessage(InvitationMain.prefix + Objects.requireNonNull(message.getString("NotPlayer")).replace("&", "§"));
                return;
            }
            UUID uuid = UUID.fromString(MySQLTaker.getUUIDByName(checkName));
            String code = MySQLTaker.getCode(uuid);
            String inviter = MySQLTaker.getInviter(uuid.toString());
            if (inviter.isEmpty()) {
                inviter = config.getString("player-placeholder", "");
            }
            int frequency = MySQLTaker.getFrequency(uuid);
            for (String s : dataMessage) {
                sender.sendMessage(s.replace("&", "§").replace("%prefix%", InvitationMain.prefix).replace("%player%", checkName).replace("%code%", code).replace("%amount%", String.valueOf(frequency)).replace("%inviter%", inviter));
            }
            return;
        }
        YamlConfiguration playerData = YamlConfiguration.loadConfiguration(new File(InvitationMain.pl.getDataFolder(), "PlayerData.yml"));
        if (!playerData.contains(checkName)) {
            sender.sendMessage(InvitationMain.prefix + Objects.requireNonNull(message.getString("NotPlayer")).replace("&", "§"));
            return;
        }
        String code2 = DataTaker.getCode(checkName);
        String inviter2 = DataTaker.getInviter(checkName);
        if (inviter2.isEmpty()) {
            inviter2 = config.getString("player-placeholder", "");
        }
        int frequency2 = DataTaker.getFrequency(checkName);
        for (String s2 : dataMessage) {
            sender.sendMessage(s2.replace("&", "§").replace("%prefix%", InvitationMain.prefix).replace("%player%", checkName).replace("%code%", code2).replace("%amount%", String.valueOf(frequency2)).replace("%inviter%", inviter2));
        }
    }

    public static void onClearCommand(CommandSender sender, String clearName) throws IOException, SQLException {
        YamlConfiguration message = YamlConfiguration.loadConfiguration(new File(InvitationMain.pl.getDataFolder(), "message.yml"));
        if (InvitationMain.mySQL) {
            if (!MySQLChecker.ifContainsPlayerName(clearName)) {
                sender.sendMessage(InvitationMain.prefix + Objects.requireNonNull(message.getString("NotPlayer")).replace("&", "§"));
                return;
            }
            MySQLUpdater.resetPlayerData(clearName);
        } else {
            YamlConfiguration playerData = YamlConfiguration.loadConfiguration(new File(InvitationMain.pl.getDataFolder(), "PlayerData.yml"));
            if (!playerData.contains(clearName)) {
                sender.sendMessage(InvitationMain.prefix + Objects.requireNonNull(message.getString("NotPlayer")).replace("&", "§"));
                return;
            }
            DataUpdater.resetPlayerData(clearName);
        }
        sender.sendMessage(InvitationMain.prefix + Objects.requireNonNull(message.getString("clearPlayer")).replace("&", "§").replace("%player%", clearName));
    }

    public static void frequencyCommand(String inviter, int frequency) {
        Set<String> set = Objects.requireNonNull(InvitationMain.pl.getConfig().getConfigurationSection("Frequency-Command")).getKeys(false);
        if (set.contains(String.valueOf(frequency))) {
            List<String> list = InvitationMain.pl.getConfig().getStringList("Frequency-Command." + frequency);
            for (String s : list) {
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), s.replace("&", "§").replace("%player%", inviter).replace("%prefix%", InvitationMain.prefix).replace("%inviter_frequency%", String.valueOf(frequency)));
            }
        }
    }

    public static void inviterOnlineMessage(String inviterName, YamlConfiguration message, String inviterCode, int frequency, String playerName) {
        if (Bukkit.getPlayer(inviterName) != null) {
            List<String> inviterMessage = message.getStringList("InviterWasOnline");
            Player p = Bukkit.getPlayer(inviterName);
            for (String s : inviterMessage) {
                if (p != null) {
                    p.sendMessage(s.replace("&", "§").replace("%prefix%", InvitationMain.prefix).replace("%inviter%", inviterName).replace("%code%", inviterCode).replace("%amount%", String.valueOf(frequency)).replace("%player%", playerName));
                }
            }
        }
    }
}