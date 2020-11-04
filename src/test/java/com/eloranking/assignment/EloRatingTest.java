package com.eloranking.assignment;

import com.eloranking.assignment.common.MyException;
import com.eloranking.assignment.dao.PlayerDao;
import com.eloranking.assignment.dao.PlayerDaoImpl;
import com.eloranking.assignment.dataModel.MatchDetailData;
import com.eloranking.assignment.model.*;
import com.eloranking.assignment.service.PlayerService;
import com.eloranking.assignment.service.PlayerServiceImpl;
import com.eloranking.assignment.util.Util;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.BufferedReader;
import java.io.Reader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

class EloRatingTest {

    List<MatchDetailData> matchDetailDataList  ;
    Map<Integer,String> playerMap;
    List<Player> playerListExpected;
    @BeforeEach
    void setUp() {
        matchDetailDataList = new ArrayList<>();
        playerMap = new HashMap<>();
        // Setting user detail
        playerMap.put(0,"Wesley");
        playerMap.put(1,"Melodie");
        playerMap.put(2,"Solange");
        playerMap.put(3,"Johanne");
        // Setting Match Result
        matchDetailDataList.add(new MatchDetailData(1,0));
        matchDetailDataList.add(new MatchDetailData(1,2));
        matchDetailDataList.add(new MatchDetailData(0,2));
        matchDetailDataList.add(new MatchDetailData(3,0));
        matchDetailDataList.add(new MatchDetailData(3,1));

    }
    // Service test case
    @Test
    public void getPlayerDetailListHappyFlow() throws MyException {
        PlayerService playerService = getPlayerService();
        List<Player> playerListOriginal = playerService.getPlayerDetailList();
        assertEquals(1,playerListOriginal.get(1).getId());
        assertEquals("Solange",playerListOriginal.get(2).getName());
        assertEquals(0,playerListOriginal.get(3).getRank());
        assertNotNull(playerListOriginal.toString());
    }

    // Service test case
    @Test
    public void getPlayersSortedHappyFlow() throws MyException {
        PlayerService playerService = getPlayerService();
        List<Player> playerListOriginal = playerService.getPlayerDetailList();
        List<PlayerSortedData> playerSorted=playerService.playersSorted(playerListOriginal);
        assertEquals("Johanne",playerSorted.get(0).getName());
        assertEquals(2,playerSorted.get(0).getTotalWin());
        BigDecimal actualScore = new BigDecimal(playerListOriginal.get(0).getScores()).setScale(2,RoundingMode.CEILING);
        BigDecimal expected = new BigDecimal(-218.61).setScale(2,RoundingMode.CEILING);
        assertEquals(expected,actualScore);
        assertEquals(0,playerSorted.get(0).getTotalLosses());
        assertNotNull(playerSorted.toString());
    }

    // Service test case
    @Test
    public void getPlayerScoresHappyFlow() throws MyException {
        PlayerService playerService = getPlayerService();
        List<Player> playerListOriginal = playerService.getPlayerDetailList();
        List<PlayerScore> playerScores=playerService.playersScore(playerListOriginal);
        BigDecimal actualScore = new BigDecimal(playerScores.get(0).getScore()).setScale(2,RoundingMode.CEILING);
        BigDecimal expected = new BigDecimal(-218.61).setScale(2,RoundingMode.CEILING);
        assertEquals(expected,actualScore);
        assertEquals(0,playerScores.get(0).getId());
        assertEquals("Wesley",playerScores.get(0).getName());
        assertNotNull(playerScores.toString());
    }

    @Test
    public void getMatchFareDetailHappyFlow() throws MyException {
        PlayerService playerService = getPlayerService();
        List<Player> playerListOriginal = playerService.getPlayerDetailList();
        List<MatchFareDetail> matcheFareDetails=playerService.playersMatchDetails(playerListOriginal);
        assertEquals("Wesley",matcheFareDetails.get(0).getName());
        assertEquals(0,matcheFareDetails.get(0).getId());
        assertEquals(3,matcheFareDetails.get(0).getMatchResults().size());
        assertEquals(0,matcheFareDetails.get(0).getMatchResults().get(0).getLoseById());
        assertEquals(1,matcheFareDetails.get(0).getMatchResults().get(0).getWinById());
        assertNotNull(matcheFareDetails.toString());
    }

