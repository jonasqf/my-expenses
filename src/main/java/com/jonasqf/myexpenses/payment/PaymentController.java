package com.jonasqf.myexpenses.payment;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/payments")
public class PaymentController {

    private final PaymentService transactionService;

    public PaymentController(PaymentService transactionService) {
        this.transactionService = transactionService;
    }

   @PostMapping
    public ResponseEntity<Payment> register(@RequestBody Payment transaction) {

        Payment newTransaction = transactionService.register(transaction);
        return new ResponseEntity<>(newTransaction, HttpStatus.CREATED);
    }

   @GetMapping
    public ResponseEntity<Collection<Payment>> findAll() {

        Collection<Payment> findAll = transactionService.findAll();
        return new ResponseEntity<>(findAll, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Payment> findById(@PathVariable UUID id){
        Optional<Payment> findById = transactionService.findById(id);
        if (findById.isPresent()) {
            return new ResponseEntity<>(findById.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Payment> delete(@PathVariable UUID id) {
        Optional<Payment> deleted = transactionService.findById(id);
        if (deleted.isPresent()) {
            transactionService.delete(deleted.get());
            return new ResponseEntity<>(deleted.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Payment> update(@RequestBody Payment transaction) {

        transactionService.update(transaction);
        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }

}
