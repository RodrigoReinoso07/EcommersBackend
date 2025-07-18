package com.shopzone.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.shopzone.backend.model.User;

@Repository
public interface IUserRepository extends JpaRepository<User, Integer>{

    @Query("SELECT u FROM User u JOIN FETCH u.userRoles WHERE u.username = :username")
	public abstract User findByUsernameAndFetchUserRolesEagerly(@Param("username") String username);
	
	@Query("SELECT u FROM User u WHERE u.username = :username")
	public abstract User findByUsername(String username);

} 