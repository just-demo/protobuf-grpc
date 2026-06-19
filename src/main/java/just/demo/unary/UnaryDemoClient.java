package just.demo.unary;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import just.demo.proto.DemoRequest;
import just.demo.proto.DemoResponse;
import just.demo.proto.UnaryDemoServiceGrpc;
import just.demo.proto.UnaryDemoServiceGrpc.UnaryDemoServiceBlockingStub;

import static java.util.concurrent.TimeUnit.SECONDS;
import static just.demo.util.LogUtils.log;

public class UnaryDemoClient {
    public static void main(String[] args) throws Exception {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8080)
                .usePlaintext()
                .build();
        UnaryDemoServiceBlockingStub blockingStub = UnaryDemoServiceGrpc.newBlockingStub(channel);

        DemoRequest request = DemoRequest.newBuilder()
                .setText("Demo request")
                .build();

        DemoResponse response = blockingStub.demo(request);
        log("response: " + response.getText());
        channel.shutdown().awaitTermination(5, SECONDS);
    }
}
