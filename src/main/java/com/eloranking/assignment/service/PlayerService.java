package com.eloranking.assignment.service;


import com.eloranking.assignment.common.MyException;
import com.eloranking.assignment.model.*;

import java.util.List;
import java.util.Optional;

public interface PlayerService {
    public List<Player> getPlayerDetailList() throws MyException;
    public List<PlayerScore> playersScore(List<Player> playerList);
    public List<PlayerSortedData> playersSorted(List<Player> playerList);
    public List<MatchFareDetail> playersMatchDetails(List<Player> playerList);
    public List<PlayerNextMatch> playersNextMatch(List<Player> playerList);
    public Optional<Player> getPlayerDetails(List<Player> playerList, String input) throws MyException;
}
