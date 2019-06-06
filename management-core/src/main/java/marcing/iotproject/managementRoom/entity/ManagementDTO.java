package marcing.iotproject.managementRoom.entity;

public class ManagementDTO {

    private String id;
    private String isClimeOn;
    private String isWindowOpen;
    private String temp;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsClimeOn() {
        return isClimeOn;
    }

    public void setIsClimeOn(String isClimeOn) {
        this.isClimeOn = isClimeOn;
    }

    public String getIsWindowOpen() {
        return isWindowOpen;
    }

    public void setIsWindowOpen(String isWindowOpen) {
        this.isWindowOpen = isWindowOpen;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }
}
