package com.jonasqf.myexpenses.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/transactions")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @RequestMapping(method = RequestMethod.POST, value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Transaction> register(@RequestBody Transaction transaction) {

        Transaction newTransaction = transactionService.register(transaction);
        return new ResponseEntity<>(newTransaction, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Transaction>> findAll() {

        Collection<Transaction> findAll = transactionService.findAll();
        return new ResponseEntity<>(findAll, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Transaction> findById(@PathVariable Integer id){
        Optional<Transaction> findById = transactionService.findById(id);
        if (findById.isPresent()) {
            return new ResponseEntity<>(findById.get(), HttpStatus.OK);
        } else {
            throw new RuntimeException();
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<Transaction> delete(@PathVariable Integer id) {

        Optional<Transaction> deleted = transactionService.findById(id);
        if (deleted.isPresent()) {
            return new ResponseEntity<>(deleted.get(), HttpStatus.NOT_FOUND);
        }
        transactionService.delete(deleted.get());
        return new ResponseEntity<>(deleted.get(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Transaction> updateCustomer(@RequestBody Transaction transaction) {

        transactionService.update(transaction);
        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }

}
