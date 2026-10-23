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
    public boolean getAuthData(String dataToFind) { //checks the user is logged in based on username
        boolean doesContain = false;
        for(AuthData user: authenticationData){
            if (user.username().equals(dataToFind)) {
                doesContain = true;
                break;
            }
        }
        return doesContain;
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
