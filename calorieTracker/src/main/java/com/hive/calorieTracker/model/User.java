package com.hive.calorieTracker.model;

import com.hive.calorieTracker.constants.Status;
import com.hive.calorieTracker.constants.Unit;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

@Data
@Entity
@Table(name="Users")
public class User extends RepresentationModel<User> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;

    private String firstName;

    private String lastName;
    private int weight;

    private Status status;
    private float allowance;

    private float bmr;

    private Unit preferedUnit;

}
