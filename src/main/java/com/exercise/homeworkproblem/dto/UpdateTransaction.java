package com.exercise.homeworkproblem.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateTransaction {

    private Integer transactionId;

    private Integer userId;

    private Integer moneySpent;

    private Integer rewardPoints;

    private LocalDate date;
}
