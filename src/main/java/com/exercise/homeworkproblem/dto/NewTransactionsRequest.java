package com.exercise.homeworkproblem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewTransactionsRequest {

    @JsonProperty("transactions")
    List<NewTransaction> newTransactionList;
}
