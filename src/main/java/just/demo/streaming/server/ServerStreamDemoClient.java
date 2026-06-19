package just.demo.streaming.server;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import just.demo.proto.DemoRequest;
import just.demo.proto.DemoResponse;
import just.demo.proto.ServerStreamDemoServiceGrpc;
import just.demo.proto.ServerStreamDemoServiceGrpc.ServerStreamDemoServiceStub;

import java.util.concurrent.CountDownLatch;

import static java.util.concurrent.TimeUnit.SECONDS;
import static just.demo.util.LogUtils.log;

public class ServerStreamDemoClient {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch done = new CountDownLatch(1);
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 8080)
                .usePlaintext()
                .build();
        ServerStreamDemoServiceStub stub = ServerStreamDemoServiceGrpc.newStub(channel);
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

        DemoRequest request = DemoRequest.newBuilder()
                .setText("Demo request")
                .build();

        stub.demo(request, responseObserver);
        done.await();
        channel.shutdown();
        channel.awaitTermination(5, SECONDS);
    }
}
