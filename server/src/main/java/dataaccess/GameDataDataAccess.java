package dataaccess;

import chess.ChessGame;
import com.google.gson.Gson;
import exceptions.DataAccessException;
import exceptions.FailedToConnectException;
import model.GameData;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class GameDataDataAccess implements DataAccessGames{

    @Override
    public Collection<GameData> listGame() throws FailedToConnectException {
        Collection<GameData> gameList = new ArrayList<>();
        try (var conn = DatabaseManager.getConnection()) {
            String statement = "SELECT * FROM games";
            try (var preparedStatement = conn.prepareStatement(statement)) {
                try(var dbData = preparedStatement.executeQuery()){
                    while (dbData.next()){
                        var gameIdDB = dbData.getInt("gameId");
                        var whiteUser = dbData.getString("whiteUser");
                        var blackUser = dbData.getString("blackUser");
                        var gameName = dbData.getString("gameName");
                        var chessGame = new Gson().fromJson(dbData.getString("game"), ChessGame.class);

                        gameList.add(new GameData(gameIdDB, whiteUser, blackUser,gameName, chessGame));
                    }
                }
            }
            return gameList;
        } catch (SQLException | DataAccessException e) {
            throw new FailedToConnectException("failed to connect");
        }
    }


    @Override
    public GameData giveGame(int gameId) throws FailedToConnectException {
        try (var conn = DatabaseManager.getConnection()) {
            String statement = "SELECT * FROM games WHERE gameID =?";
                try (var preparedStatement = conn.prepareStatement(statement)) {
                    preparedStatement.setString(1, String.valueOf(gameId));
                    try(var dbData = preparedStatement.executeQuery()){
                        if(dbData.next()){
                            var gameIdDB = dbData.getInt("gameId");
                            var whiteUser = dbData.getString("whiteUser");
                            var blackUser = dbData.getString("blackUser");
                            var gameName = dbData.getString("gameName");
                            var chessGame = new Gson().fromJson(dbData.getString("game"), ChessGame.class);

                            return new GameData(gameIdDB, whiteUser, blackUser,gameName,chessGame);
                        }
                    }
                }
        } catch (SQLException | DataAccessException e) {
            throw new FailedToConnectException("failed to connect");
        }
        return null;
    }

    public GameData giveGameByName(String gameName) throws FailedToConnectException {
        try (var conn = DatabaseManager.getConnection()) {
            String statement = "SELECT * FROM games WHERE gameName =?";
            try (var preparedStatement = conn.prepareStatement(statement)) {
                preparedStatement.setString(1, String.valueOf(gameName));
                try(var dbData = preparedStatement.executeQuery()){
                    if(dbData.next()){
                        var gameIdDB = dbData.getInt("gameId");
                        var whiteUser = dbData.getString("whiteUser");
                        var blackUser = dbData.getString("blackUser");
                        var gameNamedB = dbData.getString("gameName");
                        var chessGame = new Gson().fromJson(dbData.getString("game"), ChessGame.class);

                        return new GameData(gameIdDB, whiteUser, blackUser, gameNamedB,chessGame);
                    }
                }
            }
        } catch (SQLException | DataAccessException e) {
            throw new FailedToConnectException("failed to connect");
        }
        return null;
    }


    @Override
    public int addGame(String gameName) throws FailedToConnectException {
        try (var conn = DatabaseManager.getConnection()) {
            String json = new Gson().toJson(new ChessGame());
            String statement = "INSERT INTO games (gameName, game) VALUES (?, ?)";
            try (var preparedStatement = conn.prepareStatement(statement)) {
                preparedStatement.setString(1, gameName);
                preparedStatement.setString(2, json);

                preparedStatement.executeUpdate();
                return giveGameByName(gameName).gameID();
            }

        } catch (SQLException | DataAccessException e) {
            throw new FailedToConnectException("failed to connect");
        }
    }


    @Override
    public void joinGame(ChessGame.TeamColor teamColor, String username, int gameId) throws FailedToConnectException {
        //update
        try (var conn = DatabaseManager.getConnection()) {

            if (teamColor == ChessGame.TeamColor.WHITE){
                String statementWhite = "UPDATE games SET whiteUser = ? WHERE gameID =?";
                try (var preparedStatement = conn.prepareStatement(statementWhite)) {
                    preparedStatement.setString(1, username);
                    preparedStatement.setString(2, String.valueOf(gameId));

                    preparedStatement.executeUpdate();
                }
            }

            if (teamColor == ChessGame.TeamColor.BLACK){
                String statementBlack = "UPDATE games SET blackUser = ? WHERE gameID =?";
                try (var preparedStatement = conn.prepareStatement(statementBlack)) {
                    preparedStatement.setString(1, username);
                    preparedStatement.setString(2, String.valueOf(gameId));

                    preparedStatement.executeUpdate();
                }
            }

        } catch (SQLException | DataAccessException e) {
            throw new FailedToConnectException("failed to connect");
        }
    }


    @Override
    public void deleteGames() throws FailedToConnectException {
        try(var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("TRUNCATE games")) {
                preparedStatement.executeUpdate();
            }
        }
        catch (SQLException | DataAccessException e) {
            throw new FailedToConnectException("failed to connect");
        }
    }
}
