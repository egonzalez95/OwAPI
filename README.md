# Overwatch API
Blizzard has not released an actual API for Overwatch statistics, but rather, has allowed players to publicly view
profiles online and view their stats there. I plan to remedy this by creating a Java-based API that crawls the web-page and allows users
to pull specific data from PUBLIC/PRIVATE profiles. Keep in mind, that if Blizzard changes their profile page I will most
likely have to update this code. I will be using a Java based web-crawler using the JSoup library that I had previously
developed.

There are already API's created by other users so this is mostly a learning outcome.

## Running the API
To come. In developmental stages first, then functional API will be set.

## Documentation
Documentation for the API can be found [HERE](DOCUMENTATION.md)

## Intended Outcomes
    - An easy-to-use API
    - Username text-file parser to process multiple usernames
    - Exporter to save the results
    
## Where is this project so far?
So far, the following output is displayed when entering a users profile. 
This will be fixed later on so that a username and platform suffice.


    :: input: https://playoverwatch.com/en-us/career/pc/Seagull-1894
    
    :: output:
    Success reaching and connecting to [https://playoverwatch.com/en-us/career/pc/Seagull-1894]
    Username: Seagull
    Private Account? false
    Rank: 661
    Competitive Rank: Grandmaster
    Competitive Season Rank: 4155
    Endorsement Level: 3
     - Teammate : 0.38
     - Sportsmanship : 0.24
     - Shotcaller : 0.38

## Notes
Let it be known that I am in no way affiliated with Blizzard. This scraper is not meant to cause damages and is purely
for allowing developers an option when coding Overwatch related projects.
 