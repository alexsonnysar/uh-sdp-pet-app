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

  public List<Requests> getAllRequests(User user) {
    if (user == null || !user.isEmployee()) return null;

    User userdb = userDao.getUserById(user.getId());
    if (userdb != user) return null;

    return repository.findAll();
  }

  public Requests getRequestById(User user, String reqid){
    if (user == null || reqid == null) return null;

    User userdb = userDao.getUserById(user.getId());
    if (userdb != user) return null;
    
    Optional<Requests> req = repository.findById(reqid);
    
    return (req.isPresent() && (user.isEmployee() || (user.getId() == req.get().getUserid()))) ? req.get() : null;
  }

  public Requests createRequest(User user, String petid) {
    if(user.isEmployee() || petid == null) return null;

    User userdb = userDao.getUserById(user.getId());
    if (userdb != user) return null;

    Pet pet = petDao.getPetById(user, petid);
    if (pet == null) return null;

    /* Conrad: right now make it so request makes status "PENDING" (happens in constructor) 
       and pet.isAdopted = T and pet.isActive = F */
    pet.setActive(false);
    pet.setAdopted(true);
    return repository.insert(new Requests(user.getId(), petid));
  }

  public Requests putRequests(User user, Requests req) {
    if(user == null || req == null || req.getId() == null) return null;

    User userdb = userDao.getUserById(user.getId());
    if (userdb != user) return null;

    Requests reqdb = getRequestById(user, req.getId());
    if (reqdb == null || reqdb.getUserid() != req.getUserid()
      || reqdb.getPetid() != req.getPetid()
      || reqdb.getRequestDate() != req.getRequestDate()) return null;

    return (!user.isEmployee() && req.getStatus() == "CANCELED") ? repository.save(req) :
      (user.isEmployee() && req.getStatus() != "PENDING") ? repository.save(req) : null;
  }

  public Requests deleteRequest(User user, String reqid) {
    if(user == null || reqid == null) return null;

    User userdb = userDao.getUserById(user.getId());
    if (userdb != user || !user.isEmployee()) return null;

    Requests req = getRequestById(user, reqid);
    if (req == null) return null;

    repository.delete(req);
    return req;
  }

  public List<Requests> approveRequest(User user, String reqid) {
    if (user == null || !user.isEmployee() || reqid == null) return null;

    User userdb = userDao.getUserById(user.getId());
    if (userdb != user) return null;

    Requests req = getRequestById(user, reqid);
    if (req == null) return null;

    List<Requests> cancelReqs = repository.findAll().stream()
      .filter(r -> r.getUserid() != req.getUserid() && r.getPetid() == req.getPetid()).collect(Collectors.toList());
    
    cancelReqs.forEach(r -> r.setStatus("CANCELED"));
    repository.saveAll(cancelReqs);

    req.setStatus("APPROVE");
    repository.save(req);

    List<Requests> result = cancelReqs.subList(0, cancelReqs.size());
    result.add(req);
    return result;
  }

}