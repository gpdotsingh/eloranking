package com.eloranking.assignment.util;

import com.eloranking.assignment.common.MyException;
import com.eloranking.assignment.dataModel.MatchDetailData;
import com.eloranking.assignment.model.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Util {
    /**
     * @param is
     * @return
     */
    public BufferedReader streamToBR(InputStream is) {
        BufferedReader reader =null;
        InputStreamReader streamReader =
                new InputStreamReader(is, StandardCharsets.UTF_8);
        reader = new BufferedReader(streamReader);
        return reader;
    }

    /**
     * @param fileName
     * @return
     */
    public InputStream getFileFromResourceAsStream(String fileName) {
        // The class loader that loaded the class
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);
        // the stream holding the file content
        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return inputStream;
        }
    }

    /**
     * @param reader
     */
    public HashMap<Integer,String> getUserList(BufferedReader reader) throws MyException {
        //  TreeSet<PlayerData> playerDataSet = new TreeSet<PlayerData>((o1, o2) ->o1.getId()-o2.getId() );
        HashMap<Integer,String> playerPersonalDetail = new HashMap<Integer,String>();
        String line="";
        while (true) {
            try {
                if (!((line = reader.readLine()) != null)) break;
                String[] data = line.split("    ");
                playerPersonalDetail.put(Integer.parseInt(data[0]), data[1]);
            } catch (IOException e) {
                throw new MyException(" Please correct the input file format for User list", e);
            } catch (Exception e) {
                throw new MyException("Some exception ", e);
            }
        }
        return playerPersonalDetail;
    }

    /**
     * @param reader
     */
    public List<MatchDetailData> getMatchDetails(BufferedReader reader) throws MyException {
        List<MatchDetailData> matchDetailDataList = new ArrayList<>();
        String line="";
        while (true) {
            Float[] rating = new Float[2];
            MatchDetailData matchDetailData = new MatchDetailData();
            try {
                if (!((line = reader.readLine()) != null)) break;
                String[] data=line.split(" ");
                matchDetailData.setWinUserId(Integer.parseInt(data[0]));
                matchDetailData.setLostUserId(Integer.parseInt(data[1]));
                matchDetailDataList.add(matchDetailData);
            } catch (IOException e) {
                throw new MyException("Please correct the input file format for User list", e);            }
            catch (Exception e)
            {
                throw new MyException("Please correct the input file format for User list", e);            }
        }
        return matchDetailDataList;
    }

    /**
     * Function to calculate the Probability
     * @param rating1
     * @param rating2
     * @return
     */
    private float probability(float rating1, float rating2)
    {
        return  1.0f /
                (1 + 1.0f *(float)(Math.pow(10,  (rating1 - rating2) / 400)));
    }

    /**
     * Function to calculate Elo rating
     * @param Ra
     * @param Rb
     * @param K is a constant.
     * @return
     */
    public Float[] eloRating(float Ra, float Rb, int K)
    {
        Float[] rating= new Float[2];
        // To calculate the Winning
        // Probability of Player B
        float Pb = probability(Ra, Rb);
        // To calculate the Winning
        // Probability of Player A
        float Pa = probability(Rb, Ra);
        // Case  When Player A wins
        // Updating the Elo Ratings
        Ra = Ra + K * (1 - Pa);
        Rb = Rb + K * (0 - Pb);
        rating[0]=Ra;
        rating[1]=Rb;
        return rating;
    }

    public void assignNameNId(Map<Integer, String> playerData, HashMap<Integer, Player> playerMap, int playerId) {
        playerMap.put(playerId, new Player());
        playerMap.get(playerId).setName(playerData.get(playerId));
        playerMap.get(playerId).setId(playerId);
        playerMap.get(playerId).setScores(0.0f);
    }
}
