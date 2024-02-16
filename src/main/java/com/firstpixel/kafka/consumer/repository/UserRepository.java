package com.firstpixel.kafka.consumer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.firstpixel.kafka.consumer.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}