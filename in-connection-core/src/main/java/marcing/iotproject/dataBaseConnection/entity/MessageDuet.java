package marcing.iotproject.dataBaseConnection.entity;

public class MessageDuet {

    private String columnName;
    private String columnValue;

    MessageDuet(String columnName, String columnValue){
        this.columnName = columnName;
        this.columnValue =columnValue;
    }

    MessageDuet(){}

    public String getColumnName() {
        return columnName;
    }

    void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnValue() {
        return columnValue;
    }

    void setColumnValue(String columnValue) {
        this.columnValue = columnValue;
    }
}
