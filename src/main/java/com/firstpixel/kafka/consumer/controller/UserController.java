package com.firstpixel.kafka.consumer.controller;

import java.util.List;

//UserController.java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.firstpixel.kafka.consumer.model.User;
import com.firstpixel.kafka.consumer.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {

 @Autowired
 private UserService userService;

 @GetMapping("/users")
 public ResponseEntity<List<User>> getAllUsers() {
     return ResponseEntity.ok(userService.getAllUsers());
 }

 @GetMapping("/users/{id}")
 public ResponseEntity<User> getUserById(@PathVariable Long id) {
     User user = userService.getUserById(id);
     return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
 }

 @PostMapping("/users")
 public ResponseEntity<User> createUser(@RequestBody User user) {
     return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(user));
 }

 @PutMapping("/users/{id}")
 public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
     User updatedUser = userService.updateUser(id, userDetails);
     return updatedUser != null ? ResponseEntity.ok(updatedUser) : ResponseEntity.notFound().build();
 }

 @DeleteMapping("/users/{id}")
 public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
     userService.deleteUser(id);
     return ResponseEntity.noContent().build();
 }
}