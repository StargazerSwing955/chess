package server;

import dataAccess.DataAccessAuth;
import dataAccess.DataAccessUsers;
import dataAccess.UserAlreadyTakenException;
import dataAccess.UserNotExistException;
import model.AuthData;
import model.UserData;
import java.util.UUID;


import java.util.Collection;

public class ServiceRegister {

    private final DataAccessAuth authDAO;
    private final DataAccessUsers userDAO;

    public ServiceRegister(DataAccessAuth authDAO, DataAccessUsers userDAO){
        this.authDAO = authDAO;
        this.userDAO = userDAO;
    }

    public AuthData registerUser(UserData userData)throws UserAlreadyTakenException {
        boolean alreadyExists= userDAO.findUser(userData);
        if (alreadyExists){
            throw new UserAlreadyTakenException("Error: already taken");
        }
        userDAO.addUser(userData);
        return createAuthData(userData);

    }


    public AuthData loginUser(UserData userData) throws UserNotExistException {


        return createAuthData(userData);
    }

    public AuthData createAuthData(UserData userData){
        String username = userData.username();
        String newToken = generateToken();
        return new AuthData(newToken,username);
    }

    public static String generateToken() {
        return UUID.randomUUID().toString();
    }

}
