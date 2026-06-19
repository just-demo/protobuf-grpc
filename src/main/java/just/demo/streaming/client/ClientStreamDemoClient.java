package just.demo.streaming.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import just.demo.proto.ClientStreamDemoServiceGrpc;
import just.demo.proto.ClientStreamDemoServiceGrpc.ClientStreamDemoServiceStub;
import just.demo.proto.DemoRequest;
import just.demo.proto.DemoResponse;

import java.util.concurrent.CountDownLatch;

import static java.util.concurrent.TimeUnit.SECONDS;
import static java.util.stream.IntStream.rangeClosed;
import static just.demo.util.LogUtils.log;

public class ClientStreamDemoClient {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch done = new CountDownLatch(1);
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 8080)
                .usePlaintext()
                .build();
        ClientStreamDemoServiceStub stub = ClientStreamDemoServiceGrpc.newStub(channel);

        StreamObserver<DemoResponse> responseObserver = new StreamObserver<>() {

            @Override
            public void onNext(DemoResponse response) {
                log("response: " + response.getText());
            }

            @Override
            public void onError(Throwable t) {
                log("error: " + t.getMessage());
                done.countDown();
            }

            @Override
            public void onCompleted() {
                log("completed");
                done.countDown();
            }
        };

        StreamObserver<DemoRequest> requestObserver = stub.demo(responseObserver);
        rangeClosed(1, 10).forEach(counter -> {
            DemoRequest request = DemoRequest.newBuilder()
                    .setText("Demo request " + counter)
                    .build();
            requestObserver.onNext(request);
        });
        requestObserver.onCompleted();

        done.await();
        channel.shutdown();
        channel.awaitTermination(5, SECONDS);
    }
}
