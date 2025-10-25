// Package declaration: defines the namespace for this class
// Helps in organizing the code logically
package com.pm.analyticsservice.kafka;

// Import statement for Protocol Buffers exception handling
// InvalidProtocolBufferException is thrown if the byte array cannot be parsed into a PatientEvent
import com.google.protobuf.InvalidProtocolBufferException;

// SLF4J logger imports for logging events, info, and errors
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Spring Kafka annotation to listen to Kafka topics
import org.springframework.kafka.annotation.KafkaListener;

// Spring stereotype annotation for service layer classes
// Marks this class as a service bean for Spring's component scanning
import org.springframework.stereotype.Service;

// Import generated Protocol Buffers class for PatientEvent
// Used to deserialize the incoming byte array into a strongly-typed object
import patient.events.PatientEvent;

// Marks the class as a Spring service bean
@Service
public class KafkaConsumer {

    // Create a logger instance for this class
    // Used to log info, debug, and error messages
    private static final Logger log = LoggerFactory.getLogger(
            KafkaConsumer.class);

    // Kafka listener method
    // Listens to the "patient" topic in Kafka, with groupId "analytics-service"
    // groupId ensures that multiple consumers can share the load and messages are balanced
    @KafkaListener(topics="patient", groupId = "analytics-service")
    public void consumeEvent(byte[] event) {
        try {
            // Deserialize the incoming Kafka message from bytes to PatientEvent object
            // parseFrom is a method provided by the generated Protocol Buffers class
            PatientEvent patientEvent = PatientEvent.parseFrom(event);

            // Placeholder comment where any analytics or business logic can be performed
            // For example, updating a database, sending metrics, or triggering other services
            // ... perform any business related to analytics here

            // Log the received patient event for monitoring/debugging
            // Info level used because this is normal operation
            log.info("Received Patient Event: [PatientId={},PatientName={},PatientEmail={}]",
                    patientEvent.getPatientId(), // Logs patient ID
                    patientEvent.getName(),      // Logs patient name
                    patientEvent.getEmail());    // Logs patient email
        } catch (InvalidProtocolBufferException e) {
            // If the byte array cannot be parsed to a PatientEvent, catch the exception
            // Log the error for debugging
            log.error("Error deserializing event {}", e.getMessage());
        }
    }
}
