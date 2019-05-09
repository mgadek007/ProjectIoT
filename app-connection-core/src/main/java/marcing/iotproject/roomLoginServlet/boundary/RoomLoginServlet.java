package marcing.iotproject.roomLoginServlet.boundary;

import marcing.iotproject.dataBaseConnectionForApp.boundary.DataBaseConnectionForApp;
import marcing.iotproject.roomLoginServlet.control.BodyConverter;
import marcing.iotproject.roomLoginServlet.entity.RoomLoginDTO;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RoomLoginServlet extends HttpServlet {

    private DataBaseConnectionForApp dataBaseConnectionForApp = new DataBaseConnectionForApp();
    private BodyConverter bodyConverter = new BodyConverter();

    private static final String SUCCESS = "Success authorisation";
    private static final String FAILED = "Failed authorisation";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        RoomLoginDTO roomLoginDTO = bodyConverter.convertJsonToRoonLoginDTO(request);
        RoomLoginDTO roomFromDB = dataBaseConnectionForApp.getRoomFromDataBase(roomLoginDTO.getIdRoom());
        if (roomLoginDTO.getIdRoom().equals(roomFromDB.getIdRoom())
        && roomLoginDTO.getPassword().equals(roomFromDB.getPassword())){
            response.setStatus(200);
            response.getOutputStream().print(SUCCESS);
        }else {
            response.setStatus(401);
            response.getOutputStream().print(FAILED);
        }

    }

}
