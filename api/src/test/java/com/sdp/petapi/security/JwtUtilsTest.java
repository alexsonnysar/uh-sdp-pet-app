package com.sdp.petapi.security;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithUserDetails;

@SpringBootTest
class JwtUtilsTest {

    @Autowired
    JwtUtils jwtUtils;


    @Test
    @WithUserDetails(value = "User", userDetailsServiceBeanName = "TestingUserDetailsService")
    public void generate_token() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String token = jwtUtils.generateJwtToken(authentication);
        assertNotNull(token);

        String resultName = jwtUtils.getUserNameFromJwtToken(token);
        assertEquals("ironman@mail.com", resultName);


        Boolean result = jwtUtils.validateJwtToken(token);
		assertEquals(true, result);
    }

    @Test
    public void validate_bad_token() {
        String token = "badtoken";
        Boolean result = jwtUtils.validateJwtToken(token);
		assertEquals(false, result);
    }


}