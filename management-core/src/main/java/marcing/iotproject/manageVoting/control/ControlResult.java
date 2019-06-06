package marcing.iotproject.manageVoting.control;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import marcing.iotproject.dataBaseConnection.boundary.DataBaseConnectionDB;
import marcing.iotproject.dataPreparator.entity.DataBlockDictionary;
import marcing.iotproject.manageVoting.entity.RoomStorageVoting;
import marcing.iotproject.managementRoom.entity.ManageDictionary;
import marcing.iotproject.managementRoom.entity.ManagementDTO;

import javax.ejb.Singleton;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Singleton
public class ControlResult {

    private DataBaseConnectionDB dataBase = new DataBaseConnectionDB();
    private static final String UP = "UP";
    private static final String OK = "OK";
    private static final String DOWN = "DOWN";
    private static final String FALSE = ManageDictionary.FALSE;
    private static final String TRUE = ManageDictionary.TRUE;

    private static final double PROG = 0.6;
    private static final double AIR_PROG = 0.4;
    private static final int CHANGE_TEMP = 1;
    private static VotingStorage votingStorage= VotingStorage.getInstance();
    private Map<String, String> resultMap = new HashMap<>();
    private Map<String, String> finalMap = new HashMap<>();

    public Map<String, String> executeResult() {
        finalMap = new HashMap<>();
        Map<String, RoomStorageVoting> voteMap = votingStorage.getMapForExecuteResult();
        calculateResult(voteMap);
        votingStorage.cleanMap();
        resultMap.forEach((key, value) -> prepareStringManagementDTOFormVoting(key, value));
        return finalMap;
    }

    private void calculateResult(Map<String, RoomStorageVoting> voteMap){
        resultMap = voteMap.entrySet().stream()
                        .collect(Collectors.toMap(Map.Entry::getKey, (entry) -> calculateResultValue(entry.getValue())));

    }

    private String calculateResultValue(RoomStorageVoting resultVoting){
        String result;
        int allVotes = resultVoting.getDown() + resultVoting.getUp() + resultVoting.getOk();
        if((double) resultVoting.getUp()/allVotes >= PROG){
            result = UP;
        }else if((double) resultVoting.getDown()/allVotes >= PROG){
            result = DOWN;
        }else {
            result = OK;
        }
        return result;
    }


    private void prepareStringManagementDTOFormVoting(String id, String value) {
        ManagementDTO managementDTO = new ManagementDTO();
        managementDTO.setId(id);
        double tempOut = Double.parseDouble(dataBase.getLastValueAttribute(id, DataBlockDictionary.TEMP_OUT, true));
        double tempInNow = Double.parseDouble(dataBase.getLastValueAttribute(id, DataBlockDictionary.TEMP_IN, true));
        double airOut = Double.parseDouble(dataBase.getLastValueAttribute(id, DataBlockDictionary.AIR_QUA_OUT, true));
        double tempToSet = changeTemFromVoting(tempInNow, value);
        if(tempToSet > tempOut | airOut < AIR_PROG){
            managementDTO.setIsWindowOpen(FALSE);
            managementDTO.setIsClimeOn(TRUE);
            managementDTO.setTemp(String.valueOf(tempToSet));
        }else{
            managementDTO.setIsWindowOpen(TRUE);
            managementDTO.setIsClimeOn(FALSE);
        }
        finalMap.put(id, prepareJson(managementDTO));
    }

    private double changeTemFromVoting(double tempNow, String voteResult){
        double resultTemp = tempNow;
        if (voteResult.equals(UP)){
            resultTemp = tempNow + CHANGE_TEMP;
        }else if(voteResult.equals(DOWN)){
            resultTemp = tempNow - CHANGE_TEMP;
        }
        return resultTemp;
    }

    private String prepareJson (ManagementDTO manage){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls();
        Gson gson = gsonBuilder.create();
        return gson.toJson(manage);
    }
}
