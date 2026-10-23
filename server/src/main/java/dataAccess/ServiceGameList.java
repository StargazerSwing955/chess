package dataAccess;

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

    public AuthData checkAuthorization(String receivedAuthToken) throws DataAccessException{
        AuthData isLoggedIn = authDAO.getAuthData(receivedAuthToken);
        if(isLoggedIn == null){
            throw new DataAccessException("authToken does not exist");
        }
        return isLoggedIn;
    }

    //join game
    public void userJoinGame(String authToken) throws DataAccessException, GameFullException{
        AuthData userAuthData = checkAuthorization(authToken);

    }

    //return full game list
    public Collection<GameData> giveGameList(String authToken) throws DataAccessException{
        AuthData userAuthData = checkAuthorization(authToken);
        return gameDAO.listGame();
    }

    //create game
    public void createGame(String authToken, String gameName) throws DataAccessException {
        AuthData userAuthData = checkAuthorization(authToken);

    }

}
