package server.service;

import dataAccess.DataAccessAuth;
import dataAccess.DataAccessException;
import dataAccess.DataAccessGames;
import dataAccess.DataAccessUsers;

public class ServiceDelete {

    private final DataAccessAuth authData;
    private final DataAccessGames gameData;
    private final DataAccessUsers userData;

    public ServiceDelete(DataAccessAuth authData, DataAccessGames gameData, DataAccessUsers userData){
        this.authData = authData;
        this.gameData = gameData;
        this.userData = userData;
    }


    public void deleteData() throws DataAccessException {
        authData.removeAll();
        gameData.deleteGames();
        userData.clearUsers();
    }
}
