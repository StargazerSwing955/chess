package dataaccess;

import com.google.gson.Gson;
import model.UserData;

import java.sql.SQLException;

public class UserDataDataAccess implements DataAccessUsers{
    //email, username, password
    //remember to hash password!!!! <- where

    @Override
    public void addUser(UserData dataToAdd) throws FailedToConnectException {
        //insert
        try(var conn = DatabaseManager.getConnection()){
            try (var preparedStatement = conn.prepareStatement("INSERT INTO users (email, username, password, json) VALUES (?, ?, ?)")) {
                preparedStatement.setString(1, dataToAdd.email());
                preparedStatement.setString(2, dataToAdd.username());
                //where to hash password
                preparedStatement.setString(3, dataToAdd.password());

                preparedStatement.executeUpdate();

                String json = new Gson().toJson(dataToAdd);
            }

        } catch (SQLException | DataAccessException e) {
            throw new FailedToConnectException("failed to connect");
        }


    }

    @Override
    public UserData findUser(UserData dataToFind) {
        //select
        return null;
    }

    @Override
    public void clearUsers() {
        //drop

    }
}
