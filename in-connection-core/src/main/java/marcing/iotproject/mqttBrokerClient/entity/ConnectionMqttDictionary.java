package marcing.iotproject.mqttBrokerClient.entity;

public class ConnectionMqttDictionary {

    public static final String BROKER = "tcp://ec2-35-156-20-198.eu-central-1.compute.amazonaws.com:6979";
//    public static final String BROKER = "tcp://localhost:1883";
    public static final String IN_CONN_TOPIC = "inConn/updateinfo";
    public static final String PREPATE_DATA_TOPIC = "menagement/preparedata";
    public static final String MQTT_USER = "usereitMqtt";
    public static final String MQTT_PASSWORD = "usereitPass";
}
