package client;

import com.google.gson.Gson;
import model.*;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class ServerFacade {
    private final HttpClient client = HttpClient.newHttpClient();
    private final String serverUrl;

    public ServerFacade(String url) {
        serverUrl = url;
    }
    //connects to the endpoints of the server;
    // aka, calls server which runs all the internal workings and passes back that stuff got

    //take in port and url just in case*

    //register
    //javalin.post("/user", handler::registerUser);
    public AuthData registerFacade(UserData userData) {
        var request = buildRequest("POST", "/user", userData, null);
        var response = sendRequest(request);

        return handleResponse(response, AuthData.class);
    }


    //login
    //javalin.post("/session",handler::loginUser);
    public AuthData loginFacade(UserData userData) {
        var request = buildRequest("POST", "/session", userData, null);
        var response = sendRequest(request);

        return handleResponse(response, AuthData.class);
    }

    //logout
    //javalin.delete("/session", handler::logoutUser);
    public void logoutFacade(AuthData authTok ) {
        var request = buildRequest("DELETE", "/session", null, authTok.authToken());
        var response = sendRequest(request);
    }

    //get games
    //javalin.get("/game", handler::getGameList);
    public ListGamesResult gameListFacade(AuthData userData) {
        var request = buildRequest("GET", "/game", null, userData.authToken());
        var response = sendRequest(request);
        return handleResponse(response, ListGamesResult.class);
    }


    //make game
    //javalin.post("/game", handler::createGame);
    public GameData createGameFacade(CreateGameRequest gameInfo) {
        var request = buildRequest("POST", "/game", gameInfo, gameInfo.authToken());
        var response = sendRequest(request);
        return handleResponse(response, GameData.class);
    }


    //join game
    //javalin.put("/game", handler::joinGame);
    public GameData joinFacade(JoinRequest gameInfo, String authTok) {
        var request = buildRequest("put", "/game", gameInfo, authTok);
        var response = sendRequest(request);
        return handleResponse(response, GameData.class);
    }


    //clear is called before every test; might not need in client side
    //javalin.delete("/db", handler::clear);

    public HttpRequest buildRequest(String method, String path, Object body, String token) {
        var request = HttpRequest.newBuilder()
                .uri(URI.create(serverUrl + path))
                .method(method, makeRequestBody(body));
        if (body != null) {
            request.setHeader("Content-Type", "application/json");
        }
        if (token != null){
            request.setHeader("authorization", token);
        }

        return request.build();
    }

    private HttpRequest.BodyPublisher makeRequestBody(Object request) {
        if (request != null) {
            return HttpRequest.BodyPublishers.ofString(new Gson().toJson(request));
        } else {
            return HttpRequest.BodyPublishers.noBody();
        }
    }

    public HttpResponse<String> sendRequest(HttpRequest request) {
        try {
            return client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }


    private <T> T handleResponse(HttpResponse<String> response, Class<T> responseClass) {
        int status = response.statusCode();
        if (!isSuccessful(status)) {
            var body = response.body();
            if (body != null) {
                throw new RuntimeException(String.format("%s\n", body));
            }
            throw new RuntimeException(String.format("Error: unknown error\n"));
        }
        if (responseClass != null) {
            return new Gson().fromJson(response.body(), responseClass);
        }
        return null;
    }
    private boolean isSuccessful(int status) {
        return (status / 100) == 2;
    }
}
