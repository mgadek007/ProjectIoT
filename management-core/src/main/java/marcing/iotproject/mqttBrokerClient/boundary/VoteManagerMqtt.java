package marcing.iotproject.mqttBrokerClient.boundary;

import marcing.iotproject.manageVoting.control.VotingStorage;
import marcing.iotproject.manageVoting.entity.VotingObject;
import marcing.iotproject.mqttBrokerClient.control.GetDataFromMessage;
import marcing.iotproject.mqttBrokerClient.entity.ConnectionMqttDictionary;
import org.eclipse.paho.client.mqttv3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Startup;
import javax.faces.convert.ConverterException;
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
    private static final String START_PROCESS_VOTE = "Start process vote";
    private static final String SUCCESS_PROCESS = "Success processing vote";
    private static final String FAILED_PROCESS = "Failed processing vote";



    private static VotingStorage votingStorage= VotingStorage.getInstance();

    private GetDataFromMessage dataGetter = new GetDataFromMessage();


    private MqttClient client;

    public VoteManagerMqtt(){
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
            options.setKeepAliveInterval(120);
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
        try {
            LOG.info(START_PROCESS_VOTE);
            VotingObject vote = dataGetter.convertJsonToVote(mqttMessage);
            votingStorage.applyVote(vote);
            LOG.info(SUCCESS_PROCESS);
        }catch (Exception e){
            LOG.error(FAILED_PROCESS, e);
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
        messageForApp.setQos(2);
        LOG.debug(MessageFormat.format(TRY_PUBLISH_MESSAGE, message));
        try {
            client.publish(topic, messageForApp);
            LOG.info(PUBLISHED_MESSAGE);
        } catch (Exception e) {
            LOG.error(PUBLISHE_MESSAGE_FAILED, e);
        }
    }
}

