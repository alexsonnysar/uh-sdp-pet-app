package com.sdp.petapi.models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.*;
import java.util.stream.*;
import java.text.SimpleDateFormat;

@SpringBootTest
class PetTest {

  private static final String BITING = "biting the neighbors kid...";
  private static final String FRISBEE = "catching a frisbee";
  private static final String PARK = "walking in the park";
  private static final String DATEFORMAT = "dd-MMM-yyyy HH:mm:ss";
  private static final String FEBDATE = "26-FEB-2020 18:16:17";
  private static final String ID009 = "009";
  private static final String BUDDY = "Buddy";
  private static final String DOG = "dog";
  private static final String MEDIUM = "medium";
  private static final String OLD = "old_af";
  private static final String WET = "He is very wet. Just like all the time";

  @Test
  public void createAllArgsImageArray() throws Exception {
    String[] images = { PARK, FRISBEE, BITING };

    String[] petPics = new String[images.length];
    Arrays.asList(images).stream().collect(Collectors.toSet()).toArray(petPics);
    Date sample_date = new SimpleDateFormat(DATEFORMAT, new Locale("en")).parse(FEBDATE);

    Pet sample_pet = new Pet(ID009, BUDDY, DOG, "M", OLD, MEDIUM, 123.4, sample_date, WET, images, false, true);

    assertNotNull(sample_pet);
    assertEquals(sample_pet.getId(), ID009);
    assertEquals(sample_pet.getName(), BUDDY);
    assertEquals(sample_pet.getType(), DOG);
    assertEquals(sample_pet.getSex(), "M");
    assertEquals(sample_pet.getAge(), OLD);
    assertEquals(sample_pet.getSize(), MEDIUM);
    assertEquals(sample_pet.getWeight(), 123.4);
    assertEquals(sample_pet.getDateAdded(), sample_date);
    assertArrayEquals(sample_pet.getImageNames(), petPics);
    assertFalse(sample_pet.isAdopted());
    assertTrue(sample_pet.isActive());
  }

  @Test
  public void createAllArgsImageArrayWithSameImageNames() throws Exception {
    String[] images = { PARK, FRISBEE, BITING, FRISBEE };

    String[] petPics = new String[images.length];
    Arrays.asList(images).stream().collect(Collectors.toSet()).toArray(petPics);

    Date sample_date = new SimpleDateFormat(DATEFORMAT, new Locale("en")).parse(FEBDATE);

    Pet sample_pet = new Pet(ID009, BUDDY, DOG, "M", OLD, MEDIUM, 123.4, sample_date, WET, images, false, true);

    assertNotNull(sample_pet);
    assertEquals(sample_pet.getId(), ID009);
    assertEquals(sample_pet.getName(), BUDDY);
    assertEquals(sample_pet.getType(), DOG);
    assertEquals(sample_pet.getSex(), "M");
    assertEquals(sample_pet.getAge(), OLD);
    assertEquals(sample_pet.getSize(), MEDIUM);
    assertEquals(sample_pet.getWeight(), 123.4);
    assertEquals(sample_pet.getDateAdded(), sample_date);
    assertArrayEquals(sample_pet.getImageNames(), petPics);
    assertFalse(sample_pet.isAdopted());
    assertTrue(sample_pet.isActive());
  }

  @Test
  public void createAllArgsImageList() throws Exception {
    String[] images = { PARK, FRISBEE, BITING };

    List<String> insImages = Arrays.asList(images);
    String[] petPics = new String[images.length];
    insImages.stream().collect(Collectors.toSet()).toArray(petPics);
    Date sample_date = new SimpleDateFormat(DATEFORMAT, new Locale("en")).parse(FEBDATE);

    Pet sample_pet = new Pet(ID009, BUDDY, DOG, "M", OLD, MEDIUM, 123.4, sample_date, WET, insImages, false, true);

    assertNotNull(sample_pet);
    assertEquals(sample_pet.getId(), ID009);
    assertEquals(sample_pet.getName(), BUDDY);
    assertEquals(sample_pet.getType(), DOG);
    assertEquals(sample_pet.getSex(), "M");
    assertEquals(sample_pet.getAge(), OLD);
    assertEquals(sample_pet.getSize(), MEDIUM);
    assertEquals(sample_pet.getWeight(), 123.4);
    assertEquals(sample_pet.getDateAdded(), sample_date);
    assertArrayEquals(sample_pet.getImageNames(), petPics);
    assertFalse(sample_pet.isAdopted());
    assertTrue(sample_pet.isActive());
  }

  @Test
  public void createAllArgsImageListWithSameImageNames() throws Exception {
    String[] images = { PARK, FRISBEE, BITING, FRISBEE };

    List<String> insImages = Arrays.asList(images);
    String[] petPics = new String[images.length];
    insImages.stream().collect(Collectors.toSet()).toArray(petPics);

    Date sample_date = new SimpleDateFormat(DATEFORMAT, new Locale("en")).parse(FEBDATE);

    Pet sample_pet = new Pet(ID009, BUDDY, DOG, "M", OLD, MEDIUM, 123.4, sample_date, WET, insImages, false, true);

    assertNotNull(sample_pet);
    assertEquals(sample_pet.getId(), ID009);
    assertEquals(sample_pet.getName(), BUDDY);
    assertEquals(sample_pet.getType(), DOG);
    assertEquals(sample_pet.getSex(), "M");
    assertEquals(sample_pet.getAge(), OLD);
    assertEquals(sample_pet.getSize(), MEDIUM);
    assertEquals(sample_pet.getWeight(), 123.4);
    assertEquals(sample_pet.getDateAdded(), sample_date);
    assertArrayEquals(sample_pet.getImageNames(), petPics);
    assertFalse(sample_pet.isAdopted());
    assertTrue(sample_pet.isActive());
  }

