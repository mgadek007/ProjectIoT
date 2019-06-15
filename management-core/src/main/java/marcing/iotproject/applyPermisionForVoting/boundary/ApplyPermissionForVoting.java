package marcing.iotproject.applyPermisionForVoting.boundary;

import com.google.gson.*;
import marcing.iotproject.errors.ConvertError;
import marcing.iotproject.manageVoting.boundary.TriggerVoting;
import marcing.iotproject.manageVoting.entity.VotingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;

public class ApplyPermissionForVoting extends HttpServlet {

    private Logger LOG = LoggerFactory.getLogger(ApplyPermissionForVoting.class);

    private static final String SUCCESS = "Success change list";
    private static final String SUCCESS_EVRY = "Success every in change pass";
    private static final String FAILED_MESSAGE = "Something go wrong by processing";
    private static final String PROBLEM_WITH_CONVERT_INPUT_DATA = "Problem with convert user";
    private static final String FALSE_VALUE = "false";

    private static TriggerVoting triggerVoting = TriggerVoting.getInstance();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response){
        try{

            LOG.info(SUCCESS);
            VotingObject votingPermission = convert(request);
            boolean votingPermissionValue = validateVotingPermission(votingPermission.getValue());
            triggerVoting.applyPermissionForVoting(votingPermission.getId(), votingPermissionValue);
            response.setStatus(200);
            LOG.info(SUCCESS_EVRY);
        }catch (Exception e){
            response.setStatus(500);
            LOG.error(FAILED_MESSAGE, e);
        }
    }

    private VotingObject convert(HttpServletRequest request) throws IOException {
        VotingObject votingObject;
        try{
            JsonElement elem = new JsonParser().parse(new InputStreamReader(request.getInputStream()));
            Gson gson  = new GsonBuilder().create();
            votingObject = gson.fromJson(elem, VotingObject.class);
        } catch (JsonIOException | JsonSyntaxException e){
            LOG.error(PROBLEM_WITH_CONVERT_INPUT_DATA);
            LOG.error(e.toString());
            throw new ConvertError(PROBLEM_WITH_CONVERT_INPUT_DATA);
        }
        return votingObject;
    }

    private boolean validateVotingPermission(String value){
        boolean result = true;
        if (value.equals(FALSE_VALUE)){
            result = false;
        }
        return result;
    }
}
