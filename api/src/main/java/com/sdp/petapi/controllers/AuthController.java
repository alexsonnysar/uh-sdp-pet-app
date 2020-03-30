package com.sdp.petapi.controllers;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.sdp.petapi.models.LoginRequest;
import com.sdp.petapi.models.LoginResponse;
import com.sdp.petapi.models.Message;
import com.sdp.petapi.models.SignupRequest;
import com.sdp.petapi.models.User;
import com.sdp.petapi.repositories.UserRepository;
import com.sdp.petapi.security.JwtUtils;
import com.sdp.petapi.security.UserDetailsImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	// @Autowired
	// RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		// .authenticate will check that submitted info in LoginRequest is same as info in database throws exception otherwise
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));


		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(new LoginResponse(jwt, 
												 userDetails.getId(),
												 userDetails.getEmail(), 
												 roles));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

		// maybe user userService to check if email exists
		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new Message("Error: Email is already in use!"));
		}

		// Create new user's account
		User user = new User(null, signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()), signUpRequest.getName(), false, null, null);


		// maybe call userService here instead of saving directly to database
		userRepository.save(user);

		return ResponseEntity.ok(new Message("User registered successfully!"));
	}
}