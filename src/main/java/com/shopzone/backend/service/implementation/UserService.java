package com.shopzone.backend.service.implementation;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

import com.shopzone.backend.model.User;
import com.shopzone.backend.model.User_Role;
import com.shopzone.backend.service.IUserService;
import com.shopzone.backend.repository.IUserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service("userService")
public class UserService implements IUserService{

    private IUserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public UserService(IUserRepository userRepository, PasswordEncoder passwordEncoder) {

		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

    @Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		com.shopzone.backend.model.User user = userRepository.findByUsernameAndFetchUserRolesEagerly(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
		return buildUser(user, buildGrantedAuthorities(user.getUserRoles()));
	}

	private org.springframework.security.core.userdetails.User buildUser(User user, List<GrantedAuthority> grantedAuthorities) {
		return new org.springframework.security.core.userdetails.User(
            user.getUsername(), 
            user.getPassword(), 
            grantedAuthorities);
	}

	private List<GrantedAuthority> buildGrantedAuthorities(Set<User_Role> userRoles) {
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		for(User_Role userRole: userRoles) {
			grantedAuthorities.add(new SimpleGrantedAuthority(userRole.getRole()));
		}
		return new ArrayList<>(grantedAuthorities);
	}
	
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

    @Override
	public User insertOrUpdate(User user) {
		return userRepository.save(user);
	}
	
	public String encriptPassword(String password) {
		
		return passwordEncoder.encode(password);
		
	}
	
	public String generateRandomPassword()
    {

        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
 
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();
 
        for (int i = 0; i < 12; i++)
        {
            int randomIndex = random.nextInt(chars.length());
            sb.append(chars.charAt(randomIndex));
        }
 
        return sb.toString();
    }
	
	 public void logout(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = (SecurityContextHolder.getContext().getAuthentication());
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
	 }
}
