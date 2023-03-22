package com.hive.calorieTracker.rest;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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