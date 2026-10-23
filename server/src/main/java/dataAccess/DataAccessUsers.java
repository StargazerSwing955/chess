package dataAccess;

import model.UserData;

public interface DataAccessUsers {

    //add user
    void addUser(UserData dataToAdd);

    //find user
    UserData findUser(UserData dataToFind);

    //delete users
    void clearUsers();

}
