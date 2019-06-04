package marcing.iotproject.mqttBrokerClient.control;

import com.google.gson.*;
import marcing.iotproject.dataPreparator.entity.DataBlock;
import marcing.iotproject.errors.ConvertError;
import marcing.iotproject.managementRoom.entity.MnageLightDTO;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import static java.nio.charset.StandardCharsets.UTF_8;

public class GetDataFromMessage {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private static final String PROBLEM_WITH_CONVERT_INPUT_DATA = "Problem with convert data";


    public String getId(MqttMessage message){
        return new String(message.getPayload(), UTF_8);
    }

    public DataBlock convertJsonToDataBlock(MqttMessage request) {
        DataBlock dataBlock;
        try{
            String elem =  getId(request);
            Gson gson  = new GsonBuilder().create();
            dataBlock = gson.fromJson(elem, DataBlock.class);
        } catch (JsonIOException | JsonSyntaxException e){
            log.error(PROBLEM_WITH_CONVERT_INPUT_DATA);
            log.error(e.toString());
            throw new ConvertError(PROBLEM_WITH_CONVERT_INPUT_DATA);
        }
        return dataBlock;
    }

    public MnageLightDTO convertJsonToManageLisght(MqttMessage request) {
        MnageLightDTO mnageLightDTO;
        try{
            String elem =  getId(request);
            Gson gson  = new GsonBuilder().create();
            mnageLightDTO = gson.fromJson(elem, MnageLightDTO.class);
        } catch (JsonIOException | JsonSyntaxException e){
            log.error(PROBLEM_WITH_CONVERT_INPUT_DATA);
            log.error(e.toString());
            throw new ConvertError(PROBLEM_WITH_CONVERT_INPUT_DATA);
        }
        return mnageLightDTO;
    }


}
