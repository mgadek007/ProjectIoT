package marcing.iotproject.mqttBrokerClient.boundary;

import marcing.iotproject.mqttBrokerClient.entity.ConnectionMqttDictionary;
import org.eclipse.paho.client.mqttv3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Singleton;
import javax.ejb.Startup;

@Singleton
@Startup
public class VoteManagerServer implements MqttCallback {

    private static final String CLIENT_ID = "VoteManagerOnServer";
    private final Logger LOG = LoggerFactory.getLogger(DataForAppSender.class);
    private static final String PROBLEM_WITH_INIT = "Problem with init VoteManagerServer";
    private static final String INIT_CLASS_CORRECTLY = "Inicialization class was done correctly";
    private static final String TRY_PUBLISH_MESSAGE = "Try publish a message: {0}";
    private static final String PUBLISHED_MESSAGE = "Message published correctly";
    private static final String PUBLISHE_MESSAGE_FAILED = "Client no publish a message";
    private static final String CONNECTION_LOST = "Connection lost";

    private MqttClient client;

    public VoteManagerServer(){
        init();
    }

    private void init(){
        try {
            this.client = new MqttClient(ConnectionMqttDictionary.BROKER, CLIENT_ID);
            MqttConnectOptions options = new MqttConnectOptions();
            options.setUserName(ConnectionMqttDictionary.MQTT_USER);
            options.setPassword(ConnectionMqttDictionary.MQTT_PASSWORD.toCharArray());
            options.setAutomaticReconnect(true);
            options.setCleanSession(true);
            options.getDebug();
            client.connect(options);
            client.setCallback(this);
            LOG.info(INIT_CLASS_CORRECTLY);
            client.subscribe(ConnectionMqttDictionary.VOTE_TOPIC);
        } catch (MqttException e) {
            LOG.error(PROBLEM_WITH_INIT, e);

        }

    }

    @Override
    public void connectionLost(Throwable throwable) {
        LOG.error(CONNECTION_LOST, throwable);

    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) {
        LOG.info("Wiadomość przyszła"+ mqttMessage.toString());
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        LOG.info(PUBLISHED_MESSAGE);
    }
}
