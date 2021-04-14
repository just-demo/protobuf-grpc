package demo.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import static demo.util.LogUtils.log;

public class StreamDemoServer {
    public static void main(String[] args) throws Exception {
        Server server = ServerBuilder.forPort(8080)
                .addService(new StreamDemoServiceImpl())
                .build()
                .start();
        log("listening...");
        server.awaitTermination();
    }
}
