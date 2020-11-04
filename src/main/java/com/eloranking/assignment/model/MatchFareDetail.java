package com.eloranking.assignment.model;

import lombok.Data;

import java.util.List;

@Data
public class MatchFareDetail {
    int id;
    String name;
    List<MatchResult> matchResults;
    @Override
    public String toString() {
        return "Match fare detail for " +
                "id=" + id +
                ":: name='" + name + '\'' +
                ":: matchResults=" + matchResults +
                "\n";
    }
}
