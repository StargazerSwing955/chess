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
        String statement = "SELECT * FROM users WHERE username =?";
        try(var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement(statement)) {
                preparedStatement.setString(1, dataToFind.username());
                try(var dbData = preparedStatement.executeQuery()){
                    if(dbData.next()){
                        var userName = dbData.getString("username");
                        var passHash = dbData.getString("password");
                        var email = dbData.getString("email");
                        return new UserData(userName, passHash, email);
                    }
                }
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
