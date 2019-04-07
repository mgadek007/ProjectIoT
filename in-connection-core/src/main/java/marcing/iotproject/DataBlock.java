package marcing.iotproject;


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

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getTemp_in() {
        return temp_in;
    }

    public void setTemp_in(String temp_in) {
        this.temp_in = temp_in;
    }

    public String getTemp_out() {
        return temp_out;
    }

    public void setTemp_out(String temp_out) {
        this.temp_out = temp_out;
    }

    public String getLight_in() {
        return light_in;
    }

    public void setLight_in(String light_in) {
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

    public String getAir_qua_in() {
        return air_qua_in;
    }

    public void setAir_qua_in(String air_qua_in) {
        this.air_qua_in = air_qua_in;
    }

    public String getAir_qua_out() {
        return air_qua_out;
    }

    public void setAir_qua_out(String air_qua_out) {
        this.air_qua_out = air_qua_out;
    }

    public String isSound_detected() {
        return sound_detected;
    }

    public void setSound_detected(String sound_detected) {
        this.sound_detected = sound_detected;
    }

    public String getPeople_inside() {
        return people_inside;
    }

    public void setPeople_inside(String people_inside) {
        this.people_inside = people_inside;
    }
}
