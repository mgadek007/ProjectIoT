package marcing.iotproject.inConnectionServlet.control;

import com.google.gson.*;
import marcing.iotproject.DataBlock;
import marcing.iotproject.errors.ConvertErrorException;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStreamReader;

public class BodyReader {

    private final String PROBLEM_WITH_CONVERT_INPUT_DATA = "Problem with convert data";

    public DataBlock convertJsonToDataBlock(HttpServletRequest request) throws IOException {
        DataBlock dataBlock;
        try{
            JsonElement elem = new JsonParser().parse(new InputStreamReader(request.getInputStream()));
            Gson gson  = new GsonBuilder().create();
            dataBlock = gson.fromJson(elem, DataBlock.class);
        } catch (JsonIOException | JsonSyntaxException e){
            throw new ConvertErrorException(PROBLEM_WITH_CONVERT_INPUT_DATA);
        }

        return dataBlock;
    }
}
