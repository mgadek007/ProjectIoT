package marcing.iotproject.mqttBrokerClient.boundary;

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
public class VoteManagerMqtt implements MqttCallbackExtended {


    private static final String CLIENT_ID = "VoteServerMQtt";
    private final Logger LOG = LoggerFactory.getLogger(VoteManagerMqtt.class);
    private static final String PROBLEM_WITH_INIT = "Problem with init DataDorAppSender";
    private static final String INIT_CLASS_CORRECTLY = "Inicialization class was done correctly";
    private static final String PUBLISHED_MESSAGE = "Message published correctly";
    private static final String PUBLISHE_MESSAGE_FAILED = "Client no publish a message";
    private static final String TRY_PUBLISH_MESSAGE = "Try publish a message: {0}";


    private MqttClient client;

    private VoteManagerMqtt(){
            init();
        }

    private void init(){
        try {
            this.client = new MqttClient(ConnectionMqttDictionary.BROKER, CLIENT_ID + new Random().nextInt());
            MqttConnectOptions options = new MqttConnectOptions();
            options.setUserName(ConnectionMqttDictionary.MQTT_USER);
            options.setPassword(ConnectionMqttDictionary.MQTT_PASSWORD.toCharArray());
            options.setAutomaticReconnect(true);
            options.getDebug();
            options.setKeepAliveInterval(999999999);
            options.setCleanSession(true);
            client.connect(options);
            client.setCallback(this);
            client.subscribe(ConnectionMqttDictionary.VOTE_TOPIC);
            LOG.info(INIT_CLASS_CORRECTLY);
        } catch (MqttException e) {
            LOG.error(PROBLEM_WITH_INIT, e);
        }

    }

    @Override
    public void connectionLost(Throwable throwable) {
        new VoteManagerMqtt();
    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) {
        if (ConnectionMqttDictionary.VOTE_TOPIC.equals(s)){
            LOG.info("bahb");
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        LOG.info(ConnectionMqttDictionary.PUBLISHED_MESSAGE);
    }

    @Override
    public void connectComplete(boolean b, String s) {
        try {
            client.subscribe(ConnectionMqttDictionary.VOTE_TOPIC);
            LOG.info(MessageFormat.format(ConnectionMqttDictionary.SUBSCRIBE_TOPIC_CORRECT, ConnectionMqttDictionary.VOTE_TOPIC));
        }catch (MqttException e){
            LOG.error(ConnectionMqttDictionary.SUBSCCRIBE_TOPIC_FAILED, e);
        }
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
}

