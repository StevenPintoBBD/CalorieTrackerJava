package com.hive.calorieTracker.exceptions;

public class FoodEntryNotFoundException extends RuntimeException{

    public FoodEntryNotFoundException(Long id) {
        super("Could not find entry " + id);
    }
}
