package marcing.iotproject.manageVoting.entity;

public class RoomStorageVoting {

    private int up;
    private int down;
    private int ok;

    public RoomStorageVoting(){
        this.up = 0;
        this.down = 0;
        this.ok = 0;
    }

    public int getUp() {
        return up;
    }

    public void setUp(int up) {
        this.up = up;
    }

    public int getDown() {
        return down;
    }

    public void setDown(int down) {
        this.down = down;
    }

    public int getOk() {
        return ok;
    }

    public void setOk(int ok) {
        this.ok = ok;
    }

    public void incUp(){
        this.up = this.up + 1;
    }

    public void incDown(){
        this.down = this.down + 1;
    }

    public void incOk(){
        this.ok = this.ok + 1;
    }
}
