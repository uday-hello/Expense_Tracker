package com.jsp.expenseTracker.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jsp.expenseTracker.entity.User;
import com.jsp.expenseTracker.repository.UserRepository;

@Component
public class UserDaoImpl implements UserDao{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public User registerUser(User user) {
		/**
		 * to insert record in User table
		 */
		return userRepository.save(user);
	}

	@Override
	public User loginUser(String username, String password) {
		/**
		 * to verify user based on username and password
		 */
//		User user = userRepository.findByUserNameAndPassword(username, password).orElse(null);
//		return user;
		return userRepository.findByUserNameAndPassword(username, password).orElse(null);
	}

	@Override
	public boolean deleteUser(int userId) {
		try {
			userRepository.deleteById(userId);
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}

	@Override
	public User updateUserData(User user, int userId) {
		//fetch data from database
		User userFromDb = userRepository.findById(userId).orElse(null);
		System.out.println(userFromDb);
		//update data
		if(userFromDb != null) {
			//get user's data from an argument and store in database user object
			String username = user.getUserName();
			userFromDb.setUserName(username);
			userFromDb.setFullName(user.getFullName());
			userFromDb.setMobile(user.getMobile());
			userFromDb.setEmail(user.getEmail());
			userFromDb.setPassword(user.getPassword());
			userFromDb.setData(user.getData());
			//save data
			return userRepository.save(userFromDb);
		}
		return null;
	}

	@Override
	public User forgotPassword(String username, String newPassword) {
		/*
		 *  TODO: 1. fetch user data based on username
		 *  	  2. set newPassword in user object of Database
		 *  	  3. save user data
		 *  	  4. return updated user object to method
		 */
		
		return null;
	}

	@Override
	public User findById(int userId) {
		return userRepository.findById(userId).orElse(null);
	}

}










