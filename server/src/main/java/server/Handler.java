package server;

import com.google.gson.Gson;
import dataAccess.AuthDataMemory;
import dataAccess.DataAccessException;
import dataAccess.GameDataMemory;
import dataAccess.UserDataMemory;
import io.javalin.http.Context;
import model.AuthData;
import model.UserData;

public class Handler {

    private final ServiceDelete serviceCallDelete;
    private final ServiceRegister serviceCallRegister;

    public Handler(AuthDataMemory authMemory, UserDataMemory userMemory, GameDataMemory gameMemory){
         serviceCallDelete = new ServiceDelete(authMemory,gameMemory,userMemory);
         serviceCallRegister = new ServiceRegister( authMemory,userMemory);
    }

    //register
    public AuthData registerUser(Context ctx){
        UserData dataFromCtx  = new Gson().fromJson(ctx.body(), UserData.class);
        return serviceCallRegister.registerUser(dataFromCtx);
    }

    //login
    public AuthData loginUser(Context ctx){
        UserData dataFromCtx  = new Gson().fromJson(ctx.body(), UserData.class);

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
