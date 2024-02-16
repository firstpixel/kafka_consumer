package com.firstpixel.kafka.consumer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.firstpixel.kafka.consumer.error.CustomErrorHandler;
import com.firstpixel.kafka.consumer.model.User;

@Component
public class KafkaConsumerService {
	
	@Autowired
	UserService userService;
	

//	CustomErrorHandler customErrorHandler;


    @KafkaListener(
            topics = "my-topic",
            concurrency = "3", // Number of consumer threads
            groupId = "my-group-id",
            containerFactory = "kafkaListenerContainerFactory"
            //errorHandler = "customErrorHandler"
    )
    public void receiveMessages(String userJson, Acknowledgment acknowledgment) throws JsonMappingException, JsonProcessingException {
        // Process the message
        System.out.println("Received message: " + userJson);
        
       
	        ObjectMapper objectMapper = new ObjectMapper();
	        User user = objectMapper.readValue(userJson, User.class);
	        System.out.println(user);
	        saveToDatabase(user);
	
	        // Acknowledge message manually
	        acknowledgment.acknowledge();
        
       
    }
    
    private void saveToDatabase(User user) {
    	userService.createUser(user);
    	
    }
    
   

	
}


