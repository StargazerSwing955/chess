package dataAccess;

import chess.ChessGame;
import model.GameData;

import java.util.Collection;

public interface DataAccessGames {

    Collection<GameData> listGame();

    GameData giveGame(int gameId);

    int addGame(String gameName);

    void joinGame(ChessGame.TeamColor teamColor, String username, int gameId);

    void deleteGames();

}
