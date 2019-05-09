import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// Holds all of a players statistics
public class PlayerStats {

    private String profileUsername; // username
    private String profileURL;      // profiles URL
    private boolean privateAccount; // account privacy
    private String overallRank;     // overall account rank
    private String compRank;        // competitive ranking
    private String compSR;          // competitive SR
    private String endorsementLvl;  // endorsement level, 1-5.
    private HashMap<String, String> endorsements = new HashMap<>();   // endorsement by category breakdown

    // additional information collected after it is determined account is public
    private String totalWins;       // total account wins

    // Constructor needs at least a username.
    public PlayerStats(String username, String URL) {
        this.profileUsername = username;
        this.profileURL = URL;
    }

    public String getUsername() {
        return profileUsername;
    }

    public boolean isPrivateAccount() {
        return privateAccount;
    }

    public void setPrivateAccount(boolean privateAccount) {
        this.privateAccount = privateAccount;
        //System.out.println("Set privacy for " + this.profileUsername);
    }

    public String getOverallRank() {
        return overallRank;
    }

    public void setOverallRank(String overallRank) {
        this.overallRank = overallRank;
        //System.out.println("Set rank for " + this.profileUsername);
    }

    public String getCompRank() {
        return compRank;
    }

    public void setCompRank(String compRank) {
        this.compRank = compRank;
        //System.out.println("Set competitive rank for " + this.profileUsername);
    }

    public String getCompSR() {
        return compSR;
    }

    public void setCompSR(String compSR) {
        this.compSR = compSR;
        //System.out.println("Set SR for " + this.profileUsername);
    }

    public String getEndorsementLvl() {
        return endorsementLvl;
    }

    public void setEndorsementLvl(String endorsementLvl) {
        this.endorsementLvl = endorsementLvl;
        //System.out.println("Set endorsement level for " + this.profileUsername);
    }

    public HashMap getEndorsements() {
        return endorsements;
    }

    public void setEndorsements(HashMap endorsements) {
        this.endorsements = endorsements;
    }

    public void putEndorsement(String key, String value) {
        this.endorsements.put(key, value);
        //System.out.println("Put endorsement for " + this.profileUsername);
    }

    public String getTotalWins() {
        return totalWins;
    }

    public void setTotalWins(String totalWins) {
        this.totalWins = totalWins;
        //System.out.println("Set wins for " + this.profileUsername);
    }

    public void printBasic() {
        System.out.println("Profile Username: " + this.profileUsername);
        System.out.println("Profile Privacy: " + this.privateAccount);
        System.out.println("Profile Rank: " + this.overallRank);
        System.out.println("Profile Competitive Rank: " + this.compRank);
        System.out.println("Profile Competitive Season Rank: " + this.compSR);
        System.out.println("Profile Endorsement Level: " + this.endorsementLvl);
        System.out.println("Profile Endorsement Breakdown:");
        for (String type : this.endorsements.keySet()) {
            System.out.println(" -> " + type + ": " + this.endorsements.get(type));
        }
    }

    public void printPublicInfo() {
        System.out.println("Profile total wins: " + this.totalWins);
    }
}
