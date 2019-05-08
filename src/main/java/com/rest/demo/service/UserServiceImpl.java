package com.rest.demo.service;

import com.rest.demo.entity.User;
import com.rest.demo.exception.UserNotFoundException;
import com.rest.demo.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Repository
public class UserServiceImpl implements UserService {
//    //RedisTemplate is interaction/connection with the Redis server
    private RedisTemplate<String, User> redisTemplate;
//    //HashOperations with this object we can operate with the Redis server.
    private HashOperations operations;

    @Autowired
    public UserServiceImpl(RedisTemplate<String, User> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.operations = redisTemplate.opsForHash();
    }

//    private UsersRepository usersRepository;
//
//    @Autowired
//    public UserServiceImpl(UsersRepository usersRepository) {
//        this.usersRepository = usersRepository;
//    }


    @Override
    public User findUserById(String id) throws UserNotFoundException {
        try {
            return (User) operations.get("USER", String.valueOf(id));
//            return usersRepository.findById(id).get();
        } catch (Exception e) {
            throw new UserNotFoundException();
        }

    }

    @Override
    public Map<String, User> getAllUsers() {
        return operations.entries("USER");
    }

    @Override
    public User findUserByName(String name) throws UserNotFoundException {
     Map<String, User> userMap = operations.entries("USER");
     User user = null;
     for (Map.Entry<String, User> map: userMap.entrySet()) {
         if (map.getValue().getName().equals(name)) {
            user = map.getValue();
            break;
         }
     }

     if (user == null) {
         throw new UserNotFoundException();
     }
        return user;

//        return (User) operations.get("USER", name);
//        return usersRepository.findByName(name);
    }

    @Override
    public void saveUser(User userToSave) {
         operations.put("USER", userToSave.getId(), userToSave);
//        usersRepository.save(userToSave);
    }

    @Override
    public User updateUser(User storedUser ,@Valid User userToBeUpdated) {
        storedUser.setName(userToBeUpdated.getName());
        storedUser.setSurname(userToBeUpdated.getSurname());
        storedUser.setEmail(userToBeUpdated.getEmail());
//        return usersRepository.save(storedUser);
        operations.put("USER", storedUser.getId(), storedUser);
        return (User) operations.get("USER", storedUser.getId());
    }

    @Override
    public void deleteUserById(Integer id) throws UserNotFoundException {
        try {
            operations.delete("USER", String.valueOf(id));
//            usersRepository.delete(usersRepository.findById(String.valueOf(id)).get());
        } catch (Exception e) {
            throw new UserNotFoundException();
        }
    }
}
