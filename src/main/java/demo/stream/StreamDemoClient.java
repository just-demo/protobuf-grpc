package demo.stream;

import demo.proto.DemoRequest;
import demo.proto.DemoResponse;
import demo.proto.StreamDemoServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.ClientCallStreamObserver;
import io.grpc.stub.ClientResponseObserver;

import java.util.Iterator;
import java.util.concurrent.CountDownLatch;

import static demo.util.LogUtils.log;
import static java.util.Arrays.asList;
import static java.util.concurrent.TimeUnit.SECONDS;

public class StreamDemoClient {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch done = new CountDownLatch(1);
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 8080)
                .usePlaintext()
                .build();
        StreamDemoServiceGrpc.StreamDemoServiceStub stub = StreamDemoServiceGrpc.newStub(channel);

        Iterator<String> iterator = asList("abcdefghiklmnopqrstvxyz".split("")).iterator();
        ClientResponseObserver<DemoRequest, DemoResponse> responseObserver = new ClientResponseObserver<DemoRequest, DemoResponse>() {
            private ClientCallStreamObserver<DemoRequest> requestStream;

            @Override
            public void beforeStart(ClientCallStreamObserver<DemoRequest> requestStream) {
                this.requestStream = requestStream;
                requestStream.setOnReadyHandler(this::next);
            }

            @Override
            public void onNext(DemoResponse response) {
                log(response);
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
                if (iterator.hasNext()) {
                    DemoRequest request = DemoRequest.newBuilder()
                            .setText(iterator.next())
                            .build();
                    requestStream.onNext(request);
                } else {
                    requestStream.onCompleted();
                }
            }
        };
        stub.streamDemo(responseObserver);
        done.await();
        channel.shutdown();
        channel.awaitTermination(5, SECONDS);
    }
}
