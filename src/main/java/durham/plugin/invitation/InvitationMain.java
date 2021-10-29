package durham.plugin.invitation;

import durham.plugin.commands.BasicCommand;
import durham.plugin.hook.PAPIHook;
import durham.plugin.listener.JoinListener;
import durham.plugin.mysql.MySQLHelper;
import durham.plugin.updater.ConfigurationUpdater;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.sql.SQLException;

public class InvitationMain extends JavaPlugin{
    private String version;
    public static String prefix;
    public static InvitationMain pl;
    public static boolean mySQL;
    @Override
    public void onEnable() {
        try {new ConfigurationUpdater().checkConfigUpdate();} catch (IOException e)
        {e.printStackTrace();getServer().getConsoleSender().sendMessage(prefix+"§c配置文件更新时发生了未知错误！");}
        Bukkit.getPluginCommand("LiteInvitation").setExecutor(new BasicCommand());
        Bukkit.getPluginManager().registerEvents(new JoinListener(),this);
        ConsoleCommandSender c = getServer().getConsoleSender();
        new Metrics(this, 12930);
        try {loadMySQL();} catch (SQLException e) {e.printStackTrace();}
        loadPAPI(c);
        c.sendMessage("§8[]======[§7§lLite Invitation§8]======[]");
        c.sendMessage("§8| §c信息:");
        c.sendMessage("§8| §7Enabling");
        c.sendMessage("§8| §c作者: §7Durham");
        c.sendMessage("§8| §c版本: §7"+version);
        c.sendMessage("§8| §c联系我: ");
        c.sendMessage("§8|   §7QQ: §71664828807");
        c.sendMessage("§8|   §7MCBBS: §7Durham");
        c.sendMessage("§8|");
        if(mySQL){
            c.sendMessage("§8| §7#§b数据存储方式: §aMySQL");
        }
        else{
            c.sendMessage("§8| §7#§b数据存储方式: §aYaml");
        }
        c.sendMessage("§8[]==============================[]");
    }
    @Override
    public void onDisable() {
        ConsoleCommandSender c = getServer().getConsoleSender();
        c.sendMessage("§8[]======[§7§lLite Invitation§8]======[]");
        c.sendMessage("§8| §c信息:");
        c.sendMessage("§8| §7Disabling");
        c.sendMessage("§8| §c作者: §7Durham");
        c.sendMessage("§8| §c版本: §7"+version);
        c.sendMessage("§8| §c联系我: ");
        c.sendMessage("§8|   §7QQ: §71664828807");
        c.sendMessage("§8|   §7MCBBS: §7Durham");
        c.sendMessage("§8|");
        if(mySQL){
            c.sendMessage("§8| §7#§b数据存储方式: §aMySQL");
        }
        else{
            c.sendMessage("§8| §7#§b数据存储方式: §aYaml");
        }
        c.sendMessage("§8[]==============================[]");
    }
    @Override
    public void onLoad(){
        saveDefaultConfig();
        saveResource("message.yml",false);
        getServer().getConsoleSender().sendMessage("\n" +
                "§8|§7------------------------------------------------------------------§8|\n"+
                "§8|§b  _      _ _       _____            _ _        _   _             \n" +
                "§8|§b | |    (_) |     |_   _|          (_) |      | | (_)            \n" +
                "§8|§b | |     _| |_ ___  | |  _ ____   ___| |_ __ _| |_ _  ___  _ __  \n" +
                "§8|§b | |    | | __/ _ \\ | | | '_ \\ \\ / / | __/ _` | __| |/ _ \\| '_ \\ \n" +
                "§8|§b | |____| | ||  __/_| |_| | | \\ V /| | || (_| | |_| | (_) | | | |\n" +
                "§8|§b |______|_|\\__\\___|_____|_| |_|\\_/ |_|\\__\\__,_|\\__|_|\\___/|_| |_|\n" +
                "§8|                                                                    \n"+
                "§8|§7------------------------------------------------------------------§8|\n"+
                "                                                                 ");
        mySQL = getConfig().getBoolean("mysql.enable",false);
        pl = this;
        version = "1.4.2-SNAPSHOT";
        prefix = getConfig().getString("prefix").replace("&","§");
    }
    public String getVersion(){
        return version;
    }
    private void loadMySQL() throws SQLException {
        if (mySQL){
            getServer().getConsoleSender().sendMessage(prefix+"§f尝试访问数据库");
            MySQLHelper.MySQLInstalled();
        }
        else{
            getServer().getConsoleSender().sendMessage(prefix+"§f正在使用yaml进行文件存储");
            saveResource("PlayerData.yml",false);
            saveResource("AllCodes.yml",false);
            if (getConfig().getBoolean("Anti-SmallAccount")){
                saveResource("IPList.yml",false);
            }
        }
    }
    private void loadPAPI(ConsoleCommandSender c){
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null){
            c.sendMessage(prefix+"§a成功添加前置依赖 PlaceholderAPI");
            c.sendMessage(prefix+"§aPlaceholderAPI 变量注册成功!");
            new PAPIHook().register();
        }
        else{
            c.sendMessage(prefix+"§a也许您可以添加PlaceholderAPI插件");
            c.sendMessage(prefix+"§a本插件支持PAPI变量！");
        }
    }
}
