package com.firstpixel.kafka.consumer.service;

import java.util.List;

//UserService.java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.firstpixel.kafka.consumer.model.User;
import com.firstpixel.kafka.consumer.repository.UserRepository;

@Service
public class UserService {

 @Autowired
 private UserRepository userRepository;

 public List<User> getAllUsers() {
     return userRepository.findAll();
 }

 public User getUserById(Long id) {
     return userRepository.findById(id).orElse(null);
 }

 public User createUser(User user) {
     return userRepository.save(user);
 }

 public User updateUser(Long id, User userDetails) {
     User user = userRepository.findById(id).orElse(null);
     if (user != null) {
    	 user.setFirstName(userDetails.getFirstName());
    	 user.setLastName(userDetails.getLastName());
         user.setEmail(userDetails.getEmail());
         return userRepository.save(user);
     }
     return null;
 }

 public void deleteUser(Long id) {
     userRepository.deleteById(id);
 }
}