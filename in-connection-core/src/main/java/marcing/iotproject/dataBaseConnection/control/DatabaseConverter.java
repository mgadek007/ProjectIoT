package marcing.iotproject.dataBaseConnection.control;

import marcing.iotproject.basicElements.DataBlock;
import marcing.iotproject.dataBaseConnection.entity.ColumnAndValueDataPreparator;
import marcing.iotproject.dataBaseConnection.entity.DataPreparator;

import java.text.MessageFormat;

public class DatabaseConverter {

    private DataPreparator dataPreparator = new DataPreparator();

    private static final String INSERT_COMMAND = "INSERT INTO {0} ({1}) VALUES ({2})";

    public String prepareMessageForDataBase(DataBlock dataBlock) {
        String tableName = String.valueOf(dataBlock.getId());
        ColumnAndValueDataPreparator columnAndValueDataPreparator = dataPreparator.convertDataBlock(dataBlock);
        return prepareMessage(tableName, columnAndValueDataPreparator.getColumnName(), columnAndValueDataPreparator.getColumnValue());
    }

    private String prepareMessage(String tableName, String columnName, String columnValue) {
        return MessageFormat.format(INSERT_COMMAND, tableName, columnName, columnValue);
    }


}
