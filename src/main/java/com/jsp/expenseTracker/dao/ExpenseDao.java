package com.jsp.expenseTracker.dao;

import java.time.LocalDate;
import java.util.List;

import com.jsp.expenseTracker.entity.Expenses;

public interface ExpenseDao {

	Expenses addExpenses(Expenses expenses, int userId);

	List<Expenses> viewExpenses(int userId);

	Expenses updateExpense(Expenses expenses, int expenseId);

	boolean deleteExpense(int expenseId);

	Expenses findByExpenseId(int expenseId);

	List<Expenses> getExpenseBasedOnStartAndEndDate(LocalDate start, LocalDate end, int userId);

	List<Expenses> filterBasedOnDate(LocalDate date, int userId);

	List<Expenses> filterBasedOnCategory(String category, int userId);

	List<Expenses> filterBasedOnAmount(String range, int userId);

}
