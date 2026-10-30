package dataaccess;

import model.AuthData;

public interface DataAccessAuth {

    //add AuthData
    void addAuthData(AuthData dataToAdd) throws FailedToConnectException;

    //find AuthData
    AuthData getAuthData(String dataToFind) throws FailedToConnectException;

    //delete AuthData
    void removeAuthData(AuthData dataToRemove) throws FailedToConnectException;

    //delete all
    void removeAll() throws FailedToConnectException;

}
