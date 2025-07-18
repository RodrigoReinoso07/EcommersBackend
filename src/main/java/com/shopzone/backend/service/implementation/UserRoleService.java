package com.shopzone.backend.service.implementation;
import java.util.List;

import org.springframework.stereotype.Service;

import com.shopzone.backend.model.User_Role;
import com.shopzone.backend.repository.IUserRoleRepository;
import com.shopzone.backend.service.IUserRoleService;

@Service
public class UserRoleService implements IUserRoleService{

    private final IUserRoleRepository userRoleRepository;

    public UserRoleService(IUserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }


    @Override
    public User_Role insertOrUpdate(User_Role userRole) throws Exception {
        
        return userRoleRepository.save(userRole);
    }

    @Override
    public List<User_Role> getAll() {
        return userRoleRepository.findAll();
    }

    @Override
    public boolean remove(int id) throws Exception {
        if (userRoleRepository.existsById(id)) {
            userRoleRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
}
