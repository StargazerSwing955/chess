package dataAccess;

import java.util.HashMap;

public class GameDataMemory implements DataAccessGames {

    private HashMap<String,String> GameData = new HashMap<String, String>(); //gameID, white player, black player


    @Override
    public void findGame() {

    }

    @Override
    public void addGame() {

    }

    @Override
    public void joinGame() {

    }

    @Override
    public void deleteGames() { //clear GameData

    }
}
