package com.hive.calorieTracker.controller;

import com.hive.calorieTracker.model.Allowance;
import com.hive.calorieTracker.model.Status;
import com.hive.calorieTracker.repository.AllowanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class AllowanceController {
    @Autowired
    private AllowanceRepository allowanceRepo;

    @PutMapping("/allowance/upload/{id}")
    public ResponseEntity<Allowance> addAllowance(@PathVariable Long id, @RequestBody Allowance newAllowance) {
        Allowance allowanceObj = allowanceRepo.save(newAllowance);
        return new ResponseEntity<>(allowanceObj, HttpStatus.OK);
    }

    @GetMapping("/allowance")
    public ResponseEntity<List<Allowance>> getAllAllowance() {
        try {
            List<Allowance> allowanceList = new ArrayList<>();
            allowanceRepo.findAll().forEach(allowanceList::add);

            if (allowanceList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(allowanceList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/allowance/{id}")
    public ResponseEntity<Allowance> getAllowanceById(@PathVariable Long id) {
        Optional<Allowance> allowance = allowanceRepo.findById(id);

        if (allowance.isPresent()) {
            return new ResponseEntity<>(allowance.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/allowance/{id}/LOSE_WEIGHT")
    public ResponseEntity<Allowance> loseWeight(@PathVariable Long id) {
        Optional<Allowance> oldAllowance = allowanceRepo.findById(id);

        if (oldAllowance.isPresent()) {
            Allowance allowance = oldAllowance.get();
            if (allowance.getStatus() == (Status.GAIN_WEIGHT)) {
                allowance.setStatus(Status.LOSE_WEIGHT);
                Allowance allowanceObj = allowanceRepo.save(allowance);
                return new ResponseEntity<>(allowanceObj, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/allowance/{id}/GAIN_WEIGHT")
    public ResponseEntity<Allowance> gainWeight(@PathVariable Long id) {
        Optional<Allowance> oldAllowance = allowanceRepo.findById(id);

        if (oldAllowance.isPresent()) {
            Allowance allowance = oldAllowance.get();
            if (allowance.getStatus() == (Status.LOSE_WEIGHT)) {
                allowance.setStatus(Status.GAIN_WEIGHT);
                Allowance allowanceObj = allowanceRepo.save(allowance);
                return new ResponseEntity<>(allowanceObj, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
