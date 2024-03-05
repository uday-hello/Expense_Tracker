package com.jsp.expenseTracker.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.expenseTracker.entity.Expenses;

public interface ExpensesRepository extends JpaRepository<Expenses, Integer>{

}
