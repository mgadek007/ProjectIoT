package marcing.iotproject.mqttBrokerClient.boundary;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import marcing.iotproject.basicElements.DataBlock;
import marcing.iotproject.dataBaseConnection.boundary.DataBaseConnection;
import marcing.iotproject.inConnectionServlet.control.BodyReader;
import marcing.iotproject.inConnectionServlet.control.ManagementCoreApi;
import marcing.iotproject.mqttBrokerClient.entity.ConnectionMqttDictionary;
import org.eclipse.paho.client.mqttv3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.text.MessageFormat;

@Singleton
@Startup
public class InConnectionMqttClient implements MqttCallback {

    private static final String CLIENT_ID = "InConnectionServerClient";
    private final Logger LOG = LoggerFactory.getLogger(InConnectionMqttClient.class);
    private static final String PROBLEM_WITH_INIT = "Problem with init VoteManagerServer";
    private static final String INIT_CLASS_CORRECTLY = "Inicialization class was done correctly";
    private static final String MESSAGE_ARRIVE = "Recive message on InConnectionServerClient: {0}";
    private static final String PUBLISHED_MESSAGE = "Message published correctly";
    private static final String ARRIVE_MESSAGE_FAILED = "Problem with convert anl load message";
    private static final String CONNECTION_LOST = "Connection lost";
    private static final String SUCCESS_MESSAGE = "Data was register correctly.";
    private static final String ERROR_ROOM_ID = "Room ID must be set";


    private MqttClient client;

    private InConnectionMqttClient(){
        init();
    }

    private BodyReader bodyReader = new BodyReader();

    private DataBaseConnection dataBaseConnection = new DataBaseConnection();

    private ManagementCoreApi managementCoreApi = new ManagementCoreApi();

    private void init(){
        try {

            this.client = new MqttClient(ConnectionMqttDictionary.BROKER, CLIENT_ID);
            MqttConnectOptions options = new MqttConnectOptions();
            options.setUserName(ConnectionMqttDictionary.MQTT_USER);
            options.setPassword(ConnectionMqttDictionary.MQTT_PASSWORD.toCharArray());
//            options.setAutomaticReconnect(true);
            options.setCleanSession(false);
            options.getDebug();
            client.connect(options);
            client.setCallback(this);
            LOG.info(INIT_CLASS_CORRECTLY);
            client.subscribe(ConnectionMqttDictionary.IN_CONN_TOPIC);
        } catch (MqttException e) {
            LOG.error(PROBLEM_WITH_INIT, e);
        }

    }

    @Override
    public void connectionLost(Throwable throwable) {
        LOG.error(CONNECTION_LOST, throwable);
        new InConnectionMqttClient();
    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) {
        LOG.info(MessageFormat.format(MESSAGE_ARRIVE, mqttMessage.toString()));
        try {
            DataBlock dataBlock = bodyReader.convertJsonStringToDataBlock(mqttMessage.toString());
            LOG.debug(dataBlock.toString());
            Preconditions.checkArgument(!Strings.isNullOrEmpty(dataBlock.getId()), ERROR_ROOM_ID);
            dataBaseConnection.loadDataToDataBase(dataBlock);
            LOG.info(SUCCESS_MESSAGE);
            managementCoreApi.prepareData(dataBlock.getId());
        }catch (Throwable exception){
            LOG.error(ARRIVE_MESSAGE_FAILED, exception);
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        LOG.info(PUBLISHED_MESSAGE);
    }
}
