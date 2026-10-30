package dataaccess;

import model.AuthData;

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
        try (var conn = DatabaseManager.getConnection()) {


        } catch (SQLException | DataAccessException e) {
            throw new FailedToConnectException("failed to connect");
        }
        return null;
    }

    @Override
    public void removeAuthData(AuthData dataToRemove) throws FailedToConnectException {
        //delete
        try (var conn = DatabaseManager.getConnection()) {


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
