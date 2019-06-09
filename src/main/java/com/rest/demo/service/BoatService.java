package com.rest.demo.service;

import com.rest.demo.entity.Boat;
import com.rest.demo.entity.Engine;

public interface BoatService {

    void deleteBoatById(String id);
    void updateBoat(Boat boatToUpdate, Boat receivedBoat);
    void saveBoat(Boat boatToSave);
    Boat getBoatById(String id);
    <T> T getAllBoats();
    <T> T getAllBoatsByBrand(String brand);
    <T> T filterBoatsByCriteria(String brand, String model, Engine engine, String type);

 }
