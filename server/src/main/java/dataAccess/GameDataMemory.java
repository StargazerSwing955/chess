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
    public GameData giveGame(int gameId){
       return gameCollection.get(gameId);
    }

    @Override
    public void joinGame(ChessGame.TeamColor teamColor, String username, int gameId) {
        GameData gameToJoin = gameCollection.get(gameId);

        if (teamColor == ChessGame.TeamColor.WHITE){
            if (gameToJoin.whiteUsername() == null){
                gameCollection.replace(gameId,gameToJoin,new GameData(gameId,username,gameToJoin.blackUsername(), gameToJoin.gameName(),gameToJoin.game()));
            }

        }
        if (teamColor == ChessGame.TeamColor.BLACK){
            if (gameToJoin.blackUsername() == null){
                gameCollection.replace(gameId,gameToJoin, new GameData(gameId,gameToJoin.whiteUsername(),username,gameToJoin.gameName(),gameToJoin.game()));
            }
        }
    }

    @Override
    public void deleteGames() { //clear GameData
        gameCollection.clear();
    }

}
