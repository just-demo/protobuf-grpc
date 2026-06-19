package just.demo.unary;

import io.grpc.stub.StreamObserver;
import just.demo.proto.DemoRequest;
import just.demo.proto.DemoResponse;
import just.demo.proto.UnaryDemoServiceGrpc;

import static just.demo.util.LogUtils.log;

public class UnaryDemoServiceImpl extends UnaryDemoServiceGrpc.UnaryDemoServiceImplBase {
    @Override
    public void demo(DemoRequest request, StreamObserver<DemoResponse> responseObserver) {
        log("request: " + request.getText());
        DemoResponse response = DemoResponse.newBuilder()
                .setText("Demo response")
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
