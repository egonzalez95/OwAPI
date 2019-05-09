import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OwAPI {

    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        Utils util = new Utils();

        //  flow control.
        boolean done = false;
        while (!done) {
            System.out.print(">> Enter the profile username for the user you are searching for (CASE/SYMBOL SENSITIVE): ");
            String profile = kb.nextLine();
            try {

                // Overwatch Profile Prelink
                String preLink = "https://playoverwatch.com/en-us/career/";

                // Platforms
                List<String> platforms = new ArrayList<>();
                platforms.add("xbl");
                platforms.add("pc");
                platforms.add("psn");

                boolean foundFlag = false;
                for (String pf : platforms) {
                    StatEngine SE = new StatEngine(util.fillURL(preLink + pf + "/" + profile));
                    if (SE.isFound()) {
                        foundFlag = true;
                        System.out.println("Profile found on " + pf);
                        SE.printBasicProfileInformation();
                        if (!SE.isPrivate()){
                            // Print more info.
                            SE.printPublicProfileInformation();
                        }
                    }
                }
                if (!foundFlag) {
                    System.out.println(">> User was not found, " +
                            "please check the profile name again and confirm it is the account you're looking for.");
                }
            } catch (NullPointerException NPE) {
                // Lets go ahead and do nothing.
            }

            System.out.print(">> Would you like to search again? ");
            String userDoneHuh = kb.nextLine();

            // lazy way of determining if user is done.
            done = userDoneHuh.contains("n") || userDoneHuh.contains("N");
        }

        System.out.println("Thanks for using this tool! https://github.com/egonzalez95");
    }
}
