package com.shopzone.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.shopzone.backend.model.User_Role;


public interface IUserRoleRepository extends JpaRepository<User_Role, Integer> {
    
}
