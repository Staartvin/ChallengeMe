package plugins.challengeme.config;

import org.bukkit.configuration.file.FileConfiguration;
import plugins.challengeme.ChallengeMe;

/**
 * Represents a config file.
 */
public abstract class AbstractConfig {

    private plugins.challengeme.config.YamlFile configFile;
    private ChallengeMe plugin;

    public AbstractConfig(ChallengeMe instance) {
        setPlugin(instance);
    }

    /**
     * Create a new config file.
     */
    public void createNewFile() {
        configFile = new YamlFile(plugin, getFileName(), getFileName());

        plugin.debugMessage("File loaded (" + getFileName() + ")");
    }


    /**
     * Get the YML file.
     *
     * @return
     */
    public FileConfiguration getConfig() {
        if (configFile != null) {
            return configFile;
        }

        return null;
    }

    /**
     * Reload the YML file.
     */
    public void reloadConfig() {
        if (configFile != null) {
            configFile.reloadFile();
        }
    }

    /**
     * Save the YML file.
     */
    public void saveConfig() {
        if (configFile == null) {
            return;
        }

        configFile.saveFile();
    }

    /**
     * Load the YML file.
     */
    public void loadConfig() {
        this.createNewFile();
    }

    public abstract String getFileName();

    public ChallengeMe getPlugin() {
        return plugin;
    }

    public void setPlugin(ChallengeMe plugin) {
        this.plugin = plugin;
    }
}
