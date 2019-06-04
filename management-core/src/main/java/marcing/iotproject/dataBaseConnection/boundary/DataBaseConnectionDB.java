package marcing.iotproject.dataBaseConnection.boundary;

import marcing.iotproject.dataBaseConnection.control.DataBaseConn;
import marcing.iotproject.dataBaseConnection.entity.QueryPreparator;
import marcing.iotproject.dataPreparator.entity.DataBlock;
import marcing.iotproject.errors.ConnectionErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.List;

public class DataBaseConnectionDB {

    private final Logger log = LoggerFactory.getLogger(DataBaseConnectionDB.class);
    private DataBaseConn dataBaseConn = new DataBaseConn();
    private QueryPreparator queryPreparator = new QueryPreparator();

    private static final String IN_ROOM = "inRoom{0}";
    private static final String OUT_ROOM = "outRoom{0}";
    private static final String SELECT_ATTR_BY_NAME = "SELECT {1} FROM {0} WHERE {1} is not null ORDER BY Timestamp DESC limit {2};";
    private static final String PROBLEM_WITH_CONNECTION = "Problem with connection to DB";
    private static final String T_CHAR = "T";
    private static final String SPACE_CHAR = " ";
    private static final String ONE_VALUE = "1";
    private static final String LIST_VALUES = "5";


    public List<String> getLastAttributeListValues(String id, String attrName) {
        String tableName = prepareQuery(IN_ROOM, id);
        String query = prepareQuery(tableName, attrName, LIST_VALUES);
        return dataBaseConn.getLastTemp(query, attrName);
    }

    private String prepareQuery(String tablemName, String attrName, String numberOfAttr) {
        return MessageFormat.format(SELECT_ATTR_BY_NAME, tablemName, attrName, numberOfAttr);
    }

    private String prepareQuery(String queryFormat, String tablemName) {
        return MessageFormat.format(queryFormat, tablemName);
    }

    public String getLastValueAttribute(String id, String attrName, boolean isOutRoom){
        String tableName;
        if (isOutRoom){
            tableName = prepareQuery(OUT_ROOM, id);
        }else {
            tableName = prepareQuery(IN_ROOM, id);
        }
        String query = prepareQuery(tableName, attrName, ONE_VALUE);
        return dataBaseConn.getLastValueAttribute(query, attrName);
    }

    public void loadToFinalDB(DataBlock dataBlock){
        String message = queryPreparator.prepareMessageForDataBase(dataBlock);
        Timestamp timeStamp = prepareTimeStamp();
        try {
            dataBaseConn.loadToDatabase(message, timeStamp);
        }catch (SQLException e){
            log.error(PROBLEM_WITH_CONNECTION);
            log.error(e.toString());
            throw new ConnectionErrorException(PROBLEM_WITH_CONNECTION);
        }
    }

    private Timestamp prepareTimeStamp() {
        LocalDateTime now = LocalDateTime.now();
        String time = now.toString().replace(T_CHAR, SPACE_CHAR);
        return Timestamp.valueOf(time);
    }
}
