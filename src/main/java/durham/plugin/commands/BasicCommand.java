package durham.plugin.commands;

import durham.plugin.invitation.InvitationMain;
import durham.plugin.utils.CommandUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class BasicCommand implements CommandExecutor {
    @Override
    public boolean onCommand( CommandSender sender, Command command,  String label, String[] args) {
        if ("LiteInvitation".equalsIgnoreCase(command.getName())) {
            YamlConfiguration message = YamlConfiguration.loadConfiguration(new File(InvitationMain.pl.getDataFolder(), "message.yml"));
            if (args.length == 0) {
                List<String> help = message.getStringList("Help");
                for (String s : help) {
                    sender.sendMessage(s.replace("&", "§").replace("%prefix%", InvitationMain.prefix));
                }
                return true;
            }
            if ("help".equalsIgnoreCase(args[0])) {
                List<String> help2 = message.getStringList("Help");
                for (String s2 : help2) {
                    sender.sendMessage(s2.replace("&", "§").replace("%prefix%", InvitationMain.prefix));
                }
                return true;
            }
            if ("reload".equalsIgnoreCase(args[0])) {
                if (!sender.hasPermission("LiteInvitation.Admin")) {
                    sender.sendMessage(InvitationMain.prefix + (Objects.requireNonNull(message.getString("PermissionError"))).replace("&", "§"));
                    return true;
                }
                sender.sendMessage(InvitationMain.prefix + Objects.requireNonNull(message.getString("SuccessReload")).replace("&", "§"));
                InvitationMain.pl.reloadConfig();
                InvitationMain.mySQL = InvitationMain.pl.getConfig().getBoolean("mysql.enable", false);
                InvitationMain.prefix = InvitationMain.pl.getConfig().getString("prefix", "&8[&aLiteInvitation&8] ").replace("&", "§");
                return true;
            }
            if ("Check".equalsIgnoreCase(args[0])) {
                if (!sender.hasPermission("LiteInvitation.Admin")) {
                    sender.sendMessage(InvitationMain.prefix + Objects.requireNonNull(message.getString("PermissionError")).replace("&", "§"));
                    return true;
                }
                if (args.length != 2) {
                    sender.sendMessage(InvitationMain.prefix + Objects.requireNonNull(message.getString("CommandError")).replace("&", "§"));
                    return true;
                }
                String inputName = args[1];
                try {
                    CommandUtils.onCheckCommand(sender, inputName);
                    return true;
                } catch (SQLException e) {
                    e.printStackTrace();
                    sender.sendMessage(InvitationMain.prefix + Objects.requireNonNull(message.getString("UnknownError")).replace("&", "§"));
                    return true;
                }
            }
            if (args[0].equalsIgnoreCase("Clear")) {
                if (!sender.hasPermission("LiteInvitation.Admin")) {
                    sender.sendMessage(InvitationMain.prefix + Objects.requireNonNull(message.getString("PermissionError")).replace("&", "§"));
                    return true;
                }
                if (args.length != 2) {
                    sender.sendMessage(InvitationMain.prefix + Objects.requireNonNull(message.getString("CommandError")).replace("&", "§"));
                    return true;
                }
                String inputName = args[1];
                try {
                    CommandUtils.onClearCommand(sender, inputName);
                    return true;
                } catch (IOException | SQLException e) {
                    sender.sendMessage(InvitationMain.prefix + Objects.requireNonNull(message.getString("UnknownError")).replace("&", "§"));
                    return true;
                }
            }
            if (sender instanceof ConsoleCommandSender) {
                sender.sendMessage(InvitationMain.prefix + Objects.requireNonNull(message.getString("ConsoleError")).replace("&", "§"));
                return true;
            }
            if ("Look".equalsIgnoreCase(args[0])) {
                try {
                    CommandUtils.onLookCommand((Player) sender);
                    return true;
                } catch (SQLException e) {
                    e.printStackTrace();
                    sender.sendMessage(InvitationMain.prefix + Objects.requireNonNull(message.getString("UnknownError")).replace("&", "§"));
                    return true;
                }
            }
            if ("Accept".equalsIgnoreCase(args[0])) {
                if (args.length != 2) {
                    sender.sendMessage(InvitationMain.prefix + Objects.requireNonNull(message.getString("CommandError")).replace("&", "§"));
                    return true;
                }
                String inputCode = args[1];
                try {
                    CommandUtils.onAcceptCommand((Player) sender, inputCode);
                    return true;
                } catch (IOException | SQLException e) {
                    e.printStackTrace();
                    sender.sendMessage(InvitationMain.prefix + Objects.requireNonNull(message.getString("UnknownError")).replace("&", "§"));
                    return true;
                }
            }
            sender.sendMessage(InvitationMain.prefix + Objects.requireNonNull(message.getString("CommandError")).replace("&", "§"));
            return true;
        }
        return false;

    }
}
