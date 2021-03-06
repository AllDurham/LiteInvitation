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
        YamlConfiguration message = YamlConfiguration.loadConfiguration(new File(InvitationMain.pl.getDataFolder(), "message.yml"));
        if (InvitationMain.pl.getConfig().getDouble("version")==1.0){
            configUpdate1();
        }
        if (message.getDouble("version")==1.6){
            messageUpdate1();
        }
        if (message.getDouble("version")==1.7){
            messageUpdate2();
        }
        if (InvitationMain.pl.getConfig().getDouble("version")==1.1){
            configUpdate2();
        }
        if (InvitationMain.pl.getConfig().getDouble("version")==1.2){
            configUpdate3();
        }
        if (InvitationMain.pl.getConfig().getDouble("version")==1.3){
            configUpdate4();
        }
    }
    private void configUpdate1() throws IOException {
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
    private void messageUpdate1() throws IOException {
        YamlConfiguration message = YamlConfiguration.loadConfiguration(new File(InvitationMain.pl.getDataFolder(), "message.yml"));
        Bukkit.getServer().getConsoleSender().sendMessage
                ("\n"+InvitationMain.prefix+"§b系统检测出你当前的信息文件配置版本已过时\n"+InvitationMain.prefix+"§a正在自动为您更新配置中！");
        message.set("version",1.7);
        message.set("alreadySameIP","&c请不要使用小号哦！这个IP已经接受过邀请了");
        message.save(new File(InvitationMain.pl.getDataFolder(), "message.yml"));
    }
    private void messageUpdate2() throws IOException {
        YamlConfiguration message = YamlConfiguration.loadConfiguration(new File(InvitationMain.pl.getDataFolder(), "message.yml"));
        Bukkit.getServer().getConsoleSender().sendMessage
                ("\n"+InvitationMain.prefix+"§b系统检测出你当前的信息文件配置版本已过时\n"+InvitationMain.prefix+"§a正在自动为您更新配置中！");
        message.set("version",1.8);
        message.set("TimeNotEnough","&c接受邀请失败，§a您需要累计在线时间: %time%小时才能接受邀请！");
        message.set("KillsNotEnough","&c接受邀请失败，§a您需要累计击杀: %kills%次生物才能接受邀请！");
        message.set("LevelNotEnough","&c接受邀请失败，§a您需要达到等级: %level%级才能接受邀请！");
        message.save(new File(InvitationMain.pl.getDataFolder(), "message.yml"));
    }
    private void configUpdate2(){
        Bukkit.getServer().getConsoleSender().sendMessage
                ("\n"+InvitationMain.prefix+"§b系统检测出你当前的插件配置版本已过时\n"+InvitationMain.prefix+"§a正在自动为您更新配置中！");
        InvitationMain.pl.getConfig().set("version",1.2);
        InvitationMain.pl.getConfig().set("Anti-SmallAccount",true);
        InvitationMain.pl.saveConfig();
    }
    private void configUpdate3(){
        Bukkit.getServer().getConsoleSender().sendMessage
                ("\n"+InvitationMain.prefix+"§b系统检测出你当前的插件配置版本已过时\n"+InvitationMain.prefix+"§a正在自动为您更新配置中！");
        InvitationMain.pl.getConfig().set("version",1.3);
        InvitationMain.pl.getConfig().set("Anti-SmallAccount",null);
        InvitationMain.pl.getConfig().set("Anti-SmallAccount.IP.Enable",true);
        InvitationMain.pl.getConfig().set("Anti-SmallAccount.Time.Enable",true);
        InvitationMain.pl.getConfig().set("Anti-SmallAccount.Time.Amount",24);
        InvitationMain.pl.getConfig().set("Anti-SmallAccount.Kills.Enable",true);
        InvitationMain.pl.getConfig().set("Anti-SmallAccount.Kills.Amount",50);
        InvitationMain.pl.getConfig().set("Anti-SmallAccount.Level.Enable",true);
        InvitationMain.pl.getConfig().set("Anti-SmallAccount.Level.Amount",20);
        InvitationMain.pl.saveConfig();
    }
    private void configUpdate4(){
        Bukkit.getServer().getConsoleSender().sendMessage
                ("\n"+InvitationMain.prefix+"§b系统检测出你当前的插件配置版本已过时\n"+InvitationMain.prefix+"§a正在自动为您更新配置中！");
        InvitationMain.pl.getConfig().set("version",1.4);
        InvitationMain.pl.getConfig().set("Anti-SmallAccount.Kills.Enable",null);
        InvitationMain.pl.getConfig().set("Anti-SmallAccount.Kills.Amount",null);
        InvitationMain.pl.saveConfig();
    }
}
