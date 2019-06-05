package marcing.iotproject.changeRoomPass.boundary;

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

public class ChangePassRoomServlet extends HttpServlet {

    private DataBaseConnectionForApp dataBaseConnectionForApp = new DataBaseConnectionForApp();
    private Logger LOG = LoggerFactory.getLogger(ChangePassRoomServlet.class);

    private BodyConverter bodyConverter = new BodyConverter();

    private static final String SUCCESS = "Success change pass";
    private static final String SUCCESS_EVRY = "Success every in change pass";
    private static final String FAILED_MESSAGE = "Something go wrong by processing";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response){
        try{
            RoomLoginDTO roomLoginDTO = bodyConverter.convertJsonToRoomLoginDTO(request);
            dataBaseConnectionForApp.changePassToRoom(roomLoginDTO);
            LOG.info(SUCCESS);
            DataBlock result = dataBaseConnectionForApp.getDataFromBase(roomLoginDTO.getIdRoom());
            result.setId(roomLoginDTO.getIdRoom());
            String stringResult = prepareJson(result);
            response.getOutputStream().print(stringResult);
            response.setStatus(200);
            LOG.info(SUCCESS_EVRY);

        }catch (Exception e){
            response.setStatus(500);
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
