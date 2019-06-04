package marcing.iotproject.managementRoom.boundary;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import marcing.iotproject.dataPreparator.control.DatePreparator;
import marcing.iotproject.dataPreparator.entity.DataBlock;
import marcing.iotproject.managementRoom.control.AirConditionChecker;
import marcing.iotproject.managementRoom.entity.ManagementDTO;
import marcing.iotproject.managementRoom.entity.MnageLightDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ManageRoom {

    private AirConditionChecker airConditionChecker = new AirConditionChecker();

    private final Logger LOG = LoggerFactory.getLogger(DatePreparator.class);
    private static final String ERROR_WITH_CONVERT_TO_JSON = "Error with convert dataBlock to json";
    private static final String EMPTY_STRING = "";



    public String manageAir(DataBlock dataBlock){
        ManagementDTO result = new ManagementDTO();
        result = airConditionChecker.checkAir(dataBlock, result);
        return prepareJson(result);
    }

    private String prepareJson(Object data) {
        String result;
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            result = objectMapper.writeValueAsString(data);
        }catch (JsonProcessingException e){
            LOG.error(ERROR_WITH_CONVERT_TO_JSON, e);
            return EMPTY_STRING;
        }
        return result;
    }

    public String manageLight(MnageLightDTO request){
        MnageLightDTO result = new MnageLightDTO();
        result.setId(request.getId());
        result.setLightIn(checkLightIn(request.getLightIn()));
        return prepareJson(result);
    }

    private String checkLightIn(String lightIn) {
        double lightValue = Double.parseDouble(lightIn);
        if (lightValue > 1){
            lightValue = lightValue/100;
        }
        return String.valueOf(lightValue);
    }


}
