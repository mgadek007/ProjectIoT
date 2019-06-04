package marcing.iotproject.managementRoom.entity;

public class Converter {

    public boolean checkSound(String soundDetected) {
        return Boolean.getBoolean(soundDetected);
    }

    public boolean isPeopleInsight(String integer) {
        boolean result = false;
        int peopleInsight = Integer.parseInt(integer);
        if(peopleInsight > 0){
            result = true;
        }
        return result;
    }

    public double convertToDouble(String doubleString){
        return Double.parseDouble(doubleString);
    }
}
