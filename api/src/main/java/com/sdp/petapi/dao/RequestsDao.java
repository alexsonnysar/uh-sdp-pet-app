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

  public Requests createRequest(Requests req) {
    if(!isRequestValid(req)) return null;

    Pet pet = petDao.getPetById(req.getPetid());

    /* Conrad: right now make it so request makes status "PENDING" (happens in constructor) 
       and pet.isAdopted = T and pet.isActive = F */
    pet.setActive(false);
    pet.setAdopted(true);
    petDao.putPetByRequest(pet);
    return repository.insert(new Requests(req.getUserid(), req.getPetid()));
  }

  private Boolean isRequestValid(Requests req) {
    if(!isRequestGood(req)) return false;

    if(!areComponentsValid(req)) return false;

    if (isRequestDuplicate(req)) return false;
    return true;
  }

  private Boolean areComponentsValid(Requests req) {
    User user = userDao.getUserById(req.getUserid());
    if (!isUserValid(user)) return false;

    Pet pet = petDao.getPetById(req.getPetid());
    if (!isPetValid(pet)) return false;
    return true;
  }

  private Boolean isRequestGood(Requests req) {
    return req != null && req.getId() == null;
  }

  private Boolean isPetValid(Pet pet) {
    return pet != null && pet.isActive();
  }

  private Boolean isUserValid(User user) {
    return user != null && !user.isEmployee();
  }

  private Boolean isRequestDuplicate(Requests req) {
    return getAllRequests()
      .stream()
      .anyMatch(r -> r.getPetid().equals(req.getPetid()) && r.getUserid().equals(req.getUserid())
        && !r.getStatus().equals(CANCELED_STRING));
  }

  public Requests putRequests(Requests req) {
    if(!canAddRequest(req)) return null;

    return (req.getStatus().equals(CANCELED_STRING)) ? cancelRequest(req) : 
      (req.getStatus().equals(APPROVED_STRING)) ? approveRequest(req) : null;
  }

  private Boolean canAddRequest(Requests req) {
    if(!isValidPutRequest(req)) return false;

    Requests reqdb = getRequestById(req.getId());
    if (isRequestInDB(req, reqdb)) return false;
    return true;
  }

  private Boolean isValidPutRequest(Requests req) {
    if(req == null) return false;

    User user = userDao.getUserById(req.getUserid());
    if (user == null) return false;

    Pet pet = petDao.getPetById(req.getPetid());
    if (pet == null) return false;
    return true;
  }

  private Boolean isRequestInDB(Requests req, Requests reqdb) {
    return reqdb == null || !req.getUserid().equals(reqdb.getUserid())
      || !req.getPetid().equals(reqdb.getPetid())
      || !req.getRequestDate().equals(reqdb.getRequestDate());
  }

  public Requests deleteRequest(String reqid) {
    Requests req = getRequestById(reqid);
    if (req == null) return null;

    repository.delete(req);
    return req;
  }

  public Requests approveRequest(Requests req) {
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
    repository.save(req);

    return req;
  }

  public Requests cancelRequest(Requests req) {

    if (!repository.findAll()
      .stream()
      .anyMatch(
        r -> r.getPetid().equals(req.getPetid())
        && !r.getUserid().equals(req.getUserid())
        && !r.getStatus().equals(CANCELED_STRING)
      )
    ) {
      /* To undo Conrad's earlier comment */
      Pet pet = petDao.getPetById(req.getPetid());
      pet.setActive(true);
      pet.setAdopted(false);
      petDao.putPetByRequest(pet);
    }
    return repository.save(req);
  }

}