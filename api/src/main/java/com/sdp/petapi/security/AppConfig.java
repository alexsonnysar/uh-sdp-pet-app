package com.sdp.petapi.security;

import com.auth0.spring.security.api.JwtWebSecurityConfigurer;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import java.util.Arrays;


@Configuration
@EnableWebSecurity(debug = true)
public class AppConfig extends WebSecurityConfigurerAdapter {

    @Value(value = "${auth0.apiAudience}")
    private String apiAudience;
    @Value(value = "${auth0.issuer}")
    private String issuer;

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST"));
        configuration.setAllowCredentials(true);
        configuration.addAllowedHeader("Authorization");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors();
        JwtWebSecurityConfigurer
                .forRS256(apiAudience, issuer)
                .configure(http)
                .authorizeRequests()
                // getAllPets
                .antMatchers(HttpMethod.GET, "/pet").permitAll()
                // getPetById
                .antMatchers(HttpMethod.GET, "/pet/*").permitAll()
                // createPet
                .antMatchers(HttpMethod.POST, "/pet").hasAuthority("is:employee")
                // putPet
                .antMatchers(HttpMethod.PUT, "/pet").hasAuthority("is:employee")

                // getAllUsers
                .antMatchers(HttpMethod.GET, "/user").hasAuthority("is:employee")
                // getUserById
                .antMatchers(HttpMethod.GET, "/user/*").hasAuthority("is:employee")
                // createUser
                .antMatchers(HttpMethod.POST, "/user").permitAll()
                // putUser
                .antMatchers(HttpMethod.PUT, "/user/*").hasAuthority("is:user")
                // deleteUser
                .antMatchers(HttpMethod.DELETE, "/user/*").permitAll()
                // requestadoption
                .antMatchers(HttpMethod.DELETE, "/requestadoption").hasAuthority("is:user");

                // .antMatchers(HttpMethod.GET, "/api/private").authenticated()
                // .antMatchers(HttpMethod.GET, "/api/private-scoped").hasAuthority("is:employee");
    }

}