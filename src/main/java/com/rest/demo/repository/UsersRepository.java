package com.rest.demo.repository;

import com.rest.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository <User, Integer> {

    User findByName(String name);

}
