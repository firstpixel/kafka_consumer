package com.firstpixel.kafka.consumer.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;

import com.firstpixel.kafka.consumer.error.CustomErrorHandler;

@Configuration
@EnableKafka
public class KafkaConfig {
	
	@Autowired
	CustomErrorHandler customErrorHandler;

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory(ConsumerFactory<String, String> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE); // Set acknowledgment mode to MANUAL
        factory.getContainerProperties().setSyncCommits(false);
        factory.getContainerProperties().setAsyncAcks(true);
        
        factory.setCommonErrorHandler(customErrorHandler);
        
        
        return factory;
    }
}

