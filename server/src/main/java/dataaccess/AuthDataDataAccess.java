package dataaccess;

import model.AuthData;
import model.UserData;

import java.sql.SQLException;

public class AuthDataDataAccess implements DataAccessAuth{

    @Override
    public void addAuthData(AuthData dataToAdd) throws FailedToConnectException {
        //insert
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("INSERT INTO auth (authToken, username) VALUES (?, ?)")) {
                preparedStatement.setString(1, dataToAdd.authToken());
                preparedStatement.setString(2, dataToAdd.username());

                preparedStatement.executeUpdate();
            }

        } catch (SQLException | DataAccessException e) {
            throw new FailedToConnectException("failed to connect");
        }
    }

    @Override
    public AuthData getAuthData(String dataToFind) throws FailedToConnectException {
        //select
        String statement = "SELECT * FROM auth WHERE username =? OR authToken =?";
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement(statement)) {
                preparedStatement.setString(1, dataToFind);
                preparedStatement.setString(2, dataToFind);
                try(var dbData = preparedStatement.executeQuery()){
                    if(dbData.next()){
                        var userName = dbData.getString("username");
                        var authToken = dbData.getString("authToken");
                        return new AuthData(authToken,userName);
                    }
                }
            }

        } catch (SQLException | DataAccessException e) {
            throw new FailedToConnectException("failed to connect");
        }
        return null;
    }

    @Override
    public void removeAuthData(AuthData dataToRemove) throws FailedToConnectException {
        //delete
        String statement = "DELETE FROM auth WHERE authToken =?";
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement(statement)) {
                preparedStatement.setString(1, dataToRemove.authToken());

                preparedStatement.executeUpdate();
            }

        } catch (SQLException | DataAccessException e) {
            throw new FailedToConnectException("failed to connect");
        }
    }

    @Override
    public void removeAll() throws FailedToConnectException {
        try(var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("TRUNCATE auth")) {
                preparedStatement.executeUpdate();
            }
        }
        catch (SQLException | DataAccessException e) {
            throw new FailedToConnectException("failed to connect");
        }
    }
}
