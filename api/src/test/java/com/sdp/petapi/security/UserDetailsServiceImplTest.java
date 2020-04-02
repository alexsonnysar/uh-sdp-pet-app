package com.sdp.petapi.security;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sdp.petapi.repositories.UserRepository;

import com.sdp.petapi.models.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserDetailsServiceImplTest {

  @Mock
  // @Autowired
  UserRepository userRepo;

  @Mock
  UserDetailsImpl udi;

  @InjectMocks
  // @Autowired
  UserDetailsServiceImpl udsi;

  User employee, webUser;
  UserDetailsImpl webUserDeets;

  @BeforeEach
  public void init() throws Exception {
    ObjectMapper om = new ObjectMapper();
    employee = om.readValue(new File("src/test/java/com/sdp/petapi/resources/mocks/employeeObject.json"), User.class);
    webUser = om.readValue(new File("src/test/java/com/sdp/petapi/resources/mocks/webUserObject.json"), User.class);
    userRepo.insert(webUser);
    webUserDeets = new UserDetailsImpl(webUser.getId(), webUser.getEmail(), webUser.getPassHash(), Collections.singletonList(new SimpleGrantedAuthority("ROLE_User")));
  }

  @AfterEach
  public void cleanup() {
    userRepo.deleteAll();
  }

  @Test
  public void load_existing_user_by_username_passes() {
    when(userRepo.findByEmail("1234@mail.com")).thenReturn(Optional.of(webUser));
    UserDetails expected = (UserDetails) udsi.loadUserByUsername("1234@mail.com");
    assertEquals(expected, (UserDetails) webUserDeets);
  }

  @Test
  public void load_nonexisting_user_by_username_fails() {
    Exception err = new UsernameNotFoundException("User Not Found with email: 1234@mail.com");
    when(userRepo.findByEmail("1234@mail.com")).thenReturn(Optional.empty());
    Exception actual_err = assertThrows(UsernameNotFoundException.class, () -> udsi.loadUserByUsername("1234@mail.com"));
    assertEquals(err.getMessage(), actual_err.getMessage());
  }
  
}