package marcing.iotproject.dataBaseConnection.boundary;

import marcing.iotproject.basicElements.DataBlock;
import marcing.iotproject.dataBaseConnection.control.DatabaseConverter;
import marcing.iotproject.errors.DatabaseConnectionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.servlet.http.HttpServlet;
import java.sql.*;


public class DataBaseConnection extends HttpServlet {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private static final String PROBLEM_WITH_CONNECTION = " Problem with connection to Database";
    private static final String T_CHAR = "T";
    private static final String SPACE_CHAR = " ";

    private Connection conn;

    public void init(){
        try {
            String dbIpAddr = "localhost";
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            conn = DriverManager.getConnection("jdbc:mysql://"+dbIpAddr+":3306/sys?serverTimezone=UTC", "root", "root123!");

        } catch (SQLException e) {
            log.error(PROBLEM_WITH_CONNECTION);
            log.error(e.toString());
            throw new DatabaseConnectionException(PROBLEM_WITH_CONNECTION);
        }
    }

    private final DatabaseConverter databaseConverter = new DatabaseConverter();

    public void loadDataToDataBase(DataBlock dataBlock)   {
        String message = databaseConverter.prepareMessageForDataBase(dataBlock);
        Timestamp timeStamp = prepareTimeStamp(dataBlock.getTimeStamp());
        init();
        loadToDatabase(message, timeStamp);
    }

    private Timestamp prepareTimeStamp(String timestamp) {
        String time = timestamp.replace(T_CHAR, SPACE_CHAR);
        return Timestamp.valueOf(time);
    }

    private void loadToDatabase(String message, Timestamp timeStamp) {
        try{
            log.info("**** LOAD TO DATABASE ****");
            log.info(message + timeStamp);
            executeUpdate(message, timeStamp);
            conn.close();
        }catch(SQLException | NullPointerException e) {
            log.error(PROBLEM_WITH_CONNECTION);
            log.error(e.toString());
            throw new DatabaseConnectionException(PROBLEM_WITH_CONNECTION);
        }
    }

    private void executeUpdate(String update, Timestamp timeStamp) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(update);
        preparedStatement.setTimestamp(1, timeStamp);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }


}
