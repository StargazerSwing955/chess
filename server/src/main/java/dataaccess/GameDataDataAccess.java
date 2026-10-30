package dataaccess;

import chess.ChessGame;
import com.google.gson.Gson;
import model.GameData;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public class GameDataDataAccess implements DataAccessGames{
    @Override
    public Collection<GameData> listGame() {
        //select
        return List.of();
    }

    @Override
    public GameData giveGame(int gameId) {
        //select
        return null;
    }

    @Override
    public int addGame(String gameName) throws FailedToConnectException {
        //insert
        try (var conn = DatabaseManager.getConnection()) {
            var json = new Gson().toJson(new ChessGame());

        } catch (SQLException | DataAccessException e) {
            throw new FailedToConnectException("failed to connect");
        }


        return 0;
    }

    @Override
    public void joinGame(ChessGame.TeamColor teamColor, String username, int gameId) {
        //alter


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
