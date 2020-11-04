package com.eloranking.assignment.model;

import lombok.Data;

@Data
public class MatchResult {
    int winById;
    int loseById;

    @Override
    public String toString() {
        return " Match result ::" + "winById=" + winById + ", loseById=" + loseById +"\n";
    }
}
