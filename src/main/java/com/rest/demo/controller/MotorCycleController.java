package com.rest.demo.controller;

import com.rest.demo.entity.Motorcycle;
import com.rest.demo.exception.CustomMessage;
import com.rest.demo.service.MotorcycleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.core.MediaType;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping(path = "/motorcycles")
public class MotorCycleController {

    private MotorcycleService motorcycleService;

    @Autowired
    public MotorCycleController(MotorcycleService motorcycleService) {
        this.motorcycleService = motorcycleService;
    }

    @GetMapping(path = "/all", consumes = {MediaType.APPLICATION_JSON}, produces = {MediaType.APPLICATION_JSON})
    public ResponseEntity getAllMotorCycles() {
        Map<String, Motorcycle> motorcycleMap = motorcycleService.getAllMotorcycles();

        if (motorcycleMap == null || motorcycleMap.isEmpty()) {
           return new ResponseEntity(new CustomMessage(new Date(), "Sorry no motorcycles are currently stored"), HttpStatus.NOT_FOUND);

        } else {
            return new ResponseEntity(motorcycleMap, HttpStatus.OK);
        }
    }

    @GetMapping(path = "/id/{id}", consumes = {MediaType.APPLICATION_JSON}, produces = {MediaType.APPLICATION_JSON})
    public ResponseEntity getMotorcycleById(@PathVariable String id) {
        Motorcycle motorcycle = motorcycleService.findMotorcycleById(id);

        if (motorcycle != null) {
            return new ResponseEntity(motorcycle, HttpStatus.OK);
        } else {
            return new ResponseEntity(new CustomMessage(new Date(), "Didn't found a motorcycle with id: " + id), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/brand/{brand}", consumes = {MediaType.APPLICATION_JSON}, produces = MediaType.APPLICATION_JSON)
    public ResponseEntity getMotorcyclesWithBrand(@PathVariable String brand) {
        Map<String, Motorcycle> motorcycleMap = motorcycleService.getAllMotorcyclesWithBrand(brand);

        if (motorcycleMap == null || motorcycleMap.isEmpty()) {
            return new ResponseEntity(new CustomMessage(new Date(), "Didn't find any Motorcycles with the brand: " + brand), HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity(motorcycleMap, HttpStatus.OK);
        }
    }

    @GetMapping(path = "/search", consumes = {MediaType.APPLICATION_JSON}, produces = {MediaType.APPLICATION_JSON})
    public ResponseEntity filterMotorcycles(@RequestParam (name = "brand", required = true) String brand,
                                            @RequestParam(name = "type", required = true) String type,
                                            @RequestParam(name = "model", required = true) String model,
                                            @RequestParam(name = "colour", required = false) String colour,
                                            @RequestParam(name = "horsePower", required = false) Integer horsePower) {
        Map<String, Motorcycle> motorcycleMap = motorcycleService.filterMotorcyclesBy(brand, type, model, colour, horsePower);
        if (motorcycleMap != null && !motorcycleMap.isEmpty()) {
            return new ResponseEntity(motorcycleMap, HttpStatus.OK);
        } else  {
            return new ResponseEntity(new CustomMessage(new Date(), "Didn't find any motorcycles that fit the criteria you've given"), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(path = "/save", consumes = {MediaType.APPLICATION_JSON}, produces = {MediaType.APPLICATION_JSON})
    public ResponseEntity saveMotorcycle(@Valid @RequestBody Motorcycle motorcycle) {
        motorcycleService.saveMotorcycle(motorcycle);

        return new ResponseEntity(new CustomMessage(new Date(), "Motorcycle saved!"), HttpStatus.OK);
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity deleteMotorcycleById(@PathVariable String id) {
        Motorcycle motorcycle = motorcycleService.findMotorcycleById(id);
        if (motorcycle != null) {
            motorcycleService.deleteMotorcycle(motorcycleService.findMotorcycleById(id));
            return new ResponseEntity(new CustomMessage(new Date(), "Car with Id: " + id + " has been deleted"), HttpStatus.OK);
        } else {
            return new ResponseEntity(new CustomMessage(new Date(), "Didn't find motorcycle with the given id: " + id), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(path = "/update/motorcycle/{id}", consumes = {MediaType.APPLICATION_JSON}, produces = {MediaType.APPLICATION_JSON})
    public ResponseEntity updateMotorcycle(@PathVariable String id, @Valid @RequestBody Motorcycle motorcycle) {
        Motorcycle motorcycleToUpdate = motorcycleService.findMotorcycleById(id);
        if (motorcycleToUpdate != null) {
            motorcycleService.updateMotorcycle(motorcycleToUpdate, motorcycle);
            return new ResponseEntity(new CustomMessage(new Date(), "Updated car with id: " + id), HttpStatus.OK);
        } else {
            return new ResponseEntity(new CustomMessage(new Date(), "Didn't find motorcycle with the give id: " + id), HttpStatus.NOT_FOUND);
        }
    }



}
