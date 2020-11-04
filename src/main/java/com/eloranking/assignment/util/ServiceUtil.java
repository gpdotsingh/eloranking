package com.eloranking.assignment.util;

import com.eloranking.assignment.dataModel.MatchDetailData;
import com.eloranking.assignment.model.MatchResult;
import com.eloranking.assignment.model.Player;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class ServiceUtil {

    private Util util ;
    public ServiceUtil(Util util) {
        this.util = util;
    }
    /**
     *
     * @param player
     * @param alreadyAllocated
     * @param allPlayerId
     * @param playersForNextMatch
     */
    public void initializePlayerPool(Player player, Map<Integer, Boolean> alreadyAllocated, Set<Integer> allPlayerId, ArrayList<Integer> playersForNextMatch) {
        if (null != player)
            playersForNextMatch.addAll(allPlayerId); // List of All Players
        if (null != alreadyAllocated)
            playersForNextMatch.removeAll(alreadyAllocated.keySet()); // Removing set of players who are already allocated
        if (null != player)
        {
            playersForNextMatch.remove(new Integer(player.getId())); // Removing its own Id
            playersForNextMatch.removeAll(player.getMatchPlayedWith()); // Removing all players who already played with current player
        }
    }

    /**
     *
     * @param player
     * @param alreadyAllocated
     * @param playersForNextMatch
     */
    public void playerPoolForfirtIteration(Player player, Map<Integer, Boolean> alreadyAllocated, ArrayList<Integer> playersForNextMatch) {
        // If players have already allocated then give priority to allocated player - already played player
        playersForNextMatch.addAll(alreadyAllocated.keySet());
        if(null!=player) {
            playersForNextMatch.removeAll(player.getMatchPlayedWith());
            playersForNextMatch.remove(new Integer(player.getId())); // Removing its own Id
        }
    }

    /**
     *
     * @param player
     * @param playersForNextMatch
     */
    public void playerPoolForFinalIteration(Player player, ArrayList<Integer> playersForNextMatch) {
        playersForNextMatch.addAll(player.getMatchPlayedWith());
        playersForNextMatch.remove(new Integer(player.getId())); // Removing its own Id
    }

    /**
     *
     * @param winPlayer
     * @param lossPlayer
     * @param matchDetailData
     */
    public void setPlayerDetails(Player winPlayer, Player lossPlayer, MatchDetailData matchDetailData) {

        MatchResult matchResult = new MatchResult();
        matchResult.setLoseById(matchDetailData.getLostUserId());
        matchResult.setWinById(matchDetailData.getWinUserId());
        // Set player match details
        initiateMatchResult(winPlayer);
        initiateMatchResult(lossPlayer);
        // Add played with
        initiatePlaedWith(winPlayer);
        initiatePlaedWith(lossPlayer);
        winPlayer.getMatchResults().add(matchResult);
        lossPlayer.getMatchResults().add(matchResult);
        //  int opponentId = (a == 10) ? 20: 30;
        // Set player scores
        Float[] score=util.eloRating(winPlayer.getScores(),lossPlayer.getScores(),500);
        winPlayer.setScores(score[0]);
        winPlayer.getMatchPlayedWith().add(lossPlayer.getId());
        lossPlayer.setScores(score[1]);
        lossPlayer.getMatchPlayedWith().add(winPlayer.getId());
        // Set win loss count
        winPlayer.setTotalWin(winPlayer.getTotalWin()+1);
        lossPlayer.setTotalLosses(lossPlayer.getTotalLosses()+1);
    }

    private void initiatePlaedWith(Player winPlayer) {
        if (winPlayer.getMatchPlayedWith() == null)
            winPlayer.setMatchPlayedWith(new ArrayList<>());
    }

    private void initiateMatchResult(Player winPlayer) {
        if (winPlayer.getMatchResults() == null)
            winPlayer.setMatchResults(new ArrayList<MatchResult>());
    }
}
