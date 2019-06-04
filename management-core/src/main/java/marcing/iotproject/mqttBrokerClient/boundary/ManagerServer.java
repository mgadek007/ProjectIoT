package marcing.iotproject.mqttBrokerClient.boundary;

import marcing.iotproject.dataPreparator.control.DatePreparator;
import marcing.iotproject.dataPreparator.entity.DataBlock;
import marcing.iotproject.managementRoom.boundary.ManageRoom;
import marcing.iotproject.mqttBrokerClient.entity.ConnectionMqttDictionary;
import org.eclipse.paho.client.mqttv3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.text.MessageFormat;
import java.util.Random;

@Singleton
@Startup
public class ManagerServer implements MqttCallbackExtended {

    private static final String CLIENT_ID = "MenageServer";
    private final Logger LOG = LoggerFactory.getLogger(ManagerServer.class);
    private static final String PROBLEM_WITH_INIT = "Problem with init VoteManagerServer";
    private static final String INIT_CLASS_CORRECTLY = "Inicialization class was done correctly";
    private static final String CONNECTION_LOST = "Connection lost";
    private static final int SEND_DATA = 2;
    private int TRY_SEND_DATA = 0;

    private static final String TRY_PUBLISH_MESSAGE = "Try publish a message: {0}";
    private static final String PUBLISHED_MESSAGE = "Message published correctly";
    private static final String PUBLISHE_MESSAGE_FAILED = "Client no publish a message";
    private static final String SEND_DATA_FOR_APP_FOR = "Send data for app: {0}";
    private static final String SEND_DATA_FOR_HARDWARE = "Send data for Hardware: {0}";



    private MqttClient client;

    private ManageRoom manageRoom = new ManageRoom();
    private DatePreparator dataPreparator = new DatePreparator();
    private LightManagment li =new LightManagment();


    public ManagerServer(){
        init();
    }

    private void init(){
        try {

            this.client = new MqttClient(ConnectionMqttDictionary.BROKER, CLIENT_ID + new Random().nextInt());
            MqttConnectOptions options = new MqttConnectOptions();
            options.setUserName(ConnectionMqttDictionary.MQTT_USER);
            options.setPassword(ConnectionMqttDictionary.MQTT_PASSWORD.toCharArray());
            options.setAutomaticReconnect(true);
            options.setKeepAliveInterval(999999999);
            options.setCleanSession(true);
            options.getDebug();
            client.connect(options);
            client.setCallback(this);
            LOG.info(INIT_CLASS_CORRECTLY);
            client.subscribe(ConnectionMqttDictionary.PREPATE_DATA_TOPIC);

        } catch (MqttException e) {
            LOG.error(PROBLEM_WITH_INIT, e);

        }

    }

    @Override
    public void connectionLost(Throwable throwable) {
        LOG.error(CONNECTION_LOST, throwable);
        new ManagerServer();
    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) {
        if(ConnectionMqttDictionary.PREPATE_DATA_TOPIC.equals(s)) {
            DataBlock result = dataPreparator.prepareDataFromMqtt(mqttMessage);
            TRY_SEND_DATA++;
            if (TRY_SEND_DATA == SEND_DATA) {
                String topic = MessageFormat.format(ConnectionMqttDictionary.APP_TOPIC, mqttMessage);
                String resultString = dataPreparator.prepareJson(result);
                sendData(topic, resultString);
                TRY_SEND_DATA = 0;
                LOG.info(MessageFormat.format(SEND_DATA_FOR_APP_FOR, resultString));
            }
            String manageResult = manageRoom.manageAir(result);
            sendData(ConnectionMqttDictionary.HARDWARE_TOPIC_MANAGEMENT, manageResult);
            LOG.info(MessageFormat.format(SEND_DATA_FOR_HARDWARE, manageResult));
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        LOG.info(ConnectionMqttDictionary.PUBLISHED_MESSAGE);
    }

    public void sendData(String topic, String message){
        MqttMessage messageForApp = new MqttMessage(message.getBytes());
        messageForApp.setQos(0);
        LOG.debug(MessageFormat.format(TRY_PUBLISH_MESSAGE, message));
        try {
            client.publish(topic, messageForApp);
            LOG.info(PUBLISHED_MESSAGE);
        } catch (Exception e) {
            LOG.error(PUBLISHE_MESSAGE_FAILED, e);
        }
    }

    @Override
    public void connectComplete(boolean b, String s) {
        try {
            client.subscribe(ConnectionMqttDictionary.PREPATE_DATA_TOPIC);
            LOG.info(ConnectionMqttDictionary.SUBSCRIBE_TOPIC_CORRECT);
        }catch (MqttException e){
            LOG.error(ConnectionMqttDictionary.SUBSCCRIBE_TOPIC_FAILED, e);
        }

    }
}
