package dataAccess;

import model.AuthData;

import java.util.ArrayList;
import java.util.Collection;

public class AuthDataMemory implements DataAccessAuth {
    private Collection<AuthData> authenticationData = new ArrayList<>();

    @Override
    public void addAuthData(AuthData dataToAdd) {
            authenticationData.add(dataToAdd);
    }

    @Override
    public AuthData getAuthData(String dataToFind) { //checks the user is logged in based on username or authToken
        for(AuthData user: authenticationData){
            if (user.username().equals(dataToFind) || user.authToken().equals(dataToFind)) {
               return user;
            }
        }
        return null;
    }

    @Override
    public void removeAuthData(AuthData dataToRemove) {
        authenticationData.remove(dataToRemove);
    }

    @Override
    public void removeAll() {
        authenticationData.clear();
    }
}
