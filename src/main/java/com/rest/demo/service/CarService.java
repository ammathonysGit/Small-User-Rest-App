package com.rest.demo.service;

import com.rest.demo.entity.Car;

import java.util.Map;

public interface CarService {

    Map<String, Car> getAllCars();
    Map<String, Car> getAllCarsWithBrand(String brand);
    Map<String, Car> filterCarsByBrandModelColourHorsePower(String brand, String model, String colour, Integer horsePower);
    void saveCar(Car carToSave);
    void deleteCar(String id);
    Car findCarById(String id);
    void updateCar(Car storedCar, Car carToUpdate);

}
