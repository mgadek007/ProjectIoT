package marcing.iotproject.dataPreparator.entity;


public class DataBlock {

    private String id;
    private String timestamp;
    private String temp_in;
    private String temp_out;
    private String light_in;
    private String red;
    private String green;
    private String blue;
    private String air_qua_in;
    private String air_qua_out;
    private String sound_detected;
    private String people_inside;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String getTimeStamp() {
        return timestamp;
    }

    public void setTimeStamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getTempIn() {
        return temp_in;
    }

    public void setTempIn(String temp_in) {
        this.temp_in = temp_in;
    }

    public String getTempOut() {
        return temp_out;
    }

    public void setTempOut(String temp_out) {
        this.temp_out = temp_out;
    }

    public String getLightIn() {
        return light_in;
    }

    public void setLightIn(String light_in) {
        this.light_in = light_in;
    }

    public String getRed() {
        return red;
    }

    public void setRed(String red) {
        this.red = red;
    }

    public String getGreen() {
        return green;
    }

    public void setGreen(String green) {
        this.green = green;
    }

    public String getBlue() {
        return blue;
    }

    public void setBlue(String blue) {
        this.blue = blue;
    }

    public String getAirQuaIn() {
        return air_qua_in;
    }

    public void setAirQuaIn(String air_qua_in) {
        this.air_qua_in = air_qua_in;
    }

    public String getAirQuaOut() {
        return air_qua_out;
    }

    public void setAirQuaOut(String air_qua_out) {
        this.air_qua_out = air_qua_out;
    }

    public String isSoundDetected() {
        return sound_detected;
    }

    public void setSoundDetected(String sound_detected) {
        this.sound_detected = sound_detected;
    }

    public String getPeopleInside() {
        return people_inside;
    }

    public void setPeopleInside(String people_inside) {
        this.people_inside = people_inside;
    }

    public String toString(){
        return "DataBlock: \n"
                + DataBlockDictionary.ID + ": " + getId() + ",\n"
                + DataBlockDictionary.TIME_STAMP + ": " + getTimeStamp() + ",\n"
                + DataBlockDictionary.TEMP_IN + ": " + getTempIn() + ",\n"
                + DataBlockDictionary.TEMP_OUT + ": " + getTempOut() + ",\n"
                + DataBlockDictionary.LIGHT_INT + ": " + getLightIn() + ",\n"
                + DataBlockDictionary.RED + ": " + getRed() + ",\n"
                + DataBlockDictionary.GREEN + ": " + getGreen() + ",\n"
                + DataBlockDictionary.BLUE + ": " + getBlue() + ",\n"
                + DataBlockDictionary.AIR_AUA_IN + ": " + getAirQuaIn() + ",\n"
                + DataBlockDictionary.AIR_QUA_OUT + ": " + getAirQuaOut() + ",\n"
                + DataBlockDictionary.PEOPLE_INSIDE + ": " + getPeopleInside() + ",\n"
                + DataBlockDictionary.SOUND_DETECTED + ": " + isSoundDetected();
    }
}
