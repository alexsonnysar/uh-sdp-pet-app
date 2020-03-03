package com.sdp.petapi.models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.*;
import java.util.stream.*;

import java.util.*;

@SpringBootTest
class PetTest {

  @Test
  public void create() throws Exception {
<<<<<<< Updated upstream
    Date buddy_got_here = new Date();
    ArrayList<String> pics_of_buddy = new ArrayList<String>();
    pics_of_buddy.add("walking in the park");
    pics_of_buddy.add("catching a frisbee");
    pics_of_buddy.add("biting the neighbors kid...");
    Pet sample_pet = new Pet("Buddy", "dog", "M", "old_af", "medium", 123.4,
        "He is very wet. Just like all the time", pics_of_buddy);
    Date now = new Date();

    assertEquals(sample_pet.getName(), "Buddy");
    assertNotEquals(sample_pet, null);
    assertEquals(sample_pet.getType(), "dog");
    assertAll(() -> assertEquals(
      sample_pet.getName(), "Buddy"),
      () -> assertEquals(sample_pet.getType(), "dog"),
      () -> assertEquals(sample_pet.getDateAdded().compareTo(buddy_got_here)/Math.abs(sample_pet.getDateAdded().compareTo(buddy_got_here)),1),
      () -> assertEquals(sample_pet.getDateAdded().compareTo(now)/Math.abs(sample_pet.getDateAdded().compareTo(now)),-1)
    );
=======
    ArrayList<String> petPics =  new ArrayList<String>();
    petPics.add("walking in the park");
    petPics.add("catching a frisbee");
    petPics.add("biting the neighbors kid...");
>>>>>>> Stashed changes

    Pet sample_pet = new Pet( "009", "Buddy", "dog", "M", "old_af", "medium", 123.4, new Date(),
        "He is very wet. Just like all the time", petPics.stream().collect(Collectors.toSet()), false, true);
    
    assertAll(
      () -> assertNotEquals(sample_pet, null),
      () -> assertEquals(sample_pet.getName(), "Buddy"),
      () -> assertEquals(sample_pet.getType(), "dog"),
      () -> assertNotEquals(sample_pet.getDateAdded(), null),
      () -> assertEquals(sample_pet.getImageNames(), petPics.stream().collect(Collectors.toSet()))
    );
  }

}
