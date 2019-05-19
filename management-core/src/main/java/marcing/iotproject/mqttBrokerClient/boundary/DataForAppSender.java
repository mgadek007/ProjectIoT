package marcing.iotproject.mqttBrokerClient.boundary;

import marcing.iotproject.mqttBrokerClient.entity.ConnectionMqttDictionary;
import org.eclipse.paho.client.mqttv3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.text.MessageFormat;

@Singleton
public class DataForAppSender {

    private static final String CLIENT_ID = "DataBlockSenderInServer";
    private final Logger LOG = LoggerFactory.getLogger(DataForAppSender.class);
    private static final String PROBLEM_WITH_INIT = "Problem with init DataDorAppSender";
    private static final String INIT_CLASS_CORRECTLY = "Inicialization class was done correctly";
    private static final String TRY_PUBLISH_MESSAGE = "Try publish a message: {0}";
    private static final String PUBLISHED_MESSAGE = "Message published correctly";
    private static final String PUBLISHE_MESSAGE_FAILED = "Client no publish a message";

    private MqttClient client;

    public DataForAppSender(){
        init();
    }

    private void init(){
        try {
            this.client = new MqttClient(ConnectionMqttDictionary.BROKER, CLIENT_ID);
            MqttConnectOptions options = new MqttConnectOptions();
            options.setUserName(ConnectionMqttDictionary.MQTT_USER);
            options.setPassword(ConnectionMqttDictionary.MQTT_PASSWORD.toCharArray());
            options.setAutomaticReconnect(true);
            options.getDebug();
            options.setCleanSession(true);
            client.connect(options);
            LOG.info(INIT_CLASS_CORRECTLY);
        } catch (MqttException e) {
            LOG.error(PROBLEM_WITH_INIT, e);

        }

    }

    public void sendDataForApp(String id, String message){
        MqttMessage messageForApp = new MqttMessage(message.getBytes());
        messageForApp.setQos(0);
        LOG.debug(MessageFormat.format(TRY_PUBLISH_MESSAGE, message));
        String topic = MessageFormat.format(ConnectionMqttDictionary.APP_TOPIC, id);
        try {
            client.publish(topic, messageForApp);
            LOG.info(PUBLISHED_MESSAGE);
        } catch (Exception e) {
            LOG.error(PUBLISHE_MESSAGE_FAILED, e);
        }
    }
}