  @Test
  public void createEightArgsImageArray() throws Exception {
    String[] images = { PARK, FRISBEE, BITING };

    String[] petPics = new String[images.length];
    Arrays.asList(images).stream().collect(Collectors.toSet()).toArray(petPics);
    Date current = new Date();

    Pet sample_pet = new Pet(BUDDY, DOG, "M", OLD, MEDIUM, 123.4, WET, images);

    assertNotNull(sample_pet);
    assertNull(sample_pet.getId());
    assertEquals(sample_pet.getName(), BUDDY);
    assertEquals(sample_pet.getType(), DOG);
    assertEquals(sample_pet.getSex(), "M");
    assertEquals(sample_pet.getAge(), OLD);
    assertEquals(sample_pet.getSize(), MEDIUM);
    assertEquals(sample_pet.getWeight(), 123.4);
    assertNotNull(sample_pet.getDateAdded());
    assertTrue(sample_pet.getDateAdded().after(current) || sample_pet.getDateAdded().equals(current));
    assertArrayEquals(sample_pet.getImageNames(), petPics);
    assertFalse(sample_pet.isAdopted());
    assertTrue(sample_pet.isActive());
  }

  @Test
  public void createEightArgsImageArrayWithSameImageNames() throws Exception {
    String[] images = { PARK, FRISBEE, BITING, FRISBEE };

    String[] petPics = new String[images.length];
    Arrays.asList(images).stream().collect(Collectors.toSet()).toArray(petPics);
    Date current = new Date();

    Pet sample_pet = new Pet(BUDDY, DOG, "M", OLD, MEDIUM, 123.4, WET, images);

    assertNotNull(sample_pet);
    assertNull(sample_pet.getId());
    assertEquals(sample_pet.getName(), BUDDY);
    assertEquals(sample_pet.getType(), DOG);
    assertEquals(sample_pet.getSex(), "M");
    assertEquals(sample_pet.getAge(), OLD);
    assertEquals(sample_pet.getSize(), MEDIUM);
    assertEquals(sample_pet.getWeight(), 123.4);
    assertNotNull(sample_pet.getDateAdded());
    assertTrue(sample_pet.getDateAdded().after(current) || sample_pet.getDateAdded().equals(current));
    assertArrayEquals(sample_pet.getImageNames(), petPics);
    assertFalse(sample_pet.isAdopted());
    assertTrue(sample_pet.isActive());
  }

  @Test
  public void createEightArgsImageList() throws Exception {
    String[] images = { PARK, FRISBEE, BITING };

    List<String> insImages = Arrays.asList(images);
    String[] petPics = new String[images.length];
    Arrays.asList(images).stream().collect(Collectors.toSet()).toArray(petPics);
    Date current = new Date();

    Pet sample_pet = new Pet(BUDDY, DOG, "M", OLD, MEDIUM, 123.4, WET, insImages);

    assertNotNull(sample_pet);
    assertNull(sample_pet.getId());
    assertEquals(sample_pet.getName(), BUDDY);
    assertEquals(sample_pet.getType(), DOG);
    assertEquals(sample_pet.getSex(), "M");
    assertEquals(sample_pet.getAge(), OLD);
    assertEquals(sample_pet.getSize(), MEDIUM);
    assertEquals(sample_pet.getWeight(), 123.4);
    assertNotNull(sample_pet.getDateAdded());
    assertTrue(sample_pet.getDateAdded().after(current) || sample_pet.getDateAdded().equals(current));
    assertArrayEquals(sample_pet.getImageNames(), petPics);
    assertFalse(sample_pet.isAdopted());
    assertTrue(sample_pet.isActive());
  }

  @Test
  public void createEightArgsImageListWithSameImageNames() throws Exception {
    String[] images = { PARK, FRISBEE, BITING, FRISBEE };

    List<String> insImages = Arrays.asList(images);
    String[] petPics = new String[images.length];
    Arrays.asList(images).stream().collect(Collectors.toSet()).toArray(petPics);
    Date current = new Date();

    Pet sample_pet = new Pet(BUDDY, DOG, "M", OLD, MEDIUM, 123.4, WET, insImages);

    assertNotNull(sample_pet);
    assertNull(sample_pet.getId());
    assertEquals(sample_pet.getName(), BUDDY);
    assertEquals(sample_pet.getType(), DOG);
    assertEquals(sample_pet.getSex(), "M");
    assertEquals(sample_pet.getAge(), OLD);
    assertEquals(sample_pet.getSize(), MEDIUM);
    assertEquals(sample_pet.getWeight(), 123.4);
    assertNotNull(sample_pet.getDateAdded());
    assertTrue(sample_pet.getDateAdded().after(current) || sample_pet.getDateAdded().equals(current));
    assertArrayEquals(sample_pet.getImageNames(), petPics);
    assertFalse(sample_pet.isAdopted());
    assertTrue(sample_pet.isActive());
  }

}
