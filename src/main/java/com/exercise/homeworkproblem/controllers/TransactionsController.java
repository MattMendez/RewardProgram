package com.exercise.homeworkproblem.controllers;

import com.exercise.homeworkproblem.dto.NewTransaction;
import com.exercise.homeworkproblem.dto.UpdateTransaction;
import com.exercise.homeworkproblem.exceptions.NewTransactionsEmptyException;
import com.exercise.homeworkproblem.exceptions.TransactionsNotFoundException;
import com.exercise.homeworkproblem.exceptions.TransactionsNullFoundException;
import com.exercise.homeworkproblem.services.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/transactions")
public class TransactionsController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping(value = "/rewards-by-month/")
    @Operation(summary="Find reward points group by month", description ="Service to find  reward points in certain date range group by month")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Find successfully",
                                    value = "{\n" +
                                            "  \"1\": 1508,\n" +
                                            "  \"11\": 1198,\n" +
                                            "  \"12\": 1198\n" +
                                            "}")
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "No transactions found",
                                    value =  "{\n" +
                                            "  \"message\": \"No such transactions\"\n" +
                                            "}")
                    )
            ),
    })
    public ResponseEntity findTransactionsRewardPoints(@RequestParam Integer userId, @RequestParam Integer dateRange){
        return transactionService.findTransactionsRewardsByMonth(userId,dateRange);
    }

    @PostMapping(value = "/new-transactions/")
    @Operation(summary="Add a list of new transactions", description ="Service to add new transactions")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Request format",
            required = true,
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = @ExampleObject("[\n" +
                            "  {\n" +
                            "    \"price\": 120,\n" +
                            "    \"date\": \"2022-01-04\"\n" +
                            "  }\n" +
                            "]")))

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Insert successfully",
                                    value = "[\n" +
                                            "  {\n" +
                                            "    \"transactionId\": 7,\n" +
                                            "    \"userId\": 1,\n" +
                                            "    \"moneySpent\": 120,\n" +
                                            "    \"rewardPoints\": 90,\n" +
                                            "    \"date\": \"2022-01-04\"\n" +
                                            "  }\n" +
                                            "]")
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "No transactions found",
                                    value =  "{\n" +
                                            "  \"message\": \"A null transaction or null price or null date was send in the request\"\n" +
                                            "}")
                    )
            ),
    })
    public ResponseEntity addNewTransactions(@RequestParam Integer userId, @RequestBody List<NewTransaction> newTransactions){
        return transactionService.addNewTransactions(userId,newTransactions);
    }

    @GetMapping(value = "/")
    @Operation(summary="Find transactions by userid and date range", description ="Service to find transactions in certain date range group by month")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Find successfully",
                                    value = "[\n" +
                                            "  {\n" +
                                            "    \"transactionId\": 1,\n" +
                                            "    \"userId\": 1,\n" +
                                            "    \"moneySpent\": 120,\n" +
                                            "    \"rewardPoints\": 90,\n" +
                                            "    \"date\": \"2022-01-03\"\n" +
                                            "  },\n" +
                                            "  {\n" +
                                            "    \"transactionId\": 2,\n" +
                                            "    \"userId\": 1,\n" +
                                            "    \"moneySpent\": 185,\n" +
                                            "    \"rewardPoints\": 220,\n" +
                                            "    \"date\": \"2022-01-02\"\n" +
                                            "  }\n" +
                                            "]")
                    )
            ),
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "No transactions found",
                                    value =  "[]")
                    )
            ),
    })
    public ResponseEntity findTransactions(@RequestParam Integer userId, @RequestParam Integer dateRange){
        return transactionService.findTransactions(userId,dateRange);
    }

    @PutMapping(value = "/update-transactions")
    @Operation(summary="Add a list of transactions to update", description ="Service to update transactions already created")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Formato del request",
            required = true,
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = @ExampleObject("[\n" +
                                            "    \"transactionId\": 1,\n" +
                                            "    \"userId\": 1,\n" +
                                            "    \"moneySpent\": 150,\n" +
                                            "    \"date\": \"2022-01-03\"\n" +
                                            "  }\n" +
                                            "]")))
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Insert successfully",
                                    value = "[\n" +
                                            "  {\n" +
                                            "    \"transactionId\": 1,\n" +
                                            "    \"userId\": 1,\n" +
                                            "    \"moneySpent\": 150,\n" +
                                            "    \"rewardPoints\": 150,\n" +
                                            "    \"date\": \"2022-01-03\"\n" +
                                            "  }\n" +
                                            "]")
                    )
            ),
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Empty List",
                                    value = "[]")
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Null value found",
                                    value =  "{\n" +
                                            "  \"message\": \"A null transaction or null price or null date was send in the request\"\n" +
                                            "}")
                    )
            ),
    })
    public ResponseEntity updateTransactions(@RequestBody List<UpdateTransaction> updateTransactionList){
        return transactionService.updateTransactions(updateTransactionList);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map> RuntimeExceptionHandler(RuntimeException e) {
        return new ResponseEntity<Map>(Map.of("message","Runtime exception"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NewTransactionsEmptyException.class)
    public ResponseEntity<Map> NewTransactionsEmptyExceptionHandler(NewTransactionsEmptyException e) {
        return new ResponseEntity<Map>(Map.of("message","Empty list of new transactions was sent"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TransactionsNotFoundException.class)
    public ResponseEntity<Map> TransactionsNotFoundExceptionHandler(TransactionsNotFoundException e) {
        return new ResponseEntity<Map>(Map.of("message","No such transactions"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TransactionsNullFoundException.class)
    public ResponseEntity<Map> TransactionsNullFoundExceptionHandler(TransactionsNullFoundException e) {
        return new ResponseEntity<Map>(Map.of("message","A null transaction or null price or null date was send in the request"), HttpStatus.BAD_REQUEST);
    }

}
