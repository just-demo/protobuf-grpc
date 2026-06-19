package just.demo.unary;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

import static just.demo.util.LogUtils.log;

public class UnaryDemoServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(8080)
                .addService(new UnaryDemoServiceImpl())
                .build()
                .start();

        Runtime.getRuntime().addShutdownHook(new Thread(server::shutdown));
        log("listening...");
        server.awaitTermination();
    }
}
