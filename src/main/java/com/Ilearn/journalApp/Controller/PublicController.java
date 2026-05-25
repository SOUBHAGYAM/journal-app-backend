package com.Ilearn.journalApp.Controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Ilearn.journalApp.Entity.User;
import com.Ilearn.journalApp.dto.UserDto;
import com.Ilearn.journalApp.service.UserDetailsServiceImpl;
import com.Ilearn.journalApp.service.UserService;
import com.Ilearn.journalApp.utilis.JwtUtil;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/public")
@Slf4j
@Tag(name = "Public APIs")

public class PublicController {
	
	// railway rebuild trigger

	@Autowired
	private AuthenticationManager authenticationmanager;

	@Autowired
	private UserService userService;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Autowired
	private JwtUtil jwtutil;

	@GetMapping("/health")
	public String HealthCheck() {
		return "Healthy";
	}

	@PostMapping("/signup")
	public ResponseEntity<?> signup(
	        @RequestBody UserDto user
	) {

	    try {

	        User newUser = new User();

	        newUser.setUsername(user.getUsername());

	        newUser.setEmail(user.getEmail());

	        newUser.setPassword(user.getPassword());

	        newUser.setSentimentAnalysis(
	                user.isSentimentAnalysis()
	        );

	        boolean saved =
	                userService.saveNewUser(newUser);

	        if (saved) {

	        	Map<String, String> response =
	        	        new HashMap<>();

	        	response.put(
	        	        "message",
	        	        "Signup Successful"
	        	);

	        	return ResponseEntity.ok(response);
	        }

	        return ResponseEntity
	                .status(HttpStatus.BAD_REQUEST)
	                .body("User not saved");

	    } catch (Exception e) {

	        e.printStackTrace();

	        return ResponseEntity
	                .status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body(e.getMessage());
	    }
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(
	        @RequestBody User user
	) {

	    try {

	        authenticationmanager.authenticate(

	                new UsernamePasswordAuthenticationToken(
	                        user.getUsername(),
	                        user.getPassword()
	                )
	        );

	        UserDetails userDetails =
	                userDetailsService
	                        .loadUserByUsername(
	                                user.getUsername()
	                        );

	        String jwt =
	                jwtutil.generateToken(
	                        userDetails.getUsername()
	                );

	        Map<String, String> response =
	                new HashMap<>();

	        response.put("token", jwt);

	        System.out.println(
	                "JWT => " + jwt
	        );

	        return ResponseEntity.ok(response);

	    } catch (Exception e) {

	        e.printStackTrace();

	        return ResponseEntity
	                .status(HttpStatus.BAD_REQUEST)
	                .body("Incorrect username or password");
	    }
	}
}
