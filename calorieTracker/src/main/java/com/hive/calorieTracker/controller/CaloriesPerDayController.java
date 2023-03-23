package com.hive.calorieTracker.controller;

import com.hive.calorieTracker.model.FoodEntry;
import com.hive.calorieTracker.repository.FoodEntryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
public class CaloriesPerDayController {

    @Autowired
    private FoodEntryRepo repo;

    @GetMapping("/getDayCalories/{FK_USER_ID}")
    public ResponseEntity<ArrayList<Integer>> getDayCalories(@PathVariable Date date, @PathVariable Long FK_USER_ID){
//        try {
            ArrayList<Integer> calorieList = new ArrayList<>();
            repo.findAllById(Collections.singleton(FK_USER_ID)).forEach(foodEntry -> {
                if (foodEntry.getEntryDate() == date){
                    calorieList.add(foodEntry.getCalories());
                }
            });

            if(calorieList.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(calorieList,HttpStatus.OK);
//        }
//        catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
    }
}
