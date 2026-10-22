package server;

import dataAccess.AuthDataMemory;
import dataAccess.DataAccessException;
import dataAccess.GameDataMemory;
import dataAccess.UserDataMemory;
import io.javalin.*;

public class Server {

    private final Javalin javalin;

    //db memory objects
    private final AuthDataMemory authMemory = new AuthDataMemory();
    private final UserDataMemory userMemory = new UserDataMemory();
    private final GameDataMemory gameMemory = new GameDataMemory();

    private final Handler handler = new Handler(authMemory,userMemory,gameMemory);

    public Server() { //holds all the paths
        javalin = Javalin.create(config -> config.staticFiles.add("web"));

        //register
        javalin.post("/user", handler::registerUser);

        //login
        javalin.post("/session",ctx -> {
            //success
        });

        //logout
        javalin.delete("/session", ctx -> {
            //success: 200
            //failure: 401, message: unauthorized
            //failure: 500, message
        });

        //get games
        javalin.get("/games", ctx->{
            //success: 200, [200] { "games": [{"gameID": "", "whiteUsername":"", "blackUsername":"", "gameName:""} ]}
            //fail: 401: message: unauthorized
            //fail: 500
        });

        //make game
        javalin.post("/games", ctx->{
            //success: 200, [200] { "gameID": "" }
            //fail: 400: bad request
            //fail: 401: message: unauthorized
            //fail: 500
        });

        //join game
        javalin.put("/games", ctx->{

        });

        //clear is called before every test
        javalin.delete("/db", handler::clear);


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