    @Test
    public void getPlayerDetailsHappyFlow() throws MyException
    {
        PlayerService playerService = getPlayerService();
        List<Player> playerListOriginal = playerService.getPlayerDetailList();
        Optional<Player> player=playerService.getPlayerDetails(playerListOriginal,"1");
        assertNotNull(player);
    }

    @Test
    public void PlayerDetailsNonHappyFlow() throws MyException
    {
        PlayerService playerService = getPlayerService();
        List<Player> playerListOriginal = playerService.getPlayerDetailList();
        try {
            Optional<Player> player = playerService.getPlayerDetails(playerListOriginal, "StringValue");
        }
        catch (MyException e)
        {
            assertEquals("Please input integer value",e.getMessage());
        }
    }

    @Test
    public void getPlayerNextMatch() throws MyException
    {
        PlayerService playerService = getPlayerService();
        List<Player> playerListOriginal = playerService.getPlayerDetailList();
        List<PlayerNextMatch> playerNextMatches=playerService.playersNextMatch(playerListOriginal);
        assertNotEquals(playerNextMatches.get(0).getPlayer1Id(),playerNextMatches.get(0).getPlayer2Id());
        assertNotNull(playerNextMatches.get(0).getPlayer1Name());
        assertNotEquals(playerNextMatches.get(0).getPlayer2Id(),playerNextMatches.get(0).getPlayer1Id());
        assertNotNull(playerNextMatches.get(0).getPlayer2Name());
    }
    // Dao test case
    @Test
    public void getMatchDetailDataHappyFlow() throws MyException {
        PlayerDaoImpl userDao = Mockito.spy(new PlayerDaoImpl());
        when(userDao.getFileName()).thenReturn("matchResultTest.csv");
        List<MatchDetailData> matchDetailData= userDao.getMatchDetailData();
        assertEquals(2,matchDetailData.size());
    }

    @Test
    public void getMatchDetailDataUnHappyFlow() throws MyException {
        PlayerDaoImpl userDao = Mockito.spy(new PlayerDaoImpl());
        when(userDao.getFileName()).thenReturn("matchResultTestNotExist.csv");
        try {
            List<MatchDetailData> matchDetailData = userDao.getMatchDetailData();
        }
        catch (IllegalArgumentException e)
        {
            assertEquals(e.getMessage(),"file not found! matchResultTestNotExist.csv" );
        }
    }

    @Test
    public void getUserListUnHappyFlow() throws MyException {
        String test = "test test";
        Reader inputString = new StringReader(test);
        BufferedReader reader = new BufferedReader(inputString);
        Util util = new Util();
        try {
            util.getUserList(reader);
        }
        catch (MyException e)
        {
            assertEquals(e.getMessage(),"Some exception " );
        }
    }

    @Test
    public void getPlayerDataUnHappyFlow() throws MyException {
        PlayerDaoImpl userDao = Mockito.spy(new PlayerDaoImpl());
        when(userDao.getFileName()).thenReturn("userListTest.csv");
        HashMap<Integer,String> userDetail= userDao.getPlayerData();
        assertEquals(4,userDetail.size());
    }

    public PlayerService getPlayerService() throws MyException {
        PlayerDao playerDao = Mockito.mock(PlayerDaoImpl.class);
        when(playerDao.getMatchDetailData()).thenReturn(matchDetailDataList);
        when(playerDao.getPlayerData()).thenReturn(playerMap);
        PlayerServiceImpl playerService = new PlayerServiceImpl();
        playerService.setPlayerDao(playerDao);
        return playerService;
    }

    // Test Exception
    @Test
    public void getTestExceptionFlow() throws MyException {
        MyException exception= new MyException("Test exception",new Exception());
        assertEquals("Test exception",exception.getMessage());
    }

}