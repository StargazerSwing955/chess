package serverfacade;

import com.google.gson.Gson;
import model.AuthData;
import model.CreateGameRequest;
import model.GameData;
import model.UserData;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

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
    public AuthData registerFacade(UserData userData) throws IOException, InterruptedException {
        var request = buildRequest("POST", "/user", userData);
        var response = sendRequest(request);
        var handledResponse = handleResponse(response, AuthData.class);

        return handledResponse;
    }


    //login
    //javalin.post("/session",handler::loginUser);
    public AuthData loginFacade(UserData userData) {
        var request = buildRequest("POST", "/session", userData);
        var response = sendRequest(request);
        var handledResponse = handleResponse(response, AuthData.class);

        return handledResponse;
    }

    //logout
    //javalin.delete("/session", handler::logoutUser);
    public void logoutFacade(AuthData authTok ) {
        var request = buildRequest("DELETE", "/session", authTok);
        var response = sendRequest(request);
    }

    //get games
    //javalin.get("/game", handler::getGameList);
    public List<GameData> gameListFacade(AuthData userData) {
        var request = buildRequest("GET", "/game", userData);
        var response = sendRequest(request);
        return handleResponse(response, List.class);

    }


    //make game
    //javalin.post("/game", handler::createGame);
    public GameData createGameFacade(CreateGameRequest gameInfo) {
        var request = buildRequest("POST", "/game", gameInfo);
        var response = sendRequest(request);
        return handleResponse(response, GameData.class);
    }


    //join game
    //javalin.put("/game", handler::joinGame);
    public GameData joinFacade(List<String> gameInfo) {
        var request = buildRequest("put", "/game", gameInfo);
        var response = sendRequest(request);
        return handleResponse(response, GameData.class);
    }


    //clear is called before every test; might not need in client side
    //javalin.delete("/db", handler::clear);

    private HttpRequest buildRequest(String method, String path, Object body) {
        var request = HttpRequest.newBuilder()
                .uri(URI.create(serverUrl + path))
                .method(method, makeRequestBody(body));
        if (body != null) {
            request.setHeader("Content-Type", "application/json");
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

    private HttpResponse<String> sendRequest(HttpRequest request) {
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
                throw new RuntimeException(String.format("%d, %s", status, body));
            }
            throw new RuntimeException(String.format("%d, Error: unknown error", status));
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
