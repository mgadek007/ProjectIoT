package marcing.iotproject.dataPreparator.control;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import marcing.iotproject.dataBaseConnection.boundary.DataBaseConnectionDB;
import marcing.iotproject.dataPreparator.entity.DataBlock;
import marcing.iotproject.mqttBrokerClient.control.GetDataFromMessage;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;

public class DatePreparator {

    private DataBlockPreparator dataBlockPreparator = new DataBlockPreparator();
    private DataBaseConnectionDB dataBaseConnectionDB = new DataBaseConnectionDB();
    private GetDataFromMessage dataGetter = new GetDataFromMessage();

    private final Logger LOG = LoggerFactory.getLogger(DatePreparator.class);
    private static final String ERROR_WITH_CONVERT_TO_JSON = "Error with convert dataBlock to json";
    private static final String PREPARED_AND_LOAD_DATA = "Prepared and load to DB the data for room with id: {0}";
    private static final String EMPTY_STRING = "";
    private static final String SUCCESS_RESULT = "Success prepared data";
    private static final String FAILED_RESULT = "Something go wrong";
    private static final String START_PREPARE_DATA = "Start prepare data for room with id: {0}";




    public DataBlock prepareData(String id) {
        DataBlock data = dataBlockPreparator.prepareDataBlockForFinalTable(id);
        dataBaseConnectionDB.loadToFinalDB(data);
        LOG.info(MessageFormat.format(PREPARED_AND_LOAD_DATA, id));
        data.setTimeStamp(null);
        return data;
    }

    public String prepareJson(DataBlock dataBlock) {
        String result;
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            result = objectMapper.writeValueAsString(dataBlock);
        }catch (JsonProcessingException e){
            LOG.error(ERROR_WITH_CONVERT_TO_JSON, e);
            return EMPTY_STRING;
        }
        return result;
    }

    public DataBlock prepareDataFromMqtt(MqttMessage message){
        String id = dataGetter.getId(message);
        LOG.info(MessageFormat.format(START_PREPARE_DATA, id));
        DataBlock dataBlock = new DataBlock();
        try {
            dataBlock = prepareData(id);
            LOG.info(MessageFormat.format(SUCCESS_RESULT, id));

        }catch (Throwable e){
            LOG.info(MessageFormat.format(FAILED_RESULT, id), e);
        }
        return dataBlock;
    }



}
