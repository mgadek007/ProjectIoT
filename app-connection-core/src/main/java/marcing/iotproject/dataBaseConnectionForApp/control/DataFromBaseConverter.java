package marcing.iotproject.dataBaseConnectionForApp.control;

import marcing.iotproject.appConnectionServlet.entity.DataBlock;
import marcing.iotproject.errors.ConvertError;
import marcing.iotproject.userLoginServlet.entity.AttributesDictionary;
import marcing.iotproject.appConnectionServlet.entity.AttributesDictionaryForDataBlock;
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
                user.setTemp(result.getString(AttributesDictionary.TEMP_DB));
            }

        }catch (SQLException e){
            throw new ConvertError(PROBLEM_WITH_CONVERT);
        }
        return user;
    }

    public DataBlock convertToDataBlock(ResultSet result){
        DataBlock dataBlock = new DataBlock();
        try {
            while (result.next()){
                dataBlock.setAirQuaIn(AttributesDictionaryForDataBlock.AIR_AUA_IN);
                dataBlock.setAirQuaOut(AttributesDictionaryForDataBlock.AIR_QUA_OUT);
                dataBlock.setBlue(AttributesDictionaryForDataBlock.BLUE);
                dataBlock.setGreen(AttributesDictionaryForDataBlock.GREEN);
                dataBlock.setRed(AttributesDictionaryForDataBlock.RED);
                dataBlock.setLightIn(AttributesDictionaryForDataBlock.LIGHT_INT);
                dataBlock.setTempIn(AttributesDictionaryForDataBlock.TEMP_IN);
                dataBlock.setTempOut(AttributesDictionaryForDataBlock.TEMP_OUT);
                dataBlock.setPeopleInside(AttributesDictionaryForDataBlock.PEOPLE_INSIDE);
                dataBlock.setSoundDetected(AttributesDictionaryForDataBlock.SOUND_DETECTED);
                dataBlock.setTimeStamp(AttributesDictionaryForDataBlock.TIME_STAMP);
            }

        }catch (SQLException e){
            throw new ConvertError(PROBLEM_WITH_CONVERT);
        }
        return dataBlock;

    }
}
