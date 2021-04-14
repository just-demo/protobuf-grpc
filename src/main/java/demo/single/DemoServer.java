package demo.single;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

import static demo.util.LogUtils.log;

public class DemoServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(8080)
                .addService(new DemoServiceImpl())
                .build()
                .start();

        Runtime.getRuntime().addShutdownHook(new Thread(server::shutdown));
        log("listening...");
        server.awaitTermination();
    }
}
