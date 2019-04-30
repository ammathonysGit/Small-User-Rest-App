package com.rest.demo.controller;

import com.rest.demo.entity.User;
import com.rest.demo.exception.CustomMessage;
import com.rest.demo.exception.UserNotFoundException;
import com.rest.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.core.MediaType;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {


    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    //  /users/all catch this GET request and return a list of all Users.
    @GetMapping(path = "/all" , consumes = {MediaType.APPLICATION_JSON}, produces = {MediaType.APPLICATION_JSON})
    public ResponseEntity getAllUsers() {
         List<User>  users =userService.getAllUsers();

          if (users.isEmpty()){
            return new ResponseEntity(new CustomMessage(new Date(), "No users are currently stored."), HttpStatus.NOT_FOUND);
        } else if (users != null) {
             return new ResponseEntity<>(users, HttpStatus.OK);
         } else {
              return new ResponseEntity<>( HttpStatus.NOT_FOUND);
          }

    }

    // /users/{name} catch GET request and by the PathVariable in the brackets {name} return that user with the give name.
    @GetMapping(path = "/{name}" , produces = {MediaType.APPLICATION_JSON})
    public ResponseEntity<User> getUserByName(@PathVariable final String name) {
        User user = userService.findUserByName(name);

        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity(new CustomMessage(new Date(), "No such user with the give name: " + name + " found."), HttpStatus.NOT_FOUND);
        }

    }


    @PostMapping(path =  "/save" , consumes = {MediaType.APPLICATION_JSON}, produces = {MediaType.APPLICATION_JSON})
    public ResponseEntity createUser(@Valid @RequestBody final User user) {
          userService.saveUser(user);
          if (userService.findUserByName(user.getName()) != null) {
              return new ResponseEntity(new CustomMessage(new Date(), "User created successfully!"), HttpStatus.OK);
          } else {
              return new ResponseEntity(new CustomMessage(new Date(), "Sorry there was a problem creating the user."), HttpStatus.CONFLICT);
          }
    }


    @PutMapping(path =  "/{id}", consumes = {MediaType.APPLICATION_JSON}, produces = {MediaType.APPLICATION_JSON})
    public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody final User user) {
        try {
            userService.findUserById(id);
            User userToBeStored = userService.updateUser(userService.findUserById(id), user);
            userService.saveUser(userToBeStored);
            return new ResponseEntity<User>(userToBeStored, HttpStatus.OK);
        }catch (UserNotFoundException e) {
            return new ResponseEntity(new CustomMessage(new Date(), "Sorry couldn't find user with id: " + id), HttpStatus.CONFLICT);
        }

    }


    @DeleteMapping(path = "/delete/{id}", produces = {MediaType.APPLICATION_JSON})
    public ResponseEntity deleteUser(@PathVariable Integer id) {
        try {
            userService.deleteUserById(id);
            return new ResponseEntity(new CustomMessage(new Date(), "Successfully deleted user"), HttpStatus.OK);
        }catch (UserNotFoundException e) {
            return new ResponseEntity(new CustomMessage(new Date(), "Sorry couldn't find user with id: " + id), HttpStatus.CONFLICT);
        }
    }

    

}
