package com.sdp.petapi.models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
class PetTest {

  @Test
  public void create() throws Exception {
    Date buddy_got_here = new Date();
    String[] pics_of_buddy = { "walking in the park", "catching a frisbee", "biting the neighbors kid..." };
    Pet sample_pet = new Pet("001", "Buddy", "dog", "M", "old_af", "medium", 123.4, buddy_got_here,
        "He is very wet. Just like all the time", pics_of_buddy, false);

    assertEquals(sample_pet.getName(), "Buddy");
    assertNotEquals(sample_pet, null);
    assertEquals(sample_pet.getType(), "dog");
    assertAll(() -> assertEquals(sample_pet.getName(), "Buddy"), () -> assertEquals(sample_pet.getType(), "dog"),
        () -> assertEquals(sample_pet.getDateAdded(), buddy_got_here));

  }

}
