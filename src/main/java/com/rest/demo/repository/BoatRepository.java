package com.rest.demo.repository;

import com.rest.demo.entity.Boat;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface BoatRepository extends CrudRepository<Boat, String> {

    Optional<Boat> findById(String id);
    List<Boat> findBoatsByBrand(String brand);
    List<Boat> findAllByBrandAndModelAndType(String brand, String model, String type);


}
