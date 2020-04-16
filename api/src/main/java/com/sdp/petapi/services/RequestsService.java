package com.sdp.petapi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdp.petapi.dao.RequestsDao;

import com.sdp.petapi.models.Requests;

@Service
public class RequestsService {

  @Autowired
  transient RequestsDao reqDao;

  public List<Requests> getAllRequests() {
    return reqDao.getAllRequests();
  }

  public Requests getRequestById(String reqid){
    return reqDao.getRequestById(reqid);
  }

  public Requests createRequest(String userid, String petid) {
    return reqDao.createRequest(userid, petid);
  }

  public Requests putRequest(String reqid, String status) {
    return reqDao.putRequests(reqid, status);
  }

  public Requests deleteRequest(String reqid) {
    return reqDao.deleteRequest(reqid);
  }
}
