package com.shopzone.backend.dto;

import java.util.HashSet;
import java.util.Set;

import com.shopzone.backend.model.User_Role;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class UserDTO {
    
    private int id;
	
	private String username;
	
	private String password;
	
	private String firstname;
	
	private String lastname;
	
	private Set <User_Role> userRoles = new HashSet<User_Role>();

}
