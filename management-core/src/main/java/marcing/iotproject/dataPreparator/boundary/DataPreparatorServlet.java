package marcing.iotproject.dataPreparator.boundary;

import marcing.iotproject.dataPreparator.control.DatePreparator;
import marcing.iotproject.mqttBrokerClient.boundary.DataForAppSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.MessageFormat;

public class DataPreparatorServlet extends HttpServlet {

    private static final String SUCCESS_RESULT = "Success prepared data";
    private static final String FAILED_RESULT = "Something go wrong";
    private static final String START_PREPARE_DATA = "Start prepare data for room with id: {0}";
    private static final String SEND_DATA_FOR_APP_FOR_ROOM_ID = "Send data for app to room with id: {0}, data: {1}";
    private static final int BEGINING_OF_ID = 1;
    private final Logger log = LoggerFactory.getLogger(getClass());
    private static final int SEND_DATA = 5;
    private int TRY_SEND_DATA = 0;

    private DatePreparator datePreparator = new DatePreparator();

    private DataForAppSender dataForAppSender = new DataForAppSender();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getPathInfo().substring(BEGINING_OF_ID);
        log.info(MessageFormat.format(START_PREPARE_DATA, id));
        try {
            String message = datePreparator.prepareData(id);
            TRY_SEND_DATA++;
            if (TRY_SEND_DATA == SEND_DATA){
                dataForAppSender.sendDataForApp(id, message);
                TRY_SEND_DATA = 0;
                log.info(MessageFormat.format(SEND_DATA_FOR_APP_FOR_ROOM_ID, id, message));
            }
            response.setStatus(200);
            response.getOutputStream().println(SUCCESS_RESULT);
        }catch (Exception e){
            response.setStatus(409);
            response.getOutputStream().print(FAILED_RESULT);
        }

    }

}
