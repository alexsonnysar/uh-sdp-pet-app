package com.sdp.petapi.models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.*;
import java.util.stream.*;

@SpringBootTest
class PetTest {

  @Test
  public void create() throws Exception {
    ArrayList<String> petPics =  new ArrayList<String>();
    petPics.add("walking in the park");
    petPics.add("catching a frisbee");
    petPics.add("biting the neighbors kid...");

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

  @Test
  public void createImagesSameName() throws Exception {
    ArrayList<String> petPics =  new ArrayList<String>();
    petPics.add("walking in the park");
    petPics.add("catching a frisbee");
    petPics.add("biting the neighbors kid...");
    petPics.add("catching a frisbee");

    ArrayList<String> picsInserted = new ArrayList<String>();
    petPics.add("walking in the park");
    petPics.add("catching a frisbee");
    petPics.add("biting the neighbors kid...");

    Pet sample_pet = new Pet( "009", "Buddy", "dog", "M", "old_af", "medium", 123.4, new Date(),
        "He is very wet. Just like all the time", petPics.stream().collect(Collectors.toSet()), false, true);
    
    assertAll(
      () -> assertNotEquals(sample_pet, null),
      () -> assertEquals(sample_pet.getName(), "Buddy"),
      () -> assertEquals(sample_pet.getType(), "dog"),
      () -> assertNotEquals(sample_pet.getDateAdded(), null),
      () -> assertTrue(sample_pet.getImageNames().containsAll(picsInserted.stream().collect(Collectors.toSet())))
    );
  }

}
