package plugins.challengeme;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import plugins.challengeme.challenge.loader.ChallengeLoader;
import plugins.challengeme.config.MainConfigHandler;
import plugins.challengeme.scheduler.ChallengeScheduler;

public class ChallengeMe extends JavaPlugin{

    private MainConfigHandler configHandler;
    private ChallengeLoader challengeLoader;
    private ChallengeScheduler challengeScheduler;

    public void onEnable() {

        setConfigHandler(new MainConfigHandler());

        setChallengeLoader(new ChallengeLoader());

        setChallengeScheduler(new ChallengeScheduler());

        // Load config files.
        loadFiles();

        // Register default challenges
        this.getChallengeLoader().registerInternalChallenges();

        // Schedule a new challenge after 10 seconds.
        this.getServer().getScheduler().runTaskLater(this, new Runnable() {
            @Override
            public void run() {
                getChallengeScheduler().scheduleChallenge();
            }
        }, 20 * 10);

        this.getLogger().info(this.getName() + " has been enabled");
    }

    public void onDisable() {
        this.getLogger().info(this.getName() + " has been disabled");
    }

    public static ChallengeMe getInstance() {
        return (ChallengeMe) Bukkit.getPluginManager().getPlugin("ChallengeMe");
    }

    private void loadFiles() {
        configHandler.loadConfig();

        this.debugMessage("All files are loaded.");
    }

    public void debugMessage(String debugMessage) {
        this.getLogger().info("[DEBUG] " + debugMessage);
    }

    public plugins.challengeme.config.MainConfigHandler getConfigHandler() {
        return configHandler;
    }

    public void setConfigHandler(plugins.challengeme.config.MainConfigHandler configHandler) {
        this.configHandler = configHandler;
    }

    public ChallengeLoader getChallengeLoader() {
        return challengeLoader;
    }

    public void setChallengeLoader(ChallengeLoader challengeLoader) {
        this.challengeLoader = challengeLoader;
    }

    public ChallengeScheduler getChallengeScheduler() {
        return challengeScheduler;
    }

    public void setChallengeScheduler(ChallengeScheduler challengeScheduler) {
        this.challengeScheduler = challengeScheduler;
    }
}
