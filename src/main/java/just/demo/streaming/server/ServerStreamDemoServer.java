package just.demo.streaming.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import static just.demo.util.LogUtils.log;

public class ServerStreamDemoServer {
    public static void main(String[] args) throws Exception {
        Server server = ServerBuilder.forPort(8080)
                .addService(new ServerStreamDemoServiceImpl())
                .build()
                .start();
        log("listening...");
        server.awaitTermination();
    }
}
