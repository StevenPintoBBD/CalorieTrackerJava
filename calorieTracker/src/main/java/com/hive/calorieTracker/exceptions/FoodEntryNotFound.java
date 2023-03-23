package com.hive.calorieTracker.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class FoodEntryNotFound {

    @ResponseBody
    @ExceptionHandler(FoodEntryNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String foodEntryNotFoundHandler(FoodEntryNotFoundException ex) {
        return ex.getMessage();
    }
}
