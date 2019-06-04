package marcing.iotproject.dataBaseConnection.entity;

import com.google.common.base.Strings;
import marcing.iotproject.basicElements.AttributesDictionary;
import marcing.iotproject.basicElements.DataBlock;


public class DataPreparator {

    private static final String COMMA_SEPARATOR = ", ";
    private static final String QUESTION_MARK = "?";


    public ColumnAndValueDataPreparator convertDataBlock(DataBlock dataBlock){
        ColumnAndValueDataPreparator result = new ColumnAndValueDataPreparator();
        return prepareResult(dataBlock, result);
    }

    private ColumnAndValueDataPreparator prepareResult(DataBlock dataBlock, ColumnAndValueDataPreparator result) {
        writeToResult(AttributesDictionary.TIME_STAMP, QUESTION_MARK, result);
        writeToResult(AttributesDictionary.TEMP_IN, dataBlock.getTempIn(), result);
        writeToResult(AttributesDictionary.TEMP_OUT, dataBlock.getTempOut(), result);
        writeToResult(AttributesDictionary.LIGHT_INT, dataBlock.getLightIn(), result);
        writeToResult(AttributesDictionary.IS_CLIME_ON, dataBlock.getIsClimeOn(), result);
        writeToResult(AttributesDictionary.IS_WINDOW_OPEN, dataBlock.getIsWindowOpen(), result);
        writeToResult(AttributesDictionary.AIR_AUA_IN, dataBlock.getAirQuaIn(), result);
        writeToResult(AttributesDictionary.AIR_QUA_OUT, dataBlock.getAirQuaOut(), result);
        writeToResult(AttributesDictionary.SOUND_DETECTED, dataBlock.isSoundDetected(), result);
        writeToResult(AttributesDictionary.PEOPLE_INSIDE, dataBlock.getPeopleInside(), result);

        return result;
    }

    private void writeToResult(String name, String value, ColumnAndValueDataPreparator result){
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
