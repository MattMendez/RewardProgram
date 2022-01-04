package com.exercise.homeworkproblem;

import com.exercise.homeworkproblem.dto.NewTransaction;
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

        ResponseEntity<String> response = ResponseEntity.ok("All transactions stored");

        Assert.assertEquals( response, transactionService.addNewTransactions(userId,newTransactions));
    }

    @Test
    public void addNewTransactions_Test_200_listWithEmptyTransactions(){
        Integer userId = 1;

        List<NewTransaction> newTransactions = List.of();

        ResponseEntity<String> response = ResponseEntity.ok("Empty list of new transactions was sent");

        Assert.assertEquals( response, transactionService.addNewTransactions(userId,newTransactions));
    }

    @Test
    public void addNewTransactions_Test_400_listWithNullTransaction(){
        Integer userId = 1;

        List<NewTransaction> newTransactions = new ArrayList<NewTransaction>();

        newTransactions.add(new NewTransaction(120, LocalDate.of(2022,1,4)));
        newTransactions.add(null);

        ResponseEntity<String> response = ResponseEntity.badRequest().body("Null transaction or price or date");

        Assert.assertEquals( response, transactionService.addNewTransactions(userId,newTransactions));
    }

    @Test
    public void findTransactions_Test_200(){
        Integer userId = 1;

        Integer dateRange = 1;

        List<Transaction> transactions = List.of(
                new Transaction(1,1,120,90, LocalDate.of(2022,1,4)),
                new Transaction(2,1,1,0, LocalDate.of(2022,1,3))
        );

        Mockito.when(transactionRepository.findAllByUserIdAndDateBetween(userId, LocalDate.of(2022,1,4).minusDays(dateRange),LocalDate.of(2022,1,4) )).thenReturn(transactions);

        ResponseEntity response = ResponseEntity.ok(transactions);

        Assert.assertEquals( response, transactionService.findTransactions(userId,dateRange));
    }




}
