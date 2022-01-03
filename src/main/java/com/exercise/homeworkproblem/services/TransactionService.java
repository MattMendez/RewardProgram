package com.exercise.homeworkproblem.services;

import com.exercise.homeworkproblem.dto.NewTransaction;
import com.exercise.homeworkproblem.dto.RewardWithMonth;
import com.exercise.homeworkproblem.dto.TransactionsRewardsByMonth;
import com.exercise.homeworkproblem.models.Transaction;
import com.exercise.homeworkproblem.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public ResponseEntity addNewTransactions(Integer userId, List<NewTransaction> newTransactions) {

        //Todo: ver que no manden una lista vacia
        try{
            List<Transaction> transactionList = newTransactions.stream()
                    .map(newTransaction -> Transaction.builder()
                            .userId(userId)
                            .date(newTransaction.getDate())
                            .moneySpent(newTransaction.getPrice())
                            .rewardPoints(calculateRewards(newTransaction.getPrice()))
                            .build())
                    .collect(Collectors.toList());

            transactionRepository.saveAll(transactionList);

            return ResponseEntity.ok("All transactions stored");

        }catch (NullPointerException nullPointerException){
            //Todo: Logger
            return ResponseEntity.badRequest().body("Null in price or date");
        }
    }

    public Integer calculateRewards(Integer price){
        if (price >=50 && price < 100) {
            return price-50;
        } else if (price >100){
            return (2*(price-100) + 50);
        }
        return 0;
    }

    public ResponseEntity findTransactions(Integer userId, Integer dateRange) {

        return ResponseEntity.ok(transactionRepository.findAllByUserIdAndDateBetween(userId, LocalDate.now().minusDays(dateRange), LocalDate.now()));
    }

    public ResponseEntity findTransactionsRewardsByMonth(Integer userId, Integer dateRange) {
        List<Transaction> transactionList = transactionRepository.findAllByUserIdAndDateBetween(userId, LocalDate.now().minusDays(dateRange), LocalDate.now());
        if (!transactionList.isEmpty()){
            TransactionsRewardsByMonth transactionsRewardsByMonth = new TransactionsRewardsByMonth(new HashMap<Integer,Integer>());

            transactionList.forEach(transaction -> {
                if(transactionsRewardsByMonth.getRewardWithMonthMap().containsKey(transaction.getDate().getMonthValue())){

                    transactionsRewardsByMonth.getRewardWithMonthMap()
                            .put(
                                    transaction.getDate().getMonthValue()
                                    ,transactionsRewardsByMonth.getRewardWithMonthMap().get(transaction.getDate().getMonthValue()) + transaction.getRewardPoints()
                            );
                }else{
                    transactionsRewardsByMonth.getRewardWithMonthMap().put(transaction.getDate().getMonthValue(), transaction.getRewardPoints());
                }
            });
            return ResponseEntity.ok(transactionsRewardsByMonth.getRewardWithMonthMap());

        }else
            return ResponseEntity.ok("There are not transactions for that userId in the date range provided");
    }
}
