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
  public void createAllArgsImageArray() throws Exception {
    String[] images = {"walking in the park", "catching a frisbee", "biting the neighbors kid..."};

    String[] petPics = new String[images.length];
    Arrays.asList(images).stream().collect(Collectors.toSet()).toArray(petPics);
    
    Date sample_date = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").parse("26-FEB-2020 18:16:17");

    Pet sample_pet = new Pet( "009", "Buddy", "dog", "M", "old_af", "medium", 123.4, sample_date,
        "He is very wet. Just like all the time", images, false, true);
    
    assertAll(
      () -> assertNotNull(sample_pet),
      () -> assertEquals(sample_pet.getId(), "009"),
      () -> assertEquals(sample_pet.getName(), "Buddy"),
      () -> assertEquals(sample_pet.getType(), "dog"),
      () -> assertEquals(sample_pet.getSex(), "M"),
      () -> assertEquals(sample_pet.getAge(), "old_af"),
      () -> assertEquals(sample_pet.getSize(), "medium"),
      () -> assertEquals(sample_pet.getWeight(), 123.4),
      () -> assertEquals(sample_pet.getDateAdded(), sample_date),
      () -> assertArrayEquals(sample_pet.getImageNames(), petPics),
      () -> assertFalse(sample_pet.isAdopted()),
      () -> assertTrue(sample_pet.isActive())
    );
  }

  @Test
  public void createAllArgsImageArrayWithSameImageNames() throws Exception {
    String[] images = {"walking in the park", "catching a frisbee", "biting the neighbors kid...", "catching a frisbee"};

    String[] petPics = new String[images.length];
    Arrays.asList(images).stream().collect(Collectors.toSet()).toArray(petPics);

    Date sample_date = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").parse("26-FEB-2020 18:16:17");

    Pet sample_pet = new Pet( "009", "Buddy", "dog", "M", "old_af", "medium", 123.4, sample_date,
        "He is very wet. Just like all the time", images, false, true);
    
    assertAll(
      () -> assertNotNull(sample_pet),
      () -> assertEquals(sample_pet.getId(), "009"),
      () -> assertEquals(sample_pet.getName(), "Buddy"),
      () -> assertEquals(sample_pet.getType(), "dog"),
      () -> assertEquals(sample_pet.getSex(), "M"),
      () -> assertEquals(sample_pet.getAge(), "old_af"),
      () -> assertEquals(sample_pet.getSize(), "medium"),
      () -> assertEquals(sample_pet.getWeight(), 123.4),
      () -> assertEquals(sample_pet.getDateAdded(), sample_date),
      () -> assertArrayEquals(sample_pet.getImageNames(), petPics),
      () -> assertFalse(sample_pet.isAdopted()),
      () -> assertTrue(sample_pet.isActive())
    );
  }

  @Test
  public void createAllArgsImageList() throws Exception {
    String[] images = {"walking in the park", "catching a frisbee", "biting the neighbors kid..."};

    List<String> insImages = Arrays.asList(images);
    String[] petPics = new String[images.length];
    insImages.stream().collect(Collectors.toSet()).toArray(petPics);
    
    Date sample_date = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").parse("26-FEB-2020 18:16:17");

    Pet sample_pet = new Pet( "009", "Buddy", "dog", "M", "old_af", "medium", 123.4, sample_date,
        "He is very wet. Just like all the time", insImages, false, true);
    
    assertAll(
      () -> assertNotNull(sample_pet),
      () -> assertEquals(sample_pet.getId(), "009"),
      () -> assertEquals(sample_pet.getName(), "Buddy"),
      () -> assertEquals(sample_pet.getType(), "dog"),
      () -> assertEquals(sample_pet.getSex(), "M"),
      () -> assertEquals(sample_pet.getAge(), "old_af"),
      () -> assertEquals(sample_pet.getSize(), "medium"),
      () -> assertEquals(sample_pet.getWeight(), 123.4),
      () -> assertEquals(sample_pet.getDateAdded(), sample_date),
      () -> assertArrayEquals(sample_pet.getImageNames(), petPics),
      () -> assertFalse(sample_pet.isAdopted()),
      () -> assertTrue(sample_pet.isActive())
    );
  }

  @Test
  public void createAllArgsImageListWithSameImageNames() throws Exception {
    String[] images = {"walking in the park", "catching a frisbee", "biting the neighbors kid...", "catching a frisbee"};

    List<String> insImages = Arrays.asList(images);
    String[] petPics = new String[images.length];
    insImages.stream().collect(Collectors.toSet()).toArray(petPics);

    Date sample_date = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").parse("26-FEB-2020 18:16:17");

    Pet sample_pet = new Pet( "009", "Buddy", "dog", "M", "old_af", "medium", 123.4, sample_date,
        "He is very wet. Just like all the time", insImages, false, true);
    
    assertAll(
      () -> assertNotNull(sample_pet),
      () -> assertEquals(sample_pet.getId(), "009"),
      () -> assertEquals(sample_pet.getName(), "Buddy"),
      () -> assertEquals(sample_pet.getType(), "dog"),
      () -> assertEquals(sample_pet.getSex(), "M"),
      () -> assertEquals(sample_pet.getAge(), "old_af"),
      () -> assertEquals(sample_pet.getSize(), "medium"),
      () -> assertEquals(sample_pet.getWeight(), 123.4),
      () -> assertEquals(sample_pet.getDateAdded(), sample_date),
      () -> assertArrayEquals(sample_pet.getImageNames(), petPics),
      () -> assertFalse(sample_pet.isAdopted()),
      () -> assertTrue(sample_pet.isActive())
    );
  }

  @Test
  public void createEightArgsImageArray() throws Exception {
    String[] images = {"walking in the park", "catching a frisbee", "biting the neighbors kid..."};

    String[] petPics = new String[images.length];
    Arrays.asList(images).stream().collect(Collectors.toSet()).toArray(petPics);
    
    Date current = new Date();

    Pet sample_pet = new Pet( "Buddy", "dog", "M", "old_af", "medium", 123.4,
        "He is very wet. Just like all the time", images );
    
    assertAll(
      () -> assertNotNull(sample_pet),
      () -> assertNull(sample_pet.getId()),
      () -> assertEquals(sample_pet.getName(), "Buddy"),
      () -> assertEquals(sample_pet.getType(), "dog"),
      () -> assertEquals(sample_pet.getSex(), "M"),
      () -> assertEquals(sample_pet.getAge(), "old_af"),
      () -> assertEquals(sample_pet.getSize(), "medium"),
      () -> assertEquals(sample_pet.getWeight(), 123.4),
      () -> assertNotNull(sample_pet.getDateAdded()),
      () -> assertTrue(sample_pet.getDateAdded().after(current) || sample_pet.getDateAdded().equals(current)),
      () -> assertTrue(sample_pet.getDateAdded().before(new Date()) || sample_pet.getDateAdded().equals(current)),
      () -> assertArrayEquals(sample_pet.getImageNames(), petPics),
      () -> assertFalse(sample_pet.isAdopted()),
      () -> assertTrue(sample_pet.isActive())
    );
  }

  @Test
  public void createEightArgsImageArrayWithSameImageNames() throws Exception {
    String[] images = {"walking in the park", "catching a frisbee", "biting the neighbors kid...", "catching a frisbee"};

    String[] petPics = new String[images.length];
    Arrays.asList(images).stream().collect(Collectors.toSet()).toArray(petPics);
    
    Date current = new Date();

    Pet sample_pet = new Pet( "Buddy", "dog", "M", "old_af", "medium", 123.4,
        "He is very wet. Just like all the time", images );
    
    assertAll(
      () -> assertNotNull(sample_pet),
      () -> assertNull(sample_pet.getId()),
      () -> assertEquals(sample_pet.getName(), "Buddy"),
      () -> assertEquals(sample_pet.getType(), "dog"),
      () -> assertEquals(sample_pet.getSex(), "M"),
      () -> assertEquals(sample_pet.getAge(), "old_af"),
      () -> assertEquals(sample_pet.getSize(), "medium"),
      () -> assertEquals(sample_pet.getWeight(), 123.4),
      () -> assertNotNull(sample_pet.getDateAdded()),
      () -> assertTrue(sample_pet.getDateAdded().after(current) || sample_pet.getDateAdded().equals(current)),
      () -> assertTrue(sample_pet.getDateAdded().before(new Date()) || sample_pet.getDateAdded().equals(current)),
      () -> assertArrayEquals(sample_pet.getImageNames(), petPics),
      () -> assertFalse(sample_pet.isAdopted()),
      () -> assertTrue(sample_pet.isActive())
    );
  }

  @Test
  public void createEightArgsImageList() throws Exception {
    String[] images = {"walking in the park", "catching a frisbee", "biting the neighbors kid..."};

    List<String> insImages = Arrays.asList(images);
    String[] petPics = new String[images.length];
    Arrays.asList(images).stream().collect(Collectors.toSet()).toArray(petPics);
    
    Date current = new Date();

    Pet sample_pet = new Pet( "Buddy", "dog", "M", "old_af", "medium", 123.4,
        "He is very wet. Just like all the time", insImages );
    
    assertAll(
      () -> assertNotNull(sample_pet),
      () -> assertNull(sample_pet.getId()),
      () -> assertEquals(sample_pet.getName(), "Buddy"),
      () -> assertEquals(sample_pet.getType(), "dog"),
      () -> assertEquals(sample_pet.getSex(), "M"),
      () -> assertEquals(sample_pet.getAge(), "old_af"),
      () -> assertEquals(sample_pet.getSize(), "medium"),
      () -> assertEquals(sample_pet.getWeight(), 123.4),
      () -> assertNotNull(sample_pet.getDateAdded()),
      () -> assertTrue(sample_pet.getDateAdded().after(current) || sample_pet.getDateAdded().equals(current)),
      () -> assertTrue(sample_pet.getDateAdded().before(new Date()) || sample_pet.getDateAdded().equals(current)),
      () -> assertArrayEquals(sample_pet.getImageNames(), petPics),
      () -> assertFalse(sample_pet.isAdopted()),
      () -> assertTrue(sample_pet.isActive())
    );
  }

  @Test
  public void createEightArgsImageListWithSameImageNames() throws Exception {
    String[] images = {"walking in the park", "catching a frisbee", "biting the neighbors kid...", "catching a frisbee"};

    List<String> insImages = Arrays.asList(images);
    String[] petPics = new String[images.length];
    Arrays.asList(images).stream().collect(Collectors.toSet()).toArray(petPics);
    
    Date current = new Date();

    Pet sample_pet = new Pet( "Buddy", "dog", "M", "old_af", "medium", 123.4,
        "He is very wet. Just like all the time", insImages );
    
    assertAll(
      () -> assertNotNull(sample_pet),
      () -> assertNull(sample_pet.getId()),
      () -> assertEquals(sample_pet.getName(), "Buddy"),
      () -> assertEquals(sample_pet.getType(), "dog"),
      () -> assertEquals(sample_pet.getSex(), "M"),
      () -> assertEquals(sample_pet.getAge(), "old_af"),
      () -> assertEquals(sample_pet.getSize(), "medium"),
      () -> assertEquals(sample_pet.getWeight(), 123.4),
      () -> assertNotNull(sample_pet.getDateAdded()),
      () -> assertTrue(sample_pet.getDateAdded().after(current) || sample_pet.getDateAdded().equals(current)),
      () -> assertTrue(sample_pet.getDateAdded().before(new Date()) || sample_pet.getDateAdded().equals(current)),
      () -> assertArrayEquals(sample_pet.getImageNames(), petPics),
      () -> assertFalse(sample_pet.isAdopted()),
      () -> assertTrue(sample_pet.isActive())
    );
  }

}
