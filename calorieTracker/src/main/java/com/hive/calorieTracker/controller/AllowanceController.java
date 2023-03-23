package com.hive.calorieTracker.rest;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.mediatype.vnderrors.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@RestController
class AllowanceController {
    private final AllowanceRepository allowanceRepository;
    private final AllowanceModelAssembler assembler;

    AllowanceController(AllowanceRepository allowanceRepository, AllowanceModelAssembler assembler) {
        this.allowanceRepository = allowanceRepository;
        this.assembler = assembler;
    }

    @GetMapping("/allowance")
    CollectionModel<EntityModel<Allowance>> all() {

        List<EntityModel<Allowance>> allowance = allowanceRepository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return new CollectionModel<>(allowance,
                linkTo(methodOn(AllowanceController.class).all()).withSelfRel());
    }

    @GetMapping("/allowance/{id}")
    EntityModel<Allowance> one(@PathVariable Long id) {
        Allowance allowance = allowanceRepository.findById(id)
                .orElseThrow(() -> new AllowanceNotFoundException(id));

        return assembler.toModel(allowance);
    }

    @PostMapping("/allowance")
    ResponseEntity<EntityModel<Allowance>> newAllowance(@RequestBody Allowance allowance) {

        allowance.setStatus(Status.LOSE_WEIGHT);
        Allowance newAllowance = allowanceRepository.save(allowance);

        return ResponseEntity
                .created(linkTo(methodOn(AllowanceController.class).one(newAllowance.getId())).toUri())
                .body(assembler.toModel(newAllowance));
    }

    @PutMapping("/allowance/{id}/LOSE_WEIGHT")
    ResponseEntity<RepresentationModel> loseWheight(@PathVariable Long id) {
        Allowance allowance = allowanceRepository.findById(id).orElseThrow();

        if (allowance.getStatus() == (Status.GAIN_WEIGHT)) {
            allowance.setStatus(Status.LOSE_WEIGHT);
            return ResponseEntity.ok(assembler.toModel(allowanceRepository.save(allowance)));
        }

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(new VndErrors.VndError("Method","You can't lose weight if the " + allowance.getStatus() + " status"));
    }
}
