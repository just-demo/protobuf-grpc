package demo.single;

import demo.proto.DemoRequest;
import demo.proto.DemoResponse;
import demo.proto.DemoServiceGrpc;
import demo.proto.DemoServiceGrpc.DemoServiceBlockingStub;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import static demo.util.LogUtils.log;
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
        log(response);
        channel.shutdown().awaitTermination(5, SECONDS);
    }
}
