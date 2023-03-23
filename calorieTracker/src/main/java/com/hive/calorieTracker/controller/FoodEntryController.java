package com.hive.calorieTracker.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.hive.calorieTracker.model.FoodEntry;
import com.hive.calorieTracker.model.User;
import com.hive.calorieTracker.repository.FoodEntryRepo;
import com.hive.calorieTracker.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class FoodEntryController {
    @Autowired
    private FoodEntryRepo foodEntryRepo;

    @Autowired
    private UserRepo userRepo;

    @PostMapping("/{userid}/upload/foodEntry")
    public ResponseEntity<Object> addFoodEntry(@PathVariable Long userid, @RequestBody FoodEntry foodEntry){

        Optional<User> user = userRepo.findById(userid);
        if(user.isEmpty()){
            return ResponseHandler.parseResponse("user is not valid, first create a new user", HttpStatus.BAD_REQUEST, null);
        }
        foodEntry.setUserid(userid);
        FoodEntry foodObj = foodEntryRepo.save(foodEntry);
        foodObj.add(linkTo(methodOn(FoodEntryController.class).getFoodEntryById(foodObj.getId())).withSelfRel());
        foodObj.add(linkTo(methodOn(FoodEntryController.class).getAllEntries()).withRel("foodEntries"));
        return ResponseHandler.parseResponse("Success", HttpStatus.OK, foodObj);
    }

    @GetMapping("/getEntry/{id}")
    public ResponseEntity<Object> getFoodEntryById(@PathVariable Long id) {
        Optional<FoodEntry> foodEntry = foodEntryRepo.findById(id);
        if (foodEntry.isEmpty()) {
            return ResponseHandler.parseResponse("No food entry with the ID: " + id.toString() + " was found",
                    HttpStatus.NOT_FOUND, null);
        }
        foodEntry.get().add(linkTo(methodOn(FoodEntryController.class).getFoodEntryById(id)).withSelfRel());
        foodEntry.get().add(linkTo(methodOn(FoodEntryController.class).getAllEntries()).withRel("foodEntries"));
        return ResponseHandler.parseResponse("Success", HttpStatus.OK, foodEntry);
    }

    @GetMapping("/getAllEntries")
    public ResponseEntity<Object> getAllEntries(){
        try {
            List<FoodEntry> foodList = foodEntryRepo.findAll();

            foodList = foodList.stream().map(food -> {
                food.add(linkTo(methodOn(FoodEntryController.class).getFoodEntryById(food.getId())).withSelfRel());
                food.add(linkTo(methodOn(FoodEntryController.class).getAllEntries()).withRel("foodEntries"));
                return food;
            }).collect(Collectors.toList());

            if(foodList.isEmpty()){
                return ResponseHandler.parseResponse("No entries could be found", HttpStatus.NO_CONTENT,null);
            }

            return ResponseHandler.parseResponse("Successfully obtained entries", HttpStatus.OK,foodList);
        }
        catch (Exception e) {
            return ResponseHandler.parseResponse("Sorry, we are experiencing technical issues", HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }
}
