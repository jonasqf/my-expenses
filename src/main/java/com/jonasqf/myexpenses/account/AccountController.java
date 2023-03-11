package com.jonasqf.myexpenses.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/accounts")
public class AccountController {

    @Autowired
    AccountService accountService;

    @RequestMapping(method = RequestMethod.POST, value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Account> register(@RequestBody Account account) {

        Account newOwner = accountService.register(account);
        return new ResponseEntity<>(newOwner, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Account>> findAll() {

        Collection<Account> findAll = accountService.findAll();
        return new ResponseEntity<>(findAll, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Account> findById(@PathVariable UUID id){
        Optional<Account> findById = accountService.findById(id);
        if (findById.isPresent()) {
            return new ResponseEntity<>(findById.get(), HttpStatus.OK);
        } else {
            throw new RuntimeException();
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<Account> delete(@PathVariable UUID id) {

        Optional<Account> deleted = accountService.findById(id);
        if (deleted.isPresent()) {
            return new ResponseEntity<>(deleted.get(), HttpStatus.NOT_FOUND);
        }
        accountService.delete(deleted.get());
        return new ResponseEntity<>(deleted.get(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Account> update(@RequestBody Account account) {

        accountService.update(account);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

}
