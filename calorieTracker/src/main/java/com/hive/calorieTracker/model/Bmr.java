package com.hive.calorieTracker.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "UserBmr")
public class Bmr {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int bmr;
}