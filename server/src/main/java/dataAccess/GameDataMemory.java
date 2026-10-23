package dataAccess;

import chess.ChessGame;
import model.GameData;

import java.util.Collection;
import java.util.HashMap;

public class GameDataMemory implements DataAccessGames {

    private final HashMap<Integer,GameData> gameCollection = new HashMap<>();
    private int gameIdGen = 0;

    @Override
    public Collection<GameData> listGame() { return gameCollection.values(); }

    @Override
    public int addGame(String gameName) {
        GameData gameData = new GameData(++gameIdGen,null, null, gameName,new ChessGame());
        gameCollection.put(gameIdGen, gameData);
        return gameIdGen;
    }

    @Override
    public void joinGame(ChessGame.TeamColor teamColor, String username, int gameId) {
        GameData gameToJoin = gameCollection.get(gameId);

    }

    @Override
    public void deleteGames() { //clear GameData
        gameCollection.clear();
    }

}
