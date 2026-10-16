package server;

import io.javalin.*;

public class Server {

    private final Javalin javalin;

    public Server() {
        javalin = Javalin.create(config -> config.staticFiles.add("web"));
        // Register your endpoints and exception handlers here.
        javalin.post("/user",ctx -> ctx.result("{}"));
        //clear is called before every test
        javalin.delete("/db", ctx -> ctx.result("{}"));

        //write own tests to ensure everything works to supplement current tests


    }

    public int run(int desiredPort) {
        javalin.start(desiredPort);
        return javalin.port();
    }

    public void stop() {
        javalin.stop();
    }
}
