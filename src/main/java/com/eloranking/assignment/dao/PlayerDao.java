package com.eloranking.assignment.dao;

import com.eloranking.assignment.common.MyException;
import com.eloranking.assignment.dataModel.MatchDetailData;

import java.util.List;
import java.util.Map;

// Create a list or Map for Match detail if required
public interface PlayerDao {
    Map<Integer,String> getPlayerData() throws MyException;
    List<MatchDetailData> getMatchDetailData() throws MyException;
}
