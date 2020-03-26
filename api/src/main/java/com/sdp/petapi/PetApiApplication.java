package com.sdp.petapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

import lombok.Generated;

import lombok.Generated;

@SpringBootApplication

@PropertySources({
	@PropertySource("classpath:application.properties"),
	@PropertySource("classpath:auth0.properties")
})
public class PetApiApplication {

  @Generated
  public static void main(String[] args) {
    SpringApplication.run(PetApiApplication.class, args);
  }

}
