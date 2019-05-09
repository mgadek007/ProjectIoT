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
    private static final String SELECT_TEMP = "SELECT {1} FROM {0} ORDER BY Timestamp DESC limit 5;";
    private static final String PROBLEM_WITH_CONNECTION = "porblem wit connection to DB";
    private static final String T_CHAR = "T";
    private static final String SPACE_CHAR = " ";


    public List<String> getLastAttributeListValues(String id, String attrName) {
        String tableName = prepareQuery(IN_ROOM, id);
        String query = prepareQuery(SELECT_TEMP, tableName, attrName);
        return dataBaseConn.getLastTemp(query, attrName);
    }

    private String prepareQuery(String queryFormat, String tablemName, String attrName) {
        return MessageFormat.format(queryFormat, tablemName, attrName);
    }

    private String prepareQuery(String queryFormat, String tablemName) {
        return MessageFormat.format(queryFormat, tablemName);
    }

    public String getLastValueAttribut(String id, String attrName){
        String tableName = prepareQuery(IN_ROOM, id);
        String query = prepareQuery(SELECT_TEMP, tableName, attrName);
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
