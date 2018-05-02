package plugins.challengeme;

import org.bukkit.plugin.java.JavaPlugin;

public class ChallengeMe extends JavaPlugin{

    private plugins.challengeme.config.ConfigHandler configHandler;

    public void onEnable() {

        setConfigHandler(new plugins.challengeme.config.ConfigHandler(this));

        // Load config files.
        loadFiles();

        this.getLogger().info(this.getName() + " has been enabled");
    }

    public void onDisable() {
        this.getLogger().info(this.getName() + " has been disabled");
    }

    private void loadFiles() {
        configHandler.loadConfig();

        this.debugMessage("All files are loaded.");
    }

    public void debugMessage(String debugMessage) {
        this.getLogger().info("[DEBUG] " + debugMessage);
    }

    public plugins.challengeme.config.ConfigHandler getConfigHandler() {
        return configHandler;
    }

    public void setConfigHandler(plugins.challengeme.config.ConfigHandler configHandler) {
        this.configHandler = configHandler;
    }
}
