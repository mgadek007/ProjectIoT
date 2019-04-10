package marcing.iotproject.userLoginServlet.boundary;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import marcing.iotproject.dataBaseConnectionForApp.boundary.DataBaseConnectionForApp;
import marcing.iotproject.errors.ConvertError;
import marcing.iotproject.errors.UnauthoriseException;
import marcing.iotproject.userLoginServlet.control.BodyReader;
import marcing.iotproject.userLoginServlet.entity.UserLoginDTO;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import java.io.IOException;
import java.net.URI;

public class UserLoginServlet extends HttpServlet {

    private BodyReader bodyReader = new BodyReader();
    private DataBaseConnectionForApp dataBaseConnectionForApp = new DataBaseConnectionForApp();

    private static final String ERROR_WITH_CONVERT_TO_JSON = "Error with convert user to json";


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        processRequest(request, response);

    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            UserLoginDTO user = bodyReader.convertJsonToUserDTO(request);
            UserLoginDTO userAfterVerification = dataBaseConnectionForApp.getUserAuthorisation(user);
            if (user.getPassword().equals(userAfterVerification.getPassword())){
                userAfterVerification.setPassword(null);
                response.setStatus(200);
            }else {
                response.setStatus(401);
                userAfterVerification.setToken(null);
                userAfterVerification.setPassword(null);
            }
            String result = prepareJson(userAfterVerification);
            response.getWriter().write(result);

        }catch (UnauthoriseException exception){
            response.setStatus(401);
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
