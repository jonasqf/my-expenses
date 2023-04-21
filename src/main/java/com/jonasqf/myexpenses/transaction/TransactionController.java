package com.jonasqf.myexpenses.transaction;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

   @PostMapping
    public ResponseEntity<Transaction> register(@RequestBody Transaction transaction) {

        Transaction newTransaction = transactionService.register(transaction);
        return new ResponseEntity<>(newTransaction, HttpStatus.CREATED);
    }

   @GetMapping
    public ResponseEntity<Collection<Transaction>> findAll() {

        Collection<Transaction> findAll = transactionService.findAll();
        return new ResponseEntity<>(findAll, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Transaction> findById(@PathVariable UUID id){
        Optional<Transaction> findById = transactionService.findById(id);
        if (findById.isPresent()) {
            return new ResponseEntity<>(findById.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Transaction> delete(@PathVariable UUID id) {
        Optional<Transaction> deleted = transactionService.findById(id);
        if (deleted.isPresent()) {
            transactionService.delete(deleted.get());
            return new ResponseEntity<>(deleted.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Transaction> update(@RequestBody Transaction transaction) {

        transactionService.update(transaction);
        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }

}
