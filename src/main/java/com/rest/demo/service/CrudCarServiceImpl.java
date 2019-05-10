package com.rest.demo.service;

import com.rest.demo.entity.Car;
import com.rest.demo.repository.CarRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Profile("crud")
@RedisHash("cars")
@Service
public class CrudCarServiceImpl implements CarService {

    private CarRepository carRepository;

    @Autowired
    public CrudCarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }


    @Override
    public List<Car> getAllCars() {
        return (List<Car>) carRepository.findAll();
    }

    @Override
    public List<Car> getAllCarsWithBrand(String brand) {
        return carRepository.findByBrand(brand);
    }

    @Override
    public List<Car> filterCarsByBrandModelColourHorsePower(String brand, String model, String colour, Integer horsePower) {
        List<Car> cars = (List<Car>) carRepository.findAll();
        List<Car> carList = new ArrayList<>();
        if (StringUtils.isBlank(colour) || horsePower <= 0) {
            for (Car car:
                cars ) {
                if (brand.equals(car.getBrand()) && model.equals(car.getModel())) {
                    carList.add(car);
                }
            }
        } else {
            for (Car car:
                 cars) {
                if (brand.equals(car.getBrand()) && model.equals(car.getModel()) && colour.equals(car.getColour()) && horsePower.equals(car.getHorsePower())) {
                    carList.add(car);
                }
            }
        }
        return carList;
    }

    @Override
    public void saveCar(Car carToSave) {
        carRepository.save(carToSave);
    }

    @Override
    public void deleteCar(String id) {
        carRepository.delete(carRepository.findById(id).get());
    }

    @Override
    public Car findCarById(String id) {
        return carRepository.findById(id).get();
    }

    @Override
    public void updateCar(Car storedCar, Car carToUpdate) {
        storedCar.setModel(carToUpdate.getModel());
        storedCar.setBrand(carToUpdate.getBrand());
        storedCar.setColour(carToUpdate.getColour());
        storedCar.setHorsePower(carToUpdate.getHorsePower());
        carRepository.save(storedCar);
    }
}
