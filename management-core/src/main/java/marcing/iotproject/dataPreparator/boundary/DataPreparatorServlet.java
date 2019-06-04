package marcing.iotproject.dataPreparator.boundary;

import marcing.iotproject.dataPreparator.control.DatePreparator;
import marcing.iotproject.dataPreparator.entity.DataBlock;
import marcing.iotproject.mqttBrokerClient.boundary.ManagerServer;
import marcing.iotproject.mqttBrokerClient.entity.ConnectionMqttDictionary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private final Logger LOG = LoggerFactory.getLogger(getClass());
    private static final int SEND_DATA = 5;
    private int TRY_SEND_DATA = 0;

    private DatePreparator datePreparator = new DatePreparator();

    private ManagerServer managerServer = new ManagerServer();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getPathInfo().substring(BEGINING_OF_ID);
        LOG.info(MessageFormat.format(START_PREPARE_DATA, id));
        try {
            DataBlock dataBlock = datePreparator.prepareData(id);
            String message = datePreparator.prepareJson(dataBlock);
            TRY_SEND_DATA++;
            if (TRY_SEND_DATA == SEND_DATA){
                String topic = MessageFormat.format(ConnectionMqttDictionary.APP_TOPIC, id);
                managerServer.sendData(topic, message);
                TRY_SEND_DATA = 0;
                LOG.info(MessageFormat.format(SEND_DATA_FOR_APP_FOR_ROOM_ID, id, message));
            }
            response.setStatus(200);
            response.getOutputStream().println(SUCCESS_RESULT);
        }catch (Exception e){
            response.setStatus(409);
            response.getOutputStream().print(FAILED_RESULT);
        }

    }

}
