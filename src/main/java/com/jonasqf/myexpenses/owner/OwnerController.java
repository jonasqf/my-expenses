package com.jonasqf.myexpenses.owner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/owners")
public class OwnerController {

    @Autowired
    OwnerService ownerService;

    @RequestMapping(method = RequestMethod.POST, value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Owner> register(@RequestBody Owner owner) {

        Owner newOwner = ownerService.register(owner);
        return new ResponseEntity<>(newOwner, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Owner>> findAll() {

        Collection<Owner> findAll = ownerService.findAll();
        return new ResponseEntity<>(findAll, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Owner> findById(@PathVariable UUID id){
        Optional<Owner> findById = ownerService.findById(id);
        if (findById.isPresent()) {
            return new ResponseEntity<>(findById.get(), HttpStatus.OK);
        } else {
            throw new RuntimeException();
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<Owner> delete(@PathVariable UUID id) {

        Optional<Owner> deleted = ownerService.findById(id);
        if (deleted.isPresent()) {
            return new ResponseEntity<>(deleted.get(), HttpStatus.NOT_FOUND);
        }
        ownerService.delete(deleted.get());
        return new ResponseEntity<>(deleted.get(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Owner> update(@RequestBody Owner account) {

        ownerService.update(account);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

}
