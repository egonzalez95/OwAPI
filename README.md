# Overwatch API
Blizzard has not released an actual API for Overwatch statistics, but rather, has allowed players to publicly view
profiles online and view their stats there. I plan to remedy this by creating a Java-based API that crawls the web-page and allows users
to pull specific data from PUBLIC/PRIVATE profiles. Keep in mind, that if Blizzard changes their profile page I will most
likely have to update this code. I will be using a Java based web-crawler using the JSoup library that I had previously
developed.

There are already API's created by other users so this is mostly a learning outcome.

This in no way rips from other projects that might be similar to this or might be names similarly.

Last note, this program is far from an API right now and will work as a stat generator tool.

## Running the API
To come. In developmental stages first, then functional API will be set.

For now, you can run this [locally](OwAPI.jar) if you have the latest version of java installed.

    In CMD prompt:
    
    java -jar OwAPI.jar

## Documentation
Documentation for the API can be found [HERE](DOCUMENTATION.md)

## Intended Outcomes
    - An easy-to-use API
    - Username text-file parser to process multiple usernames
    - Exporter to save the results
    
## Where is this project so far?
So far, the following output is displayed when entering a users profile. 
This will be fixed later on so that a username and platform suffice.


    :: Example run:
    
        >> Enter the profile username for the user you are searching for (CASE/SYMBOL SENSITIVE): Seagull-1894
        Profile found on pc
        Profile Username: Seagull
        Profile Privacy: false
        Profile Rank: 664
        Profile Competitive Rank: Grandmaster
        Profile Competitive Season Rank: 4212
        Profile Endorsement Level: 3
        Profile Endorsement Breakdown:
         -> Teammate: 0.38
         -> Sportsmanship: 0.24
         -> Shotcaller: 0.38
        Profile total wins: 1969
        >> Would you like to search again?

## Notes
Let it be known that I am in no way affiliated with Blizzard. This scraper is not meant to cause damages and is purely
for allowing developers an option when coding Overwatch related projects.
 
Developer Page: [Overwatch](https://us.shop.battle.net/en-us/product/overwatch)
 
    Written with passion in Boston, MA by Emilio Gonzalez - 2019.