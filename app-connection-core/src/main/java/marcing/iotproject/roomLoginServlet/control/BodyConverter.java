package marcing.iotproject.roomLoginServlet.control;

import com.google.gson.*;
import marcing.iotproject.errors.ConvertError;
import marcing.iotproject.roomLoginServlet.entity.RoomLoginDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStreamReader;

public class BodyConverter {

    private final Logger log = LoggerFactory.getLogger(BodyConverter.class);

    private static final String PROBLEM_WITH_CONVERT_INPUT_DATA = "Problem with convert user";

    public RoomLoginDTO convertJsonToRoomLoginDTO(HttpServletRequest request) throws IOException {
        RoomLoginDTO roomLoginDTO;
        try{
            JsonElement elem = new JsonParser().parse(new InputStreamReader(request.getInputStream()));
            Gson gson  = new GsonBuilder().create();
            roomLoginDTO = gson.fromJson(elem, RoomLoginDTO.class);
        } catch (JsonIOException | JsonSyntaxException e){
            log.error(PROBLEM_WITH_CONVERT_INPUT_DATA);
            log.error(e.toString());
            throw new ConvertError(PROBLEM_WITH_CONVERT_INPUT_DATA);
        }
        return roomLoginDTO;
    }
}
