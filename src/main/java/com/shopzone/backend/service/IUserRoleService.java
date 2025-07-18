package com.shopzone.backend.service;

import java.util.List;

import com.shopzone.backend.model.User_Role;

public interface IUserRoleService {
    
    public User_Role insertOrUpdate(User_Role userRole) throws Exception;
	
	public List<User_Role> getAll();
	
	public boolean remove(int id) throws Exception;

}
