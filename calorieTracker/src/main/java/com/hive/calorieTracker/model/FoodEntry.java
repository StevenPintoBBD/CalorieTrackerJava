package com.hive.calorieTracker.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import java.util.Date;

@Data
@Entity
@Table(name="Food")
public class FoodEntry extends RepresentationModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JoinColumn(name= "fk_user_id")
    private Long userid;

    private String foodName;
    private int calories;
    private Date entryDate;
}
