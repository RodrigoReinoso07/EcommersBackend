package com.shopzone.backend.filters;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.shopzone.backend.config.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {

        String token = extractTokenFromRequest(request);
        
        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }     

        if (token != null && jwtUtil.validateToken(token)) {
            
            String username = jwtUtil.extractUsername(token); // Extraemos el nombre de usuario
            String role = jwtUtil.extractClaim(token, claims -> claims.get("roles", String.class));

            if (username != null && role != null) {
                List<GrantedAuthority> authorities = Arrays.stream(role.split(","))
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

                Authentication authentication = new UsernamePasswordAuthenticationToken(
                    username, null, authorities
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }else{
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized: Invalid or expired token.") ; 
            return;
        }

        filterChain.doFilter(request, response);
    }

    private String extractTokenFromRequest (HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

   
    
}
