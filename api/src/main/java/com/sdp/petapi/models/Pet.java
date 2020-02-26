package com.sdp.petapi.models;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@NoArgsConstructor
public @Data class Pet {
  private String id;
  private String name;
  private String type;
  private String sex; // M, F
  private String age; // newborn, young, adult
  private String size; // small, medium, large, extra large
  private Double weight;
  private String dateAdded;
  private String description;
  private String[] imageNames; // link to photos
  private boolean isAdopted;

}
