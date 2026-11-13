package client;

import model.*;
import org.junit.jupiter.api.*;
import server.Server;

import static org.junit.jupiter.api.Assertions.*;


public class ServerFacadeTests {
    private static ServerFacade facade;
    private static Server server;


    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        facade = new ServerFacade("http://localhost:"+port);
        System.out.println("Started test HTTP server on " + port);
    }
    @BeforeEach
    public void clear(){
        var request = facade.buildRequest("delete","/db",null,null);
        facade.sendRequest(request);
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }


    @Test
    public void sampleTest() {
        Assertions.assertTrue(true);
    }

    @Test
    public void register(){
        UserData testData = new UserData("bananaRoti", "sundae", "t@email.com");
        AuthData testReturn = facade.registerFacade(testData);

        assertEquals(testData.username(),testReturn.username());
    }

    @Test
    public void login(){
        UserData testData = new UserData("bananaRoti", "sundae", "t@email.com");
        AuthData registeredData = facade.registerFacade(testData);
        facade.logoutFacade(registeredData);
        AuthData testReturn = facade.loginFacade(new UserData(testData.username(),testData.password(),null));

        assertEquals(testData.username(),testReturn.username());
    }
    @Test
    public void noGames(){
        UserData testData = new UserData("bananaRoti", "sundae", "t@email.com");
        AuthData registeredData = facade.registerFacade(testData);
        ListGamesResult gList = facade.gameListFacade(registeredData);
        assertTrue(gList.games().isEmpty());
    }

    @Test
    public void createGame(){
        UserData testData = new UserData("bananaRoti", "sundae", "t@email.com");
        AuthData registeredData = facade.registerFacade(testData);
        CreateGameRequest gameReq = new CreateGameRequest(registeredData.authToken(),"Sugarland");
        facade.createGameFacade(gameReq);
        ListGamesResult gList = facade.gameListFacade(registeredData);
        assertFalse(gList.games().isEmpty());
    }


}
