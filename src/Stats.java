import java.util.HashMap;

public class Stats {

    // Statistics for user.
    private String username;
    private GameMode mode;
    private HashMap<String, String> playerStats = new HashMap<>();
    private HashMap<String, HashMap<String, String>> charStats = new HashMap<>();

    // Constructor
    public Stats(String username, GameMode mode){
        this.username = username;
        this.mode = mode;
    }

    // Add a stat to our players queue
    public void addPlayerStat(String key, String value){
        // Add the stat to the player stat map
        this.playerStats.put(key, value);
    }

    // Add a stat to our players characters queue
    public void addPlayerChar(String character, String key, String value){
        // Check if the character was already added
        if (this.charStats.containsKey(character)){
            // if so, add the character stat map
            this.charStats.get(character).put(key,value);
        }

        else {
            // If not, add the character and the stat map
            HashMap<String, String> stat = new HashMap<>();
            this.playerStats.put(character, stat.put(key, value));
        }
    }

    // Return the value for a specific players stat
    public String pullPlayerStat(String key){
        return this.playerStats.get(key);
    }

    // Return the value for a specific player characters stat
    public String pullCharacterStat(String character, String stat){
        return this.charStats.get(character).get(stat);
    }

    // Return all of our player stats
    public HashMap returnAllPlayerStats(){
        return this.playerStats;
    }

    // Return all of our character stats
    public HashMap returnAllPlayerCharStats(){
        return this.charStats;
    }
}
