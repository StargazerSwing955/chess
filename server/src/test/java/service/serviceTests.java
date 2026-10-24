package service;

import chess.ChessGame;
import dataAccess.*;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.Test;
import server.service.ServiceGameList;
import server.service.ServiceDelete;
import server.service.ServiceUser;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class serviceTests {

    private static DataAccessGames gamesDAO = new GameDataMemory();
    private static DataAccessAuth authDAO = new AuthDataMemory();
    private static DataAccessUsers userDAO = new UserDataMemory();


    private static ServiceUser userServices = new ServiceUser(authDAO,userDAO);
    private static ServiceGameList gameServices = new ServiceGameList(authDAO,gamesDAO);
    private static ServiceDelete clearService =new ServiceDelete(authDAO,gamesDAO,userDAO);



    @Test
    void clearAllDAO() {
        try {
            clearService.deleteData();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        } {

        }
    }

    @Test
    void registerUser(){
        UserData tester = new UserData("beebee","14567","a@email.com");
        userServices.registerUser(tester);

        assertEquals(tester, userDAO.findUser(tester));
    }

    @Test
    void loginUser(){
        UserData tester = new UserData("beebee","14567","a@email.com");
        AuthData testedRegister = userServices.registerUser(tester);
        try {
            userServices.logoutUser(testedRegister.authToken());
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        userServices.loginUser(tester);

        assertEquals(tester, userDAO.findUser(tester));
    }

    @Test
    void createGame(){
        UserData tester = new UserData("beebee","14567","a@email.com");
        AuthData testedRegister = userServices.registerUser(tester);

        gameServices.createGame(testedRegister.authToken(),"game1");
        GameData game1 = new GameData(1,null,null,"game1", new ChessGame());

        assertEquals(game1, gamesDAO.giveGame(1));

    }


}
