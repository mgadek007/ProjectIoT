package marcing.iotproject.dataBaseConnection.boundary;

import com.google.common.base.Strings;
import marcing.iotproject.basicElements.DataBlock;
import marcing.iotproject.dataBaseConnection.control.DatabaseConverter;
import marcing.iotproject.errors.DatabaseConnectionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDateTime;
import javax.servlet.http.HttpServlet;
import java.sql.*;


public class DataBaseConnection extends HttpServlet {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private static final String PROBLEM_WITH_CONNECTION = " Problem with connection to Database";
    private static final String T_CHAR = "T";
    private static final String SPACE_CHAR = " ";

    private Connection conn;
    private PreparedStatement preparedStatement;

    private DatabaseConverter databaseConverter = new DatabaseConverter();

    public void init(){
        try {
            String dbIpAddr = "localhost";
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            conn = DriverManager.getConnection("jdbc:mysql://"+dbIpAddr+":3306/sys?serverTimezone=Europe/Warsaw", "root", "root123!");

        } catch (SQLException e) {
            log.error(PROBLEM_WITH_CONNECTION);
            log.error(e.toString());
            throw new DatabaseConnectionException(PROBLEM_WITH_CONNECTION);
        }
    }


    public void loadDataToDataBase(DataBlock dataBlock)   {
        String message = databaseConverter.prepareMessageForDataBase(dataBlock);
        Timestamp timeStamp = prepareTimeStamp(dataBlock.getTimeStamp());
        init();
        try {
            loadToDatabase(message, timeStamp);
        }catch (SQLException e){
            log.error(PROBLEM_WITH_CONNECTION);
            log.error(e.toString());
            throw new DatabaseConnectionException(PROBLEM_WITH_CONNECTION);
        }
    }

    private Timestamp prepareTimeStamp(String timestamp) {
        String time;
        if (!Strings.isNullOrEmpty(timestamp)){
            time = timestamp.replace(T_CHAR, SPACE_CHAR);
        } else{
            LocalDateTime now = LocalDateTime.now();
            time = now.toString().replace(T_CHAR, SPACE_CHAR);

        }
        return Timestamp.valueOf(time);
    }

    private void loadToDatabase(String message, Timestamp timeStamp) throws SQLException {
        try{
            log.info("**** LOAD TO DATABASE ****");
            log.info(message + timeStamp);
            executeUpdate(message, timeStamp);

        }catch(SQLException | NullPointerException e) {
            log.error(PROBLEM_WITH_CONNECTION);
            log.error(e.toString());
            throw new DatabaseConnectionException(PROBLEM_WITH_CONNECTION);
        }finally {
            conn.close();
        }
    }

    private void executeUpdate(String update, Timestamp timeStamp) throws SQLException {
        try {
            preparedStatement = conn.prepareStatement(update);
            preparedStatement.setTimestamp(1, timeStamp);
            preparedStatement.executeUpdate();
        }finally {
            preparedStatement.close();
        }


    }


}
