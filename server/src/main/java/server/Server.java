package server;

import dataaccess.*;
import exceptions.DataAccessException;
import io.javalin.*;

public class Server {

    private final Javalin javalin;

    //db memory objects
    private final DataAccessAuth authMemory = new AuthDataDataAccess();
    private final DataAccessUsers userMemory = new UserDataDataAccess();
    private final DataAccessGames gameMemory = new GameDataDataAccess();

    private final Handler handler = new Handler(authMemory,userMemory,gameMemory);

    public Server() {
        try {
            DatabaseManager.createDatabase();
        } catch (DataAccessException e){
            throw new RuntimeException("failed to connect to database", e);
        }
        //holds all the paths
        javalin = Javalin.create(config -> config.staticFiles.add("web"));

        //register
        javalin.post("/user", handler::registerUser);

        //login
        javalin.post("/session",handler::loginUser);

        //logout
        javalin.delete("/session", handler::logoutUser);

        //get games
        javalin.get("/game", handler::getGameList);

        //make game
        javalin.post("/game", handler::createGame);

        //join game
        javalin.put("/game", handler::joinGame);

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
