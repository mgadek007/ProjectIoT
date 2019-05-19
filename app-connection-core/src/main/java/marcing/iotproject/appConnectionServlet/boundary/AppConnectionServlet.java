package marcing.iotproject.appConnectionServlet.boundary;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import marcing.iotproject.appConnectionServlet.entity.DataBlock;
import marcing.iotproject.dataBaseConnectionForApp.boundary.DataBaseConnectionForApp;
import marcing.iotproject.errors.ConvertError;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AppConnectionServlet extends HttpServlet {


    private static final int BEGINING_OF_ID = 1;
    private static final String ERROR_WITH_CONVERT_TO_JSON = "Error with convert result;";

    private DataBaseConnectionForApp dataBaseConnectionForApp = new DataBaseConnectionForApp();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getPathInfo().substring(BEGINING_OF_ID);
        DataBlock result = dataBaseConnectionForApp.getDataFromBase(id);
        response.setStatus(200);
        response.getOutputStream().println(prepareJson(result));
    }

    private String prepareJson(DataBlock dataBlock) {
        String result;
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            result = objectMapper.writeValueAsString(dataBlock);
        }catch (JsonProcessingException e){
            throw new ConvertError(ERROR_WITH_CONVERT_TO_JSON);
        }
        return result;

    }

}
