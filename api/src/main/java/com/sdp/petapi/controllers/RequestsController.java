package com.sdp.petapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sdp.petapi.models.RequestInformation;
import com.sdp.petapi.models.Requests;
import com.sdp.petapi.security.UserDetailsImpl;
import com.sdp.petapi.services.RequestsService;

@RestController
@CrossOrigin
@RequestMapping("/request")
public class RequestsController {

  @Autowired
  transient RequestsService reqService;

  transient RequestInformation reqInfo;

  @GetMapping
  @PreAuthorize("hasRole('Employee')")
  public List<Requests> getAllRequests() {
    return reqService.getAllRequests();
  }

  @GetMapping("/{reqid}")
  @PreAuthorize("hasRole('Employee') or hasRole('User')")
  public Requests getRequestById(@PathVariable String reqid) {
    if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority("ROLE_Employee"))) {
      return reqService.getRequestById(reqid);
    }
    else {
      Requests req = reqService.getRequestById(reqid);
      UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      return (req != null && req.getUserid().equals(userDetails.getId())) ? req : null;
    }
  }

  @GetMapping("/request-info")
  @PreAuthorize("hasRole('Employee')")
  public List<RequestInformation> getAllRequestInfo() {
    return reqInfo.createPacket(reqService.getAllRequests());
  }

  @GetMapping("/request-info/{reqid}")
  @PreAuthorize("hasRole('Employee') or hasRole('User')")
  public RequestInformation getRequestInfoById(@PathVariable String reqid) {
    if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority("ROLE_Employee"))) {
      return reqInfo.createPacket(reqService.getRequestById(reqid));
    }
    else {
      Requests req = reqService.getRequestById(reqid);
      UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      return (req != null && req.getUserid().equals(userDetails.getId())) ? reqInfo.createPacket(req) : null;
    }
  }

  @PostMapping("/adopt/{petid}")
  @PreAuthorize("hasRole('User')")
  public Requests createRequest(@PathVariable String petid) {
    UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    String userid = userDetails.getId();
    return reqService.createRequest(userid, petid);
  }

  @PutMapping("/{reqid}")
  @PreAuthorize("hasRole('Employee') or hasRole('User')")
  public Requests putRequest(@PathVariable String reqid, @RequestBody String status) {

    Requests dbReq = reqService.getRequestById(reqid);

    if (!reqIdIsValid(dbReq)) {
      return null;
    } else {
      if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority("ROLE_Employee"))) {
        return (validEmployeeStatus(status)) ? reqService.putRequest(reqid, status) : null;
      }
      else {
        return (userCanUpdateRequest(dbReq, status)) ? reqService.putRequest(reqid, status) : null;
      }
    }
  }

  private Boolean reqIdIsValid(Requests dbReq) {
    return (dbReq != null && dbReq.getStatus().equals("PENDING"));
  }

  private Boolean validEmployeeStatus(String status) {
    return status != null && (status.equals("APPROVED") || status.equals("CANCELED"));
  }

  private Boolean userCanUpdateRequest(Requests dbReq, String status) {
    return userMadeRequest(dbReq) && validUserStatus(status);
  }

  private Boolean userMadeRequest(Requests dbReq) {
    UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return (dbReq.getUserid().equals(userDetails.getId()));
  }

  private Boolean validUserStatus(String status) {
    return (status.equals("CANCELED"));
  } 

@DeleteMapping("/{reqid}")
  public Requests deleteRequest(@PathVariable String reqid) {
    return reqService.deleteRequest(reqid);
  }
}
