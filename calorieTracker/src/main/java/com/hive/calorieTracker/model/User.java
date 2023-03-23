package com.hive.calorieTracker.model;

import com.hive.calorieTracker.constants.Unit;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;

    private String firstName;

    private String lastName;
    private int weight;

    private float allowance;

    private float bmr;

    private Unit preferedUnit;

}
