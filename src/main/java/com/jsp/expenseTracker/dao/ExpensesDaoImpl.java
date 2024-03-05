package com.jsp.expenseTracker.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jsp.expenseTracker.entity.Expenses;
import com.jsp.expenseTracker.entity.User;
import com.jsp.expenseTracker.repository.ExpensesRepository;
import com.jsp.expenseTracker.repository.UserRepository;

@Component
public class ExpensesDaoImpl implements ExpenseDao{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ExpensesRepository expensesRepository;

	@Override
	public Expenses addExpenses(Expenses expenses, int userId) {
		//check whether userId is valid or not
		User user = userRepository.findById(userId).orElse(null);
		if(user != null) {
			//add expense
			//to add user object in Expense object
			expenses.setUserInfo(user);
			return expensesRepository.save(expenses);
		}
		return null;
	}

	@Override
	public List<Expenses> viewExpenses(int userId) {
		//check whether given userid is valid or not
		User user = userRepository.findById(userId).orElse(null);
		if(user != null) {
			//fetch expenses related to user
			return user.getExpenses();
		}
		return null;
	}

	@Override
	public Expenses updateExpense(Expenses expenses, int expenseId) {
		Expenses expensesFromDb = expensesRepository.findById(expenseId).orElse(null);
		if(expensesFromDb != null) {
			expensesFromDb.setAmount(expenses.getAmount());
			expensesFromDb.setDate(expenses.getDate());
			expensesFromDb.setDescription(expenses.getDescription());
			expensesFromDb.setExpenseCategory(expenses.getExpenseCategory());
			
			return expensesRepository.save(expensesFromDb);
		}
		return null;
	}

	@Override
	public boolean deleteExpense(int expenseId) {
		try {
			expensesRepository.deleteById(expenseId);
			return true;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Expenses findByExpenseId(int expenseId) {
		Expenses expenses = expensesRepository.findById(expenseId).orElse(null);
		return expenses;
	}

	@Override
	public List<Expenses> getExpenseBasedOnStartAndEndDate(LocalDate start, LocalDate end, int userId) {
		List<Expenses> listOfExpenses = viewExpenses(userId);
		
		//This is stream API
		List<Expenses> finalOutput = listOfExpenses.stream()
					  .filter(t -> {
							return !t.getDate().isBefore(start) && !t.getDate().isAfter(end);
						}).collect(Collectors.toList());
		System.out.println("demo"+ finalOutput);
		return finalOutput;
	}

	@Override
	public List<Expenses> filterBasedOnDate(LocalDate date, int userId) {
		List<Expenses> expenses = viewExpenses(userId);
		List<Expenses> finalOutput = expenses.stream()
				.filter(t -> t.getDate().equals(date)).collect(Collectors.toList());
		return finalOutput;
	}

	@Override
	public List<Expenses> filterBasedOnCategory(String category, int userId) {
		List<Expenses> expenses = viewExpenses(userId);
		List<Expenses> finalOutput = expenses.stream()
				.filter(t -> t.getExpenseCategory().equals(category)).collect(Collectors.toList());
		return finalOutput;
	}

	@Override
	public List<Expenses> filterBasedOnAmount(String range, int userId) {
		String[] arr = range.split("-");
		  List<Expenses> expenses = viewExpenses(userId);
		  
		  List<Expenses> finalOutput = expenses.stream()
		  		  .filter(t -> {
					int start = Integer.parseInt(arr[0]);
					int end = Integer.parseInt(arr[1]);
					return start <= t.getAmount() && end >= t.getAmount();
		  		  }).collect(Collectors.toList());
		  return finalOutput;
	}

}


























