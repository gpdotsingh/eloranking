package com.eloranking.assignment.model;

import lombok.Data;

@Data
public strictfp class PlayerScore {
    int id;
    String Name;
    Float score;

    @Override
    public String toString() {
        return
                "id=" + id +
                ", Name='" + Name + '\'' +
                ", score=" + score +"\n";
    }
}
