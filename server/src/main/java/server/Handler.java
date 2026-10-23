package server;

import com.google.gson.Gson;
import dataAccess.*;
import io.javalin.http.ContentType;
import io.javalin.http.Context;
import model.GameData;
import model.UserData;

import java.util.Collection;
import java.util.Map;

public class Handler {

    private final ServiceDelete serviceCallDelete;
    private final ServiceRegister serviceCallRegister;
    private final ServiceGameList serviceCallGames;

    public Handler(AuthDataMemory authMemory, UserDataMemory userMemory, GameDataMemory gameMemory){
         serviceCallDelete = new ServiceDelete(authMemory,gameMemory,userMemory);
         serviceCallRegister = new ServiceRegister( authMemory,userMemory);
         serviceCallGames = new ServiceGameList(authMemory, gameMemory);
    }

    //register
    public void registerUser(Context ctx){
        UserData dataFromCtx  = new Gson().fromJson(ctx.body(), UserData.class);
        String resultString = "";
        if (dataFromCtx.username() == null || dataFromCtx.password() == null || dataFromCtx.email() ==null){
            ctx.result( new Gson().toJson(Map.of("message","Error: bad request")))
                    .contentType(ContentType.APPLICATION_JSON).status(400);
            return;
        }
        try {
            resultString = new Gson().toJson(serviceCallRegister.registerUser(dataFromCtx));
        } catch (UserAlreadyTakenException e) {
            ctx.result( new Gson().toJson(Map.of("message","Error: already taken")))
                    .contentType(ContentType.APPLICATION_JSON).status(403);
            return;
        }
        ctx.result(resultString).contentType(ContentType.APPLICATION_JSON).status(200);

    }

    //login
    public void loginUser(Context ctx){
        UserData dataFromCtx  = new Gson().fromJson(ctx.body(), UserData.class);
        String resultString = "";
        if (dataFromCtx.username() == null || dataFromCtx.password() == null){
            ctx.result( new Gson().toJson(Map.of("message","Error: bad request")))
                    .contentType(ContentType.APPLICATION_JSON).status(400);
            return;
        }
        try {
            resultString = new Gson().toJson(serviceCallRegister.loginUser(dataFromCtx));
        } catch (UserNotExistException | InvalidAuthorizationException e){
            ctx.result( new Gson().toJson(Map.of("message","Error: unauthorized")))
                    .contentType(ContentType.APPLICATION_JSON).status(401);
            return;
        }
        ctx.result(resultString).contentType(ContentType.APPLICATION_JSON).status(200);
    }

    //logout
    public void logoutUser(Context ctx){
        if (hasAuthToken(ctx)) {
            String dataFromCtx  = ctx.header("authorization");
            try {
                serviceCallRegister.logoutUser(dataFromCtx);
            } catch (DataAccessException e) {
                ctx.result(new Gson().toJson(Map.of("message", "Error: unauthorized")))
                        .contentType(ContentType.APPLICATION_JSON).status(401);
                return;
            }
            ctx.status(200);
        }
    }



    //get games
    public void getGameList(Context ctx){ // just authToken
        if(hasAuthToken(ctx)) {
            String authFromCtx = ctx.header("authorization");
            Collection<GameData> resultString = null;
            try{
                resultString = serviceCallGames.giveGameList(authFromCtx);
            } catch (InvalidAuthorizationException e) {
                ctx.result(new Gson().toJson(Map.of("message", "Error: unauthorized")))
                        .contentType(ContentType.APPLICATION_JSON).status(401);
                return;
            }
            ctx.result(new Gson().toJson(Map.of("games",resultString))).contentType(ContentType.APPLICATION_JSON).status(200);
        }
    }

    //make game
    public void createGame(Context ctx) { // authToken, gameName
        if (hasAuthToken(ctx)) {
            String authFromCtx = ctx.header("authorization");
            GameData gameNameString  = new Gson().fromJson(ctx.body(), GameData.class);
            String resultString = "";
            if (gameNameString.gameName() == null){
                ctx.result( new Gson().toJson(Map.of("message","Error: bad request")))
                        .contentType(ContentType.APPLICATION_JSON).status(400);
                return;
            }
            try {
                resultString = String.valueOf(serviceCallGames.createGame(authFromCtx,gameNameString.gameName()));
            } catch (InvalidAuthorizationException e) {
                ctx.result(new Gson().toJson(Map.of("message", "Error: unauthorized")))
                        .contentType(ContentType.APPLICATION_JSON).status(401);
                return;
            }
            ctx.result(new Gson().toJson(Map.of("gameID",resultString))).contentType(ContentType.APPLICATION_JSON).status(200);
        }
    }

    //join game
    public void joinGame(Context ctx){ //authToken, playerColor, gameID
        if (hasAuthToken(ctx)){
            String authFromCtx = ctx.header("authorization");
            JoinRequest gameToJoin = new Gson().fromJson(ctx.body(), JoinRequest.class);
            if (gameToJoin.gameID() == null || gameToJoin.playerColor()==null){
                ctx.result( new Gson().toJson(Map.of("message","Error: bad request")))
                        .contentType(ContentType.APPLICATION_JSON).status(400);
                return;
            }
            try{
                serviceCallGames.joinGame(authFromCtx,gameToJoin.playerColor(), gameToJoin.gameID());
            } catch (InvalidAuthorizationException e) {
                ctx.result(new Gson().toJson(Map.of("message", "Error: unauthorized")))
                        .contentType(ContentType.APPLICATION_JSON).status(401);
                return;
            } catch (GameFullException e){
                ctx.result(new Gson().toJson(Map.of("message", "Error: already taken")))
                        .contentType(ContentType.APPLICATION_JSON).status(403);
                return;
            }

            ctx.status(200);
        }
    }

    //clear
    public void clear(Context ctx){
        try {
            serviceCallDelete.deleteData();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }

    }

    public boolean hasAuthToken(Context ctx){
        String dataFromCtx  = ctx.header("authorization");
        if (dataFromCtx == null){
            ctx.result( new Gson().toJson(Map.of("message","Error: unauthorized")))
                    .contentType(ContentType.APPLICATION_JSON).status(401);
        }
        return true;
    }


}
