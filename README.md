
# Project Description  
This project is generate ranking, score and to predict next set of match of the player's.
It have two input file that contains player `Player Id` vs `Player Name`and `Winner Id` Vs `Lost Player Id`.
Using mentioned input files project is generating score, ranking and set of next matches and provide list in
sorted order while implementing Elo algorithm.
    
    Elo algorithm
    Elo Rating Algorithm is widely used rating algorithm that is used to rank players in many competitive games.
    Players with higher ELO rating have a higher probability of winning a game than a player with lower ELO rating. After each game, ELO rating of players is updated.
    If a player with higher ELO rating wins, only a few points are transferred from the lower rated player.
    However if lower rated player wins, then transferred points from a higher rated player are far greater.		
    
    Ref https://www.geeksforgeeks.org/elo-rating-algorithm/

## Project Description 

* Score each player based on the games played 
* Generate a list of players sorted by score, their ranking (position in the list) and their number of wins and losses.
* Generate a report for each person, showing with whom they played and how they fared. 
* Generate a list of suggested next matches.
    - next matche opponent will be planned on basis of below algorithm.
        * Priority 1: Both player should never played before and in current set of match both are available to play(Not mapped with anyone to play)
        * Priority 2: Both player should never played before and in current set , 
        map with random player from current pool(Even though the opponent is already mapped for next match so opponent will play multiple matches)   
        * Priority 3: Any player from the pool irrespective of both opponent played before or opponent is mapped for another match

### Steps to run project
`Generate the build and download dependecy`
*   mvn clean install

`Command to run the project`
*   mvn exec:java

On application run you will get below options.
    
    Enter menu number for below option
    1: PlAYER MATCH DETAIL 
    2: SHOW SCORE 
    3: SORTED SCORE LIST 
    4: PLAYER WISE MATCH REPORT 
    5: NEXT MATCH CHART
    6: EXIT
    
    *   Please provide input type as numeric. For fourth option and then player id