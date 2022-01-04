package com.exercise.homeworkproblem;

import com.exercise.homeworkproblem.dto.NewTransaction;
import com.exercise.homeworkproblem.dto.UpdateTransaction;
import com.exercise.homeworkproblem.exceptions.NewTransactionsEmptyException;
import com.exercise.homeworkproblem.exceptions.TransactionsNotFoundException;
import com.exercise.homeworkproblem.exceptions.TransactionsNullFoundException;
import com.exercise.homeworkproblem.models.Transaction;
import com.exercise.homeworkproblem.repository.TransactionRepository;
import com.exercise.homeworkproblem.services.TransactionService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestExecutionListeners(DependencyInjectionTestExecutionListener.class)
public class TestTransactionService {

    @Autowired
    @MockBean
    TransactionRepository transactionRepository;

    @Autowired
    TransactionService transactionService;

    @Test
    public void addNewTransactions_Test_200_listWithNewTransactions(){
        Integer userId = 1;

        List<NewTransaction> newTransactions = List.of(
                new NewTransaction(120, LocalDate.of(2022,1,4)),
                new NewTransaction(185, LocalDate.of(2022,1,5)),
                new NewTransaction(1, LocalDate.of(2022,1,3)),
                new NewTransaction(60, LocalDate.of(2022,1,2))
        );

        List<Transaction> transactionBeforeSave = List.of(
                new Transaction(null,1,120,90, LocalDate.of(2022,1,4)),
                new Transaction(null,1,185,220, LocalDate.of(2022,1,5)),
                new Transaction(null,1,1,0, LocalDate.of(2022,1,3)),
                new Transaction(null,1,60,10, LocalDate.of(2022,1,2))
        );

        List<Transaction> transactionAfterSave = List.of(
                new Transaction(1,1,120,90, LocalDate.of(2022,1,4)),
                new Transaction(2,1,185,220, LocalDate.of(2022,1,5)),
                new Transaction(3,1,1,0, LocalDate.of(2022,1,3)),
                new Transaction(4,1,60,10, LocalDate.of(2022,1,2))
        );

        Mockito.when(transactionRepository.saveAll(transactionBeforeSave)).thenReturn(transactionAfterSave);

        ResponseEntity<List<Transaction>> response = ResponseEntity.ok(transactionAfterSave);

        Assert.assertEquals( response, transactionService.addNewTransactions(userId,newTransactions));
    }

    @Test(expected = NewTransactionsEmptyException.class)
    public void addNewTransactions_Test_200_listWithEmptyTransactions(){
        Integer userId = 1;

        List<NewTransaction> newTransactions = List.of();

        transactionService.addNewTransactions(userId,newTransactions);
    }

    @Test(expected = TransactionsNullFoundException.class)
    public void addNewTransactions_Test_400_listWithNullTransaction(){
        Integer userId = 1;

        List<NewTransaction> newTransactions = new ArrayList<NewTransaction>();
        newTransactions.add(new NewTransaction(120, LocalDate.of(2022,1,4)));
        newTransactions.add(null);

        transactionService.addNewTransactions(userId,newTransactions);
    }

    @Test
    public void findTransactions_Test_200(){
        Integer userId = 1;

        Integer dayRange = 1;

        List<Transaction> transactions = List.of(
                new Transaction(1,1,120,90, LocalDate.of(2022,1,4)),
                new Transaction(2,1,1,0, LocalDate.of(2022,1,3))
        );

        Mockito.when(transactionRepository.findAllByUserIdAndDateBetween(userId, LocalDate.of(2022,1,4).minusDays(dayRange),LocalDate.of(2022,1,4) )).thenReturn(transactions);

        ResponseEntity response = ResponseEntity.ok(transactions);

        Assert.assertEquals( response, transactionService.findTransactions(userId,dayRange));
    }

    @Test
    public void findTransactionsRewardsByMonth_Test_200(){
        Integer userId = 1;

        Integer dayRange = 90;

        List<Transaction> transactions = List.of(
                new Transaction(1,1,120,90, LocalDate.of(2022,1,4)),
                new Transaction(2,1,1,0, LocalDate.of(2022,1,3)),
                new Transaction(3,1,150,150, LocalDate.of(2022,12,3)),
                new Transaction(4,1,185,220, LocalDate.of(2022,12,4))
        );

        Mockito.when(transactionRepository.findAllByUserIdAndDateBetween(userId, LocalDate.of(2022,1,4).minusDays(dayRange),LocalDate.of(2022,1,4) )).thenReturn(transactions);

        HashMap<Integer,Integer> transactionsRewardsByMonth = new HashMap<Integer,Integer>();

        transactionsRewardsByMonth.put(1,90);
        transactionsRewardsByMonth.put(12,370);

        ResponseEntity<HashMap> response = ResponseEntity.ok(transactionsRewardsByMonth);

        Assert.assertEquals( response, transactionService.findTransactionsRewardsByMonth(userId,dayRange));
    }

    @Test(expected = TransactionsNotFoundException.class)
    public void findTransactionsRewardsByMonth_Test_404_transactionsNotFound(){
        Integer userId = 1;

        Integer dayRange = 90;

        List<Transaction> transactions = List.of();

        Mockito.when(transactionRepository.findAllByUserIdAndDateBetween(userId, LocalDate.of(2022,1,4).minusDays(dayRange),LocalDate.of(2022,1,4) )).thenReturn(transactions);

        transactionService.findTransactionsRewardsByMonth(userId,dayRange);
    }

    @Test
    public void updateTransactions_Test_200(){

        List<UpdateTransaction> transactions = List.of(
                new UpdateTransaction(1,1,150, LocalDate.of(2022,1,4))
        );

        Transaction transaction =  new Transaction(1,1,120,90, LocalDate.of(2022,1,4));

        Mockito.when(transactionRepository.findById(transactions.get(0).getTransactionId())).thenReturn(java.util.Optional.of(transaction));

        transaction.setRewardPoints(transactionService.calculateRewards(transaction.getMoneySpent()));
        transaction.setMoneySpent(transaction.getMoneySpent());

        Mockito.when(transactionRepository.save(transaction)).thenReturn(transaction);

        ResponseEntity<List<Transaction>> response = ResponseEntity.ok(List.of(new Transaction(1,1,150,150, LocalDate.of(2022,1,4))));

        Assert.assertEquals( response, transactionService.updateTransactions(transactions));
    }

    @Test(expected = TransactionsNullFoundException.class)
    public void updateTransactions_Test_400_listWithNullTransaction(){

        List<UpdateTransaction> transactions = new ArrayList<>();
        transactions.add(null);

        transactionService.updateTransactions(transactions);
    }

}
