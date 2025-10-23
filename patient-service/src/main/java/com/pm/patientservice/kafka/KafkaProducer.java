package com.pm.patientservice.kafka; // Package where this Kafka producer class belongs

import com.pm.patientservice.model.Patient; // Import Patient model to access patient data
import org.slf4j.Logger; // Import Logger interface for logging info/errors
import org.slf4j.LoggerFactory; // Import LoggerFactory to create logger instance
import org.springframework.kafka.core.KafkaTemplate; // Import KafkaTemplate to send messages to Kafka
import org.springframework.stereotype.Service; // Import Service annotation to make this a Spring-managed bean
import patient.events.PatientEvent; // Import generated Protobuf class for PatientEvent

@Service // Marks this class as a Spring Service so it can be injected where needed
public class KafkaProducer {

    // Create a Logger instance specific to this class to log info and errors
    private static final Logger log = LoggerFactory.getLogger(KafkaProducer.class);

    // KafkaTemplate is a Spring helper class for sending messages to Kafka
    // <String, byte[]> means the message key is a String and the value is a byte array
    private final KafkaTemplate<String, byte[]> kafkaTemplate;

    // Constructor injection: Spring injects KafkaTemplate bean into this class
    public KafkaProducer(KafkaTemplate<String, byte[]> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate; // Assign the injected KafkaTemplate to local field
    }

    // Method to send patient data as a Kafka event
    public void sendEvent(Patient patient) {
        // Build a PatientEvent Protobuf object from the patient model
        PatientEvent event = PatientEvent.newBuilder()
                .setPatientId(patient.getId().toString()) // Set patient ID as string
                .setName(patient.getName()) // Set patient name
                .setEmail(patient.getEmail()) // Set patient email
                .setEventType("PATIENT_CREATED") // Set event type, e.g., created
                .build(); // Build the final Protobuf object

        try {
            // Send the event to Kafka topic named "patient"
            // Convert the Protobuf object to byte array before sending
            kafkaTemplate.send("patient", event.toByteArray());

            // Log success message with the event details
            log.info("Successfully sent PatientCreated event: {}", event);
        } catch (Exception e) {
            // If sending fails, log an error with the exception
            log.error("Error sending PatientCreated event: {}", e);
        }
    }
}
