package marcing.iotproject.userLoginServlet.control;

import com.google.gson.*;
import marcing.iotproject.errors.ConvertError;
import marcing.iotproject.userLoginServlet.entity.UserLoginDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStreamReader;

public class BodyReader {

    private final Logger log = LoggerFactory.getLogger(BodyReader.class);

    private static final String PROBLEM_WITH_CONVERT_INPUT_DATA = "Problem with convert user";

    public UserLoginDTO convertJsonToUserDTO(HttpServletRequest request) throws IOException {
        UserLoginDTO userLoginDTO;
        try{
            JsonElement elem = new JsonParser().parse(new InputStreamReader(request.getInputStream()));
            Gson gson  = new GsonBuilder().create();
            userLoginDTO = gson.fromJson(elem, UserLoginDTO.class);
        } catch (JsonIOException | JsonSyntaxException e){
            log.error(PROBLEM_WITH_CONVERT_INPUT_DATA);
            log.error(e.toString());
            throw new ConvertError(PROBLEM_WITH_CONVERT_INPUT_DATA);
        }
        return userLoginDTO;
    }
}
