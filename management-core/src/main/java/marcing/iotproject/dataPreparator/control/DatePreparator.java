package marcing.iotproject.dataPreparator.control;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import marcing.iotproject.dataBaseConnection.boundary.DataBaseConnectionDB;
import marcing.iotproject.dataPreparator.entity.DataBlock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;

public class DatePreparator {

    private DataBlockPreparator dataBlockPreparator = new DataBlockPreparator();
    private DataBaseConnectionDB dataBaseConnectionDB = new DataBaseConnectionDB();
    private final Logger LOG = LoggerFactory.getLogger(DatePreparator.class);
    private static final String ERROR_WITH_CONVERT_TO_JSON = "Error with convert dataBlock to json";
    private static final String PREPARED_AND_LOAD_DATA = "Prepared and load to DB the data for room with id: {0}";
    private static final String EMPTY_STRING = "";



    public String prepareData(String id) {
        DataBlock data = dataBlockPreparator.prepareDataBlockForFinalTable(id);
        dataBaseConnectionDB.loadToFinalDB(data);
        LOG.info(MessageFormat.format(PREPARED_AND_LOAD_DATA, id));
        data.setTimeStamp(null);
        return prepareJson(data);
    }

    private String prepareJson(DataBlock dataBlock) {
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


}
