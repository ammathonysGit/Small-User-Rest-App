package com.rest.demo.controller;

import com.rest.demo.entity.Truck;
import com.rest.demo.exception.CustomMessage;
import com.rest.demo.service.TruckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.core.MediaType;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/trucks")
public class TruckController {

    private TruckService truckService;


    @Autowired
    public TruckController(TruckService truckService) {
        this.truckService = truckService;
    }


    @GetMapping(path = "/all", consumes = {MediaType.APPLICATION_JSON}, produces = {MediaType.APPLICATION_JSON})
    public ResponseEntity getAllTrucks() {
        Map<String, Truck> truckMap = truckService.getAllTrucks();

        if (truckMap.isEmpty() || truckMap == null) {
            return new ResponseEntity(new CustomMessage(new Date(), "Sorry there are no trucks currently"), HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity(truckMap, HttpStatus.OK);
        }


    }


    @GetMapping(path = "/id/{id}", consumes = {MediaType.APPLICATION_JSON}, produces = {MediaType.APPLICATION_JSON})
    public ResponseEntity getTruckById(@PathVariable String id) {
        Truck truck = truckService.getTruckById(id);

        if (truck != null) {
            return new ResponseEntity(truck, HttpStatus.OK);
        } else {
            return new ResponseEntity(new CustomMessage(new Date(), "Sorry there is no truck available with the id: " + id), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/brand/{brand}", consumes = {MediaType.APPLICATION_JSON}, produces = {MediaType.APPLICATION_JSON})
    public ResponseEntity getTrucksByBrand(@Valid @PathVariable String brand) {
        Map<String, Truck> truckMap = truckService.filterTrucskByBrand(brand);

        if (truckMap.isEmpty() || truckMap == null) {
            return new ResponseEntity(new CustomMessage(new Date(),
                    "Sorry there are no available trucks with brand: " + brand), HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity(truckMap, HttpStatus.OK);
        }
    }

    @GetMapping(path = "/search", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public ResponseEntity filterTrucksBySearchCriteria(@RequestParam(name = "brand", required = true) String brand,
                                                       @RequestParam(name = "model", required = true) String model,
                                                       @RequestParam(name = "horsePower", required = false) Integer horsePower,
                                                       @RequestParam(name = "colour", required = false) String colour) {
        Map<String, Truck> map = truckService.filterTrucksByAllFields(brand, model, horsePower, colour);

        if (map == null || map.isEmpty()) {
            return new ResponseEntity(new CustomMessage(new Date(), "Sorry there are no available trucks"), HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity(map, HttpStatus.OK);
        }
    }

    @PostMapping(path = "/save", consumes = {MediaType.APPLICATION_JSON}, produces = {MediaType.APPLICATION_JSON})
    public ResponseEntity saveTruck(@Valid @RequestBody Truck truckToSave) {
        truckService.saveTruck(truckToSave);
        return new ResponseEntity(new CustomMessage(new Date(), "Succesfully saved truck!"), HttpStatus.OK);
    }

    @DeleteMapping(path = "/delete/{id}", consumes = {MediaType.APPLICATION_JSON}, produces = {MediaType.APPLICATION_JSON})
    public ResponseEntity deleteTruckById(@Valid @PathVariable String id) {
        Truck truck = truckService.getTruckById(id);

        if (truck != null) {
            truckService.deleteTruck(truck);
            return new ResponseEntity(new CustomMessage(new Date(), "Truck deleted succesfully!"), HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity(new CustomMessage(new Date(),
                    "There is no available Truck with id: " + id + " to be deleted"), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(path = "/update/{id}", consumes = {MediaType.APPLICATION_JSON}, produces = {MediaType.APPLICATION_JSON})
    public ResponseEntity updateTruckByIdAndRequestBody(@Valid @PathVariable String id, @Valid @RequestBody Truck truck) {
        Truck truckToBeUpdated = truckService.getTruckById(id);

        if (truckToBeUpdated != null) {
            truckService.updateTruck(truckToBeUpdated, truck);
            return new ResponseEntity(new CustomMessage(new Date(),
                    "Succesfully updated Truck with id:" + id), HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity(new CustomMessage(new Date(),
                    "Sorry there is no Truck available with the give id: " + id), HttpStatus.NOT_FOUND);
        }
    }




}











