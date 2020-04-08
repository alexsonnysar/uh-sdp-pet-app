// package com.sdp.petapi.security;

// import static org.mockito.Mockito.mock;
// import static org.mockito.Mockito.verify;
// import static org.mockito.Mockito.when;

// import java.io.IOException;

// import javax.servlet.FilterChain;
// import javax.servlet.ServletException;
// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;

// import org.junit.jupiter.api.AfterEach;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.junit.runner.RunWith;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.security.core.AuthenticationException;
// import org.springframework.test.context.ContextConfiguration;
// import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

// @SpringBootTest
// @RunWith(SpringJUnit4ClassRunner.class)
// @ContextConfiguration
// class AuthTokenFilterTest {

//   @Autowired
//   AuthTokenFilter atf;

//   @BeforeEach
//   public void init() throws Exception {
//     // ObjectMapper om = new ObjectMapper();
//     // employee = om.readValue(new File("src/test/java/com/sdp/petapi/resources/mocks/employeeObject.json"), User.class);
//     // webUser = om.readValue(new File("src/test/java/com/sdp/petapi/resources/mocks/webUserObject.json"), User.class);
//     // userRepo.insert(webUser);
//     // webUserDeets = new UserDetailsImpl(webUser.getId(), webUser.getEmail(), webUser.getPassHash(), Collections.singletonList(new SimpleGrantedAuthority("ROLE_User")));
//   }

//   @AfterEach
//   public void cleanup() {}
  
//   @Test
//   public void no_authorization_header_returns_null() throws IOException, ServletException {

//     HttpServletRequest request = mock(HttpServletRequest.class);
//     when(request.getHeader("Authorization")).thenReturn(null);
//     HttpServletResponse response = mock(HttpServletResponse.class);
//     FilterChain chain = mock(FilterChain.class);

//     atf.doFilterInternal(request, response, chain);
//     verify(response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: Unauthorized");
//   }


// }