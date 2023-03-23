package com.hive.calorieTracker.controller;

import com.hive.calorieTracker.model.Bmr;
import com.hive.calorieTracker.repository.BmrRepository;
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
class BmrController {
    @Autowired
    private BmrRepository bmrRepo;
    
    @PutMapping("/bmr/upload/{id}")
    public ResponseEntity<Bmr> addBmr(@PathVariable Long id, @RequestBody Bmr newBmr) {
        Bmr bmrObj = bmrRepo.save(newBmr);
        return new ResponseEntity<>(bmrObj, HttpStatus.OK);
    }

    @GetMapping("/bmr")
    public ResponseEntity<List<Bmr>> getAllBmr() {
        try {
            List<Bmr> bmrList = new ArrayList<>();
            bmrRepo.findAll().forEach(bmrList::add);

            if (bmrList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(bmrList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/bmr/{id}")
    public ResponseEntity<Bmr> getBmrById(@PathVariable Long id) {
        Optional<Bmr> bmr = bmrRepo.findById(id);

        if (bmr.isPresent()) {
            return new ResponseEntity<>(bmr.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
