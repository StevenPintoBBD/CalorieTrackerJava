package com.hive.calorieTracker.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "UserAllowance")
public class Allowance {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int calories;
    private Status status;
}