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
        javalin.post("/session",handler::loginUser);

        //logout
        javalin.delete("/session", handler::logoutUser);

        //get games
        javalin.get("/games", handler::getGameList);

        //make game
        javalin.post("/games", handler::createGame);

        //join game
        javalin.put("/games", handler::joinGame);

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
