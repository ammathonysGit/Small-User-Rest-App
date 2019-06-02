package com.rest.demo.repository;

import com.rest.demo.entity.Car;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CarRepository extends CrudRepository<Car, String> {

    List<Car> findByBrand(String brand);
    List<Car> findByBrandAndModelAndColourAndHorsePower(String brand, String model, String colour, Integer horsePower);
    List<Car> findByBrandAndModel(String brand, String model);
}
