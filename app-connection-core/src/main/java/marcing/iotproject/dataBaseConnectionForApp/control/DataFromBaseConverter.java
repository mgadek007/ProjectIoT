package marcing.iotproject.dataBaseConnectionForApp.control;

import marcing.iotproject.errors.ConvertError;
import marcing.iotproject.userLoginServlet.entity.AttributesDictionary;
import marcing.iotproject.userLoginServlet.entity.UserLoginDTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DataFromBaseConverter {

    private static final String PROBLEM_WITH_CONVERT = "Problem with convert table to DTO";

    public UserLoginDTO convertToUserLoginDTO(ResultSet result){
        UserLoginDTO user = new UserLoginDTO();
        try {
            while (result.next()){
                user.setUser(result.getString(AttributesDictionary.USER_DB));
                user.setPassword(result.getString(AttributesDictionary.PASSWORD_DB));
                user.setToken(result.getString(AttributesDictionary.TOKEN_DB));
            }

        }catch (SQLException e){
            throw new ConvertError(PROBLEM_WITH_CONVERT);
        }


        return user;
    }
}
