package com.hive.calorieTracker.rest;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "UserBmr")
class Bmr {

    private @Id @GeneratedValue Long id;

    private int bmr;
    Bmr() {}

    Bmr(int bmr) {

        this.bmr = bmr;
    }
}