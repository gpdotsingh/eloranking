package com.eloranking.assignment.dataModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchDetailData {
    int winUserId;
    int lostUserId;
}
