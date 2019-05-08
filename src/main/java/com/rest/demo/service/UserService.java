package com.rest.demo.service;

import com.rest.demo.entity.User;
import com.rest.demo.exception.UserNotFoundException;

import java.util.List;
import java.util.Map;

public interface UserService {

    User findUserById(String id) throws UserNotFoundException;
    Map<String, User> getAllUsers();
//    List<User> getAllUsers();
    User findUserByName(String name) throws UserNotFoundException;
    void saveUser(User userToSave);
    User updateUser(User storedUser, User userToBeSaved);
    void deleteUserById(Integer id) throws UserNotFoundException;
}
