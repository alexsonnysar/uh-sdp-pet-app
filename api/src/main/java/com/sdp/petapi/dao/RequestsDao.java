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
    if (reqid == null) return null;
    Optional<Requests> req = repository.findById(reqid);
    
    return req.isPresent() ? req.get() : null;
  }

  public Requests createRequest(Requests req) {
    if(req == null || req.getId() != null) return null;

    User user = userDao.getUserById(req.getUserid());
    if (user == null || user.isEmployee()) return null;

    Pet pet = petDao.getPetById(req.getPetid());
    if (pet == null || !pet.isActive()) return null;

    Boolean existing_req = getAllRequests()
      .stream()
      .anyMatch(r -> r.getPetid().equals(req.getPetid()) && r.getUserid().equals(user.getId())
        && !r.getStatus().equals("CANCELED"));
    if (existing_req) return null;

    /* Conrad: right now make it so request makes status "PENDING" (happens in constructor) 
       and pet.isAdopted = T and pet.isActive = F */
    pet.setActive(false);
    pet.setAdopted(true);
    petDao.putPetByRequest(pet);
    return repository.insert(new Requests(user.getId(), pet.getId()));
  }

  public Requests putRequests(Requests req) {
    if(req == null) return null;

    User user = userDao.getUserById(req.getUserid());
    if (user == null) return null;

    Pet pet = petDao.getPetById(req.getPetid());
    if (pet == null) return null;

    Requests reqdb = getRequestById(req.getId());
    if (reqdb == null || !reqdb.getUserid().equals(req.getUserid())
      || !reqdb.getPetid().equals(req.getPetid())
      || !reqdb.getRequestDate().equals(req.getRequestDate()))
        return null;

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
      .filter(r -> !r.getUserid().equals(req.getUserid())
        && r.getPetid().equals(req.getPetid())
        && r.getStatus().equals("PENDING"))
      .collect(Collectors.toList());
    
    if (!cancelReqs.isEmpty()) {
      cancelReqs.forEach(r -> r.setStatus("CANCELED"));
      repository.saveAll(cancelReqs);
    }
    
    req.setStatus("APPROVED");
    repository.save(req);

    return req;
  }

  public Requests cancelRequest(Requests req) {
    String petid = req.getPetid();
    String userid = req.getUserid();

    if (!repository.findAll()
      .stream()
      .anyMatch(
        r -> r.getPetid().equals(petid)
        && !r.getUserid().equals(userid)
        && !r.getStatus().equals("CANCELED")
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