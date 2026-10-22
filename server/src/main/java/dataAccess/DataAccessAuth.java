package dataAccess;

import model.AuthData;

public interface DataAccessAuth {

    //add AuthData
    void addAuthData(AuthData dataToAdd);

    //find AuthData
    void getAuthData(AuthData dataToFind);

    //delete AuthData
    void removeAuthData(AuthData dataToRemove);

    //delete all
    void removeAll();

}
