package dataaccess;

import chess.ChessGame;
import com.google.gson.Gson;
import model.GameData;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class GameDataDataAccess implements DataAccessGames{

    @Override
    public Collection<GameData> listGame() throws FailedToConnectException {
        Collection<GameData> gameList = new ArrayList<GameData>();
        try (var conn = DatabaseManager.getConnection()) {
            String statement = "SELECT * FROM game";
            try (var preparedStatement = conn.prepareStatement(statement)) {
                try(var dbData = preparedStatement.executeQuery()){
                    while (dbData.next()){
                        var gameIdDB = dbData.getInt("gameId");
                        var whiteUser = dbData.getString("whiteUser");
                        var blackUser = dbData.getString("blackUser");
                        var gameName = dbData.getString("gameName");
                        gameList.add(new GameData(gameIdDB, whiteUser, blackUser,gameName,null));
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
            String statement = "SELECT * FROM game WHERE gameID =?";
                try (var preparedStatement = conn.prepareStatement(statement)) {
                    preparedStatement.setString(1, String.valueOf(gameId));
                    try(var dbData = preparedStatement.executeQuery()){
                        if(dbData.next()){
                            var gameIdDB = dbData.getInt("gameId");
                            var whiteUser = dbData.getString("whiteUser");
                            var blackUser = dbData.getString("blackUser");
                            var gameName = dbData.getString("gameName");
                            return new GameData(gameIdDB, whiteUser, blackUser,gameName,null);
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
            String statement = "SELECT * FROM game WHERE gameName =?";
            try (var preparedStatement = conn.prepareStatement(statement)) {
                preparedStatement.setString(1, String.valueOf(gameName));
                try(var dbData = preparedStatement.executeQuery()){
                    if(dbData.next()){
                        var gameIdDB = dbData.getInt("gameId");
                        var whiteUser = dbData.getString("whiteUser");
                        var blackUser = dbData.getString("blackUser");
                        var gameNamedB = dbData.getString("gameName");
                        return new GameData(gameIdDB, whiteUser, blackUser, gameNamedB,null);
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
            var json = new Gson().toJson(new ChessGame());
            try (var preparedStatement = conn.prepareStatement("INSERT INTO games (whiteUser, blackUser,gameName, game) VALUES (?, ?, ?, ?)")) {
                preparedStatement.setString(1, null);
                preparedStatement.setString(2, null);
                preparedStatement.setString(3, gameName);
                preparedStatement.setString(4, json);

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
            String statement = "UPDATE ? FROM game WHERE gameID =?";

            if (teamColor == ChessGame.TeamColor.WHITE){
                try (var preparedStatement = conn.prepareStatement(statement)) {
                    preparedStatement.setString(1, "whiteUser");
                    preparedStatement.setString(2, username);
                    preparedStatement.executeUpdate();
                }
            }

            if (teamColor == ChessGame.TeamColor.BLACK){
                try (var preparedStatement = conn.prepareStatement(statement)) {
                    preparedStatement.setString(1, "blackUser");
                    preparedStatement.setString(2, username);
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
