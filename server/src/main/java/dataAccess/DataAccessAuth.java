package dataAccess;

import model.AuthData;

public interface DataAccessAuth {

    //add AuthData
    void addAuthData(AuthData dataToAdd);

    //find AuthData
    AuthData getAuthData(String dataToFind);

    //delete AuthData
    void removeAuthData(AuthData dataToRemove);

    //delete all
    void removeAll();

}
