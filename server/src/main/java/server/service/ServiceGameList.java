package server.service;

import chess.ChessGame;
import dataAccess.*;
import model.AuthData;
import model.GameData;

import java.util.Collection;

public class ServiceGameList {

    private final DataAccessAuth authDAO;
    private final DataAccessGames gameDAO;

    public ServiceGameList(DataAccessAuth authDAO, DataAccessGames gameDAO){
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
    }

    public AuthData checkAuthorization(String receivedAuthToken) throws InvalidAuthorizationException {
        AuthData isLoggedIn = authDAO.getAuthData(receivedAuthToken);
        if(isLoggedIn == null){
            throw new InvalidAuthorizationException("authToken does not exist");
        }
        return isLoggedIn;
    }

    //join game
    public void joinGame(String authToken, ChessGame.TeamColor teamColor, Integer gameId) throws InvalidAuthorizationException, GameFullException {
        AuthData userAuthData = checkAuthorization(authToken);
        GameData seeIfFull = gameDAO.giveGame(gameId);
        if(teamColor == ChessGame.TeamColor.WHITE){
            if(seeIfFull.whiteUsername()!= null){
                throw new GameFullException("taken");
            }
        }
        if(teamColor == ChessGame.TeamColor.BLACK){
            if(seeIfFull.blackUsername()!= null){
                throw new GameFullException("taken");
            }
        }
        gameDAO.joinGame(teamColor, userAuthData.username(),gameId);
    }

    //return full game list
    public Collection<GameData> giveGameList(String authToken) throws InvalidAuthorizationException{
        AuthData userAuthData = checkAuthorization(authToken);
        return gameDAO.listGame();
    }

    //create game
    public int createGame(String authToken, String gameName) throws InvalidAuthorizationException {
        AuthData userAuthData = checkAuthorization(authToken);
        return gameDAO.addGame(gameName);

    }

}
