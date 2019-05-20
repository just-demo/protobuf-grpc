package self.ed.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import self.ed.proto.DemoRequest;
import self.ed.proto.DemoResponse;
import self.ed.proto.DemoServiceGrpc;
import self.ed.proto.DemoServiceGrpc.DemoServiceBlockingStub;

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
        System.out.println(response.getText());
        channel.shutdown().awaitTermination(5, SECONDS);
    }
}
