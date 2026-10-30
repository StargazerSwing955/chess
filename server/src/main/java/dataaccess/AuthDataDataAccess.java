package dataaccess;

import model.AuthData;

public class AuthDataDataAccess implements DataAccessAuth{

    @Override
    public void addAuthData(AuthData dataToAdd) {
        //insert
    }

    @Override
    public AuthData getAuthData(String dataToFind) {
        //select
        return null;
    }

    @Override
    public void removeAuthData(AuthData dataToRemove) {
        //delete
    }

    @Override
    public void removeAll() {
        //drop
    }
}
