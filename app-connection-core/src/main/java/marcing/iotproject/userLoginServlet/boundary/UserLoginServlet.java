package marcing.iotproject.userLoginServlet.boundary;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import marcing.iotproject.dataBaseConnectionForApp.boundary.DataBaseConnectionForApp;
import marcing.iotproject.errors.ConnectionError;
import marcing.iotproject.errors.ConvertError;
import marcing.iotproject.errors.UnauthoriseException;
import marcing.iotproject.userLoginServlet.control.BodyReader;
import marcing.iotproject.userLoginServlet.entity.UserLoginDTO;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserLoginServlet extends HttpServlet {

    private static final String ERROR_WITH_CONVERT_TO_JSON = "Error with convert user to json";
    private static final String USER_DO_NOT_EXIST = "User do not exist in database.";

    private BodyReader bodyReader = new BodyReader();

    private DataBaseConnectionForApp dataBaseConnectionForApp = new DataBaseConnectionForApp();


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        processRequest(request, response);

    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            UserLoginDTO user = bodyReader.convertJsonToUserDTO(request);
            UserLoginDTO userAfterVerification = dataBaseConnectionForApp.getUserAuthorisation(user);
            if (userAfterVerification.getUser() != null){
                if (user.getPassword().equals(userAfterVerification.getPassword())){
                    userAfterVerification.setPassword(null);
                    response.setStatus(200);
                }else {
                    response.setStatus(401);
                    userAfterVerification.setToken(null);
                    userAfterVerification.setPassword(null);
                    userAfterVerification.setTemp(null);
                }
                String result = prepareJson(userAfterVerification);
                response.getWriter().write(result);
            }else {
                response.setStatus(401);
                response.getWriter().write(USER_DO_NOT_EXIST);
            }
        }catch (Exception exception){
            if (exception instanceof ConvertError) {
                response.setStatus(404);
            }else if(exception instanceof UnauthoriseException) {
                response.setStatus(401);
            }else if (exception instanceof ConnectionError) {
                response.setStatus(408);
            }else {
                response.setStatus(500);
            }
            response.getOutputStream().print(exception.getMessage());
        }
    }

    private String prepareJson(UserLoginDTO userAfterVerification) {
        String result;
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            result = objectMapper.writeValueAsString(userAfterVerification);
        }catch (JsonProcessingException e){
            throw new ConvertError(ERROR_WITH_CONVERT_TO_JSON);
        }
        return result;
    }
}
