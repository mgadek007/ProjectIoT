package marcing.iotproject.mqttBrokerClient.boundary;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import marcing.iotproject.managementRoom.boundary.ManageRoom;
import marcing.iotproject.managementRoom.entity.ManagementDTO;
import marcing.iotproject.managementRoom.entity.MnageLightDTO;
import marcing.iotproject.mqttBrokerClient.control.GetDataFromMessage;
import marcing.iotproject.mqttBrokerClient.entity.ConnectionMqttDictionary;
import org.eclipse.paho.client.mqttv3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Startup;
import javax.inject.Singleton;
import java.text.MessageFormat;
import java.util.Random;

@Singleton
@Startup
public class LightAndTempManagment implements MqttCallbackExtended {

    private static final String CLIENT_ID = "LightManage";
    private final Logger LOG = LoggerFactory.getLogger(LightAndTempManagment.class);
    private static final String PROBLEM_WITH_INIT = "Problem with init DataDorAppSender";
    private static final String INIT_CLASS_CORRECTLY = "Inicialization class was done correctly";
    private static final String TRY_PUBLISH_MESSAGE = "Try publish a message: {0}";
    private static final String PUBLISHED_MESSAGE = "Message published correctly";
    private static final String PUBLISHE_MESSAGE_FAILED = "Client no publish a message";
    private static final String START_PREPARE_DATA = "Start menage light an temp for rrom {0}";
    private static final String SEND_DATA_FOR_APP_FOR_ROOM_ID = "Send data for app to room with id: {0}, data: {1}";
    private static final String FAILED_RESULT = "Failed prepared data for room with id: {0}";

    private MqttClient client;

    private GetDataFromMessage dataGetter = new GetDataFromMessage();
    private ManageRoom manageRoom = new ManageRoom();


    LightAndTempManagment(){
        init();
    }

    private void init(){
        try {
            this.client = new MqttClient(ConnectionMqttDictionary.BROKER, CLIENT_ID+ new Random().nextInt());
            MqttConnectOptions options = new MqttConnectOptions();
            options.setUserName(ConnectionMqttDictionary.MQTT_USER);
            options.setPassword(ConnectionMqttDictionary.MQTT_PASSWORD.toCharArray());
            options.setAutomaticReconnect(true);
            options.getDebug();
            options.setKeepAliveInterval(120);
            options.setCleanSession(true);
            client.connect(options);
            client.setCallback(this);
            client.subscribe(ConnectionMqttDictionary.MANAGENENT_LIGHT_TOPIC);
            client.subscribe(ConnectionMqttDictionary.MANAGEMENT_TEMP_TOPIC);
            LOG.info(INIT_CLASS_CORRECTLY);
        } catch (MqttException e) {
            LOG.error(PROBLEM_WITH_INIT, e);

        }

    }

    private void sendDataForApp(String topic, String message){
        MqttMessage messageForApp = new MqttMessage(message.getBytes());
        messageForApp.setQos(2);
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
            client.subscribe(ConnectionMqttDictionary.MANAGENENT_LIGHT_TOPIC);
            client.subscribe(ConnectionMqttDictionary.MANAGEMENT_TEMP_TOPIC);
            LOG.info(MessageFormat.format(ConnectionMqttDictionary.SUBSCRIBE_TOPIC_CORRECT, ConnectionMqttDictionary.PREPATE_DATA_TOPIC));
        }catch (MqttException e){
            LOG.error(MessageFormat.format(ConnectionMqttDictionary.SUBSCCRIBE_TOPIC_FAILED, ConnectionMqttDictionary.PREPATE_DATA_TOPIC), e);
        }

    }

    @Override
    public void connectionLost(Throwable throwable) {
        new LightAndTempManagment();
    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) {
        if(s.equals(ConnectionMqttDictionary.MANAGENENT_LIGHT_TOPIC)){
            MnageLightDTO requestData = dataGetter.convertJsonToManageLisght(mqttMessage);
            LOG.info(MessageFormat.format(START_PREPARE_DATA, requestData.getId()));
            String result = manageRoom.manageLight(requestData);
            try {
                sendDataForApp(ConnectionMqttDictionary.HARDWARE_LIGHT_TOPIC_MANAGEMENT, result);
                LOG.info(MessageFormat.format(SEND_DATA_FOR_APP_FOR_ROOM_ID, requestData.getId(), requestData));
            }catch (Throwable e){
                LOG.info(MessageFormat.format(FAILED_RESULT, requestData.getId()), e);
            }
        }else if (s.equals(ConnectionMqttDictionary.MANAGEMENT_TEMP_TOPIC)){
            ManagementDTO requestTemp = dataGetter.convertJsonToManageDTO(mqttMessage);
            LOG.info(MessageFormat.format(START_PREPARE_DATA, requestTemp.getId()));
            try {
                sendDataForApp(ConnectionMqttDictionary.HARDWARE_TOPIC_MANAGEMENT, prepareJson(requestTemp));
                LOG.info(MessageFormat.format(SEND_DATA_FOR_APP_FOR_ROOM_ID, requestTemp.getId(), requestTemp));
            }catch (Throwable e){
                LOG.info(MessageFormat.format(FAILED_RESULT, requestTemp.getId()), e);
            }
        }

    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        LOG.info(ConnectionMqttDictionary.PUBLISHED_MESSAGE);
    }

    private String prepareJson(ManagementDTO manage){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls();
        Gson gson = gsonBuilder.create();
        return gson.toJson(manage);

    }
}
