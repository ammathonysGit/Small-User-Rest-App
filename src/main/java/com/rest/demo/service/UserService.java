package com.rest.demo.service;

import com.rest.demo.entity.User;
import com.rest.demo.exception.UserNotFoundException;

import java.util.List;

public interface UserService {

    User findUserById(Integer id) throws UserNotFoundException;
    List<User> getAllUsers();
    User findUserByName(String name);
    User saveUser(User userToSave);
    User updateUser(User storedUser, User userToBeSaved);
    void deleteUserById(Integer id) throws UserNotFoundException;
}
