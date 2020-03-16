package com.sdp.petapi.models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserTest {

  @Test
  public void create() throws Exception {
    User sample_user = new User("5e644862ba4beb24fd67c888", "pet-user@conrad-parker.com",
        "$toringP1ainTextPasswords!sNever@Good!dea@nd$hou1dBe@voided@t@11Cost", "bob", "joe", false);

    assertEquals(sample_user.getEmail(), "pet-user@conrad-parker.com");
    assertNotEquals(sample_user, null);
    assertEquals(sample_user.isEmployee(), false);
    assertAll(() -> assertEquals(sample_user.getEmail(), "pet-user@conrad-parker.com"),
        () -> assertEquals(sample_user.isEmployee(), false));

  }

}
