package plugins.challengeme.challenge.challenges.challengeconfig;

import plugins.challengeme.ChallengeMe;
import plugins.challengeme.challenge.challenges.AbstractChallenge;
import plugins.challengeme.config.AbstractConfig;

public class ChallengeConfig extends AbstractConfig {

    private AbstractChallenge challenge;

    public ChallengeConfig(ChallengeMe instance, AbstractChallenge challenge) {
        super(instance);
        this.challenge = challenge;
    }

    @Override
    public String getFileName() {
        return "/challengedata/" + challenge.getName();
    }

    /**
     * Store data in the form of a key-value pair.
     * @param key Key
     * @param value Value
     */
    public void setData(String key, Object value) {
        this.getConfig().set(key, value);
        this.saveConfig();
    }

    /**
     * Get the integer value for a given key
     * @param key Key
     * @return integer value or -1 if it does not exist.
     */
    public Integer getIntegerData(String key) {
        return this.getConfig().getInt(key, -1);
    }

    /**
     * Get the double value for a given key
     * @param key Key
     * @return double value or -1 if it does not exist.
     */
    public Double getDoubleData(String key) {
        return this.getConfig().getDouble(key, -1.0);
    }

    /**
     * Get the string value for a given key
     * @param key Key
     * @return string value or empty string if it does not exist.
     */
    public String getStringData(String key) {
        return this.getConfig().getString(key, "");
    }

    /**
     * Get the object value for a given key
     * @param key Key
     * @return object value or null if it does not exist.
     */
    public Object getObjectData(String key) {
        return this.getConfig().get(key, null);
    }
}
