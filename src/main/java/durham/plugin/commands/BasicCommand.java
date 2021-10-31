package durham.plugin.commands;

import durham.plugin.invitation.InvitationMain;
import durham.plugin.utils.CommandUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class BasicCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, Command command, @NotNull String label, String[] args) {
        if (command.getName().equalsIgnoreCase("LiteInvitation")){
            YamlConfiguration message = YamlConfiguration.loadConfiguration(new File(InvitationMain.pl.getDataFolder(), "message.yml"));
            if (args.length==0){
                List<String> help = message.getStringList("Help");
                for (String s : help) {
                    sender.sendMessage(s.replace("&", "§").replace("%prefix%", InvitationMain.prefix));
                }
                return true;
            }
            if (args[0].equalsIgnoreCase("help")){
                List<String> help = message.getStringList("Help");
                for (String s : help) {
                    sender.sendMessage(s.replace("&", "§").replace("%prefix%", InvitationMain.prefix));
                }
                return true;
            }
            if (args[0].equalsIgnoreCase("reload")){
                sender.sendMessage(InvitationMain.prefix+message.getString("SuccessReload").replace("&","§"));
                InvitationMain.pl.reloadConfig();
                return true;
            }
            if (args[0].equalsIgnoreCase("Check")){
                if (!sender.hasPermission("LiteInvitation.Admin")){
                    sender.sendMessage(InvitationMain.prefix+message.getString("PermissionError").replace("&","§"));
                    return true;
                }
                if (args.length != 2){
                    sender.sendMessage(InvitationMain.prefix+message.getString("CommandError").replace("&","§"));
                    return true;
                }
                String inputName = args[1];
                CommandUtils commandUtils = new CommandUtils();
                try {
                    commandUtils.onCheckCommand(sender,inputName);
                } catch (SQLException e) {
                    sender.sendMessage(InvitationMain.prefix+message.getString("UnknownError").replace("&","§"));
                }
                return true;
            }
            if (args[0].equalsIgnoreCase("Clear")){
                if (!sender.hasPermission("LiteInvitation.Admin")){
                    sender.sendMessage(InvitationMain.prefix+message.getString("PermissionError").replace("&","§"));
                    return true;
                }
                if (args.length != 2){
                    sender.sendMessage(InvitationMain.prefix+message.getString("CommandError").replace("&","§"));
                    return true;
                }
                String inputName = args[1];
                CommandUtils commandUtils = new CommandUtils();
                try {
                    commandUtils.onClearCommand(sender,inputName);
                } catch (IOException | SQLException e) {
                    sender.sendMessage(InvitationMain.prefix+message.getString("UnknownError").replace("&","§"));
                }
                return true;
            }
            if (sender instanceof ConsoleCommandSender){
                sender.sendMessage(InvitationMain.prefix+message.getString("ConsoleError").replace("&","§"));
                return true;
            }
            if (args[0].equalsIgnoreCase("Look")){
                CommandUtils commandUtils = new CommandUtils();
                try {
                    commandUtils.onLookCommand((Player)sender);
                } catch (SQLException e) {
                    sender.sendMessage(InvitationMain.prefix+message.getString("UnknownError").replace("&","§"));
                }
                return true;
            }
            if (args[0].equalsIgnoreCase("Accept")){
                if (args.length != 2){
                    sender.sendMessage(InvitationMain.prefix+message.getString("CommandError").replace("&","§"));
                    return true;
                }
                String inputCode = args[1];
                CommandUtils commandUtils = new CommandUtils();
                try {
                    commandUtils.onAcceptCommand((Player)sender,inputCode);
                } catch (IOException | SQLException e) {
                    sender.sendMessage(InvitationMain.prefix+message.getString("UnknownError").replace("&","§"));
                }
                return true;
            }
            sender.sendMessage(InvitationMain.prefix+message.getString("CommandError").replace("&","§"));
            return true;
        }
        return false;
    }
}
