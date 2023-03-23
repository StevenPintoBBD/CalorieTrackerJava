package com.hive.calorieTracker.rest;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;


@RestController
class BmrController {

    private final BmrRepository bmrRepository;
    private final BmrModelAssembler assembler;

    BmrController(BmrRepository bmrRepository,
                  BmrModelAssembler assembler) {

        this.bmrRepository = bmrRepository;
        this.assembler = assembler;
    }

    @GetMapping("/bmr")
    CollectionModel<EntityModel<Bmr>> all() {

        List<EntityModel<Bmr>> bmr = bmrRepository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return new CollectionModel<>(bmr,
                linkTo(methodOn(BmrController.class).all()).withSelfRel());
    }

    @GetMapping("/bmr/{id}")
    EntityModel<Bmr> one(@PathVariable Long id) {
        Bmr bmr = bmrRepository.findById(id).orElseThrow();

        return assembler.toModel(bmr);
    }

    @PostMapping("/bmr")
    ResponseEntity<EntityModel<Bmr>> newBmr(@RequestBody Bmr bmr) {

        Bmr newBmr = bmrRepository.save(bmr);

        return ResponseEntity
                .created(linkTo(methodOn(BmrController.class).one(newBmr.getId())).toUri())
                .body(assembler.toModel(newBmr));
    }
}
