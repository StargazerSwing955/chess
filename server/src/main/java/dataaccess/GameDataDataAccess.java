package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.Collection;
import java.util.List;

public class GameDataDataAccess implements DataAccessGames{
    @Override
    public Collection<GameData> listGame() {
        return List.of();
    }

    @Override
    public GameData giveGame(int gameId) {
        return null;
    }

    @Override
    public int addGame(String gameName) {
        return 0;
    }

    @Override
    public void joinGame(ChessGame.TeamColor teamColor, String username, int gameId) {

    }

    @Override
    public void deleteGames() {

    }
}
