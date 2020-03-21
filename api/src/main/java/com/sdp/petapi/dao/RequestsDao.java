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
  private RequestsRepository repository;

  @Autowired
  private UserDao userDao;

  @Autowired
  private PetDao petDao;

  public List<Requests> getAllRequests() {
    return repository.findAll();
  }

  public Requests getRequestById(String reqid){
    Optional<Requests> req = repository.findById(reqid);
    
    return req.isPresent() ? req.get() : null;
  }

  public Requests createRequest(User user, String petid) {
    if(user.isEmployee() || petid == null) return null;

    User userdb = userDao.getUserById(user.getId());
    if (userdb != user) return null;

    Pet pet = petDao.getPetById(petid);
    if (pet == null || !pet.isActive()) return null;

    /* Conrad: right now make it so request makes status "PENDING" (happens in constructor) 
       and pet.isAdopted = T and pet.isActive = F */
    pet.setActive(false);
    pet.setAdopted(true);
    return repository.insert(new Requests(user.getId(), petid));
  }

  public Requests putRequests(User user, Requests req) {
    if(user == null || req == null || (!user.isEmployee() && user.getId() != req.getUserid())) return null;

    User userdb = userDao.getUserById(user.getId());
    if (userdb != user) return null;

    Requests reqdb = getRequestById(req.getId());
    if (reqdb == null || reqdb.getUserid() != req.getUserid()
      || reqdb.getPetid() != req.getPetid()
      || reqdb.getRequestDate() != req.getRequestDate()) return null;

    // WebUser canceling request means pet may be available for adoption again
    return (req.getStatus() == "CANCELED") ? cancelRequest(req) :
      (user.isEmployee() && req.getStatus() == "APPROVED") ? approveRequest(req) : null;
  }

  public Requests deleteRequest(String reqid) {
    Requests req = getRequestById(reqid);
    if (req == null) return null;

    repository.delete(req);
    return req;
  }

  public Requests approveRequest(Requests req) {
    List<Requests> cancelReqs = repository.findAll().stream()
      .filter(r -> r.getUserid() != req.getUserid() && r.getPetid() == req.getPetid() && r.getStatus() == "PENDING")
      .collect(Collectors.toList());
    
    cancelReqs.forEach(r -> r.setStatus("CANCELED"));
    repository.saveAll(cancelReqs);

    req.setStatus("APPROVE");
    repository.save(req);

    return req;
  }

  public Requests cancelRequest(Requests req) {
    String petid = req.getPetid();
    String userid = req.getUserid();
    if (!repository.findAll()
      .stream()
      .anyMatch(
        r -> r.getPetid() == petid
        && r.getUserid() != userid
        && r.getStatus() != "CANCELED"
      )
    ) {
      /* To undo Conrad's earlier comment */
      Pet pet = petDao.getPetById(petid);
      pet.setActive(true);
      pet.setAdopted(false);
      petDao.putPetByRequest(pet);
    }
    return repository.save(req);
  }

}