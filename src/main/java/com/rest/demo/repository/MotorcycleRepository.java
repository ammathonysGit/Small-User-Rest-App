package com.rest.demo.repository;

import com.rest.demo.entity.Motorcycle;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MotorcycleRepository extends CrudRepository<Motorcycle, String> {
}
