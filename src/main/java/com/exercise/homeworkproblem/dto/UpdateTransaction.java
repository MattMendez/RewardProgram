package com.exercise.homeworkproblem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTransaction {

    private Integer transactionId;

    private Integer userId;

    private Integer moneySpent;

    private LocalDate date;
}
