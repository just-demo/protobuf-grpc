package just.demo.streaming.bidirectional;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.ClientCallStreamObserver;
import io.grpc.stub.ClientResponseObserver;
import just.demo.proto.BidirectionalStreamDemoServiceGrpc;
import just.demo.proto.BidirectionalStreamDemoServiceGrpc.BidirectionalStreamDemoServiceStub;
import just.demo.proto.DemoRequest;
import just.demo.proto.DemoResponse;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.concurrent.TimeUnit.SECONDS;
import static just.demo.util.LogUtils.log;

public class BidirectionalStreamDemoClient {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch done = new CountDownLatch(1);
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 8080)
                .usePlaintext()
                .build();
        BidirectionalStreamDemoServiceStub stub = BidirectionalStreamDemoServiceGrpc.newStub(channel);

        AtomicInteger counter = new AtomicInteger();
        ClientResponseObserver<DemoRequest, DemoResponse> responseObserver = new ClientResponseObserver<>() {
            private ClientCallStreamObserver<DemoRequest> requestStream;

            @Override
            public void beforeStart(ClientCallStreamObserver<DemoRequest> requestStream) {
                this.requestStream = requestStream;
                requestStream.setOnReadyHandler(this::next);
            }

            @Override
            public void onNext(DemoResponse response) {
                log("response: " + response.getText());
                next();
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

            private void next() {
                if (counter.incrementAndGet() > 10) {
                    requestStream.onCompleted();
                } else {
                    DemoRequest request = DemoRequest.newBuilder()
                            .setText("Demo request " + counter.get())
                            .build();
                    requestStream.onNext(request);
                }
            }
        };

        stub.demo(responseObserver);
        done.await();
        channel.shutdown();
        channel.awaitTermination(5, SECONDS);
    }
}
