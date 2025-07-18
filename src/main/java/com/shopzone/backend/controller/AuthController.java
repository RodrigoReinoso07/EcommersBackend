package com.shopzone.backend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.shopzone.backend.service.implementation.AuthService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import com.shopzone.backend.config.JwtUtil;
import com.shopzone.backend.model.User;
import com.shopzone.backend.model.User_Role;
import com.shopzone.backend.service.IUserService;
import com.shopzone.backend.service.IUserRoleService;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    private final AuthService authService;
    private final IUserService userService;
    private final IUserRoleService userRoleService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthService authService, IUserService userService,
                          IUserRoleService userRoleService, JwtUtil jwtUtil) {
        this.authService = authService;
        this.userService = userService;
        this.userRoleService = userRoleService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials,  HttpServletResponse respons){
        
        String username = credentials.get("username");
        String password = credentials.get("password");
        
        try {
            
            String token = authService.login(username, password);

            Cookie cookie = new Cookie("token", token);
            cookie.setHttpOnly(true);  // Impide que JavaScript acceda a la cookie
            cookie.setSecure(false);   // Si estás usando HTTPS, configúralo en true
            cookie.setPath("/");       // Asegúrate de que esté disponible en todo el dominio
            cookie.setMaxAge(3600);
            respons.addCookie(cookie);

            return ResponseEntity.ok(token);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> credentials){
        
        String username = credentials.get("username");
        String password = credentials.get("password");
        String firstName = credentials.get("firstname");
        String lastName = credentials.get("lastname");

         try {
            if (userService.findByUsername(username) != null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuario ya existe");
            }
            // Encriptar la contraseña:
            password = userService.encriptPassword(password);

            User user = new User(username, password, true, firstName, lastName);
            
            userService.insertOrUpdate(user);

            User_Role userRole = new User_Role(user, "ROLE_USER");
            userRoleService.insertOrUpdate(userRole);

            // devolvemos el token JWT inmediatamente
            String token = authService.login(user.getUsername(), user.getPassword());
            return ResponseEntity.ok(Map.of("user", user, "token", token));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al registrar usuario");
        }
    }

    @GetMapping("/verify")
    public ResponseEntity<Map<String, Object>> verifyToken(@CookieValue(value = "token",defaultValue = "", required = false) String token) {
        Map<String, Object> response = new HashMap<>();
        try{
            if (token == null || token.isEmpty()) {
                response.put("message", "Token no presente o formato incorrecto");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            boolean isValid = jwtUtil.validateToken(token); // Necesitas implementar validateToken en AuthService

            if (isValid) {
                String stringRoles = jwtUtil.extractClaim(token, claims -> claims.get("roles", String.class));
                List<String> roles = List.of(stringRoles.split(","));
                response.put("roles", roles);  // Agregamos los roles a la respuesta
                return ResponseEntity.ok(response);
            } else {
                response.put("message", "Token inválido");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }

        } catch (Exception e) {
            response.put("message", "Error al verificar el token");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    @GetMapping("/logout")
    public ResponseEntity<?> logout(@CookieValue(value = "token",defaultValue = "", required = false) String token, HttpServletResponse response) {
        
        Cookie cookie = new Cookie("token", null); // debe ser el mismo nombre que usás para el token
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // o true si estás en producción con HTTPS
        cookie.setPath("/");
        cookie.setMaxAge(0); // indica que la cookie debe borrarse inmediatamente
        response.addCookie(cookie);

        return ResponseEntity.ok("Logout exitoso");
    }


}
