package durham.plugin.utils;

import durham.plugin.invitation.InvitationMain;
import durham.plugin.mysql.MySQLChecker;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.sql.SQLException;
import java.util.Random;

public class RandomUtil {
    public static String createCode() throws SQLException {
        String code = randomCode();
        MySQLChecker mySQLChecker = new MySQLChecker();
        if (InvitationMain.mySQL){
            while (mySQLChecker.ifContainsCode(code)) {
                Bukkit.getServer().getConsoleSender().sendMessage(InvitationMain.prefix+"§c"+code+"已经存在,正在重新刷新");
                code = randomCode();
                Bukkit.getServer().getConsoleSender().sendMessage(InvitationMain.prefix+"§a新Code:"+code);
            }
        }
        else{
            while (YamlConfiguration.loadConfiguration(new File(InvitationMain.pl.getDataFolder(), "AllCodes.yml")).contains(code)) {
                Bukkit.getServer().getConsoleSender().sendMessage(InvitationMain.prefix+"§c"+code+"已经存在,正在重新刷新");
                code = randomCode();
                Bukkit.getServer().getConsoleSender().sendMessage(InvitationMain.prefix+"§a新Code:"+code);
            }
        }
        return code;
    }
    private static String randomCode(){
        char[] str = {
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
                'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
                'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd',
                'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
                'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x',
                'y', 'z', 'r', '0', '1', '2', '3', '4', '5', '6',
                '7', '8', '9' };
        StringBuilder code = new StringBuilder();
        Random r = new Random();
        for (int i=0;i<InvitationMain.pl.getConfig().getInt("CodeLength",6);i++){
            int num = r.nextInt(str.length);
            char c = str[num];
            code.append(c);
        }
        return code.toString();
    }
}
