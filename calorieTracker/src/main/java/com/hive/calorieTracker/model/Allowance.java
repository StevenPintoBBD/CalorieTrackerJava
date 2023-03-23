package com.hive.calorieTracker.rest;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "UserAllowance")
class Allowance {
    private @Id @GeneratedValue Long id;

    private int allowance;
    private Status status;

    Allowance() {}

    Allowance(int allowance, Status status) {
        this.allowance = allowance;
        this.status = status;
    }
}