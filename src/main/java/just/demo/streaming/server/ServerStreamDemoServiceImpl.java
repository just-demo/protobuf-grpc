package just.demo.streaming.server;

import io.grpc.stub.StreamObserver;
import just.demo.proto.DemoRequest;
import just.demo.proto.DemoResponse;
import just.demo.proto.ServerStreamDemoServiceGrpc.ServerStreamDemoServiceImplBase;

import static java.util.stream.IntStream.rangeClosed;
import static just.demo.util.LogUtils.log;

public class ServerStreamDemoServiceImpl extends ServerStreamDemoServiceImplBase {

    @Override
    public void demo(DemoRequest request, StreamObserver<DemoResponse> responseObserver) {
        log("request: " + request.getText());
        rangeClosed(1, 10).forEach(counter -> {
            DemoResponse response = DemoResponse.newBuilder()
                    .setText("Demo response " + counter)
                    .build();
            responseObserver.onNext(response);
        });
        responseObserver.onCompleted();
    }
}
