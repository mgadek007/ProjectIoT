package marcing.iotproject.manageVoting.boundary;

import marcing.iotproject.manageVoting.control.ControlResult;
import marcing.iotproject.mqttBrokerClient.boundary.VoteManagerMqtt;
import marcing.iotproject.mqttBrokerClient.entity.ConnectionMqttDictionary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.util.Date;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

@Startup
@Singleton
public class TriggerVoting {

    private static final String START = "START";
    private static final String STOP = "STOP";
    private ControlResult controlResult = new ControlResult();
    private VoteManagerMqtt voteManagerMqtt = new VoteManagerMqtt();
    private final Logger LOG = LoggerFactory.getLogger(TriggerVoting.class);
    private static final String START_VOTING = "Start Voting";
    private static final String STOP_VOTING = "Stop Voting";
    private static final String START_EXCECUTE_RESULT = "Start excexute voting result";
    private static final String RESET_COUNTER = "Reset Counter";
    private static final String TOPIC = ConnectionMqttDictionary.APP_START_STOP_VOTING;


    private int NOW = 0;
    private final int STOP_INT = 5;
    private final int RESULT = 6;
    private final int RESTART = 15;

    private long delayOfStart = 60000; //1 min
    private LoopTask taskToStart = new LoopTask();
    private Timer timerStart = new Timer("TaskNameStart");

    @PostConstruct
    public void start() {
        timerStart.cancel();
        timerStart = new Timer("TaskNameStart");
        Date executionDate = new Date(); // no params = now
        timerStart.scheduleAtFixedRate(taskToStart, executionDate, delayOfStart);
    }

    private class LoopTask extends TimerTask {
        public void run() {
            if(NOW == 0){
                voteManagerMqtt.sendData(TOPIC, START);
                LOG.info(START_VOTING);
            }
            if(NOW == STOP_INT) {
                voteManagerMqtt.sendData(TOPIC, STOP);
                LOG.info(STOP_VOTING);
            }
            if(NOW == RESULT){
                LOG.info(START_EXCECUTE_RESULT);
                Map<String, String> result =  controlResult.executeResult();
                result.forEach((key, value) -> sendDataForHardware(key, value));
            }
            if (NOW == RESTART){
                NOW = -1;
                LOG.info(RESET_COUNTER);
            }
            NOW++;
        }
    }

    private void sendDataForHardware(String idRoom, String message){
        voteManagerMqtt.sendData(ConnectionMqttDictionary.HARDWARE_TOPIC_MANAGEMENT, message);
    }

    public static void main(String[] args) {
        TriggerVoting executingTask = new TriggerVoting();
        executingTask.start();
    }


}
