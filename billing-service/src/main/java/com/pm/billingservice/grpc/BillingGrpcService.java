//package com.pm.billingservice.grpc;
//
//import billing.BillingRequest;
//import billing.BillingResponse;
//import billing.BillingServiceGrpc.BillingServiceImplBase;
//import io.grpc.stub.StreamObserver;
//import net.devh.boot.grpc.server.service.GrpcService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//@GrpcService
//public class BillingGrpcService extends BillingServiceImplBase {
//
//    private static final Logger log = LoggerFactory.getLogger(
//            BillingGrpcService.class);
//
//    @Override
//    public void createBillingAccount(BillingRequest billingRequest,
//                                     StreamObserver<BillingResponse> responseObserver) {
//
//        log.info("createBillingAccount request received {}", billingRequest.toString());
//
//        // Business logic - e.g save to database, perform calculates etc
//
//        BillingResponse response = BillingResponse.newBuilder()
//                .setAccountId("12345")
//                .setStatus("ACTIVE")
//                .build();
//
//        responseObserver.onNext(response);
//        responseObserver.onCompleted();
//    }
//}
package com.pm.billingservice.grpc;

// Import generated classes from proto file
import billing.BillingRequest;
import billing.BillingResponse;
import billing.BillingServiceGrpc.BillingServiceImplBase;

// Import gRPC StreamObserver for sending responses
import io.grpc.stub.StreamObserver;

// Annotation to register this class as a gRPC service in Spring Boot
import net.devh.boot.grpc.server.service.GrpcService;

// Logger imports
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GrpcService // Marks this class as a gRPC service that Spring Boot should start automatically
public class BillingGrpcService extends BillingServiceImplBase { // Extend the generated base class to implement server logic

    // Logger to print info in console
    private static final Logger log = LoggerFactory.getLogger(BillingGrpcService.class);

    // Override the gRPC method defined in proto file
    @Override
    public void createBillingAccount(BillingRequest billingRequest,
                                     StreamObserver<BillingResponse> responseObserver) {

        // Log the incoming request for debugging
        log.info("createBillingAccount request received {}", billingRequest.toString());

        // ----- Business logic section -----
        // Here you can save data to DB, perform calculations, call other services, etc.

        // Build a response object to send back to the client
        BillingResponse response = BillingResponse.newBuilder()
                .setAccountId("12345") // Example account ID
                .setStatus("ACTIVE")    // Example status
                .build();

        // Send the response back to the client
        responseObserver.onNext(response);

        // Tell gRPC that response is complete
        responseObserver.onCompleted();
    }
}

