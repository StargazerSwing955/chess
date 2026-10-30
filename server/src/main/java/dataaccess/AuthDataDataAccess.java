package dataaccess;

import model.AuthData;

import java.sql.SQLException;

public class AuthDataDataAccess implements DataAccessAuth{

    @Override
    public void addAuthData(AuthData dataToAdd) {
        //insert
    }

    @Override
    public AuthData getAuthData(String dataToFind) {
        //select
        return null;
    }

    @Override
    public void removeAuthData(AuthData dataToRemove) {
        //delete
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
