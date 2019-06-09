package com.rest.demo.controller;

import com.rest.demo.entity.Boat;
import com.rest.demo.exception.CustomMessage;
import com.rest.demo.exception.EmptyCollectionException;
import com.rest.demo.exception.NoSuchEntityFound;
import com.rest.demo.service.BoatService;
import org.glassfish.jersey.server.wadl.internal.generators.resourcedoc.model.ResponseDocType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.print.attribute.standard.Media;
import javax.validation.Valid;
import javax.ws.rs.core.MediaType;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping(path = "/boats")
public class BoatController {

    private BoatService boatService;

    @Autowired
    public BoatController(BoatService boatService) {
        this.boatService = boatService;
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/all", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public Map<String, Boat> getAllBoats() {
         return boatService.getAllBoats();
        //Method may Throw NoSuchEntityFoundException if there are no boats currently stored. The Exception Handling is happening in the ApplicationExceptionHandler class.
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/id/{id}", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public Boat getBoatById(@PathVariable String id) {
        return boatService.getBoatById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/brand/{brand}", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public Map<String, Boat> getAllBoatsByBrand(@Valid @PathVariable String brand) {
        try {
            return boatService.getAllBoatsByBrand(brand);
        } catch (EmptyCollectionException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There are no Boats currently stored with brand: " +brand, ex);
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(path = "/save", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public ResponseEntity saveBoat(@Valid @RequestBody Boat boat) {
        boatService.saveBoat(boat);
        return new ResponseEntity(new CustomMessage(new Date(),"Boat saved succesfully"), HttpStatus.ACCEPTED);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(path = "/update/{id}", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public ResponseEntity updateBoat(@PathVariable String id, @Valid @RequestBody Boat boat) {
        boatService.updateBoat(boatService.getBoatById(id), boat);
        return new ResponseEntity(new CustomMessage(new Date(),"Boat saved succesfully"), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(path = "/delete/{id}", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public ResponseEntity deleteBoatById(@PathVariable String id) {
        boatService.deleteBoatById(id);
        return new ResponseEntity(new CustomMessage(new Date(),"Boat deleted succesfully"), HttpStatus.ACCEPTED);
    }


}
