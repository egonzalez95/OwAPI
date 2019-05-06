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
    private String compSR;
    private String compRank;
    private String endorsementLevel;
    private HashMap<String, String> endorsements = new HashMap<>();

    // For our misc. helpers
    private Utils util = new Utils();

    private PlayerStats data;

    // Constructor
    StatEngine(String wplink) {
        try {
            // Populate the webpage and all of the statistics
            this.wpURL = wplink;
            this.webpage = Jsoup.connect(this.wpURL).get();
            this.statify();
        }

        // catch any errors with the input.
        catch (IOException e) {
            System.out.println("** Bad IO at StatEngine input: " + wplink);
        }
    }

    // Master method that populates all player stats.
    private void statify() {
        // Grab the username and profile privacy.
        this.grabProfileIdentifiers();

        if (privacyhuh) {
            // Can't run advanced search.

        } else {
            // Run advanced search.
            this.data = new PlayerStats(this.username);
            this.runFullPlayerStatCollection();
        }

        // Uncomment to debug.
        //this.basicProfileDataDebugger();
    }
    
    /*
        Actual API stuff ###########################################
     */

    // Return the profile url
    public String getProfileURL() {
        return this.wpURL;
    }

    // Return the username
    public String getUsername() {
        return this.username;
    }

    // Is the account private?
    public boolean isPrivate() {
        return this.privacyhuh;
    }

    // return users rank
    public String getRank() {
        return this.rank;
    }

    // return the users endorsement level
    public String getEndorsementLevel() {
        return this.endorsementLevel;
    }

    // return endorsement level distribution
    public String endorsementDistribution() {
        String rv = "";
        for (String key : this.endorsements.keySet()) {
            rv += (key + " : " + this.endorsements.get(key) + "\n");
        }
        return rv;
    }

    // Return endorsement level by type.
    // Shotcaller, Teammate, Sportsmanship.
    public String getSpecificEndorsementDistribution(String type) {
        return this.endorsements.get(type);
    }

    // Return the users SR
    public String getCurrentSeasonSR() {
        return this.compSR;
    }

    // Return the users Competitive Rank
    public String getCurrentSeasonRank() {
        return this.compRank;
    }


    /*
        ENGINE DATA COLLECTION ALGORITHMS
        - THE FOLLOWING SECTION IS PRETTY LONG BUT AUTOMATES DATA COLLECTION.
        - WE CAN COLLECT SOME PRIVATE ACCOUNT INFORMATION.
        - WE CAN COLLECT MOST PUBLIC ACCOUNT INFORMATION.

    */

    /*
        ALL USER PROFILES, PUBLIC AND PRIVATE
    */

    /*
    Grab a profiles basic identifiers.
        - Profile URL
        - Profile username
        - Profile privacy
        - Rank
        - Endorsement Level
            - Endorsement breakdowns
    */
    private void grabProfileIdentifiers() {
        // Used to temporarily hold the CSS Query.
        String cssQuery;

        try {
            // Grab Profile Username
            cssQuery = "h1.header-masthead";
            Element usernameElement = webpage.select(cssQuery).first();
            this.username = this.util.stripHTML(usernameElement.toString());

            // Grab Profile Privacy
            cssQuery = "p.masthead-permission-level-text";
            Element privacyElement = webpage.select(cssQuery).first();
            String privacy = this.util.stripHTML(privacyElement.toString());
            privacyhuh = !privacy.contains("Public Profile");

            // Grab Profile Rank and calculate it.

            // Portrait
            cssQuery = "div.player-level";
            Element portraitElement = webpage.select(cssQuery).first();
            String portrait = portraitElement.attr("style");

            // Star + code tweaks
            // Users that have never earned a star won't have this CSS attribute.
            String star = "";
            try {
                // If we find it great!
                cssQuery = "div.player-rank";
                Element starElement = webpage.select(cssQuery).first();
                star = starElement.attr("style");
            } catch (NullPointerException NPE) {
                // We can leave it initialized to empty.
            }

            // Rank number
            cssQuery = "div.u-vertical-center";
            Element rankElement = webpage.select(cssQuery).first();
            String baseRank = this.util.stripString(this.util.stripHTML(rankElement.toString()));


            //rankify all three pieces of data.
            this.rank = this.util.rankify(baseRank, portrait, star);
        } catch (NullPointerException NPE) {
            // Uncomment below to debug.
            //System.out.println("** User does not exist.");
        }

        try {
            // Grab Competative Rank
            cssQuery = "div.competitive-rank";
            Element compRankElement = webpage.select(cssQuery).first();
            this.compRank = this.util.determineCompetitiveRank(compRankElement.toString());

            // Grab Competative SR
            Element compSRElement = webpage.select(cssQuery).first();
            this.compSR = this.util.stripString(this.util.stripHTML(compSRElement.toString()));
        } catch (NullPointerException NPE) {
            // Uncomment below to debug.
            //System.out.println("** User has no Competitive placement this season.");
            this.compRank = "Not placed.";
            this.compSR = "Not placed.";
        }

        try {
            // Grab endorsement information
            // endorsement level
            cssQuery = "div.u-center";
            Element eLvlElement = webpage.select(cssQuery).first();
            this.endorsementLevel = this.util.stripString(this.util.stripHTML(eLvlElement.toString()));

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
        } catch (NullPointerException NPE0) {
            // Uncomment below to debug.
            //System.out.println("** User has not been endorsed.");

        }
        // Uncomment below to debug this process.
        //this.basicProfileDataDebugger();
    }

    private void basicProfileDataDebugger() {
        System.out.println("Success reaching and connecting to [" + wpURL + "]");
        System.out.println("Username: " + this.username);
        System.out.println("Private Account? " + privacyhuh);
        System.out.println("Rank: " + this.rank);
        System.out.println("Competitive Rank: " + this.compRank);
        System.out.println("Competitive Season Rank: " + this.compSR);
        System.out.println("Endorsement Level: " + this.endorsementLevel);
        for (String key : this.endorsements.keySet()) {
            System.out.println(" - " + key + " : " + this.endorsements.get(key));
        }
    }

    /*
        PUBLIC USER PROFILES
    */

    public void runFullPlayerStatCollection() {
        // Nothing yet :)
    }

}
