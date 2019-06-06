package marcing.iotproject.managementRoom.entity;

public class Converter {

    private static final String ONE = "1";

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

    public String convertToBoolean(String bool){
        String result = ManageDictionary.FALSE;
        if(bool.equals(ONE) || bool.equals(ManageDictionary.TRUE)){
            result = ManageDictionary.TRUE;
        }
        return result;
    }
}
