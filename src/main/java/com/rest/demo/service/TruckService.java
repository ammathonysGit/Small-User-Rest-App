package com.rest.demo.service;

import com.rest.demo.entity.Truck;

public interface TruckService {

    Truck getTruckById(String id);
    void deleteTruck(Truck truckToDelete);
    void updateTruck(Truck truckToUpdate, Truck updatedTruckFromRequest);
    void saveTruck(Truck truckToSave);
    <T> T getAllTrucks();
    <T> T filterTrucskByBrand(String brand);
    <T> T filterTrucksByAllFields(String brand, String mode, Integer horsePower, String colour);
 }
