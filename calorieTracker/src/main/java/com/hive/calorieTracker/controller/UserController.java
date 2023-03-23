package com.hive.calorieTracker.controller;

import com.hive.calorieTracker.exceptions.FoodEntryNotFoundException;
import com.hive.calorieTracker.model.FoodEntry;
import com.hive.calorieTracker.model.User;
import com.hive.calorieTracker.repository.FoodEntryRepo;
import com.hive.calorieTracker.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.hateoas.CollectionModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
@RestController
public class UserController {

    @Autowired
    private FoodEntryRepo foodRepo;
    @Autowired
    private UserRepo userRepo;

    @GetMapping("/getUser/{id}")
    public ResponseEntity<User> one(@PathVariable Long id) {

        User user = userRepo.findById(id)
                .orElseThrow(() -> new FoodEntryNotFoundException(id));
        user.add(linkTo(methodOn(UserController.class).one(id)).withSelfRel());
        user.add(linkTo(methodOn(UserController.class).getAllUsers()).withRel("users"));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<Collection<User>> getAllUsers(){
        try {
            Collection<User> users = userRepo.findAll();
            List<User> response = new ArrayList<>();
            users.forEach(user -> {
                user.add(linkTo(methodOn(UserController.class).one(user.getId())).withSelfRel());
                user.add(linkTo(methodOn(UserController.class).getAllUsers()).withRel("users"));
                response.add(user);
            });

            if(response.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/upload/user")
    ResponseEntity<?> addUser(@RequestBody User user){

        User userObj = userRepo.save(user);
        userObj.add(linkTo(methodOn(UserController.class).one(userObj.getId())).withSelfRel());
        return new ResponseEntity<>(userObj, HttpStatus.OK);
    }
}
