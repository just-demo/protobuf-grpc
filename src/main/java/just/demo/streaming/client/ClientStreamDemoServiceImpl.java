package just.demo.streaming.client;

import io.grpc.stub.StreamObserver;
import just.demo.proto.ClientStreamDemoServiceGrpc.ClientStreamDemoServiceImplBase;
import just.demo.proto.DemoRequest;
import just.demo.proto.DemoResponse;

import java.util.concurrent.atomic.AtomicInteger;

import static just.demo.util.LogUtils.log;

public class ClientStreamDemoServiceImpl extends ClientStreamDemoServiceImplBase {
    @Override
    public StreamObserver<DemoRequest> demo(StreamObserver<DemoResponse> responseObserver) {
        return new StreamObserver<>() {
            private final AtomicInteger counter = new AtomicInteger();

            @Override
            public void onNext(DemoRequest request) {
                log("request: " + request.getText());
                counter.incrementAndGet();
            }

            @Override
            public void onError(Throwable t) {
                log("error: " + t.getMessage());
            }

            @Override
            public void onCompleted() {
                DemoResponse response = DemoResponse.newBuilder()
                        .setText("Received " + counter.get() + " requests")
                        .build();
                responseObserver.onNext(response);
                responseObserver.onCompleted();
            }
        };
    }
}
