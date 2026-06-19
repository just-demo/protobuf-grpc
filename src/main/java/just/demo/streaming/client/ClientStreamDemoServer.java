package just.demo.streaming.client;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import static just.demo.util.LogUtils.log;

public class ClientStreamDemoServer {
    public static void main(String[] args) throws Exception {
        Server server = ServerBuilder.forPort(8080)
                .addService(new ClientStreamDemoServiceImpl())
                .build()
                .start();
        log("listening...");
        server.awaitTermination();
    }
}
