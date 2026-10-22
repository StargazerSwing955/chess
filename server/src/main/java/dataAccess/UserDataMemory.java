package dataAccess;

import model.AuthData;
import model.UserData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class UserDataMemory implements DataAccessUsers{

    private Collection<UserData> userData = new ArrayList<>();


    @Override
    public void addUser() {

    }

    @Override
    public void findUser() {

    }

    @Override
    public void clearUsers() {
        userData.clear();

    }
}
