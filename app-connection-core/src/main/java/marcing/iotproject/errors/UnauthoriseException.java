package marcing.iotproject.errors;

public class UnauthoriseException extends RuntimeException {

    private static final String UNAUTHORISE = "Unauthorise user";

    public UnauthoriseException(){
        super(UNAUTHORISE);
    }

    public UnauthoriseException(String message){
        super(message);
    }

}
