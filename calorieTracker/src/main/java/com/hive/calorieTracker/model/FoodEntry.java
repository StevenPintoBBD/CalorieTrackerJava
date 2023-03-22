package com.hive.calorieTracker.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name="Food")
public class FoodEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String foodName;
    private int calories;
    private Date entryDate;
}
