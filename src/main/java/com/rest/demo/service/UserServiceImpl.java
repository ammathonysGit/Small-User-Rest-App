package com.rest.demo.service;

import com.rest.demo.entity.User;
import com.rest.demo.exception.UserNotFoundException;
import com.rest.demo.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UsersRepository usersRepository;

    @Autowired
    public UserServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }


    @Override
    public User findUserById(Integer id) throws UserNotFoundException {
        try {
            return usersRepository.findById(id).get();
        } catch (Exception e) {
            throw new UserNotFoundException();
        }

    }

    @Override
    public List<User> getAllUsers() {
        return usersRepository.findAll();
    }

    @Override
    public User findUserByName(String name) {
        return usersRepository.findByName(name);
    }

    @Override
    public User saveUser(User userToSave) {
        return usersRepository.save(userToSave);
    }

    @Override
    public User updateUser(User storedUser ,@Valid User userToBeUpdated) {
        storedUser.setName(userToBeUpdated.getName());
        storedUser.setSurname(userToBeUpdated.getSurname());
        storedUser.setEmail(userToBeUpdated.getEmail());
        return usersRepository.save(storedUser);
    }

    @Override
    public void deleteUserById(Integer id) throws UserNotFoundException {
        try {
            usersRepository.delete(usersRepository.findById(id).get());
        } catch (Exception e) {
            throw new UserNotFoundException();
        }
    }
}
