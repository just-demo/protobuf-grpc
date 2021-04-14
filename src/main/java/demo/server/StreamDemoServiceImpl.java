package demo.server;

import demo.proto.DemoRequest;
import demo.proto.DemoResponse;
import demo.proto.StreamDemoServiceGrpc;
import io.grpc.stub.StreamObserver;

import static demo.util.LogUtils.log;

public class StreamDemoServiceImpl extends StreamDemoServiceGrpc.StreamDemoServiceImplBase {
    @Override
    public StreamObserver<DemoRequest> streamDemo(StreamObserver<DemoResponse> responseObserver) {
        return new StreamObserver<DemoRequest>() {
            @Override
            public void onNext(DemoRequest request) {
                log(request);
                DemoResponse response = DemoResponse.newBuilder()
                        .setText(request.getText())
                        .build();
                responseObserver.onNext(response);
            }

            @Override
            public void onError(Throwable t) {
                log("error: " + t.getMessage());
                responseObserver.onError(t);
            }

            @Override
            public void onCompleted() {
                log("completed");
                responseObserver.onCompleted();
            }
        };
    }
}
