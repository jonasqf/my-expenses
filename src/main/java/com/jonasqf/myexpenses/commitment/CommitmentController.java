package com.jonasqf.myexpenses.commitment;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/commitments")
public class CommitmentController {

    private final CommitmentService commitmentService;

    public CommitmentController(CommitmentService commitmentService) {
        this.commitmentService = commitmentService;
    }

    @PostMapping
    public ResponseEntity<Commitment> register(@RequestBody
                                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                   Commitment commitment) {

        Commitment commitment1 = commitmentService.register(commitment);
        return new ResponseEntity<>(commitment1, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Collection<Commitment>> findAll() {

        Collection<Commitment> findAll = commitmentService.findAll();
        return new ResponseEntity<>(findAll, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Commitment> findById(@PathVariable UUID id){
        Optional<Commitment> findById = commitmentService.findById(id);
        if (findById.isPresent()) {
            return new ResponseEntity<>(findById.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Commitment> delete(@PathVariable UUID id) {
        Optional<Commitment> deleted = commitmentService.findById(id);
        if (deleted.isPresent()) {
            commitmentService.delete(deleted.get());
            return new ResponseEntity<>(deleted.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Commitment> update(@RequestBody Commitment commitment) {
        commitmentService.update(commitment);
        return new ResponseEntity<>(commitment, HttpStatus.OK);
    }

}
