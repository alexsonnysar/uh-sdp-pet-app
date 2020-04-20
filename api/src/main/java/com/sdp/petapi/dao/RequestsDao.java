package com.sdp.petapi.dao;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sdp.petapi.models.*;
import com.sdp.petapi.repositories.RequestsRepository;

@Component
public class RequestsDao {

  @Autowired
  transient RequestsRepository repository;

  @Autowired
  transient UserDao userDao;

  @Autowired
  transient PetDao petDao;

  
  private static final String CANCELED_STRING = "CANCELED";
  private static final String APPROVED_STRING = "APPROVED";
  private static final String PENDING_STRING  = "PENDING";

  public List<Requests> getAllRequests() {
    return repository.findAll();
  }

  public Requests getRequestById(String reqid){
    if (reqid == null) return null;
    Optional<Requests> req = repository.findById(reqid);
    
    return req.isPresent() ? req.get() : null;
  }

  public Requests createRequest(String userid, String petid) {
    if(!isRequestValid(userid, petid)) return null;

    Pet pet = petDao.getPetById(petid);

    pet.setActive(false);
    pet.setAdopted(true);
    petDao.putPetByRequest(pet);

    Requests tempRequest = new Requests(userid, petid);
    Requests temp = repository.insert(tempRequest);
    return temp;
  }

  private Boolean isRequestValid(String userid, String petid) {
    return (areComponentsValid(userid, petid) && !isRequestDuplicate(userid, petid)) ? true : false;
  }

  private Boolean areComponentsValid(String userid, String petid) {
    return (isUserValid(userid) && isPetValid(petid)) ? true : false;
  }

  private Boolean isUserValid(String userid) {
    User user = userDao.getUserById(userid);
    return user != null && !user.isEmployee();
  }

  private Boolean isPetValid(String petid) {
    Pet pet = petDao.getPetById(petid);
    return pet != null && pet.isActive();
  }

  private Boolean isRequestDuplicate(String userid, String petid) {
    return getAllRequests()
      .stream()
      .anyMatch(r -> r.getPetid().equals(petid) && r.getUserid().equals(userid)
        && !r.getStatus().equals(CANCELED_STRING));
  }

  public Requests putRequests(String reqid, String status) {
    return (status.equals(APPROVED_STRING)) ? approveRequest(reqid) : 
      (status.equals(CANCELED_STRING)) ? cancelRequest(reqid) : null;
  }

  public Requests approveRequest(String reqid) {
    Requests req = getRequestById(reqid);

    List<Requests> cancelReqs = repository.findAll().stream()
      .filter(r -> !r.getUserid().equals(req.getUserid())
        && r.getPetid().equals(req.getPetid())
        && r.getStatus().equals(PENDING_STRING))
      .collect(Collectors.toList());
    
    if (!cancelReqs.isEmpty()) {
      cancelReqs.forEach(r -> r.setStatus(CANCELED_STRING));
      repository.saveAll(cancelReqs);
    }
    
    req.setStatus(APPROVED_STRING);
    return repository.save(req);
  }

  public Requests cancelRequest(String reqid) {
    Requests req = getRequestById(reqid);

    if (!repository.findAll().stream().anyMatch
    (r -> r.getPetid().equals(req.getPetid())
    && !r.getUserid().equals(req.getUserid()) 
    && !r.getStatus().equals(CANCELED_STRING))) 
    {
      Pet pet = petDao.getPetById(req.getPetid());
      pet.setActive(true);
      pet.setAdopted(false);
      petDao.putPetByRequest(pet);
    }

    req.setStatus(APPROVED_STRING);
    return repository.save(req);
  }

  public Requests deleteRequest(String reqid) {
    Requests req = getRequestById(reqid);
    if (req == null) return null;

    repository.delete(req);
    return req;
  }
  
  public RequestInformation getRequestInfoById(String reqid) {
    Requests req = getRequestById(reqid);
    System.out.println("before User Dao");
    User user = userDao.getUserById(req.getUserid());
    System.out.println("after User Dao");
    Pet pet = petDao.getPetById(req.getUserid());

    return new RequestInformation(req.getId(), req.getUserid(), user.getName(), user.getEmail(), req.getPetid(), pet.getName(), 
      (pet.getImageNames() != null && pet.getImageNames().length > 0) ? pet.getImageNames()[0] : "N/A",
      req.getRequestDate(), req.getStatus()
    );
  }

  public List<RequestInformation> getAllRequestInfo() {
    List<Requests> reqList = getAllRequests();
    System.out.println("before User Dao");
    List<User> userInfo = userDao.getAllUsers();
    System.out.println("after User Dao");
    List<Pet> petInfo = new PetDao().getAllPets();
    
    return reqList.stream().map(r -> new RequestInformation(r.getId(), 
        r.getUserid(), 
        userInfo.stream().filter(u -> u.getId().equals(r.getUserid())).findAny().get().getName(), 
        userInfo.stream().filter(u -> u.getId().equals(r.getUserid())).findAny().get().getEmail(), 
        r.getPetid(), 
        petInfo.stream().filter(p -> p.getId().equals(r.getPetid())).findAny().get().getName(), 
        "N/A", //change after images start getting stored
        r.getRequestDate(), 
        r.getStatus())
      ).collect(Collectors.toList());
  }

}