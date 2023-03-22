package com.hive.calorieTracker.rest;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
class BmrModelAssembler implements RepresentationModelAssembler<Bmr, EntityModel<Bmr>> {
    @Override
    public EntityModel<Bmr> toModel(Bmr bmr) {
        EntityModel<Bmr> bmrModel = new EntityModel<>(bmr,
                linkTo(methodOn(BmrController.class).one(bmr.getId())).withSelfRel(),
                linkTo(methodOn(BmrController.class).all()).withRel("bmr"));

        return bmrModel;
    }
}
