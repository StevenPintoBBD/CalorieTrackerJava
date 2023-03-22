package com.hive.calorieTracker.controller;

import com.hive.calorieTracker.model.FoodEntry;
import com.hive.calorieTracker.repository.FoodEntryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class FoodEntryController {
    @Autowired
    private FoodEntryRepo repo;

    @PostMapping("/{username}/upload/item")
    public ResponseEntity<FoodEntry> addItem(@PathVariable String username, @RequestBody FoodEntry foodEntry){

        FoodEntry foodObj = repo.save(foodEntry);

        return new ResponseEntity<>(foodObj, HttpStatus.OK);
    }

    @GetMapping("/getAllEntries")
    public ResponseEntity<List<FoodEntry>> getAllEntries(){
        try {
            List<FoodEntry> foodList = new ArrayList<>();
            repo.findAll().forEach(foodList::add);

            if(foodList.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(foodList,HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
