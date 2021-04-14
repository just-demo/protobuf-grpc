package demo.server;

import demo.proto.DemoRequest;
import demo.proto.DemoResponse;
import io.grpc.stub.StreamObserver;
import demo.proto.DemoServiceGrpc;

import static demo.util.LogUtils.log;

public class DemoServiceImpl extends DemoServiceGrpc.DemoServiceImplBase {
    @Override
    public void demo(DemoRequest request, StreamObserver<DemoResponse> responseObserver) {
        log(request);
        DemoResponse response = DemoResponse.newBuilder()
                .setText("Demo response")
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
