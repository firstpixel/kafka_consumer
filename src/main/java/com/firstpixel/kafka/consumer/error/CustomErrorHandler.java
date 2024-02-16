package com.firstpixel.kafka.consumer.error;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.ListenerExecutionFailedException;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaUtils;
import org.springframework.stereotype.Component;




@Component
public class CustomErrorHandler implements CommonErrorHandler {

	
	@Override
	public void handleOtherException(Exception thrownException, Consumer<?, ?> consumer,
			MessageListenerContainer container, boolean batchListener) {

		logger().error(thrownException, "'handleOtherException' is implemented by this handler");
		//save exceptions
		
		
	}
	
	
	
	@Override
	public boolean handleOne(Exception thrownException, ConsumerRecord<?, ?> record, Consumer<?, ?> consumer,
			MessageListenerContainer container) {

		logger().error(thrownException, () -> "'handleOne' is implemented by this handler for "
			+ KafkaUtils.format(record) );
		Throwable cause = thrownException.getCause();
        if (cause != null) {
        	logger().error("Error occurred during message processing: " + cause.getMessage());
    		//save to db and akt

        }
		if (container.getContainerProperties().isAsyncAcks()) {
			handleOneManualAcknowledgment(record, consumer);
			
          //save to db and akt
    	}
		return true;
	}
	
	
	@Override
	public void handleRemaining(Exception thrownException, List<ConsumerRecord<?, ?>> records, Consumer<?, ?> consumer, MessageListenerContainer container) {
    	 // Manually acknowledge messages
        if (container.getContainerProperties().isAsyncAcks()) {
            handleManualAcknowledgment(records, consumer);
        
	    	// Custom error handling logic
	        // You can log the exception, perform recovery actions, or handle it as needed
	        if (thrownException instanceof ListenerExecutionFailedException) {
	            // Extract the original cause of the exception
	            Throwable cause = thrownException.getCause();
	            if (cause != null) {
	                System.err.println("Error occurred during message processing: " + cause.getMessage());
	        		//save to db and akt

	            }
	        }
        }
    }
   

	private void handleManualAcknowledgment(List<ConsumerRecord<?, ?>> records, Consumer<?, ?> consumer) {
        for (ConsumerRecord<?, ?> record : records) {
            TopicPartition topicPartition = new TopicPartition(record.topic(), record.partition());
            
            Acknowledgment acknowledgment = retrieveAcknowledgment(record);
            logger().info("Acknowledged!: " + acknowledgment);
    		if (acknowledgment != null) {
            	
                acknowledgment.acknowledge();
            }
            consumer.seek(topicPartition, record.offset() + 1); // Move the offset to the next message
            
        }
    }
	
	private void handleOneManualAcknowledgment(ConsumerRecord<?, ?> record, Consumer<?, ?> consumer) {
        TopicPartition topicPartition = new TopicPartition(record.topic(), record.partition());
        Acknowledgment acknowledgment = retrieveAcknowledgment(record);
        logger().info("Acknowledged!: " + acknowledgment);
		
        if (acknowledgment != null) {
            acknowledgment.acknowledge();
        }
        consumer.seek(topicPartition, record.offset() + 1); // Move the offset to the next message
        
    }
	
	
	private Acknowledgment retrieveAcknowledgment(ConsumerRecord<?, ?> record) {
        Object acknowledgment = record.headers().lastHeader("spring_acknowledgment");
        return acknowledgment != null ? (Acknowledgment) acknowledgment : null;
    }


	
}