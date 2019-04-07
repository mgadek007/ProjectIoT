package marcing.iotproject.dataBaseConnection.control;

import marcing.iotproject.DataBlock;
import marcing.iotproject.dataBaseConnection.entity.DataPreparator;
import marcing.iotproject.dataBaseConnection.entity.MessageDuet;

import java.text.MessageFormat;

public class DatabaseConverter {

    private DataPreparator dataPreparator = new DataPreparator();

    private final String INSERT_COMMAND = "INSERT INTO {0} ({1}) VALUES ({2})";

    public String prepareMessageForDataBase(DataBlock dataBlock) {
        String tableName = String.valueOf(dataBlock.getId());
        MessageDuet messageDuet = dataPreparator.convertDataBlock(dataBlock);
        return prepareMessage(tableName, messageDuet.getColumnName(), messageDuet.getColumnValue());
    }

    private String prepareMessage(String tableName, String columnName, String columnValue) {
        return MessageFormat.format(INSERT_COMMAND, tableName, columnName, columnValue);
    }


}
