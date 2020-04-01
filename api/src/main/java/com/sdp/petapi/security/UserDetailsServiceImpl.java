package com.sdp.petapi.security;

import com.sdp.petapi.models.User;
import com.sdp.petapi.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
	@Transactional
	// read email from JWT and see if it exists in db
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + email));

		// build(user) will set the user authority based on the boolean value isEmployee
		return UserDetailsImpl.build(user);
		// basically this builds a new User with an id, email, password, name, employee = true or false
	}

}
