package com.exercise.homeworkproblem.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewTransaction {

    private Integer price;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
}
