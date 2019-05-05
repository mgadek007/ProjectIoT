package marcing.iotproject.dataBaseConnectionForApp.boundary;

import marcing.iotproject.appConnectionServlet.entity.DataBlock;
import marcing.iotproject.dataBaseConnectionForApp.control.DataFromBaseConverter;
import marcing.iotproject.errors.ConnectionError;
import marcing.iotproject.errors.ConvertError;
import marcing.iotproject.userLoginServlet.entity.UserLoginDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.text.MessageFormat;

public class DataBaseConnectionForApp {

    private final Logger log = LoggerFactory.getLogger(DataFromBaseConverter.class);

    private static final String PROBLEM_WITH_CONNECTION = " Problem with connection to Database";
    private static final String OUT_ROOM = "outRoom{0}";
    private static final String SELECT_USER = "SELECT * FROM user WHERE Name = ''{0}'';";
    private static final String SELECT_DATA = "SELECT * FROM {0} ORDER BY timestamp DESC WHERE dac limit 1";

    private DataFromBaseConverter dataFromBaseConverter = new DataFromBaseConverter();
    private Connection conn;
    private Statement statement;


    private void init() {
        try {
            String dbIpAddr = "localhost";
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            conn = DriverManager.getConnection("jdbc:mysql://" + dbIpAddr + ":3306/iot?serverTimezone=UTC", "usereitDB", "3kl3ktyk4");

        } catch (SQLException e) {
            log.error(PROBLEM_WITH_CONNECTION);
            log.error(e.toString());
            throw new ConnectionError(PROBLEM_WITH_CONNECTION);
        }
    }

    public UserLoginDTO getUserAuthorisation(UserLoginDTO userLoginDTO) {
        String message = prepareQuery(SELECT_USER, userLoginDTO.getUser());
        UserLoginDTO result;
        init();
        try {
            result = getFromDatabase(message);
        } catch (SQLException e) {
            log.error(PROBLEM_WITH_CONNECTION);
            log.error(e.toString());
            throw new ConvertError(PROBLEM_WITH_CONNECTION);
        }
        return result;

    }

    public DataBlock getDataFromBase(String id){
        String tableName = MessageFormat.format(OUT_ROOM, id);
        String query = prepareQuery(SELECT_DATA, tableName);
        DataBlock result;
        init();
        try {
            result = getDataFromDataBase(query);
        }catch (SQLException e){
            throw new ConvertError(PROBLEM_WITH_CONNECTION);
        }
        return result;

    }

    private String prepareQuery(String queryFormat, String queryValue) {
        return MessageFormat.format(queryFormat, queryValue);
    }


    private UserLoginDTO getFromDatabase(String query) throws SQLException {
        UserLoginDTO result;
        try {
            log.info("**** GET FROM DATABASE ****");
            log.info(query);
            result = executeQuery(query);

        } catch (SQLException | NullPointerException e) {
            log.error(PROBLEM_WITH_CONNECTION);
            log.error(e.toString());
            throw new ConnectionError(PROBLEM_WITH_CONNECTION);
        } finally {
            conn.close();
        }
        return result;
    }

    private UserLoginDTO executeQuery(String query) throws SQLException {
        UserLoginDTO user ;
        try {
            statement = conn.createStatement();
            ResultSet result = statement.executeQuery(query);
            user = dataFromBaseConverter.convertToUserLoginDTO(result);
        } catch (SQLException e) {
            log.error(PROBLEM_WITH_CONNECTION);
            log.error(e.toString());
            throw new ConnectionError(PROBLEM_WITH_CONNECTION);
        } finally {
            statement.close();
        }
        return user;
    }

    private DataBlock getDataFromDataBase(String query) throws SQLException {
        DataBlock result;
        try {
            log.info("**** GET DATA FROM DATABASE ****");
            log.info(query);
            result = executeQueryForDataBlock(query);

        } catch (SQLException | NullPointerException e) {
            log.error(PROBLEM_WITH_CONNECTION);
            log.error(e.toString());
            throw new ConvertError(PROBLEM_WITH_CONNECTION);
        } finally {
            conn.close();
        }
        return result;
    }

    private DataBlock executeQueryForDataBlock(String query) throws SQLException {
        DataBlock data ;
        try {
            statement = conn.createStatement();
            ResultSet result = statement.executeQuery(query);
            data = dataFromBaseConverter.convertToDataBlock(result);
        } catch (SQLException e) {
            log.error(PROBLEM_WITH_CONNECTION);
            log.error(e.toString());
            throw new ConnectionError(PROBLEM_WITH_CONNECTION);
        } finally {
            statement.close();
        }
        return data;
    }


}
