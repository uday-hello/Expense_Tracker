package com.jsp.expenseTracker.controller;

import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.jsp.expenseTracker.dao.ExpenseDao;
import com.jsp.expenseTracker.entity.Expenses;
import com.jsp.expenseTracker.entity.User;

@Controller
@RequestMapping("/expense")
public class ExpenseController {

	@Autowired
	private ExpenseDao expenseDao;

	@RequestMapping("addExpense")
	public ModelAndView addExpense(@ModelAttribute Expenses expenses, HttpServletRequest request,
			@RequestParam("expenseDate") String date) {
		User user = (User) request.getSession().getAttribute("userData");

		LocalDate convertedDate = LocalDate.parse(date);
		expenses.setDate(convertedDate);

		Expenses expensesFromDB = expenseDao.addExpenses(expenses, user.getUserId());

		ModelAndView mv = new ModelAndView();

		if (expensesFromDB != null) {
			// viewExpenses page
			// to redirect request to the another method of controller
			mv.setViewName("redirect:/expense/displayExpense"); // pass argumet as url of a method
			System.out.println("expenses added " + expensesFromDB.getExpenseId());
			return mv;
		}
		// addExpenses page
		mv.setViewName("addExpense"); // pass argument as html file name
		System.out.println("Invalid details...");
		return mv;
	}

	@RequestMapping("displayExpense")
	public ModelAndView displayExpense(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();

		User user = (User) request.getSession().getAttribute("userData");

		List<Expenses> listOfExpenses = expenseDao.viewExpenses(user.getUserId());

		mv.setViewName("viewExpense"); // pass argument as html file name
		System.out.println(listOfExpenses);
		mv.addObject("list", listOfExpenses);
		return mv;
	}

	@RequestMapping("deleteExpense")
	public ModelAndView deleteExpense(@RequestParam("eId") int expenseId) {
		System.out.println(expenseId);
		boolean status = expenseDao.deleteExpense(expenseId);
		ModelAndView mv = new ModelAndView();
		if (status) {
			// viewExpense page
			mv.setViewName("redirect:/expense/displayExpense");
			return mv;
		}
		return null;
	}

	@RequestMapping("updateExpense")
	public ModelAndView updateExpenseInDB(@ModelAttribute Expenses expenses, @RequestParam("expenseDate") String date) {

		expenses.setDate(LocalDate.parse(date));
		Expenses expensesFromDB = expenseDao.updateExpense(expenses, expenses.getExpenseId());

		ModelAndView mv = new ModelAndView();
		if (expensesFromDB != null) {
			// viewExpense page
			mv.setViewName("redirect:/expense/displayExpense");
			return mv;
		}
		return null;
	}

	@RequestMapping("totalExpense")
	public ModelAndView totalExpense(@RequestParam("start") String startDate, @RequestParam("end") String endDate,
			HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("userData");

		List<Expenses> listOfExpense = expenseDao.getExpenseBasedOnStartAndEndDate(LocalDate.parse(startDate),
				LocalDate.parse(endDate), user.getUserId());

		double sum = listOfExpense.stream().mapToDouble(t -> t.getAmount()).sum();

		ModelAndView mv = new ModelAndView();
		if (listOfExpense != null) {
			mv.addObject("sum", sum);
			mv.addObject("list", listOfExpense);
			mv.setViewName("viewExpense");
		}
		return mv;
	}
	
	@RequestMapping("filterExpense")
	public ModelAndView filterExpenseByCategoryDateAmount(HttpServletRequest request,
													@RequestParam("range")String range,
													@RequestParam("date")String date,
													@RequestParam("category")String category) {

		User user = (User)request.getSession().getAttribute("userData");
		System.out.println(range);
		ModelAndView mv = new ModelAndView();
		if(date != "") {
			LocalDate finalDate = LocalDate.parse(date);
			List<Expenses> listOfExpense = expenseDao.filterBasedOnDate(finalDate, user.getUserId());
			if (listOfExpense != null) {
				mv.addObject("list", listOfExpense);
			}
		}
		else if(!range.equals("-Select-")) {
			List<Expenses> listOfExpenses = expenseDao.filterBasedOnAmount(range, user.getUserId());
			mv.addObject("list", listOfExpenses);
		}
		else if(category != "") {
			List<Expenses> listOfExpenses = expenseDao.filterBasedOnCategory(category, user.getUserId());
		}
		mv.setViewName("viewExpense");
		return mv;
		
	}

}



















