package durham.plugin.hook;

import durham.plugin.invitation.InvitationMain;
import durham.plugin.utils.RequestUtils;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;

public class PAPIHook extends PlaceholderExpansion {
    @Override
    public @NotNull String getIdentifier() {
        return "liteinvitation";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Durham";
    }

    @Override
    public @NotNull String getVersion() {
        return InvitationMain.pl.getVersion();
    }

    @Override
    public boolean persist() {
        return true; // This is required or else PlaceholderAPI will unregister the Expansion on reload
    }
    @Override
    public String onRequest(OfflinePlayer player, String params) {
        if (params.equalsIgnoreCase("code")){
            RequestUtils requestUtils = new RequestUtils();
            try {
                return requestUtils.requestCode(player);} catch (SQLException e) {
                Bukkit.getServer().getConsoleSender().sendMessage(InvitationMain.prefix+"§c在操作PlaceholderAPI相关的操作时发生了未知错误\n§a请尝试更换PAPI到最新版！");
                e.printStackTrace();
            }
        }
        if (params.equalsIgnoreCase("frequency")){
            RequestUtils requestUtils = new RequestUtils();
            try {
                return requestUtils.requestFrequency(player);} catch (SQLException e) {
                Bukkit.getServer().getConsoleSender().sendMessage(InvitationMain.prefix+"§c在操作PlaceholderAPI相关的操作时发生了未知错误\n§a请尝试更换PAPI到最新版！");
                e.printStackTrace();
            }
        }
        if (params.equalsIgnoreCase("inviter")){
            RequestUtils requestUtils = new RequestUtils();
            try {
                return requestUtils.requestInviter(player);} catch (SQLException e) {
                Bukkit.getServer().getConsoleSender().sendMessage(InvitationMain.prefix+"§c在操作PlaceholderAPI相关的操作时发生了未知错误\n§a请尝试更换PAPI到最新版！");
                e.printStackTrace();
            }
        }
        return null;
    }
}