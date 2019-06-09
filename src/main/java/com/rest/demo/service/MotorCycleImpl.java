package com.rest.demo.service;


import com.rest.demo.entity.Motorcycle;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MotorCycleImpl implements MotorcycleService {

    private RedisTemplate redisTemplate;
    private HashOperations hashOperations;

    @Autowired
    public MotorCycleImpl(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
    }


    @Override
    public Map<String, Motorcycle> getAllMotorcycles() {
        return hashOperations.entries("motorcycles");
    }

    @Override
    public void deleteMotorcycle(Motorcycle motorcycleToDelete) {
        hashOperations.delete("motorcycles", motorcycleToDelete.getId());
    }

    @Override
    public void saveMotorcycle(Motorcycle motorcycleToSave) {
        hashOperations.put("motorcycles", motorcycleToSave.getId(), motorcycleToSave);
    }

    @Override
    public Motorcycle findMotorcycleById(String id) {
        return (Motorcycle) hashOperations.get("motorcycles", id);
    }

    @Override
    public void updateMotorcycle(Motorcycle motorcycleToUpdate, Motorcycle motorcycleToGetInfoFrom) {
        motorcycleToUpdate.setBrand(motorcycleToGetInfoFrom.getBrand());
        motorcycleToUpdate.setColour(motorcycleToGetInfoFrom.getColour());
        motorcycleToUpdate.setHorsePower(motorcycleToGetInfoFrom.getHorsePower());
        motorcycleToUpdate.setType(motorcycleToGetInfoFrom.getType());
        motorcycleToUpdate.setModel(motorcycleToGetInfoFrom.getModel());
        hashOperations.put("motorcycles", motorcycleToUpdate.getId(), motorcycleToUpdate);
    }

    @Override
    public Map filterMotorcyclesBy(String brand, String type, String model, String colour, Integer horsePower) {
        if (StringUtils.isBlank(colour) && horsePower == null || horsePower <= 0) {
            return filterBy(brand, type, model);
        } else {
            return filterByAll(brand, type, model, colour, horsePower);
        }
    }

    @Override
    public Map<String, Motorcycle> getAllMotorcyclesWithBrand(String brand) {
        Map<String, Motorcycle> motorcycles = hashOperations.entries("motorcycles");

        for (Map.Entry<String, Motorcycle> motorcycle:
             motorcycles.entrySet()) {
            if (!motorcycle.getValue().getBrand().equals(brand)) {
                motorcycles.remove(motorcycle.getKey());
            }
        }


        return motorcycles;
    }


    private Map<String, Motorcycle> filterBy(String brand, String type, String model) {
        Map<String, Motorcycle> motorcycleMap = hashOperations.entries("motorcycles");

        for (Map.Entry<String, Motorcycle> entry:
             motorcycleMap.entrySet()) {
            if (!entry.getValue().getBrand().equals(brand) && entry.getValue().getType().equals(type) && entry.getValue().getModel().equals(model)) {
                motorcycleMap.remove(entry.getKey());
            }
        }
        return motorcycleMap;
    }

    private Map<String, Motorcycle> filterByAll(String brand, String type, String model, String colour, Integer horsePower) {
        Map<String, Motorcycle> motorcycleMap = hashOperations.entries("motorcycles");

        for (Map.Entry<String, Motorcycle> entry:
             motorcycleMap.entrySet()) {
            if (!entry.getValue().getBrand().equals(brand) && entry.getValue().getType().equals(type) && entry.getValue().getModel().equals(model) &&
            entry.getValue().getColour().equals(colour) && entry.getValue().getHorsePower().equals(horsePower)) {
                motorcycleMap.remove(entry.getKey());
            }
        }
        return motorcycleMap;
    }
}
