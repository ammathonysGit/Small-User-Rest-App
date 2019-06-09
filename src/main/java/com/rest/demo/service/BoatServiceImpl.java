package com.rest.demo.service;

import com.rest.demo.entity.Boat;
import com.rest.demo.entity.Engine;
import com.rest.demo.exception.EmptyCollectionException;
import com.rest.demo.exception.NoSuchEntityFound;
import com.sun.org.apache.bcel.internal.generic.INSTANCEOF;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class BoatServiceImpl implements BoatService {

    private RedisTemplate redisTemplate;
    private HashOperations hashOperations;

    public BoatServiceImpl(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
    }


    @Override
    public void deleteBoatById(String id) {
        hashOperations.delete("boats", id);
    }

    @Override
    public void updateBoat(Boat boatToUpdate, Boat receivedBoat) {
        boatToUpdate.setBrand(receivedBoat.getBrand());
        boatToUpdate.setEngine(receivedBoat.getEngine());
        boatToUpdate.setModel(receivedBoat.getModel());
        boatToUpdate.setType(receivedBoat.getType());

        hashOperations.put("boats", boatToUpdate.getId(), boatToUpdate);
    }

    @Override
    public void saveBoat(Boat boatToSave) {
        hashOperations.put("boats", boatToSave.getId(), boatToSave);
    }

    @Override
    public Boat getBoatById(String id) {
        Boat boat = (Boat) hashOperations.get("boats", id);

        if (boat == null) {
            throw new NoSuchEntityFound("No Such Entity found of type Boat.", new Throwable("There are no available Boats."));
        }
        return boat;
    }

    @Override
    public Map<String, Boat> getAllBoats() {
        Map<String, Boat> boatMap = hashOperations.entries("boats");

        if (boatMap.isEmpty() || boatMap == null) {
            throw new NoSuchEntityFound("No Entities found of type Boat.", new Throwable("There are no Entities of type Boat currently stored(method call getAllBoats()"));
        }
        return boatMap;
    }

    @Override
    public Map<String, Boat> getAllBoatsByBrand(String brand) {
        Map<String, Boat> boatMap =hashOperations.entries("boats");

        if (boatMap.isEmpty() || boatMap == null) {
            throw new NoSuchEntityFound("No Entities found of type Boat.", new Throwable("There are no Entities of type Boat currently stored(method call getAllBoatsByBrand()"));
        }

        for (Map.Entry<String, Boat> entry:
             boatMap.entrySet()) {
            if (!entry.getValue().getBrand().equals(brand)) {
                boatMap.remove(entry.getKey(), entry.getValue());
            }
        }

        if (boatMap.isEmpty()) {
            throw new EmptyCollectionException("Empty Collection ", new Throwable("Empty Map<String, Boat> after filtering by Brand"));
        }

        return boatMap;
    }

    @Override
    public Map<String, Boat> filterBoatsByCriteria(String brand, String model, Engine engine, String type) {
        Map<String, Boat> boatMap = hashOperations.entries("boats");

        if (boatMap.isEmpty() || boatMap == null) {
            throw new NoSuchEntityFound("No Entities found of type Boat.", new Throwable("There are no Entities of type Boat currently stored(method call getAllBoatsByCriteria()"));
        }

        for (Map.Entry<String, Boat> entry:
             boatMap.entrySet()) {
            if (!entry.getValue().getBrand().equals(brand) &&
                    entry.getValue().getEngine().equals(engine) &&
                    entry.getValue().getModel().equals(model) &&
                    entry.getValue().getType().equals(type)) {
                boatMap.remove(entry.getKey(), entry.getValue());
            }
        }

        if (boatMap.isEmpty()) {
            throw new EmptyCollectionException("Empty Collection", new Throwable("Empty Map<String, Boat> after filtering by all of Boat Entity fields"));
        }

        return boatMap;
    }
}
