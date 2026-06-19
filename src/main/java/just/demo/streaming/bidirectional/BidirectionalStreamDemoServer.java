package just.demo.streaming.bidirectional;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import static just.demo.util.LogUtils.log;

public class BidirectionalStreamDemoServer {
    public static void main(String[] args) throws Exception {
        Server server = ServerBuilder.forPort(8080)
                .addService(new BidirectionalStreamDemoServiceImpl())
                .build()
                .start();
        log("listening...");
        server.awaitTermination();
    }
}
