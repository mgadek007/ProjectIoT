package marcing.iotproject.dataBaseConnection.entity;

import com.google.common.base.Strings;
import marcing.iotproject.AttributesDictionary;
import marcing.iotproject.DataBlock;



public class DataPreparator {

    private final String COMMA_SEPARATOR = ", ";


    public MessageDuet convertDataBlock(DataBlock dataBlock){
        MessageDuet result = new MessageDuet();
        return prepareResult(dataBlock, result);
    }

    private MessageDuet prepareResult(DataBlock dataBlock, MessageDuet result) {
        writeToResult(AttributesDictionary.TIME_STAMP, dataBlock.getTimestamp(), result);
        writeToResult(AttributesDictionary.TEMP_IN, dataBlock.getTemp_in(), result);
        writeToResult(AttributesDictionary.TEMP_OUT, dataBlock.getTemp_out(), result);
        writeToResult(AttributesDictionary.LIGHT_INT, dataBlock.getLight_in(), result);
        writeToResult(AttributesDictionary.RED, dataBlock.getRed(), result);
        writeToResult(AttributesDictionary.BLUE, dataBlock.getBlue(), result);
        writeToResult(AttributesDictionary.GREEN, dataBlock.getGreen(), result);
        writeToResult(AttributesDictionary.AIR_AUA_IN, dataBlock.getAir_qua_in(), result);
        writeToResult(AttributesDictionary.AIR_QUA_OUT, dataBlock.getAir_qua_out(), result);
        writeToResult(AttributesDictionary.SOUND_DETECTED, dataBlock.isSound_detected(), result);
        writeToResult(AttributesDictionary.PEOPLE_INSIGHR, dataBlock.getPeople_inside(), result);

        return result;
    }

    private void writeToResult(String name, String value, MessageDuet result){
        String columnName = result.getColumnName();
        String columnValue = result.getColumnValue();
        if (!Strings.isNullOrEmpty(value)){
            if (!Strings.isNullOrEmpty(columnName)){
                columnName = columnName +COMMA_SEPARATOR+ name;
                columnValue = columnValue + COMMA_SEPARATOR +value;
            }else{
                columnName = name;
                columnValue = value;
            }
        }
        result.setColumnName(columnName);
        result.setColumnValue(columnValue);
    }

}
