package com.sdp.petapi.models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.*;
import java.util.stream.*;
import java.text.SimpleDateFormat;

@SpringBootTest
class PetTest {

  @Test
  public void create() throws Exception {
    String[] images = {"walking in the park", "catching a frisbee", "biting the neighbors kid..."};
    ArrayList<String> petPics =  new ArrayList<String>();
    Collections.addAll(petPics, images);
    
    Date sample_date = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").parse("26-FEB-2020 18:16:17");

    Pet sample_pet = new Pet( "009", "Buddy", "dog", "M", "old_af", "medium", 123.4, sample_date,
        "He is very wet. Just like all the time", petPics.stream().collect(Collectors.toSet()), false, true);
    
    assertAll(
      () -> assertNotEquals(sample_pet, null),
      () -> assertEquals(sample_pet.getId(), "009"),
      () -> assertEquals(sample_pet.getName(), "Buddy"),
      () -> assertEquals(sample_pet.getType(), "dog"),
      () -> assertEquals(sample_pet.getSex(), "M"),
      () -> assertEquals(sample_pet.getAge(), "old_af"),
      () -> assertEquals(sample_pet.getSize(), "medium"),
      () -> assertEquals(sample_pet.getWeight(), 123.4),
      () -> assertEquals(sample_pet.getDateAdded(), sample_date),
      () -> assertEquals(sample_pet.getImageNames(), petPics.stream().collect(Collectors.toSet())),
      () -> assertFalse(sample_pet.isAdopted()),
      () -> assertTrue(sample_pet.isActive())
    );
  }

  @Test
  public void createImagesSameName() throws Exception {
    String[] images = {"walking in the park", "catching a frisbee", "biting the neighbors kid...", "catching a frisbee"};
    ArrayList<String> petPics =  new ArrayList<String>();
    Collections.addAll(petPics, images);
    Date sample_date = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").parse("26-FEB-2020 18:16:17");

    String[] imagesInserted = {"walking in the park", "catching a frisbee", "biting the neighbors kid..."};
    ArrayList<String> picsInserted = new ArrayList<String>();
    Collections.addAll(picsInserted, imagesInserted);

    Pet sample_pet = new Pet( "009", "Buddy", "dog", "M", "old_af", "medium", 123.4, sample_date,
        "He is very wet. Just like all the time", petPics.stream().collect(Collectors.toSet()), false, true);
    
    assertAll(
      () -> assertNotEquals(sample_pet, null),
      () -> assertEquals(sample_pet.getId(), "009"),
      () -> assertEquals(sample_pet.getName(), "Buddy"),
      () -> assertEquals(sample_pet.getType(), "dog"),
      () -> assertEquals(sample_pet.getSex(), "M"),
      () -> assertEquals(sample_pet.getAge(), "old_af"),
      () -> assertEquals(sample_pet.getSize(), "medium"),
      () -> assertEquals(sample_pet.getWeight(), 123.4),
      () -> assertEquals(sample_pet.getDateAdded(), sample_date),
      () -> assertEquals(sample_pet.getImageNames(), picsInserted.stream().collect(Collectors.toSet())),
      () -> assertFalse(sample_pet.isAdopted()),
      () -> assertTrue(sample_pet.isActive())
    );
  }

  @Test
  public void eight_argument_constructor() throws Exception {
    String name = "Buddy";
    String type = "dog";
    String sex = "M";
    String age = "old_af";
    String size = "medium";
    double weight = 123.4;
    String description = "He is very wet. Just like all the time";
    String[] images = {"walking in the park", "catching a frisbee", "biting the neighbors kid..."};
    ArrayList<String> petPics =  new ArrayList<String>();
    Collections.addAll(petPics, images);

    Pet pet = new Pet(name, type, sex, age, size, weight, description, petPics);
    assertAll(
      () -> assertNull(pet.getId()),
      () -> assertEquals(pet.getName(), name),
      () -> assertEquals(pet.getType(), type),
      () -> assertEquals(pet.getSex(), sex),
      () -> assertEquals(pet.getAge(), age),
      () -> assertEquals(pet.getSize(), size),
      () -> assertEquals(pet.getWeight(), weight),
      () -> assertNotNull(pet.getDateAdded()),
      () -> assertEquals(pet.getDescription(), description),
      () -> assertEquals(pet.getImageNames(), petPics.stream().collect(Collectors.toSet())),
      () -> assertTrue(pet.isActive()),
      () -> assertFalse(pet.isAdopted())
    );
  }

  @Test
  public void twelve_argument_constructor() throws Exception {
    String id = "009";
    String name = "Buddy";
    String type = "dog";
    String sex = "M";
    String age = "old_af";
    String size = "medium";
    double weight = 123.4;
    Date date = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").parse("26-FEB-2020 18:16:17");
    String description = "He is very wet. Just like all the time";
    String[] images = {"walking in the park", "catching a frisbee", "biting the neighbors kid..."};
    ArrayList<String> petPics =  new ArrayList<String>();
    Collections.addAll(petPics, images);
    Boolean adopt = false;
    Boolean active = true;

    Pet pet = new Pet(id, name, type, sex, age, size, weight, date, description, petPics, adopt, active);
    assertAll(
      () -> assertEquals(pet.getId(), id),
      () -> assertEquals(pet.getName(), name),
      () -> assertEquals(pet.getType(), type),
      () -> assertEquals(pet.getSex(), sex),
      () -> assertEquals(pet.getAge(), age),
      () -> assertEquals(pet.getSize(), size),
      () -> assertEquals(pet.getWeight(), weight),
      () -> assertEquals(pet.getDateAdded(), date),
      () -> assertEquals(pet.getDescription(), description),
      () -> assertEquals(pet.getImageNames(), petPics.stream().collect(Collectors.toSet())),
      () -> assertTrue(pet.isActive()),
      () -> assertFalse(pet.isAdopted())
    );
  }

}
