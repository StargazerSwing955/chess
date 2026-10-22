package server;

import dataAccess.AuthDataMemory;
import dataAccess.DataAccessException;
import dataAccess.GameDataMemory;
import dataAccess.UserDataMemory;
import io.javalin.http.Context;

public class Handler {

    private final ServiceDelete serviceCallDelete;

    public Handler(AuthDataMemory authMemory, UserDataMemory userMemory, GameDataMemory gameMemory){
         serviceCallDelete = new ServiceDelete(authMemory,gameMemory,userMemory);
    }

    //register

    //login

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
