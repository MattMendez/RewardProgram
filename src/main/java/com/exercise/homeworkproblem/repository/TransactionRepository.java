package com.exercise.homeworkproblem.repository;

import com.exercise.homeworkproblem.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    List<Transaction> findAllByUserIdAndDateBetween(Integer userId, LocalDate date1, LocalDate date2);
}