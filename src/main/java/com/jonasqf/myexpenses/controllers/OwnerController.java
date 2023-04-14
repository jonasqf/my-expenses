package com.jonasqf.myexpenses.controllers;

import com.jonasqf.myexpenses.entities.Owner;
import com.jonasqf.myexpenses.services.OwnerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("api/v1/owners")
public class OwnerController {

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @PostMapping
    public ResponseEntity<Owner> register(@RequestBody Owner owner) {

        Owner newOwner = ownerService.register(owner);
        return new ResponseEntity<>(newOwner, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Collection<Owner>> findAll() {

        Collection<Owner> findAll = ownerService.findAll();
        return new ResponseEntity<>(findAll, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Owner> findById(@PathVariable UUID id){
        Optional<Owner> findById = ownerService.findById(id);
        if (findById.isPresent()) {
            return new ResponseEntity<>(findById.get(), HttpStatus.OK);
        } else {
            throw new RuntimeException();
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Owner> delete(@PathVariable UUID id) {

        Optional<Owner> deleted = ownerService.findById(id);
        if (deleted.isPresent()) {
            ownerService.delete(deleted.get());
            return new ResponseEntity<>(deleted.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

   @PutMapping(path = "/{id}")
    public ResponseEntity<Owner> update(@RequestBody Owner owner) {

        ownerService.update(owner);
        return new ResponseEntity<>(owner, HttpStatus.OK);
    }

}
