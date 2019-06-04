package marcing.iotproject.roomLoginServlet.boundary;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import marcing.iotproject.appConnectionServlet.entity.DataBlock;
import marcing.iotproject.dataBaseConnectionForApp.boundary.DataBaseConnectionForApp;
import marcing.iotproject.roomLoginServlet.control.BodyConverter;
import marcing.iotproject.roomLoginServlet.entity.RoomLoginDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.MessageFormat;

public class RoomLoginServlet extends HttpServlet {

    private DataBaseConnectionForApp dataBaseConnectionForApp = new DataBaseConnectionForApp();
    private Logger LOG = LoggerFactory.getLogger(RoomLoginServlet.class);

    private BodyConverter bodyConverter = new BodyConverter();

    private static final String SUCCESS = "Success authorisation";
    private static final String FAILED = "Failed authorisation to room: {0}";
    private static final String FAILED_MESSAGE = "Something go wrong by processing";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response){
        try{
            RoomLoginDTO roomLoginDTO = bodyConverter.convertJsonToRoomLoginDTO(request);
            RoomLoginDTO roomFromDB = dataBaseConnectionForApp.getRoomFromDataBase(roomLoginDTO.getIdRoom());
            if (roomLoginDTO.getIdRoom().equals(roomFromDB.getIdRoom()) && roomLoginDTO.getPassword().equals(roomFromDB.getPassword())){
                LOG.info(SUCCESS);
                response.setStatus(200);
                DataBlock result = dataBaseConnectionForApp.getDataFromBase(roomFromDB.getIdRoom());
                result.setId(roomFromDB.getIdRoom());
                String stringResult = prepareJson(result);
                response.getOutputStream().print(stringResult);
            }else {
                response.setStatus(401);
                response.getOutputStream().print(FAILED);
                LOG.warn(MessageFormat.format(FAILED, roomLoginDTO.getIdRoom()));
            }
        }catch (Exception e){
             LOG.error(FAILED_MESSAGE, e);
        }
    }

    private String prepareJson(DataBlock dataBlock) {
        GsonBuilder gson  = new GsonBuilder();
        gson.serializeNulls();
        Gson gson1 = gson.create();
        return gson1.toJson(dataBlock);
    }

}
