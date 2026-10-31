package dataaccess;

import chess.ChessGame;
import exceptions.FailedToConnectException;
import model.GameData;

import java.util.Collection;

public interface DataAccessGames {

    Collection<GameData> listGame() throws FailedToConnectException;

    GameData giveGame(int gameId) throws FailedToConnectException;

    int addGame(String gameName) throws FailedToConnectException;

    void joinGame(ChessGame.TeamColor teamColor, String username, int gameId) throws FailedToConnectException;

    void deleteGames() throws FailedToConnectException;

}
