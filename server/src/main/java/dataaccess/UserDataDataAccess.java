package dataaccess;

import com.google.gson.Gson;
import model.UserData;

import java.sql.SQLException;

public class UserDataDataAccess implements DataAccessUsers{

    @Override
    public void addUser(UserData dataToAdd) throws FailedToConnectException {
        try(var conn = DatabaseManager.getConnection()){
            try (var preparedStatement = conn.prepareStatement("INSERT INTO users (email, username, password) VALUES (?, ?, ?)")) {
                preparedStatement.setString(1, dataToAdd.email());
                preparedStatement.setString(2, dataToAdd.username());
                preparedStatement.setString(3, dataToAdd.password());

                preparedStatement.executeUpdate();
            }

        } catch (SQLException | DataAccessException e) {
            throw new FailedToConnectException("failed to connect");
        }


    }

    @Override
    public UserData findUser(UserData dataToFind) throws FailedToConnectException {
        //select
        try(var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("SELECT (username, password) ")) {


            }
        }
        catch (SQLException | DataAccessException e) {
            throw new FailedToConnectException("failed to connect");
        }
        return null;
    }

    @Override
    public void clearUsers() throws FailedToConnectException {
        try(var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("TRUNCATE users")) {
                preparedStatement.executeUpdate();
            }
        }
        catch (SQLException | DataAccessException e) {
            throw new FailedToConnectException("failed to connect");
        }
    }
}
