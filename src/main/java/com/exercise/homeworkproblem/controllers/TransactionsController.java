package com.exercise.homeworkproblem.controllers;

import com.exercise.homeworkproblem.dto.NewTransaction;
import com.exercise.homeworkproblem.dto.TransactionsRewardsByMonth;
import com.exercise.homeworkproblem.models.Transaction;
import com.exercise.homeworkproblem.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionsController {

    @Autowired
    private TransactionService transactionService;

    //ToDo: Pido user y rango de dias para dar los puntos por mes
    @GetMapping(value = "/month-points/")
    public ResponseEntity findTransactionsPoints(@RequestParam Integer userId, @RequestParam Integer dateRange){
        return transactionService.findTransactionsRewardsByMonth(userId,dateRange);
    }

    @PostMapping(value = "/new-transactions/")
    public ResponseEntity addNewTransactions(@RequestParam Integer userId, @RequestBody List<NewTransaction> newTransactions){
        return transactionService.addNewTransactions(userId,newTransactions);
    }

    //ToDo: un GET lista de transacciones para un user y rango de dias o una fecha

    @GetMapping(value = "/")
    public ResponseEntity findTransactions(@RequestParam Integer userId, @RequestParam Integer dateRange){
        return transactionService.findTransactions(userId,dateRange);
    }

    //ToDo: un put con una lista con ids de la transacciones , el monto nuevo y o la fecha nueva


}
