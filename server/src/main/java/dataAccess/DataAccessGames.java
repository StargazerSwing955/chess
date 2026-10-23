package dataAccess;

import chess.ChessGame;
import model.GameData;

import java.util.Collection;

public interface DataAccessGames {

    //find game
    Collection<GameData> listGame();

    //add game
    int addGame(String gameName);

    //join game
    void joinGame(ChessGame.TeamColor teamColor, String username, int gameId);

    //delete game
    void deleteGames();

    //do I want to delete games that are already over (implement delete 1)

}
