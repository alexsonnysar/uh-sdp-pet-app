package com.sdp.petapi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdp.petapi.dao.RequestsDao;

import com.sdp.petapi.models.Requests;
import com.sdp.petapi.models.User;

@Service
public class RequestsService {

  @Autowired
  private RequestsDao reqDao;

  public List<Requests> getAllRequests(User user) {
    return reqDao.getAllRequests(user);
  }

  public Requests getRequestById(User user, String reqid){
    return reqDao.getRequestById(user, reqid);
  }

  public Requests createRequest(User user, String petid) {
    return reqDao.createRequest(user, petid);
  }

  public Requests putRequest(User user, Requests req) {
    return reqDao.putRequests(user, req);
  }

  public Requests deleteRequest(User user, String reqid) {
    return reqDao.deleteRequest(user, reqid);
  }

  public List<Requests> approveRequest(User user, String reqid) {
    return reqDao.approveRequest(user, reqid);
  }
}
