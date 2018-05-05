package plugins.challengeme.scheduler;

import plugins.challengeme.ChallengeMe;
import plugins.challengeme.challenge.challenges.AbstractChallenge;

import java.util.UUID;

public class ChallengeScheduler {

    private long lastChallengeStarted;
    private long lastChallengeEnded;

    public AbstractChallenge scheduleChallenge() {

        AbstractChallenge randomChallenge = ChallengeMe.getInstance().getChallengeLoader().getRandomChallenge();

        // Try to load the challenge.
        boolean loadedCorrectly = randomChallenge.onLoad();

        if (!loadedCorrectly) {
            ChallengeMe.getInstance().getLogger().warning("Could not load Challenge '" + randomChallenge.getName() +
                    "' by " + randomChallenge.getAuthor());
            return null;
        }

        ChallengeMe.getInstance().getServer().broadcastMessage("Challenge '" + randomChallenge.getName() + "' will " +
                "start shortly.");

        // Start challenge.
        randomChallenge.onStart();

        // Set the challenge to active mode.
        randomChallenge.setRunning(true);

        // Run a task that terminates the challenge if it was not completed yet.
        ChallengeMe.getInstance().getServer().getScheduler().runTaskLater(ChallengeMe.getInstance(), () -> {

            // Challenge is not running anymore.
            if (!randomChallenge.isRunning()) {
                return;
            }

            // End challenge.
            endChallenge(randomChallenge);

        }, randomChallenge.getChallengeDuration() * 20);

        lastChallengeStarted = System.currentTimeMillis();

        return randomChallenge;
    }

    public void completeChallenge(AbstractChallenge completedChallenge, UUID uuid) {
        endChallenge(completedChallenge);
    }

    public void endChallenge(AbstractChallenge challengeToEnd) {
        // Signal challenge that it should stop if it was not completed.
        if (!challengeToEnd.isCompleted()) {
            challengeToEnd.onEnd();
        }

        // Stop challenge from running.
        challengeToEnd.setRunning(false);

        // Notify challenge that it is time to quit.
        challengeToEnd.onUnload();

        lastChallengeEnded = System.currentTimeMillis();
    }

}
