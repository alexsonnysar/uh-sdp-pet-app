package com.sdp.petapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sdp.petapi.models.Requests;

import com.sdp.petapi.services.RequestsService;

@RestController
@RequestMapping("/request")
public class RequestsController {

  @Autowired
  private RequestsService reqService;

  @GetMapping
  @CrossOrigin
  public List<Requests> getAllRequests() {
    return reqService.getAllRequests();
  }

  @GetMapping("/{id}")
  public Requests getRequestById(@PathVariable String id) {
    return reqService.getRequestById(id);
  }

  @PostMapping
  public Requests createRequest(@RequestBody Requests req) {
    return reqService.createRequest(req);
  }

  @PutMapping("/{id}")
  public Requests putRequest(@PathVariable String id, @RequestBody Requests req) {
    return (id == null || !id.equals(req.getId())) ? null : reqService.putRequest(req);
  }

  @DeleteMapping("/{id}")
  public Requests deleteRequest(@PathVariable String id) {
    return reqService.deleteRequest(id);
  }
}
