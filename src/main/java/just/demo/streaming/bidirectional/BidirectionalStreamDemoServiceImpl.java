package just.demo.streaming.bidirectional;

import io.grpc.stub.StreamObserver;
import just.demo.proto.BidirectionalStreamDemoServiceGrpc.BidirectionalStreamDemoServiceImplBase;
import just.demo.proto.DemoRequest;
import just.demo.proto.DemoResponse;

import static just.demo.util.LogUtils.log;

public class BidirectionalStreamDemoServiceImpl extends BidirectionalStreamDemoServiceImplBase {
    @Override
    public StreamObserver<DemoRequest> demo(StreamObserver<DemoResponse> responseObserver) {
        return new StreamObserver<>() {
            @Override
            public void onNext(DemoRequest request) {
                log("request: " + request.getText());
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
