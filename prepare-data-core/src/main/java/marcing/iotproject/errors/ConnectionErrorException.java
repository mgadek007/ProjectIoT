package marcing.iotproject.errors;

public class ConnectionErrorException extends RuntimeException {

    public ConnectionErrorException (String messgage){
        super(messgage);
    }
}
