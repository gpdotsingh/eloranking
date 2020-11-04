package com.eloranking.assignment.model;


import com.eloranking.assignment.model.MatchResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Player {
    int id;
    String name;
    Float scores;
    int rank;
    int totalWin;
    int totalLosses;
    List<Integer> matchPlayedWith;
    List<MatchResult> matchResults;

    @Override
    public String toString() {
        return "id=" + id +
                ", name='" + name +
                ", scores=" + scores +
                ", rank=" + rank +
                ", totalWin=" + totalWin +
                ", totalLosses=" + totalLosses +
                ", matchPlayedWith=" + matchPlayedWith +
                ", matchResults=" + matchResults ;
    }
// List of match details
}

