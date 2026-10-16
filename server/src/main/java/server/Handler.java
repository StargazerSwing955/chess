package server;

import io.javalin.http.Context;

public class Handler {


    private ServiceDelete serviceCallDelete = new ServiceDelete();

    public Handler(){

    }

    //register

    //login

    //logout

    //get games

    //make game

    //join game

    //clear
    public void clear(Context ctx){
      serviceCallDelete.deleteData();

    }



}
