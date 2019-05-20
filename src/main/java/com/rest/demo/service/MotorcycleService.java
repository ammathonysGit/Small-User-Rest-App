package com.rest.demo.service;

import com.rest.demo.entity.Motorcycle;

public interface MotorcycleService {

    <T> T getAllMotorcycles();
    void deleteMotorcycle(Motorcycle motorcycleToDelete);
    void saveMotorcycle(Motorcycle motorcycleToSave);
    Motorcycle findMotorcycleById(String id);
    void updateMotorcycle(Motorcycle motorcycleToUpdate, Motorcycle motorcycleToGetInfoFrom);
    <T> T getAllMotorcyclesWithBrand(String brand);
    <T> T filterMotorcyclesBy(String brand, String type, String model, String colour, Integer horsePower);


}
