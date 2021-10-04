package durham.plugin.updater;

import durham.plugin.invitation.InvitationMain;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConfigurationUpdater {
    public void checkConfigUpdate() throws IOException {
        if (InvitationMain.pl.getConfig().getDouble("version")==1.0){
            configUpdate1();
            return;
        }
        Bukkit.getServer().getConsoleSender().sendMessage
                (InvitationMain.prefix+"§a您的配置文件当前是最新版本哦！");
    }
    public void configUpdate1() throws IOException {
        YamlConfiguration message = YamlConfiguration.loadConfiguration(new File(InvitationMain.pl.getDataFolder(), "message.yml"));
        Bukkit.getServer().getConsoleSender().sendMessage
                ("\n"+InvitationMain.prefix+"§b系统检测出你当前的插件配置版本已过时\n"+InvitationMain.prefix+"§a正在自动为您更新配置中！");
        List<String> set1 = new ArrayList<>();
        List<String> set2 = new ArrayList<>();
        List<String> set3 = new ArrayList<>();
        set1.add("tell %player% &a你已经邀请了三个人了！额外给你一点奖励！");
        set1.add("give %player% minecraft:iron_ingot 1");
        set2.add("tell %player% &a你已经邀请了五个人了！额外给你亿点奖励！");
        set2.add("give %player% minecraft:diamond 1");
        set3.add("&8|&7----------------------------------------------------------&8|");
        set3.add("&8|                  %prefix% &a有人接受了你的邀请！");
        set3.add("&8|");
        set3.add("&8|&7--&b%inviter%:");
        set3.add("&8|  &e”你的邀请码“&b--%code%");
        set3.add("&8|  &e“你累计邀请了”&b--%amount%人");
        set3.add("&8|  &e“接受了你的邀请”&b--%player%");
        set3.add("&8|");
        set3.add("&8|&7----------------------------------------------------------&8|");
        InvitationMain.pl.getConfig().set("version",1.1);
        InvitationMain.pl.getConfig().set("prefix",message.getString("prefix"));
        InvitationMain.pl.getConfig().set("Frequency-Command.3",set1);
        InvitationMain.pl.getConfig().set("Frequency-Command.5",set2);
        message.set("prefix",null);
        message.set("version",1.6);
        message.set("SuccessReload","&a成功重载了插件配置！");
        message.set("InviterWasOnline",set3);
        message.save(new File(InvitationMain.pl.getDataFolder(), "message.yml"));
        InvitationMain.pl.saveConfig();
    }
}
