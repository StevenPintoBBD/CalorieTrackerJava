package com.hive.calorieTracker.rest;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class AllowanceModelAssembler implements RepresentationModelAssembler<Allowance, EntityModel<Allowance>> {
    @Override
    public EntityModel<Allowance> toModel(Allowance allowance) {

        EntityModel<Allowance> allowanceModel = new EntityModel<>(allowance,
                linkTo(methodOn(AllowanceController.class).one(allowance.getId())).withSelfRel());

        return allowanceModel;
    }
}
