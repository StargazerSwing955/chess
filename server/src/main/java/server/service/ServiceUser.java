package server.service;

import dataAccess.*;
import model.AuthData;
import model.UserData;
import java.util.UUID;


public class ServiceUser {

    private final DataAccessAuth authDAO;
    private final DataAccessUsers userDAO;

    public ServiceUser(DataAccessAuth authDAO, DataAccessUsers userDAO){
        this.authDAO = authDAO;
        this.userDAO = userDAO;
    }

    public AuthData registerUser(UserData userData)throws UserAlreadyTakenException {
        if (userData.username() == null){

        }
        UserData alreadyExists= userDAO.findUser(userData);
        if (alreadyExists != null){
            throw new UserAlreadyTakenException("Error: already taken");
        }
        userDAO.addUser(userData);
        return createAuthData(userData);

    }

    public AuthData loginUser(UserData userData) throws UserNotExistException, InvalidAuthorizationException {
        UserData alreadyExists = userDAO.findUser(userData);
        if(alreadyExists == null){
            throw new UserNotExistException("Error: bad request");
        }
        if (!alreadyExists.password().equals(userData.password())){
            throw new InvalidAuthorizationException("Error: invalid login");
        }
        return createAuthData(userData);
    }

    public AuthData createAuthData(UserData userData) {
        String username = userData.username();
        String newToken = generateToken();
        AuthData createdData = new AuthData(newToken, username);
        authDAO.addAuthData(createdData);
        return createdData;
    }

    public static String generateToken() {
        return UUID.randomUUID().toString();
    }

    public void logoutUser(String logoutData) throws DataAccessException{ //takes just the authToken
        AuthData isLoggedIn = authDAO.getAuthData(logoutData);
        if(isLoggedIn == null){
            throw new DataAccessException("authToken does not exist");
        }
        authDAO.removeAuthData(isLoggedIn);
    }



}
