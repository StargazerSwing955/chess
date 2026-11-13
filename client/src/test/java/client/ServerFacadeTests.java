package client;

import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.*;
import server.Server;

import java.net.http.HttpRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ServerFacadeTests {
    private static ServerFacade facade;
    private static Server server;


    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
    }
    @BeforeEach
    public static void clear(){
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

}
