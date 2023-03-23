package com.hive.calorieTracker.exceptions;

public class InternalServerErrorException extends RuntimeException{
    InternalServerErrorException(){
        super("We are experiencing technical issues on our side. Please try again later");
    }
}
