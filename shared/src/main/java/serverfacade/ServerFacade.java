package serverfacade;

public class ServerFacade {
    //connects to the endpoints of the server;
    // aka, calls server which runs all the internal workings and passes back that stuff got
    //example is in petshop's shared folder!!

    //take in port and url just in case*

    //register
    //javalin.post("/user", handler::registerUser);

    //login
    //javalin.post("/session",handler::loginUser);

    //logout
    //javalin.delete("/session", handler::logoutUser);


    //get games
    //javalin.get("/game", handler::getGameList);


    //make game
    //javalin.post("/game", handler::createGame);


    //join game
    //javalin.put("/game", handler::joinGame);


    //clear is called before every test; might not need in client side
    //javalin.delete("/db", handler::clear);
}
