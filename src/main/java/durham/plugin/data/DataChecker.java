package durham.plugin.data;


import durham.plugin.invitation.InvitationMain;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.util.UUID;

public class DataChecker {
    public static boolean ifContainsPlayer(UUID uuid){
        YamlConfiguration allCodes = YamlConfiguration.loadConfiguration(new File(InvitationMain.pl.getDataFolder(), "AllCodes.yml"));
        return allCodes.getStringList("AllCodes").contains(uuid.toString());
    }
}
