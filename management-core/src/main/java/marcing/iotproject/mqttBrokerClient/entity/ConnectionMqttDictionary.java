package marcing.iotproject.mqttBrokerClient.entity;

public class ConnectionMqttDictionary {

//    public static final String BROKER = "tcp://localhost:1883";
    public static final String BROKER = "tcp://ec2-35-156-20-198.eu-central-1.compute.amazonaws.com:6979";

    public static final String APP_TOPIC = "app/updateinfo/{0}";
    public static final String PREPATE_DATA_TOPIC = "menagement/preparedata";
    public static final String MANAGENENT_LIGHT_TOPIC = "management/lightmanage";
    public static final String HARDWARE_TOPIC_MANAGEMENT = "hardware/management";
    public static final String HARDWARE_LIGHT_TOPIC_MANAGEMENT = "hardware/management/light";
    public static final String VOTE_TOPIC = "app/votemanager/123";
    public static final String MQTT_USER = "usereitMqtt";
    public static final String MQTT_PASSWORD = "usereitPass";
    public static final String SUBSCRIBE_TOPIC_CORRECT = "Subscribe topic: {0} done";
    public static final String SUBSCCRIBE_TOPIC_FAILED = "Subscribe topic: [0} failed";
    public static final String PUBLISHED_MESSAGE = "Message published correctly";

}
