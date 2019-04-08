package marcing.iotproject.dataBaseConnectionForApp.boundary;

import marcing.iotproject.dataBaseConnectionForApp.control.DataFromBaseConverter;
import marcing.iotproject.errors.ConnectionError;
import marcing.iotproject.errors.UnauthoriseException;
import marcing.iotproject.userLoginServlet.entity.UserLoginDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.text.MessageFormat;

public class DataBaseConnectionForApp {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private static final String PROBLEM_WITH_CONNECTION = " Problem with connection to Database";
    private static final String SELECT_USER = "SELECT * FROM user WHERE Name = ''{0}'';";

    private DataFromBaseConverter dataFromBaseConverter = new DataFromBaseConverter();
    private Connection conn;
    private Statement statement;

    private void init() {
        try {
            String dbIpAddr = "localhost";
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            conn = DriverManager.getConnection("jdbc:mysql://" + dbIpAddr + ":3306/sys?serverTimezone=UTC", "root", "root123!");

        } catch (SQLException e) {
            log.error(PROBLEM_WITH_CONNECTION);
            log.error(e.toString());
            throw new ConnectionError(PROBLEM_WITH_CONNECTION);
        }
    }

    public UserLoginDTO getUserAuthorisation(UserLoginDTO userLoginDTO) {
        String message = prepareMessageToGet(userLoginDTO.getUser());
        UserLoginDTO result;
        init();
        try {
            result = getFromDatabase(message);
        } catch (SQLException e) {
            log.error(PROBLEM_WITH_CONNECTION);
            log.error(e.toString());
            throw new UnauthoriseException(PROBLEM_WITH_CONNECTION);
        }
        return result;

    }

    private String prepareMessageToGet(String userName) {
        return MessageFormat.format(SELECT_USER, userName);
    }


    private UserLoginDTO getFromDatabase(String message) throws SQLException {
        UserLoginDTO result;
        try {
            log.info("**** GET FROM DATABASE ****");
            log.info(message);
            result = executeQuery(message);

        } catch (SQLException | NullPointerException e) {
            log.error(PROBLEM_WITH_CONNECTION);
            log.error(e.toString());
            throw new UnauthoriseException(PROBLEM_WITH_CONNECTION);
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


}
