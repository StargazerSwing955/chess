package dataAccess;

import java.util.HashMap;

public class UserDataMemory implements DataAccessUsers{

    private HashMap<String,String> UserData = new HashMap<String, String>(); //username, password, email


    @Override
    public void addUser() {

    }

    @Override
    public void findUser() {

    }

    @Override
    public void clearUsers() {
        UserData.clear();

    }
}
