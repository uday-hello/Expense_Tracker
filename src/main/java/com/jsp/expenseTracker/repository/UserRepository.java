package com.jsp.expenseTracker.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.expenseTracker.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{

	Optional<User> findByUserNameAndPassword(String username, String password);
}
