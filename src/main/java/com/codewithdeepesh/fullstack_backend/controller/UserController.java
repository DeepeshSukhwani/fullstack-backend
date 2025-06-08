package com.codewithdeepesh.fullstack_backend.controller;


import com.codewithdeepesh.fullstack_backend.exception.UserNotFoundException;
import com.codewithdeepesh.fullstack_backend.model.User;
import com.codewithdeepesh.fullstack_backend.reposittory.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("http://localhost:3000/")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/user")
    User newUser(@RequestBody User user){
        return userRepository.save(user);
    }

    @GetMapping("/users")
    List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable Long id) {
   return userRepository.findById(id).orElseThrow(()->new UserNotFoundException("Could nor found user" + id));
    }

    @PutMapping("/user/{id}")
    User updateUser(@RequestBody User newUser,@PathVariable Long id){
        return userRepository.findById(id)
                .map(user -> {
                    user.setUsername(newUser.getUsername());
                    user.setName(newUser.getName());
                    user.setEmail(newUser.getEmail());
                    return userRepository.save(user);
                }).orElseThrow(()->new UserNotFoundException("Could nor found user" + id));
    }
    @DeleteMapping("/user/{id}")
    public String deleteUser(@PathVariable Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Could not find user with id: " + id));

        userRepository.deleteById(id);
        return "User with id " + id + " has been deleted successfully.";
    }


}
