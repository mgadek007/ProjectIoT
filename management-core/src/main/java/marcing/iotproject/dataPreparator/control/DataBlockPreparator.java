package marcing.iotproject.dataPreparator.control;

import com.google.common.base.Strings;
import marcing.iotproject.dataBaseConnection.boundary.DataBaseConnectionDB;
import marcing.iotproject.dataPreparator.entity.DataBlock;
import marcing.iotproject.dataPreparator.entity.DataBlockDictionary;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class DataBlockPreparator {

    private DataBaseConnectionDB dataBaseConnectionDB = new DataBaseConnectionDB();


    DataBlock prepareDataBlockForFinalTable(String id){
        String tempIn = getFinalValueOfSttribute(id, DataBlockDictionary.TEMP_IN);
        String tempOut = getFinalValueOfSttribute(id, DataBlockDictionary.TEMP_OUT);
        String light = getFinalValueOfSttribute(id, DataBlockDictionary.LIGHT_INT);
        String airIn = getFinalValueOfSttribute(id, DataBlockDictionary.AIR_AUA_IN);
        String airOut = getFinalValueOfSttribute(id, DataBlockDictionary.AIR_QUA_OUT);
        String isWindowOpen = dataBaseConnectionDB.getLastValueAttribute(id, DataBlockDictionary.IS_WINDOW_OPEN, false);
        String isClimeOn = dataBaseConnectionDB.getLastValueAttribute(id, DataBlockDictionary.IS_CLIME_ON, false);
        String people = dataBaseConnectionDB.getLastValueAttribute(id, DataBlockDictionary.PEOPLE_INSIDE, false);
        String sound = dataBaseConnectionDB.getLastValueAttribute(id, DataBlockDictionary.SOUND_DETECTED, false);

        DataBlock result =new DataBlock();
        result.setId(id);
        result.setTempIn(tempIn);
        result.setTempOut(tempOut);
        result.setLightIn(light);
        result.setIsWindowOpen(isWindowOpen);
        result.setIsClimeOn(isClimeOn);
        result.setAirQuaIn(airIn);
        result.setAirQuaOut(airOut);
        result.setPeopleInside(people);
        result.setSoundDetected(sound);
        return result;

    }

    private String getFinalValueOfSttribute(String id, String attributeName){
        List<String> result = dataBaseConnectionDB.getLastAttributeListValues(id, attributeName);
        return average(result);
    }

    private String average(List<String> listValue) {
        listValue = clearList(listValue);
        double average = calculateAverage(listValue);
        return String.valueOf(average);
    }

    private List<String> clearList(List<String> listValue) {
        return listValue.stream()
                .filter(data -> !Strings.isNullOrEmpty(data))
                .collect(Collectors.toList());
    }

    private double calculateAverage(List<String> listValue) {
        double sum;
        int size = listValue.size();
        sum = IntStream.range(0, size)
                .mapToDouble(i -> Double.valueOf(listValue.get(i)))
                .sum();
        return sum/size;
    }
}
