package com.sdp.petapi.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.io.File;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sdp.petapi.services.UserService;
import com.sdp.petapi.controllers.AuthController;
import com.sdp.petapi.models.Message;
import com.sdp.petapi.models.Pet;
import com.sdp.petapi.models.SignupRequest;
import com.sdp.petapi.models.User;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
class AuthControllerTest {
	Pet pet;
	User employee, webUser;

	@Mock
	UserService userService;

	@Mock
	PasswordEncoder passwordEncoder;

	// makes a userController whose userService is the mock above
	@InjectMocks
	AuthController authController;

	@BeforeEach
	public void init() throws Exception {
		ObjectMapper om = new ObjectMapper();
		pet = om.readValue(new File("src/test/java/com/sdp/petapi/resources/mocks/petObject.json"), Pet.class);

		employee = om.readValue(new File("src/test/java/com/sdp/petapi/resources/mocks/employeeObject.json"),
				User.class);

		webUser = om.readValue(new File("src/test/java/com/sdp/petapi/resources/mocks/webUserObject.json"), User.class);
	}

	@AfterEach
	public void cleanup() {
	}

	@Test
	public void signup_new_user() {
		SignupRequest signUpRequest = new SignupRequest("test@gmail.com", "password", "test name");
		when(userService.existsByEmail(signUpRequest.getEmail())).thenReturn(false);
		when(userService.createUser(webUser)).thenReturn(webUser);
		when(passwordEncoder.encode(signUpRequest.getPassword())).thenReturn("Encoded password");
		Message returnedMessage = authController.registerUser(signUpRequest);
		assertEquals(new Message("User registered successfully!"), returnedMessage);
	}

	@Test
	public void signup_user_already_exists() {
		SignupRequest signUpRequest = new SignupRequest("test@gmail.com", "password", "test name");
		when(userService.existsByEmail(signUpRequest.getEmail())).thenReturn(true);
		Message returnedMessage = authController.registerUser(signUpRequest);
		assertEquals(new Message("Error: Email is already in use!"), returnedMessage);
	}

}