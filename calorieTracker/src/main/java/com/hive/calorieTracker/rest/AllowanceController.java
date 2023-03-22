package com.hive.calorieTracker.rest;

import org.springframework.expression.ExpressionException;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
class AllowanceController {
    @Inject
    AllowanceRepository allowanceRepository;
    @Inject
    AllowanceModelAssembler assembler;

    AllowanceController(AllowanceRepository allowanceRepository, AllowanceModelAssembler assembler) {
        this.allowanceRepository = allowanceRepository;
        this.assembler = assembler;
    }

    @GetMapping("/allowance/{id}")
    EntityModel<Allowance> one(@PathVariable Long id) {
        Allowance allowance = allowanceRepository.findById(id)
                .orElseThrow(); //TODO: Fix orElse
        return assembler.toModel(allowance);
    }

    @PostMapping("/allowance")
    ResponseEntity<EntityModel<Allowance>> newOrder(@RequestBody Allowance allowance) {

        allowance.setStatus(Status.LOSE_WEIGHT);
        Allowance newAllowance = allowanceRepository.save(allowance);

        return ResponseEntity
                .created(linkTo(methodOn(AllowanceController.class).one(newAllowance.getId())).toUri())
                .body(assembler.toModel(newAllowance));
    }
}
