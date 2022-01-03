package com.exercise.homeworkproblem.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class NewTransaction {

    private Integer price;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
}
