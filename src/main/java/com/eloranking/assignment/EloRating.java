package com.eloranking.assignment;

import com.eloranking.assignment.common.MyException;
import com.eloranking.assignment.model.Player;
import com.eloranking.assignment.service.PlayerService;
import com.eloranking.assignment.service.PlayerServiceImpl;

import java.util.List;
import java.util.Scanner;

public class EloRating {

    public static void main(String a[]) throws Exception
    {
        PlayerService playerService = new PlayerServiceImpl();
        List<Player> playerList=playerService.getPlayerDetailList();
        // Loading data from file done.
        //n is the number of strings we want to enter
        System.out.println("Enter menu number for below option");
        System.out.println("\n1: SHOW SCORE " +
                "\n2: SORTED SCORE LIST " +
                "\n3: PLAYER WISE MATCH REPORT " +
                "\n4: NEXT MATCH CHART"+
                "\n5: TO SEE PLAYER WISE RESULT PRESS 5 THEN HIT ENTER THE PLAYER ID THEN HIT ENTER "+
                "\n6: EXIT");
        //creating object of the Scanner class
        Scanner sc = new Scanner(System.in);
        boolean scannerCondition=true;
        while(scannerCondition)
        {
            //takes string as input
            String str = sc.nextLine();
            selectOperation(playerService, playerList, str);
            if(str.equalsIgnoreCase("Quit"))
                scannerCondition=false;

        }
    }

    /**
     *
     * @param playerService
     * @param playerList
     * @param str
     */
    public static void selectOperation(PlayerService playerService, List<Player> playerList, String str) throws MyException {
        int option =Integer.parseInt(str);
        switch (option)
        {
            case 1:
                System.out.println("Player Score in with detail Id, name & Score");
                display(playerService.playersScore(playerList));
                break;
            case 2:
                System.out.println("Player sorted list with detail id, name, score, total win and total loss");
                display(playerService.playersSorted(playerList));
                break;
            case 3:
                System.out.println("Player wise match win or loss");
                display(playerService.playersMatchDetails(playerList));
                break;
            case 4:
                display(playerService.playersNextMatch(playerList));
                break;
            case 5: {
                System.out.println("Please enter the player id");
                Scanner sc = new Scanner(System.in);
                String input = sc.nextLine();
                System.out.println(playerService.getPlayerDetails(playerList,input));;
                break;
            }
            case 6:
                System.exit(1);
            default:
                System.out.println("Please choose correct input");
        }
    }

    private static void display(List<? extends Object> output) {
        output.stream().forEach(System.out:: println);
    }
}
