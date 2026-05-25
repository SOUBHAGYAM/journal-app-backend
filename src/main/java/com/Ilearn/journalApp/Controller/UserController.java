package com.Ilearn.journalApp.Controller;

import com.Ilearn.journalApp.Entity.User;
import com.Ilearn.journalApp.Repository.UserRepository;
import com.Ilearn.journalApp.api.response.WeatherResponse;
import com.Ilearn.journalApp.service.UserService;
import com.Ilearn.journalApp.service.WeatherService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Tag(name = "User APIs", description = "Read, Update & Delete User")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	//@Autowired
	//private WeatherService weatherService;

	/*
	 * @GetMapping public List<User> findAll(){ return userService.getAll(); }
	 */

	@PutMapping
	public ResponseEntity<?> updateUser(@RequestBody User user) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		User userindb = userService.findByUsername(username);

		userindb.setPassword(user.getUsername());
		userindb.setPassword(user.getPassword());
		userService.saveNewUser(userindb);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping
	public ResponseEntity<?> deleteById(@RequestBody ObjectId id) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		userRepository.deleteByUsername(authentication.getName());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

/*	@GetMapping
	public ResponseEntity<?> greetings() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		WeatherResponse weatherResponse = weatherService.getWeather("Banglore");
		String greeting = "";
		if (weatherResponse != null) {
			greeting = ", Weather feels like " + weatherResponse.getCurrent().getFeelslike();
		}
		return new ResponseEntity<>("Hey " + authentication.getName() + greeting, HttpStatus.OK);
	}*/

}
