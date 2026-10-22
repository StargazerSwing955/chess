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
    public boolean findUser(UserData dataToFind) {
        boolean doesContain = false;
        for(UserData user: userDataCollection){
            if (user.username().equals(dataToFind.username())) {
                doesContain = true;
                break;
            }
        }
        return doesContain;
    }

    @Override
    public void clearUsers() {
        userDataCollection.clear();

    }
}
