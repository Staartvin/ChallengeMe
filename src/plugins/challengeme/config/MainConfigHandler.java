package plugins.challengeme.config;

/**
 * This handler performs all communication between the main config and the plugin.
 */
public class MainConfigHandler extends plugins.challengeme.config.AbstractConfig {

    public MainConfigHandler(final plugins.challengeme.ChallengeMe instance) {
        setPlugin(instance);
    }

    private static String IS_CHALLENGE_INTERVAL_RANDOM = "challenges interval is random";
    private static String TIME_BETWEEN_CHALLENGES = "time between challenges";
    private static String DISABLED_CHALLENGES = "disabled challenges";


    @Override
    public void loadConfig() {

        super.loadConfig();

        this.getConfig().options().header("This is the main config file of ChallengeMe" +
                "\n" +
                "\n" +
                "'Time between challenges' is the time (in seconds) between two challenges. A lower value " +
                "means challenges more frequently, a higher value means challenges less frequently." +
                "\n" +
                "'Challenges interval is random' can either be true or false. If it is true, challenges occur at " +
                "random intervals with a time between them of at least 'time between challenges'. If it is " +
                "false, a new challenge will be run deterministically every x seconds (depending on the interval)." +
                "\n" +
                "'Disabled challenges' is a list of disabled challenges (by name). These cannot be executed " +
                "automatically or manually.");


        this.getConfig().addDefault(TIME_BETWEEN_CHALLENGES, 60);
        this.getConfig().addDefault(IS_CHALLENGE_INTERVAL_RANDOM, false);

        this.getConfig().addDefault(DISABLED_CHALLENGES, java.util.Arrays.asList("Disabled " +
                "challenge 1", "Disabled challenge 2"));

        this.getConfig().options().copyDefaults(true);

        saveConfig();
    }

    @Override
    public String getFileName() {
        return "config.yml";
    }

    // ------------------------ Specific methods are implemented below ------------------------------

    /**
     * Check whether the time between to challenges is random or not.
     * @return true if it is random, false if it is deterministic.
     */
    public boolean isChallengeIntervalRandom() {
        return this.getConfig().getBoolean(IS_CHALLENGE_INTERVAL_RANDOM, false);
    }

    /**
     * Get the (minimum) time between two challenges being executed automatically.
     * @return minimum time in seconds.
     */
    public int getTimeBetweenChallenges() {
        return this.getConfig().getInt(TIME_BETWEEN_CHALLENGES, 60);
    }

    /**
     * Get a list of disabled challenges.
     * @return Names of disabled challenges.
     */
    public java.util.List<String> getDisabledChallenges() {
        return this.getConfig().getStringList(DISABLED_CHALLENGES);
    }


}
