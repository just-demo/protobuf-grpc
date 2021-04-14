package demo.client;

import demo.proto.DemoRequest;
import demo.proto.DemoResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import demo.proto.DemoServiceGrpc;
import demo.proto.DemoServiceGrpc.DemoServiceBlockingStub;

import static java.util.concurrent.TimeUnit.SECONDS;

public class DemoClient {
    public static void main(String[] args) throws Exception {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8080)
                .usePlaintext()
                .build();
        DemoServiceBlockingStub blockingStub = DemoServiceGrpc.newBlockingStub(channel);

        DemoRequest request = DemoRequest.newBuilder()
                .setText("Demo request")
                .build();

        DemoResponse response = blockingStub.demo(request);
        System.out.println(response);
        channel.shutdown().awaitTermination(5, SECONDS);
    }
}
