package com.sdp.petapi.security;

import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

public class TestUserDetailsServiceImpl implements UserDetailsService {

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String type) throws UsernameNotFoundException {

    if (type.equals("Employee")) {
      return new UserDetailsImpl("001", "1234@mail.com", "",
          Collections.singletonList(new SimpleGrantedAuthority("ROLE_Employee")));
    } else {
      return new UserDetailsImpl("002", "ironman@mail.com", "",
          Collections.singletonList(new SimpleGrantedAuthority("ROLE_User")));
    }
  }

}