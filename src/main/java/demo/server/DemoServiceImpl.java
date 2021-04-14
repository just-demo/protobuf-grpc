package demo.server;

import demo.proto.DemoRequest;
import demo.proto.DemoResponse;
import io.grpc.stub.StreamObserver;
import demo.proto.DemoServiceGrpc;

public class DemoServiceImpl extends DemoServiceGrpc.DemoServiceImplBase {
    @Override
    public void demo(DemoRequest request, StreamObserver<DemoResponse> responseObserver) {
        System.out.println(request);
        DemoResponse response = DemoResponse.newBuilder()
                .setText("Demo response")
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
