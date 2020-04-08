package com.sdp.petapi.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sdp.petapi.models.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
@SpringBootTest
public class AuthTokenFilterTest {

    @Mock
    transient JwtUtils jwtUtils;

    @Mock
    transient UserDetailsServiceImpl userDetailsService;

    @InjectMocks
    transient AuthTokenFilter authTokenFilter;

    transient User employee, webUser;
    transient UserDetailsImpl webUserDeets;  

    final static String TOKENSTRING = "TOKEN";

    @BeforeEach
    public void init() throws Exception {
        ObjectMapper om = new ObjectMapper();
        employee = om.readValue(new File("src/test/java/com/sdp/petapi/resources/mocks/employeeObject.json"), User.class);
        webUser = om.readValue(new File("src/test/java/com/sdp/petapi/resources/mocks/webUserObject.json"), User.class);
        webUserDeets = new UserDetailsImpl(webUser.getId(), webUser.getEmail(), webUser.getPassHash(), Collections.singletonList(new SimpleGrantedAuthority("ROLE_User")));
    }

    @Test
	public void testFilterIgnoresRequestsContainingNoAuthorizationHeader() throws Exception {

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setServletPath("/pet");
        final MockHttpServletResponse response = new MockHttpServletResponse();

        FilterChain chain = mock(FilterChain.class);
        authTokenFilter.doFilter(request, response, chain);

        verify(chain).doFilter(any(ServletRequest.class), any(ServletResponse.class));

        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
	public void testFilterIgnoresRequestsContainingAuthorizationHeaderNotStartingWithBearer() throws Exception {
        String token = "NOT_A_VALID_TOKEN";
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.addHeader("Authorization",
				"Bear " + token);
        
        request.setServletPath("/pet");
        final MockHttpServletResponse response = new MockHttpServletResponse();

        FilterChain chain = mock(FilterChain.class);
        authTokenFilter.doFilter(request, response, chain);

        verify(chain).doFilter(any(ServletRequest.class), any(ServletResponse.class));
        
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }
    
    @Test
	public void testInvalidTokenIsIgnored() throws Exception {
		String token = "NOT_A_VALID_TOKEN";
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.addHeader("Authorization",
				"Bearer " + token);
        request.setServletPath("/pet");
        
        final MockHttpServletResponse response = new MockHttpServletResponse();

		FilterChain chain = mock(FilterChain.class);
		authTokenFilter.doFilter(request, response, chain);

        verify(chain).doFilter(any(ServletRequest.class), any(ServletResponse.class));
        
        assertNull(SecurityContextHolder.getContext().getAuthentication());
		assertEquals(response.getStatus(), 200);
    }
    
    @Test
	public void testValidTokenAuthorization() throws Exception {
		String token = "NOT_A_VALID_TOKEN";
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.addHeader("Authorization",
				"Bearer " + token);
        request.setServletPath("/pet");
        
        final MockHttpServletResponse response = new MockHttpServletResponse();

        FilterChain chain = mock(FilterChain.class);
        
        when(jwtUtils.validateJwtToken(token)).thenReturn(true);
        when(jwtUtils.getUserNameFromJwtToken(token)).thenReturn(webUser.getEmail());
        when(userDetailsService.loadUserByUsername(webUser.getEmail())).thenReturn(new UserDetailsImpl(
            webUser.getId(), 
            webUser.getEmail(), 
            webUser.getPassHash(),
            Collections.singletonList(new SimpleGrantedAuthority("ROLE_User"))));


		authTokenFilter.doFilter(request, response, chain);


        verify(chain).doFilter(any(ServletRequest.class), any(ServletResponse.class));
        
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());

		assertEquals(response.getStatus(), 200);
    }
}
