package self.ed.server;

import io.grpc.stub.StreamObserver;
import self.ed.proto.DemoRequest;
import self.ed.proto.DemoResponse;
import self.ed.proto.DemoServiceGrpc;

public class DemoServiceImpl extends DemoServiceGrpc.DemoServiceImplBase {
    @Override
    public void demo(DemoRequest request, StreamObserver<DemoResponse> responseObserver) {
        System.out.println(request.getText());
        DemoResponse response = DemoResponse.newBuilder()
                .setText("Demo response")
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
