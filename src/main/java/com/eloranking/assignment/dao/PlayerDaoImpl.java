package com.eloranking.assignment.dao;

import com.eloranking.assignment.common.MyException;
import com.eloranking.assignment.dataModel.MatchDetailData;
import com.eloranking.assignment.util.Util;
import java.io.BufferedReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlayerDaoImpl implements PlayerDao {

    private Util util ;

    public void setUtil(Util util) { this.util = util; }

    public String getFileName() {
        return fileName;
    }
    private String fileName;

    public void setFileName(String fileName) { this.fileName = fileName; }

    public PlayerDaoImpl()
    { this.util = new Util();
    }
    /**
     *
     * @return PlayerData list
     */
    @Override
    public HashMap<Integer,String> getPlayerData() throws MyException {
        setFileName("userList.csv");
        BufferedReader reader =util.streamToBR(util.getFileFromResourceAsStream(getFileName()));
        return util.getUserList(reader);
    }

    /**
     *
     * @return MatchDetailData list
     */
    @Override
    public List<MatchDetailData> getMatchDetailData() throws MyException {
        setFileName("matchResult.csv");
        InputStream is= util.getFileFromResourceAsStream(getFileName());
        BufferedReader reader =util.streamToBR(is);
        return util.getMatchDetails(reader);
    }
}
