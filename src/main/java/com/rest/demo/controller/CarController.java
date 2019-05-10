package com.rest.demo.controller;

import com.rest.demo.entity.Car;
import com.rest.demo.exception.CustomMessage;
import com.rest.demo.service.CarService;
import com.rest.demo.service.CarServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.core.MediaType;
import java.util.*;

@RestController
@RequestMapping(path = "/cars")
public class CarController {

    private CarService carService;


    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping(path = "/all", consumes = {MediaType.APPLICATION_JSON}, produces = {MediaType.APPLICATION_JSON})
    public ResponseEntity getAllCars() {
        if (carService instanceof CarServiceImpl) {
            Map<String, Car> map = carService.getAllCars();
            if (map.isEmpty() || map == null) {
                return new ResponseEntity(new CustomMessage(new Date(), " Sorry there are no cars available"), HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(map, HttpStatus.OK);
            }
        } else {
            List<Car> carList = carService.getAllCars();

            if (carList.isEmpty() || carList == null) {
                return new ResponseEntity(new CustomMessage(new Date(), " Sorry there are no cars available"), HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(carList, HttpStatus.OK);
            }
        }


    }

    @GetMapping(path = "/search", consumes = {MediaType.APPLICATION_JSON}, produces = {MediaType.APPLICATION_JSON})
    public ResponseEntity filterCars
            (@RequestParam(name = "brand", required = true)  String brand, @RequestParam(name = "model", required = true)  String model,
             @RequestParam(name = "colour", defaultValue = "")  String colour, @RequestParam(name = "horsePower", required = false, defaultValue = "0")  Integer horsePower) {
        if (carService instanceof CarServiceImpl) {
            Map<String, Car> map = carService.filterCarsByBrandModelColourHorsePower(brand, model, colour, horsePower);

            if (map.isEmpty() || map == null) {
                return new ResponseEntity(new CustomMessage(new Date(), " Sorry there are no cars available with brand: "
                        + brand + " model: " + model + " colour: " + colour + " horse power:" + horsePower), HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(map, HttpStatus.OK);
            }
        } else {
            List<Car> cars = carService.filterCarsByBrandModelColourHorsePower(brand, model, colour, horsePower);
            if (cars.isEmpty() || cars == null) {
                return new ResponseEntity(new CustomMessage(new Date(), " Sorry there are no cars available with brand: "
                        + brand + " model: " + model + " colour: " + colour + " horse power:" + horsePower), HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity(cars, HttpStatus.OK);
            }
        }

    }

    @GetMapping(path = "/car/id/{id}", consumes = {MediaType.APPLICATION_JSON}, produces = {MediaType.APPLICATION_JSON})
    public ResponseEntity<Car> getCarById(@PathVariable String id) {
        Car car = carService.findCarById(id);

        if (car != null) {
            return new ResponseEntity<>(car, HttpStatus.OK);
        } else {
            return new ResponseEntity(new CustomMessage(new Date(), "Sorry we could't find a car with id :" + id), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/car/brand/{brand}", consumes = {MediaType.APPLICATION_JSON}, produces = {MediaType.APPLICATION_JSON})
    public ResponseEntity  getAllCarsWithBrand(@PathVariable String brand) {
        if (carService instanceof CarServiceImpl) {
            Map<String, Car> cars = carService.getAllCarsWithBrand(brand);

            if (cars.isEmpty() || cars == null) {
                return new ResponseEntity(new CustomMessage(new Date(), "Sorry there were no cars found with brand: " + brand), HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(cars, HttpStatus.OK);
            }
        } else {
            List<Car> cars = carService.getAllCarsWithBrand(brand);
            if (cars.isEmpty() || cars == null) {
                return new ResponseEntity(new CustomMessage(new Date(), "Sorry there were no cars found with brand: " + brand), HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(cars, HttpStatus.OK);
            }
        }
    }

    @PostMapping(path = "/save", consumes = {MediaType.APPLICATION_JSON}, produces = {MediaType.APPLICATION_JSON})
    public ResponseEntity saveCar(@Valid @RequestBody final Car car) {
        carService.saveCar(car);
        return new ResponseEntity(new CustomMessage(new Date(), "Car saved!"), HttpStatus.ACCEPTED);
    }

    @PutMapping(path = "/update/{id}", consumes = {MediaType.APPLICATION_JSON}, produces = {MediaType.APPLICATION_JSON})
    public ResponseEntity updateCar(@PathVariable String id, @Valid @RequestBody final Car car) {
        carService.updateCar(carService.findCarById(id), car);
        return new ResponseEntity(new CustomMessage(new Date(), "Car updated!"), HttpStatus.OK);
    }

    @DeleteMapping(path = "/delete/{id}", consumes = {MediaType.APPLICATION_JSON}, produces = {MediaType.APPLICATION_JSON})
    public ResponseEntity deleteCar(@PathVariable String id) {
        carService.deleteCar(id);
        return new ResponseEntity(new CustomMessage(new Date(), "Car deleted!"), HttpStatus.OK);
    }



}
