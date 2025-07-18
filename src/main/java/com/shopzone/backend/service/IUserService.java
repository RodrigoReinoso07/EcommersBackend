package com.shopzone.backend.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.shopzone.backend.model.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface IUserService extends UserDetailsService {
    
    public User insertOrUpdate (User user);
	
	public String encriptPassword(String password);
	
	public User findByUsername(String username);
	
	public String generateRandomPassword();
	
	public void logout(HttpServletRequest request, HttpServletResponse response);

}
