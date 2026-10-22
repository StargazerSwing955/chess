package dataAccess;

import model.AuthData;
import java.util.ArrayList;
import java.util.Collection;

public class AuthDataMemory implements DataAccessAuth {
    private Collection<AuthData> authenticationData = new ArrayList<>();

    @Override
    public void addAuthData(AuthData dataToAdd) {
        if (!authenticationData.contains(dataToAdd)){
            authenticationData.add(dataToAdd);
        }
    }

    @Override
    public void getAuthData(AuthData dataToFind) {
        authenticationData.contains(dataToFind);
    }

    @Override
    public void removeAuthData(AuthData dataToRemove) {
        if (!authenticationData.contains(dataToRemove)){
            authenticationData.remove(dataToRemove);

        }
    }

    @Override
    public void removeAll() {
        authenticationData.clear();
    }
}
