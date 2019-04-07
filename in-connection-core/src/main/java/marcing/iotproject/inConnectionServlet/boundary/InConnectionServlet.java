package marcing.iotproject.inConnectionServlet.boundary;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import marcing.iotproject.DataBlock;
import marcing.iotproject.dataBaseConnection.boundary.DataBaseConnection;
import marcing.iotproject.errors.ConvertErrorException;
import marcing.iotproject.errors.DatabaseConnectionException;
import marcing.iotproject.inConnectionServlet.control.BodyReader;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class InConnectionServlet extends HttpServlet {

    private BodyReader bodyReader = new BodyReader();
    private DataBaseConnection dataBaseConnection = new DataBaseConnection();

    private static final String SUCCESS_MESSAGE = "Data was register correctly.";
    private static final String ERROR_ROOM_ID = "Room ID must be set";


    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        processRequest(request, response);

    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            DataBlock dataBlock = bodyReader.convertJsonToDataBlock(request);
            Preconditions.checkArgument(Strings.isNullOrEmpty(dataBlock.getId()), ERROR_ROOM_ID);
            dataBaseConnection.loadDataToDataBase(dataBlock);

            response.setStatus(200);
            response.getOutputStream().print(SUCCESS_MESSAGE);
        }catch (ConvertErrorException | DatabaseConnectionException | IllegalArgumentException e){
            response.getOutputStream().print(e.getMessage());
            if(e instanceof ConvertErrorException){
                response.setStatus(400);
            }else if(e instanceof DatabaseConnectionException){
                response.setStatus(404);
            }else {
                response.setStatus(500);
            }

        }


    }

}
