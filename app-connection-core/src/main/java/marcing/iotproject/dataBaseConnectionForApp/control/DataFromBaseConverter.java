package marcing.iotproject.dataBaseConnectionForApp.control;

import marcing.iotproject.appConnectionServlet.entity.DataBlock;
import marcing.iotproject.errors.ConvertError;
import marcing.iotproject.roomLoginServlet.entity.RoomLoginDTO;
import marcing.iotproject.roomLoginServlet.entity.RoomsDictionary;
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
                dataBlock.setAirQuaIn(result.getString(AttributesDictionaryForDataBlock.AIR_AUA_IN));
                dataBlock.setAirQuaOut(result.getString(AttributesDictionaryForDataBlock.AIR_QUA_OUT));
                dataBlock.setLightIn(result.getString(AttributesDictionaryForDataBlock.LIGHT_INT));
                dataBlock.setTempIn(result.getString(AttributesDictionaryForDataBlock.TEMP_IN));
                dataBlock.setTempOut(result.getString(AttributesDictionaryForDataBlock.TEMP_OUT));
                dataBlock.setPeopleInside(result.getString(AttributesDictionaryForDataBlock.PEOPLE_INSIDE));
                dataBlock.setSoundDetected(result.getString(AttributesDictionaryForDataBlock.SOUND_DETECTED));
                dataBlock.setTimeStamp(result.getString(AttributesDictionaryForDataBlock.TIME_STAMP));
                dataBlock.setIsClimeOn(result.getString(AttributesDictionaryForDataBlock.IS_CLIME_ON));
                dataBlock.setIsWindowOpen(result.getString(AttributesDictionaryForDataBlock.IS_WINDOW_OPEN));
            }

        }catch (SQLException e){
            throw new ConvertError(PROBLEM_WITH_CONVERT);
        }
        return dataBlock;

    }

    public RoomLoginDTO convertToRoomDTO(ResultSet result) {
        RoomLoginDTO room = new RoomLoginDTO();
        try {
            while (result.next()){
                room.setIdRoom(result.getString(RoomsDictionary.ID_ROOM_DB));
                room.setPassword(result.getString(RoomsDictionary.PASS_DB));
            }

        }catch (SQLException e){
            throw new ConvertError(PROBLEM_WITH_CONVERT);
        }
        return room;
    }
}
