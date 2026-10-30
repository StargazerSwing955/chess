package dataaccess;

import model.UserData;

public interface DataAccessUsers {

    //add user
    void addUser(UserData dataToAdd) throws FailedToConnectException;

    //find user
    UserData findUser(UserData dataToFind) throws FailedToConnectException;

    //delete users
    void clearUsers() throws FailedToConnectException;

}
