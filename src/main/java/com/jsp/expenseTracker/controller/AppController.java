package com.jsp.expenseTracker.controller;

import java.util.Base64;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jsp.expenseTracker.dao.ExpenseDao;
import com.jsp.expenseTracker.dao.UserDao;
import com.jsp.expenseTracker.entity.Expenses;
import com.jsp.expenseTracker.entity.User;

@Controller
public class AppController {

	@RequestMapping("/")
	public String index() {
		return "index";
	}

	@RequestMapping("home")
	public String home() {
		return "Home";
	}

	@RequestMapping("login")
	public String login() {
		return "login";
	}

	@RequestMapping("register")
	public String register() {
		return "register";
	}

	@RequestMapping("addExpense")
	public String addExpense() {
		return "AddExpense";
	}

	@RequestMapping("viewExpense")
	public String viewExpense() {
		return "viewExpense";
	}

	@RequestMapping("FilterExpense")
	public String filterExpense() {
		return "FilterExpense";
	}

	@RequestMapping("TotalExpense")
	public String totalExpense() {
		return "TotalExpense";
	}
	
	@Autowired
	private ExpenseDao expenseDao;
	
	@RequestMapping("UpdateExpense")
	public String updateExpense(@RequestParam("eId") int expenseId, Model model) {
		Expenses expenses = expenseDao.findByExpenseId(expenseId);
		model.addAttribute("expense", expenses);
		model.addAttribute("date",expenses.getDate().toString());
		return "UpdateExpens";
	}
	
	@Autowired
	private UserDao userDao;
	
	@RequestMapping("UpdateProfile")
	public String showUpdateProfile(Model model, HttpServletRequest request) {
		User user =(User)request.getSession().getAttribute("userData");
		User finalData = userDao.findById(user.getUserId());
		model.addAttribute("user", finalData);
		model.addAttribute("image", Base64.getMimeEncoder().encodeToString(finalData.getData()));
		return "UpdateProfile";
	}
}












