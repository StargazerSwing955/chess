package dataAccess;

import model.UserData;

public interface DataAccessUsers {

    //add user
    void addUser(UserData dataToAdd);

    //find user
    boolean findUser(UserData dataToFind);

    //delete users
    void clearUsers();

}
