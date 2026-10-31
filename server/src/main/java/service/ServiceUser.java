package service;

import dataaccess.*;
import model.AuthData;
import model.UserData;
import org.mindrot.jbcrypt.BCrypt;

import java.util.UUID;


public class ServiceUser {

    private final DataAccessAuth authDAO;
    private final DataAccessUsers userDAO;

    public ServiceUser(DataAccessAuth authDAO, DataAccessUsers userDAO){
        this.authDAO = authDAO;
        this.userDAO = userDAO;
    }

    public AuthData registerUser(UserData userData) throws UserAlreadyTakenException, FailedToConnectException {
        String hash = BCrypt.hashpw(userData.password(), BCrypt.gensalt());
        UserData dataToAdd = new UserData(userData.username(),hash,userData.email());

        UserData alreadyExists= userDAO.findUser(userData);
        if (alreadyExists != null){
            throw new UserAlreadyTakenException("Error: already taken");
        }
        try {
            userDAO.addUser(dataToAdd);
        } catch (DataAccessException e) {
            throw new FailedToConnectException("Did not connect");
        }
        return createAuthData(userData);
    }

    public AuthData loginUser(UserData userData) throws UserNotExistException, InvalidAuthorizationException,
            FailedToConnectException {
        UserData alreadyExists = userDAO.findUser(userData);
        if(alreadyExists == null){
            throw new UserNotExistException("Error: bad request");
        }
        if (!BCrypt.checkpw(userData.password(), alreadyExists.password())){
            throw new InvalidAuthorizationException("Error: invalid login");
        }
        return createAuthData(userData);
    }

    public AuthData createAuthData(UserData userData) throws FailedToConnectException {
        String username = userData.username();
        String newToken = generateToken();
        AuthData createdData = new AuthData(newToken, username);
        authDAO.addAuthData(createdData);
        return createdData;
    }

    public static String generateToken() {
        return UUID.randomUUID().toString();
    }

    public void logoutUser(String logoutData) throws FailedToConnectException, DataAccessException{ //takes just the authToken
        AuthData isLoggedIn = authDAO.getAuthData(logoutData);
        if(isLoggedIn == null){
            throw new DataAccessException("authToken does not exist");
        }
        authDAO.removeAuthData(isLoggedIn);
    }



}
