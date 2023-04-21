package com.jonasqf.myexpenses.account;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<Account> register(@RequestBody Account account) {

        Account newOwner = accountService.register(account);
        return new ResponseEntity<>(newOwner, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Collection<Account>> findAll() {

        Collection<Account> findAll = accountService.findAll();
        return new ResponseEntity<>(findAll, HttpStatus.OK);
    }

   @GetMapping(path = "/{id}")
    public ResponseEntity<Account> findById(@PathVariable UUID id){
        Optional<Account> findById = accountService.findById(id);
        if (findById.isPresent()) {
            return new ResponseEntity<>(findById.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Account> delete(@PathVariable UUID id) {
        Optional<Account> deleted = accountService.findById(id);
        if (deleted.isPresent()) {
            accountService.delete(deleted.get());
            return new ResponseEntity<>(deleted.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Account> update(@RequestBody Account account) {

        accountService.update(account);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

}
