package marcing.iotproject.errors;

public class ConnectionError extends RuntimeException {

    public ConnectionError(String message){
        super(message);
    }
}
