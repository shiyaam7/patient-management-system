//package com.pm.patientservice.grpc;
//
//import billing.BillingRequest;
//import billing.BillingResponse;
//import billing.BillingServiceGrpc;
//import io.grpc.ManagedChannel;
//import io.grpc.ManagedChannelBuilder;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//@Service
//public class BillingServiceGrpcClient {
//
//    private static final Logger log = LoggerFactory.getLogger(
//            BillingServiceGrpcClient.class);
//    private final BillingServiceGrpc.BillingServiceBlockingStub blockingStub;
//
//    public BillingServiceGrpcClient(
//            @Value("${billing.service.address:localhost}") String serverAddress,
//            @Value("${billing.service.grpc.port:9001}") int serverPort) {
//
//        log.info("Connecting to Billing Service GRPC service at {}:{}",
//                serverAddress, serverPort);
//
//        ManagedChannel channel = ManagedChannelBuilder.forAddress(serverAddress,
//                serverPort).usePlaintext().build();
//
//        blockingStub = BillingServiceGrpc.newBlockingStub(channel);
//    }
//
//    public BillingResponse createBillingAccount(String patientId, String name,
//                                                String email) {
//
//        BillingRequest request = BillingRequest.newBuilder().setPatientId(patientId)
//                .setName(name).setEmail(email).build();
//
//        BillingResponse response = blockingStub.createBillingAccount(request);
//        log.info("Received response from billing service via GRPC: {}", response);
//        return response;
//    }
//}

package com.pm.patientservice.grpc;

// Import generated classes from proto file
import billing.BillingRequest;
import billing.BillingResponse;
import billing.BillingServiceGrpc;

// Import gRPC channel classes
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

// Logger imports
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Spring imports for dependency injection and service annotation
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service // Marks this class as a Spring service that can be injected into other components
public class BillingServiceGrpcClient {

    // Logger to print info in console
    private static final Logger log = LoggerFactory.getLogger(BillingServiceGrpcClient.class);

    // This stub is used to make gRPC calls to Billing Service
    private final BillingServiceGrpc.BillingServiceBlockingStub blockingStub;

    // Constructor to set up gRPC connection
    public BillingServiceGrpcClient(
            @Value("${billing.service.address:localhost}") String serverAddress, // Read Billing Service host from config else take localhost as default
            @Value("${billing.service.grpc.port:9001}") int serverPort) {        // Read Billing Service gRPC port from config

        // Log that we are connecting to Billing Service
        log.info("Connecting to Billing Service GRPC service at {}:{}", serverAddress, serverPort);

        // Create a gRPC channel to connect to the Billing Service
        ManagedChannel channel = ManagedChannelBuilder.forAddress(serverAddress,
                        serverPort)
                .usePlaintext() // Disable SSL/TLS (plain text communication)
                .build();

        // Create a blocking stub (synchronous client) using the channel
        blockingStub = BillingServiceGrpc.newBlockingStub(channel);
    }

    // Method to call the Billing Service's createBillingAccount gRPC method
    public BillingResponse createBillingAccount(String patientId, String name,
                                                String email) {

        // Build a request object using the generated BillingRequest class
        BillingRequest request = BillingRequest.newBuilder()
                .setPatientId(patientId)
                .setName(name)
                .setEmail(email)
                .build();

        // Make the gRPC call using the blocking stub and get the response
        BillingResponse response = blockingStub.createBillingAccount(request);

        // Log the response received from the Billing Service
        log.info("Received response from billing service via GRPC: {}", response);

        // Return the response back to the caller
        return response;
    }
}
