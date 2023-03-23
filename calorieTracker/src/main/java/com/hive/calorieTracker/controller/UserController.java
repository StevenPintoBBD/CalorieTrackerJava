package com.hive.calorieTracker.controller;

import com.hive.calorieTracker.constants.Unit;
import com.hive.calorieTracker.model.User;
import com.hive.calorieTracker.repository.FoodEntryRepo;
import com.hive.calorieTracker.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
@RestController
public class UserController {

    @Autowired
    private FoodEntryRepo foodRepo;
    @Autowired
    private UserRepo userRepo;

    @GetMapping("/getUser/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable Long id) {
        try{
            Optional<User> user = userRepo.findById(id);
            if (user.isEmpty()) {
                return ResponseHandler.parseResponse("No user with the ID: " + id.toString() + " was found",
                        HttpStatus.NOT_FOUND, null);
            }

            user.get().add(linkTo(methodOn(UserController.class).getUserById(id)).withSelfRel());
            user.get().add(linkTo(methodOn(UserController.class).getAllUsers()).withRel("users"));
            return ResponseHandler.parseResponse("Success", HttpStatus.OK, user.get());
        } catch (Exception e) {
            return ResponseHandler.parseResponse("Invalid", HttpStatus.INTERNAL_SERVER_ERROR,null);
        }
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<Object> getAllUsers(){
        try {
            List<User> users = userRepo.findAll();
            if(users.isEmpty()){
                return ResponseHandler.parseResponse("No users found", HttpStatus.NO_CONTENT, null);
            }
            users = users.stream().map(user -> {
                        user.add(linkTo(methodOn(UserController.class).getUserById(user.getId())).withSelfRel());
                        user.add(linkTo(methodOn(UserController.class).getAllUsers()).withRel("users"));
                        return user;
                    }
            ).collect(Collectors.toList());

            return ResponseHandler.parseResponse("Success", HttpStatus.OK, users);
        }
        catch (Exception e) {
            return ResponseHandler.parseResponse("Sorry, we are experiencing technical difficulties", HttpStatus.INTERNAL_SERVER_ERROR,null);
        }
    }
    @PostMapping("/upload/user")
    public ResponseEntity<Object> addUser(@RequestBody User user) {

        //Do validation for the user
        if(user.getFirstName().isEmpty() || user.getLastName().isEmpty()){

            return ResponseHandler.parseResponse("Bad request: invalid first or last name ", HttpStatus.BAD_REQUEST,null);
        } else if (!(user.getPreferedUnit() == Unit.CAL || user.getPreferedUnit() == Unit.KJ)) {
            return ResponseHandler.parseResponse("Bad request: invalid unit, please select CAL or KJ ", HttpStatus.BAD_REQUEST,null);
        }

        User userObj = userRepo.save(user);
        userObj.add(linkTo(methodOn(UserController.class).getUserById(userObj.getId())).withSelfRel());
        userObj.add(linkTo(methodOn(UserController.class).getAllUsers()).withRel("users"));
        return ResponseHandler.parseResponse("Successfully added the user", HttpStatus.OK, userObj);
    }

    @PutMapping("/update/user/{id}")
    ResponseEntity<Object> updateUser(@PathVariable Long id, @RequestBody User user){
        Optional<User> userCheck = userRepo.findById(id);
        if(userCheck.isEmpty()){
            return ResponseHandler.parseResponse("Can not update, user does not exist", HttpStatus.BAD_REQUEST,null);
        } else {
            User updatedUser = userCheck.get();
            if(user.getFirstName().isEmpty() || user.getLastName().isEmpty()){

                return ResponseHandler.parseResponse("Bad request: invalid first or last name ", HttpStatus.BAD_REQUEST,null);
            } else if (!(user.getPreferedUnit() == Unit.CAL || user.getPreferedUnit() == Unit.KJ)) {
                return ResponseHandler.parseResponse("Bad request: invalid unit, please select CAL or KJ ", HttpStatus.BAD_REQUEST,null);
            }
            updatedUser.setFirstName(user.getFirstName());
            updatedUser.setLastName(user.getLastName());
            updatedUser.setBmr(user.getBmr());
            updatedUser.setAllowance(user.getAllowance());
            updatedUser.setUsername(user.getUsername());
            updatedUser.setPreferedUnit(user.getPreferedUnit());

            User userObj = userRepo.save(updatedUser);
            userObj.add(linkTo(methodOn(UserController.class).getUserById(userObj.getId())).withSelfRel());
            userObj.add(linkTo(methodOn(UserController.class).getAllUsers()).withSelfRel());
            return ResponseHandler.parseResponse("Successfully updated user: " + id.toString(), HttpStatus.OK, userObj);
        }
    }

    @DeleteMapping("/remove/user/{id}")
    public ResponseEntity<Object> removeUser(@PathVariable Long id) {
        Optional<User> userCheck = userRepo.findById(id);
        if(userCheck.isEmpty()){
            return ResponseHandler.parseResponse("Can not delete, user does not exist", HttpStatus.BAD_REQUEST,null);
        } else {
            userRepo.deleteById(id);
            return ResponseHandler.parseResponse("Successfully deleted the user", HttpStatus.OK,null);
        }
    }
}
