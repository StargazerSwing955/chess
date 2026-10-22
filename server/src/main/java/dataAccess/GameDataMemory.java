package dataAccess;

import model.AuthData;
import model.GameData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class GameDataMemory implements DataAccessGames {

    private Collection<GameData> gameLists = new ArrayList<>();


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
        gameLists.clear();
    }
}
