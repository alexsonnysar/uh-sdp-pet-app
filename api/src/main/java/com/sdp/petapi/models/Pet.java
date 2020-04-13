package com.sdp.petapi.models;

import java.util.*;
import java.util.stream.*;

import lombok.NoArgsConstructor;
import lombok.Data;

import org.springframework.data.annotation.Id;

@NoArgsConstructor
public @Data class Pet {
  @Id
  private String id;

  private String name;
  private String type;
  private String sex; // M, F
  private String age; // newborn, young, adult
  private String size; // small, medium, large, extra large
  private Double weight;
  private Date dateAdded;
  private String description;
  private String[] imageNames = {}; // link to photos
  private boolean isAdopted;
  private boolean isActive;

  private String capitalizeName(String name) {
    return Arrays.stream(name.split(" ")).map(n -> n.substring(0,1).toUpperCase() + n.substring(1).toLowerCase())
        .collect(Collectors.joining(" "));
  }
  
  public Pet(String name, String type, String sex, String age, String size, double weight, String description,
    List<String> images) {
      this.name = capitalizeName(name);
      this.type = type;
      this.sex = sex;
      this.age = age;
      this.size = size;
      this.weight = weight;
      this.description = description;
      this.imageNames = new String[images.size()];
      images.stream().collect(Collectors.toSet()).toArray(this.imageNames);
      this.dateAdded = new Date();
      this.isActive = true;
  }
  
  public Pet(String name, String type, String sex, String age, String size, double weight, String description,
    String[] images) {
      this.name = capitalizeName(name);
      this.type = type;
      this.sex = sex;
      this.age = age;
      this.size = size;
      this.weight = weight;
      this.description = description;
      this.imageNames = new String[images.length];
      Arrays.asList(images).stream().collect(Collectors.toSet()).toArray(this.imageNames);
      this.dateAdded = new Date();
      this.isActive = true;
  }
  
  public Pet(String id, String name, String type, String sex, String age, String size, Double weight, Date date,
    String desc, List<String> images, boolean adopt, boolean status){
      this.id = id;
      this.name = capitalizeName(name);
      this.type = type;
      this.sex = sex;
      this.age = age;
      this.size = size;
      this.weight = weight;
      this.dateAdded = date;
      this.description = desc;
      this.imageNames = new String[images.size()];
      images.stream().collect(Collectors.toSet()).toArray(this.imageNames);
      this.isAdopted = adopt;
      this.isActive = status;
  }
  
  public Pet(String id, String name, String type, String sex, String age, String size, Double weight, Date date,
    String desc, String[] images, boolean adopt, boolean status){
      this.id = id;
      this.name = capitalizeName(name);
      this.type = type;
      this.sex = sex;
      this.age = age;
      this.size = size;
      this.weight = weight;
      this.dateAdded = date;
      this.description = desc;
      this.imageNames = new String[images.length];
      Arrays.asList(images).stream().collect(Collectors.toSet()).toArray(this.imageNames);
      this.isAdopted = adopt;
      this.isActive = status;
  }

}