package com.rest.demo.service;

import com.rest.demo.entity.Car;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;


@Service
@Profile("redistemp")
public class CarServiceImpl implements CarService {

    private RedisTemplate redisTemplate;
    private HashOperations hashOperations;

    @Autowired
    public CarServiceImpl(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
    }


    @Override
    public Map<String, Car> getAllCars() {
        System.out.println("Getting cars");
        return hashOperations.entries("cars");
    }


    @Override
    public Map<String, Car> getAllCarsWithBrand(String brand) {
        System.out.println("Getting cars with brand");
        Map<String, Car> cars = hashOperations.entries("cars");

        for (Map.Entry<String, Car> entry :
                cars.entrySet()) {
            if (!brand.equals(entry.getValue().getBrand())) {
                cars.remove(entry.getKey());
            }
        }

        return cars;
    }

    @Override
    public Map<String, Car> filterCarsByBrandModelColourHorsePower(String brand, String model, String colour, Integer horsePower) {
        Map<String, Car> cars = hashOperations.entries("cars");
        Map<String, Car> carsToReturn = new HashMap<>();

        if (StringUtils.isBlank(colour) || horsePower <= 0) {
            for (Map.Entry<String, Car> entrys :
                    cars.entrySet()) {
                if (brand.equals(entrys.getValue().getBrand()) && model.equals(entrys.getValue().getModel())) {
                    carsToReturn.put(entrys.getKey(), entrys.getValue());
                }
            }
        } else {

            for (Map.Entry<String, Car> entry :
                    cars.entrySet()) {
                if (brand.equals(entry.getValue().getBrand()) && model.equals(entry.getValue().getModel())
                        && colour.equals(entry.getValue().getColour()) && horsePower.equals(entry.getValue().getHorsePower())) {
                    carsToReturn.put(entry.getKey(), entry.getValue());
                }
            }

        }

        return carsToReturn;
    }

    @Override
    public void saveCar(Car carToSave) {
        hashOperations.put("cars", carToSave.getId(), carToSave);
    }

    @Override
    public void deleteCar(String id) {
        hashOperations.delete("cars", id);
    }

    @Override
    public Car findCarById(String id) {
        return (Car) hashOperations.get("cars", id);
    }

    @Override
    public void updateCar(Car storedCar, @Valid Car carToUpdate) {
        storedCar.setBrand(carToUpdate.getBrand());
        storedCar.setColour(carToUpdate.getColour());
        storedCar.setHorsePower(carToUpdate.getHorsePower());
        storedCar.setModel(carToUpdate.getModel());
        hashOperations.put("cars", storedCar.getId(), storedCar);
    }
}
