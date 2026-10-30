package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.Collection;
import java.util.List;

public class GameDataDataAccess implements DataAccessGames{
    @Override
    public Collection<GameData> listGame() {
        //select
        return List.of();
    }

    @Override
    public GameData giveGame(int gameId) {
        //select
        return null;
    }

    @Override
    public int addGame(String gameName) {
        //insert
        return 0;
    }

    @Override
    public void joinGame(ChessGame.TeamColor teamColor, String username, int gameId) {
        //alter
    }

    @Override
    public void deleteGames() {
        //drop
    }
}
