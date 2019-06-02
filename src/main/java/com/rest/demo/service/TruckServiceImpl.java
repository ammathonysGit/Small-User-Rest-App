package com.rest.demo.service;

import com.rest.demo.entity.Truck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.Map;

@Service
public class TruckServiceImpl implements TruckService {

    private RedisTemplate redisTemplate;
    private HashOperations hashOperations;

    @Autowired
    public TruckServiceImpl(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
    }




    @Override
    public Truck getTruckById(String id) {
        return (Truck) hashOperations.get("trucks", id);
    }

    @Override
    public void deleteTruck(Truck truckToDelete) {
        hashOperations.delete("trucks", truckToDelete.getId());
    }

    @Override
    public void updateTruck(Truck truckToUpdate, @Valid Truck updatedTruckFromRequest) {
        truckToUpdate.setBrand(updatedTruckFromRequest.getBrand());
        truckToUpdate.setColour(updatedTruckFromRequest.getColour());
        truckToUpdate.setHorsePower(updatedTruckFromRequest.getHorsePower());
        truckToUpdate.setModel(updatedTruckFromRequest.getModel());

        hashOperations.put("trucks", truckToUpdate.getId(), truckToUpdate);
    }

    @Override
    public void saveTruck(Truck truckToSave) {
        hashOperations.put("trucks", truckToSave.getId(), truckToSave);
    }

    @Override
    public Map<String, Truck>  getAllTrucks() {
        return hashOperations.entries("trucks");
    }

    @Override
    public Map<String, Truck> filterTrucskByBrand(String brand) {
        Map<String, Truck> truckMap = hashOperations.entries("trucks");

        for (Map.Entry<String, Truck> entry:
             truckMap.entrySet()) {
            if (!entry.getValue().getBrand().equals(brand)) {
                truckMap.remove(entry.getKey(), entry.getValue());
            }
        }

        return truckMap;
    }

    @Override
    public Map<String, Truck> filterTrucksByAllFields(String brand, String mode, Integer horsePower, String colour) {
        Map<String, Truck> truckMap = hashOperations.entries("trucks");

        for (Map.Entry<String, Truck> entry:
             truckMap.entrySet()) {
            if (!entry.getValue().getBrand().equals(brand) && entry.getValue().getColour().equals(colour)
            && entry.getValue().getModel().equals(mode) && entry.getValue().getHorsePower().equals(horsePower)) {
                truckMap.remove(entry.getKey(), entry.getValue());
            }
        }
        return truckMap;
    }

}
