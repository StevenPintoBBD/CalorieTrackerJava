package com.hive.calorieTracker.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;
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
    private Integer calories;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate entryDate;
}
