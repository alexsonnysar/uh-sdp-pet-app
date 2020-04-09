package com.sdp.petapi.security;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.sdp.petapi.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserDetailsImplTest {

  transient UserDetailsImpl userDetailsImpl;

  transient User employee, webUser;

  private static final String ROLE_USER = "ROLE_User";
  
  private static final UserDetailsImpl NULL = null;

  @BeforeEach
  public void init() throws Exception {
    ObjectMapper om = new ObjectMapper();
    employee = om.readValue(new File("src/test/java/com/sdp/petapi/resources/mocks/employeeObject.json"), User.class);
    webUser = om.readValue(new File("src/test/java/com/sdp/petapi/resources/mocks/webUserObject.json"), User.class);
    userDetailsImpl = new UserDetailsImpl(webUser.getId(), webUser.getEmail(), webUser.getPassHash(), Collections.singletonList(new SimpleGrantedAuthority(ROLE_USER)));
  }

  @AfterEach
  public void cleanup() {
  }

  @Test
  public void build_user() {
    List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(ROLE_USER));

    UserDetailsImpl expected = new UserDetailsImpl(webUser.getId(), webUser.getEmail(), webUser.getPassHash(), authorities);

    assertEquals(expected, UserDetailsImpl.build(webUser));
  }

  @Test
  public void build_employee() {
    List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_Employee"));

    UserDetailsImpl expected = new UserDetailsImpl(employee.getId(), employee.getEmail(), employee.getPassHash(), authorities);

    assertEquals(expected, UserDetailsImpl.build(employee));
  }

  @Test
  public void get_authorities() {
    List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(ROLE_USER));
    assertEquals(authorities, userDetailsImpl.getAuthorities());
  }

  @Test
  public void get_id() {
    String id = "002";
    assertEquals(id, userDetailsImpl.getId());
  }

  @Test
  public void get_email() {
    String email = "ironman@mail.com";
    assertEquals(email, userDetailsImpl.getEmail());
  }

  @Test
  public void get_password() {
    String pwd = "whoneedsone";
    assertEquals(pwd, userDetailsImpl.getPassword());
  }

  @Test
  public void get_username() {
    String email = "ironman@mail.com";
    assertEquals(email, userDetailsImpl.getUsername());
  }

  @Test
  public void is_account_non_expired_returns_true() {
    assertTrue(userDetailsImpl.isAccountNonExpired());
  }

  @Test
  public void is_account_non_locked() {
    assertTrue(userDetailsImpl.isAccountNonLocked());
  }

  @Test
  public void is_credential_non_expired() {
    assertTrue(userDetailsImpl.isCredentialsNonExpired());
  }

  @Test
  public void is_enabled() {
    assertTrue(userDetailsImpl.isEnabled());
  }

  @Test
  public void equals_returns_true() {
    UserDetailsImpl userDetailsImpl2 = new UserDetailsImpl(webUser.getId(), webUser.getEmail(), webUser.getPassHash(), Collections.singletonList(new SimpleGrantedAuthority(ROLE_USER)));
    assertTrue(userDetailsImpl.equals(userDetailsImpl2));
  }

  @Test
  public void equals_with_same_item_returns_true() {
    assertTrue(userDetailsImpl.equals(userDetailsImpl));
  }

  @Test
  public void not_equals_returns_false() {
    UserDetailsImpl userDetailsImpl2 = new UserDetailsImpl(employee.getId(), employee.getEmail(), employee.getPassHash(), Collections.singletonList(new SimpleGrantedAuthority("ROLE_Employee")));
    assertFalse(userDetailsImpl.equals(userDetailsImpl2));
  }

  @Test
  public void not_equals_with_null_returns_false() {
    assertFalse(userDetailsImpl.equals(NULL));
  }

  @Test
  public void not_equals_with_different_object_returns_false() {
    String notCorrectObject = "take that!";
    assertFalse(userDetailsImpl.equals(notCorrectObject));
  }

  @Test
  public void testHashCode() {
    assertNotNull(userDetailsImpl.hashCode());
  }

}