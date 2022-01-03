package com.exercise.homeworkproblem.dto;

import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionsRewardsByMonth {

    private HashMap<Integer,Integer> rewardWithMonthMap;

}