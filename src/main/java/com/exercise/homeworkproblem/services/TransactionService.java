package com.exercise.homeworkproblem.services;

import com.exercise.homeworkproblem.dto.NewTransaction;
import com.exercise.homeworkproblem.dto.TransactionsRewardsByMonth;
import com.exercise.homeworkproblem.dto.UpdateTransaction;
import com.exercise.homeworkproblem.models.Transaction;
import com.exercise.homeworkproblem.repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    private final Logger log = LoggerFactory.getLogger(TransactionService.class);

    @Autowired
    private TransactionRepository transactionRepository;

    public ResponseEntity addNewTransactions(Integer userId, List<NewTransaction> newTransactions) {
        try{
            if(newTransactions.isEmpty()){
                return ResponseEntity.ok("Empty list of new transactions was sent");
            }else {
                List<Transaction> transactionList = newTransactions.stream()
                        .map(newTransaction -> Transaction.builder()
                                .userId(userId)
                                .date(newTransaction.getDate())
                                .moneySpent(newTransaction.getPrice())
                                .rewardPoints(calculateRewards(newTransaction.getPrice()))
                                .build())
                        .collect(Collectors.toList());

                transactionRepository.saveAll(transactionList);
                log.info("New transactions were stored: " + transactionList.toString());

                return ResponseEntity.ok("All transactions stored");
            }
        }catch (NullPointerException nullPointerException){
            log.error("A null transaction or null price or null date was send in the request");
            return ResponseEntity.badRequest().body("Null transaction or price or date");
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

    public ResponseEntity updateTransactions(List<UpdateTransaction> updateTransactionList) {
        try{
            String updatedRows = "Rows updated: ";

            updateTransactionList.forEach(transaction -> {
                Optional<Transaction> optionalTransaction = transactionRepository.findById(transaction.getTransactionId());
                optionalTransaction.ifPresent(oldTransaction -> {
                    Transaction updatedTransaction = Transaction.builder()
                            .transactionId(oldTransaction.getTransactionId())
                            .date(oldTransaction.getDate())
                            .moneySpent(oldTransaction.getMoneySpent())
                            .userId(oldTransaction.getUserId())
                            .rewardPoints(calculateRewards(oldTransaction.getMoneySpent()))
                            .build();

                    transactionRepository.save(updatedTransaction);

                });
                //Todo: fix message
                updatedRows.concat(transaction.getTransactionId().toString() + ", ");
            });

            return  ResponseEntity.ok(updatedRows);

        }catch (NullPointerException nullPointerException){
        log.error("A null transaction or null price or null date was send in the request");
        return ResponseEntity.badRequest().body("Null transaction or price or date");
        }
    }
}
