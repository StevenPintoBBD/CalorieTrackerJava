package com.hive.calorieTracker.controller;

import com.hive.calorieTracker.constants.UserModelAssembler;
import com.hive.calorieTracker.exceptions.FoodEntryNotFound;
import com.hive.calorieTracker.exceptions.FoodEntryNotFoundException;
import com.hive.calorieTracker.model.User;
import com.hive.calorieTracker.repository.FoodEntryRepo;
import com.hive.calorieTracker.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.hateoas.CollectionModel;

import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
@RestController
public class UserController {

    @Autowired
    private FoodEntryRepo foodRepo;
    @Autowired
    private UserRepo userRepo;

    private UserModelAssembler assembler;
    UserController(UserRepo userRepo,
                   FoodEntryRepo foodRepo, UserModelAssembler userModelAssembler){
        this.foodRepo = foodRepo;
        this.userRepo = userRepo;
        this.assembler = userModelAssembler;
    }

    @GetMapping("/getUser/{id}")
    EntityModel<User> one(@PathVariable Long id) {

        User user = userRepo.findById(id)
                .orElseThrow(() -> new FoodEntryNotFoundException(id));
        return assembler.toModel(user);
    }
    @GetMapping("/getUser/all")
    CollectionModel<EntityModel<User>> all() {
        List<EntityModel<User>> users = userRepo.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return new CollectionModel<>(users, linkTo(methodOn(UserController.class).all()).withSelfRel());
    }
    @PostMapping("/upload/user")
    ResponseEntity<?> addUser(@RequestBody User user) throws URISyntaxException {

        return ResponseEntity.created()
        userRepo.save(user);
        return new EntityModel<>(user,
                linkTo(methodOn(UserController.class).one()))
    }
}
