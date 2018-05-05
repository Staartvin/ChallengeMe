package plugins.challengeme.challenge.challenges;

import org.bukkit.Bukkit;
import plugins.challengeme.ChallengeMe;
import plugins.challengeme.challenge.challenges.challengeconfig.ChallengeConfig;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * This class represents a challenge that players can complete when it is started. ChallengeMe only loads a challenge
 * when it is needed (lazy-loading). First, the onLoad() method is called to load the challenge. This method can be
 * used to set up the challenge. Next, onStart() is called to start the challenge. ChallengeMe will call onEnd()
 * automatically when the challenge that was started has reached its timeout and must be stopped. However, a
 * challenge can call the onComplete() method to signal that the challenge is completed by a player. Lastly,
 * ChallengeMe calls onUnload() to
 * allow the challenge to unload before it is deleted and reloaded when needed.
 */
public abstract class AbstractChallenge {

    private ChallengeConfig challengeConfig;

    // Whether this challenge is completed by a player or not.
    private boolean isCompleted = false;

    // Whether this challenge is currently running or not.
    private boolean isRunning = false;

    /**
     * Called by ChallengeMe when the challenge is registered for the first time. This method is only called once and
     * should be used for setting up config files, connection to database or any other stuff you don't want to repeat
     * while loading and unloading a challenge.
     */
    public abstract void onRegister();

    /**
     * Called by ChallengeMe when the challenge will need to start soon. Use this time to load the challenge. You
     * should return whether the challenge was successfully loaded. If it is not, the challenge will not be started
     * (onStart() will not be called).
     * @return whether the challenge was successfully loaded.
     */
    public abstract boolean onLoad();

    /**
     * Called by ChallengeMe when the challenge may be started. The challenge cannot be aborted any more.
     */
    public abstract void onStart();

    /**
     * Called by ChallengeMe to signify that the challenge was ended due to a timeout and should now be aborted.
     */
    public abstract void onEnd();

    /**
     * This method should be called by this challenge to signal that the challenge was completed by a player.
     * @param uuid UUID of the player that completed the challenge
     */
    public void onComplete(UUID uuid) {
        isCompleted = true;
        ChallengeMe.getInstance().getChallengeScheduler().completeChallenge(this, uuid);
    }

    /**
     * Called by ChallengeMe when the challenge is done (this method call is always preceded by a call to either
     * {@link #onEnd()} or {@link #onComplete(UUID)}) and should be unloaded.
     * @return whether the challenge was correctly unloaded.
     */
    public abstract boolean onUnload();

    /**
     * Get the name of this Challenge.
     * @return name of challenge
     */
    public abstract String getName();

    /**
     * Get the main author of this Challenge.
     * @return author
     */
    public abstract String getAuthor();

    /**
     * Get the version of this Challenge.
     * @return integer version
     */
    public abstract int getVersion();

    /**
     * Get the data file that can be used to store data for this challenge.
     * @return {@link ChallengeConfig} used to store and retrieve persistent data.
     */
    public ChallengeConfig getChallengeDataFile() {
        if (challengeConfig == null) {
            challengeConfig = new ChallengeConfig(getChallengeMePlugin(), this);
        }

        return challengeConfig;
    }

    public ChallengeMe getChallengeMePlugin() {
        return (ChallengeMe) Bukkit.getPluginManager().getPlugin("ChallengeMe");
    }

    /**
     * Get a random element from a given list. Useful for getting random items.
     * @param list List to get a random element out of.
     * @return a random element from the given list. Returns null if the given list is empty.
     */
    public Object getRandomElement(List<?> list) {

        if (list.size() < 1) {
            return null;
        }

        int index = new Random().nextInt(list.size());
        return list.get(index);
    }

    /**
     * Send a message in the console using the name (of this challenge) as a prefix.
     * @param message Message to send in the console.
     */
    public void sendConsoleMessage(String message) {
        this.getChallengeMePlugin().getLogger().info("[" + this.getName() + "] " + message);
    }

    /**
     * Broadcast a message to all online players.
     * @param message Message to send.
     */
    public void broadcastMessage(String message) {
        this.getChallengeMePlugin().getServer().broadcastMessage(this.getName() + ": " + message);
    }

    /**
     * Get the total duration of this challenge in seconds.
     * @return maximum duration of challenge.
     */
    public abstract int getChallengeDuration();

    /**
     * Check whether a player has completed this challange.
     * @return true if a player has completed it.
     */
    public boolean isCompleted() {
        return isCompleted;
    }

    /**
     * Whehter this challenge is currently running.
     * @return true if it is active, false otherwise.
     */
    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

}
