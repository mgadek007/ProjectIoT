package marcing.iotproject.appConnectionServlet.entity;


public class DataBlock {

    private String id;
    private String timestamp;
    private String temp_in;
    private String temp_out;
    private String light_in;
    private String isClimeOn;
    private String isWindowOpen;
    private String air_qua_in;
    private String air_qua_out;
    private String sound_detected;
    private String people_inside;


    private String getId() {
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

    private String getTempIn() {
        return temp_in;
    }

    public void setTempIn(String temp_in) {
        this.temp_in = temp_in;
    }

    private String getTempOut() {
        return temp_out;
    }

    public void setTempOut(String temp_out) {
        this.temp_out = temp_out;
    }

    private String getLightIn() {
        return light_in;
    }

    public void setLightIn(String light_in) {
        this.light_in = light_in;
    }

    private String getAirQuaIn() {
        return air_qua_in;
    }

    public void setAirQuaIn(String air_qua_in) {
        this.air_qua_in = air_qua_in;
    }

    private String getAirQuaOut() {
        return air_qua_out;
    }

    public void setAirQuaOut(String air_qua_out) {
        this.air_qua_out = air_qua_out;
    }

    private String isSoundDetected() {
        return sound_detected;
    }

    public void setSoundDetected(String sound_detected) {
        this.sound_detected = sound_detected;
    }

    private String getPeopleInside() {
        return people_inside;
    }

    public void setPeopleInside(String people_inside) {
        this.people_inside = people_inside;
    }

    public String toString(){
        return "DataBlock: \n"
                + AttributesDictionaryForDataBlock.ID + ": " + getId() + ",\n"
                + AttributesDictionaryForDataBlock.TIME_STAMP + ": " + getTimeStamp() + ",\n"
                + AttributesDictionaryForDataBlock.TEMP_IN + ": " + getTempIn() + ",\n"
                + AttributesDictionaryForDataBlock.TEMP_OUT + ": " + getTempOut() + ",\n"
                + AttributesDictionaryForDataBlock.LIGHT_INT + ": " + getLightIn() + ",\n"
                + AttributesDictionaryForDataBlock.AIR_AUA_IN + ": " + getAirQuaIn() + ",\n"
                + AttributesDictionaryForDataBlock.AIR_QUA_OUT + ": " + getAirQuaOut() + ",\n"
                + AttributesDictionaryForDataBlock.PEOPLE_INSIDE + ": " + getPeopleInside() + ",\n"
                + AttributesDictionaryForDataBlock.SOUND_DETECTED + ": " + isSoundDetected();
    }

    public String getIsWindowOpen() {
        return isWindowOpen;
    }

    public void setIsWindowOpen(String isWindowOpen) {
        this.isWindowOpen = isWindowOpen;
    }

    public String getIsClimeOn() {
        return isClimeOn;
    }

    public void setIsClimeOn(String isClimeOn) {
        this.isClimeOn = isClimeOn;
    }
}
