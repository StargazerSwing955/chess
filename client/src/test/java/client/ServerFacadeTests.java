package client;

import chess.ChessGame;
import model.*;
import org.junit.jupiter.api.*;
import server.Server;

import java.util.List;

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
    public void registerMultipleTimes(){
        UserData testData = new UserData("melancream", "death", "scythe@email.com");
        AuthData testReturn = facade.registerFacade(testData);
        facade.logoutFacade(testReturn);


        assertThrows(RuntimeException.class,()->{facade.registerFacade(testData);});

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

    @Test
    public void joinGame(){
        UserData testData = new UserData("bananaRoti", "sundae", "t@email.com");
        AuthData registeredData = facade.registerFacade(testData);
        CreateGameRequest gameReq = new CreateGameRequest(registeredData.authToken(),"Sugarland");
        facade.createGameFacade(gameReq);
        JoinRequest joinReq = new JoinRequest(ChessGame.TeamColor.BLACK,1);
        facade.joinFacade(joinReq,registeredData.authToken());
        ListGamesResult gList = facade.gameListFacade(registeredData);
        List<GameData> gData = gList.games();
        GameData testGame = gData.get(0);
        assertTrue(testGame.blackUsername().equals(testData.username()));
    }

    @Test
    public void joinGame2(){
        UserData testData1 = new UserData("bananaRoti", "sundae", "t@email.com");
        AuthData registeredData1 = facade.registerFacade(testData1);

        CreateGameRequest gameReq = new CreateGameRequest(registeredData1.authToken(),"Sugarland");
        facade.createGameFacade(gameReq);

        JoinRequest joinReq1 = new JoinRequest(ChessGame.TeamColor.BLACK,1);
        facade.joinFacade(joinReq1, registeredData1.authToken());
        facade.logoutFacade(registeredData1);

        UserData testData2 = new UserData("sundae", "doll", "h@email.com");
        AuthData registeredData2 = facade.registerFacade(testData2);
        JoinRequest joinReq2 = new JoinRequest(ChessGame.TeamColor.WHITE,1);
        facade.joinFacade(joinReq2,registeredData2.authToken());

        ListGamesResult gList = facade.gameListFacade(registeredData2);
        List<GameData> gData = gList.games();
        GameData testGame = gData.getFirst();


        assertTrue((testGame.blackUsername().equals(testData1.username())&&
                (testGame.whiteUsername().equals(testData2.username()))));
    }

}
