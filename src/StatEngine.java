import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.HashMap;

public class StatEngine {

    // Webpage stats
    private Document webpage;
    private String wpURL;

    // Basic Player Stats
    private PlayerStats userData;

    // Hold basic user stats to add them on later.
    private String username;
    private boolean privacyhuh;

    // For our misc. helpers
    private Utils util = new Utils();


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

        // create the users basic stats object
        this.userData = new PlayerStats(this.username, this.wpURL);

        // Set the privacy.
        this.userData.setPrivateAccount(this.privacyhuh);

        if (this.username != null) {
            // Grab the rest of the attributes
            this.grabProfileAttributes();

            // If it is a private account, there is not much more we can do.
            if (privacyhuh) {
                // Can't run advanced search.

            } else {
                // Run advanced search.
                this.runFullPlayerStatCollection();
            }
        } else {
            // do nothing for now.
        }

        // Uncomment to debug.
        //this.basicProfileDataDebugger();
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

    private void grabProfileIdentifiers() {
        try {
            String cssQuery;

            // Grab Profile Username
            cssQuery = "h1.header-masthead";
            Element usernameElement = this.webpage.select(cssQuery).first();
            this.username = this.util.stripHTML(usernameElement.toString());

            // Grab Profile Privacy
            cssQuery = "p.masthead-permission-level-text";
            Element privacyElement = this.webpage.select(cssQuery).first();
            String privacy = this.util.stripHTML(privacyElement.toString());
            this.privacyhuh = !privacy.contains("Public Profile");
        } catch (NullPointerException NPE) {
            //System.out.println("Does not exist");
        }
    }

    /*
    Grab a profiles basic attributes.
        - Rank
        - Competitive Rank
        - Competitive SR
        - Endorsement Level
            - Endorsement breakdowns
    */
    private void grabProfileAttributes() {
        // Used to temporarily hold the CSS Query.
        String cssQuery;

        try {
            // Grab Profile Rank and calculate it.
            // Portrait
            cssQuery = "div.player-level";
            Element portraitElement = this.webpage.select(cssQuery).first();
            String portrait = portraitElement.attr("style");

            // Star + code tweaks
            // Users that have never earned a star won't have this CSS attribute.
            String star = "";
            try {
                // If we find it great!
                cssQuery = "div.player-rank";
                Element starElement = this.webpage.select(cssQuery).first();
                star = starElement.attr("style");
            } catch (NullPointerException NPE) {
                // We can leave it initialized to empty.
            }

            // Rank number
            cssQuery = "div.u-vertical-center";
            Element rankElement = this.webpage.select(cssQuery).first();
            String baseRank = this.util.stripString(this.util.stripHTML(rankElement.toString()));


            //rankify all three pieces of data.
            this.userData.setOverallRank(this.util.rankify(baseRank, portrait, star));
        } catch (NullPointerException NPE) {
            System.out.println("Unable to retrieve rank.");
        }


        try {
            // Grab Competative Rank
            cssQuery = "div.competitive-rank";
            Element compRankElement = webpage.select(cssQuery).first();
            this.userData.setCompRank(this.util.determineCompetitiveRank(compRankElement.toString()));

            // Grab Competative SR
            Element compSRElement = webpage.select(cssQuery).first();
            this.userData.setCompSR(this.util.stripString(this.util.stripHTML(compSRElement.toString())));
        } catch (NullPointerException NPE) {
            // Uncomment below to debug.
            //System.out.println("** User has no Competitive placement this season.");
            this.userData.setCompRank("Not placed.");
            this.userData.setCompSR("Not placed.");
        }

        try {
            // Grab endorsement information
            // endorsement level
            cssQuery = "div.u-center";
            Element eLvlElement = this.webpage.select(cssQuery).first();
            this.userData.setEndorsementLvl(this.util.stripString(this.util.stripHTML(eLvlElement.toString())));
            // Grab all 3 endorsement value categories.
            // Use next element sibling to go trough the CSS elements.
            cssQuery = "svg.EndorsementIcon-border";
            Element endorsementCategoriesElement = this.webpage.select(cssQuery).first();
            //CSS: "EndorsementIcon-border EndorsementIcon-border--shotcaller"
            String shotcallerPercent = endorsementCategoriesElement.attr("data-value");
            this.userData.putEndorsement("Shotcaller", shotcallerPercent);
            //CSS: "EndorsementIcon-border EndorsementIcon-border--teammate"
            endorsementCategoriesElement = endorsementCategoriesElement.nextElementSibling();
            String teammatePercent = endorsementCategoriesElement.attr("data-value");
            this.userData.putEndorsement("Teammate", teammatePercent);
            //CSS: "EndorsementIcon-border EndorsementIcon-border--sportsmanship"
            endorsementCategoriesElement = endorsementCategoriesElement.nextElementSibling();
            String sportsmanshipPercent = endorsementCategoriesElement.attr("data-value");
            this.userData.putEndorsement("Sportsmanship", sportsmanshipPercent);
        } catch (NullPointerException NPE) {
            // Uncomment below to debug.
            //System.out.println("** User has not been endorsed.");
        }
    }

    public void printBasicProfileInformation() {
        this.userData.printBasic();
    }

    public void printPublicProfileInformation() {
        this.userData.printPublicInfo();
    }

    /*
        PUBLIC USER PROFILES
    */

    private void runFullPlayerStatCollection() {
        String cssQuery;

        try {// User total wins
            cssQuery = "p.masthead-detail";
            Element eUserTotalWins = this.webpage.select(cssQuery).first();
            String wins = this.util.stripString(this.util.stripHTML(eUserTotalWins.toString()));
            // further stripping
            this.userData.setTotalWins(this.util.stripAlpha(wins));
        }
        catch(NullPointerException NPE){
            // do nothing.
        }
    }

    // is the username found?
    public boolean isFound() {
        return (username != null);
    }

    // returns account privacy
    public boolean isPrivate(){
        return this.privacyhuh;
    }
}
