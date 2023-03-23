package com.hive.calorieTracker.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hive.calorieTracker.model.FoodEntry;
import com.hive.calorieTracker.repository.FoodEntryRepo;
import jakarta.ws.rs.QueryParam;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class CaloriesPerDayController {

    @Autowired
    private FoodEntryRepo foodEntryRepo;

    @GetMapping("/getDayCalories/{id}/total")
    public ResponseEntity<Object> getDayCalories(@PathVariable Long id, @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) throws JSONException {

        List<FoodEntry> foodEntries = foodEntryRepo.findAll().stream().
                filter(foodEntry -> foodEntry.getUserid() == id).
                filter(foodEntry -> foodEntry.getEntryDate().equals(date)).
                map(foodEntry -> {
                    foodEntry.add(linkTo(methodOn(FoodEntryController.class).getFoodEntryById(foodEntry.getId())).withSelfRel());
                    foodEntry.add(linkTo(methodOn(FoodEntryController.class).getAllEntries()).withRel("foodEntries"));
                    return foodEntry;
                })
                .collect(Collectors.toList());

        if (foodEntries.isEmpty()){
            return ResponseHandler.parseResponse("No entries for this date or user", HttpStatus.NOT_FOUND,null);
        }

        int totalCalories = foodEntries.stream().map(entry -> entry.getCalories()).reduce(0,(sub,value) -> sub + value);
        Map<String, Object> map = new HashMap<String,Object>();
        map.put("entries", foodEntries);
        map.put("totalCalories", totalCalories);

        return ResponseHandler.parseResponse("Successfully obtained entries total cal: " + totalCalories, HttpStatus.OK, map);
    }
}
