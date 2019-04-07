package marcing.iotproject.dataBaseConnection.entity;

public class ColumnAndValueDataPreparator {

    private String columnName;
    private String columnValue;

    ColumnAndValueDataPreparator(){}

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
