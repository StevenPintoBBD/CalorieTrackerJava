package com.hive.calorieTracker.controller;

import com.hive.calorieTracker.constants.Status;
import com.hive.calorieTracker.constants.Unit;
import com.hive.calorieTracker.model.User;
import com.hive.calorieTracker.repository.FoodEntryRepo;
import com.hive.calorieTracker.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.jws.soap.SOAPBinding;
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
    ResponseEntity<Object> addUser(@RequestBody User user) {

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

    // I am unsure if the getUserAllowance and getUserBmr should be added to the linkTo stuff
    @GetMapping("/user/allowance/{username}")
    ResponseEntity<Object> getUserAllowance(@PathVariable String userName) {
        try {
            List<User> users = userRepo.selectUserAllowance(userName);
            if (users.isEmpty()) {
                return ResponseHandler.parseResponse("No user " + userName + "found", HttpStatus.NO_CONTENT, null);
            }
            users = users.stream().map(user -> {
                user.add(linkTo(methodOn(UserController.class).getUserById(user.getId())).withSelfRel());
                user.add(linkTo(methodOn(UserController.class).getAllUsers()).withRel("users"));
                user.add(linkTo(methodOn(UserController.class).getUserAllowance(user.getFirstName())).withSelfRel());
                user.add(linkTo(methodOn(UserController.class).getUserBmr(user.getFirstName())).withSelfRel());
                //I don't know if this should be added like this
                return user;
            }).collect(Collectors.toList());

            return ResponseHandler.parseResponse("Success", HttpStatus.OK, users);
        } catch (Exception e) {
            return ResponseHandler.parseResponse("Sorry, we are experiencing technical difficulties", HttpStatus.INTERNAL_SERVER_ERROR,null);
        }
    }


    @PutMapping("/user/allowance/{id}/lose_weight")
    ResponseEntity<Object> loseWeight(@PathVariable Long id) {
        Optional<User> userCheck = userRepo.findById(id);
        if (userCheck.isEmpty()) {
            return ResponseHandler.parseResponse("User does not exist", HttpStatus.BAD_REQUEST, null);
        } else {
            User updateUserStatus = userCheck.get();
            if (updateUserStatus.getStatus() == (Status.GAIN_WEIGHT)) {
                updateUserStatus.setStatus(Status.LOSE_WEIGHT);
            } else {
                return ResponseHandler.parseResponse("User already set to LOSE_WEIGHT", HttpStatus.METHOD_NOT_ALLOWED, null);
            }
            User userObj = userRepo.save(updateUserStatus);
            userObj.add(linkTo(methodOn(UserController.class).getUserById(userObj.getId())).withSelfRel());
            userObj.add(linkTo(methodOn(UserController.class).getAllUsers()).withRel("users"));
            userObj.add(linkTo(methodOn(UserController.class).getUserAllowance(userObj.getFirstName())).withSelfRel());
            userObj.add(linkTo(methodOn(UserController.class).getUserBmr(userObj.getFirstName())).withSelfRel());
            return ResponseHandler.parseResponse("Successfully update status to LOSE_WEIGHT: " + id.toString(), HttpStatus.OK, userObj);
        }
    }

    @PutMapping("/user/allowance/{id}/gain_weight")
    ResponseEntity<Object> gainWeight(@PathVariable Long id) {
        Optional<User> userCheck = userRepo.findById(id);
        if (userCheck.isEmpty()) {
            return ResponseHandler.parseResponse("User does not exist", HttpStatus.BAD_REQUEST, null);
        } else {
            User updateUserStatus = userCheck.get();
            if (updateUserStatus.getStatus() == (Status.LOSE_WEIGHT)) {
                updateUserStatus.setStatus(Status.GAIN_WEIGHT);
            } else {
                return ResponseHandler.parseResponse("User already set to GAIN_WEIGHT", HttpStatus.METHOD_NOT_ALLOWED, null);
            }
            User userObj = userRepo.save(updateUserStatus);
            userObj.add(linkTo(methodOn(UserController.class).getUserById(userObj.getId())).withSelfRel());
            userObj.add(linkTo(methodOn(UserController.class).getAllUsers()).withRel("users"));
            userObj.add(linkTo(methodOn(UserController.class).getUserAllowance(userObj.getFirstName())).withSelfRel());
            userObj.add(linkTo(methodOn(UserController.class).getUserBmr(userObj.getFirstName())).withSelfRel());
            return ResponseHandler.parseResponse("Successfully update status to GAIN_WEIGHT: " + id.toString(), HttpStatus.OK, userObj);
        }
    }

    @GetMapping("/user/bmr/{username}")
    ResponseEntity<Object> getUserBmr(@PathVariable String userName) {
        try {
            List<User> users = userRepo.selectUserBmr(userName);
            if (users.isEmpty()) {
                return ResponseHandler.parseResponse("No user " + userName + "found", HttpStatus.NO_CONTENT, null);
            }
            users = users.stream().map(user -> {
                user.add(linkTo(methodOn(UserController.class).getUserById(user.getId())).withSelfRel());
                user.add(linkTo(methodOn(UserController.class).getAllUsers()).withRel("users"));
                user.add(linkTo(methodOn(UserController.class).getUserAllowance(user.getFirstName())).withSelfRel());
                user.add(linkTo(methodOn(UserController.class).getUserBmr(user.getFirstName())).withSelfRel());
                return user;
            }).collect(Collectors.toList());

            return ResponseHandler.parseResponse("Success", HttpStatus.OK, users);
        } catch (Exception e) {
            return ResponseHandler.parseResponse("Sorry, we are experiencing technical difficulties", HttpStatus.INTERNAL_SERVER_ERROR,null);
        }
    }

    @PutMapping("/user/bmr/{id}/update")
    ResponseEntity<Object> updateBmr(@PathVariable Long id, int bmr) {
        Optional<User> userCheck = userRepo.findById(id);
        if(userCheck.isEmpty()){
            return ResponseHandler.parseResponse("Can not update, food user does not exist", HttpStatus.BAD_REQUEST,null);
        } else {
            User updatedUserBmr = userCheck.get();
            updatedUserBmr.setBmr(bmr);

            User userObj = userRepo.save(updatedUserBmr);
            userObj.add(linkTo(methodOn(UserController.class).getUserById(userObj.getId())).withSelfRel());
            userObj.add(linkTo(methodOn(UserController.class).getAllUsers()).withRel("users"));
            userObj.add(linkTo(methodOn(UserController.class).getUserAllowance(userObj.getFirstName())).withSelfRel());
            userObj.add(linkTo(methodOn(UserController.class).getUserBmr(userObj.getFirstName())).withSelfRel());
            return ResponseHandler.parseResponse("Successfully updated user bmr: " + id.toString(), HttpStatus.OK, userObj);
        }
    }
}