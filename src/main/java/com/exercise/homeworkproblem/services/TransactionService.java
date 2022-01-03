package com.exercise.homeworkproblem.services;

import com.exercise.homeworkproblem.dto.NewTransaction;
import com.exercise.homeworkproblem.models.Transaction;
import com.exercise.homeworkproblem.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public ResponseEntity<String> addNewTransactions(Integer userId, List<NewTransaction> newTransactions) {

        List<Transaction> transactionList = newTransactions.stream()
                .map(newTransaction -> Transaction.builder()
                        .userId(userId)
                        .dateTime(newTransaction.getDateTime())
                        .moneySpent(newTransaction.getPrice())
                        .rewardPoints(calculateRewards(newTransaction.getPrice()))
                        .build())
                .collect(Collectors.toList());
        //ToDo: try catch null

        transactionRepository.saveAll(transactionList);

        return ResponseEntity.ok("All transactions stored");
    }

    public Integer calculateRewards(Integer price){
        if (price >=50 && price < 100) {
            return price-50;
        } else if (price >100){
            return (2*(price-100) + 50);
        }
        return 0;
    }

}
