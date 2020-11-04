package com.eloranking.assignment.service;

import com.eloranking.assignment.common.MyException;
import com.eloranking.assignment.dao.PlayerDao;
import com.eloranking.assignment.dao.PlayerDaoImpl;
import com.eloranking.assignment.dataModel.MatchDetailData;
import com.eloranking.assignment.model.*;
import com.eloranking.assignment.util.ServiceUtil;
import com.eloranking.assignment.util.Util;

import java.security.SecureRandom;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class PlayerServiceImpl implements PlayerService{

    private Util util ;
    private PlayerDao playerDao;
    private ServiceUtil serviceUtil;
    public void setPlayerDao(PlayerDao playerDao) {
        this.playerDao = playerDao;
    }
    public PlayerServiceImpl()
    {
        this.playerDao = new PlayerDaoImpl();
        this.util= new Util();
        this.serviceUtil= new ServiceUtil(util);
    }
    /**
     *
     * @return
     */
    @Override
    public List<Player> getPlayerDetailList() throws MyException {

        List<MatchDetailData>  matchDetailDataList= playerDao.getMatchDetailData();
        Map<Integer,String> playerData= playerDao.getPlayerData();

        HashMap<Integer,Player> playerMap= new HashMap<>();
        for (MatchDetailData matchDetailData:matchDetailDataList)
        {
            int winnerId=matchDetailData.getWinUserId();
            int looserId=matchDetailData.getLostUserId();

            if(playerMap.get(winnerId)==null)
                util.assignNameNId(playerData, playerMap, winnerId);
            if(playerMap.get(looserId)==null)
                util.assignNameNId(playerData, playerMap, looserId);
            serviceUtil.setPlayerDetails(playerMap.get(winnerId),playerMap.get(matchDetailData.getLostUserId()),matchDetailData);

        }
        return new ArrayList(playerMap.values());
    }

    // Create for one player
    @Override
    public List<PlayerScore> playersScore(List<Player> playerList) {
        return playerList.stream().map(player -> {
            PlayerScore playerScore= new PlayerScore();
            playerScore.setId(player.getId());;
            playerScore.setName(player.getName());
            playerScore.setScore(player.getScores());
            return playerScore;
        }).collect(Collectors.toList());
    }

    /**
     *
     * @param playerList
     * @return
     */
    @Override
    public List<PlayerSortedData> playersSorted(List<Player> playerList) {

        Comparator<Player> byScore =
                Comparator.comparing(Player::getScores);
        List<PlayerSortedData> playerSortedDataList = getPlayerSortedDataByScore(playerList, byScore);
//      Comparator for sorting list
        Comparator<PlayerSortedData> byRank =
                Comparator.comparingInt(PlayerSortedData::getRank);
        Comparator<PlayerSortedData> byNumberofWin =
                Comparator.comparingInt(PlayerSortedData::getTotalWin);
        Comparator<PlayerSortedData> byNumberOfLoss =
                Comparator.comparingInt(PlayerSortedData::getTotalLosses);

        Collections.sort(playerSortedDataList,byRank
                .thenComparing(byNumberofWin.reversed()).
                        thenComparing(byNumberOfLoss));
        return playerSortedDataList;
    }

    /***
     * @param playerList
     * @param byScore
     * @return
     */
    private List<PlayerSortedData> getPlayerSortedDataByScore(List<Player> playerList, Comparator<Player> byScore) {
        AtomicInteger listCounter = new AtomicInteger(0);

        return playerList.stream().sorted(byScore.reversed()).map(
                player -> new PlayerSortedData(player.getId(),player.getName(),
                        player.getScores(),listCounter.incrementAndGet(),player.getTotalWin(),
                        player.getTotalLosses()))
                .collect(Collectors.toList());
    }

    /**
     *
     * @param playerList
     * @return
     */
    @Override
    public List<MatchFareDetail> playersMatchDetails(List<Player> playerList) {
        return playerList.stream().map(player ->
        {
            MatchFareDetail matchFareDetail = new MatchFareDetail();
            matchFareDetail.setId(player.getId());
            matchFareDetail.setMatchResults(player.getMatchResults());
            matchFareDetail.setName(player.getName());
            return matchFareDetail;
        }).collect(Collectors.toList());
    }

    /**
     *
     * @param playerList
     * @return
     */
    @Override
    public List<PlayerNextMatch> playersNextMatch(List<Player> playerList) {
        Map<Integer, Boolean> alreadyAllocated= new HashMap<>();
        List<PlayerNextMatch> playerNextMatches = new ArrayList<>();
        Map<Integer,Player> playerMap = playerList.stream().
                collect(Collectors.toMap(Player::getId,player -> player));
        getNextMatchOpponent(alreadyAllocated, playerNextMatches, playerMap);
        return playerNextMatches;
    }

    @Override
    public Optional<Player> getPlayerDetails(List<Player> playerList, String input) throws MyException {
        Optional<Player> matchingObject;
        try{
            int playerId= Integer.parseInt(input);
            matchingObject = playerList.stream().
                    filter(p -> p.getId()==playerId).findFirst();
        }
        catch (Exception e)
        {
            throw new MyException("Please input integer value",e);
        }
        return matchingObject;
    }

    /**
     *
     * @param alreadyAllocated
     * @param playerNextMatches
     * @param playerMap
     */
    private void getNextMatchOpponent(Map<Integer, Boolean> alreadyAllocated, List<PlayerNextMatch> playerNextMatches, Map<Integer, Player> playerMap) {
        int getSecondPlayerId;
        for (Player player: playerMap.values()) {
            PlayerNextMatch playerNextMatch = new PlayerNextMatch();
            if(null==alreadyAllocated.get(player.getId())) {
                playerNextMatch.setPlayer1Id(player.getId());
                playerNextMatch.setPlayer1Name(player.getName());
                getSecondPlayerId = setPlayer2Details(player, alreadyAllocated,playerMap.keySet());
                playerNextMatch.setPlayer2Id(playerMap.get(getSecondPlayerId).getId());
                playerNextMatch.setPlayer2Name(playerMap.get(getSecondPlayerId).getName());
                alreadyAllocated.put(playerNextMatch.getPlayer1Id(), true);
                alreadyAllocated.put(playerNextMatch.getPlayer2Id(), true);
                playerNextMatches.add(playerNextMatch);
            }
        }
    }

    /**
     *
     * @param player
     * @param alreadyAllocated
     * @param allPlayerId
     * @return
     */
    private int setPlayer2Details(Player player, Map<Integer, Boolean> alreadyAllocated, Set<Integer> allPlayerId)
    {
        Integer matchFound;
        ArrayList<Integer> playersForNextMatch = new ArrayList<>();
        serviceUtil.initializePlayerPool(player, alreadyAllocated, allPlayerId, playersForNextMatch);
        SecureRandom random = new SecureRandom();

        if(playersForNextMatch.size()>0) {
            matchFound= random.nextInt(playersForNextMatch.size());
        }
        else
        {
            serviceUtil.playerPoolForfirtIteration(player, alreadyAllocated, playersForNextMatch);
            // If allocated player - already played player are same the choose any of them
            if(playersForNextMatch.size()>0) {
                matchFound = random.nextInt(playersForNextMatch.size());
            }
            else {
                serviceUtil.playerPoolForFinalIteration(player, playersForNextMatch);
                matchFound = random.nextInt(playersForNextMatch.size());
            }
        }
        return playersForNextMatch.get(matchFound);
    }

}
