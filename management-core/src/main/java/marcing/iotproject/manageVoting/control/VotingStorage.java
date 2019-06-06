package marcing.iotproject.manageVoting.control;

import marcing.iotproject.manageVoting.entity.RoomStorageVoting;
import marcing.iotproject.manageVoting.entity.VotingObject;

import javax.ejb.Singleton;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class VotingStorage {

    private static final String OK = "ok";
    private static final String UP = "up";
    private static final String DOWN = "down";

    private static VotingStorage OurInstance = new VotingStorage();

    public static VotingStorage getInstance(){
        return OurInstance;
    }

    private VotingStorage(){}


    private Map<String, RoomStorageVoting> votingMap = new HashMap<>();

    public void applyVote(VotingObject vote){
        if (votingMap.containsKey(vote.getId())){
            RoomStorageVoting oldVote = votingMap.get(vote.getId());
            RoomStorageVoting result = updateObject(vote.getValue(), oldVote);
            votingMap.put(vote.getId(), result);
        }else {
            RoomStorageVoting init = new RoomStorageVoting();
            RoomStorageVoting result = updateObject(vote.getValue(), init);
            votingMap.put(vote.getId(), result);
        }

    }

    private RoomStorageVoting updateObject(String value, RoomStorageVoting result){
        if(value.equals(OK)){
            result.incOk();
        }else if (value.equals(UP)){
            result.incUp();
        }else if (value.equals(DOWN)){
            result.incDown();
        }
        return result;
    }

    public Map<String, RoomStorageVoting> getMapForExecuteResult(){
        return this.votingMap;
    }

    public void cleanMap(){
        this.votingMap = new HashMap<>();
    }
}
