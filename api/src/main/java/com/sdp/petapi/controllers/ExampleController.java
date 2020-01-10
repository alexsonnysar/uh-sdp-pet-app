package com.sdp.petapi.controllers;

import com.sdp.petapi.models.Example;
import com.sdp.petapi.services.ExampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RequestMapping("api")
@RestController
public class ExampleController {

    private final ExampleService exampleService;

    @Autowired
    public ExampleController(ExampleService exampleService) {
        this.exampleService = exampleService;
    }

    @GetMapping
    public String helloWorld(){
        //default GET response for /api
        return "Hello World!";
    }

    @GetMapping(path = "examples")
    public List<Example> getAllExamples() {
        //GET request that returns list of examples from fake database
        return exampleService.getAllExamples();
    }

    @PostMapping(path = "examples")
    public void addExample(@Valid @NotNull @RequestBody Example example) {
        //POST request that adds example from request body to fake database
        exampleService.addExample(example);
    }
}
