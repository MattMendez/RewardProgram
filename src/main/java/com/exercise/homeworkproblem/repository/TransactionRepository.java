package com.exercise.homeworkproblem.repository;

import com.exercise.homeworkproblem.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

}