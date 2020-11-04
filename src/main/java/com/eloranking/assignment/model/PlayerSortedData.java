package com.eloranking.assignment.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlayerSortedData {
    int id;
    String name;
    Float scores;
    int rank;
    int totalWin;
    int totalLosses;

    @Override
    public String toString() {
        return  "id=" + id +
                ", name='" + name + '\'' +
                ", scores=" + scores +
                ", rank=" + rank +
                ", totalWin=" + totalWin +
                ", totalLosses=" + totalLosses +
                "}\n";
    }
}
