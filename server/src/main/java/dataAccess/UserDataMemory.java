package dataAccess;

import model.UserData;

import java.util.ArrayList;
import java.util.Collection;

public class UserDataMemory implements DataAccessUsers{

    private Collection<UserData> userDataCollection = new ArrayList<>();


    @Override
    public void addUser(UserData dataToAdd) {
            userDataCollection.add(dataToAdd);
    }

    @Override
    public UserData findUser(UserData dataToFind) {
        for(UserData user: userDataCollection){
            if (user.username().equals(dataToFind.username())) {
                return user;
            }
        }
        return null;
    }

    @Override
    public void clearUsers() {
        userDataCollection.clear();

    }
}
