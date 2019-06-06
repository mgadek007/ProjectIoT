package marcing.iotproject.dataBaseConnection.control;

import marcing.iotproject.errors.ConnectionErrorException;
import marcing.iotproject.errors.ConvertError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.List;

public class DataBaseConn {

    private final Logger log = LoggerFactory.getLogger(DataBaseConn.class);
    private static final String PROBLEM_WITH_CONNECTION = " Problem with connection to Database";

    private DataBaseConvert dataBaseConvert = new DataBaseConvert();

    private Connection conn;
    private Statement statement;
    private PreparedStatement preparedStatement;


    private void init() {
        try {
            String dbIpAddr = "localhost";
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            conn = DriverManager.getConnection("jdbc:mysql://" + dbIpAddr + ":3306/sys?serverTimezone=Europe/Warsaw", "root", "root123!");


        } catch (SQLException e) {
            log.error(PROBLEM_WITH_CONNECTION);
            log.error(e.toString());
            throw new ConnectionErrorException(PROBLEM_WITH_CONNECTION);
        }
    }

    public List<String> getLastTemp(String query, String attrName) {
        init();
        List<String> result;
        try {
            result = getTempFromDataBase(query, attrName);
        }catch (SQLException e){
            throw new ConvertError(PROBLEM_WITH_CONNECTION);
        }
        return result;

    }

    private List<String> getTempFromDataBase(String query, String attrName) throws SQLException {
        List<String> result;
        try {
            log.info("**** GET DATA FROM DATABASE ****");
            log.info(query);
            result = executeQueryForTemp(query, attrName);

        } catch (SQLException | NullPointerException e) {
            log.error(PROBLEM_WITH_CONNECTION);
            log.error(e.toString());
            throw new ConvertError(PROBLEM_WITH_CONNECTION);
        } finally {
            conn.close();
        }
        return result;
    }

    private List<String> executeQueryForTemp(String query, String attrName) throws SQLException {
        List<String> data;
        try {
            statement = conn.createStatement();
            ResultSet result = statement.executeQuery(query);
            data = dataBaseConvert.convertToList(result, attrName);
        } catch (SQLException e) {
            log.error(PROBLEM_WITH_CONNECTION);
            log.error(e.toString());
            throw new ConnectionErrorException(PROBLEM_WITH_CONNECTION);
        } finally {
            statement.close();
        }
        return data;
    }

    public String getLastValueAttribute(String query, String attrName) {
        init();
        String result;
        try {
            result = getAttributeFromDB(query, attrName);
        }catch (SQLException e){
            throw new ConvertError(PROBLEM_WITH_CONNECTION);
        }
        return result;
    }

    private String getAttributeFromDB(String query, String attrName) throws SQLException {
        String result;
        try {
            log.info("**** GET DATA FROM DATABASE ****");
            log.info(query);
            result = executeQueryForAttribute(query, attrName);

        } catch (SQLException | NullPointerException e) {
            log.error(PROBLEM_WITH_CONNECTION);
            log.error(e.toString());
            throw new ConvertError(PROBLEM_WITH_CONNECTION);
        } finally {
            conn.close();
        }
        return result;
    }

    private String executeQueryForAttribute(String query, String attrName) throws SQLException {
        String data;
        try {
            statement = conn.createStatement();
            ResultSet result = statement.executeQuery(query);
            data = dataBaseConvert.getAttributeFromResult(result, attrName);
        } catch (SQLException e) {
            log.error(PROBLEM_WITH_CONNECTION);
            log.error(e.toString());
            throw new ConnectionErrorException(PROBLEM_WITH_CONNECTION);
        } finally {
            statement.close();
        }
        return data;
    }

    public void loadToDatabase(String message, Timestamp time) throws SQLException{
        init();
        try{
            log.info("**** LOAD TO DATABASE ****");
            log.info(message + time);
            executeUpdate(message, time);

        }catch(SQLException | NullPointerException e) {
            log.error(PROBLEM_WITH_CONNECTION);
            log.error(e.toString());
            throw new ConnectionErrorException(PROBLEM_WITH_CONNECTION);
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
