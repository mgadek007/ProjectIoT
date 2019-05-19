package marcing.iotproject.dataBaseConnection.entity;

import com.google.common.base.Strings;
import marcing.iotproject.dataPreparator.entity.DataBlock;
import marcing.iotproject.dataPreparator.entity.DataBlockDictionary;

import java.text.MessageFormat;

public class QueryPreparator {

    private static final String COMMA_SEPARATOR = ", ";
    private static final String QUESTION_MARK = "?";
    private static final String OUT_SUFFIX = "outRoom{0}";
    private static final String INSERT_COMMAND = "INSERT INTO {0} ({1}) VALUES ({2})";



    public String prepareMessageForDataBase(DataBlock dataBlock) {
        String tableName = prepareTableName(dataBlock.getId());
        QueryPreparatoResult columnAndValueDataPreparator = convertDataBlock(dataBlock);
        return prepareMessage(tableName, columnAndValueDataPreparator.getColumnName(), columnAndValueDataPreparator.getColumnValue());
    }

    private String prepareTableName(String id){
        return MessageFormat.format(OUT_SUFFIX, id);
    }


    private QueryPreparatoResult convertDataBlock(DataBlock dataBlock){
        QueryPreparatoResult result = new QueryPreparatoResult();
        return prepareResult(dataBlock, result);
    }

    private QueryPreparatoResult prepareResult(DataBlock dataBlock, QueryPreparatoResult result) {
        writeToResult(DataBlockDictionary.TIME_STAMP, QUESTION_MARK, result);
        writeToResult(DataBlockDictionary.TEMP_IN, dataBlock.getTempIn(), result);
        writeToResult(DataBlockDictionary.TEMP_OUT, dataBlock.getTempOut(), result);
        writeToResult(DataBlockDictionary.LIGHT_INT, dataBlock.getLightIn(), result);
        writeToResult(DataBlockDictionary.RED, dataBlock.getRed(), result);
        writeToResult(DataBlockDictionary.BLUE, dataBlock.getBlue(), result);
        writeToResult(DataBlockDictionary.GREEN, dataBlock.getGreen(), result);
        writeToResult(DataBlockDictionary.AIR_AUA_IN, dataBlock.getAirQuaIn(), result);
        writeToResult(DataBlockDictionary.AIR_QUA_OUT, dataBlock.getAirQuaOut(), result);
        writeToResult(DataBlockDictionary.SOUND_DETECTED, dataBlock.isSoundDetected(), result);
        writeToResult(DataBlockDictionary.PEOPLE_INSIDE, dataBlock.getPeopleInside(), result);

        return result;
    }

    private void writeToResult(String name, String value, QueryPreparatoResult result){
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

    private String prepareMessage(String tableName, String columnName, String columnValue) {
        return MessageFormat.format(INSERT_COMMAND, tableName, columnName, columnValue);
    }
}
