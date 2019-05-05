import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// Holds all of a players statistics
public class PlayerStats {

    // username
    private String username;

    // stats for game modes
    private Stats quickplay;
    private Stats competative;

    PlayerStats(String username){
        this.username = username;
        this.quickplay = new Stats(this.username, GameMode.QUICKPLAY);
        this.competative = new Stats(this.username, GameMode.COMPETATIVE);
    }

    // Add a players stat
    public void addPlayerStat(String key, String value, GameMode mode){
        switch(mode){
            case QUICKPLAY: {
                this.quickplay.addPlayerStat(key, value);
                break;
            }
            case COMPETATIVE: {
                this.competative.addPlayerStat(key, value);
                break;
            }
            default:
                System.out.println("SYSTEM: Unable to add a players stat due to invalid game mode.");
                break;
        }
    }

    // Add a player characters stat
    public void addCharacterStat(String character, String key, String value, GameMode mode){
        switch(mode){
            case QUICKPLAY: {
                this.quickplay.addPlayerChar(character, key, value);
                break;
            }
            case COMPETATIVE: {
                this.competative.addPlayerChar(character, key, value);
                break;
            }
            default:
                System.out.println("SYSTEM: Unable to add a player character stat due to invalid game mode.");
                break;
        }
    }
}
