package marcing.iotproject.dataBaseConnection.entity;

class QueryPreparatoResult {

    private String columnName;
    private String columnValue;

    String getColumnName() {
        return columnName;
    }

    void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    String getColumnValue() {
        return columnValue;
    }

    void setColumnValue(String columnValue) {
        this.columnValue = columnValue;
    }
}
