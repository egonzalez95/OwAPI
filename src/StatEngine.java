import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.HashMap;


public class StatEngine {

    // Webpage stats
    private Document webpage;
    private String wpURL;

    // Basic User Profile Stats
    private String username;
    private boolean privacyhuh;
    private String rank;
    private String endorsementLevel;
    private HashMap<String, String> endorsements = new HashMap<>();

    // Advanced stats for the player if it is a public profile

    private PlayerStats data;

    // Constructor
    StatEngine(String wplink){
        try {
            // Populate the webpage and all of the statistics
            this.wpURL = wplink;
            this.webpage = Jsoup.connect(this.wpURL).get();
            this.statify();
        }

        // catch any errors with the input.
        catch (IOException e) {
            System.out.println("Bad IO at StatEngine input: " + wplink);
        }
    }

    // Master method that populates all player stats.
    private void statify() {
        // Grab the username and profile privacy.
        this.grabProfileIdentifiers();

        if (privacyhuh){
            System.out.println("User " + username + " has a private profile.");

            // Let the user know that we have SOME information.
            System.out.println("What we do know:");

        }
        else {
            this.data = new PlayerStats(this.username);
        }

        this.basicProfileDataDebugger();
    }

    /*
        Grab a profiles basic identifiers.
            - Profile URL
            - Profile username
            - Profile privacy
            - Rank
            - Endorsement Level
                - Endorsement breakdowns
     */
    private void grabProfileIdentifiers(){
        // Used to temporarily hold the CSS Query.
        String cssQuery;

        // Grab Profile Username
        cssQuery = "h1.header-masthead";
        Element usernameElement = webpage.select(cssQuery).first();
        this.username = this.stripHTML(usernameElement.toString());

        // Grab Profile Privacy
        cssQuery = "p.masthead-permission-level-text";
        Element privacyElement = webpage.select(cssQuery).first();
        String privacy = this.stripHTML(privacyElement.toString());
        if (privacy.contains("Public Profile")) {
            privacyhuh = false;
        }
        else {
            privacyhuh = true;
        }

        // Grab Profile Rank
        // TODO: determine actual rank
        cssQuery = "div.u-vertical-center";
        Element rankElement = webpage.select(cssQuery).first();
        this.rank = this.stripString(this.stripHTML(rankElement.toString()));

        // Grab endorsement information
        // endorsement level
        cssQuery = "div.u-center";
        Element eLvlElement = webpage.select(cssQuery).first();
        this.endorsementLevel = this.stripString(this.stripHTML(eLvlElement.toString()));

        // Grab all 3 endorsement value categories.
        // Use next element sibling to go trough the CSS elements.
        cssQuery = "svg.EndorsementIcon-border";
        Element endorsementCategoriesElement = webpage.select(cssQuery).first();
        //CSS: "EndorsementIcon-border EndorsementIcon-border--shotcaller"
        String shotcallerPercent = endorsementCategoriesElement.attr("data-value");
        this.endorsements.put("Shotcaller", shotcallerPercent);
        //CSS: "EndorsementIcon-border EndorsementIcon-border--teammate"
        endorsementCategoriesElement = endorsementCategoriesElement.nextElementSibling();
        String teammatePercent = endorsementCategoriesElement.attr("data-value");
        this.endorsements.put("Teammate", teammatePercent);
        //CSS: "EndorsementIcon-border EndorsementIcon-border--sportsmanship"
        endorsementCategoriesElement = endorsementCategoriesElement.nextElementSibling();
        String sportsmanshipPercent = endorsementCategoriesElement.attr("data-value");
        this.endorsements.put("Sportsmanship", sportsmanshipPercent);

        // Uncomment below to debug this process.
        //this.basicProfileDataDebugger();

        Element profileElement = webpage.select("div.masthead").first();
        //System.out.println("\n\n\n" + profileElement);
    }

    // Utility that strips HTML markup from code.
    private String stripHTML(String code){
        // in return: strip HTML markup
        return code.replaceAll("<[^>]*>", "");
    }

    // Utility that strips whitespace and new lines from a string.
    private String stripString(String text){
        // in return: trip linebreaks / spaces / new lines
        return text.replaceAll("\\r| |\\n", "");
    }

    private void basicProfileDataDebugger(){
        System.out.println("Success ["+ wpURL + "]");
        System.out.println("Username: " + this.username);
        System.out.println("Private Account? " + privacyhuh);
        System.out.println("Rank: " + this.rank);
        System.out.println("Endorsement Level: " + this.endorsementLevel);
        for (String key : this.endorsements.keySet()){
            System.out.println(" - " + key + " : " + this.endorsements.get(key));
        }
    }


    /*

        Actual API stuff ###########################################

     */

    // Return the profile url
    public String getProfileURL(){
        return this.wpURL;
    }

    // Return the username
    public String getUsername(){
        return this.username;
    }

    // Is the account private?
    public boolean isPrivate(){
        return this.privacyhuh;
    }

    // return users rank
    public String getRank(){
        return this.rank;
    }

    // return the users endorsement level
    public String getEndorsementLevel(){
        return this.endorsementLevel;
    }

    // return endorsement level distribution
    public String endorsementDistribution(){
        String rv = "";
        for (String key : this.endorsements.keySet()){
            rv += (key + " : " + this.endorsements.get(key) + "\n");
        }
        return rv;
    }

    // Return endorsement level by type.
    // Shotcaller, Teammate, Sportsmanship.
    public String getSpecificEndorsementDistribution(String type){
        return this.endorsements.get(type);
    }
}
