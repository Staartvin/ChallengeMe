package plugins.challengeme.challenge.loader;

import plugins.challengeme.ChallengeMe;
import plugins.challengeme.challenge.challenges.AbstractChallenge;
import plugins.challengeme.challenge.challenges.SayWordChallenge;

import java.util.Random;

public class ChallengeLoader {

    private java.util.List<AbstractChallenge> challengeList = new java.util.ArrayList<>();

    public void registerChallenge(AbstractChallenge challenge) throws IllegalArgumentException {

        if (challenge == null) {
            throw new IllegalArgumentException("Challenge cannot be null.");
        }

        // Challenge is already registered.
        if (isChallengeRegistered(challenge)) {
            return;
        }

        // Add the challenge to the list of registered challenges
        challengeList.add(challenge);

        // Load new data file.
        challenge.getChallengeDataFile().loadConfig();

        // Notify challenge of registration
        challenge.onRegister();

        ChallengeMe.getInstance().debugMessage("Challenge " + challenge.getName() + " is registered.");
    }

    public boolean isChallengeRegistered(final String challengeName) throws IllegalArgumentException {

        if (challengeName == null) {
            throw new IllegalArgumentException("Name of challenge cannot be null.");
        }

        return challengeList.stream().anyMatch(challenge -> challenge.getName().equalsIgnoreCase(challengeName));
    }

    public boolean isChallengeRegistered(AbstractChallenge challenge) throws IllegalArgumentException {
        if (challenge == null) {
            throw new IllegalArgumentException("Challenge cannot be null.");
        }

        return this.isChallengeRegistered(challenge.getName());
    }

    public void registerInternalChallenges() {
        // TODO: register challenges that ChallengeMe delivers itself.
        this.registerChallenge(new SayWordChallenge());
    }

    public AbstractChallenge getRandomChallenge() {
        return challengeList.get(new Random().nextInt(challengeList.size()));
    }

}
