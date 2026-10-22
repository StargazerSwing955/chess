package dataAccess;

import java.util.HashMap;

public class AuthDataMemory implements DataAccessAuth {

    private HashMap<String,String> AuthenticationData = new HashMap<String, String>(); //AuthData, Username

    @Override
    public void addAuthData() {

    }

    @Override
    public void getAuthData() {

    }

    @Override
    public void removeAuthData() {

    }

    @Override
    public void removeAll() {
        AuthenticationData.clear();
    }
}
