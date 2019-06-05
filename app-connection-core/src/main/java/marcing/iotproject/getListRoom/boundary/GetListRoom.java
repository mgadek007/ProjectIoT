package marcing.iotproject.getListRoom.boundary;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import marcing.iotproject.changeRoomPass.boundary.ChangePassRoomServlet;
import marcing.iotproject.dataBaseConnectionForApp.boundary.DataBaseConnectionForApp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

public class GetListRoom extends HttpServlet {

    private DataBaseConnectionForApp dataBaseConnectionForApp = new DataBaseConnectionForApp();
    private Logger LOG = LoggerFactory.getLogger(ChangePassRoomServlet.class);

    private static final String SUCCESS = "Success change list";
    private static final String SUCCESS_EVRY = "Success every in change pass";
    private static final String FAILED_MESSAGE = "Something go wrong by processing";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response){
        try{
            ArrayList result = dataBaseConnectionForApp.getListRoom();
            LOG.info(SUCCESS);
            String stringResult = prepareJson(result);
            response.getOutputStream().print(stringResult);
            response.setStatus(200);
            LOG.info(SUCCESS_EVRY);

        }catch (Exception e){
            response.setStatus(500);
            LOG.error(FAILED_MESSAGE, e);
        }
    }

    private String prepareJson(ArrayList list) {
        GsonBuilder gson  = new GsonBuilder();
        gson.serializeNulls();
        Gson gson1 = gson.create();
        return gson1.toJson(list);
    }
}
