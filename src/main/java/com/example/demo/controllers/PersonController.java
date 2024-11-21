package com.example.demo.controllers;

import com.example.demo.models.Person;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/people")
public class PersonController {

    Logger logger = LoggerFactory.getLogger(PersonController.class);
    List<Person> PersonList = new ArrayList<>();

    @GetMapping()
    public ResponseEntity<List<Person>> getAllPersons(){
        return ResponseEntity.ok(PersonList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> getPerson(@PathVariable String id){
        Optional<Person> firstPerson = PersonList
                .stream()
                .filter(d -> d.getId().toString().equals(id))
                .findFirst();

        if (firstPerson.isPresent()) {
            return ResponseEntity.ok(firstPerson.get());
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Person> updatePerson(@Valid @RequestBody Person request, @PathVariable String id){
        Optional<Person> firstPerson = PersonList
                .stream()
                .filter(d -> d.getId().toString().equals(id))
                .findFirst();

        if (firstPerson.isPresent()) {
            PersonList.remove(firstPerson.get());
            PersonList.add(request);
            return ResponseEntity.ok(request);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping()
    public ResponseEntity<Person> createPerson(@Valid @RequestBody Person request) {
        request.setId(UUID.randomUUID());
        logger.info("Created Person with ID: {}", request.getId());
        PersonList.add(request);
        return ResponseEntity.ok(request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Person> deletePerson(@PathVariable String id) {
        Optional<Person> firstPerson = PersonList
                .stream()
                .filter(d -> d.getId().toString().equals(id))
                .findFirst();

        if (firstPerson.isPresent()) {
            PersonList.remove(firstPerson.get());
            return ResponseEntity.ok(firstPerson.get());
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
