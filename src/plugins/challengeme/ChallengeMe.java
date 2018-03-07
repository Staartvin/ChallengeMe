package plugins.challengeme;

import org.bukkit.plugin.java.JavaPlugin;

public class ChallengeMe extends JavaPlugin{

    public void onEnable() {
        this.getLogger().info(this.getName() + " has been enabled");
    }

    public void onDisable() {
        this.getLogger().info(this.getName() + " has been disabled");
    }
}
