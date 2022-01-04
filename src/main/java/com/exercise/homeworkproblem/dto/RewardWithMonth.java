package com.exercise.homeworkproblem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RewardWithMonth {

    private Integer rewards;

    private Integer month;
}
