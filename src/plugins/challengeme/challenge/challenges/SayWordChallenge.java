package plugins.challengeme.challenge.challenges;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This challenge checks whether a specific word has been said by a player. The first player to say the word wins.
 */
public class SayWordChallenge extends AbstractChallenge implements Listener {

    private List<String> words = Arrays.asList("mackerel", "eventually", "oopsiedaisy", "teletubbie", "utter gold " +
            "mine");

    private String chosenWord;

    @Override
    public void onRegister() {
        System.out.println("Register CHALLENGE");

        // Create default list of words if it does not exist.
        this.getChallengeDataFile().getConfig().addDefault("words", words);
        this.getChallengeDataFile().getConfig().options().copyDefaults(true);

        // Register myself as a listener
        this.getChallengeMePlugin().getServer().getPluginManager().registerEvents(this, this.getChallengeMePlugin());
    }

    @Override
    public boolean onLoad() {
        System.out.println("Load CHALLENGE");


        words = this.getChallengeDataFile().getConfig().getStringList("words");

        Object chosenObject = getRandomElement(words);

        // List of words is empty!
        if (chosenObject == null) {
            this.sendConsoleMessage("There are no words defined in your words list!");
            return false;
        }

        chosenWord = chosenObject.toString();

        return chosenWord != null;
    }

    @Override
    public void onStart() {
        System.out.println("Start CHALLENGE");

        this.broadcastMessage(String.format("The first one to type '%s' wins!", chosenWord));
    }

    @Override
    public void onEnd() {
        System.out.println("Timeout CHALLENGE");

        chosenWord = null;
    }

    @Override
    public boolean onUnload() {
        System.out.println("Unload CHALLENGE");

        return true;
    }

    @Override
    public String getName() {
        return "Say a word";
    }

    @Override
    public String getAuthor() {
        return "Staartvin";
    }

    @Override
    public int getVersion() {
        return 1;
    }

    @Override
    public int getChallengeDuration() {
        return 30;
    }

    @EventHandler()
    public void onPlayerTalk(AsyncPlayerChatEvent event) {
        // Player talked
        Player player = event.getPlayer();

        String message = event.getMessage();

        if (message.equals(chosenWord)) {
            this.broadcastMessage(String.format("%s said the word first!", player.getName()));

            // Complete challenge.
            this.onComplete(player.getUniqueId());
        }
    }
}
