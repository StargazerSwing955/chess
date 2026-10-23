package server;

import com.google.gson.Gson;
import dataAccess.*;
import io.javalin.http.ContentType;
import io.javalin.http.Context;
import model.AuthData;
import model.UserData;

import java.util.Map;

public class Handler {

    private final ServiceDelete serviceCallDelete;
    private final ServiceRegister serviceCallRegister;

    public Handler(AuthDataMemory authMemory, UserDataMemory userMemory, GameDataMemory gameMemory){
         serviceCallDelete = new ServiceDelete(authMemory,gameMemory,userMemory);
         serviceCallRegister = new ServiceRegister( authMemory,userMemory);
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

    //get games

    //make game

    //join game

    //clear
    public void clear(Context ctx){
        try {
            serviceCallDelete.deleteData();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }

    }



}
