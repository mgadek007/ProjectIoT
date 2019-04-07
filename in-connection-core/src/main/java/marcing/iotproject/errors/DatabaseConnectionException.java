package marcing.iotproject.errors;

public class DatabaseConnectionException extends RuntimeException{

    public DatabaseConnectionException(String message){
        super(message);
    }
}
